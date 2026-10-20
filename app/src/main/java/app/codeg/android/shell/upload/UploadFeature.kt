package app.codeg.android.shell.upload

import android.net.Uri
import android.webkit.MimeTypeMap
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import app.codeg.android.R
import app.codeg.android.shell.ShellFeature
import app.codeg.android.shell.ShellHost
import java.io.File
import java.util.Locale

/** Owns one WebView upload callback, including every cancellation path. */
class UploadFeature : ShellFeature {
    private var callback: ValueCallback<Array<Uri>>? = null
    private var menu by mutableStateOf(false)
    private var mimeTypes = arrayOf("*/*")
    private var multiple = false
    private var externalPending = false
    private var cameraUri: Uri? = null
    private var cameraFile: File? = null
    private lateinit var photo: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var photos: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var document: ActivityResultLauncher<Array<String>>
    private lateinit var documents: ActivityResultLauncher<Array<String>>
    private lateinit var camera: ActivityResultLauncher<Uri>

    override fun onCreate(host: ShellHost) {
        val activity = host.activity
        photo = activity.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            externalPending = false
            complete(it?.let { uri -> arrayOf(uri) })
        }
        photos = activity.registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {
            externalPending = false
            complete(it.takeIf { uris -> uris.isNotEmpty() }?.toTypedArray())
        }
        document = activity.registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            externalPending = false
            complete(it?.let { uri -> arrayOf(uri) })
        }
        documents = activity.registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) {
            externalPending = false
            complete(it.takeIf { uris -> uris.isNotEmpty() }?.toTypedArray())
        }
        camera = activity.registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            externalPending = false
            val uri = cameraUri
            if (!success) cameraFile?.delete()
            cameraUri = null
            cameraFile = null
            complete(if (success && uri != null) arrayOf(uri) else null)
        }
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                complete(null)
                // Keep an in-flight camera file: the external camera may still be writing it.
                photo.unregister()
                photos.unregister()
                document.unregister()
                documents.unregister()
                camera.unregister()
            }
        })
    }

    override fun onShowFileChooser(
        host: ShellHost,
        view: WebView,
        callback: ValueCallback<Array<Uri>>,
        params: WebChromeClient.FileChooserParams,
    ): Boolean {
        // Never let an old external result complete a newer request.
        if (externalPending) {
            callback.onReceiveValue(null)
            return true
        }
        complete(null)
        this.callback = callback
        mimeTypes = normalizeTypes(params.acceptTypes)
        multiple = params.mode == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE
        if (params.mode != WebChromeClient.FileChooserParams.MODE_OPEN && !multiple) {
            complete(null)
        } else if (params.isCaptureEnabled && isVisual() && canTakePhoto()) {
            takePhoto(host)
        } else if (isVisual() || canTakePhoto()) {
            menu = true
        } else {
            openDocuments()
        }
        return true
    }

    private fun complete(uris: Array<Uri>?) {
        menu = false
        val pending = callback
        callback = null
        pending?.onReceiveValue(uris)
    }

    private fun isVisual() = mimeTypes.all { it.startsWith("image/") || it.startsWith("video/") }
    private fun canTakePhoto() = mimeTypes.any { it == "image/*" || it == "image/jpeg" || it == "*/*" }

    private fun launch(action: () -> Unit) {
        menu = false
        externalPending = true
        try {
            action()
        } catch (_: RuntimeException) {
            externalPending = false
            cameraFile?.delete()
            cameraFile = null
            cameraUri = null
            complete(null)
        }
    }

    private fun openDocuments() = launch {
        if (multiple) documents.launch(mimeTypes) else document.launch(mimeTypes)
    }

    private fun openPhotos() = launch {
        val type = when {
            mimeTypes.contentEquals(arrayOf("*/*")) -> ActivityResultContracts.PickVisualMedia.ImageAndVideo
            mimeTypes.size == 1 && mimeTypes[0] != "image/*" && mimeTypes[0] != "video/*" ->
                ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeTypes[0])
            mimeTypes.all { it.startsWith("image/") } -> ActivityResultContracts.PickVisualMedia.ImageOnly
            mimeTypes.all { it.startsWith("video/") } -> ActivityResultContracts.PickVisualMedia.VideoOnly
            else -> ActivityResultContracts.PickVisualMedia.ImageAndVideo
        }
        val request = PickVisualMediaRequest(type)
        if (multiple) photos.launch(request) else photo.launch(request)
    }

    private fun takePhoto(host: ShellHost) = launch {
        val directory = File(host.activity.cacheDir, "camera").apply { mkdirs() }
        val file = File.createTempFile("upload-", ".jpg", directory)
        cameraFile = file
        val uri = FileProvider.getUriForFile(host.activity, "${host.activity.packageName}.files", file)
        cameraUri = uri
        camera.launch(uri)
    }

    override fun onBack(host: ShellHost): Boolean {
        if (!menu) return false
        complete(null)
        return true
    }

    override fun onWebViewCreated(host: ShellHost, webView: WebView) {
        complete(null)
    }

    override fun onPageStarted(host: ShellHost, view: WebView, url: String, favicon: android.graphics.Bitmap?) {
        complete(null)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Overlay(host: ShellHost) {
        if (!menu) return
        ModalBottomSheet(onDismissRequest = { complete(null) }) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text(stringResource(R.string.shell_upload_title))
                if (isVisual() || mimeTypes.contentEquals(arrayOf("*/*"))) {
                    TextButton(onClick = { openPhotos() }, modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(R.string.shell_upload_gallery))
                    }
                }
                if (canTakePhoto()) {
                    TextButton(onClick = { takePhoto(host) }, modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(R.string.shell_upload_camera))
                    }
                }
                TextButton(onClick = { openDocuments() }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.shell_upload_files))
                }
            }
        }
    }

    private fun normalizeTypes(accept: Array<String>): Array<String> = accept
        .flatMap { it.split(',') }
        .map { it.trim().lowercase(Locale.ROOT).substringBefore(';').trim() }
        .filter { it.isNotEmpty() }
        .map { type ->
            when {
                type.startsWith('.') -> MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(type.drop(1)) ?: "*/*"
                type.matches(Regex("[a-z0-9!#$&^_.+*-]+/[a-z0-9!#$&^_.+*-]+")) -> type
                else -> "*/*"
            }
        }.distinct().let { types ->
            if (types.isEmpty() || "*/*" in types) arrayOf("*/*") else types.toTypedArray()
        }
}

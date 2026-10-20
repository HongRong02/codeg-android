package app.codeg.android.core.designsystem.component

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.FileViewerContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Shared read-only renderer. Network loading stays with the caller. */
@Composable
fun FileViewer(content: FileViewerContent) {
    when (content) {
        is FileViewerContent.Text -> {
            val lines = remember(content.text) { content.text.lineSequence().take(4001).toList() }
            val numbered = remember(lines) { lines.take(4000).mapIndexed { i, line -> "${i + 1}  $line" }.joinToString("\n") }
            Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(12.dp)) {
                CopyButton(content.text.lineSequence().take(4000).joinToString("\n"))
                if (content.text.isEmpty()) Text(stringResource(R.string.files_empty_file), color = CodegTheme.colors.textSecondary)
                if (lines.size > 4000) Text(stringResource(R.string.files_truncated_note, 4000), color = CodegTheme.colors.textSecondary)
                CodeBlock(numbered, language = content.path.substringAfterLast('.', ""), collapseAfter = Int.MAX_VALUE)
            }
        }
        is FileViewerContent.Image -> FileImage(content)
        is FileViewerContent.Unavailable -> Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(if (content.tooLarge) R.string.file_viewer_too_large else R.string.file_viewer_binary), color = CodegTheme.colors.textSecondary)
                Text(content.size?.let { stringResource(R.string.file_viewer_size, it) } ?: stringResource(R.string.file_viewer_size_unknown), color = CodegTheme.colors.textSecondary)
            }
        }
    }
}

@Composable
private fun FileImage(content: FileViewerContent.Image) {
    var decoding by remember(content.base64) { mutableStateOf(true) }
    val bitmap by produceState<androidx.compose.ui.graphics.ImageBitmap?>(null, content.base64) {
        value = withContext(Dispatchers.Default) {
            runCatching {
                val bytes = Base64.decode(content.base64.substringAfter(','), Base64.DEFAULT)
                val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
                val options = BitmapFactory.Options().apply {
                    while (bounds.outWidth / inSampleSize > 4096 || bounds.outHeight / inSampleSize > 4096) inSampleSize *= 2
                }
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)?.asImageBitmap()
            }.getOrNull()
        }
        decoding = false
    }
    var scale by remember(content) { mutableFloatStateOf(1f) }
    var offset by remember(content) { mutableStateOf(Offset.Zero) }
    val transform = rememberTransformableState { zoom, pan, _ ->
        scale = (scale * zoom).coerceIn(1f, 10f)
        offset = if (scale == 1f) Offset.Zero else offset + pan
    }
    Column(Modifier.fillMaxSize()) {
        TextButton(onClick = { scale = 1f; offset = Offset.Zero }) { Text(stringResource(R.string.file_viewer_reset_zoom)) }
        val image = bitmap
        if (image == null) {
            Text(stringResource(if (decoding) R.string.common_loading else R.string.file_viewer_image_unavailable), color = CodegTheme.colors.textSecondary, modifier = Modifier.padding(16.dp))
        } else Box(Modifier.fillMaxSize().clipToBounds().transformable(transform), contentAlignment = Alignment.Center) {
            Image(image, contentDescription = content.path, contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize().graphicsLayer {
                scaleX = scale; scaleY = scale; translationX = offset.x; translationY = offset.y
            })
        }
    }
}

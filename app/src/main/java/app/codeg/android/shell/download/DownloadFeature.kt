package app.codeg.android.shell.download

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.URLUtil
import android.webkit.WebView
import android.widget.Toast
import app.codeg.android.R
import app.codeg.android.shell.ShellFeature
import app.codeg.android.shell.ShellHost
import java.io.OutputStream
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.coroutines.resume

/** Downloads remain private pending MediaStore rows until the complete stream is written. */
class DownloadFeature : ShellFeature {
    private val client = OkHttpClient.Builder().followRedirects(false).followSslRedirects(false).build()
    @Volatile private var key = ""
    @Volatile private var bridgeServer: String? = null
    private val transfers = mutableMapOf<String, SavedFile>()

    override fun onCreate(host: ShellHost) {
        host.activity.lifecycle.addObserver(object : androidx.lifecycle.DefaultLifecycleObserver {
            override fun onDestroy(owner: androidx.lifecycle.LifecycleOwner) { clearTransfers() }
        })
    }

    private fun clearTransfers() {
        key = ""
        synchronized(transfers) { transfers.values.forEach { it.abort() }; transfers.clear() }
    }

    override fun onRenderProcessGone(host: ShellHost, view: WebView, detail: android.webkit.RenderProcessGoneDetail): Boolean {
        clearTransfers()
        return false
    }

    override fun onWebViewCreated(host: ShellHost, webView: WebView) {
        clearTransfers()
        webView.addJavascriptInterface(Bridge(host), "CodegShellDownload")
        webView.setDownloadListener { url, agent, disposition, mime, _ ->
            if (url.startsWith("blob:")) {
                webView.evaluateJavascript("window.__codegDownload && window.__codegDownload(${JSONObject.quote(url)}, '', ${JSONObject.quote(mime ?: "")})", null)
            } else download(host, url, "", disposition, mime, agent)
        }
    }

    override fun onPageStarted(host: ShellHost, view: WebView, url: String, favicon: android.graphics.Bitmap?) {
        clearTransfers()
    }

    override fun onPageFinished(host: ShellHost, view: WebView, url: String) {
        if (!sameOrigin(url, host.currentServerUrl) || !sameOrigin(view.url, host.currentServerUrl)) return
        bridgeServer = host.currentServerUrl
        key = UUID.randomUUID().toString()
        view.evaluateJavascript(downloadScript(key), null)
    }

    private fun download(host: ShellHost, url: String, name: String, disposition: String?, mime: String?, agent: String?) {
        val server = host.currentServerUrl
        val cookie = CookieManager.getInstance().getCookie(url)
        host.scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    if (url.startsWith("data:")) {
                        val comma = url.indexOf(','); require(comma > 0) { "Invalid data URL" }
                        val header = url.substring(5, comma)
                        val type = header.substringBefore(';').ifBlank { "application/octet-stream" }
                        val file = create(host, name.ifBlank { URLUtil.guessFileName("https://download/download", null, type) }, type)
                        try {
                            val payload = url.substring(comma + 1)
                            if (header.endsWith(";base64")) {
                                android.util.Base64InputStream(payload.byteInputStream(), Base64.DEFAULT).use { it.copyTo(file.output) }
                            } else {
                                var offset = 0
                                while (offset < payload.length) {
                                    if (payload[offset] == '%') {
                                        require(offset + 2 < payload.length) { "Invalid data URL" }
                                        file.output.write(payload.substring(offset + 1, offset + 3).toInt(16)); offset += 3
                                    } else {
                                        val end = payload.indexOf('%', offset).let { if (it < 0) payload.length else it }
                                        file.output.write(payload.substring(offset, end).toByteArray(Charsets.UTF_8)); offset = end
                                    }
                                }
                            }
                            file.finish()
                        } catch (e: Exception) { file.abort(); throw e }
                    } else {
                        var target = url
                        var authorized = false
                        var token: String? = null
                        var redirects = 0
                        while (true) {
                            val request = Request.Builder().url(target)
                            agent?.let { request.header("User-Agent", it) }
                            // CookieManager scopes cookies per target, including redirects.
                            val cookies = if (target == url) cookie else CookieManager.getInstance().getCookie(target)
                            cookies?.let { request.header("Cookie", it) }
                            if (authorized && sameOrigin(target, server)) request.header("Authorization", "Bearer $token")
                            val call = client.newCall(request.build())
                            val cancellation = host.scope.launch(start = kotlinx.coroutines.CoroutineStart.UNDISPATCHED) {
                                try { kotlinx.coroutines.awaitCancellation() } finally { call.cancel() }
                            }
                            try { call.execute().use { response ->
                                if (response.code == 401 && !authorized && sameOrigin(target, server)) {
                                    token = readToken(host, server)
                                    check(!token.isNullOrBlank()) { "HTTP 401" }
                                    authorized = true
                                } else if (response.code in listOf(301, 302, 303, 307, 308)) {
                                    require(++redirects <= 10) { "Too many redirects" }
                                    target = response.header("Location")?.let { response.request.url.resolve(it)?.toString() } ?: error("Invalid redirect")
                                    require(target.toHttpUrlOrNull() != null) { "Invalid download URL" }
                                    authorized = false
                                } else {
                                    check(response.isSuccessful) { "HTTP ${response.code}" }
                                    val type = response.header("Content-Type")?.substringBefore(';') ?: mime ?: "application/octet-stream"
                                    val cd = response.header("Content-Disposition") ?: disposition
                                    val filename = if (!cd.isNullOrBlank()) Regex("filename\\*\\s*=\\s*UTF-8''([^;]+)", RegexOption.IGNORE_CASE)
                                        .find(cd)?.groupValues?.get(1)?.trim()?.trim('"')?.let(Uri::decode)
                                        ?: URLUtil.guessFileName(target, cd, type) else name.ifBlank { URLUtil.guessFileName(target, null, type) }
                                    val file = create(host, filename, type)
                                    try { response.body.byteStream().use { it.copyTo(file.output) }; file.finish() }
                                    catch (e: Exception) { file.abort(); throw e }
                                    return@withContext
                                }
                            } } finally { cancellation.cancel() }
                        }
                    }
                }
            } catch (e: Exception) { failure(host, e) }
        }
    }

    private suspend fun readToken(host: ShellHost, server: String?): String? = withContext(Dispatchers.Main) {
        if (!sameOrigin(server, host.currentServerUrl) || !sameOrigin(host.webView?.url, server)) return@withContext null
        suspendCancellableCoroutine { continuation ->
            val script = "location.origin === new URL(${JSONObject.quote(server ?: "")}).origin ? localStorage.getItem('codeg_token') : null"
            host.webView?.evaluateJavascript(script) {
                if (continuation.isActive) continuation.resume(runCatching { org.json.JSONTokener(it).nextValue() as? String }.getOrNull())
            } ?: continuation.resume(null)
        }
    }

    private inner class Bridge(private val host: ShellHost) {
        private fun allowed(secret: String) = secret.isNotEmpty() && secret == key && sameOrigin(bridgeServer, host.currentServerUrl)
        @JavascriptInterface fun start(secret: String, id: String, name: String, mime: String): Boolean = synchronized(transfers) {
            if (!allowed(secret) || transfers.size >= 4 || transfers.containsKey(id)) return false
            try { val file = create(host, name.ifBlank { URLUtil.guessFileName("https://download/download", null, mime) }, mime.ifBlank { "application/octet-stream" })
                transfers[id] = file
                host.scope.launch {
                    kotlinx.coroutines.delay(600_000)
                    synchronized(transfers) { if (transfers[id] === file) { transfers.remove(id); file.abort() } }
                }
                true }
            catch (e: Exception) { failure(host, e); false }
        }
        @JavascriptInterface fun chunk(secret: String, id: String, data: String): Boolean = synchronized(transfers) {
            if (!allowed(secret) || data.length > 131072) return false
            try { (transfers[id] ?: return false).output.write(Base64.decode(data, Base64.DEFAULT)); true }
            catch (e: Exception) { transfers.remove(id)?.abort(); failure(host, e); false }
        }
        @JavascriptInterface fun end(secret: String, id: String) = synchronized(transfers) {
            if (allowed(secret)) transfers.remove(id)?.let { file ->
                try { file.finish() } catch (e: Exception) { file.abort(); failure(host, e) }
            }
        }
        @JavascriptInterface fun fail(secret: String, id: String) = synchronized(transfers) {
            if (allowed(secret)) { transfers.remove(id)?.abort(); failure(host, IllegalStateException("Blob transfer failed")) }
        }
        @JavascriptInterface fun link(secret: String, url: String, name: String) {
            if (allowed(secret)) host.activity.runOnUiThread {
                if (allowed(secret)) download(host, url, name, null, null, host.webView?.settings?.userAgentString)
            }
        }
    }

    @Synchronized private fun create(host: ShellHost, requested: String, mime: String): SavedFile {
        val resolver = host.activity.contentResolver
        val base = requested.substringAfterLast('/').substringAfterLast('\\').replace(Regex("[\\p{Cntrl}]"), "_").take(180).ifBlank { "download" }
        var name = base
        var index = 0
        fun exists(candidate: String): Boolean = resolver.query(MediaStore.Downloads.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Downloads._ID), "${MediaStore.Downloads.DISPLAY_NAME} = ?", arrayOf(candidate), null)?.use { it.moveToFirst() } ?: false
        while (exists(name)) {
            index++
            val dot = base.lastIndexOf('.').takeIf { it > 0 } ?: base.length
            name = "${base.take(dot)} ($index)${base.substring(dot)}"
        }
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, name); put(MediaStore.Downloads.MIME_TYPE, mime)
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            put(MediaStore.Downloads.IS_PENDING, 1)
        }) ?: error("Cannot create download")
        val output = try { resolver.openOutputStream(uri) ?: error("Cannot open download") }
        catch (e: Exception) { resolver.delete(uri, null, null); throw e }
        host.activity.runOnUiThread { Toast.makeText(host.activity, host.activity.getString(R.string.shell_download_started, name), Toast.LENGTH_SHORT).show() }
        return SavedFile(host, uri, output, name, mime)
    }

    private inner class SavedFile(val host: ShellHost, val uri: Uri, val output: OutputStream, val name: String, val mime: String) {
        fun abort() { runCatching { output.close() }; runCatching { host.activity.contentResolver.delete(uri, null, null) } }
        fun finish() {
            output.close()
            host.activity.contentResolver.update(uri, ContentValues().apply { put(MediaStore.Downloads.IS_PENDING, 0) }, null, null)
            host.activity.runOnUiThread {
                if (!host.activity.isFinishing && !host.activity.isDestroyed) AlertDialog.Builder(host.activity)
                    .setMessage(host.activity.getString(R.string.shell_download_saved, name))
                    .setPositiveButton(R.string.shell_download_open) { _, _ ->
                        try { host.activity.startActivity(Intent(Intent.ACTION_VIEW).setDataAndType(uri, mime).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)) }
                        catch (e: Exception) { failure(host, e) }
                    }.setNegativeButton(android.R.string.ok, null).show()
            }
        }
    }

    private fun failure(host: ShellHost, error: Exception) = host.activity.runOnUiThread {
        Toast.makeText(host.activity, host.activity.getString(R.string.shell_download_failed, error.message ?: error.javaClass.simpleName), Toast.LENGTH_LONG).show()
    }
}

internal fun origin(url: String?): String? = url?.toHttpUrlOrNull()?.let { "${it.scheme}://${it.host}:${it.port}" }
internal fun sameOrigin(left: String?, right: String?): Boolean = origin(left)?.let { it == origin(right) } ?: false

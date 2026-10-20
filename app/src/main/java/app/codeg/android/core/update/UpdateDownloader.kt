package app.codeg.android.core.update

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.ensureActive
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class UpdateDownloader @Inject constructor(@ApplicationContext private val context: Context) {
    private val client = OkHttpClient()

    suspend fun download(update: AppUpdate, progress: suspend (Float?) -> Unit): File = withContext(Dispatchers.IO) {
        val directory = File(context.cacheDir, "updates").apply { mkdirs() }
        val target = File(directory, "build-${update.versionCode}.apk")
        val partial = File(directory, "build-${update.versionCode}.part")
        target.delete()
        try {
            val digest = MessageDigest.getInstance("SHA-256")
            client.newCall(Request.Builder().url(update.apkUrl).build()).execute().use { response ->
                check(response.isSuccessful)
                val body = checkNotNull(response.body)
                val length = body.contentLength().takeIf { it > 0 } ?: update.size
                body.byteStream().use { input ->
                    partial.outputStream().use { output ->
                        val buffer = ByteArray(64 * 1024)
                        var total = 0L
                        while (true) {
                            coroutineContext.ensureActive()
                            val count = input.read(buffer)
                            if (count == -1) break
                            output.write(buffer, 0, count)
                            digest.update(buffer, 0, count)
                            total += count
                            progress(if (length > 0) (total.toFloat() / length).coerceIn(0f, 1f) else null)
                        }
                        check(total > 0)
                        if (length > 0) check(total == length)
                    }
                }
            }
            update.sha256Url?.let { url ->
                val expected = client.newCall(Request.Builder().url(url).build()).execute().use { response ->
                    check(response.isSuccessful)
                    checkNotNull(response.body).string().trim().split(Regex("\\s+"))[0]
                }
                check(expected.matches(Regex("[a-fA-F0-9]{64}")))
                val actual = digest.digest().joinToString("") { "%02x".format(it) }
                check(actual.equals(expected, ignoreCase = true))
            }
            check(partial.renameTo(target))
            target
        } catch (e: Exception) {
            partial.delete()
            target.delete()
            throw e
        }
    }
}

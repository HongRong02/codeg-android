package app.codeg.android.core.update

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.HttpUrl.Companion.toHttpUrl
import kotlinx.serialization.json.*

data class AppUpdate(
    val versionName: String,
    val versionCode: Int,
    val notes: String,
    val apkUrl: String,
    val sha256Url: String?,
    val size: Long,
    val sha256: String? = null,
)

/** Dedicated anonymous client; never inherits codeg server credentials. */
class UpdateChecker(private val client: HttpClient = HttpClient(OkHttp) {
    expectSuccess = true
    install(HttpTimeout) { requestTimeoutMillis = 10_000L }
}) : java.io.Closeable {
    override fun close() = client.close()
    suspend fun check(currentVersion: Int, server: String? = null, manual: Boolean = false): AppUpdate? = try {
        val serverResult = server?.let { origin ->
            withTimeoutOrNull(10_000L) {
                try {
                    val response = client.get("${origin.trimEnd('/')}/api/app_release/android/latest")
                    check(response.status.value in 200..299) { "Server HTTP ${response.status.value}" }
                    // A successful, current release is authoritative too.
                    ServerResult(parseServer(response.bodyAsText(), origin, currentVersion))
                } catch (e: CancellationException) {
                    throw e
                } catch (_: Exception) {
                    null
                }
            }
        }
        if (serverResult != null) serverResult.update else {
            val response = client.get("https://api.github.com/repos/HongRong02/codeg-android/releases?per_page=20")
            check(response.status.value in 200..299) { "GitHub HTTP ${response.status.value}" }
            parse(response.bodyAsText(), currentVersion)
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        if (manual) throw e else null
    }

    private data class ServerResult(val update: AppUpdate?)

    companion object {
        fun parseServer(body: String, server: String, currentVersion: Int): AppUpdate? {
            val item = Json.parseToJsonElement(body).jsonObject
            val code = item.getValue("version_code").jsonPrimitive.int
            val base = server.toHttpUrl()
            val path = item.getValue("apk_url").jsonPrimitive.content
            require(path.startsWith("/") && !path.startsWith("//")) { "Invalid APK path" }
            val url = requireNotNull(base.resolve(path))
            require(url.scheme == base.scheme && url.host == base.host && url.port == base.port)
            val checksum = item.getValue("sha256").jsonPrimitive.content
            require(checksum.matches(Regex("[a-fA-F0-9]{64}"))) { "Invalid SHA-256" }
            val name = item.getValue("version_name").jsonPrimitive.content
            if (code <= currentVersion) return null
            return AppUpdate(name, code, item["notes"]?.jsonPrimitive?.contentOrNull.orEmpty(),
                url.toString(), null, item["size_bytes"]?.jsonPrimitive?.longOrNull ?: 0L, checksum)
        }

        fun versionCode(tag: String): Int? = Regex("build-(\\d+)").matchEntire(tag)?.groupValues?.get(1)?.toIntOrNull()

        fun parse(body: String, currentVersion: Int): AppUpdate? {
            val release = Json.parseToJsonElement(body).jsonArray.map { it.jsonObject }
                .filter { it["draft"]?.jsonPrimitive?.booleanOrNull != true && it["prerelease"]?.jsonPrimitive?.booleanOrNull != true }
                .mapNotNull { item -> versionCode(item["tag_name"]?.jsonPrimitive?.content.orEmpty())?.let { it to item } }
                .maxByOrNull { it.first } ?: return null
            val (code, item) = release
            if (code <= currentVersion) return null
            val assets = item["assets"]?.jsonArray?.map { it.jsonObject }.orEmpty()
            val apk = assets.firstOrNull { it["name"]?.jsonPrimitive?.content?.endsWith(".apk") == true } ?: return null
            val apkName = apk["name"]!!.jsonPrimitive.content
            val checksum = assets.firstOrNull { it["name"]?.jsonPrimitive?.content == "$apkName.sha256" }
            return AppUpdate("1.1.$code", code, item["body"]?.jsonPrimitive?.contentOrNull.orEmpty(),
                apk["browser_download_url"]!!.jsonPrimitive.content,
                checksum?.get("browser_download_url")?.jsonPrimitive?.content,
                apk["size"]?.jsonPrimitive?.longOrNull ?: 0L)
        }
    }
}

package app.codeg.android.core.update

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.security.MessageDigest

class UpdateCheckerTest {
    private val latest = """{"version_code":180,"version_name":"1.1.180","notes":"hello","apk_url":"/api/app_release/android/apk","sha256":"${"a".repeat(64)}","size_bytes":42}"""

    @Test fun parsesServerAndComparesVersions() {
        val update = UpdateChecker.parseServer(latest, "https://example.com", 179)!!
        assertEquals(180, update.versionCode)
        assertEquals("https://example.com/api/app_release/android/apk", update.apkUrl)
        assertEquals("a".repeat(64), update.sha256)
        assertNull(UpdateChecker.parseServer(latest, "https://example.com", 180))
        assertNull(UpdateChecker.parseServer(latest, "https://example.com", 181))
    }

    @Test fun serverSuccessDoesNotFallBackEvenWhenCurrent() = runTest {
        val urls = mutableListOf<String>()
        UpdateChecker(HttpClient(MockEngine { request ->
            urls += request.url.toString()
            respond(latest)
        })).use { assertNull(it.check(180, "https://example.com", true)) }
        assertEquals(listOf("https://example.com/api/app_release/android/latest"), urls)
    }

    @Test fun serverFailureFallsBackToGithub() = runTest {
        for (status in listOf(HttpStatusCode.NotFound, HttpStatusCode.InternalServerError)) {
            val urls = mutableListOf<String>()
            UpdateChecker(HttpClient(MockEngine { request ->
                urls += request.url.toString()
                if (urls.size == 1) respond("", status) else respond("[]")
            })).use { assertNull(it.check(180, "https://example.com", true)) }
            assertEquals(2, urls.size)
            assertTrue(urls.last().startsWith("https://api.github.com/"))
        }
    }

    @Test fun timeoutAndNetworkFailureFallBack() = runTest {
        for (timeout in listOf(false, true)) {
            var requests = 0
            UpdateChecker(HttpClient(MockEngine {
                requests++
                if (requests == 1) {
                    if (timeout) kotlinx.coroutines.delay(11_000) else throw java.io.IOException("offline")
                    respond(latest)
                } else respond("[]")
            })).use { assertNull(it.check(180, "https://example.com", true)) }
            assertEquals(2, requests)
        }
    }

    @Test fun rejectsMissingChecksumAndForeignApk() {
        for (body in listOf(latest.replace("a".repeat(64), ""),
            latest.replace("/api/app_release/android/apk", "//evil.example/app.apk"))) {
            try {
                UpdateChecker.parseServer(body, "https://example.com", 179)
                fail("Unsafe release accepted")
            } catch (_: IllegalArgumentException) { }
        }
    }

    @Test fun verifiesChecksumAndRejectsMismatch() {
        val digest = MessageDigest.getInstance("SHA-256").digest("abc".toByteArray())
        val hash = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"
        assertTrue(UpdateChecksum.matches(digest, hash.uppercase()))
        assertFalse(UpdateChecksum.matches(digest, "0".repeat(64)))
        assertFalse(UpdateChecksum.matches(digest, "invalid"))
    }

    @Test fun githubVersionComparison() {
        assertEquals(180, UpdateChecker.versionCode("build-180"))
        assertNull(UpdateChecker.versionCode("invalid"))
        val body = """[{"tag_name":"build-180","assets":[{"name":"app.apk","browser_download_url":"https://example.com/app.apk"}]}]"""
        assertNotNull(UpdateChecker.parse(body, 179))
        assertNull(UpdateChecker.parse(body, 180))
    }
}

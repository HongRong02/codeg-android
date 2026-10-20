package app.codeg.android.core.update

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class UpdateCheckerTest {
    @Test fun checksAnonymousReleaseEndpointAndHandlesFailures() = runBlocking {
        val engine = MockEngine { request ->
            assertEquals("api.github.com", request.url.host)
            assertEquals("20", request.url.parameters["per_page"])
            assertNull(request.headers["Authorization"])
            respond("[]", HttpStatusCode.OK)
        }
        HttpClient(engine) { expectSuccess = true }.use { client ->
            assertNull(UpdateChecker(client).check(2, true))
        }
        HttpClient(MockEngine { respond("unavailable", HttpStatusCode.ServiceUnavailable) }) { expectSuccess = true }.use { client ->
            assertNull(UpdateChecker(client).check(2))
            try {
                UpdateChecker(client).check(2, true)
                fail("Manual checks must report failures")
            } catch (_: io.ktor.client.plugins.ServerResponseException) { }
        }
    }

    @Test fun parsesOnlyBuildTags() {
        assertEquals(42, UpdateChecker.versionCode("build-42"))
        assertNull(UpdateChecker.versionCode("v1.2.3"))
        assertNull(UpdateChecker.versionCode("build-999999999999"))
        assertNull(UpdateChecker.versionCode("build-42-extra"))
    }

    @Test fun selectsHighestStableBuildWithApk() {
        val releases = """[
          {"tag_name":"build-9","assets":[{"name":"app.apk","browser_download_url":"https://example.com/9.apk","size":12}]},
          {"tag_name":"build-12","prerelease":true,"assets":[]},
          {"tag_name":"build-10","name":"Codeg Android 1.1.10","body":"Changes","assets":[
            {"name":"app.apk","browser_download_url":"https://example.com/10.apk","size":30},
            {"name":"app.apk.sha256","browser_download_url":"https://example.com/10.sha256"}]},
          {"tag_name":"build-11","draft":true,"assets":[]}
        ]"""
        val update = UpdateChecker.parse(releases, 9)!!
        assertEquals(10, update.versionCode)
        assertEquals("1.1.10", update.versionName)
        assertEquals("Changes", update.notes)
        assertEquals(30L, update.size)
        assertNotNull(update.sha256Url)
        assertNull(UpdateChecker.parse(releases, 10))
    }

    @Test fun highestReleaseWithoutApkDoesNotOfferOlderBuild() {
        assertNull(UpdateChecker.parse("""[{"tag_name":"build-20","assets":[]}]""", 2))
    }
}

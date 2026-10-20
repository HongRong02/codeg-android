package app.codeg.android.feature.projects.forge

import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.*
import kotlinx.serialization.json.*
import org.junit.Assert.*
import org.junit.Test

class ForgeTest {
    @Test fun sourceKeyMatchesServerNormalization() {
        val remote = ForgeRemote("GitHub.com", "Acme/App", "github", true)
        assertEquals("github:github.com:acme/app:issue:42", remote.sourceKey(ForgeIssueRow(42, "Fix")))
        assertEquals("github:github.com:acme/app:pr:9", remote.sourceKey(ForgeIssueRow(9, "PR", is_pr = true)))
    }
    @Test fun responseUsesSnakeCaseAndPreservesPaging() {
        val json = Json { ignoreUnknownKeys = true }
        val page = json.decodeFromString<ForgeIssueList>("""{"rows":[{"number":7,"title":"PR","is_pr":true,"updated_at":"today"}],"has_next":true,"total_count":21}""")
        assertTrue(page.has_next)
        assertTrue(page.rows.single().is_pr)
        assertEquals("today", page.rows.single().updated_at)
        assertEquals(21L, page.total_count)
    }
    @Test fun requestsKeepNestedCamelCaseAndTaskSnakeCase() {
        val request = forgeRequest(5, "query", buildJsonObject { put("perPage", 20) })
        assertEquals(5, request["folderId"]!!.jsonPrimitive.int)
        assertEquals(20, request["query"]!!.jsonObject["perPage"]!!.jsonPrimitive.int)
        val draft = forgeTaskDraft(5, ForgeRemote("github.com", "a/b", "github", true), ForgeIssueRow(1, "Fix"))
        assertEquals(5, draft["folder_id"]!!.jsonPrimitive.int)
        assertEquals("issue", draft["source"]!!.jsonObject["kind"]!!.jsonPrimitive.content)
        assertEquals("Fix", draft["snapshot"]!!.jsonObject["title"]!!.jsonPrimitive.content)
    }
}

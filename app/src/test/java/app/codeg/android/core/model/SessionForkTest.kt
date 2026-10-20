package app.codeg.android.core.model

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SessionForkTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test fun preflightShowsWarningsAndFailuresButOmitsPassingChecks() {
        val result = json.decodeFromString<SessionPreflightResult>("""
            {"agent_type":"claude_code","agent_name":"Claude","passed":true,"checks":[
              {"check_id":"node","status":"pass","message":"Ready","fixes":[]},
              {"status":"warn","message":"Upgrade recommended"},
              {"status":"fail","message":"Missing CLI"}
            ],"adapter":null}
        """.trimIndent())
        assertEquals("Upgrade recommended\nMissing CLI", result.issueMessage())
    }

    @Test fun successfulPreflightHasNoBanner() {
        assertNull(SessionPreflightResult(true, listOf(SessionPreflightCheck("pass", "Ready"))).issueMessage())
    }

    @Test fun forkResponseDistinguishesNewSessionFromOriginalSibling() {
        val result = json.decodeFromString<SessionForkResult>("""
            {"forkedSessionId":"new","originalSessionId":"old","siblingConversationId":42}
        """.trimIndent())
        assertEquals("new", result.forkedSessionId)
        assertEquals("old", result.originalSessionId)
        assertEquals(42, result.siblingConversationId)
    }
}

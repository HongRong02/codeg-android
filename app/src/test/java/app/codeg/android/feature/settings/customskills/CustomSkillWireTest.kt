package app.codeg.android.feature.settings.customskills

import app.codeg.android.core.model.AgentType
import app.codeg.android.core.model.ExpertInstallStatus
import app.codeg.android.core.model.CustomSkillResult
import app.codeg.android.core.model.wire.*
import app.codeg.android.core.network.CodegJson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.*
import org.junit.Test

class CustomSkillWireTest {
    private val camel = Json { ignoreUnknownKeys = true }

    @Test fun `link requests use the shared expert ID and camel case agent key`() {
        val agent = AgentType.fromWire("codex")
        val encoded = CodegJson.request.encodeToString(CustomSkillLinksBody(listOf(CustomSkillLinkOp("my-skill", agent, true))))
        val op = Json.parseToJsonElement(encoded).jsonObject.getValue("ops").jsonArray.single().jsonObject
        assertEquals("my-skill", op.getValue("expertId").jsonPrimitive.content)
        assertEquals("codex", op.getValue("agentType").jsonPrimitive.content)
        assertEquals("true", op.getValue("enable").jsonPrimitive.content)
        assertFalse(op.containsKey("expert_id"))
    }

    @Test fun `duplicate request preserves source and new ID`() {
        val encoded = CodegJson.request.encodeToString(CustomSkillDuplicateBody("source", "copy"))
        val body = Json.parseToJsonElement(encoded).jsonObject
        assertEquals("source", body.getValue("sourceId").jsonPrimitive.content)
        assertEquals("copy", body.getValue("newId").jsonPrimitive.content)
    }

    @Test fun `camel case statuses preserve the owning skill and agent`() {
        val status = camel.decodeFromString<ExpertInstallStatus>("""{"expertId":"my-skill","agentType":"codex","state":"linked_to_codeg","copyMode":true}""")
        assertEquals("my-skill", status.expertId)
        assertTrue(status.state.isLinked)
        assertTrue(status.copyMode)
    }

    @Test fun `partial import results distinguish skipped and failed entries`() {
        val results = camel.decodeFromString<List<CustomSkillResult>>("""[{"id":"a","ok":true},{"id":"b","ok":false,"skipped":true},{"id":"c","ok":false,"error":"denied"}]""")
        assertEquals(listOf("c"), results.filter { !it.ok && !it.skipped }.map { it.id })
    }
}

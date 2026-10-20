package app.codeg.android.core.model

import org.junit.Assert.*
import org.junit.Test

class AssistantTest {
    @Test fun monogramKeepsSupplementaryCharacters() {
        assertEquals("🤖", assistantMonogram(" 🤖 Helper"))
        assertEquals("A", assistantMonogram("alice"))
        assertEquals("", assistantMonogram(" "))
    }

    @Test fun reorderMovesOnlyTheRequestedNeighbor() {
        assertEquals(listOf(2, 1, 3), reorderedAssistantIds(listOf(1, 2, 3), 2, -1))
        assertEquals(listOf(1, 3, 2), reorderedAssistantIds(listOf(1, 2, 3), 2, 1))
        assertEquals(listOf(1, 2, 3), reorderedAssistantIds(listOf(1, 2, 3), 1, -1))
    }

    @Test fun modesAndBooleanOptionsMustBeExplicit() {
        val snapshot = AgentOptionsSnapshot(
            modes = SessionModeState(availableModes = listOf(SessionModeInfo("plan", "Plan"))),
            configOptions = listOf(SessionConfigOption("allow", "Allow", kind = SessionConfigKind(type = "boolean"))),
        )
        assertNull(assistantConfig(AgentType.CODEX, "Codex", snapshot, null, mapOf("allow" to "true")))
        assertNull(assistantConfig(AgentType.CODEX, "Codex", snapshot, "plan", mapOf("allow" to "yes")))
        val config = assistantConfig(AgentType.CODEX, "Codex", snapshot, "plan", mapOf("allow" to "false", "stale" to "value"))!!
        assertEquals("Plan", config.labelSnapshot.mode)
        assertEquals(mapOf("allow" to "false"), config.agentOptions.configValues)
    }

    @Test fun requestsKeepSnakeCaseInsideCamelCaseEnvelopes() {
        val draft = app.codeg.android.core.model.wire.AssistantDraft(
            "Helper", "", AssistantAvatar(),
            AssistantAgentConfig(AgentType.CODEX, AssistantOptions(), AssistantLabels("Codex")), "", true,
        )
        val json = app.codeg.android.core.network.CodegJson.request
        val body = json.parseToJsonElement(json.encodeToString(
            app.codeg.android.core.model.wire.AssistantCreateRequest.serializer(),
            app.codeg.android.core.model.wire.AssistantCreateRequest(draft),
        )) as kotlinx.serialization.json.JsonObject
        val nested = body["draft"] as kotlinx.serialization.json.JsonObject
        val config = nested["agent_config"] as kotlinx.serialization.json.JsonObject
        assertEquals(kotlinx.serialization.json.JsonPrimitive("codex"), config["agent_type"])
        assertNotNull(config["label_snapshot"])
        assertFalse(config.containsKey("agentType"))
    }

    @Test fun configRequiresEveryAdvertisedOption() {
        val snapshot = AgentOptionsSnapshot(configOptions = listOf(
            SessionConfigOption("effort", "Effort", kind = SessionConfigKind(options = listOf(SessionConfigSelectOption("high", "High"))))
        ))
        assertNull(assistantConfig(AgentType.CLAUDE_CODE, "Claude", snapshot, null, emptyMap()))
        val config = assistantConfig(AgentType.CLAUDE_CODE, "Claude", snapshot, null, mapOf("effort" to "high"))!!
        assertEquals("High", config.labelSnapshot.options.single().valueLabel)
        assertEquals(mapOf("effort" to "high"), config.agentOptions.configValues)
        assertNull(assistantConfig(AgentType.CLAUDE_CODE, "Claude", snapshot, null, mapOf("effort" to "invalid")))
    }
}

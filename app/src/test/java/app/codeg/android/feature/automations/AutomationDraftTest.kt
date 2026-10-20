package app.codeg.android.feature.automations

import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import org.junit.Assert.*
import org.junit.Test

class AutomationDraftTest {
    @Test fun preservesConfigurationAndEscapesPrompt() {
        val original = Automation(1, "old", false, TriggerKind.MANUAL, timezone = "UTC",
            agentType = AgentType.CODEX, rootFolderId = 2, isolation = IsolationMode.SHARED_IN_ROOT,
            branch = "topic", config = buildJsonObject { put("mode_id", "plan"); put("action", "start_session") })
        val draft = automationDraft(original, " renamed ", 3, AgentType.CODEX, "say \"hi\"\nnext", "0 * * * *", "UTC", true)
        assertEquals("renamed", draft.name)
        assertFalse(draft.enabled)
        assertEquals("topic", draft.branch)
        assertEquals("plan", draft.config["mode_id"]?.jsonPrimitive?.content)
        assertEquals("say \"hi\"\nnext", draft.config["display_text"]?.jsonPrimitive?.content)
        val body = Json.parseToJsonElement(Json.encodeToString(CreateAutomationBody(draft))).jsonObject
        assertEquals("schedule", body["draft"]!!.jsonObject["trigger_kind"]?.jsonPrimitive?.content)
        assertEquals(3, body["draft"]!!.jsonObject["root_folder_id"]?.jsonPrimitive?.int)
    }

    @Test fun manualDraftClearsCronAndAgentChangeClearsOptions() {
        val original = Automation(1, "old", true, TriggerKind.SCHEDULE, timezone = "UTC",
            agentType = AgentType.CODEX, isolation = IsolationMode.WORKTREE_PER_RUN,
            config = buildJsonObject { put("mode_id", "plan"); put("action", "enqueue_task") })
        val draft = automationDraft(original, "name", 2, AgentType.CLAUDE_CODE, "prompt", "bad", "UTC", false)
        assertNull(draft.cron)
        assertEquals(TriggerKind.MANUAL, draft.triggerKind)
        assertFalse(draft.config.containsKey("mode_id"))
        assertEquals("enqueue_task", draft.config["action"]?.jsonPrimitive?.content)
    }
}

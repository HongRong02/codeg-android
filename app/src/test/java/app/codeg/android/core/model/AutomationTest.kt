package app.codeg.android.core.model

import app.codeg.android.core.model.wire.GetAutomationBody
import app.codeg.android.core.model.wire.ListRunsBody
import app.codeg.android.core.model.wire.SetEnabledBody
import app.codeg.android.core.network.CodegJson
import app.codeg.android.feature.automations.AutomationFilter
import app.codeg.android.feature.automations.AutomationUiState
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant

class AutomationTest {

    @Test
    fun automation_request_bodies_encode_camelCase() {
        val getBody = CodegJson.request.encodeToString(GetAutomationBody(id = 42))
        assertEquals("{\"id\":42}", getBody)

        val listRunsBody = CodegJson.request.encodeToString(ListRunsBody(automationId = 10, limit = 50))
        assertTrue(listRunsBody.contains("\"automationId\":10"))
        assertTrue(listRunsBody.contains("\"limit\":50"))

        val setEnabledBody = CodegJson.request.encodeToString(SetEnabledBody(id = 5, enabled = true))
        assertTrue(setEnabledBody.contains("\"id\":5"))
        assertTrue(setEnabledBody.contains("\"enabled\":true"))
    }

    @Test
    fun automation_model_decodes_snake_case_fields_correctly() {
        val json = """
        {
            "id": 1,
            "name": "Nightly Build & Audit",
            "enabled": true,
            "trigger_kind": "schedule",
            "cron": "0 2 * * *",
            "timezone": "UTC",
            "next_run_at": "2026-10-21T02:00:00Z",
            "agent_type": "claude_code",
            "root_folder_id": 100,
            "isolation": "worktree_per_run",
            "branch": "main",
            "is_remote_branch": false,
            "config": {
                "display_text": "Run full test suite and audit dependencies"
            },
            "last_run_at": "2026-10-20T02:00:00Z",
            "last_run_status": "succeeded",
            "last_run_conversation_id": 1234,
            "unseen_failures": 0,
            "created_at": "2026-10-01T00:00:00Z",
            "updated_at": "2026-10-20T02:05:00Z"
        }
        """.trimIndent()

        val automation = CodegJson.response.decodeFromString<Automation>(json)
        assertEquals(1, automation.id)
        assertEquals("Nightly Build & Audit", automation.name)
        assertTrue(automation.enabled)
        assertEquals(TriggerKind.SCHEDULE, automation.triggerKind)
        assertEquals("0 2 * * *", automation.cron)
        assertEquals(AgentType.CLAUDE_CODE, automation.agentType)
        assertEquals(IsolationMode.WORKTREE_PER_RUN, automation.isolation)
        assertEquals("Run full test suite and audit dependencies", automation.promptText)
        assertEquals(Instant.parse("2026-10-21T02:00:00Z"), automation.nextRunAt)
        assertEquals("succeeded", automation.lastRunStatus)
    }

    @Test
    fun automation_run_model_decodes_statuses_and_dates() {
        val json = """
        {
            "id": 99,
            "automation_id": 1,
            "status": "succeeded",
            "trigger": "schedule",
            "scheduled_for": "2026-10-20T02:00:00Z",
            "started_at": "2026-10-20T02:00:05Z",
            "ended_at": "2026-10-20T02:05:30Z",
            "conversation_id": 1234,
            "worktree_folder_id": 105,
            "stop_reason": "completed",
            "summary": "All tests passed",
            "created_at": "2026-10-20T02:00:00Z"
        }
        """.trimIndent()

        val run = CodegJson.response.decodeFromString<AutomationRun>(json)
        assertEquals(99, run.id)
        assertEquals(1, run.automationId)
        assertEquals(AutomationRunStatus.SUCCEEDED, run.status)
        assertEquals("schedule", run.trigger)
        assertEquals(Instant.parse("2026-10-20T02:00:05Z"), run.startedAt)
        assertEquals("All tests passed", run.summary)
        assertNull(run.error)
    }

    @Test
    fun automation_UI_state_filters_correctly() {
        val a1 = Automation(
            id = 1,
            name = "Enabled One",
            enabled = true,
            triggerKind = TriggerKind.MANUAL,
            timezone = "UTC",
            agentType = AgentType.CODEX,
            isolation = IsolationMode.SHARED_IN_ROOT,
        )
        val a2 = Automation(
            id = 2,
            name = "Disabled Two",
            enabled = false,
            triggerKind = TriggerKind.SCHEDULE,
            cron = "*/15 * * * *",
            timezone = "UTC",
            agentType = AgentType.GEMINI,
            isolation = IsolationMode.WORKTREE_PER_RUN,
        )

        val state = AutomationUiState(automations = listOf(a1, a2))

        assertEquals(2, state.filteredAutomations.size)
        assertEquals(listOf(a1), state.copy(filter = AutomationFilter.ENABLED).filteredAutomations)
        assertEquals(listOf(a2), state.copy(filter = AutomationFilter.DISABLED).filteredAutomations)

        assertEquals(a1, state.copy(selectedAutomationId = 1).selectedAutomation)
        assertNull(state.copy(selectedAutomationId = 3).selectedAutomation)
    }
}

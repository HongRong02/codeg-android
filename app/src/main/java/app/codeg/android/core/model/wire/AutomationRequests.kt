package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable

@Serializable
data class GetAutomationBody(
    val id: Int,
)

@Serializable
data class ListRunsBody(
    val automationId: Int,
    val limit: Long = 100,
)

@Serializable
data class SetEnabledBody(
    val id: Int,
    val enabled: Boolean,
)

@Serializable
data class AutomationDraft(
    val name: String,
    val enabled: Boolean = true,
    @kotlinx.serialization.SerialName("trigger_kind") val triggerKind: app.codeg.android.core.model.TriggerKind,
    val cron: String? = null,
    val timezone: String,
    @kotlinx.serialization.SerialName("agent_type") val agentType: app.codeg.android.core.model.AgentType,
    @kotlinx.serialization.SerialName("root_folder_id") val rootFolderId: Int,
    val isolation: app.codeg.android.core.model.IsolationMode,
    val branch: String? = null,
    @kotlinx.serialization.SerialName("is_remote_branch") val isRemoteBranch: Boolean = false,
    val config: kotlinx.serialization.json.JsonObject,
)
@Serializable data class CreateAutomationBody(val draft: AutomationDraft)
@Serializable data class UpdateAutomationBody(val id: Int, val draft: AutomationDraft)
@Serializable data class ComputeAutomationBody(val cron: String, val timezone: String)
@Serializable data class RunAutomationBody(val automationId: Int)
@Serializable data class CancelAutomationBody(val runId: Int)

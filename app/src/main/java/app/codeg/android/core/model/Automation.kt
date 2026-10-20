package app.codeg.android.core.model

import app.codeg.android.core.model.wire.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import java.time.Instant

@Serializable
enum class TriggerKind {
    @SerialName("schedule")
    SCHEDULE,
    @SerialName("manual")
    MANUAL,
}

@Serializable
enum class IsolationMode {
    @SerialName("worktree_per_run")
    WORKTREE_PER_RUN,
    @SerialName("shared_in_root")
    SHARED_IN_ROOT,
}

@Serializable
enum class AutomationRunStatus {
    @SerialName("running")
    RUNNING,
    @SerialName("succeeded")
    SUCCEEDED,
    @SerialName("failed")
    FAILED,
    @SerialName("cancelled")
    CANCELLED,
    @SerialName("skipped")
    SKIPPED,
}

@Serializable
data class Automation(
    val id: Int,
    val name: String,
    val enabled: Boolean,
    @SerialName("trigger_kind")
    val triggerKind: TriggerKind,
    val cron: String? = null,
    val timezone: String,
    @Serializable(with = InstantSerializer::class)
    @SerialName("next_run_at")
    val nextRunAt: Instant? = null,
    @SerialName("agent_type")
    val agentType: AgentType,
    @SerialName("root_folder_id")
    val rootFolderId: Int? = null,
    val isolation: IsolationMode,
    val branch: String? = null,
    @SerialName("is_remote_branch")
    val isRemoteBranch: Boolean = false,
    val config: JsonObject? = null,
    @Serializable(with = InstantSerializer::class)
    @SerialName("last_run_at")
    val lastRunAt: Instant? = null,
    @SerialName("last_run_status")
    val lastRunStatus: String? = null,
    @SerialName("last_run_conversation_id")
    val lastRunConversationId: Int? = null,
    @SerialName("unseen_failures")
    val unseenFailures: Int = 0,
    @Serializable(with = InstantSerializer::class)
    @SerialName("created_at")
    val createdAt: Instant? = null,
    @Serializable(with = InstantSerializer::class)
    @SerialName("updated_at")
    val updatedAt: Instant? = null,
) {
    val promptText: String
        get() = config?.get("display_text")?.toString()?.trim('"') ?: ""
}

@Serializable
data class AutomationRun(
    val id: Int,
    @SerialName("automation_id")
    val automationId: Int,
    val status: AutomationRunStatus,
    val trigger: String,
    @Serializable(with = InstantSerializer::class)
    @SerialName("scheduled_for")
    val scheduledFor: Instant? = null,
    @Serializable(with = InstantSerializer::class)
    @SerialName("started_at")
    val startedAt: Instant? = null,
    @Serializable(with = InstantSerializer::class)
    @SerialName("ended_at")
    val endedAt: Instant? = null,
    @SerialName("conversation_id")
    val conversationId: Int? = null,
    @SerialName("worktree_folder_id")
    val worktreeFolderId: Int? = null,
    @SerialName("stop_reason")
    val stopReason: String? = null,
    val error: String? = null,
    val summary: String? = null,
    @Serializable(with = InstantSerializer::class)
    @SerialName("created_at")
    val createdAt: Instant? = null,
)

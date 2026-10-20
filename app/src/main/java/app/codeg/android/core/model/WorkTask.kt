package app.codeg.android.core.model

import app.codeg.android.core.network.ApiError
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WorkTask(
    val id: Int,
    val title: String,
    val status: String,
    val agentType: String? = null,
    val updatedAt: String,
    val resultSummary: String? = null,
    val lastError: String? = null,
    val failureReason: String? = null,
    val workBranch: String? = null,
    val folderId: Int = 0,
    val config: kotlinx.serialization.json.JsonObject = kotlinx.serialization.json.JsonObject(emptyMap()),
    val deferredAt: String? = null,
    val archivedAt: String? = null,
    val scheduledAt: String? = null,
    val mergeQueued: JsonElement? = null,
    val worktreeFolderId: Int? = null,
    val worktreeMissing: Boolean = false,
    val filesChanged: Int? = null,
    val sourceKind: String? = null,
    val cleanupState: String? = null,
)

@Serializable
data class WorkTaskEvent(
    val id: Int,
    val taskId: Int,
    val kind: String,
    val actor: String,
    val payload: JsonElement? = null,
    val createdAt: String,
)

@Serializable
data class WorkTaskChangedFile(val file: String, val additions: Int, val deletions: Int)

fun Throwable.isWorkTasksUnsupported(): Boolean = this is ApiError.Server &&
    (status == 404 || code in setOf("unknown_command", "unknown_method", "not_implemented") ||
        serverMessage.contains("unknown command", ignoreCase = true) ||
        serverMessage.contains("unknown endpoint", ignoreCase = true))

@Serializable
data class WorkTaskTemplate(val id: Int, val name: String, val title: String,
    val config: kotlinx.serialization.json.JsonObject)

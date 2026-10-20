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

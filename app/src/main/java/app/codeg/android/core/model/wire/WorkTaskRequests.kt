package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable

@Serializable
data class WorkTaskListBody(val folderId: Int? = null)
@Serializable
data class WorkTaskIdBody(val id: Int)
@Serializable
data class WorkTaskEventsBody(val taskId: Int, val limit: Long = 500)
@Serializable
data class WorkTaskDiffBody(val id: Int, val file: String? = null)

@Serializable
data class WorkTaskDraft(
    @kotlinx.serialization.SerialName("folder_id") val folderId: Int,
    val title: String,
    val config: kotlinx.serialization.json.JsonObject,
)
@Serializable
data class WorkTaskSaveBody(val draft: WorkTaskDraft, val id: Int? = null)
@Serializable
data class WorkTaskReorderBody(val folderId: Int, val orderedIds: List<Int>)
@Serializable
data class WorkTaskActionBody(
    val id: Int,
    val deleteWorktree: Boolean,
    val feedback: String? = null,
    val scheduledAt: String? = null,
    val archived: Boolean? = null,
    val deferred: Boolean? = null,
)

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

package app.codeg.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ConversationTag(
    val id: Int,
    val name: String,
    val color: String,
    val folderId: Int? = null,
    val sortOrder: Int = 0,
)

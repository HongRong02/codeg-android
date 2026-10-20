package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable

@Serializable
data class CreateConversationTagRequest(val name: String, val color: String, val folderId: Int? = null)

@Serializable
data class UpdateConversationTagRequest(val tagId: Int, val name: String, val color: String)

@Serializable
data class ConversationTagIdRequest(val tagId: Int)

@Serializable
data class UpdateConversationTagsRequest(val conversationId: Int, val add: List<Int>, val remove: List<Int>)

package app.codeg.android.core.model.wire

import app.codeg.android.core.model.AgentType
import kotlinx.serialization.Serializable

@Serializable
data class SessionForkRequest(
    val connectionId: String,
    val conversationId: Int?,
    val folderId: Int?,
)

@Serializable
data class SessionDisconnectRequest(val connectionId: String)

@Serializable
data class SessionPreflightRequest(val agentType: AgentType, val forceRefresh: Boolean = false)

package app.codeg.android.core.model.wire

import app.codeg.android.core.model.AssistantAgentConfig
import app.codeg.android.core.model.AssistantAvatar
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssistantDraft(
    val name: String,
    val description: String,
    val avatar: AssistantAvatar,
    @SerialName("agent_config") val agentConfig: AssistantAgentConfig? = null,
    val instructions: String,
    val enabled: Boolean,
)

@Serializable
data class AssistantCreateRequest(val draft: AssistantDraft)
@Serializable
data class AssistantUpdateRequest(val id: Int, val patch: AssistantDraft)
@Serializable
data class AssistantIdRequest(val id: Int)
@Serializable
data class AssistantReorderRequest(val ids: List<Int>)

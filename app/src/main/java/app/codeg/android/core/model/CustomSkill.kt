package app.codeg.android.core.model

import kotlinx.serialization.Serializable

/** Custom skill responses use camelCase, like the shared expert link API. */
@Serializable
data class CustomSkill(val id: String, val name: String = "", val description: String? = null)

@Serializable
data class CustomSkillResult(
    val id: String = "",
    val ok: Boolean = false,
    val skipped: Boolean = false,
    val error: String? = null,
)

@Serializable
data class CustomLinkResult(
    val expertId: String = "",
    val agentType: AgentType,
    val ok: Boolean = false,
    val error: String? = null,
)

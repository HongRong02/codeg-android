package app.codeg.android.core.model.wire

import app.codeg.android.core.model.AgentType
import kotlinx.serialization.Serializable

@Serializable
data class CustomSkillIdBody(val id: String)
@Serializable
data class CustomSkillContentBody(val id: String, val content: String)
@Serializable
data class CustomSkillDuplicateBody(val sourceId: String, val newId: String)
@Serializable
data class CustomSkillDeleteBody(val ids: List<String>)
@Serializable
data class CustomSkillImportBody(val agentType: AgentType, val ids: List<String>)
@Serializable
data class CustomSkillLinkOp(val expertId: String, val agentType: AgentType, val enable: Boolean)
@Serializable
data class CustomSkillLinksBody(val ops: List<CustomSkillLinkOp>)

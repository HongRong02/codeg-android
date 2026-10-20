package app.codeg.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionForkResult(
    val forkedSessionId: String,
    val originalSessionId: String,
    val siblingConversationId: Int,
)

@Serializable
data class SessionPreflightResult(
    val passed: Boolean,
    val checks: List<SessionPreflightCheck> = emptyList(),
) {
    fun issueMessage(): String? = checks.filter { it.status == "warn" || it.status == "fail" }
        .joinToString("\n") { it.message }.takeIf { it.isNotBlank() }
}

@Serializable
data class SessionPreflightCheck(val status: String, val message: String)

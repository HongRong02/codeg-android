package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable

@Serializable
data class GetAutomationBody(
    val id: Int,
)

@Serializable
data class ListRunsBody(
    val automationId: Int,
    val limit: Long = 100,
)

@Serializable
data class SetEnabledBody(
    val id: Int,
    val enabled: Boolean,
)

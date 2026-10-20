package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable

@Serializable
data class RecentServerLogsBody(val limit: Int = 500, val minLevel: String? = null)

@Serializable
data class ReadServerLogBody(val name: String, val maxBytes: Int = 262144)

@Serializable
data class ServerTokenReportBody(val filter: ServerTokenFilter)

@Serializable
data class ServerTokenFilter(
    val start: String? = null,
    val end: String,
    val bucket: String = "day",
    val tzOffsetMinutes: Int,
)

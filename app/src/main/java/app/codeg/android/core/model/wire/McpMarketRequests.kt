package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class McpMarketSearchBody(val providerId: String, val query: String, val limit: Int)
@Serializable
data class McpMarketDetailBody(val providerId: String, val serverId: String)
@Serializable
data class McpMarketInstallBody(
    val providerId: String, val serverId: String, val apps: List<String>,
    val optionId: String, val protocol: String, val parameterValues: JsonObject,
)

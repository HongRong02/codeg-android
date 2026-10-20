package app.codeg.android.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class McpMarketProvider(val id: String, val name: String, val description: String = "")

@Serializable
data class McpMarketItem(val provider_id: String, val server_id: String, val name: String, val description: String = "")

@Serializable
data class McpMarketParameter(
    val key: String, val label: String, val description: String? = null,
    val required: Boolean = false, val secret: Boolean = false, val kind: String = "string",
    val default_value: JsonElement? = null, val placeholder: String? = null,
    val enum_values: List<String> = emptyList(), val location: String? = null,
)

@Serializable
data class McpMarketOption(
    val id: String, val protocol: String, val label: String,
    val parameters: List<McpMarketParameter> = emptyList(),
)

@Serializable
data class McpMarketDetail(
    val provider_id: String, val server_id: String, val name: String, val description: String = "",
    val default_option_id: String? = null, val install_options: List<McpMarketOption> = emptyList(),
)

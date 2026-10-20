package app.codeg.android.feature.settings.mcpmarket

import app.codeg.android.core.model.McpMarketParameter
import kotlinx.serialization.json.*

class MarketParameterException(val field: String) : IllegalArgumentException()

fun parseMarketParameters(fields: List<McpMarketParameter>, draft: Map<String, String>): JsonObject = buildJsonObject {
    fields.forEach { field ->
        val raw = draft[field.key].orEmpty()
        val text = raw.trim()
        if (text.isEmpty()) {
            val default = field.default_value?.takeUnless { it == JsonNull }
            if (default != null) put(field.key, default)
            else if (field.required) throw MarketParameterException(field.label)
            return@forEach
        }
        val value = try {
            if (field.enum_values.isNotEmpty() && text !in field.enum_values) throw MarketParameterException(field.label)
            when (field.kind) {
                "boolean" -> JsonPrimitive(text.toBooleanStrict())
                "integer" -> JsonPrimitive(text.toLong())
                "number" -> JsonPrimitive(text.toDouble().also { require(it.isFinite()) })
                "json" -> Json.parseToJsonElement(text)
                else -> JsonPrimitive(raw)
            }
        } catch (_: Exception) { throw MarketParameterException(field.label) }
        put(field.key, value)
    }
}

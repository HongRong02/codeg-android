package app.codeg.android.feature.settings.mcpmarket

import app.codeg.android.core.model.McpMarketParameter
import kotlinx.serialization.json.*
import org.junit.Assert.*
import org.junit.Test

class McpMarketParametersTest {
    @Test fun preservesTypesAndSecrets() {
        val fields = listOf(
            McpMarketParameter("token", "Token", secret = true),
            McpMarketParameter("count", "Count", kind = "integer"),
            McpMarketParameter("enabled", "Enabled", kind = "boolean"),
            McpMarketParameter("config", "Config", kind = "json"),
        )
        val result = parseMarketParameters(fields, mapOf("token" to " secret ", "count" to "3", "enabled" to "false", "config" to "{\"a\":1}"))
        assertEquals(" secret ", result["token"]!!.jsonPrimitive.content)
        assertEquals(3, result["count"]!!.jsonPrimitive.int)
        assertEquals(false, result["enabled"]!!.jsonPrimitive.boolean)
        assertEquals(1, result["config"]!!.jsonObject["a"]!!.jsonPrimitive.int)
    }
    @Test fun rejectsInvalidRequiredAndTypedValues() {
        for ((kind, raw) in listOf("integer" to "1.5", "number" to "NaN", "boolean" to "yes", "json" to "{")) {
            assertTrue(runCatching { parseMarketParameters(listOf(McpMarketParameter("x", "X", kind = kind)), mapOf("x" to raw)) }.isFailure)
        }
        assertTrue(runCatching { parseMarketParameters(listOf(McpMarketParameter("x", "X", required = true)), emptyMap()) }.isFailure)
        assertTrue(runCatching { parseMarketParameters(listOf(McpMarketParameter("x", "X", enum_values = listOf("a"))), mapOf("x" to "b")) }.isFailure)
    }
    @Test fun usesDefaultsAndOmitsBlankOptionalValues() {
        val result = parseMarketParameters(listOf(McpMarketParameter("x", "X", required = true, default_value = JsonPrimitive(5)), McpMarketParameter("y", "Y")), emptyMap())
        assertEquals(JsonPrimitive(5), result["x"])
        assertFalse(result.containsKey("y"))
    }
}

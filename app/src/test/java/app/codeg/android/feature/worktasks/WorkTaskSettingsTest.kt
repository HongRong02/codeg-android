package app.codeg.android.feature.worktasks

import kotlinx.serialization.json.*
import org.junit.Assert.*
import org.junit.Test

class WorkTaskSettingsTest {
    @Test fun validatesNumbersAndOptionalValues() {
        assertNull(settingsValue("max_concurrent", "-1"))
        assertNull(settingsValue("max_concurrent", "2147483648"))
        assertNull(settingsValue("auto_compact_percent", "101"))
        assertEquals(JsonPrimitive(0), settingsValue("max_concurrent", "0"))
        assertEquals(JsonNull, settingsValue("preflight_command_id", ""))
        assertEquals(JsonNull, settingsValue("mode_id", ""))
    }

    @Test fun acceptsOnlyStringMapsAndValidJson() {
        assertNull(settingsValue("config_values", "{bad"))
        assertNull(settingsValue("stage_prompts", "{\"work\":12}"))
        assertEquals(JsonObject(mapOf("work" to JsonPrimitive("review"))),
            settingsValue("stage_prompts", "{\"work\":\"review\"}"))
        assertNull(settingsValue("merge_strategy", "invalid"))
        assertEquals(JsonPrimitive("merge"), settingsValue("merge_strategy", "merge"))
    }
}

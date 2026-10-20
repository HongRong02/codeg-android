package app.codeg.android.feature.projects.importsessions

import app.codeg.android.feature.worktasks.formatWorkTaskTime
import java.util.TimeZone
import org.junit.Assert.assertEquals
import org.junit.Test

class ImportSessionTimeTest {
    @Test fun formatsTimestampInDeviceZone() {
        val previous = TimeZone.getDefault()
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"))
            assertEquals("2026-10-15 18:47", formatWorkTaskTime("2026-10-15T10:47:32.593Z"))
            assertEquals("invalid", formatWorkTaskTime("invalid"))
        } finally { TimeZone.setDefault(previous) }
    }
}

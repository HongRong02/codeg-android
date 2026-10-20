package app.codeg.android.feature.serverlogs

import app.codeg.android.core.network.ApiError
import app.codeg.android.core.network.CodegJson
import app.codeg.android.core.model.ServerLogRecord
import app.codeg.android.core.model.wire.ServerTokenReportBody
import kotlinx.serialization.encodeToString
import org.junit.Assert.*
import org.junit.Test
import java.time.Instant
import java.time.ZoneOffset

class ServerLogsTest {
    @Test fun rangeUsesExclusiveNowAndLocalCalendarDays() {
        val now = Instant.parse("2026-10-20T04:00:00Z")
        val filter = tokenFilter(7, now, ZoneOffset.ofHours(8))
        assertEquals("2026-10-13T16:00:00Z", filter.start)
        assertEquals(now.toString(), filter.end)
        assertEquals(480, filter.tzOffsetMinutes)
        val json = CodegJson.request.encodeToString(ServerTokenReportBody(filter))
        assertTrue(json.contains("\"tzOffsetMinutes\":480"))
        assertTrue(json.contains("\"filter\":"))
        assertNull(tokenFilter(null, now, ZoneOffset.UTC).start)
    }

    @Test fun searchIsCaseInsensitiveAndBlankPreservesContent() {
        val content = "INFO connected\nERROR Failed\n  detail"
        assertEquals(content, filterLogText(content, " "))
        assertEquals("ERROR Failed", filterLogText(content, "failed"))
        assertEquals("", filterLogText(content, "absent"))
    }

    @Test fun unsupportedDoesNotHideAuthOrTransportFailures() {
        assertTrue(isUnsupportedLogsApi(ApiError.Server(404, null, "Not Found")))
        assertTrue(isUnsupportedLogsApi(ApiError.Server(400, "unknown_command", "Unknown command")))
        assertFalse(isUnsupportedLogsApi(ApiError.Unauthorized))
        assertFalse(isUnsupportedLogsApi(ApiError.Transport("offline")))
        assertFalse(isUnsupportedLogsApi(ApiError.Server(500, null, "Database failure")))
    }

    @Test fun logRecordDecodesSnakeCaseAndKeepsDetails() {
        val record = CodegJson.response.decodeFromString(ServerLogRecord.serializer(),
            """{"seq":2,"timestamp_ms":0,"level":"ERROR","target":"test","message":"failed","fields":{"id":"42"},"spans":[{"name":"request","fields":{}}]}""")
        assertEquals(0L, record.timestampMs)
        assertTrue(record.line().contains("id=42"))
        assertTrue(record.line().contains("request"))
    }
}

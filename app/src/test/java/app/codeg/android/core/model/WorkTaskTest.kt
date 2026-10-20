package app.codeg.android.core.model

import app.codeg.android.core.network.CodegJson
import app.codeg.android.core.network.ApiError
import org.junit.Assert.*
import org.junit.Test

class WorkTaskTest {
    @Test fun decodesSnakeCaseAndFutureStatus() {
        val task = CodegJson.response.decodeFromString(WorkTask.serializer(),
            """{"id":7,"title":"Task","status":"future_status","agent_type":"codex","updated_at":"2026-10-20T00:00:00Z","extra":true}""")
        assertEquals("codex", task.agentType)
        assertEquals("future_status", task.status)
        assertEquals("2026-10-20T00:00:00Z", task.updatedAt)
    }

    @Test fun decodesEventsAndFileCounts() {
        val event = CodegJson.response.decodeFromString(WorkTaskEvent.serializer(),
            """{"id":1,"task_id":7,"kind":"started","actor":"system","payload":{"run_seq":2},"created_at":"now"}""")
        assertEquals(7, event.taskId)
        assertNotNull(event.payload)
        val file = CodegJson.response.decodeFromString(WorkTaskChangedFile.serializer(),
            """{"file":"a.kt","additions":3,"deletions":1}""")
        assertEquals(3, file.additions)
        assertEquals(1, file.deletions)
    }

    @Test fun requestUsesCamelCase() {
        val request = CodegJson.request.encodeToString(
            app.codeg.android.core.model.wire.WorkTaskEventsBody.serializer(),
            app.codeg.android.core.model.wire.WorkTaskEventsBody(7))
        assertTrue(request.contains("\"taskId\":7"))
        assertTrue(request.contains("\"limit\":500"))
        assertFalse(request.contains("task_id"))
    }

    @Test fun onlyMissingCommandsAreUnsupported() {
        assertTrue(ApiError.Server(404, null, "").isWorkTasksUnsupported())
        assertTrue(ApiError.Server(400, "unknown_command", "Unknown command").isWorkTasksUnsupported())
        assertFalse(ApiError.Server(500, null, "Database error").isWorkTasksUnsupported())
        assertFalse(ApiError.Unauthorized.isWorkTasksUnsupported())
        assertFalse(ApiError.Transport("Offline").isWorkTasksUnsupported())
    }
}

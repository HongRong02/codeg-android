package app.codeg.android.feature.worktasks

import app.codeg.android.core.model.WorkTask
import org.junit.Assert.*
import org.junit.Test
import kotlinx.serialization.json.*

class WorkTaskBoardTest {
    private fun task(status: String) = WorkTask(1, "Task", status, updatedAt = "2026-10-20T00:00:00Z")
    @Test fun groupsEveryStatusAndOnlyDefersSettledTasks() {
        mapOf("todo" to 0, "queued" to 0, "preparing" to 1, "running" to 1,
            "awaiting_input" to 2, "review" to 2, "merging" to 2, "failed" to 2,
            "done" to 3, "canceled" to 3).forEach { (status, column) ->
            assertEquals(status, column, task(status).boardColumn())
        }
        assertEquals(3, task("review").copy(deferredAt = "now").boardColumn())
        assertEquals(3, task("failed").copy(deferredAt = "now").boardColumn())
        assertEquals(1, task("running").copy(deferredAt = "stale").boardColumn())
    }
    @Test fun hidesCanceledAndArchivedByDefault() {
        val tasks = listOf(task("todo"), task("canceled").copy(id = 2), task("done").copy(id = 3, archivedAt = "now"))
        assertEquals(listOf(1), boardTasks(tasks, false).map { it.id })
        assertEquals(listOf(1, 2), boardTasks(tasks, true).map { it.id })
    }
    @Test fun reviewAcceptanceRespectsWorktreeAndForgeSource() {
        val review = task("review").copy(worktreeFolderId = 5, filesChanged = 1)
        assertTrue("merge" in review.availableActions())
        assertFalse("merge" in review.copy(sourceKind = "forge_pr").availableActions())
        assertTrue("deliver_pr" in review.copy(sourceKind = "forge_pr").availableActions())
        assertTrue("complete" in review.copy(filesChanged = 0).availableActions())
        assertFalse("cancel" in task("merging").availableActions())
    }
    @Test fun editingPreservesUnknownOptionsAndAttachments() {
        val original = buildJsonObject {
            put("display_text", "old")
            put("base_branch", "release")
            put("prompt_blocks", buildJsonArray {
                add(buildJsonObject { put("type", "text"); put("text", "old") })
                add(buildJsonObject { put("type", "image"); put("url", "attachment") })
            })
        }
        val edited = taskConfig(original, "new", null)
        assertEquals("release", edited["base_branch"]?.jsonPrimitive?.content)
        assertEquals(JsonNull, edited["agent_type"])
        assertEquals("new", edited["prompt_blocks"]?.jsonArray?.first()?.jsonObject?.get("text")?.jsonPrimitive?.content)
        assertEquals("image", edited["prompt_blocks"]?.jsonArray?.last()?.jsonObject?.get("type")?.jsonPrimitive?.content)
        assertEquals(original["prompt_blocks"], taskConfig(original, "old", "codex")["prompt_blocks"])
    }

    @Test fun movingUsesVisibleOrderAndPreservesOtherColumns() {
        val older = task("todo").copy(id = 1, folderId = 4)
        val newer = older.copy(id = 2, updatedAt = "2026-10-21T00:00:00Z")
        val newest = older.copy(id = 3, updatedAt = "2026-10-22T00:00:00Z")
        val running = older.copy(id = 4, status = "running")
        val archived = older.copy(id = 5, archivedAt = "now")
        val tasks = listOf(older, running, newest, archived, newer)
        assertEquals(listOf(2, 4, 3, 5, 1), reorderedTaskIds(tasks, newest, 1))
        assertNull(reorderedTaskIds(tasks, newest, -1))
    }

}

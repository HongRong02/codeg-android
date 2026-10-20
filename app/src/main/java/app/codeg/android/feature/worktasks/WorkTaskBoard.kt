package app.codeg.android.feature.worktasks

import app.codeg.android.core.model.WorkTask
import kotlinx.serialization.json.*
import java.time.Instant

fun WorkTask.boardColumn(): Int = when {
    deferredAt != null && status in setOf("review", "failed") -> 3
    status in setOf("todo", "queued") -> 0
    status in setOf("preparing", "running") -> 1
    status in setOf("done", "canceled") -> 3
    else -> 2
}

fun boardTasks(tasks: List<WorkTask>, showCanceled: Boolean): List<WorkTask> = tasks
    .filter { it.archivedAt == null && (showCanceled || it.status != "canceled") }
    .sortedByDescending { runCatching { Instant.parse(it.updatedAt) }.getOrNull() }

fun WorkTask.availableActions(): List<String> = buildList {
    if (archivedAt != null) { add("archive"); return@buildList }
    when (status) {
        "todo" -> addAll(listOf("start", "schedule", "delete"))
        "queued", "preparing", "running", "awaiting_input" -> add("cancel")
        "review" -> {
            if (mergeQueued != null && mergeQueued !is JsonNull) add("merge_unqueue")
            else {
                add("return")
                if (filesChanged == 0 || worktreeFolderId == null || worktreeMissing) add("complete")
                else {
                    if (sourceKind != "forge_pr") add("merge")
                    if (sourceKind in setOf("forge_issue", "forge_pr")) add("deliver_pr")
                }
                add("defer")
            }
        }
        "failed" -> addAll(listOf("retry", "defer", "archive", "delete"))
        "done" -> add("archive")
        "canceled" -> addAll(listOf("requeue", "archive", "delete"))
    }
    if (status in setOf("done", "canceled") && worktreeFolderId != null && !worktreeMissing || cleanupState == "failed") add("cleanup")
}

// Keep unknown per-task options and attachments when editing an existing draft.
fun taskConfig(original: JsonObject, description: String, agent: String?): JsonObject = JsonObject(
    original.toMutableMap().apply {
        val oldText = (original["display_text"] as? JsonPrimitive)?.contentOrNull.orEmpty()
        put("display_text", JsonPrimitive(description))
        put("agent_type", agent?.let(::JsonPrimitive) ?: JsonNull)
        if (oldText != description || "prompt_blocks" !in original) {
            val attachments = (original["prompt_blocks"] as? JsonArray).orEmpty().filter {
                (it as? JsonObject)?.get("type")?.jsonPrimitive?.contentOrNull != "text"
            }
            put("prompt_blocks", JsonArray(listOf(buildJsonObject { put("type", "text"); put("text", description) }) + attachments))
        }
    }
)

package app.codeg.android.core.model.wire

import app.codeg.android.core.model.ForgeIssueRow
import app.codeg.android.core.model.ForgeRemote
import kotlinx.serialization.json.*

/** HTTP envelopes use camelCase; the task draft is the server's snake_case model. */
fun forgeRequest(folderId: Int, key: String? = null, value: JsonObject = buildJsonObject {}): JsonObject = buildJsonObject {
    put("folderId", folderId)
    if (key != null) put(key, value)
}

fun forgeTaskDraft(folderId: Int, remote: ForgeRemote, row: ForgeIssueRow): JsonObject = buildJsonObject {
    put("folder_id", folderId)
    putJsonObject("source") {
        put("kind", row.kind); put("number", row.number); put("provider", remote.provider)
        put("server_host", remote.server_host); put("owner_repo", remote.owner_repo)
    }
    putJsonObject("snapshot") {
        put("title", row.title); row.body?.let { put("body", it) }; row.author?.let { put("author", it) }
        putJsonArray("labels") { row.labels.forEach { add(it.name) } }
    }
}

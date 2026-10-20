package app.codeg.android.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import java.util.Locale

@Serializable
data class ForgeRemote(val server_host: String, val owner_repo: String, val provider: String, val supported: Boolean = true) {
    fun sourceKey(row: ForgeIssueRow): String =
        "${provider.trim().lowercase(Locale.ROOT)}:${server_host.trim().lowercase(Locale.ROOT)}:${owner_repo.trim().trim('/').lowercase(Locale.ROOT)}:${row.kind}:${row.number}"
}
@Serializable
data class ForgeLabel(val name: String)
@Serializable
data class ForgeLabelList(val labels: List<ForgeLabel> = emptyList(), val truncated: Boolean = false)
@Serializable
data class ForgeIssueRow(
    val number: Long, val title: String, val body: String? = null, val state: String = "open",
    val draft: Boolean = false, val labels: List<ForgeLabel> = emptyList(), val author: String? = null,
    val updated_at: String? = null, val html_url: String = "", val is_pr: Boolean = false,
) { val kind: String get() = if (is_pr) "pr" else "issue" }
@Serializable
data class ForgeIssueList(val rows: List<ForgeIssueRow> = emptyList(), val page: Int = 1, val has_next: Boolean = false, val total_count: Long? = null, val incomplete: Boolean = false)
@Serializable
data class ForgeComment(val id: String, val body: String, val author: String? = null, val created_at: String? = null)
@Serializable
data class ForgeCommentList(val comments: List<ForgeComment> = emptyList(), val page: Int = 1, val has_next: Boolean = false)
@Serializable
data class ForgeChangeDetail(val number: Long, val base_ref: String, val head_ref: String, val head_sha: String? = null, val state: String, val draft: Boolean = false, val mergeable: Boolean? = null, val merge_state: String? = null)
@Serializable
data class ForgeChangedFile(val path: String, val patch: String? = null, val binary: Boolean = false)
@Serializable
data class ForgeChangedFileList(val files: List<ForgeChangedFile> = emptyList(), val page: Int = 1, val has_next: Boolean = false)
@Serializable
data class ForgeMergeOptions(val methods: List<String> = emptyList(), val default_method: String = "merge", val merge_strategy: String = "merge_commit")
@Serializable
data class ForgeTaskLink(val source_key: String, val task_id: Int)
@Serializable
data class ForgeCreateResult(val outcome: String, val task: JsonObject? = null, val existing: JsonObject? = null)

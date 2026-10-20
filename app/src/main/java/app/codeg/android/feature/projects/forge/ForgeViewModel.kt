package app.codeg.android.feature.projects.forge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.*
import app.codeg.android.core.network.ApiError
import app.codeg.android.core.network.CodegClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.*

data class ForgeUi(
    val remote: ForgeRemote? = null, val busy: Boolean = false, val error: Throwable? = null,
    val tab: String = "issues", val filter: String = "open", val list: ForgeIssueList = ForgeIssueList(),
    val counts: Map<String, Long?> = emptyMap(), val selected: ForgeIssueRow? = null,
    val comments: ForgeCommentList = ForgeCommentList(), val detail: ForgeChangeDetail? = null,
    val files: ForgeChangedFileList = ForgeChangedFileList(), val options: ForgeMergeOptions? = null,
    val labels: ForgeLabelList = ForgeLabelList(), val taskId: Int? = null, val mismatch: Boolean = false,
)

class ForgeViewModel(private val client: CodegClient, private val folderId: Int) : ViewModel() {
    private val state = MutableStateFlow(ForgeUi())
    val ui = state.asStateFlow()
    private var job: Job? = null
    fun probe() = run {
        try { state.value = state.value.copy(remote = client.forgeRemote(forgeRequest(folderId))) }
        catch (e: ApiError.Server) {
            if (e.status == 404 || e.serverMessage.contains("unknown", ignoreCase = true)) {
                state.value = state.value.copy(remote = null)
            } else throw e
        }
    }
    fun refresh(tab: String = state.value.tab, filter: String = state.value.filter, page: Int = 1) = run {
        state.value = state.value.copy(tab = tab, filter = filter, selected = null)
        val result = client.forgeIssues(forgeRequest(folderId, "query", buildJsonObject {
            put("tab", tab); put("state", filter); put("page", page); put("perPage", 20)
        }))
        state.value = state.value.copy(list = result, counts = mapOf(tab to result.total_count))
        val other = if (tab == "issues") "prs" else "issues"
        val count = client.forgeCount(buildJsonObject {
            put("folderId", folderId); put("tab", other); putJsonObject("filters") { put("state", filter) }
        })
        state.value = state.value.copy(counts = state.value.counts + (other to count))
    }
    fun open(row: ForgeIssueRow) = run { loadDetail(row) }
    private suspend fun loadDetail(row: ForgeIssueRow) {
        state.value = state.value.copy(selected = row, detail = null, files = ForgeChangedFileList(),
            comments = ForgeCommentList(), options = null, taskId = null, mismatch = false)
        // Keep independent detail sections usable if another endpoint fails.
        section { state.value = state.value.copy(comments = client.forgeComments(item(row, "filters"))) }
        if (row.is_pr) {
            section { state.value = state.value.copy(detail = client.forgeDetail(item(row, "query"))) }
            section { state.value = state.value.copy(files = client.forgeFiles(item(row, "query"))) }
            section { state.value = state.value.copy(options = client.forgeMergeOptions(forgeRequest(folderId))) }
        }
        section {
            val remote = state.value.remote ?: return@section
            val links = client.forgeLookup(buildJsonObject { putJsonArray("sourceKeys") { add(remote.sourceKey(row)) } })
            state.value = state.value.copy(taskId = links.firstOrNull()?.task_id)
        }
    }
    fun back() { if (!state.value.busy) state.value = state.value.copy(selected = null, error = null) }
    fun moreComments() = run {
        val row = state.value.selected ?: return@run
        val old = state.value.comments
        val next = client.forgeComments(item(row, "filters") { put("page", old.page + 1) })
        state.value = state.value.copy(comments = next.copy(comments = (old.comments + next.comments).distinctBy { it.id }))
    }
    fun moreFiles() = run {
        val row = state.value.selected ?: return@run
        val old = state.value.files
        val next = client.forgeFiles(item(row, "query") { put("page", old.page + 1) })
        state.value = state.value.copy(files = next.copy(files = old.files + next.files))
    }
    fun comment(body: String, onSuccess: () -> Unit) = run {
        val row = state.value.selected ?: return@run
        client.forgeComment(item(row, "draft") { put("body", body.trim()) })
        onSuccess()
        loadDetail(row)
    }
    fun setState() = run {
        val row = state.value.selected ?: return@run
        val changed = client.forgeState(item(row, "request") { put("action", if (row.state == "open") "close" else "reopen") })
        loadDetail(changed)
    }
    fun merge(method: String) = run {
        val row = state.value.selected ?: return@run
        val changed = client.forgeMerge(item(row, "request") {
            put("method", method); state.value.detail?.head_sha?.let { put("headSha", it) }
        })
        loadDetail(changed ?: row.copy(state = "merged"))
    }
    fun loadLabels() = run { state.value = state.value.copy(labels = client.forgeLabels(forgeRequest(folderId))) }
    fun createIssue(title: String, body: String, labels: Set<String>, onSuccess: () -> Unit) = run {
        val row = client.forgeCreateIssue(forgeRequest(folderId, "draft", buildJsonObject {
            put("title", title.trim()); put("body", body.trim()); putJsonArray("labels") { labels.forEach { add(it) } }
        }))
        onSuccess(); loadDetail(row)
    }
    fun createTask() = run {
        val row = state.value.selected ?: return@run
        val remote = state.value.remote ?: return@run
        val result = client.forgeCreateTask(buildJsonObject { put("draft", forgeTaskDraft(folderId, remote, row)) })
        state.value = state.value.copy(
            taskId = (result.task ?: result.existing)?.get("id")?.jsonPrimitive?.intOrNull,
            mismatch = result.outcome == "folder_mismatch",
        )
    }
    private fun item(row: ForgeIssueRow, key: String, extra: JsonObjectBuilder.() -> Unit = {}): JsonObject =
        forgeRequest(folderId, key, buildJsonObject { put("number", row.number); put("kind", row.kind); extra() })
    private suspend fun section(block: suspend () -> Unit) {
        try { block() } catch (e: CancellationException) { throw e }
        catch (e: Exception) { state.value = state.value.copy(error = e) }
    }
    private fun run(block: suspend () -> Unit) {
        if (job?.isActive == true) return
        job = viewModelScope.launch {
            state.value = state.value.copy(busy = true, error = null)
            try { block() } catch (e: CancellationException) { throw e }
            catch (e: Exception) { state.value = state.value.copy(error = e) }
            finally { state.value = state.value.copy(busy = false) }
        }
    }
}

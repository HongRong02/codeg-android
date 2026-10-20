package app.codeg.android.feature.projects.git

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.model.MergeVersions
import app.codeg.android.core.model.mergeOperationActive
import app.codeg.android.core.network.ApiError
import app.codeg.android.core.network.CodegClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MergeOpsUi(
    val busy: Boolean = false,
    val ready: Boolean = false,
    val supported: Boolean = true,
    val branches: List<String> = emptyList(),
    val conflicts: List<String> = emptyList(),
    val active: Boolean = false,
    val operation: String = "merge",
    val file: String? = null,
    val versions: MergeVersions? = null,
    val error: String? = null,
    val completed: Boolean = false,
)

class MergeOpsViewModel : ViewModel() {
    private val state = MutableStateFlow(MergeOpsUi())
    val ui = state.asStateFlow()
    private suspend fun detect(client: CodegClient, path: String) {
        val head = client.gitHasMergeHead(path)
        val files = client.gitListConflicts(path)
        state.value = state.value.copy(conflicts = files, active = mergeOperationActive(head, files, state.value.active, state.value.operation),
            operation = if (head) "merge" else state.value.operation)
    }
    private fun run(block: suspend () -> Unit) {
        if (state.value.busy) return
        state.value = state.value.copy(busy = true, error = null, completed = false)
        viewModelScope.launch {
            try { block() } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                val unsupported = e is ApiError.Server && (e.status == 404 || e.code?.contains("unknown") == true)
                state.value = state.value.copy(error = if (unsupported) null else e.message, supported = !unsupported)
            } finally { state.value = state.value.copy(busy = false) }
        }
    }
    fun refresh(client: CodegClient, path: String) = run {
        detect(client, path)
        val branches = client.gitListAllBranches(path)
        state.value = state.value.copy(ready = true, branches = (branches.local + branches.remote).distinct())
    }
    fun operation(value: String) { if (!state.value.busy) state.value = state.value.copy(operation = value) }
    fun execute(client: CodegClient, path: String, action: String, target: String, mode: String) = run {
        state.value = state.value.copy(operation = action)
        try {
            when (action) {
                "merge" -> client.gitMerge(path, target)
                "rebase" -> client.gitRebase(path, target)
                "reset" -> client.gitReset(path, target, mode)
            }
            state.value = state.value.copy(completed = true)
        } finally { detect(client, path) }
    }
    fun versions(client: CodegClient, path: String, file: String) = run {
        state.value = state.value.copy(file = null, versions = null)
        val versions = client.gitConflictFileVersions(path, file)
        state.value = state.value.copy(file = file, versions = versions)
    }
    fun resolve(client: CodegClient, path: String, ours: Boolean) = run {
        val current = state.value
        val versions = current.versions ?: return@run
        client.gitResolveConflict(path, current.file ?: return@run, if (ours) versions.ours else versions.theirs)
        state.value = state.value.copy(file = null, versions = null)
        detect(client, path)
    }
    fun finish(client: CodegClient, path: String, abort: Boolean) = run {
        if (abort) client.gitAbortOperation(path, state.value.operation)
        else {
            // Recheck immediately before continuing: another client may have changed the index.
            val remaining = client.gitListConflicts(path)
            state.value = state.value.copy(conflicts = remaining)
            if (remaining.isNotEmpty()) return@run
            try {
                client.gitContinueOperation(path, state.value.operation)
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                // Continuing a rebase can stop again at the next conflicting commit.
                detect(client, path)
                throw e
            }
        }
        state.value = state.value.copy(active = false)
        detect(client, path)
        state.value = state.value.copy(completed = true, file = null, versions = null)
    }
}

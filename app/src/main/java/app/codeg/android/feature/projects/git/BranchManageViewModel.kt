package app.codeg.android.feature.projects.git

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.model.BranchManageEntry
import app.codeg.android.core.model.GitCredentials
import app.codeg.android.core.network.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BranchManageViewModel(private val client: CodegClient, private val path: String) : ViewModel() {
    data class Ui(
        val branches: List<BranchManageEntry> = emptyList(),
        val busy: Boolean = false,
        val supported: Boolean = true,
        val hiddenActions: Set<String> = emptySet(),
        val error: String? = null,
        val completed: Boolean = false,
        val conflict: List<String>? = null,
        val diff: String? = null,
        val diffBranch: String? = null,
        val credentialsRequested: Boolean = false,
    )
    private val state = MutableStateFlow(Ui())
    val ui = state.asStateFlow()
    private var credentials: CompletableDeferred<GitCredentials?>? = null

    init { refresh() }

    private suspend fun load() {
        val local = client.gitListManagedBranches(path)
        val all = client.gitListAllBranches(path)
        val localNames = all.local.toSet()
        val branches = local.filter { it.name in localNames }.toMutableList()
        all.local.filter { name -> branches.none { it.name == name } }.forEach { branches += BranchManageEntry(it) }
        all.remote.filterNot { it.endsWith("/HEAD") }.forEach { name ->
            val detail = local.firstOrNull { it.name == name }
            branches += (detail ?: BranchManageEntry(name)).copy(isRemote = true)
        }
        state.update { it.copy(branches = branches) }
    }

    fun refresh() = run("list") { load() }

    fun delete(branch: BranchManageEntry, force: Boolean) = run(if (branch.isRemote) "remoteDelete" else "delete") {
        if (branch.isRemote) withCredentials { client.gitDeleteRemoteBranch(path, branch.remote, branch.remoteBranch, it) }
        else client.gitDeleteBranch(path, branch.name, force)
        state.update { it.copy(completed = true, diff = null, diffBranch = null) }
        load()
    }

    fun update(branch: BranchManageEntry) = run("update") {
        val result = withCredentials { client.gitUpdateBranch(path, branch.name, branch.isRemote, it) }
        state.update { it.copy(completed = true, conflict = result.conflict?.takeIf { c -> c.hasConflicts }?.conflictedFiles) }
        load()
    }

    fun compare(branch: BranchManageEntry) = run("compare") {
        val diff = client.gitDiffWithBranch(path, branch.name)
        state.update { it.copy(diff = diff, diffBranch = branch.name) }
    }

    fun submitCredentials(value: GitCredentials?) { credentials?.complete(value) }

    private suspend fun <T> withCredentials(body: suspend (GitCredentials?) -> T): T {
        var value: GitCredentials? = null
        while (true) {
            try { return body(value) } catch (e: ApiError) {
                if (!e.isAuthFailure) throw e
                val pending = CompletableDeferred<GitCredentials?>()
                credentials = pending
                state.update { it.copy(credentialsRequested = true, error = e.displayMessage()) }
                try { value = pending.await() ?: throw e } finally {
                    credentials = null
                    state.update { it.copy(credentialsRequested = false) }
                }
            }
        }
    }

    private fun run(action: String, body: suspend () -> Unit) {
        if (state.value.busy) return
        state.update { it.copy(busy = true, error = null, completed = false, conflict = null) }
        viewModelScope.launch {
            try { body() } catch (e: CancellationException) { throw e } catch (e: Exception) {
                val unsupported = e is ApiError.Server &&
                    (e.status == 404 || e.code == "unknown_command" || e.code == "unknown_method" || e.serverMessage.contains("unknown command", ignoreCase = true))
                state.update {
                    if (unsupported) it.copy(supported = action != "list" && it.supported, hiddenActions = it.hiddenActions + action)
                    else it.copy(error = e.displayMessage())
                }
            } finally { state.update { it.copy(busy = false) } }
        }
    }
}

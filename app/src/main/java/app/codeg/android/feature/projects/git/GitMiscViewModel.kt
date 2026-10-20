package app.codeg.android.feature.projects.git

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.model.GitMiscWorktree
import app.codeg.android.core.model.GitRemote
import app.codeg.android.core.model.GitCredentials
import app.codeg.android.core.network.ApiError
import app.codeg.android.core.network.CodegClient
import app.codeg.android.core.network.displayMessage
import app.codeg.android.core.network.isAuthFailure
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GitMiscViewModel(application: Application) : AndroidViewModel(application) {
    private val appContext = application.applicationContext
    private val state = MutableStateFlow(GitMiscUi())
    val ui = state.asStateFlow()

    fun run(action: String, operation: suspend () -> Unit) {
        if (state.value.busy) return
        state.update { it.copy(busy = true, error = null, success = false, authRemote = null) }
        viewModelScope.launch {
            try {
                operation()
                state.update { it.copy(success = true) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                if (e is ApiError.Server && (e.status == 404 || e.code in setOf("unknown_command", "unknown_method", "not_found"))) {
                    state.update { it.copy(hidden = it.hidden + action) }
                } else {
                    state.update { it.copy(error = e.displayMessage(appContext)) }
                }
            } finally {
                state.update { it.copy(busy = false) }
            }
        }
    }

    suspend fun load(client: CodegClient, path: String) {
        try {
            client.gitStatus(path)
        } catch (e: ApiError.Server) {
            if (e.code != "not_a_git_repository") throw e
            state.update { it.copy(notRepository = true, remotes = emptyList(), worktrees = emptyList()) }
            return
        }
        state.update { it.copy(notRepository = false) }
        val remotes = client.gitListRemotes(path).distinctBy { it.name }
        val worktrees = client.gitListAllBranches(path).worktreeBranches.mapNotNull { branch ->
            client.resolveWorktreeFolder(path, branch).path?.takeIf { it != path }?.let { GitMiscWorktree(branch, it) }
        }
        state.update { it.copy(remotes = remotes, worktrees = worktrees) }
    }

    fun fetch(client: CodegClient, path: String, name: String, credentials: GitCredentials? = null) = run("fetch") {
        try {
            client.gitFetchRemote(path, name, credentials)
        } catch (e: ApiError) {
            if (!e.isAuthFailure) throw e
            state.update { it.copy(authRemote = name) }
            throw e
        }
    }
}

data class GitMiscUi(
    val busy: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val notRepository: Boolean = false,
    val remotes: List<GitRemote> = emptyList(),
    val worktrees: List<GitMiscWorktree> = emptyList(),
    val hidden: Set<String> = emptySet(),
    val authRemote: String? = null,
)

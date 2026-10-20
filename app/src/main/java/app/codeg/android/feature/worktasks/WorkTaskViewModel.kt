package app.codeg.android.feature.worktasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.data.ServerRepository
import app.codeg.android.core.model.*
import app.codeg.android.core.network.CodegClient
import app.codeg.android.core.network.displayMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WorkTaskUi(
    val loading: Boolean = true,
    val supported: Boolean = false,
    val tasks: List<WorkTask> = emptyList(),
    val attention: Long = 0,
    val selected: WorkTask? = null,
    val detailLoading: Boolean = false,
    val events: List<WorkTaskEvent> = emptyList(),
    val files: List<WorkTaskChangedFile> = emptyList(),
    val diff: String = "",
    val error: String? = null,
    val detailErrors: List<String> = emptyList(),
)

@HiltViewModel
class WorkTaskViewModel @Inject constructor(private val repository: ServerRepository) : ViewModel() {
    private val _ui = MutableStateFlow(WorkTaskUi())
    val ui: StateFlow<WorkTaskUi> = _ui.asStateFlow()
    private var client: CodegClient? = null
    private var loadJob: Job? = null
    private var detailJob: Job? = null

    init {
        viewModelScope.launch {
            repository.selectedProfile.collectLatest { profile ->
                loadJob?.cancel()
                detailJob?.cancel()
                client = null
                _ui.value = WorkTaskUi()
                if (profile == null) {
                    _ui.update { it.copy(loading = false) }
                } else {
                    client = repository.client(profile)
                    load()
                }
            }
        }
    }

    fun load() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            val c = client ?: return@launch
            _ui.update { it.copy(loading = true, error = null) }
            try {
                val count = c.workTaskAttentionCount()
                val tasks = c.workTaskList()
                _ui.update { it.copy(loading = false, supported = true, attention = count, tasks = tasks) }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _ui.update { it.copy(loading = false, supported = !e.isWorkTasksUnsupported(), error = if (e.isWorkTasksUnsupported()) null else e.displayMessage()) }
            }
        }
    }

    fun select(task: WorkTask?) {
        detailJob?.cancel()
        _ui.update { it.copy(selected = task, detailLoading = task != null, events = emptyList(), files = emptyList(), diff = "", detailErrors = emptyList()) }
        if (task == null) return
        detailJob = viewModelScope.launch {
            val c = client ?: return@launch
            // Keep successful sections visible when a worktree has already been cleaned up.
            suspend fun section(block: suspend () -> Unit) {
                try { block() } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    _ui.update { it.copy(detailErrors = it.detailErrors + e.displayMessage()) }
                }
            }
            section { val value = c.workTaskGet(task.id); _ui.update { it.copy(selected = value) } }
            section { val value = c.workTaskEvents(task.id); _ui.update { it.copy(events = value.sortedBy { event -> event.createdAt }) } }
            section { val value = c.workTaskChangedFiles(task.id); _ui.update { it.copy(files = value) } }
            section { val value = c.workTaskDiff(task.id); _ui.update { it.copy(diff = value) } }
            _ui.update { it.copy(detailLoading = false) }
        }
    }
}

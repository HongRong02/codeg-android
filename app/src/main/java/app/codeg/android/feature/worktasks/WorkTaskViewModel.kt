package app.codeg.android.feature.worktasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.data.ServerRepository
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.*
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
    val busy: Boolean = false,
    val editorOpen: Boolean = false,
    val editing: WorkTask? = null,
    val folders: List<FolderDetail> = emptyList(),
    val agents: List<AcpAgentInfo> = emptyList(),
    val templates: List<WorkTaskTemplate> = emptyList(),
    val hiddenActions: Set<String> = emptySet(),
)

@HiltViewModel
class WorkTaskViewModel @Inject constructor(private val repository: ServerRepository) : ViewModel() {
    private val _ui = MutableStateFlow(WorkTaskUi())
    val ui: StateFlow<WorkTaskUi> = _ui.asStateFlow()
    private var client: CodegClient? = null
    private var loadJob: Job? = null
    private var detailJob: Job? = null
    private var mutationJob: Job? = null
    private var editorJob: Job? = null

    init {
        viewModelScope.launch {
            repository.selectedProfile.collectLatest { profile ->
                loadJob?.cancel()
                detailJob?.cancel()
                mutationJob?.cancel()
                editorJob?.cancel()
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

    fun dismissEditor() { if (!_ui.value.busy) _ui.update { it.copy(editorOpen = false) } }

    fun edit(task: WorkTask? = null) {
        _ui.update { it.copy(editorOpen = true, editing = task, error = null) }
        editorJob?.cancel()
        editorJob = viewModelScope.launch {
            val c = client ?: return@launch
            try {
                val folders = c.listFolders().filter { !it.isChat }
                val agents = c.acpListAgents().filter { it.available && it.enabled }
                _ui.update { it.copy(folders = folders, agents = agents) }
                if ("template_list" !in _ui.value.hiddenActions) {
                    try { val templates = c.workTaskTemplates(); _ui.update { it.copy(templates = templates) } }
                    catch (e: Exception) {
                        if (e is CancellationException) throw e
                        if (e.isWorkTasksUnsupported()) _ui.update { it.copy(hiddenActions = it.hiddenActions + "template_list") }
                        else throw e
                    }
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _ui.update { it.copy(error = e.displayMessage()) }
            }
        }
    }

    private fun mutate(action: String, block: suspend (CodegClient) -> Unit) {
        if (_ui.value.busy || action in _ui.value.hiddenActions) return
        mutationJob = viewModelScope.launch {
            val c = client ?: return@launch
            _ui.update { it.copy(busy = true, error = null) }
            try {
                block(c)
                val tasks = c.workTaskList()
                val count = c.workTaskAttentionCount()
                _ui.update { it.copy(tasks = tasks, attention = count) }
                val selectedId = _ui.value.selected?.id
                if (selectedId != null) select(tasks.find { it.id == selectedId })
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                if (e.isWorkTasksUnsupported()) _ui.update { it.copy(hiddenActions = it.hiddenActions + action) }
                else _ui.update { it.copy(error = e.displayMessage()) }
            } finally { _ui.update { it.copy(busy = false) } }
        }
    }

    fun save(draft: WorkTaskDraft) {
        val id = _ui.value.editing?.id
        mutate(if (id == null) "create" else "update") { c ->
            c.workTaskSave(WorkTaskSaveBody(draft, id))
            _ui.update { it.copy(editorOpen = false) }
        }
    }

    fun startAll() = mutate("start_all") { it.workTaskStartAll() }

    fun action(task: WorkTask, action: String, text: String = "") = mutate(action) { c ->
        c.workTaskAction(action, WorkTaskActionBody(task.id, deleteWorktree = false,
            feedback = text.takeIf { action == "return" },
            scheduledAt = text.takeIf { action == "schedule" && it.isNotBlank() },
            archived = if (action == "archive") task.archivedAt == null else null,
            deferred = if (action == "defer") task.deferredAt == null else null))
    }

    fun move(task: WorkTask, delta: Int) = mutate("reorder") { c ->
        // Reorder the whole project, preserving rows outside the visible column.
        val project = _ui.value.tasks.filter { it.folderId == task.folderId }.toMutableList()
        val column = boardTasks(project, true).filter { it.boardColumn() == task.boardColumn() }
        val neighbor = column.getOrNull(column.indexOfFirst { it.id == task.id } + delta) ?: return@mutate
        val a = project.indexOfFirst { it.id == task.id }
        val b = project.indexOfFirst { it.id == neighbor.id }
        project[a] = neighbor
        project[b] = task
        c.workTaskReorder(WorkTaskReorderBody(task.folderId, project.map { it.id }))
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

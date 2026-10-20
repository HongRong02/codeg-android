package app.codeg.android.feature.automations

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.data.ServerRepository
import app.codeg.android.core.model.Automation
import app.codeg.android.core.model.AutomationRun
import app.codeg.android.core.network.CodegClient
import app.codeg.android.core.network.displayMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AutomationFilter {
    ALL,
    ENABLED,
    DISABLED,
}

data class AutomationUiState(
    val editorOpen: Boolean = false,
    val editing: Automation? = null,
    val busy: Boolean = false,
    val folders: List<app.codeg.android.core.model.FolderDetail> = emptyList(),
    val preview: String? = null,
    val previewSchedule: Pair<String, String>? = null,
    val previewError: String? = null,
    val previewLoading: Boolean = false,
    val writesSupported: Boolean = true,
    val loading: Boolean = true,
    val refreshing: Boolean = false,
    val automations: List<Automation> = emptyList(),
    val filter: AutomationFilter = AutomationFilter.ALL,
    val selectedAutomationId: Int? = null,
    val detailLoading: Boolean = false,
    val runs: List<AutomationRun> = emptyList(),
    val error: String? = null,
) {
    val selectedAutomation: Automation?
        get() = automations.firstOrNull { it.id == selectedAutomationId }

    val filteredAutomations: List<Automation>
        get() = when (filter) {
            AutomationFilter.ALL -> automations
            AutomationFilter.ENABLED -> automations.filter { it.enabled }
            AutomationFilter.DISABLED -> automations.filter { !it.enabled }
        }
}

@HiltViewModel
class AutomationViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: ServerRepository,
) : ViewModel() {

    private val _ui = MutableStateFlow(AutomationUiState())
    val ui: StateFlow<AutomationUiState> = _ui.asStateFlow()

    private var client: CodegClient? = null

    init {
        load(isInitial = true)
    }

    fun load(isInitial: Boolean = false) {
        viewModelScope.launch {
            if (isInitial) {
                _ui.update { it.copy(loading = true, error = null) }
            } else {
                _ui.update { it.copy(refreshing = true, error = null) }
            }

            try {
                val profile = repository.selectedProfile.first()
                    ?: throw IllegalStateException("No server selected")
                val c = repository.client(profile)
                    ?: throw IllegalStateException("Missing client")
                client = c

                val list = c.automationList()
                _ui.update { state ->
                    val currentSelected = state.selectedAutomationId
                    val newSelected = if (currentSelected != null && list.any { it.id == currentSelected }) {
                        currentSelected
                    } else {
                        null
                    }
                    state.copy(
                        loading = false,
                        refreshing = false,
                        automations = list,
                        selectedAutomationId = newSelected,
                        error = null,
                    )
                }

                val selId = _ui.value.selectedAutomationId
                if (selId != null) {
                    loadRuns(selId)
                }
            } catch (e: Exception) {
                _ui.update {
                    it.copy(
                        loading = false,
                        refreshing = false,
                        error = e.displayMessage(appContext),
                    )
                }
            }
        }
    }

    fun setFilter(filter: AutomationFilter) {
        _ui.update { it.copy(filter = filter) }
    }

    fun selectAutomation(automationId: Int?) {
        _ui.update { it.copy(selectedAutomationId = automationId, runs = emptyList()) }
        if (automationId != null) {
            loadRuns(automationId)
            write {
                it.automationMarkSeen()
                _ui.update { state -> state.copy(automations = state.automations.map { row -> row.copy(unseenFailures = 0) }) }
            }
        }
    }

    fun loadRuns(automationId: Int) {
        viewModelScope.launch {
            _ui.update { it.copy(detailLoading = true) }
            try {
                val c = client ?: run {
                    val profile = repository.selectedProfile.first() ?: return@launch
                    repository.client(profile) ?: return@launch
                }
                val runs = c.automationRuns(automationId)
                if (_ui.value.selectedAutomationId == automationId) {
                    _ui.update { it.copy(detailLoading = false, runs = runs) }
                }
            } catch (e: Exception) {
                if (_ui.value.selectedAutomationId == automationId) {
                    _ui.update { it.copy(detailLoading = false) }
                }
            }
        }
    }

    private var previewJob: kotlinx.coroutines.Job? = null

    fun openEditor(automation: Automation?) {
        _ui.update { it.copy(editorOpen = true, editing = automation, preview = null, previewError = null, error = null) }
        write { c ->
            val folders = c.listFolders().filter { !it.isChat && it.parentId == null }
            _ui.update { it.copy(folders = folders) }
        }
    }

    fun closeEditor() {
        if (_ui.value.busy) return
        previewJob?.cancel()
        _ui.update { it.copy(editorOpen = false) }
    }

    fun preview(cron: String, timezone: String) {
        previewJob?.cancel()
        _ui.update { it.copy(preview = null, previewSchedule = cron to timezone, previewError = null, previewLoading = true) }
        previewJob = viewModelScope.launch {
            try {
                kotlinx.coroutines.delay(350)
                val result = requireNotNull(client).automationComputeNextRun(cron, timezone)
                _ui.update { it.copy(preview = result.trim('"'), previewLoading = false) }
            } catch (e: kotlinx.coroutines.CancellationException) { throw e
            } catch (e: Exception) {
                _ui.update { it.copy(previewError = e.displayMessage(appContext), previewLoading = false) }
                unsupported(e)
            }
        }
    }

    fun save(draft: app.codeg.android.core.model.wire.AutomationDraft) = write { c ->
        val editing = _ui.value.editing
        val saved = if (editing == null) c.automationCreate(draft) else c.automationUpdate(editing.id, draft)
        _ui.update { state -> state.copy(editorOpen = false, selectedAutomationId = saved.id,
            automations = state.automations.filterNot { it.id == saved.id } + saved) }
        loadRuns(saved.id)
    }

    fun delete(automation: Automation) = write { c ->
        c.automationDelete(automation.id)
        _ui.update { state -> state.copy(selectedAutomationId = null, runs = emptyList(),
            automations = state.automations.filterNot { it.id == automation.id }) }
    }

    fun runNow(id: Int) = write { c -> c.automationRunNow(id); loadRuns(id) }
    fun cancelRun(id: Int) = write { c ->
        c.automationCancelRun(id)
        _ui.value.selectedAutomationId?.let { loadRuns(it) }
    }

    private fun unsupported(e: Exception): Boolean {
        val message = e.message.orEmpty().lowercase()
        val unsupported = (e as? app.codeg.android.core.network.ApiError.Server)?.status == 404 ||
            "unknown command" in message || "unknown endpoint" in message
        if (unsupported) _ui.update { it.copy(writesSupported = false, editorOpen = false, error = null) }
        return unsupported
    }

    private fun write(action: suspend (CodegClient) -> Unit) {
        if (_ui.value.busy) return
        _ui.update { it.copy(busy = true, error = null) }
        viewModelScope.launch {
            try { action(requireNotNull(client))
            } catch (e: kotlinx.coroutines.CancellationException) { throw e
            } catch (e: Exception) {
                if (!unsupported(e)) _ui.update { it.copy(error = e.displayMessage(appContext)) }
            } finally { _ui.update { it.copy(busy = false) } }
        }
    }

    fun toggleEnabled(automation: Automation) {
        viewModelScope.launch {
            val newEnabled = !automation.enabled
            // Optimistic update
            _ui.update { state ->
                state.copy(
                    automations = state.automations.map {
                        if (it.id == automation.id) it.copy(enabled = newEnabled) else it
                    }
                )
            }

            try {
                val c = client ?: run {
                    val profile = repository.selectedProfile.first() ?: return@launch
                    repository.client(profile) ?: return@launch
                }
                val updated = c.automationSetEnabled(automation.id, newEnabled)
                _ui.update { state ->
                    state.copy(
                        automations = state.automations.map {
                            if (it.id == updated.id) updated else it
                        }
                    )
                }
            } catch (e: Exception) {
                // Revert on failure
                _ui.update { state ->
                    state.copy(
                        automations = state.automations.map {
                            if (it.id == automation.id) automation else it
                        },
                        error = e.displayMessage(appContext),
                    )
                }
            }
        }
    }
}

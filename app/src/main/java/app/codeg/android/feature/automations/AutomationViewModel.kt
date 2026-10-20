package app.codeg.android.feature.automations

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
                        error = e.displayMessage(),
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
                        error = e.displayMessage(),
                    )
                }
            }
        }
    }
}

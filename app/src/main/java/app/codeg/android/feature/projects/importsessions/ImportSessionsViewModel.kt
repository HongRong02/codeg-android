package app.codeg.android.feature.projects.importsessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.data.ServerRepository
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.ImportSessionKey
import app.codeg.android.core.network.ApiError
import app.codeg.android.core.network.CodegClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal fun unsupportedImport(e: Exception): Boolean = e is ApiError.Server &&
    (e.status == 404 || e.code?.contains("unknown", true) == true || e.serverMessage.contains("unknown", true))

data class ImportSessionsUi(
    val supported: Boolean = false,
    val busy: Boolean = false,
    val scan: ImportScanResult? = null,
    val selected: Set<ImportSessionKey> = emptySet(),
    val result: ImportSessionsResult? = null,
    val error: String? = null,
)

@HiltViewModel
class ImportSessionsViewModel @Inject constructor(private val repository: ServerRepository) : ViewModel() {
    private val state = MutableStateFlow(ImportSessionsUi())
    val ui: StateFlow<ImportSessionsUi> = state.asStateFlow()
    private var client: CodegClient? = null

    init {
        viewModelScope.launch {
            repository.selectedProfile.collectLatest { profile ->
                client = null
                state.value = ImportSessionsUi()
                if (profile != null) {
                    client = repository.client(profile)
                    client?.let { scanWith(it) }
                }
            }
        }
    }

    private suspend fun scanWith(c: CodegClient) {
        state.update { it.copy(busy = true, error = null, result = null, selected = emptySet()) }
        try {
            val scan = c.scanImportableSessions()
            state.update { it.copy(scan = scan, supported = true) }
        } catch (e: CancellationException) { throw e
        } catch (e: Exception) {
            state.update { it.copy(supported = !unsupportedImport(e), error = if (unsupportedImport(e)) null else e.message) }
        } finally { state.update { it.copy(busy = false) } }
    }

    fun scan() {
        if (state.value.busy) return
        val c = client ?: return
        viewModelScope.launch { scanWith(c) }
    }

    fun toggle(keys: List<ImportSessionKey>) {
        if (state.value.busy) return
        state.update { it.copy(selected = if (keys.all(it.selected::contains)) it.selected - keys.toSet() else it.selected + keys) }
    }

    fun import(all: Boolean = false, onImported: () -> Unit) {
        if (state.value.busy) return
        val c = client ?: return
        val available = state.value.scan?.folders.orEmpty().flatMap { it.sessions }.importableKeys()
        val keys = if (all) available else available.filter { it in state.value.selected }
        if (keys.isEmpty()) return
        state.update { it.copy(busy = true, error = null) }
        viewModelScope.launch {
            try {
                val result = c.importSelectedSessions(keys)
                state.update { it.copy(result = result, selected = emptySet()) }
                repository.notifyConversationsChanged()
                repository.notifyFoldersChanged()
                onImported()
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) {
                state.update { it.copy(error = if (unsupportedImport(e)) null else e.message, supported = !unsupportedImport(e)) }
            } finally { state.update { it.copy(busy = false) } }
        }
    }
}

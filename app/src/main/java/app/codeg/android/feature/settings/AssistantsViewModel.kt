package app.codeg.android.feature.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.R
import app.codeg.android.core.data.ServerRepository
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.AssistantDraft
import app.codeg.android.core.network.ApiError
import app.codeg.android.core.network.CodegClient
import app.codeg.android.core.network.displayMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AssistantsUi(
    val rows: List<Assistant> = emptyList(),
    val agents: List<AcpAgentInfo> = emptyList(),
    val loading: Boolean = true,
    val busy: Boolean = false,
    val supported: Boolean = true,
    val error: String? = null,
    val editing: Boolean = false,
    val editor: Assistant? = null,
    val options: AgentOptionsSnapshot? = null,
    val probing: Boolean = false,
    val optionsError: String? = null,
)

@HiltViewModel
class AssistantsViewModel @Inject constructor(
    private val repository: ServerRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val state = MutableStateFlow(AssistantsUi())
    val ui = state.asStateFlow()
    private var client: CodegClient? = null
    private var operation: Job? = null
    private var probe: Job? = null

    init {
        viewModelScope.launch {
            repository.selectedProfile.collectLatest { profile ->
                operation?.cancel()
                probe?.cancel()
                client = null
                state.value = AssistantsUi()
                try {
                    val c = profile?.let { repository.client(it) }
                    client = c
                    if (c == null) {
                        state.update { it.copy(loading = false, error = context.getString(R.string.assistants_no_server)) }
                    } else loadFrom(c)
                } catch (e: CancellationException) { throw e }
                catch (e: Exception) { failure(e) }
            }
        }
    }

    private fun failure(e: Exception) {
        val unsupported = e is ApiError.Server && (e.status == 404 || e.code in listOf("unknown_command", "unknown_method") || e.serverMessage.contains("unknown command", true))
        state.update { it.copy(loading = false, busy = false, supported = !unsupported, editing = if (unsupported) false else it.editing, error = if (unsupported) null else e.displayMessage(context)) }
    }

    private suspend fun loadFrom(c: CodegClient) {
        val rows = c.assistantList().filter { it.deletedAt == null }.sortedBy { it.sortOrder }
        val agents = try { c.acpListAgents() } catch (e: CancellationException) { throw e } catch (_: Exception) { emptyList() }
        state.update { it.copy(rows = rows, agents = agents, loading = false, error = null, supported = true) }
    }

    fun load() {
        if (state.value.busy || state.value.loading) return
        val c = client ?: return
        state.update { it.copy(loading = true, error = null) }
        operation = viewModelScope.launch {
            try { loadFrom(c) } catch (e: CancellationException) { throw e } catch (e: Exception) { failure(e) }
        }
    }

    fun edit(row: Assistant?) {
        if (state.value.busy) return
        probe?.cancel()
        state.update { it.copy(editing = true, editor = row, options = null, probing = false, optionsError = null, error = null) }
    }

    fun closeEditor() {
        if (state.value.busy) return
        probe?.cancel()
        state.update { it.copy(editing = false, options = null, probing = false, error = null) }
    }

    fun probeAgent(agent: AgentType) {
        val c = client ?: return
        probe?.cancel()
        state.update { it.copy(options = null, probing = true, optionsError = null) }
        probe = viewModelScope.launch {
            try {
                val options = c.describeAgentOptions(agent, null)
                state.update { it.copy(options = options, probing = false) }
            } catch (e: CancellationException) { throw e }
            catch (e: Exception) { state.update { it.copy(probing = false, optionsError = e.displayMessage(context)) } }
        }
    }

    fun save(draft: AssistantDraft) = mutate {
        val id = state.value.editor?.id
        val row = if (id == null) assistantCreate(draft) else assistantUpdate(id, draft)
        state.update { it.copy(rows = (it.rows.filterNot { old -> old.id == row.id } + row).sortedBy { row -> row.sortOrder }, editing = false) }
    }

    fun delete(row: Assistant) = mutate {
        assistantDelete(row.id)
        state.update { it.copy(rows = it.rows.filterNot { old -> old.id == row.id }) }
    }

    fun move(row: Assistant, delta: Int) = mutate {
        val ids = reorderedAssistantIds(state.value.rows.map { it.id }, row.id, delta)
        val rows = assistantReorder(ids).filter { it.deletedAt == null }.sortedBy { it.sortOrder }
        state.update { it.copy(rows = rows) }
    }

    private fun mutate(block: suspend CodegClient.() -> Unit) {
        val c = client ?: return
        if (state.value.busy || state.value.loading) return
        state.update { it.copy(busy = true, error = null) }
        operation = viewModelScope.launch {
            try { c.block() } catch (e: CancellationException) { throw e } catch (e: Exception) { failure(e) }
            finally { state.update { it.copy(busy = false) } }
        }
    }
}

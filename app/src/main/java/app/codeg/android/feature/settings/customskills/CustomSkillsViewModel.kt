package app.codeg.android.feature.settings.customskills

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.R
import app.codeg.android.core.data.ServerRepository
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.CustomSkillLinkOp
import app.codeg.android.core.network.ApiError
import app.codeg.android.core.network.CodegClient
import app.codeg.android.core.network.displayMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CustomSkillsUi(
    val supported: Boolean = false,
    val loading: Boolean = true,
    val busy: Boolean = false,
    val skills: List<CustomSkill> = emptyList(),
    val agents: List<AgentType> = emptyList(),
    val statuses: List<ExpertInstallStatus> = emptyList(),
    val error: String? = null,
)

@HiltViewModel
class CustomSkillsViewModel @Inject constructor(
    private val repository: ServerRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val state = MutableStateFlow(CustomSkillsUi())
    val ui = state.asStateFlow()
    private var client: CodegClient? = null

    private var refreshing = false

    init { refresh() }

    fun refresh() {
        if (state.value.busy || refreshing) return
        refreshing = true
        viewModelScope.launch {
            state.update { it.copy(loading = true, error = null) }
            try {
                val profile = repository.selectedProfile.first()
                    ?: error(context.getString(R.string.custom_skills_no_server))
                val c = repository.client(profile)
                    ?: error(context.getString(R.string.custom_skills_no_server))
                client = c
                reload(c)
            } catch (e: CancellationException) { throw e
            } catch (e: Exception) { report(e) }
            finally { refreshing = false; state.update { it.copy(loading = false) } }
        }
    }

    private suspend fun reload(c: CodegClient) {
        val skills = c.customSkillsList()
        val statuses = c.customSkillsStatuses()
        val agents = c.acpListAgents().filter { it.available && it.enabled }
            .sortedBy { it.sortOrder }.map { it.agentType }
        state.update { it.copy(supported = true, skills = skills, statuses = statuses, agents = agents) }
    }

    private fun report(e: Exception) {
        val unsupported = e is ApiError.Server && (e.status == 404 ||
            e.code?.contains("unknown", ignoreCase = true) == true ||
            e.serverMessage.contains("unknown command", ignoreCase = true) ||
            e.serverMessage.contains("unknown api", ignoreCase = true))
        state.update { it.copy(supported = if (unsupported) false else it.supported,
            error = if (unsupported) null else e.displayMessage(context)) }
    }

    private fun currentClient(): CodegClient = client ?: error(context.getString(R.string.custom_skills_no_server))

    suspend fun read(id: String): String = currentClient().customSkillRead(id)

    /** Return success for dialogs; a mutation and its refresh are separate outcomes. */
    private suspend fun mutate(action: suspend (CodegClient) -> Unit): Boolean {
        if (state.value.busy || state.value.loading) return false
        state.update { it.copy(busy = true, error = null) }
        try {
            val c = currentClient()
            action(c)
            try { reload(c) } catch (e: CancellationException) { throw e
            } catch (e: Exception) { report(e) }
            return true
        } catch (e: CancellationException) { throw e
        } catch (e: Exception) { report(e); return false
        } finally { state.update { it.copy(busy = false) } }
    }

    suspend fun save(id: String, content: String, create: Boolean): Boolean = mutate { c ->
        if (create) c.customSkillCreate(id, content) else c.customSkillSave(id, content)
    }

    suspend fun duplicate(sourceId: String, newId: String): Boolean = mutate { it.customSkillDuplicate(sourceId, newId) }

    fun delete(id: String) {
        viewModelScope.launch {
            mutate { c ->
                val results = c.customSkillsDelete(listOf(id))
                state.update { it.copy(error = results.filter { !it.ok }.joinToString("\n") { r ->
                    "${r.id}: ${r.error ?: context.getString(R.string.custom_skills_operation_failed)}"
                }.ifEmpty { null }) }
            }
        }
    }

    fun toggle(id: String, agent: AgentType, enable: Boolean) {
        val status = state.value.statuses.find { it.expertId == id && it.agentType == agent }
        if (enable && !canEnableCustomSkill(status?.state)) return
        viewModelScope.launch {
            mutate { c ->
                try {
                    val results = c.customSkillsApplyLinks(listOf(CustomSkillLinkOp(id, agent, enable)))
                    state.update { it.copy(error = results.filter { !it.ok }.joinToString("\n") { r ->
                        "${r.expertId} · ${r.agentType.displayName}: ${r.error ?: context.getString(R.string.custom_skills_operation_failed)}"
                    }.ifEmpty { null }) }
                } finally {
                    // Shared agent directories can change other cells too, even after partial failure.
                    val statuses = c.customSkillsStatuses()
                    state.update { it.copy(statuses = statuses) }
                }
            }
        }
    }

    suspend fun importCandidates(agent: AgentType): List<AgentSkillItem> {
        val result = currentClient().listAgentSkills(agent)
        check(result.supported) { result.message ?: context.getString(R.string.custom_skills_import_unsupported) }
        return importableCustomSkills(result.skills, state.value.skills.mapTo(mutableSetOf()) { it.id })
    }

    suspend fun importFromAgent(agent: AgentType, ids: List<String>): Boolean = mutate { c ->
        val results = c.customSkillsImport(agent, ids)
        state.update { it.copy(error = results.filter { !it.ok && !it.skipped }.joinToString("\n") { r ->
            "${r.id}: ${r.error ?: context.getString(R.string.custom_skills_operation_failed)}"
        }.ifEmpty { null }) }
    }
}

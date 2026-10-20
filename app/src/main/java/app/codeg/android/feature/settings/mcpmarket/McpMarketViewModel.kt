package app.codeg.android.feature.settings.mcpmarket

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.R
import app.codeg.android.core.data.ServerRepository
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.*
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

@HiltViewModel
class McpMarketViewModel @Inject constructor(
    private val repository: ServerRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _ui = MutableStateFlow(McpMarketUi())
    val ui = _ui.asStateFlow()
    private var client: CodegClient? = null
    private var searchJob: Job? = null
    private var detailJob: Job? = null

    init { load() }

    fun load() {
        viewModelScope.launch {
            _ui.update { it.copy(loading = true, error = null) }
            try {
                val profile = repository.selectedProfile.first() ?: error(context.getString(R.string.server_none_selected))
                val c = repository.client(profile) ?: error(context.getString(R.string.server_none_selected))
                client = c
                val providers = c.mcpListMarketplaces()
                _ui.update { it.copy(providers = providers, provider = providers.firstOrNull()?.id, loading = false, available = providers.isNotEmpty()) }
                search()
            } catch (e: Exception) { failure(e) }
        }
    }

    fun query(value: String) { _ui.update { it.copy(query = value, canLoadMore = false) } }
    fun provider(id: String) {
        detailJob?.cancel()
        _ui.update { it.copy(provider = id, detail = null, items = emptyList()) }
        search()
    }
    fun search(more: Boolean = false) {
        val state = _ui.value
        val provider = state.provider ?: return
        val c = client ?: return
        searchJob?.cancel()
        val limit = if (more) state.limit + 30 else 30
        searchJob = viewModelScope.launch {
            _ui.update { it.copy(loading = true, error = null, items = if (more) it.items else emptyList(), canLoadMore = false) }
            try {
                val items = c.mcpSearchMarketplace(McpMarketSearchBody(provider, state.query.trim(), limit))
                _ui.update { it.copy(items = items, limit = limit, canLoadMore = items.size >= limit, loading = false) }
            } catch (e: Exception) { failure(e) }
        }
    }
    fun detail(item: McpMarketItem) {
        detailJob?.cancel()
        detailJob = viewModelScope.launch {
            _ui.update { it.copy(loading = true, error = null) }
            try {
                val detail = client!!.mcpMarketplaceDetail(McpMarketDetailBody(item.provider_id, item.server_id))
                val option = detail.install_options.find { it.id == detail.default_option_id } ?: detail.install_options.firstOrNull()
                _ui.update { it.copy(loading = false, detail = detail, option = option, draft = defaults(option)) }
            } catch (e: Exception) { failure(e) }
        }
    }
    fun back() { detailJob?.cancel(); _ui.update { it.copy(detail = null, option = null, draft = emptyMap(), error = null) } }
    fun option(option: McpMarketOption) { _ui.update { it.copy(option = option, draft = defaults(option), error = null) } }
    fun field(key: String, value: String) { _ui.update { it.copy(draft = it.draft + (key to value), error = null) } }
    fun app(app: String) { _ui.update { it.copy(apps = if (app in it.apps) it.apps - app else it.apps + app) } }
    fun install(onInstalled: () -> Unit) {
        val state = _ui.value
        val detail = state.detail ?: return
        val option = state.option ?: return
        if (state.installing || state.apps.isEmpty()) return
        val values = try { parseMarketParameters(option.parameters, state.draft) }
        catch (e: MarketParameterException) {
            _ui.update { it.copy(error = context.getString(R.string.mcp_market_invalid, e.field)) }; return
        }
        viewModelScope.launch {
            _ui.update { it.copy(installing = true, error = null) }
            try {
                client!!.mcpInstallMarketplace(McpMarketInstallBody(detail.provider_id, detail.server_id, state.apps.toList(), option.id, option.protocol, values))
                _ui.update { it.copy(installing = false, detail = null, draft = emptyMap()) }
                onInstalled()
            } catch (e: Exception) { failure(e) }
        }
    }
    private fun failure(e: Exception) {
        if (e is CancellationException) throw e
        val unsupported = e is ApiError.Server && (e.status == 404 || e.code?.contains("unknown", true) == true || e.serverMessage.contains("unknown", true))
        _ui.update { it.copy(loading = false, installing = false, available = if (unsupported) false else true, error = if (unsupported) null else e.displayMessage(context)) }
    }
    private fun defaults(option: McpMarketOption?): Map<String, String> = option?.parameters.orEmpty().mapNotNull { field ->
        field.default_value?.takeUnless { it == kotlinx.serialization.json.JsonNull }?.let {
            field.key to ((it as? kotlinx.serialization.json.JsonPrimitive)?.content ?: it.toString())
        }
    }.toMap()
}

data class McpMarketUi(
    val available: Boolean = false, val loading: Boolean = false, val installing: Boolean = false,
    val providers: List<McpMarketProvider> = emptyList(), val provider: String? = null,
    val query: String = "", val items: List<McpMarketItem> = emptyList(), val limit: Int = 30,
    val canLoadMore: Boolean = false, val detail: McpMarketDetail? = null, val option: McpMarketOption? = null,
    val draft: Map<String, String> = emptyMap(), val apps: Set<String> = AgentType.entries.map { it.wire }.toSet(),
    val error: String? = null,
)

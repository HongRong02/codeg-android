package app.codeg.android.feature.serverlogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.core.data.ServerRepository
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.ServerTokenFilter
import app.codeg.android.core.network.ApiError
import app.codeg.android.core.network.CodegClient
import app.codeg.android.core.network.displayMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

internal fun tokenFilter(days: Int?, now: Instant = Instant.now(), zone: ZoneId = ZoneId.systemDefault()): ServerTokenFilter =
    ServerTokenFilter(
        start = days?.let { now.atZone(zone).toLocalDate().minusDays(it.toLong() - 1).atStartOfDay(zone).toInstant().toString() },
        end = now.toString(),
        tzOffsetMinutes = zone.rules.getOffset(now).totalSeconds / 60,
    )

internal fun filterLogText(text: String, query: String): String =
    if (query.isBlank()) text else text.lineSequence().filter { it.contains(query.trim(), ignoreCase = true) }.joinToString("\n")

internal fun isUnsupportedLogsApi(error: Exception): Boolean = error is ApiError.Server &&
    (error.status == 404 || error.code in setOf("unknown", "unknown_command", "unknown_method", "unknown_endpoint") ||
        error.serverMessage.contains("unknown command", ignoreCase = true) ||
        error.serverMessage.contains("unknown method", ignoreCase = true))

enum class LogsSection { RECENT, FILES, STATS, TOKENS }

data class ServerLogsUi(
    val loading: Boolean = false,
    val unsupported: Set<LogsSection> = emptySet(),
    val errors: Map<LogsSection, String> = emptyMap(),
    val recent: List<ServerLogRecord> = emptyList(),
    val files: List<ServerLogFile> = emptyList(),
    val stats: ServerConversationStats? = null,
    val report: ServerTokenReport? = null,
    val level: String? = null,
    val days: Int? = 7,
    val selectedFile: String? = null,
    val fileText: String? = null,
    val fileLoading: Boolean = false,
) {
    val available: Boolean get() = unsupported.size < LogsSection.entries.size
}

@HiltViewModel
class ServerLogsViewModel @Inject constructor(private val repository: ServerRepository) : ViewModel() {
    private val _ui = MutableStateFlow(ServerLogsUi())
    val ui = _ui.asStateFlow()
    private var client: CodegClient? = null
    private var loadJob: Job? = null
    private var fileJob: Job? = null

    init {
        viewModelScope.launch {
            repository.selectedProfile.collectLatest { profile ->
                loadJob?.cancel()
                fileJob?.cancel()
                client = null
                _ui.value = ServerLogsUi()
                if (profile != null) {
                    client = repository.client(profile)
                    load()
                }
            }
        }
    }

    fun setLevel(level: String?) { _ui.update { it.copy(level = level) }; load() }
    fun setDays(days: Int?) { _ui.update { it.copy(days = days, report = null) }; load() }

    fun load() {
        val c = client ?: return
        loadJob?.cancel()
        fileJob?.cancel()
        loadJob = viewModelScope.launch {
            _ui.update { it.copy(loading = true, errors = emptyMap(), fileLoading = false) }
            val snapshot = _ui.value
            request(LogsSection.RECENT) { val logs = c.recentServerLogs(snapshot.level); _ui.update { it.copy(recent = logs) } }
            request(LogsSection.FILES) { val files = c.serverLogFiles(); _ui.update { it.copy(files = files) } }
            request(LogsSection.STATS) { val stats = c.serverConversationStats(); _ui.update { it.copy(stats = stats) } }
            request(LogsSection.TOKENS) { val report = c.serverTokenReport(tokenFilter(snapshot.days)); _ui.update { it.copy(report = report) } }
            _ui.update { it.copy(loading = false) }
            _ui.value.selectedFile?.let { selectFile(it) }
        }
    }

    private suspend fun request(section: LogsSection, action: suspend () -> Unit) {
        try {
            action()
            _ui.update { it.copy(unsupported = it.unsupported - section, errors = it.errors - section) }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            _ui.update {
                if (isUnsupportedLogsApi(e)) it.copy(unsupported = it.unsupported + section, errors = it.errors - section)
                else it.copy(errors = it.errors + (section to e.displayMessage()))
            }
        }
    }

    fun selectFile(name: String?) {
        fileJob?.cancel()
        _ui.update { it.copy(selectedFile = name, fileText = null, fileLoading = name != null, errors = it.errors - LogsSection.FILES) }
        if (name == null) return
        val c = client ?: return
        fileJob = viewModelScope.launch {
            request(LogsSection.FILES) {
                val text = c.readServerLog(name)
                _ui.update { it.copy(fileText = text) }
            }
            _ui.update { it.copy(fileLoading = false) }
        }
    }
}

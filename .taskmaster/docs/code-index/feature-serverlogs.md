# feature/serverlogs

Source root: `app/src/main/java/app/codeg/android/`

## feature/serverlogs/ServerLogsScreen.kt

| Line | Declaration |
|---|---|
| 29 | `@Composable fun ServerLogsScreen(onBack: () -> Unit, viewModel: ServerLogsViewModel = hiltViewModel())` |
| 30 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 31 |   `val colors` |
| 32 |   `var tab by remember` |
| 33 |   `var query by remember(ui.selectedFile, tab)` |
| 34 |   `val tabs` |
| 159 | `@Composable private fun StatRow(label: String, value: Long)` |

## feature/serverlogs/ServerLogsViewModel.kt

| Line | Declaration |
|---|---|
| 20 | `internal fun tokenFilter(days: Int?, now: Instant = Instant.now(), zone: ZoneId = ZoneId.systemDefault()): ServerTokenFilter` |
| 27 | `internal fun filterLogText(text: String, query: String): String` |
| 30 | `internal fun isUnsupportedLogsApi(error: Exception): Boolean` |
| 35 | `enum class LogsSection` |
| 37 | `data class ServerLogsUi( val loading: Boolean = false, val unsupported: Set<LogsSection> = emptySet(), val errors: Map<LogsSection, String> = emptyMap(), val recent: List<ServerLogRecord> = emptyList(), val files: List<ServerLogFile> = emptyList(), val stats: ServerConversationStats? = null, val report: ServerTokenReport? = null, val level: String? = null, val days: Int? = 7, val selectedFile: String? = null, val fileText: String? = null, val fileLoading: Boolean = false, )` |
| 38 |   `val loading: Boolean` |
| 39 |   `val unsupported: Set<LogsSection>` |
| 40 |   `val errors: Map<LogsSection, String>` |
| 41 |   `val recent: List<ServerLogRecord>` |
| 42 |   `val files: List<ServerLogFile>` |
| 43 |   `val stats: ServerConversationStats?` |
| 44 |   `val report: ServerTokenReport?` |
| 45 |   `val level: String?` |
| 46 |   `val days: Int?` |
| 47 |   `val selectedFile: String?` |
| 48 |   `val fileText: String?` |
| 49 |   `val fileLoading: Boolean` |
| 51 |   `val available: Boolean get()` |
| 55 | `class ServerLogsViewModel @Inject constructor(private val repository: ServerRepository) : ViewModel()` |
| 56 |   `private val _ui` |
| 57 |   `val ui` |
| 58 |   `private var client: CodegClient?` |
| 59 |   `private var loadJob: Job?` |
| 60 |   `private var fileJob: Job?` |
| 77 |   `fun setLevel(level: String?)` |
| 78 |   `fun setDays(days: Int?)` |
| 80 |   `fun load()` |
| 96 |   `private suspend fun request(section: LogsSection, action: suspend () -> Unit)` |
| 110 |   `fun selectFile(name: String?)` |

# feature/automations

Source root: `app/src/main/java/app/codeg/android/`

## feature/automations/AutomationEditorSheet.kt

| Line | Declaration |
|---|---|
| 18 | `internal fun automationDraft( original: Automation?, name: String, folderId: Int, agent: AgentType, prompt: String, cron: String, timezone: String, scheduled: Boolean, ): AutomationDraft` |
| 22 |   `val config` |
| 25 |   `val originalPrompt` |
| 45 | `@Composable internal fun AutomationEditorSheet(ui: AutomationUiState, viewModel: AutomationViewModel)` |
| 46 |   `val original` |
| 47 |   `var name by remember` |
| 48 |   `var folderId by remember` |
| 49 |   `var agent by remember` |
| 50 |   `var prompt by remember` |
| 51 |   `var cron by remember` |
| 52 |   `var timezone by remember` |
| 53 |   `var scheduled by remember` |
| 85 | `@Composable private fun <T> Choice(label: String, items: List<Pair<T, String>>, selected: T?, onSelect: (T) -> Unit)` |
| 86 |   `var expanded by remember` |

## feature/automations/AutomationScreen.kt

| Line | Declaration |
|---|---|
| 80 | `@Composable fun AutomationScreen( onBack: () -> Unit, viewModel: AutomationViewModel = hiltViewModel(), )` |
| 84 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 85 |   `val colors` |
| 87 |   `var deleteTarget by remember` |
| 88 |   `var cancelTarget by remember` |
| 89 |   `val selected` |
| 187 | `@Composable private fun AutomationListView( ui: AutomationUiState, onFilterChanged: (AutomationFilter) -> Unit, onSelectAutomation: (Automation) -> Unit, onToggleEnabled: (Automation) -> Unit, )` |
| 193 |   `val colors` |
| 257 | `@Composable private fun AutomationListItem( automation: Automation, onClick: () -> Unit, onToggle: () -> Unit, )` |
| 262 |   `val colors` |
| 331 | `@Composable private fun AutomationDetailView( automation: Automation, runs: List<AutomationRun>, detailLoading: Boolean, onToggleEnabled: () -> Unit, onRefreshRuns: () -> Unit, actions: @Composable () -> Unit, onCancelRun: (AutomationRun) -> Unit, canCancel: Boolean, )` |
| 341 |   `val colors` |
| 531 | `@Composable private fun RunHistoryItem(run: AutomationRun)` |
| 532 |   `val colors` |
| 534 |   `val statusColor` |
| 541 |   `val statusLabel` |
| 603 | `@Composable private fun DetailRow( icon: ImageVector, label: String, value: String, )` |
| 608 |   `val colors` |
| 636 | `@Composable private fun formatAutomationSubtitle(automation: Automation): String` |
| 637 |   `val parts` |
| 651 | `private val dateTimeFormatter` |
| 654 | `private fun formatDateTime(instant: Instant): String` |

## feature/automations/AutomationViewModel.kt

| Line | Declaration |
|---|---|
| 21 | `enum class AutomationFilter` |
| 27 | `data class AutomationUiState( val editorOpen: Boolean = false, val editing: Automation? = null, val busy: Boolean = false, val folders: List<app.codeg.android.core.model.FolderDetail> = emptyList(), val preview: String? = null, val previewSchedule: Pair<String, String>? = null, val previewError: String? = null, val previewLoading: Boolean = false, val writesSupported: Boolean = true, val loading: Boolean = true, val refreshing: Boolean = false, val automations: List<Automation> = emptyList(), val filter: AutomationFilter = AutomationFilter.ALL, val selectedAutomationId: Int? = null, val detailLoading: Boolean = false, val runs: List<AutomationRun> = emptyList(), val error: String? = null, )` |
| 28 |   `val editorOpen: Boolean` |
| 29 |   `val editing: Automation?` |
| 30 |   `val busy: Boolean` |
| 31 |   `val folders: List<app.codeg.android.core.model.FolderDetail>` |
| 32 |   `val preview: String?` |
| 33 |   `val previewSchedule: Pair<String, String>?` |
| 34 |   `val previewError: String?` |
| 35 |   `val previewLoading: Boolean` |
| 36 |   `val writesSupported: Boolean` |
| 37 |   `val loading: Boolean` |
| 38 |   `val refreshing: Boolean` |
| 39 |   `val automations: List<Automation>` |
| 40 |   `val filter: AutomationFilter` |
| 41 |   `val selectedAutomationId: Int?` |
| 42 |   `val detailLoading: Boolean` |
| 43 |   `val runs: List<AutomationRun>` |
| 44 |   `val error: String?` |
| 46 |   `val selectedAutomation: Automation?` |
| 49 |   `val filteredAutomations: List<Automation>` |
| 58 | `class AutomationViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 60 |   `private val repository: ServerRepository` |
| 63 |   `private val _ui` |
| 64 |   `val ui: StateFlow<AutomationUiState>` |
| 66 |   `private var client: CodegClient?` |
| 72 |   `fun load(isInitial: Boolean = false)` |
| 120 |   `fun setFilter(filter: AutomationFilter)` |
| 124 |   `fun selectAutomation(automationId: Int?)` |
| 135 |   `fun loadRuns(automationId: Int)` |
| 155 |   `private var previewJob: kotlinx.coroutines.Job?` |
| 157 |   `fun openEditor(automation: Automation?)` |
| 165 |   `fun closeEditor()` |
| 171 |   `fun preview(cron: String, timezone: String)` |
| 187 |   `fun save(draft: app.codeg.android.core.model.wire.AutomationDraft)` |
| 195 |   `fun delete(automation: Automation)` |
| 201 |   `fun runNow(id: Int)` |
| 202 |   `fun cancelRun(id: Int)` |
| 207 |   `private fun unsupported(e: Exception): Boolean` |
| 215 |   `private fun write(action: suspend (CodegClient) -> Unit)` |
| 227 |   `fun toggleEnabled(automation: Automation)` |

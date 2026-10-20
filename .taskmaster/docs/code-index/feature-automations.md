# feature/automations

Source root: `app/src/main/java/app/codeg/android/`

## feature/automations/AutomationEditorSheet.kt

| Line | Declaration |
|---|---|
| 17 | `internal fun automationDraft( original: Automation?, name: String, folderId: Int, agent: AgentType, prompt: String, cron: String, timezone: String, scheduled: Boolean, ): AutomationDraft` |
| 21 |   `val config` |
| 24 |   `val originalPrompt` |
| 44 | `@Composable internal fun AutomationEditorSheet(ui: AutomationUiState, viewModel: AutomationViewModel)` |
| 45 |   `val original` |
| 46 |   `var name by remember` |
| 47 |   `var folderId by remember` |
| 48 |   `var agent by remember` |
| 49 |   `var prompt by remember` |
| 50 |   `var cron by remember` |
| 51 |   `var timezone by remember` |
| 52 |   `var scheduled by remember` |
| 86 | `@Composable private fun <T> Choice(label: String, items: List<Pair<T, String>>, selected: T?, onSelect: (T) -> Unit)` |

## feature/automations/AutomationScreen.kt

| Line | Declaration |
|---|---|
| 56 | `@Composable fun AutomationScreen( onBack: () -> Unit, viewModel: AutomationViewModel = hiltViewModel(), )` |
| 60 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 61 |   `val colors` |
| 63 |   `var deleteTarget by remember` |
| 64 |   `var cancelTarget by remember` |
| 65 |   `val selected` |
| 165 | `@Composable private fun AutomationListView( ui: AutomationUiState, onFilterChanged: (AutomationFilter) -> Unit, onSelectAutomation: (Automation) -> Unit, onToggleEnabled: (Automation) -> Unit, )` |
| 171 |   `val colors` |
| 235 | `@Composable private fun AutomationListItem( automation: Automation, onClick: () -> Unit, onToggle: () -> Unit, )` |
| 240 |   `val colors` |
| 274 | `@Composable private fun AutomationDetailView( automation: Automation, runs: List<AutomationRun>, detailLoading: Boolean, onToggleEnabled: () -> Unit, onRefreshRuns: () -> Unit, actions: @Composable () -> Unit, onCancelRun: (AutomationRun) -> Unit, canCancel: Boolean, )` |
| 284 |   `val colors` |
| 419 | `@Composable private fun RunHistoryItem(run: AutomationRun)` |
| 420 |   `val colors` |
| 422 |   `val statusLabel` |
| 477 | `@Composable private fun DetailRow( icon: ImageVector, label: String, value: String, )` |
| 482 |   `val colors` |
| 510 | `@Composable private fun formatAutomationSubtitle(automation: Automation): String` |
| 511 |   `val parts` |
| 525 | `private val dateTimeFormatter` |
| 528 | `private fun formatDateTime(instant: Instant): String` |

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

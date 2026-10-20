# feature/automations

Source root: `app/src/main/java/app/codeg/android/`

## feature/automations/AutomationEditorSheet.kt

| Line | Declaration |
|---|---|
| 18 | `internal fun automationDraft( original: Automation?, name: String, folderId: Int, agent: AgentType, prompt: String, cron: String, timezone: String, scheduled: Boolean, ): AutomationDraft` |
| 22 |   `val config` |
| 42 | `@Composable internal fun AutomationEditorSheet(ui: AutomationUiState, viewModel: AutomationViewModel)` |
| 43 |   `val original` |
| 44 |   `var name by remember` |
| 45 |   `var folderId by remember` |
| 46 |   `var agent by remember` |
| 47 |   `var prompt by remember` |
| 48 |   `var cron by remember` |
| 49 |   `var timezone by remember` |
| 50 |   `var scheduled by remember` |
| 82 | `@Composable private fun <T> Choice(label: String, items: List<Pair<T, String>>, selected: T?, onSelect: (T) -> Unit)` |
| 83 |   `var expanded by remember` |

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
| 27 | `data class AutomationUiState( val editorOpen: Boolean = false, val editing: Automation? = null, val busy: Boolean = false, val folders: List<app.codeg.android.core.model.FolderDetail> = emptyList(), val preview: String? = null, val previewError: String? = null, val previewLoading: Boolean = false, val writesSupported: Boolean = true, val loading: Boolean = true, val refreshing: Boolean = false, val automations: List<Automation> = emptyList(), val filter: AutomationFilter = AutomationFilter.ALL, val selectedAutomationId: Int? = null, val detailLoading: Boolean = false, val runs: List<AutomationRun> = emptyList(), val error: String? = null, )` |
| 28 |   `val editorOpen: Boolean` |
| 29 |   `val editing: Automation?` |
| 30 |   `val busy: Boolean` |
| 31 |   `val folders: List<app.codeg.android.core.model.FolderDetail>` |
| 32 |   `val preview: String?` |
| 33 |   `val previewError: String?` |
| 34 |   `val previewLoading: Boolean` |
| 35 |   `val writesSupported: Boolean` |
| 36 |   `val loading: Boolean` |
| 37 |   `val refreshing: Boolean` |
| 38 |   `val automations: List<Automation>` |
| 39 |   `val filter: AutomationFilter` |
| 40 |   `val selectedAutomationId: Int?` |
| 41 |   `val detailLoading: Boolean` |
| 42 |   `val runs: List<AutomationRun>` |
| 43 |   `val error: String?` |
| 45 |   `val selectedAutomation: Automation?` |
| 48 |   `val filteredAutomations: List<Automation>` |
| 57 | `class AutomationViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 59 |   `private val repository: ServerRepository` |
| 62 |   `private val _ui` |
| 63 |   `val ui: StateFlow<AutomationUiState>` |
| 65 |   `private var client: CodegClient?` |
| 71 |   `fun load(isInitial: Boolean = false)` |
| 119 |   `fun setFilter(filter: AutomationFilter)` |
| 123 |   `fun selectAutomation(automationId: Int?)` |
| 134 |   `fun loadRuns(automationId: Int)` |
| 154 |   `private var previewJob: kotlinx.coroutines.Job?` |
| 156 |   `fun openEditor(automation: Automation?)` |
| 164 |   `fun closeEditor()` |
| 170 |   `fun preview(cron: String, timezone: String)` |
| 186 |   `fun save(draft: app.codeg.android.core.model.wire.AutomationDraft)` |
| 194 |   `fun delete(automation: Automation)` |
| 200 |   `fun runNow(id: Int)` |
| 201 |   `fun cancelRun(id: Int)` |
| 206 |   `private fun unsupported(e: Exception): Boolean` |
| 213 |   `private fun write(action: suspend (CodegClient) -> Unit)` |
| 225 |   `fun toggleEnabled(automation: Automation)` |

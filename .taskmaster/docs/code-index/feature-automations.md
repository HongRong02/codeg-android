# feature/automations

Source root: `app/src/main/java/app/codeg/android/`

## feature/automations/AutomationScreen.kt

| Line | Declaration |
|---|---|
| 76 | `@Composable fun AutomationScreen( onBack: () -> Unit, viewModel: AutomationViewModel = hiltViewModel(), )` |
| 80 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 81 |   `val colors` |
| 83 |   `val selected` |
| 153 | `@Composable private fun AutomationListView( ui: AutomationUiState, onFilterChanged: (AutomationFilter) -> Unit, onSelectAutomation: (Automation) -> Unit, onToggleEnabled: (Automation) -> Unit, )` |
| 159 |   `val colors` |
| 223 | `@Composable private fun AutomationListItem( automation: Automation, onClick: () -> Unit, onToggle: () -> Unit, )` |
| 228 |   `val colors` |
| 297 | `@Composable private fun AutomationDetailView( automation: Automation, runs: List<AutomationRun>, detailLoading: Boolean, onToggleEnabled: () -> Unit, onRefreshRuns: () -> Unit, )` |
| 304 |   `val colors` |
| 490 | `@Composable private fun RunHistoryItem(run: AutomationRun)` |
| 491 |   `val colors` |
| 493 |   `val statusColor` |
| 500 |   `val statusLabel` |
| 562 | `@Composable private fun DetailRow( icon: ImageVector, label: String, value: String, )` |
| 567 |   `val colors` |
| 595 | `@Composable private fun formatAutomationSubtitle(automation: Automation): String` |
| 596 |   `val parts` |
| 610 | `private val dateTimeFormatter` |
| 613 | `private fun formatDateTime(instant: Instant): String` |

## feature/automations/AutomationViewModel.kt

| Line | Declaration |
|---|---|
| 21 | `enum class AutomationFilter` |
| 27 | `data class AutomationUiState( val loading: Boolean = true, val refreshing: Boolean = false, val automations: List<Automation> = emptyList(), val filter: AutomationFilter = AutomationFilter.ALL, val selectedAutomationId: Int? = null, val detailLoading: Boolean = false, val runs: List<AutomationRun> = emptyList(), val error: String? = null, )` |
| 28 |   `val loading: Boolean` |
| 29 |   `val refreshing: Boolean` |
| 30 |   `val automations: List<Automation>` |
| 31 |   `val filter: AutomationFilter` |
| 32 |   `val selectedAutomationId: Int?` |
| 33 |   `val detailLoading: Boolean` |
| 34 |   `val runs: List<AutomationRun>` |
| 35 |   `val error: String?` |
| 37 |   `val selectedAutomation: Automation?` |
| 40 |   `val filteredAutomations: List<Automation>` |
| 49 | `class AutomationViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 51 |   `private val repository: ServerRepository` |
| 54 |   `private val _ui` |
| 55 |   `val ui: StateFlow<AutomationUiState>` |
| 57 |   `private var client: CodegClient?` |
| 63 |   `fun load(isInitial: Boolean = false)` |
| 111 |   `fun setFilter(filter: AutomationFilter)` |
| 115 |   `fun selectAutomation(automationId: Int?)` |
| 122 |   `fun loadRuns(automationId: Int)` |
| 142 |   `fun toggleEnabled(automation: Automation)` |

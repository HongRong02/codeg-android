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
| 19 | `enum class AutomationFilter` |
| 25 | `data class AutomationUiState( val loading: Boolean = true, val refreshing: Boolean = false, val automations: List<Automation> = emptyList(), val filter: AutomationFilter = AutomationFilter.ALL, val selectedAutomationId: Int? = null, val detailLoading: Boolean = false, val runs: List<AutomationRun> = emptyList(), val error: String? = null, )` |
| 26 |   `val loading: Boolean` |
| 27 |   `val refreshing: Boolean` |
| 28 |   `val automations: List<Automation>` |
| 29 |   `val filter: AutomationFilter` |
| 30 |   `val selectedAutomationId: Int?` |
| 31 |   `val detailLoading: Boolean` |
| 32 |   `val runs: List<AutomationRun>` |
| 33 |   `val error: String?` |
| 35 |   `val selectedAutomation: Automation?` |
| 38 |   `val filteredAutomations: List<Automation>` |
| 47 | `class AutomationViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 48 |   `private val repository: ServerRepository` |
| 51 |   `private val _ui` |
| 52 |   `val ui: StateFlow<AutomationUiState>` |
| 54 |   `private var client: CodegClient?` |
| 60 |   `fun load(isInitial: Boolean = false)` |
| 108 |   `fun setFilter(filter: AutomationFilter)` |
| 112 |   `fun selectAutomation(automationId: Int?)` |
| 119 |   `fun loadRuns(automationId: Int)` |
| 139 |   `fun toggleEnabled(automation: Automation)` |

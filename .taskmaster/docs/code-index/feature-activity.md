# feature/activity

Source root: `app/src/main/java/app/codeg/android/`

## feature/activity/ActivityScreen.kt

| Line | Declaration |
|---|---|
| 58 | `@Composable fun ActivityScreen( onOpenConversation: (Int) -> Unit, viewModel: ActivityViewModel = hiltViewModel(), )` |
| 62 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 63 |   `val sections by viewModel.sections.collectAsStateWithLifecycle()` |
| 64 |   `var collapsed by rememberSaveable` |
| 65 |   `val colors` |
| 173 | `private data class ActivityStyle(val icon: ImageVector, val tint: Color, val label: String)` |
| 176 | `@Composable private fun activitySectionStyle(kind: ActivityKind): ActivityStyle` |
| 177 |   `val colors` |
| 194 | `@Composable private fun UpdatedFooter(timestamp: Instant)` |
| 195 |   `val relative` |

## feature/activity/ActivityViewModel.kt

| Line | Declaration |
|---|---|
| 37 | `class ActivityViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 38 |   `private val repository: ServerRepository` |
| 41 |   `private val _ui` |
| 42 |   `val ui: StateFlow<ActivityUiState>` |
| 50 |   `val sections: StateFlow<List<ActivitySection>>` |
| 58 |   `private data class ActivityInput( val conversations: List<ConversationSummary>, val folderNames: Map<Int, String>, )` |
| 63 |   `private var client: CodegClient?` |
| 64 |   `private var pollJob: Job?` |
| 72 |   `private suspend fun onProfile(profile: ServerProfile?)` |
| 89 |   `fun refresh()` |
| 94 |   `private suspend fun fetch(manual: Boolean = false)` |
| 112 | `data class ActivityUiState( val conversations: List<ConversationSummary> = emptyList(), val folderNames: Map<Int, String> = emptyMap(), val loading: Boolean = false, val refreshing: Boolean = false, val hasLoaded: Boolean = false, val error: String? = null, val lastRefreshed: Instant? = null, )` |
| 113 |   `val conversations: List<ConversationSummary>` |
| 114 |   `val folderNames: Map<Int, String>` |
| 115 |   `val loading: Boolean` |
| 116 |   `val refreshing: Boolean` |
| 117 |   `val hasLoaded: Boolean` |
| 118 |   `val error: String?` |
| 119 |   `val lastRefreshed: Instant?` |
| 126 |   `val isEmptyFeed: Boolean` |
| 134 | `enum class ActivityKind` |
| 137 | `data class ActivitySection( val id: String, val kind: ActivityKind, val rows: List<SessionRowItem>, )` |
| 138 |   `val id: String` |
| 139 |   `val kind: ActivityKind` |
| 140 |   `val rows: List<SessionRowItem>` |
| 142 |   `val count: Int get()` |
| 151 | `fun buildActivitySections( conversations: List<ConversationSummary>, folderNames: Map<Int, String>, ): List<ActivitySection>` |
| 155 |   `fun row(c: ConversationSummary)` |
| 157 |   `val running` |
| 159 |   `val cutoff` |
| 160 |   `val recent` |
| 163 |   `val out` |

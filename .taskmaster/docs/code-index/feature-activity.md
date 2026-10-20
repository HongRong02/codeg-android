# feature/activity

Source root: `app/src/main/java/app/codeg/android/`

## feature/activity/ActivityScreen.kt

| Line | Declaration |
|---|---|
| 57 | `@Composable fun ActivityScreen( onOpenConversation: (Int) -> Unit, viewModel: ActivityViewModel = hiltViewModel(), )` |
| 61 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 62 |   `val sections by viewModel.sections.collectAsStateWithLifecycle()` |
| 63 |   `var collapsed by rememberSaveable` |
| 64 |   `val colors` |
| 172 | `private data class ActivityStyle(val icon: ImageVector, val tint: Color, val label: String)` |
| 175 | `@Composable private fun activitySectionStyle(kind: ActivityKind): ActivityStyle` |
| 176 |   `val colors` |
| 193 | `@Composable private fun UpdatedFooter(timestamp: Instant)` |
| 194 |   `val relative` |

## feature/activity/ActivityViewModel.kt

| Line | Declaration |
|---|---|
| 39 | `class ActivityViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 41 |   `private val repository: ServerRepository` |
| 44 |   `private val _ui` |
| 45 |   `val ui: StateFlow<ActivityUiState>` |
| 53 |   `val sections: StateFlow<List<ActivitySection>>` |
| 61 |   `private data class ActivityInput( val conversations: List<ConversationSummary>, val folderNames: Map<Int, String>, )` |
| 66 |   `private var client: CodegClient?` |
| 67 |   `private var pollJob: Job?` |
| 75 |   `private suspend fun onProfile(profile: ServerProfile?)` |
| 92 |   `fun refresh()` |
| 97 |   `private suspend fun fetch(manual: Boolean = false)` |
| 115 | `data class ActivityUiState( val conversations: List<ConversationSummary> = emptyList(), val folderNames: Map<Int, String> = emptyMap(), val loading: Boolean = false, val refreshing: Boolean = false, val hasLoaded: Boolean = false, val error: String? = null, val lastRefreshed: Instant? = null, )` |
| 116 |   `val conversations: List<ConversationSummary>` |
| 117 |   `val folderNames: Map<Int, String>` |
| 118 |   `val loading: Boolean` |
| 119 |   `val refreshing: Boolean` |
| 120 |   `val hasLoaded: Boolean` |
| 121 |   `val error: String?` |
| 122 |   `val lastRefreshed: Instant?` |
| 129 |   `val isEmptyFeed: Boolean` |
| 137 | `enum class ActivityKind` |
| 140 | `data class ActivitySection( val id: String, val kind: ActivityKind, val rows: List<SessionRowItem>, )` |
| 141 |   `val id: String` |
| 142 |   `val kind: ActivityKind` |
| 143 |   `val rows: List<SessionRowItem>` |
| 145 |   `val count: Int get()` |
| 154 | `fun buildActivitySections( conversations: List<ConversationSummary>, folderNames: Map<Int, String>, ): List<ActivitySection>` |
| 158 |   `fun row(c: ConversationSummary)` |
| 160 |   `val running` |
| 162 |   `val cutoff` |
| 163 |   `val recent` |
| 166 |   `val out` |

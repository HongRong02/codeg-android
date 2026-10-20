# feature/search

Source root: `app/src/main/java/app/codeg/android/`

## feature/search/SearchScreen.kt

| Line | Declaration |
|---|---|
| 41 | `@Composable fun SearchScreen( onOpenConversation: (Int) -> Unit, viewModel: SearchViewModel = hiltViewModel(), )` |
| 45 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 46 |   `val query by viewModel.query.collectAsStateWithLifecycle()` |
| 47 |   `val recent by viewModel.recent.collectAsStateWithLifecycle()` |
| 48 |   `val colors` |
| 49 |   `val web` |
| 146 | `@Composable private fun RecentList( recent: List<String>, onUse: (String) -> Unit, onClear: () -> Unit, modifier: Modifier = Modifier, )` |
| 152 |   `val colors` |

## feature/search/SearchViewModel.kt

| Line | Declaration |
|---|---|
| 29 | `class SearchViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, private val recentStore: RecentSearchesStore, ) : ViewModel()` |
| 31 |   `private val repository: ServerRepository` |
| 32 |   `private val recentStore: RecentSearchesStore` |
| 35 |   `private val _query` |
| 36 |   `val query: StateFlow<String>` |
| 38 |   `private val _ui` |
| 39 |   `val ui: StateFlow<SearchUiState>` |
| 41 |   `val recent: StateFlow<List<String>>` |
| 44 |   `private var client: CodegClient?` |
| 45 |   `private var folderNames: Map<Int, String>` |
| 60 |   `fun onQueryChange(value: String)` |
| 62 |   `fun submit()` |
| 67 |   `fun useRecent(term: String)` |
| 69 |   `fun clearRecent()` |
| 72 |   `fun retry()` |
| 74 |   `private suspend fun runSearch(raw: String)` |
| 94 | `data class SearchUiState( val query: String = "", val results: List<ConversationSummary> = emptyList(), val folderNames: Map<Int, String> = emptyMap(), val searching: Boolean = false, val error: String? = null, )` |
| 95 |   `val query: String` |
| 96 |   `val results: List<ConversationSummary>` |
| 97 |   `val folderNames: Map<Int, String>` |
| 98 |   `val searching: Boolean` |
| 99 |   `val error: String?` |

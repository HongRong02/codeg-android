# feature/search

Source root: `app/src/main/java/app/codeg/android/`

## feature/search/SearchScreen.kt

| Line | Declaration |
|---|---|
| 54 | `@Composable fun SearchScreen( onOpenConversation: (Int) -> Unit, viewModel: SearchViewModel = hiltViewModel(), )` |
| 58 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 59 |   `val query by viewModel.query.collectAsStateWithLifecycle()` |
| 60 |   `val recent by viewModel.recent.collectAsStateWithLifecycle()` |
| 61 |   `val colors` |
| 145 | `@Composable private fun RecentList(recent: List<String>, onUse: (String) -> Unit, onClear: () -> Unit)` |
| 146 |   `val colors` |

## feature/search/SearchViewModel.kt

| Line | Declaration |
|---|---|
| 27 | `class SearchViewModel @Inject constructor( private val repository: ServerRepository, private val recentStore: RecentSearchesStore, ) : ViewModel()` |
| 28 |   `private val repository: ServerRepository` |
| 29 |   `private val recentStore: RecentSearchesStore` |
| 32 |   `private val _query` |
| 33 |   `val query: StateFlow<String>` |
| 35 |   `private val _ui` |
| 36 |   `val ui: StateFlow<SearchUiState>` |
| 38 |   `val recent: StateFlow<List<String>>` |
| 41 |   `private var client: CodegClient?` |
| 42 |   `private var folderNames: Map<Int, String>` |
| 57 |   `fun onQueryChange(value: String)` |
| 59 |   `fun submit()` |
| 64 |   `fun useRecent(term: String)` |
| 66 |   `fun clearRecent()` |
| 69 |   `fun retry()` |
| 71 |   `private suspend fun runSearch(raw: String)` |
| 91 | `data class SearchUiState( val query: String = "", val results: List<ConversationSummary> = emptyList(), val folderNames: Map<Int, String> = emptyMap(), val searching: Boolean = false, val error: String? = null, )` |
| 92 |   `val query: String` |
| 93 |   `val results: List<ConversationSummary>` |
| 94 |   `val folderNames: Map<Int, String>` |
| 95 |   `val searching: Boolean` |
| 96 |   `val error: String?` |

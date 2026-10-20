# feature/sessions

Source root: `app/src/main/java/app/codeg/android/`

## feature/sessions/SectionHeader.kt

| Line | Declaration |
|---|---|
| 34 | `@Composable fun CollapsibleSectionHeader( icon: ImageVector, tint: Color, label: String, count: Int, collapsed: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier, collapsible: Boolean = true, )` |
| 44 |   `val colors` |
| 45 |   `val rotation by animateFloatAsState( targetValue = if (collapsed) 0f else 180f, label = "section-chevron", )` |
| 49 |   `val toggleLabel` |

## feature/sessions/SessionListScreen.kt

| Line | Declaration |
|---|---|
| 82 | `@Composable fun SessionListScreen( servers: List<ServerProfile>, selectedId: String?, onSelectServer: (String) -> Unit, onManageServers: () -> Unit, onOpenConversation: (Int) -> Unit, onNewTask: () -> Unit, viewModel: SessionListViewModel = hiltViewModel(), )` |
| 91 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 92 |   `val sections by viewModel.sections.collectAsStateWithLifecycle()` |
| 96 |   `var collapsed by rememberSaveable` |
| 97 |   `val colors` |
| 98 |   `val selectedName` |
| 201 | `@Composable private fun SessionList( sections: List<SessionSection>, collapsed: Set<String>, onToggleSection: (String) -> Unit, onOpenConversation: (Int) -> Unit, onTogglePin: (ConversationSummary) -> Unit, refreshError: String?, onRetry: () -> Unit, onDismissError: () -> Unit, )` |
| 266 | `private data class SectionStyle(val icon: ImageVector, val tint: Color, val label: String)` |
| 269 | `@Composable private fun sectionStyle(kind: SectionKind): SectionStyle` |
| 270 |   `val colors` |
| 297 | `@Composable private fun RefreshErrorBanner( message: String, onRetry: () -> Unit, onDismiss: () -> Unit, modifier: Modifier = Modifier, )` |
| 303 |   `val colors` |
| 304 |   `val shape` |
| 347 | `@Composable private fun ServerSwitcher( name: String, servers: List<ServerProfile>, selectedId: String?, onSelect: (String) -> Unit, onManageServers: () -> Unit, )` |
| 354 |   `var open by remember` |
| 355 |   `val colors` |

## feature/sessions/SessionListViewModel.kt

| Line | Declaration |
|---|---|
| 44 | `class SessionListViewModel @Inject constructor( private val repository: ServerRepository, @ApplicationContext private val appContext: Context, ) : ViewModel()` |
| 45 |   `private val repository: ServerRepository` |
| 49 |   `private val _ui` |
| 50 |   `val ui: StateFlow<SessionListUiState>` |
| 60 |   `val sections: StateFlow<List<SessionSection>>` |
| 69 |   `private data class GroupingInput( val folders: List<FolderDetail>, val conversations: List<ConversationSummary>, )` |
| 74 |   `private var client: CodegClient?` |
| 75 |   `private var loadedEndpoint: String?` |
| 76 |   `private var fetchGeneration` |
| 88 |   `private suspend fun onProfile(profile: ServerProfile?)` |
| 108 |   `fun refresh()` |
| 113 |   `private suspend fun fetch(isInitial: Boolean, force: Boolean = false)` |
| 153 |   `fun setPinned(conversation: ConversationSummary, pinned: Boolean)` |
| 172 |   `fun dismissError()` |
| 176 | `data class SessionListUiState( val folders: List<FolderDetail> = emptyList(), val conversations: List<ConversationSummary> = emptyList(), val isLoading: Boolean = false, val isRefreshing: Boolean = false, val hasLoaded: Boolean = false, val error: String? = null, )` |
| 177 |   `val folders: List<FolderDetail>` |
| 178 |   `val conversations: List<ConversationSummary>` |
| 179 |   `val isLoading: Boolean` |
| 180 |   `val isRefreshing: Boolean` |
| 181 |   `val hasLoaded: Boolean` |
| 182 |   `val error: String?` |
| 184 |   `val isBusy: Boolean get()` |
| 193 |   `val isEmpty: Boolean get()` |
| 197 | `data class FolderGroup( val folder: FolderDetail, val conversations: List<ConversationSummary>, )` |
| 198 |   `val folder: FolderDetail` |
| 199 |   `val conversations: List<ConversationSummary>` |
| 208 | `object SessionGrouping` |
| 210 |   `fun sortedFolders(folders: List<FolderDetail>): List<FolderDetail>` |
| 216 |   `fun folderNames(folders: List<FolderDetail>): Map<Int, String>` |
| 223 |   `fun pinned(conversations: List<ConversationSummary>, search: String = ""): List<ConversationSummary>` |
| 232 |   `fun folderGroups( folders: List<FolderDetail>, conversations: List<ConversationSummary>, search: String = "", ): List<FolderGroup>` |
| 247 |   `fun ungrouped( folders: List<FolderDetail>, conversations: List<ConversationSummary>, search: String = "", ): List<ConversationSummary>` |
| 259 |   `private fun matching(convs: List<ConversationSummary>, search: String): List<ConversationSummary>` |

## feature/sessions/SessionRow.kt

| Line | Declaration |
|---|---|
| 48 | `@Composable fun SessionRow( conversation: ConversationSummary, onClick: () -> Unit, modifier: Modifier = Modifier, folderName: String? = null, selected: Boolean = false, onTogglePin: (() -> Unit)? = null, )` |
| 56 |   `val colors` |
| 57 |   `var menuOpen by remember` |

## feature/sessions/SessionSections.kt

| Line | Declaration |
|---|---|
| 16 | `data class SessionSection( /** Stable id used for the collapse set and the header's LazyColumn key. */ val id: String, val kind: SectionKind, val rows: List<SessionRowItem>, )` |
| 18 |   `val id: String` |
| 19 |   `val kind: SectionKind` |
| 20 |   `val rows: List<SessionRowItem>` |
| 22 |   `val count: Int get()` |
| 26 | `sealed interface SectionKind` |
| 28 |   `data object Pinned : SectionKind` |
| 31 |   `data class Folder(val name: String, val colorHex: String) : SectionKind` |
| 34 |   `data object Other : SectionKind` |
| 38 | `data class SessionRowItem( val conversation: ConversationSummary, /** Dim folder tag shown on cross-folder rows (Pinned/Other); `null` inside a * folder group, where the folder is already the header. */ val folderName: String?, )` |
| 39 |   `val conversation: ConversationSummary` |
| 42 |   `val folderName: String?` |
| 55 | `fun buildSessionSections( folders: List<FolderDetail>, conversations: List<ConversationSummary>, ): List<SessionSection>` |
| 59 |   `val folderNames` |
| 60 |   `val out` |
| 62 |   `val pinned` |
| 79 |   `val other` |

# feature/sessions

Source root: `app/src/main/java/app/codeg/android/`

## feature/sessions/SectionHeader.kt

| Line | Declaration |
|---|---|
| 16 | `@Composable fun CollapsibleSectionHeader( icon: ImageVector, tint: Color, label: String, count: Int, collapsed: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier, collapsible: Boolean = true, )` |

## feature/sessions/SessionListScreen.kt

| Line | Declaration |
|---|---|
| 78 | `@Composable fun SessionListScreen( servers: List<ServerProfile>, selectedId: String?, onSelectServer: (String) -> Unit, onManageServers: () -> Unit, onOpenConversation: (Int) -> Unit, onNewTask: () -> Unit, viewModel: SessionListViewModel = hiltViewModel(), )` |
| 87 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 88 |   `val sections by viewModel.sections.collectAsStateWithLifecycle()` |
| 89 |   `var tagSheetOpen by remember(selectedId)` |
| 90 |   `var tagConversation by remember(selectedId)` |
| 98 |   `var collapsed by rememberSaveable` |
| 99 |   `val toaster` |
| 100 |   `val errorTitle` |
| 107 |   `val colors` |
| 108 |   `val selectedName` |
| 221 | `@Composable private fun SessionList( tags: List<ConversationTag>, onEditTags: ((ConversationSummary) -> Unit)?, sections: List<SessionSection>, collapsed: Set<String>, onToggleSection: (String) -> Unit, onOpenConversation: (Int) -> Unit, onTogglePin: (ConversationSummary) -> Unit, refreshError: String?, onRetry: () -> Unit, onDismissError: () -> Unit, )` |
| 290 | `private data class SectionStyle(val icon: ImageVector, val tint: Color, val label: String)` |
| 293 | `@Composable private fun sectionStyle(kind: SectionKind): SectionStyle` |
| 294 |   `val colors` |
| 321 | `@Composable private fun RefreshErrorBanner( message: String, onRetry: () -> Unit, onDismiss: () -> Unit, modifier: Modifier = Modifier, )` |
| 327 |   `val colors` |
| 328 |   `val shape` |
| 370 | `@Composable private fun ServerSwitcher( name: String, servers: List<ServerProfile>, selectedId: String?, onSelect: (String) -> Unit, onManageServers: () -> Unit, )` |
| 377 |   `var open by remember` |
| 378 |   `val colors` |

## feature/sessions/SessionListViewModel.kt

| Line | Declaration |
|---|---|
| 48 | `class SessionListViewModel @Inject constructor( private val repository: ServerRepository, @ApplicationContext private val appContext: Context, ) : ViewModel()` |
| 49 |   `private val repository: ServerRepository` |
| 53 |   `private val _ui` |
| 54 |   `val ui: StateFlow<SessionListUiState>` |
| 64 |   `val sections: StateFlow<List<SessionSection>>` |
| 73 |   `private data class GroupingInput( val folders: List<FolderDetail>, val conversations: List<ConversationSummary>, )` |
| 78 |   `private var client: CodegClient?` |
| 79 |   `private var loadedEndpoint: String?` |
| 80 |   `private var fetchGeneration` |
| 92 |   `private suspend fun onProfile(profile: ServerProfile?)` |
| 113 |   `fun refresh()` |
| 118 |   `private suspend fun fetch(isInitial: Boolean, force: Boolean = false)` |
| 162 |   `fun setPinned(conversation: ConversationSummary, pinned: Boolean)` |
| 181 |   `private suspend fun loadTags(active: CodegClient): List<ConversationTag>?` |
| 188 |   `fun toggleTagFilter(id: Int)` |
| 192 |   `fun clearTagFilter()` |
| 193 |   `fun setMatchAllTags(value: Boolean)` |
| 195 |   `fun saveConversationTags(conversationId: Int, selected: Set<Int>, onSaved: () -> Unit)` |
| 204 |   `fun saveTag(tag: ConversationTag?, name: String, color: String, onSaved: () -> Unit)` |
| 212 |   `fun deleteTag(tag: ConversationTag, onDeleted: () -> Unit)` |
| 223 |   `private fun tagAction(action: suspend (CodegClient) -> Unit)` |
| 238 |   `fun dismissError()` |
| 242 | `data class SessionListUiState( val tags: List<ConversationTag> = emptyList(), val tagsSupported: Boolean = false, val selectedTags: Set<Int> = emptySet(), val matchAllTags: Boolean = false, val tagsBusy: Boolean = false, val folders: List<FolderDetail> = emptyList(), val conversations: List<ConversationSummary> = emptyList(), val isLoading: Boolean = false, val isRefreshing: Boolean = false, val hasLoaded: Boolean = false, val error: String? = null, )` |
| 243 |   `val tags: List<ConversationTag>` |
| 244 |   `val tagsSupported: Boolean` |
| 245 |   `val selectedTags: Set<Int>` |
| 246 |   `val matchAllTags: Boolean` |
| 247 |   `val tagsBusy: Boolean` |
| 248 |   `val folders: List<FolderDetail>` |
| 249 |   `val conversations: List<ConversationSummary>` |
| 250 |   `val isLoading: Boolean` |
| 251 |   `val isRefreshing: Boolean` |
| 252 |   `val hasLoaded: Boolean` |
| 253 |   `val error: String?` |
| 255 |   `val isBusy: Boolean get()` |
| 264 |   `val isEmpty: Boolean get()` |
| 268 | `data class FolderGroup( val folder: FolderDetail, val conversations: List<ConversationSummary>, )` |
| 269 |   `val folder: FolderDetail` |
| 270 |   `val conversations: List<ConversationSummary>` |
| 279 | `object SessionGrouping` |
| 281 |   `fun sortedFolders(folders: List<FolderDetail>): List<FolderDetail>` |
| 287 |   `fun folderNames(folders: List<FolderDetail>): Map<Int, String>` |
| 294 |   `fun pinned(conversations: List<ConversationSummary>, search: String = ""): List<ConversationSummary>` |
| 303 |   `fun folderGroups( folders: List<FolderDetail>, conversations: List<ConversationSummary>, search: String = "", ): List<FolderGroup>` |
| 318 |   `fun ungrouped( folders: List<FolderDetail>, conversations: List<ConversationSummary>, search: String = "", ): List<ConversationSummary>` |
| 330 |   `private fun matching(convs: List<ConversationSummary>, search: String): List<ConversationSummary>` |

## feature/sessions/SessionRow.kt

| Line | Declaration |
|---|---|
| 46 | `@Composable fun SessionRow( conversation: ConversationSummary, onClick: () -> Unit, modifier: Modifier = Modifier, folderName: String? = null, selected: Boolean = false, onTogglePin: (() -> Unit)? = null, tags: List<ConversationTag> = emptyList(), onEditTags: (() -> Unit)? = null, )` |
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

## feature/sessions/tags/ConversationTagLogic.kt

| Line | Declaration |
|---|---|
| 4 | `internal fun matchesTagFilter(ids: List<Int>?, selected: Set<Int>, matchAll: Boolean): Boolean` |
| 8 | `internal fun tagDelta(previous: List<Int>?, selected: Set<Int>): Pair<List<Int>, List<Int>>` |

## feature/sessions/tags/ConversationTagUi.kt

| Line | Declaration |
|---|---|
| 39 | `@Composable fun ConversationTagChip(tag: ConversationTag)` |
| 40 |   `val color` |
| 50 | `@Composable fun ConversationTagFilter(ui: SessionListUiState, viewModel: SessionListViewModel, onManage: () -> Unit)` |
| 71 | `@Composable fun ConversationTagSheet( conversation: ConversationSummary?, ui: SessionListUiState, viewModel: SessionListViewModel, onDismiss: () -> Unit, )` |
| 78 |   `var selected by remember(conversation?.id)` |
| 79 |   `var editing by remember` |
| 80 |   `var formOpen by remember` |
| 81 |   `var deleting by remember` |
| 83 |   `val folder` |
| 84 |   `val rootId` |
| 85 |   `val visible` |
| 143 | `@Composable private fun TagForm(tag: ConversationTag?, busy: Boolean, onDismiss: () -> Unit, onSave: (String, String) -> Unit)` |
| 144 |   `var name by remember(tag?.id)` |
| 145 |   `var color by remember(tag?.id)` |
| 146 |   `val valid` |

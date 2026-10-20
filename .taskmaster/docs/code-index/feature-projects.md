# feature/projects

Source root: `app/src/main/java/app/codeg/android/`

## feature/projects/BranchPickerSheet.kt

| Line | Declaration |
|---|---|
| 57 | `@Composable fun BranchPickerSheet(viewModel: ProjectDetailViewModel, onDismiss: () -> Unit)` |
| 58 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 59 |   `val colors` |
| 60 |   `val sheetState` |
| 61 |   `var loading by remember` |
| 62 |   `var locals by remember` |
| 63 |   `var remotes by remember` |
| 64 |   `var newBranch by remember` |
| 65 |   `var busy by remember` |
| 66 |   `var error by remember` |
| 75 |   `fun doCheckout(branch: String)` |
| 115 | `@Composable private fun SectionLabel(text: String)` |
| 120 | `@Composable private fun BranchRow(branch: String, current: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 121 |   `val colors` |

## feature/projects/CloneRepoSheet.kt

| Line | Declaration |
|---|---|
| 58 | `@Composable fun CloneRepoSheet( loadHome: suspend () -> String, loadDirs: suspend (String) -> List<DirectoryEntry>, clone: suspend (String, String, GitCredentials?) -> String?, onCloned: () -> Unit, onDismiss: () -> Unit, )` |
| 65 |   `val colors` |
| 66 |   `val scope` |
| 67 |   `var url by remember` |
| 68 |   `var parentDir by remember` |
| 69 |   `var username by remember` |
| 70 |   `var password by remember` |
| 71 |   `var cloning by remember` |
| 72 |   `var error by remember` |
| 73 |   `var showBrowser by remember` |
| 75 |   `val repoName` |
| 76 |   `val targetDir` |
| 77 |   `val canClone` |

## feature/projects/CommitSheet.kt

| Line | Declaration |
|---|---|
| 73 | `@Composable fun CommitSheet( viewModel: ProjectDetailViewModel, client: CodegClient, rootPath: String, onDismiss: () -> Unit, onCommitted: (push: Boolean) -> Unit, )` |
| 80 |   `val colors` |
| 81 |   `val scope` |
| 83 |   `var entries by remember` |
| 84 |   `var selected by remember` |
| 85 |   `var message by remember` |
| 86 |   `var loadError by remember` |
| 87 |   `var committing by remember` |
| 88 |   `var commitError by remember` |
| 90 |   `suspend fun load()` |
| 103 |   `val all` |
| 104 |   `val trimmedMessage` |
| 105 |   `val canCommit` |
| 107 |   `fun runCommit(push: Boolean)` |
| 215 | `@Composable private fun SelectionHeader( selectedCount: Int, total: Int, allSelected: Boolean, enabled: Boolean, onToggleAll: () -> Unit, )` |
| 222 |   `val colors` |
| 243 | `@Composable private fun FileSection( title: String, entries: List<GitStatusEntry>, selected: Set<String>, enabled: Boolean, onToggle: (String) -> Unit, )` |
| 250 |   `val colors` |

## feature/projects/DirectoryBrowserSheet.kt

| Line | Declaration |
|---|---|
| 61 | `@Composable fun DirectoryBrowserSheet( title: String, confirmLabel: String, loadHome: suspend () -> String, loadDirs: suspend (String) -> List<DirectoryEntry>, onSelect: (String) -> Unit, onDismiss: () -> Unit, )` |
| 69 |   `val colors` |
| 70 |   `val scope` |
| 71 |   `var path by remember` |
| 72 |   `var dirs by remember` |
| 73 |   `var loading by remember` |
| 125 | `@Composable private fun BrowserRow(name: String, hasChildren: Boolean, icon: ImageVector, onClick: () -> Unit)` |
| 126 |   `val colors` |

## feature/projects/FolderChangesView.kt

| Line | Declaration |
|---|---|
| 72 | `@Composable fun FolderChangesView( viewModel: ProjectDetailViewModel, client: CodegClient, rootPath: String, onCommit: () -> Unit, )` |
| 78 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 79 |   `var diffFile by remember` |
| 82 |   `val df` |
| 88 |   `var result by remember(rootPath)` |
| 92 |   `var actionEntry by remember` |
| 93 |   `var confirm by remember` |
| 144 |   `val target` |
| 176 | `@Composable private fun FileActions( entry: GitStatusEntry, onViewDiff: () -> Unit, onStage: () -> Unit, onDiscard: () -> Unit, onDelete: () -> Unit, )` |
| 183 |   `val colors` |
| 200 | `@Composable private fun ActionRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, tint: Color, onClick: () -> Unit)` |
| 213 | `@Composable private fun ChangeRow(entry: GitStatusEntry, onClick: () -> Unit, onLongClick: () -> Unit)` |
| 214 |   `val colors` |
| 215 |   `val sub` |
| 228 | `@Composable private fun WorkingDiff(client: CodegClient, rootPath: String, file: String)` |
| 229 |   `var result by remember(file)` |
| 231 |   `val r` |
| 244 | `@Composable private fun Centered(content: @Composable () -> Unit)` |

## feature/projects/FolderCommitsView.kt

| Line | Declaration |
|---|---|
| 72 | `@Composable fun FolderCommitsView(viewModel: ProjectDetailViewModel, client: CodegClient, rootPath: String)` |
| 73 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 74 |   `var commit by remember` |
| 77 |   `val sel` |
| 83 |   `var result by remember(rootPath)` |
| 84 |   `var pushInfo by remember(rootPath)` |
| 90 |   `val r` |
| 91 |   `val isRepo` |
| 146 | `@Composable private fun SyncHeader( branch: String?, trackingRemote: String?, hasRemote: Boolean, unpushed: Int, busy: Boolean, onPull: () -> Unit, onPush: () -> Unit, onFetch: () -> Unit, )` |
| 156 |   `val colors` |
| 182 | `@Composable private fun SyncButton( icon: ImageVector, label: String, enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier, badge: Int? = null, )` |
| 190 |   `val colors` |
| 191 |   `val tint` |
| 217 | `@Composable private fun CommitRow(entry: GitLogEntry, onClick: () -> Unit)` |
| 218 |   `val colors` |
| 240 | `@Composable private fun CommitDetail(client: CodegClient, rootPath: String, entry: GitLogEntry)` |
| 241 |   `val colors` |
| 242 |   `var result by remember(entry.fullHash)` |
| 281 | `private fun relativeDate(iso: String): String` |

## feature/projects/FolderFilesView.kt

| Line | Declaration |
|---|---|
| 56 | `@Composable fun FolderFilesView(client: CodegClient, rootPath: String)` |
| 57 |   `val colors` |
| 58 |   `var stack by remember` |
| 59 |   `var previewPath by remember` |
| 65 |   `val preview` |
| 71 |   `val dir` |
| 72 |   `var result by remember(dir)` |
| 75 |   `val r` |
| 109 | `@Composable private fun FileRow(item: DirectoryItem, onClick: () -> Unit)` |
| 110 |   `val colors` |
| 111 |   `val sizeLabel` |
| 129 | `@Composable private fun FilePreview(client: CodegClient, rootPath: String, absPath: String)` |
| 130 |   `val colors` |
| 131 |   `val rel` |
| 132 |   `var result by remember(absPath)` |
| 159 | `@Composable private fun FileContent(content: String)` |
| 160 |   `val colors` |
| 161 |   `val truncationNote` |
| 162 |   `val lines` |
| 166 |   `val gutterWidth` |
| 179 | `private fun sortItems(items: List<DirectoryItem>): List<DirectoryItem>` |
| 182 | `private fun formatBytes(bytes: Long): String` |

## feature/projects/GitChangeVisuals.kt

| Line | Declaration |
|---|---|
| 23 | `@Composable internal fun GitChangeBadge(change: GitChange, modifier: Modifier = Modifier)` |
| 24 |   `val colors` |
| 25 |   `val tint` |

## feature/projects/GitCredentialSheet.kt

| Line | Declaration |
|---|---|
| 66 | `@Composable fun GitCredentialSheet( viewModel: ProjectDetailViewModel, prompt: GitCredentialPrompt, )` |
| 70 |   `val colors` |
| 71 |   `val scope` |
| 72 |   `val uriHandler` |
| 74 |   `var token by remember` |
| 75 |   `var username by remember` |
| 76 |   `var password by remember` |
| 77 |   `var saveCredentials by remember` |
| 78 |   `var submitting by remember` |
| 79 |   `var error by remember` |
| 81 |   `val hostLabel` |
| 82 |   `val serverUrl` |
| 83 |   `val invalidTokenMsg` |
| 85 |   `val canSubmit` |
| 91 |   `fun submit()` |
| 185 | `@Composable private fun Banner(message: String, tint: Color, textColor: Color)` |

## feature/projects/GitStatusStrip.kt

| Line | Declaration |
|---|---|
| 40 | `@Composable internal fun GitStatusStrip( busy: Boolean, busyTitle: String?, banner: GitBanner?, onDismissBanner: () -> Unit, modifier: Modifier = Modifier, )` |
| 47 |   `val colors` |

## feature/projects/ProjectDetailScreen.kt

| Line | Declaration |
|---|---|
| 55 | `private enum class ProjectTab(val labelRes: Int)` |
| 63 | `@Composable fun ProjectDetailScreen( onBack: () -> Unit, viewModel: ProjectDetailViewModel = hiltViewModel(), )` |
| 67 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 68 |   `val colors` |
| 69 |   `var tab by remember` |
| 70 |   `var showBranchPicker by remember` |
| 71 |   `var showCommit by remember` |
| 75 |   `val commitClient` |
| 76 |   `val commitFolder` |
| 155 | `@Composable private fun Header(name: String, path: String, color: Color, branch: String?, running: Int, sessions: Int, onBranchClick: () -> Unit = {})` |
| 156 |   `val colors` |
| 180 | `@Composable private fun SegmentedTabs(selected: ProjectTab, onSelect: (ProjectTab) -> Unit)` |
| 181 |   `val entries` |

## feature/projects/ProjectDetailViewModel.kt

| Line | Declaration |
|---|---|
| 38 | `class ProjectDetailViewModel @Inject constructor( private val repository: ServerRepository, savedStateHandle: SavedStateHandle, @ApplicationContext private val appContext: Context, ) : ViewModel()` |
| 39 |   `private val repository: ServerRepository` |
| 44 |   `private val folderId: Int` |
| 46 |   `private val _ui` |
| 47 |   `val ui: StateFlow<ProjectDetailState>` |
| 51 |   `fun load()` |
| 83 |   `suspend fun loadBranches(): GitBranchList` |
| 90 |   `fun checkout(branch: String, onResult: (String?) -> Unit)` |
| 100 |   `fun createBranch(name: String, onResult: (String?) -> Unit)` |
| 119 |   `private val client: CodegClient? get()` |
| 120 |   `private val repoPath: String? get()` |
| 122 |   `private var credentialDeferred: CompletableDeferred<GitCredentialOutcome?>?` |
| 123 |   `private var promptSeq` |
| 130 |   `suspend fun gitCommit(message: String, files: List<String>)` |
| 138 |   `fun push()` |
| 142 |   `fun pull()` |
| 146 |   `fun fetch()` |
| 152 |   `fun discard(file: String, displayName: String)` |
| 158 |   `fun stage(file: String, displayName: String)` |
| 164 |   `fun deleteUntracked(file: String, displayName: String)` |
| 170 |   `fun dismissBanner()` |
| 174 |   `fun submitCredentials(outcome: GitCredentialOutcome)` |
| 176 |   `fun cancelCredentials()` |
| 179 |   `fun cancelCredentialsIfShowing(id: Long)` |
| 183 |   `private fun resolveCredentials(outcome: GitCredentialOutcome?)` |
| 190 |   `suspend fun validateGithubToken(serverUrl: String, token: String): GitHubTokenValidation?` |
| 194 |   `suspend fun saveGithubAccount(serverUrl: String, host: String?, validation: GitHubTokenValidation, token: String)` |
| 215 |   `private fun setBanner(kind: GitBannerKind, message: String)` |
| 218 |   `private fun didMutate()` |
| 220 |   `private fun quantity(@PluralsRes id: Int, count: Int): String` |
| 224 |   `private fun performGit(@StringRes title: Int, body: suspend (CodegClient, String) -> GitBanner?)` |
| 254 |   `private suspend fun <T> withCredentialRetry(operation: suspend (GitCredentials?) -> T): T` |
| 275 |   `private suspend fun requestCredentials(github: Boolean, host: String?, retry: Boolean): GitCredentialOutcome?` |
| 284 |   `private suspend fun resolveRemoteHost(): String?` |
| 292 |   `private suspend fun saveGenericAccount(host: String?, credentials: GitCredentials)` |
| 309 |   `private fun pushBanner(result: GitPushResult): GitBanner` |
| 315 |   `private fun pullBanner(result: GitPullResult): GitBanner` |
| 326 |   `private fun extractHost(url: String): String?` |
| 343 | `data class ProjectDetailState( val loading: Boolean = false, val folder: FolderDetail? = null, val client: CodegClient? = null, val branch: String? = null, val runningCount: Int = 0, val sessionCount: Int = 0, val error: String? = null, val gitBusy: Boolean = false, val gitBusyTitle: String? = null, val gitBanner: GitBanner? = null, val reloadToken: Int = 0, val credentialPrompt: GitCredentialPrompt? = null, )` |
| 344 |   `val loading: Boolean` |
| 345 |   `val folder: FolderDetail?` |
| 346 |   `val client: CodegClient?` |
| 347 |   `val branch: String?` |
| 348 |   `val runningCount: Int` |
| 349 |   `val sessionCount: Int` |
| 350 |   `val error: String?` |
| 351 |   `val gitBusy: Boolean` |
| 352 |   `val gitBusyTitle: String?` |
| 353 |   `val gitBanner: GitBanner?` |
| 354 |   `val reloadToken: Int` |
| 355 |   `val credentialPrompt: GitCredentialPrompt?` |
| 359 | `enum class GitBannerKind` |
| 362 | `data class GitBanner(val kind: GitBannerKind, val message: String)` |
| 365 | `data class GitCredentialPrompt( val id: Long, /** GitHub host → a token field; other hosts → username + password. */ val github: Boolean, val host: String?, /** True when re-prompting after a failed retry (the sheet shows a hint). */ val isRetry: Boolean, )` |
| 366 |   `val id: Long` |
| 368 |   `val github: Boolean` |
| 369 |   `val host: String?` |
| 371 |   `val isRetry: Boolean` |
| 379 | `data class GitCredentialOutcome( val credentials: GitCredentials, val saveAfterSuccess: Boolean, )` |
| 380 |   `val credentials: GitCredentials` |
| 381 |   `val saveAfterSuccess: Boolean` |

## feature/projects/ProjectListScreen.kt

| Line | Declaration |
|---|---|
| 72 | `@Composable fun ProjectListScreen( onOpenFolder: (Int) -> Unit, viewModel: ProjectsViewModel = hiltViewModel(), )` |
| 76 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 77 |   `val colors` |
| 78 |   `var menuOpen by remember` |
| 79 |   `var showBrowser by remember` |
| 80 |   `var showClone by remember` |
| 285 | `@Composable private fun FolderRow( folder: FolderDetail, running: Int, modifier: Modifier = Modifier, onClick: () -> Unit, )` |
| 291 |   `val colors` |
| 292 |   `val tile` |
| 349 | `@Composable private fun RunningPill(count: Int, modifier: Modifier = Modifier)` |
| 350 |   `val colors` |
| 371 | `@Composable fun BranchPill(branch: String, modifier: Modifier = Modifier)` |
| 372 |   `val colors` |
| 398 | `internal fun sortedFolders(folders: List<FolderDetail>): List<FolderDetail>` |

## feature/projects/ProjectsViewModel.kt

| Line | Declaration |
|---|---|
| 25 | `class ProjectsViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 26 |   `private val repository: ServerRepository` |
| 29 |   `private val _ui` |
| 30 |   `val ui: StateFlow<ProjectsUiState>` |
| 32 |   `private var client: CodegClient?` |
| 33 |   `private var loadedEndpoint: String?` |
| 46 |   `private suspend fun onProfile(profile: ServerProfile?)` |
| 60 |   `fun refresh()` |
| 65 |   `private suspend fun fetch(initial: Boolean)` |
| 84 |   `fun openFolder(path: String, onResult: (String?) -> Unit)` |
| 94 |   `suspend fun homeDirectory(): String` |
| 98 |   `suspend fun listDirectories(path: String): List<app.codeg.android.core.model.DirectoryEntry>` |
| 102 |   `suspend fun clone( url: String, targetDir: String, credentials: app.codeg.android.core.model.GitCredentials?, ): String?` |
| 119 | `data class ProjectsUiState( val folders: List<FolderDetail> = emptyList(), val conversations: List<ConversationSummary> = emptyList(), val loading: Boolean = false, val refreshing: Boolean = false, val hasLoaded: Boolean = false, val error: String? = null, )` |
| 120 |   `val folders: List<FolderDetail>` |
| 121 |   `val conversations: List<ConversationSummary>` |
| 122 |   `val loading: Boolean` |
| 123 |   `val refreshing: Boolean` |
| 124 |   `val hasLoaded: Boolean` |
| 125 |   `val error: String?` |
| 127 |   `val isBusy: Boolean get()` |
| 128 |   `fun runningCount(folderId: Int): Int` |

## feature/projects/git/BranchManageSheet.kt

| Line | Declaration |
|---|---|
| 23 | `@Composable fun BranchManageSheet(viewModel: BranchManageViewModel, onDismiss: () -> Unit)` |
| 24 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 25 |   `var deleting by remember` |
| 26 |   `var force by remember` |
| 27 |   `val colors` |

## feature/projects/git/BranchManageViewModel.kt

| Line | Declaration |
|---|---|
| 15 | `class BranchManageViewModel(private val client: CodegClient, private val path: String) : ViewModel()` |
| 16 |   `data class Ui( val branches: List<BranchManageEntry> = emptyList(), val busy: Boolean = false, val supported: Boolean = true, val hiddenActions: Set<String> = emptySet(), val error: String? = null, val completed: Boolean = false, val conflict: List<String>? = null, val diff: String? = null, val diffBranch: String? = null, val credentialsRequested: Boolean = false, )` |
| 28 |   `private val state` |
| 29 |   `val ui` |
| 30 |   `private var credentials: CompletableDeferred<GitCredentials?>?` |
| 34 |   `private suspend fun load()` |
| 47 |   `fun refresh()` |
| 49 |   `fun delete(branch: BranchManageEntry, force: Boolean)` |
| 56 |   `fun update(branch: BranchManageEntry)` |
| 62 |   `fun compare(branch: BranchManageEntry)` |
| 67 |   `fun submitCredentials(value: GitCredentials?)` |
| 69 |   `private suspend fun <T> withCredentials(body: suspend (GitCredentials?) -> T): T` |
| 85 |   `private fun run(action: String, body: suspend () -> Unit)` |

## feature/projects/git/GitActionsMenu.kt

| Line | Declaration |
|---|---|
| 27 | `@Composable fun GitActionsMenu(onSwitchBranch: () -> Unit, enabled: Boolean = true)` |
| 28 |   `val project: ProjectDetailViewModel` |
| 29 |   `val projectUi by project.ui.collectAsStateWithLifecycle()` |
| 30 |   `val client` |
| 31 |   `val path` |
| 32 |   `var showManage by remember(client, path)` |
| 33 |   `val manager: BranchManageViewModel?` |
| 39 |   `val manageUi` |
| 43 |   `var expanded by remember` |

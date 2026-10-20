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
| 241 |   `var unavailableFiles by remember(entry.fullHash)` |
| 242 |   `var selectedFile by remember(entry.fullHash)` |
| 251 |   `val colors` |
| 252 |   `var result by remember(entry.fullHash)` |
| 298 | `private fun relativeDate(iso: String): String` |

## feature/projects/FolderFilesView.kt

| Line | Declaration |
|---|---|
| 62 | `@Composable fun FolderFilesView(client: CodegClient, rootPath: String)` |
| 63 |   `val colors` |
| 64 |   `var stack by remember` |
| 65 |   `var previewPath by remember` |
| 67 |   `var unavailablePaths by remember(rootPath)` |
| 73 |   `val preview` |
| 79 |   `val dir` |
| 80 |   `var result by remember(dir)` |
| 83 |   `val r` |
| 117 | `@Composable private fun FileRow(item: DirectoryItem, onClick: () -> Unit)` |
| 118 |   `val colors` |
| 119 |   `val sizeLabel` |
| 137 | `@Composable internal fun FilePreview(client: CodegClient, rootPath: String, absPath: String, size: Long? = null, commit: String? = null, onUnsupported: () -> Unit = {})` |
| 138 |   `val colors` |
| 139 |   `val rel` |
| 140 |   `var result by remember(rootPath, absPath, commit)` |
| 141 |   `var attempt by remember(rootPath, absPath, commit)` |
| 175 | `private fun sortItems(items: List<DirectoryItem>): List<DirectoryItem>` |
| 178 | `private fun formatBytes(bytes: Long): String` |

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

## feature/projects/git/ConflictListScreen.kt

| Line | Declaration |
|---|---|
| 14 | `@Composable fun ConflictListScreen(vm: MergeOpsViewModel, client: CodegClient, path: String, branch: String)` |
| 15 |   `val ui by vm.ui.collectAsStateWithLifecycle()` |
| 16 |   `var pending by remember` |
| 17 |   `var version by remember` |
| 65 | `@Composable private fun versionLabel(value: String): String` |

## feature/projects/git/GitActionsMenu.kt

| Line | Declaration |
|---|---|
| 32 | `@Composable fun GitActionsMenu(onSwitchBranch: () -> Unit, enabled: Boolean = true)` |
| 33 |   `val project: ProjectDetailViewModel` |
| 34 |   `val projectUi by project.ui.collectAsStateWithLifecycle()` |
| 35 |   `val client` |
| 36 |   `val folder` |
| 37 |   `val path` |
| 40 |   `var showManage by remember(client, path)` |
| 41 |   `val manager: BranchManageViewModel?` |
| 47 |   `val manageUi` |
| 53 |   `val mergeVm: MergeOpsViewModel` |
| 54 |   `val mergeUi by mergeVm.ui.collectAsStateWithLifecycle()` |
| 55 |   `var showMerge by remember(path)` |
| 67 |   `var showMisc by remember` |
| 72 |   `var expanded by remember` |

## feature/projects/git/GitMiscSheet.kt

| Line | Declaration |
|---|---|
| 22 | `@Composable fun GitMiscSheet(client: CodegClient, path: String, folderId: Int, onDismiss: () -> Unit, onChanged: () -> Unit)` |
| 23 |   `val model: GitMiscViewModel` |
| 24 |   `val ui by model.ui.collectAsStateWithLifecycle()` |
| 25 |   `var name by remember` |
| 26 |   `var url by remember` |
| 27 |   `var branch by remember` |
| 28 |   `var destination by remember` |
| 29 |   `var base by remember` |
| 30 |   `var username by remember` |
| 31 |   `var password by remember` |
| 32 |   `var confirmation by remember` |
| 34 |   `fun mutate(action: String, operation: suspend () -> Unit)` |

## feature/projects/git/GitMiscViewModel.kt

| Line | Declaration |
|---|---|
| 18 | `class GitMiscViewModel : ViewModel()` |
| 19 |   `private val state` |
| 20 |   `val ui` |
| 22 |   `fun run(action: String, operation: suspend () -> Unit)` |
| 43 |   `suspend fun load(client: CodegClient, path: String)` |
| 59 |   `fun fetch(client: CodegClient, path: String, name: String, credentials: GitCredentials? = null)` |
| 70 | `data class GitMiscUi( val busy: Boolean = false, val error: String? = null, val success: Boolean = false, val notRepository: Boolean = false, val remotes: List<GitRemote> = emptyList(), val worktrees: List<GitMiscWorktree> = emptyList(), val hidden: Set<String> = emptySet(), val authRemote: String? = null, )` |
| 71 |   `val busy: Boolean` |
| 72 |   `val error: String?` |
| 73 |   `val success: Boolean` |
| 74 |   `val notRepository: Boolean` |
| 75 |   `val remotes: List<GitRemote>` |
| 76 |   `val worktrees: List<GitMiscWorktree>` |
| 77 |   `val hidden: Set<String>` |
| 78 |   `val authRemote: String?` |

## feature/projects/git/MergeOpsSheet.kt

| Line | Declaration |
|---|---|
| 17 | `@Composable fun MergeOpsSheet(vm: MergeOpsViewModel, client: CodegClient, path: String, branch: String?, onDismiss: () -> Unit)` |
| 18 |   `val ui by vm.ui.collectAsStateWithLifecycle()` |
| 19 |   `var action by remember` |
| 20 |   `var target by remember` |
| 21 |   `var mode by remember` |
| 22 |   `var confirm by remember` |
| 23 |   `var typed by remember` |
| 75 | `@Composable internal fun operationLabel(value: String): String` |

## feature/projects/git/MergeOpsViewModel.kt

| Line | Declaration |
|---|---|
| 14 | `data class MergeOpsUi( val busy: Boolean = false, val ready: Boolean = false, val supported: Boolean = true, val branches: List<String> = emptyList(), val conflicts: List<String> = emptyList(), val active: Boolean = false, val operation: String = "merge", val file: String? = null, val versions: MergeVersions? = null, val error: String? = null, val completed: Boolean = false, )` |
| 15 |   `val busy: Boolean` |
| 16 |   `val ready: Boolean` |
| 17 |   `val supported: Boolean` |
| 18 |   `val branches: List<String>` |
| 19 |   `val conflicts: List<String>` |
| 20 |   `val active: Boolean` |
| 21 |   `val operation: String` |
| 22 |   `val file: String?` |
| 23 |   `val versions: MergeVersions?` |
| 24 |   `val error: String?` |
| 25 |   `val completed: Boolean` |
| 28 | `class MergeOpsViewModel : ViewModel()` |
| 29 |   `private val state` |
| 30 |   `val ui` |
| 31 |   `private suspend fun detect(client: CodegClient, path: String)` |
| 37 |   `private fun run(block: suspend () -> Unit)` |
| 48 |   `fun refresh(client: CodegClient, path: String)` |
| 53 |   `fun operation(value: String)` |
| 54 |   `fun execute(client: CodegClient, path: String, action: String, target: String, mode: String)` |
| 65 |   `fun versions(client: CodegClient, path: String, file: String)` |
| 70 |   `fun resolve(client: CodegClient, path: String, ours: Boolean)` |
| 77 |   `fun finish(client: CodegClient, path: String, abort: Boolean)` |

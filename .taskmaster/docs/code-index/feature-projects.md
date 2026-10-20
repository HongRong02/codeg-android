# feature/projects

Source root: `app/src/main/java/app/codeg/android/`

## feature/projects/BranchPickerSheet.kt

| Line | Declaration |
|---|---|
| 55 | `@Composable fun BranchPickerSheet(viewModel: ProjectDetailViewModel, onDismiss: () -> Unit)` |
| 56 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 57 |   `val colors` |
| 58 |   `val sheetState` |
| 59 |   `var loading by remember` |
| 60 |   `var locals by remember` |
| 61 |   `var remotes by remember` |
| 62 |   `var newBranch by remember` |
| 63 |   `var busy by remember` |
| 64 |   `var error by remember` |
| 73 |   `fun doCheckout(branch: String)` |
| 113 | `@Composable private fun SectionLabel(text: String)` |
| 118 | `@Composable private fun BranchRow(branch: String, current: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 119 |   `val colors` |

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
| 72 | `@Composable fun CommitSheet( viewModel: ProjectDetailViewModel, client: CodegClient, rootPath: String, onDismiss: () -> Unit, onCommitted: (push: Boolean) -> Unit, )` |
| 79 |   `val colors` |
| 80 |   `val scope` |
| 82 |   `var entries by remember` |
| 83 |   `var selected by remember` |
| 84 |   `var message by remember` |
| 85 |   `var loadError by remember` |
| 86 |   `var committing by remember` |
| 87 |   `var commitError by remember` |
| 89 |   `suspend fun load()` |
| 102 |   `val all` |
| 103 |   `val trimmedMessage` |
| 104 |   `val canCommit` |
| 106 |   `fun runCommit(push: Boolean)` |
| 214 | `@Composable private fun SelectionHeader( selectedCount: Int, total: Int, allSelected: Boolean, enabled: Boolean, onToggleAll: () -> Unit, )` |
| 221 |   `val colors` |
| 242 | `@Composable private fun FileSection( title: String, entries: List<GitStatusEntry>, selected: Set<String>, enabled: Boolean, onToggle: (String) -> Unit, )` |
| 249 |   `val colors` |

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
| 71 | `@Composable fun FolderCommitsView(viewModel: ProjectDetailViewModel, client: CodegClient, rootPath: String)` |
| 72 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 73 |   `var commit by remember` |
| 76 |   `val sel` |
| 82 |   `var result by remember(rootPath)` |
| 83 |   `var pushInfo by remember(rootPath)` |
| 89 |   `val r` |
| 90 |   `val isRepo` |
| 145 | `@Composable private fun SyncHeader( branch: String?, trackingRemote: String?, hasRemote: Boolean, unpushed: Int, busy: Boolean, onPull: () -> Unit, onPush: () -> Unit, onFetch: () -> Unit, )` |
| 155 |   `val colors` |
| 181 | `@Composable private fun SyncButton( icon: ImageVector, label: String, enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier, badge: Int? = null, )` |
| 189 |   `val colors` |
| 190 |   `val tint` |
| 216 | `@Composable private fun CommitRow(entry: GitLogEntry, onClick: () -> Unit)` |
| 217 |   `val colors` |
| 239 | `@Composable private fun CommitDetail(client: CodegClient, rootPath: String, entry: GitLogEntry)` |
| 240 |   `val colors` |
| 241 |   `var result by remember(entry.fullHash)` |
| 279 | `private val Color_Add` |
| 281 | `private fun relativeDate(iso: String): String` |

## feature/projects/FolderFilesView.kt

| Line | Declaration |
|---|---|
| 55 | `@Composable fun FolderFilesView(client: CodegClient, rootPath: String)` |
| 56 |   `val colors` |
| 57 |   `var stack by remember` |
| 58 |   `var previewPath by remember` |
| 64 |   `val preview` |
| 70 |   `val dir` |
| 71 |   `var result by remember(dir)` |
| 74 |   `val r` |
| 108 | `@Composable private fun FileRow(item: DirectoryItem, onClick: () -> Unit)` |
| 109 |   `val colors` |
| 110 |   `val sizeLabel` |
| 128 | `@Composable private fun FilePreview(client: CodegClient, rootPath: String, absPath: String)` |
| 129 |   `val colors` |
| 130 |   `val rel` |
| 131 |   `var result by remember(absPath)` |
| 158 | `@Composable private fun FileContent(content: String)` |
| 159 |   `val colors` |
| 160 |   `val truncationNote` |
| 161 |   `val lines` |
| 165 |   `val gutterWidth` |
| 178 | `private fun sortItems(items: List<DirectoryItem>): List<DirectoryItem>` |
| 181 | `private fun formatBytes(bytes: Long): String` |

## feature/projects/GitChangeVisuals.kt

| Line | Declaration |
|---|---|
| 20 | `internal val GitChangeColors: Map<GitChange, Color>` |
| 33 | `@Composable internal fun GitChangeBadge(change: GitChange, modifier: Modifier = Modifier)` |
| 34 |   `val tint` |

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
| 184 | `private val WarnTint` |
| 187 | `@Composable private fun Banner(message: String, tint: Color, textColor: Color)` |

## feature/projects/GitStatusStrip.kt

| Line | Declaration |
|---|---|
| 32 | `private val SuccessGreen` |
| 33 | `private val WarnAmber` |
| 42 | `@Composable internal fun GitStatusStrip( busy: Boolean, busyTitle: String?, banner: GitBanner?, onDismissBanner: () -> Unit, modifier: Modifier = Modifier, )` |
| 49 |   `val colors` |

## feature/projects/ProjectDetailScreen.kt

| Line | Declaration |
|---|---|
| 54 | `private enum class ProjectTab(val labelRes: Int)` |
| 62 | `@Composable fun ProjectDetailScreen( onBack: () -> Unit, viewModel: ProjectDetailViewModel = hiltViewModel(), )` |
| 66 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 67 |   `val colors` |
| 68 |   `var tab by remember` |
| 69 |   `var showBranchPicker by remember` |
| 70 |   `var showCommit by remember` |
| 74 |   `val commitClient` |
| 75 |   `val commitFolder` |
| 148 | `@Composable private fun Header(name: String, path: String, color: Color, branch: String?, running: Int, sessions: Int, onBranchClick: () -> Unit = {})` |
| 149 |   `val colors` |
| 173 | `@Composable private fun SegmentedTabs(selected: ProjectTab, onSelect: (ProjectTab) -> Unit)` |
| 174 |   `val entries` |

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

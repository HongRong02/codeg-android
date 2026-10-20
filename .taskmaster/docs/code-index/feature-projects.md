# feature/projects

Source root: `app/src/main/java/app/codeg/android/`

## feature/projects/BranchPickerSheet.kt

| Line | Declaration |
|---|---|
| 48 | `@Composable fun BranchPickerSheet(viewModel: ProjectDetailViewModel, onDismiss: () -> Unit)` |
| 49 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 50 |   `val colors` |
| 51 |   `var loading by remember` |
| 52 |   `var locals by remember` |
| 53 |   `var remotes by remember` |
| 54 |   `var newBranch by remember` |
| 55 |   `var busy by remember` |
| 56 |   `var error by remember` |
| 65 |   `fun doCheckout(branch: String)` |
| 103 | `@Composable private fun SectionLabel(text: String)` |
| 108 | `@Composable private fun BranchRow(branch: String, current: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 109 |   `val colors` |

## feature/projects/CloneRepoSheet.kt

| Line | Declaration |
|---|---|
| 44 | `@Composable fun CloneRepoSheet( loadHome: suspend () -> String, loadDirs: suspend (String) -> List<DirectoryEntry>, clone: suspend (String, String, GitCredentials?) -> String?, onCloned: () -> Unit, onDismiss: () -> Unit, )` |
| 51 |   `val colors` |
| 52 |   `val scope` |
| 53 |   `var url by remember` |
| 54 |   `var parentDir by remember` |
| 55 |   `var username by remember` |
| 56 |   `var password by remember` |
| 57 |   `var cloning by remember` |
| 58 |   `var error by remember` |
| 59 |   `var showBrowser by remember` |
| 61 |   `val repoName` |
| 62 |   `val targetDir` |
| 63 |   `val canClone` |

## feature/projects/CommitSheet.kt

| Line | Declaration |
|---|---|
| 62 | `@Composable fun CommitSheet( viewModel: ProjectDetailViewModel, client: CodegClient, rootPath: String, onDismiss: () -> Unit, onCommitted: (push: Boolean) -> Unit, )` |
| 69 |   `val context` |
| 70 |   `val colors` |
| 71 |   `val scope` |
| 73 |   `var entries by remember` |
| 74 |   `var selected by remember` |
| 75 |   `var message by remember` |
| 76 |   `var loadError by remember` |
| 77 |   `var committing by remember` |
| 78 |   `var commitError by remember` |
| 80 |   `suspend fun load()` |
| 93 |   `val all` |
| 94 |   `val trimmedMessage` |
| 95 |   `val canCommit` |
| 97 |   `fun runCommit(push: Boolean)` |
| 195 | `@Composable private fun SelectionHeader( selectedCount: Int, total: Int, allSelected: Boolean, enabled: Boolean, onToggleAll: () -> Unit, )` |
| 202 |   `val colors` |
| 223 | `@Composable private fun FileSection( title: String, entries: List<GitStatusEntry>, selected: Set<String>, enabled: Boolean, onToggle: (String) -> Unit, )` |
| 230 |   `val colors` |

## feature/projects/DirectoryBrowserSheet.kt

| Line | Declaration |
|---|---|
| 44 | `@Composable fun DirectoryBrowserSheet( title: String, confirmLabel: String, loadHome: suspend () -> String, loadDirs: suspend (String) -> List<DirectoryEntry>, onSelect: (String) -> Unit, onDismiss: () -> Unit, )` |
| 52 |   `val colors` |
| 53 |   `val scope` |
| 54 |   `var path by remember` |
| 55 |   `var dirs by remember` |
| 56 |   `var loading by remember` |
| 97 | `@Composable private fun BrowserRow(name: String, hasChildren: Boolean, icon: ImageVector, onClick: () -> Unit)` |
| 98 |   `val colors` |

## feature/projects/FolderChangesView.kt

| Line | Declaration |
|---|---|
| 62 | `@Composable fun FolderChangesView( viewModel: ProjectDetailViewModel, client: CodegClient, rootPath: String, onCommit: () -> Unit, )` |
| 68 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 69 |   `var diffFile by remember` |
| 72 |   `val df` |
| 78 |   `var result by remember(rootPath)` |
| 82 |   `var actionEntry by remember` |
| 83 |   `var confirm by remember` |
| 134 |   `val target` |
| 166 | `@Composable private fun FileActions( entry: GitStatusEntry, onViewDiff: () -> Unit, onStage: () -> Unit, onDiscard: () -> Unit, onDelete: () -> Unit, )` |
| 173 |   `val colors` |
| 190 | `@Composable private fun ActionRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, tint: Color, onClick: () -> Unit)` |
| 200 | `@Composable private fun ChangeRow(entry: GitStatusEntry, onClick: () -> Unit, onLongClick: () -> Unit)` |
| 201 |   `val colors` |
| 202 |   `val sub` |
| 213 | `@Composable private fun WorkingDiff(client: CodegClient, rootPath: String, file: String)` |
| 214 |   `var result by remember(file)` |
| 216 |   `val r` |
| 229 | `@Composable private fun Centered(content: @Composable () -> Unit)` |

## feature/projects/FolderCommitsView.kt

| Line | Declaration |
|---|---|
| 70 | `@Composable fun FolderCommitsView(viewModel: ProjectDetailViewModel, client: CodegClient, rootPath: String)` |
| 71 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 72 |   `var commit by remember` |
| 75 |   `val sel` |
| 81 |   `var result by remember(rootPath)` |
| 82 |   `var pushInfo by remember(rootPath)` |
| 88 |   `val r` |
| 89 |   `val isRepo` |
| 144 | `@Composable private fun SyncHeader( branch: String?, trackingRemote: String?, hasRemote: Boolean, unpushed: Int, busy: Boolean, onPull: () -> Unit, onPush: () -> Unit, onFetch: () -> Unit, )` |
| 154 |   `val colors` |
| 180 | `@Composable private fun SyncButton( icon: ImageVector, label: String, enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier, badge: Int? = null, )` |
| 197 | `@Composable private fun CommitRow(entry: GitLogEntry, onClick: () -> Unit)` |
| 198 |   `val colors` |
| 215 | `@Composable private fun CommitDetail(client: CodegClient, rootPath: String, entry: GitLogEntry)` |
| 216 |   `var unavailableFiles by remember(entry.fullHash)` |
| 217 |   `var selectedFile by remember(entry.fullHash)` |
| 226 |   `val colors` |
| 227 |   `var result by remember(entry.fullHash)` |
| 274 | `private fun relativeDate(iso: String): String` |

## feature/projects/FolderFilesView.kt

| Line | Declaration |
|---|---|
| 47 | `@Composable fun FolderFilesView(client: CodegClient, rootPath: String)` |
| 48 |   `val colors` |
| 49 |   `var stack by remember` |
| 50 |   `var previewPath by remember` |
| 52 |   `var unavailablePaths by remember(rootPath)` |
| 58 |   `val preview` |
| 64 |   `val dir` |
| 65 |   `var result by remember(dir)` |
| 68 |   `val r` |
| 97 | `@Composable private fun FileRow(item: DirectoryItem, onClick: () -> Unit)` |
| 98 |   `val colors` |
| 99 |   `val sizeLabel` |
| 112 | `@Composable internal fun FilePreview(client: CodegClient, rootPath: String, absPath: String, size: Long? = null, commit: String? = null, onUnsupported: () -> Unit = {})` |
| 113 |   `val colors` |
| 114 |   `val rel` |
| 115 |   `var result by remember(rootPath, absPath, commit)` |
| 116 |   `var attempt by remember(rootPath, absPath, commit)` |
| 151 | `private fun sortItems(items: List<DirectoryItem>): List<DirectoryItem>` |
| 154 | `private fun formatBytes(bytes: Long): String` |

## feature/projects/forge/ForgeDetail.kt

| Line | Declaration |
|---|---|
| 17 | `@Composable internal fun ForgeDetail(ui: ForgeUi, vm: ForgeViewModel, modifier: Modifier = Modifier)` |
| 18 |   `val row` |
| 19 |   `var comment by remember(row.number, row.kind)` |
| 20 |   `var confirmation by remember(row.number, row.kind)` |
| 21 |   `var method by remember(ui.options)` |

## feature/projects/forge/ForgeSheet.kt

| Line | Declaration |
|---|---|
| 19 | `@Composable fun ForgeSheet(vm: ForgeViewModel, onDismiss: () -> Unit)` |
| 20 |   `val ui by vm.ui.collectAsStateWithLifecycle()` |
| 21 |   `var newIssue by remember` |
| 79 | `@Composable private fun ForgeNewIssue(ui: ForgeUi, vm: ForgeViewModel, dismiss: () -> Unit)` |
| 80 |   `var title by remember` |
| 81 |   `var body by remember` |
| 82 |   `var labels by remember` |

## feature/projects/forge/ForgeViewModel.kt

| Line | Declaration |
|---|---|
| 16 | `data class ForgeUi( val remote: ForgeRemote? = null, val busy: Boolean = false, val error: Throwable? = null, val tab: String = "issues", val filter: String = "open", val list: ForgeIssueList = ForgeIssueList(), val counts: Map<String, Long?> = emptyMap(), val selected: ForgeIssueRow? = null, val comments: ForgeCommentList = ForgeCommentList(), val detail: ForgeChangeDetail? = null, val files: ForgeChangedFileList = ForgeChangedFileList(), val options: ForgeMergeOptions? = null, val labels: ForgeLabelList = ForgeLabelList(), val taskId: Int? = null, val mismatch: Boolean = false, )` |
| 17 |   `val remote: ForgeRemote?` |
| 18 |   `val tab: String` |
| 19 |   `val counts: Map<String, Long?>` |
| 20 |   `val comments: ForgeCommentList` |
| 21 |   `val files: ForgeChangedFileList` |
| 22 |   `val labels: ForgeLabelList` |
| 25 | `class ForgeViewModel(private val client: CodegClient, private val folderId: Int) : ViewModel()` |
| 26 |   `private val state` |
| 27 |   `val ui` |
| 28 |   `private var job: Job?` |
| 29 |   `fun probe()` |
| 37 |   `fun refresh(tab: String = state.value.tab, filter: String = state.value.filter, page: Int = 1)` |
| 49 |   `fun open(row: ForgeIssueRow)` |
| 50 |   `private suspend fun loadDetail(row: ForgeIssueRow)` |
| 66 |   `fun back()` |
| 67 |   `fun moreComments()` |
| 73 |   `fun moreFiles()` |
| 79 |   `fun comment(body: String, onSuccess: () -> Unit)` |
| 85 |   `fun setState()` |
| 90 |   `fun merge(method: String)` |
| 97 |   `fun loadLabels()` |
| 98 |   `fun createIssue(title: String, body: String, labels: Set<String>, onSuccess: () -> Unit)` |
| 104 |   `fun createTask()` |
| 113 |   `private fun item(row: ForgeIssueRow, key: String, extra: JsonObjectBuilder.() -> Unit = {}): JsonObject` |
| 115 |   `private suspend fun section(block: suspend () -> Unit)` |
| 119 |   `private fun run(block: suspend () -> Unit)` |

## feature/projects/git/BranchManageSheet.kt

| Line | Declaration |
|---|---|
| 20 | `@Composable fun BranchManageSheet(viewModel: BranchManageViewModel, onDismiss: () -> Unit)` |
| 21 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 22 |   `var deleting by remember` |
| 23 |   `var force by remember` |
| 24 |   `var deleteReady by remember` |
| 25 |   `val colors` |

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
| 20 |   `val operations` |
| 58 | `@Composable private fun versionLabel(value: String): String` |

## feature/projects/git/GitActionsMenu.kt

| Line | Declaration |
|---|---|
| 30 | `@Composable fun GitActionsMenu(onSwitchBranch: () -> Unit, enabled: Boolean = true)` |
| 31 |   `val project: ProjectDetailViewModel` |
| 32 |   `val projectUi by project.ui.collectAsStateWithLifecycle()` |
| 33 |   `val client` |
| 34 |   `val folder` |
| 35 |   `val path` |
| 38 |   `var showStash by remember(client, path)` |
| 39 |   `val stash: StashViewModel?` |
| 45 |   `val stashUi` |
| 52 |   `var showManage by remember(client, path)` |
| 53 |   `val manager: BranchManageViewModel?` |
| 59 |   `val manageUi` |
| 65 |   `val mergeVm: MergeOpsViewModel` |
| 66 |   `val mergeUi by mergeVm.ui.collectAsStateWithLifecycle()` |
| 67 |   `var showMerge by remember(path)` |
| 79 |   `var showMisc by remember` |
| 85 |   `var showForge by remember(client, folder?.id)` |
| 86 |   `val forge: app.codeg.android.feature.projects.forge.ForgeViewModel?` |
| 93 |   `val forgeUi` |
| 99 |   `var expanded by remember` |

## feature/projects/git/GitMiscSheet.kt

| Line | Declaration |
|---|---|
| 18 | `@Composable fun GitMiscSheet(client: CodegClient, path: String, folderId: Int, onDismiss: () -> Unit, onChanged: () -> Unit)` |
| 19 |   `val model: GitMiscViewModel` |
| 20 |   `val ui by model.ui.collectAsStateWithLifecycle()` |
| 21 |   `var name by remember` |
| 22 |   `var url by remember` |
| 23 |   `var branch by remember` |
| 24 |   `var destination by remember` |
| 25 |   `var base by remember` |
| 26 |   `var username by remember` |
| 27 |   `var password by remember` |
| 28 |   `var confirmation by remember` |
| 30 |   `fun mutate(action: String, operation: suspend () -> Unit)` |

## feature/projects/git/GitMiscViewModel.kt

| Line | Declaration |
|---|---|
| 19 | `class GitMiscViewModel(application: Application) : AndroidViewModel(application)` |
| 20 |   `private val appContext` |
| 21 |   `private val state` |
| 22 |   `val ui` |
| 24 |   `fun run(action: String, operation: suspend () -> Unit)` |
| 45 |   `suspend fun load(client: CodegClient, path: String)` |
| 61 |   `fun fetch(client: CodegClient, path: String, name: String, credentials: GitCredentials? = null)` |
| 72 | `data class GitMiscUi( val busy: Boolean = false, val error: String? = null, val success: Boolean = false, val notRepository: Boolean = false, val remotes: List<GitRemote> = emptyList(), val worktrees: List<GitMiscWorktree> = emptyList(), val hidden: Set<String> = emptySet(), val authRemote: String? = null, )` |
| 73 |   `val busy: Boolean` |
| 74 |   `val error: String?` |
| 75 |   `val success: Boolean` |
| 76 |   `val notRepository: Boolean` |
| 77 |   `val remotes: List<GitRemote>` |
| 78 |   `val worktrees: List<GitMiscWorktree>` |
| 79 |   `val hidden: Set<String>` |
| 80 |   `val authRemote: String?` |

## feature/projects/git/MergeOpsSheet.kt

| Line | Declaration |
|---|---|
| 15 | `@Composable fun MergeOpsSheet(vm: MergeOpsViewModel, client: CodegClient, path: String, branch: String?, onDismiss: () -> Unit)` |
| 16 |   `val ui by vm.ui.collectAsStateWithLifecycle()` |
| 17 |   `var action by remember` |
| 18 |   `var target by remember` |
| 19 |   `var mode by remember` |
| 20 |   `var confirm by remember` |
| 21 |   `var typed by remember` |
| 22 |   `var hardVerified by remember` |
| 71 | `@Composable internal fun operationLabel(value: String): String` |

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

## feature/projects/git/StashSheet.kt

| Line | Declaration |
|---|---|
| 17 | `@Composable fun StashSheet(viewModel: StashViewModel, onDismiss: () -> Unit, onChanged: () -> Unit)` |
| 18 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 19 |   `var message by remember` |
| 20 |   `var keepIndex by remember` |
| 21 |   `var confirmation by remember` |
| 22 |   `val colors` |
| 71 | `@Composable private fun pathLabel(count: Int): String` |

## feature/projects/git/StashViewModel.kt

| Line | Declaration |
|---|---|
| 15 | `class StashViewModel(private val client: CodegClient, private val path: String) : ViewModel()` |
| 16 |   `data class Ui( val entries: List<StashEntry> = emptyList(), val supported: Boolean = true, val busy: Boolean = false, val error: String? = null, val result: String? = null, val diff: String? = null, val diffRef: String? = null, )` |
| 25 |   `private val state` |
| 26 |   `val ui` |
| 28 |   `fun refresh()` |
| 30 |   `fun show(ref: String)` |
| 37 |   `fun change(action: String, ref: String?, message: String, keepIndex: Boolean, onChanged: () -> Unit)` |
| 55 |   `private fun run(block: suspend () -> Unit)` |

## feature/projects/GitChangeVisuals.kt

| Line | Declaration |
|---|---|
| 12 | `@Composable internal fun GitChangeBadge(change: GitChange, modifier: Modifier = Modifier)` |

## feature/projects/GitCredentialSheet.kt

| Line | Declaration |
|---|---|
| 56 | `@Composable fun GitCredentialSheet( viewModel: ProjectDetailViewModel, prompt: GitCredentialPrompt, )` |
| 60 |   `val context` |
| 61 |   `val colors` |
| 62 |   `val scope` |
| 63 |   `val uriHandler` |
| 65 |   `var token by remember` |
| 66 |   `var username by remember` |
| 67 |   `var password by remember` |
| 68 |   `var saveCredentials by remember` |
| 69 |   `var submitting by remember` |
| 70 |   `var error by remember` |
| 72 |   `val hostLabel` |
| 73 |   `val serverUrl` |
| 74 |   `val invalidTokenMsg` |
| 76 |   `val canSubmit` |
| 82 |   `fun submit()` |
| 166 | `@Composable private fun Banner(message: String, tint: Color, textColor: Color)` |

## feature/projects/GitStatusStrip.kt

| Line | Declaration |
|---|---|
| 39 | `@Composable internal fun GitStatusStrip( busy: Boolean, busyTitle: String?, banner: GitBanner?, onDismissBanner: () -> Unit, modifier: Modifier = Modifier, )` |
| 46 |   `val colors` |

## feature/projects/groups/FolderLayout.kt

| Line | Declaration |
|---|---|
| 6 | `data class FolderEntry(val folder: FolderDetail? = null, val group: FolderGroup? = null)` |
| 8 | `fun folderLayout(folders: List<FolderDetail>, groups: List<FolderGroup>): List<FolderEntry>` |
| 9 |   `val ids` |
| 17 | `fun groupMembers(folders: List<FolderDetail>, id: Int): List<FolderDetail>` |
| 20 | `fun folderDisplayName(folder: FolderDetail): String` |

## feature/projects/groups/GroupDialogs.kt

| Line | Declaration |
|---|---|
| 20 | `@Composable fun folderTint(value: String): androidx.compose.ui.graphics.Color` |
| 21 |   `val colors` |
| 24 |   `val preset` |
| 29 | `@Composable fun GroupTextDialog(title: String, initial: String, allowEmpty: Boolean = false, onDismiss: () -> Unit, onSave: (String) -> Unit)` |
| 30 |   `var value by remember` |
| 42 | `@Composable fun FolderEditDialog(folder: FolderDetail, groups: List<FolderGroup>, onDismiss: () -> Unit, onAlias: (String) -> Unit, onColor: (String) -> Unit, onGroup: (Int?) -> Unit)` |
| 44 |   `var page by remember` |

## feature/projects/importsessions/ImportSessionsSheet.kt

| Line | Declaration |
|---|---|
| 16 | `@Composable fun ImportSessionsSheet(ui: ImportSessionsUi, viewModel: ImportSessionsViewModel, onDismiss: () -> Unit, onImported: () -> Unit)` |
| 17 |   `var confirmAll by remember` |
| 18 |   `val all` |

## feature/projects/importsessions/ImportSessionsViewModel.kt

| Line | Declaration |
|---|---|
| 16 | `internal fun unsupportedImport(e: Exception): Boolean` |
| 19 | `data class ImportSessionsUi( val supported: Boolean = false, val busy: Boolean = false, val scan: ImportScanResult? = null, val selected: Set<ImportSessionKey> = emptySet(), val result: ImportSessionsResult? = null, val error: String? = null, )` |
| 20 |   `val supported: Boolean` |
| 21 |   `val busy: Boolean` |
| 22 |   `val scan: ImportScanResult?` |
| 23 |   `val selected: Set<ImportSessionKey>` |
| 24 |   `val result: ImportSessionsResult?` |
| 25 |   `val error: String?` |
| 29 | `class ImportSessionsViewModel @Inject constructor(private val repository: ServerRepository) : ViewModel()` |
| 30 |   `private val state` |
| 31 |   `val ui: StateFlow<ImportSessionsUi>` |
| 32 |   `private var client: CodegClient?` |
| 47 |   `private suspend fun scanWith(c: CodegClient)` |
| 58 |   `fun scan()` |
| 64 |   `fun toggle(keys: List<ImportSessionKey>)` |
| 69 |   `fun import(all: Boolean = false, onImported: () -> Unit)` |

## feature/projects/ProjectDetailScreen.kt

| Line | Declaration |
|---|---|
| 56 | `private enum class ProjectTab(val labelRes: Int)` |
| 64 | `@Composable fun ProjectDetailScreen( onBack: () -> Unit, viewModel: ProjectDetailViewModel = hiltViewModel(), )` |
| 68 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 69 |   `val colors` |
| 70 |   `var tab by remember` |
| 71 |   `var showBranchPicker by remember` |
| 72 |   `var showCommit by remember` |
| 76 |   `val commitClient` |
| 77 |   `val commitFolder` |
| 156 | `@Composable private fun Header(name: String, path: String, color: Color, branch: String?, running: Int, sessions: Int, onBranchClick: () -> Unit = {})` |
| 157 |   `val colors` |
| 181 | `@Composable private fun ProjectTabs(selected: ProjectTab, onSelect: (ProjectTab) -> Unit)` |
| 182 |   `val entries` |

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
| 74 | `@Composable fun ProjectListScreen( onOpenFolder: (Int) -> Unit, viewModel: ProjectsViewModel = hiltViewModel(), )` |
| 78 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 79 |   `val colors` |
| 80 |   `val importViewModel: app.codeg.android.feature.projects.importsessions.ImportSessionsViewModel` |
| 81 |   `val importUi by importViewModel.ui.collectAsStateWithLifecycle()` |
| 82 |   `var showImport by remember` |
| 83 |   `var menuOpen by remember` |
| 84 |   `var showBrowser by remember` |
| 85 |   `var showClone by remember` |
| 86 |   `var createGroup by remember` |
| 87 |   `var editFolder by remember` |
| 88 |   `var renameGroup by remember` |
| 89 |   `var deleteGroup by remember` |
| 90 |   `var collapsed by remember` |
| 354 | `@Composable private fun FolderRow( folder: FolderDetail, running: Int, modifier: Modifier = Modifier, onLongClick: (() -> Unit)? = null, onClick: () -> Unit, )` |
| 361 |   `val colors` |
| 362 |   `val tile` |
| 384 | `@Composable private fun RunningPill(count: Int, modifier: Modifier = Modifier)` |
| 390 | `@Composable fun BranchPill(branch: String, modifier: Modifier = Modifier)` |
| 394 | `internal fun sortedFolders(folders: List<FolderDetail>): List<FolderDetail>` |

## feature/projects/ProjectsViewModel.kt

| Line | Declaration |
|---|---|
| 29 | `class ProjectsViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 31 |   `private val repository: ServerRepository` |
| 34 |   `private val _ui` |
| 35 |   `val ui: StateFlow<ProjectsUiState>` |
| 37 |   `private var client: CodegClient?` |
| 38 |   `private var loadedEndpoint: String?` |
| 51 |   `private suspend fun onProfile(profile: ServerProfile?)` |
| 65 |   `fun refresh()` |
| 70 |   `private suspend fun fetch(initial: Boolean)` |
| 96 |   `private fun unsupported(e: Exception): Boolean` |
| 100 |   `fun editGroup(action: suspend (CodegClient) -> Unit)` |
| 120 |   `fun openFolder(path: String, onResult: (String?) -> Unit)` |
| 130 |   `suspend fun homeDirectory(): String` |
| 134 |   `suspend fun listDirectories(path: String): List<app.codeg.android.core.model.DirectoryEntry>` |
| 138 |   `suspend fun clone( url: String, targetDir: String, credentials: app.codeg.android.core.model.GitCredentials?, ): String?` |
| 155 | `data class ProjectsUiState( val groups: List<FolderGroup> = emptyList(), val groupsSupported: Boolean = false, val editing: Boolean = false, val folders: List<FolderDetail> = emptyList(), val conversations: List<ConversationSummary> = emptyList(), val loading: Boolean = false, val refreshing: Boolean = false, val hasLoaded: Boolean = false, val error: String? = null, )` |
| 156 |   `val groups: List<FolderGroup>` |
| 157 |   `val groupsSupported: Boolean` |
| 158 |   `val editing: Boolean` |
| 159 |   `val folders: List<FolderDetail>` |
| 160 |   `val conversations: List<ConversationSummary>` |
| 161 |   `val loading: Boolean` |
| 162 |   `val refreshing: Boolean` |
| 163 |   `val hasLoaded: Boolean` |
| 164 |   `val error: String?` |
| 166 |   `val isBusy: Boolean get()` |
| 167 |   `fun runningCount(folderId: Int): Int` |

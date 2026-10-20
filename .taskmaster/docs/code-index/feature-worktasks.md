# feature/worktasks

Source root: `app/src/main/java/app/codeg/android/`

## feature/worktasks/WorkTaskBoard.kt

| Line | Declaration |
|---|---|
| 7 | `fun WorkTask.boardColumn(): Int` |
| 15 | `fun boardTasks(tasks: List<WorkTask>, showCanceled: Boolean): List<WorkTask>` |
| 19 | `fun WorkTask.availableActions(): List<String>` |
| 44 | `fun taskConfig(original: JsonObject, description: String, agent: String?): JsonObject` |
| 58 | `fun reorderedTaskIds(tasks: List<WorkTask>, task: WorkTask, delta: Int): List<Int>?` |
| 59 |   `val project` |
| 60 |   `val column` |
| 61 |   `val index` |
| 63 |   `val neighbor` |
| 66 |   `var next` |

## feature/worktasks/WorkTaskControls.kt

| Line | Declaration |
|---|---|
| 17 | `fun actionLabel(action: String): Int` |
| 35 | `@Composable fun WorkTaskActions(task: WorkTask, ui: WorkTaskUi, viewModel: WorkTaskViewModel, compact: Boolean = false)` |
| 36 |   `var pending by remember(task.id)` |
| 37 |   `var text by remember` |
| 87 | `@Composable private fun Choice(label: String, value: String, choices: List<Pair<String, () -> Unit>>, enabled: Boolean, selectedIndex: Int = choices.indexOfFirst { it.first == value })` |
| 94 | `@Composable fun WorkTaskEditor(ui: WorkTaskUi, viewModel: WorkTaskViewModel)` |
| 95 |   `val task` |
| 96 |   `var title by remember` |
| 97 |   `var original by remember` |
| 98 |   `var description by remember` |
| 99 |   `var folderId by remember` |
| 100 |   `var agent by remember` |
| 101 |   `var templateName by remember` |
| 102 |   `val inherit` |

## feature/worktasks/WorkTaskLabels.kt

| Line | Declaration |
|---|---|
| 12 | `@Composable fun workTaskStatusLabel(status: String): String` |
| 27 | `private val timeFormatter` |
| 30 | `fun formatWorkTaskTime(iso: String): String` |

## feature/worktasks/WorkTaskScreen.kt

| Line | Declaration |
|---|---|
| 23 | `@Composable fun WorkTaskScreen(onBack: () -> Unit, viewModel: WorkTaskViewModel = hiltViewModel())` |
| 24 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 25 |   `var column by remember` |
| 26 |   `var showCanceled by remember` |
| 27 |   `val visible` |
| 28 |   `val selected` |
| 30 |   `val back` |

## feature/worktasks/WorkTaskSettingsSheet.kt

| Line | Declaration |
|---|---|
| 14 | `internal fun settingsValue(key: String, text: String): JsonElement?` |
| 35 | `private val settingsLabels` |
| 53 | `private val booleanSettings` |
| 56 | `@Composable fun WorkTaskSettingsSheet(ui: WorkTaskUi, viewModel: WorkTaskViewModel)` |
| 57 |   `var reset by remember` |
| 58 |   `val source` |
| 59 |   `var fields by remember(ui.settingsFolder, source)` |
| 68 |   `val parsed` |
| 73 |   `val enabled` |
| 74 |   `val folder` |
| 134 | `@Composable private fun SettingsSummary(settings: JsonObject)` |

## feature/worktasks/WorkTaskTemplates.kt

| Line | Declaration |
|---|---|
| 18 | `@Composable fun WorkTaskManagementMenu(ui: WorkTaskUi, viewModel: WorkTaskViewModel)` |
| 19 |   `var expanded by remember` |
| 39 | `@Composable fun WorkTaskTemplates(ui: WorkTaskUi, viewModel: WorkTaskViewModel)` |
| 40 |   `var editing by remember` |
| 41 |   `var original by remember` |
| 42 |   `var name by remember` |
| 43 |   `var title by remember` |
| 44 |   `var description by remember` |
| 45 |   `var deleting by remember` |

## feature/worktasks/WorkTaskViewModel.kt

| Line | Declaration |
|---|---|
| 17 | `data class WorkTaskUi( val loading: Boolean = true, val supported: Boolean = false, val tasks: List<WorkTask> = emptyList(), val attention: Long = 0, val selected: WorkTask? = null, val detailLoading: Boolean = false, val events: List<WorkTaskEvent> = emptyList(), val files: List<WorkTaskChangedFile> = emptyList(), val diff: String = "", val error: String? = null, val detailErrors: List<String> = emptyList(), val busy: Boolean = false, val editorOpen: Boolean = false, val editing: WorkTask? = null, val folders: List<FolderDetail> = emptyList(), val agents: List<AcpAgentInfo> = emptyList(), val templates: List<WorkTaskTemplate> = emptyList(), val templateRevision: Int = 0, val templatesSupported: Boolean = false, val settingsSupported: Boolean = false, val templatesOpen: Boolean = false, val settingsOpen: Boolean = false, val settingsFolder: Int? = null, val settingsLoading: Boolean = false, val effectiveSettings: kotlinx.serialization.json.JsonObject? = null, val ownSettings: kotlinx.serialization.json.JsonObject? = null, val hiddenActions: Set<String> = emptySet(), )` |
| 18 |   `val loading: Boolean` |
| 19 |   `val supported: Boolean` |
| 20 |   `val tasks: List<WorkTask>` |
| 21 |   `val attention: Long` |
| 22 |   `val selected: WorkTask?` |
| 23 |   `val detailLoading: Boolean` |
| 24 |   `val events: List<WorkTaskEvent>` |
| 25 |   `val files: List<WorkTaskChangedFile>` |
| 26 |   `val diff: String` |
| 27 |   `val error: String?` |
| 28 |   `val detailErrors: List<String>` |
| 29 |   `val busy: Boolean` |
| 30 |   `val editorOpen: Boolean` |
| 31 |   `val editing: WorkTask?` |
| 32 |   `val folders: List<FolderDetail>` |
| 33 |   `val agents: List<AcpAgentInfo>` |
| 34 |   `val templates: List<WorkTaskTemplate>` |
| 35 |   `val templateRevision: Int` |
| 36 |   `val templatesSupported: Boolean` |
| 37 |   `val settingsSupported: Boolean` |
| 38 |   `val templatesOpen: Boolean` |
| 39 |   `val settingsOpen: Boolean` |
| 40 |   `val settingsFolder: Int?` |
| 41 |   `val settingsLoading: Boolean` |
| 42 |   `val effectiveSettings: kotlinx.serialization.json.JsonObject?` |
| 43 |   `val ownSettings: kotlinx.serialization.json.JsonObject?` |
| 44 |   `val hiddenActions: Set<String>` |
| 48 | `class WorkTaskViewModel @Inject constructor(private val repository: ServerRepository) : ViewModel()` |
| 49 |   `private val _ui` |
| 50 |   `val ui: StateFlow<WorkTaskUi>` |
| 51 |   `private var client: CodegClient?` |
| 52 |   `private var loadJob: Job?` |
| 53 |   `private var detailJob: Job?` |
| 54 |   `private var mutationJob: Job?` |
| 55 |   `private var editorJob: Job?` |
| 57 |   `private var managementJob: Job?` |
| 58 |   `private var settingsJob: Job?` |
| 81 |   `fun load()` |
| 98 |   `private suspend fun probeManagement(c: CodegClient)` |
| 99 |     `suspend fun probe(block: suspend () -> Unit)` |
| 119 |   `fun openTemplates()` |
| 127 |   `fun closeManagement()` |
| 133 |   `fun openSettings()` |
| 137 |   `fun loadSettings(folderId: Int)` |
| 156 |   `private fun manage(kind: String, block: suspend (CodegClient) -> Unit)` |
| 174 |   `fun saveTemplate(draft: WorkTaskTemplateDraft)` |
| 180 |   `fun deleteTemplate(id: Int)` |
| 186 |   `fun saveSettings(folderId: Int, settings: kotlinx.serialization.json.JsonObject)` |
| 193 |   `fun resetSettings(folderId: Int)` |
| 200 |   `fun dismissEditor()` |
| 202 |   `fun edit(task: WorkTask? = null)` |
| 226 |   `private fun mutate(action: String, block: suspend (CodegClient) -> Unit)` |
| 249 |   `fun save(draft: WorkTaskDraft)` |
| 257 |   `fun startAll()` |
| 259 |   `fun action(task: WorkTask, action: String, text: String = "")` |
| 267 |   `fun move(task: WorkTask, delta: Int)` |
| 273 |   `fun select(task: WorkTask?)` |

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
| 18 | `fun actionLabel(action: String): Int` |
| 36 | `@Composable fun WorkTaskActions(task: WorkTask, ui: WorkTaskUi, viewModel: WorkTaskViewModel, compact: Boolean = false)` |
| 37 |   `var pending by remember(task.id)` |
| 38 |   `var text by remember` |
| 87 | `@Composable private fun Choice(label: String, value: String, choices: List<Pair<String, () -> Unit>>, enabled: Boolean)` |
| 88 |   `var expanded by remember` |
| 100 | `@Composable fun WorkTaskEditor(ui: WorkTaskUi, viewModel: WorkTaskViewModel)` |
| 101 |   `val task` |
| 102 |   `var title by remember` |
| 103 |   `var original by remember` |
| 104 |   `var description by remember` |
| 105 |   `var folderId by remember` |
| 106 |   `var agent by remember` |
| 107 |   `var templateName by remember` |
| 108 |   `val inherit` |

## feature/worktasks/WorkTaskLabels.kt

| Line | Declaration |
|---|---|
| 12 | `@Composable fun workTaskStatusLabel(status: String): String` |
| 27 | `private val timeFormatter` |
| 30 | `fun formatWorkTaskTime(iso: String): String` |

## feature/worktasks/WorkTaskScreen.kt

| Line | Declaration |
|---|---|
| 27 | `@Composable fun WorkTaskScreen(onBack: () -> Unit, viewModel: WorkTaskViewModel = hiltViewModel())` |
| 28 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 29 |   `var column by remember` |
| 30 |   `var showCanceled by remember` |
| 31 |   `val visible` |
| 32 |   `val selected` |
| 34 |   `val back` |

## feature/worktasks/WorkTaskSettingsSheet.kt

| Line | Declaration |
|---|---|
| 15 | `internal fun settingsValue(key: String, text: String): JsonElement?` |
| 36 | `private val settingsLabels` |
| 54 | `private val booleanSettings` |
| 57 | `@Composable fun WorkTaskSettingsSheet(ui: WorkTaskUi, viewModel: WorkTaskViewModel)` |
| 58 |   `var projectMenu by remember` |
| 59 |   `var reset by remember` |
| 60 |   `val source` |
| 61 |   `var fields by remember(ui.settingsFolder, source)` |
| 70 |   `val parsed` |
| 75 |   `val enabled` |
| 76 |   `val folder` |
| 152 | `@Composable private fun SettingsSummary(settings: JsonObject)` |

## feature/worktasks/WorkTaskTemplates.kt

| Line | Declaration |
|---|---|
| 21 | `@Composable fun WorkTaskManagementMenu(ui: WorkTaskUi, viewModel: WorkTaskViewModel)` |
| 22 |   `var expanded by remember` |
| 42 | `@Composable fun WorkTaskTemplates(ui: WorkTaskUi, viewModel: WorkTaskViewModel)` |
| 43 |   `var editing by remember` |
| 44 |   `var original by remember` |
| 45 |   `var name by remember` |
| 46 |   `var title by remember` |
| 47 |   `var description by remember` |
| 48 |   `var deleting by remember` |

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

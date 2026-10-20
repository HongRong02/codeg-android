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
| 107 |   `val inherit` |

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

## feature/worktasks/WorkTaskViewModel.kt

| Line | Declaration |
|---|---|
| 17 | `data class WorkTaskUi( val loading: Boolean = true, val supported: Boolean = false, val tasks: List<WorkTask> = emptyList(), val attention: Long = 0, val selected: WorkTask? = null, val detailLoading: Boolean = false, val events: List<WorkTaskEvent> = emptyList(), val files: List<WorkTaskChangedFile> = emptyList(), val diff: String = "", val error: String? = null, val detailErrors: List<String> = emptyList(), val busy: Boolean = false, val editorOpen: Boolean = false, val editing: WorkTask? = null, val folders: List<FolderDetail> = emptyList(), val agents: List<AcpAgentInfo> = emptyList(), val templates: List<WorkTaskTemplate> = emptyList(), val hiddenActions: Set<String> = emptySet(), )` |
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
| 35 |   `val hiddenActions: Set<String>` |
| 39 | `class WorkTaskViewModel @Inject constructor(private val repository: ServerRepository) : ViewModel()` |
| 40 |   `private val _ui` |
| 41 |   `val ui: StateFlow<WorkTaskUi>` |
| 42 |   `private var client: CodegClient?` |
| 43 |   `private var loadJob: Job?` |
| 44 |   `private var detailJob: Job?` |
| 45 |   `private var mutationJob: Job?` |
| 46 |   `private var editorJob: Job?` |
| 67 |   `fun load()` |
| 83 |   `fun dismissEditor()` |
| 85 |   `fun edit(task: WorkTask? = null)` |
| 109 |   `private fun mutate(action: String, block: suspend (CodegClient) -> Unit)` |
| 132 |   `fun save(draft: WorkTaskDraft)` |
| 140 |   `fun startAll()` |
| 142 |   `fun action(task: WorkTask, action: String, text: String = "")` |
| 150 |   `fun move(task: WorkTask, delta: Int)` |
| 156 |   `fun select(task: WorkTask?)` |

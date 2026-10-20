# feature/worktasks

Source root: `app/src/main/java/app/codeg/android/`

## feature/worktasks/WorkTaskScreen.kt

| Line | Declaration |
|---|---|
| 27 | `@Composable fun WorkTaskScreen(onBack: () -> Unit, viewModel: WorkTaskViewModel = hiltViewModel())` |
| 28 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 29 |   `val selected` |
| 30 |   `val back` |

## feature/worktasks/WorkTaskViewModel.kt

| Line | Declaration |
|---|---|
| 16 | `data class WorkTaskUi( val loading: Boolean = true, val supported: Boolean = false, val tasks: List<WorkTask> = emptyList(), val attention: Long = 0, val selected: WorkTask? = null, val detailLoading: Boolean = false, val events: List<WorkTaskEvent> = emptyList(), val files: List<WorkTaskChangedFile> = emptyList(), val diff: String = "", val error: String? = null, val detailErrors: List<String> = emptyList(), )` |
| 17 |   `val loading: Boolean` |
| 18 |   `val supported: Boolean` |
| 19 |   `val tasks: List<WorkTask>` |
| 20 |   `val attention: Long` |
| 21 |   `val selected: WorkTask?` |
| 22 |   `val detailLoading: Boolean` |
| 23 |   `val events: List<WorkTaskEvent>` |
| 24 |   `val files: List<WorkTaskChangedFile>` |
| 25 |   `val diff: String` |
| 26 |   `val error: String?` |
| 27 |   `val detailErrors: List<String>` |
| 31 | `class WorkTaskViewModel @Inject constructor(private val repository: ServerRepository) : ViewModel()` |
| 32 |   `private val _ui` |
| 33 |   `val ui: StateFlow<WorkTaskUi>` |
| 34 |   `private var client: CodegClient?` |
| 35 |   `private var loadJob: Job?` |
| 36 |   `private var detailJob: Job?` |
| 55 |   `fun load()` |
| 71 |   `fun select(task: WorkTask?)` |

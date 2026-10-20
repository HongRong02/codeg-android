# feature/server

Source root: `app/src/main/java/app/codeg/android/`

## feature/server/ServerEditorScreen.kt

| Line | Declaration |
|---|---|
| 33 | `@Composable fun ServerEditorScreen( onDone: () -> Unit, viewModel: ServerEditorViewModel = hiltViewModel(), )` |
| 37 |   `val colors` |
| 38 |   `val toaster` |
| 39 |   `val test` |
| 40 |   `val testMessage` |

## feature/server/ServerEditorViewModel.kt

| Line | Declaration |
|---|---|
| 23 | `class ServerEditorViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, savedStateHandle: SavedStateHandle, ) : ViewModel()` |
| 25 |   `private val repository: ServerRepository` |
| 29 |   `private val editingId: String?` |
| 30 |   `val isEditing: Boolean` |
| 32 |   `var name by mutableStateOf("")` |
| 34 |   `var url by mutableStateOf("")` |
| 36 |   `var token by mutableStateOf("")` |
| 38 |   `var test by mutableStateOf<TestState>(TestState.Idle)` |
| 45 |   `fun onNameChange(value: String)` |
| 46 |   `fun onUrlChange(value: String)` |
| 47 |   `fun onTokenChange(value: String)` |
| 49 |   `val canSave: Boolean` |
| 52 |   `private fun loadExisting(id: String)` |
| 61 |   `fun testConnection()` |
| 73 |   `fun save(onDone: () -> Unit)` |
| 88 | `sealed interface TestState` |
| 89 |   `data object Idle : TestState` |
| 90 |   `data object Testing : TestState` |
| 91 |   `data class Success(val version: String) : TestState` |
| 92 |   `data class Failure(val message: String) : TestState` |

## feature/server/ServerListScreen.kt

| Line | Declaration |
|---|---|
| 30 | `@Composable fun ServerListScreen( servers: List<ServerProfile>, selectedId: String?, onSelect: (String) -> Unit, onDelete: (String) -> Unit, onAdd: () -> Unit, onEdit: (String) -> Unit, onBack: () -> Unit, )` |
| 39 |   `val colors` |
| 40 |   `var pendingDelete by remember` |
| 97 |   `val toDelete` |
| 112 | `@Composable private fun ServerRow( profile: ServerProfile, selected: Boolean, onClick: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit, )` |
| 119 |   `val colors` |
| 120 |   `var menuOpen by remember` |

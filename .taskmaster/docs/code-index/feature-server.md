# feature/server

Source root: `app/src/main/java/app/codeg/android/`

## feature/server/ServerEditorScreen.kt

| Line | Declaration |
|---|---|
| 35 | `@Composable fun ServerEditorScreen( onDone: () -> Unit, viewModel: ServerEditorViewModel = hiltViewModel(), )` |
| 39 |   `val colors` |

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
| 37 | `@Composable fun ServerListScreen( servers: List<ServerProfile>, selectedId: String?, onSelect: (String) -> Unit, onDelete: (String) -> Unit, onAdd: () -> Unit, onEdit: (String) -> Unit, onBack: () -> Unit, )` |
| 46 |   `val colors` |
| 47 |   `var pendingDelete by remember` |
| 104 |   `val toDelete` |
| 119 | `@Composable private fun ServerRow( profile: ServerProfile, selected: Boolean, onClick: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit, )` |
| 126 |   `val colors` |
| 127 |   `var menuOpen by remember` |

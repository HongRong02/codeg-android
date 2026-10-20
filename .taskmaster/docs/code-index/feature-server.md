# feature/server

Source root: `app/src/main/java/app/codeg/android/`

## feature/server/ServerEditorScreen.kt

| Line | Declaration |
|---|---|
| 41 | `@Composable fun ServerEditorScreen( onDone: () -> Unit, viewModel: ServerEditorViewModel = hiltViewModel(), )` |
| 45 |   `val colors` |
| 126 | `@Composable private fun SectionHeader(text: String)` |

## feature/server/ServerEditorViewModel.kt

| Line | Declaration |
|---|---|
| 21 | `class ServerEditorViewModel @Inject constructor( private val repository: ServerRepository, savedStateHandle: SavedStateHandle, ) : ViewModel()` |
| 22 |   `private val repository: ServerRepository` |
| 26 |   `private val editingId: String?` |
| 27 |   `val isEditing: Boolean` |
| 29 |   `var name by mutableStateOf("")` |
| 31 |   `var url by mutableStateOf("")` |
| 33 |   `var token by mutableStateOf("")` |
| 35 |   `var test by mutableStateOf<TestState>(TestState.Idle)` |
| 42 |   `fun onNameChange(value: String)` |
| 43 |   `fun onUrlChange(value: String)` |
| 44 |   `fun onTokenChange(value: String)` |
| 46 |   `val canSave: Boolean` |
| 49 |   `private fun loadExisting(id: String)` |
| 58 |   `fun testConnection()` |
| 70 |   `fun save(onDone: () -> Unit)` |
| 85 | `sealed interface TestState` |
| 86 |   `data object Idle : TestState` |
| 87 |   `data object Testing : TestState` |
| 88 |   `data class Success(val version: String) : TestState` |
| 89 |   `data class Failure(val message: String) : TestState` |

## feature/server/ServerListScreen.kt

| Line | Declaration |
|---|---|
| 53 | `@Composable fun ServerListScreen( servers: List<ServerProfile>, selectedId: String?, onSelect: (String) -> Unit, onDelete: (String) -> Unit, onAdd: () -> Unit, onEdit: (String) -> Unit, onBack: () -> Unit, )` |
| 62 |   `val colors` |
| 63 |   `var pendingDelete by remember` |
| 117 |   `val toDelete` |
| 138 | `@Composable private fun ServerRow( profile: ServerProfile, selected: Boolean, onClick: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit, )` |
| 145 |   `val colors` |
| 146 |   `var menuOpen by remember` |

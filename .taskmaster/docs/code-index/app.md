# app

Source root: `app/src/main/java/app/codeg/android/`

## app/AppViewModel.kt

| Line | Declaration |
|---|---|
| 21 | `class AppViewModel @Inject constructor( private val serverRepository: ServerRepository, appSettingsStore: AppSettingsStore, ) : ViewModel()` |
| 22 |   `private val serverRepository: ServerRepository` |
| 26 |   `val settings: StateFlow<AppSettings>` |
| 30 |   `val servers: StateFlow<List<ServerProfile>?>` |
| 33 |   `val selectedProfile: StateFlow<ServerProfile?>` |
| 36 |   `fun selectServer(id: String)` |
| 40 |   `fun deleteServer(id: String)` |

## app/CodegApp.kt

| Line | Declaration |
|---|---|
| 30 | `@Composable fun CodegApp(appViewModel: AppViewModel = hiltViewModel())` |
| 31 |   `val settings by appViewModel.settings.collectAsStateWithLifecycle()` |
| 46 | `@Composable private fun OnboardingRoot()` |
| 47 |   `val nav` |
| 59 | `@Composable private fun Splash()` |

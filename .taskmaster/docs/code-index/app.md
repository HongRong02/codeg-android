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
| 31 | `@Composable fun CodegApp(appViewModel: AppViewModel = hiltViewModel())` |
| 32 |   `val settings by appViewModel.settings.collectAsStateWithLifecycle()` |
| 49 | `@Composable private fun OnboardingRoot()` |
| 50 |   `val nav` |
| 62 | `@Composable private fun Splash()` |

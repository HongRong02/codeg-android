# core/update

Source root: `app/src/main/java/app/codeg/android/`

## core/update/UpdateBanner.kt

| Line | Declaration |
|---|---|
| 31 | `@Composable fun UpdateBanner(viewModel: UpdateViewModel = hiltViewModel())` |
| 32 |   `val state by viewModel.ui.collectAsStateWithLifecycle()` |
| 33 |   `val context` |
| 34 |   `var showNotes by remember` |
| 35 |   `var pendingPermission by rememberSaveable` |
| 36 |   `fun install(file: File)` |
| 44 |   `val permission` |
| 66 |   `val colors` |
| 91 | `@Composable fun UpdateAbout(viewModel: UpdateViewModel = hiltViewModel())` |
| 92 |   `val state by viewModel.ui.collectAsStateWithLifecycle()` |
| 93 |   `var showNotes by remember` |
| 94 |   `var manual by remember` |
| 106 | `@Composable private fun UpdateNotes(state: UpdateUi, dismiss: () -> Unit, download: () -> Unit)` |
| 107 |   `val update` |

## core/update/UpdateChecker.kt

| Line | Declaration |
|---|---|
| 10 | `data class AppUpdate( val versionName: String, val versionCode: Int, val notes: String, val apkUrl: String, val sha256Url: String?, val size: Long, )` |
| 11 |   `val versionName: String` |
| 12 |   `val versionCode: Int` |
| 13 |   `val notes: String` |
| 14 |   `val apkUrl: String` |
| 15 |   `val sha256Url: String?` |
| 16 |   `val size: Long` |
| 20 | `class UpdateChecker(private val client: HttpClient = HttpClient(OkHttp) { expectSuccess = true })` |
| 21 |   `suspend fun check(currentVersion: Int, manual: Boolean = false): AppUpdate?` |
| 29 |   `companion object` |
| 30 |     `fun versionCode(tag: String): Int?` |
| 32 |     `fun parse(body: String, currentVersion: Int): AppUpdate?` |

## core/update/UpdateDownloader.kt

| Line | Declaration |
|---|---|
| 15 | `class UpdateDownloader @Inject constructor(@ApplicationContext private val context: Context)` |
| 16 |   `private val client` |
| 18 |   `suspend fun download(update: AppUpdate, progress: (Float?) -> Unit): File` |

## core/update/UpdateViewModel.kt

| Line | Declaration |
|---|---|
| 25 | `data class UpdateUi( val update: AppUpdate? = null, val dismissed: Int? = null, val checking: Boolean = false, val downloading: Boolean = false, val progress: Float? = null, val message: Int? = null, val installer: File? = null, )` |
| 26 |   `val update: AppUpdate?` |
| 27 |   `val dismissed: Int?` |
| 28 |   `val checking: Boolean` |
| 29 |   `val downloading: Boolean` |
| 30 |   `val progress: Float?` |
| 31 |   `val message: Int?` |
| 32 |   `val installer: File?` |
| 37 | `class UpdateRepository @Inject constructor( private val preferences: DataStore<Preferences>, private val downloader: UpdateDownloader, )` |
| 38 |   `private val preferences: DataStore<Preferences>` |
| 39 |   `private val downloader: UpdateDownloader` |
| 41 |   `private val mutable` |
| 42 |   `val state` |
| 43 |   `private val checker` |
| 44 |   `private val scope` |
| 45 |   `private var started` |
| 46 |   `private val lastCheck` |
| 48 |   `suspend fun check(manual: Boolean)` |
| 69 |   `fun dismiss()` |
| 70 |   `fun installerHandled()` |
| 71 |   `fun installFailed()` |
| 73 |   `fun download()` |
| 77 |   `private suspend fun downloadInBackground()` |
| 95 | `class UpdateViewModel @Inject constructor(private val repository: UpdateRepository) : ViewModel()` |
| 96 |   `val ui` |
| 97 |   `fun check(manual: Boolean = false)` |
| 98 |   `fun download()` |
| 99 |   `fun dismiss()` |
| 100 |   `fun installerHandled()` |
| 101 |   `fun installFailed()` |

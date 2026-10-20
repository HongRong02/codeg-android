# core/update

Source root: `app/src/main/java/app/codeg/android/`

## core/update/UpdateBanner.kt

| Line | Declaration |
|---|---|
| 29 | `@Composable fun UpdateBanner(viewModel: UpdateViewModel = hiltViewModel())` |
| 30 |   `val state by viewModel.ui.collectAsStateWithLifecycle()` |
| 31 |   `val context` |
| 32 |   `var showNotes by remember` |
| 33 |   `var pendingPermission by remember` |
| 34 |   `fun install(file: File)` |
| 42 |   `val permission` |
| 64 |   `val colors` |
| 89 | `@Composable fun UpdateAbout(viewModel: UpdateViewModel = hiltViewModel())` |
| 90 |   `val state by viewModel.ui.collectAsStateWithLifecycle()` |
| 91 |   `var showNotes by remember` |
| 92 |   `var manual by remember` |
| 104 | `@Composable private fun UpdateNotes(state: UpdateUi, dismiss: () -> Unit, download: () -> Unit)` |
| 105 |   `val update` |

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
| 23 | `data class UpdateUi( val update: AppUpdate? = null, val dismissed: Int? = null, val checking: Boolean = false, val downloading: Boolean = false, val progress: Float? = null, val message: Int? = null, val installer: File? = null, )` |
| 24 |   `val update: AppUpdate?` |
| 25 |   `val dismissed: Int?` |
| 26 |   `val checking: Boolean` |
| 27 |   `val downloading: Boolean` |
| 28 |   `val progress: Float?` |
| 29 |   `val message: Int?` |
| 30 |   `val installer: File?` |
| 35 | `class UpdateRepository @Inject constructor( private val preferences: DataStore<Preferences>, private val downloader: UpdateDownloader, )` |
| 36 |   `private val preferences: DataStore<Preferences>` |
| 37 |   `private val downloader: UpdateDownloader` |
| 39 |   `private val mutable` |
| 40 |   `val state` |
| 41 |   `private val checker` |
| 42 |   `private var started` |
| 43 |   `private val lastCheck` |
| 45 |   `suspend fun check(manual: Boolean)` |
| 66 |   `fun dismiss()` |
| 67 |   `fun installerHandled()` |
| 68 |   `fun installFailed()` |
| 70 |   `suspend fun download()` |
| 88 | `class UpdateViewModel @Inject constructor(private val repository: UpdateRepository) : ViewModel()` |
| 89 |   `val ui` |
| 90 |   `fun check(manual: Boolean = false)` |
| 91 |   `fun download()` |
| 92 |   `fun dismiss()` |
| 93 |   `fun installerHandled()` |
| 94 |   `fun installFailed()` |

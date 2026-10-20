# core/update

Source root: `app/src/main/java/app/codeg/android/`

## core/update/UpdateBanner.kt

| Line | Declaration |
|---|---|
| 30 | `@Composable fun UpdateBanner(viewModel: UpdateViewModel = hiltViewModel())` |
| 31 |   `val state by viewModel.ui.collectAsStateWithLifecycle()` |
| 32 |   `val context` |
| 33 |   `var showNotes by remember` |
| 34 |   `var pendingPermission by rememberSaveable` |
| 35 |   `fun install(file: File)` |
| 43 |   `val permission` |
| 65 |   `val toaster` |
| 66 |   `val failureMessage` |
| 70 |   `val colors` |
| 96 | `@Composable fun UpdateAbout(viewModel: UpdateViewModel = hiltViewModel())` |
| 97 |   `val state by viewModel.ui.collectAsStateWithLifecycle()` |
| 98 |   `var showNotes by remember` |
| 99 |   `var manual by remember` |
| 107 |   `val toaster` |
| 108 |   `val message` |
| 120 | `@Composable private fun UpdateNotes(state: UpdateUi, dismiss: () -> Unit, download: () -> Unit)` |
| 121 |   `val update` |

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
| 35 | `val UpdateUi.showBanner: Boolean` |
| 40 | `class UpdateRepository @Inject constructor( private val preferences: DataStore<Preferences>, private val downloader: UpdateDownloader, )` |
| 41 |   `private val preferences: DataStore<Preferences>` |
| 42 |   `private val downloader: UpdateDownloader` |
| 44 |   `private val mutable` |
| 45 |   `val state` |
| 46 |   `private val checker` |
| 47 |   `private val scope` |
| 48 |   `private var started` |
| 49 |   `private var checkMessageRevision` |
| 50 |   `private val lastCheck` |
| 52 |   `suspend fun check(manual: Boolean)` |
| 74 |   `fun clearCheckMessage()` |
| 79 |   `fun dismiss()` |
| 80 |   `fun installerHandled()` |
| 81 |   `fun installFailed()` |
| 83 |   `fun download()` |
| 87 |   `private suspend fun downloadInBackground()` |
| 105 | `class UpdateViewModel @Inject constructor(private val repository: UpdateRepository) : ViewModel()` |
| 106 |   `val ui` |
| 107 |   `fun check(manual: Boolean = false)` |
| 108 |   `fun clearCheckMessage()` |
| 109 |   `fun download()` |
| 110 |   `fun dismiss()` |
| 111 |   `fun installerHandled()` |
| 112 |   `fun installFailed()` |

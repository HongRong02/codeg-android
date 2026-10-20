package app.codeg.android.core.update

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.BuildConfig
import app.codeg.android.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

data class UpdateUi(
    val update: AppUpdate? = null,
    val dismissed: Int? = null,
    val checking: Boolean = false,
    val downloading: Boolean = false,
    val progress: Float? = null,
    val message: Int? = null,
    val installer: File? = null,
)

val UpdateUi.showBanner: Boolean
    get() = (update != null && dismissed != update.versionCode) || downloading || message == R.string.update_download_failed

/** Process-wide state shared by the banner and About screen, including dismissals. */
@Singleton
class UpdateRepository @Inject constructor(
    private val preferences: DataStore<Preferences>,
    private val downloader: UpdateDownloader,
) {
    private val mutable = MutableStateFlow(UpdateUi())
    val state = mutable.asStateFlow()
    private val checker = UpdateChecker()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var started = false
    private var checkMessageRevision = 0
    private val lastCheck = longPreferencesKey("apk_update_last_check")

    suspend fun check(manual: Boolean) {
        if (mutable.value.checking) return
        if (!manual && started) return
        started = true
        val messageRevision = checkMessageRevision
        mutable.update { it.copy(checking = true, message = null) }
        try {
            val now = System.currentTimeMillis()
            val previous = preferences.data.first()[lastCheck] ?: 0L
            if (!manual && now >= previous && now - previous < 6 * 60 * 60 * 1000L) return
            preferences.edit { it[lastCheck] = now }
            val update = checker.check(BuildConfig.VERSION_CODE, manual)
            mutable.update { it.copy(update = update, message = if (manual && messageRevision == checkMessageRevision && update == null) R.string.update_latest else null) }
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            if (manual && messageRevision == checkMessageRevision) mutable.update { it.copy(message = R.string.update_check_failed) }
        } finally {
            mutable.update { it.copy(checking = false) }
        }
    }

    fun clearCheckMessage() {
        checkMessageRevision++
        mutable.update { if (it.message == R.string.update_latest || it.message == R.string.update_check_failed) it.copy(message = null) else it }
    }

    fun dismiss() { mutable.update { it.copy(dismissed = it.update?.versionCode) } }
    fun installerHandled() { mutable.update { it.copy(installer = null) } }
    fun installFailed() { mutable.update { it.copy(message = R.string.update_install_failed) } }

    fun download() {
        scope.launch { downloadInBackground() }
    }

    private suspend fun downloadInBackground() {
        val update = mutable.value.update ?: return
        if (mutable.value.downloading) return
        mutable.update { it.copy(downloading = true, progress = null, message = null) }
        try {
            val file = downloader.download(update) { progress -> mutable.update { it.copy(progress = progress) } }
            mutable.update { it.copy(installer = file) }
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            mutable.update { it.copy(message = R.string.update_download_failed) }
        } finally {
            mutable.update { it.copy(downloading = false) }
        }
    }
}

@HiltViewModel
class UpdateViewModel @Inject constructor(private val repository: UpdateRepository) : ViewModel() {
    val ui = repository.state
    fun check(manual: Boolean = false) { viewModelScope.launch { repository.check(manual) } }
    fun clearCheckMessage() = repository.clearCheckMessage()
    fun download() = repository.download()
    fun dismiss() = repository.dismiss()
    fun installerHandled() = repository.installerHandled()
    fun installFailed() = repository.installFailed()
}

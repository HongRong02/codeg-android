package app.codeg.android.core.update

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.codeg.android.BuildConfig
import app.codeg.android.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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

/** Process-wide state shared by the banner and About screen, including dismissals. */
@Singleton
class UpdateRepository @Inject constructor(
    private val preferences: DataStore<Preferences>,
    private val downloader: UpdateDownloader,
) {
    private val mutable = MutableStateFlow(UpdateUi())
    val state = mutable.asStateFlow()
    private val checker = UpdateChecker()
    private var started = false
    private val lastCheck = longPreferencesKey("apk_update_last_check")

    suspend fun check(manual: Boolean) {
        if (mutable.value.checking) return
        if (!manual && started) return
        started = true
        mutable.value = mutable.value.copy(checking = true, message = null)
        try {
            val now = System.currentTimeMillis()
            val previous = preferences.data.first()[lastCheck] ?: 0L
            if (!manual && now >= previous && now - previous < 6 * 60 * 60 * 1000L) return
            preferences.edit { it[lastCheck] = now }
            val update = checker.check(BuildConfig.VERSION_CODE, manual)
            mutable.value = mutable.value.copy(update = update, message = if (manual && update == null) R.string.update_latest else null)
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            if (manual) mutable.value = mutable.value.copy(message = R.string.update_check_failed)
        } finally {
            mutable.value = mutable.value.copy(checking = false)
        }
    }

    fun dismiss() { mutable.value = mutable.value.copy(dismissed = mutable.value.update?.versionCode) }
    fun installerHandled() { mutable.value = mutable.value.copy(installer = null) }
    fun installFailed() { mutable.value = mutable.value.copy(message = R.string.update_install_failed) }

    suspend fun download() {
        val update = mutable.value.update ?: return
        if (mutable.value.downloading) return
        mutable.value = mutable.value.copy(downloading = true, progress = null, message = null)
        try {
            val file = downloader.download(update) { progress -> mutable.value = mutable.value.copy(progress = progress) }
            mutable.value = mutable.value.copy(installer = file)
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            mutable.value = mutable.value.copy(message = R.string.update_download_failed)
        } finally {
            mutable.value = mutable.value.copy(downloading = false)
        }
    }
}

@HiltViewModel
class UpdateViewModel @Inject constructor(private val repository: UpdateRepository) : ViewModel() {
    val ui = repository.state
    fun check(manual: Boolean = false) { viewModelScope.launch { repository.check(manual) } }
    fun download() { viewModelScope.launch { repository.download() } }
    fun dismiss() = repository.dismiss()
    fun installerHandled() = repository.installerHandled()
    fun installFailed() = repository.installFailed()
}

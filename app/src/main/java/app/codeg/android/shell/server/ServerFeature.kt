package app.codeg.android.shell.server

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.codeg.android.shell.ShellFeature
import app.codeg.android.shell.ShellHost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * S5 — server configuration. SKELETON STUB: a single URL in SharedPreferences.
 * Contract kept by the real implementation:
 *  - [current]: origin of the selected server (no trailing slash) or null; switching servers = new value.
 *  - [SetupScreen]: shown when [current] is null.
 *  - [ManagerScreen]: list/add/edit/delete/switch; [onClose] returns to the WebView.
 */
class ServerFeature : ShellFeature {
    private val state = MutableStateFlow<String?>(null)
    val current: StateFlow<String?> = state

    override fun onCreate(host: ShellHost) {
        state.value = prefs(host.activity).getString(KEY, null)
    }

    fun select(context: Context, url: String?) {
        prefs(context).edit().putString(KEY, url).apply()
        state.value = url
    }

    @Composable
    fun SetupScreen(host: ShellHost) = UrlForm(host, onDone = {})

    @Composable
    fun ManagerScreen(host: ShellHost, onClose: () -> Unit) = UrlForm(host, onDone = onClose)

    @Composable
    private fun UrlForm(host: ShellHost, onDone: () -> Unit) {
        var url by remember { mutableStateOf(state.value ?: "http://") }
        Surface(Modifier.fillMaxSize()) {
            Column(Modifier.safeDrawingPadding().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = url, onValueChange = { url = it }, singleLine = true)
                Button(onClick = { select(host.activity, url.trim().trimEnd('/')); onDone() }) { Text("OK") }
            }
        }
    }

    private fun prefs(context: Context) = context.getSharedPreferences("shell_stub", Context.MODE_PRIVATE)

    private companion object {
        const val KEY = "url"
    }
}

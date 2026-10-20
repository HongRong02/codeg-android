package app.codeg.android.shell.server

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.datastore.ServerProfile
import app.codeg.android.shell.ShellHost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.SocketTimeoutException
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLException

@Composable
internal fun ServerManager(feature: ServerFeature, host: ShellHost, onClose: () -> Unit) {
    val profiles by feature.profiles.collectAsState()
    val current by feature.current.collectAsState()
    var editing by remember { mutableStateOf<ServerProfile?>(null) }
    var adding by remember { mutableStateOf(false) }
    var deleting by remember { mutableStateOf<ServerProfile?>(null) }
    var busy by remember { mutableStateOf(false) }
    var failed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    if (adding || editing != null) {
        ServerEditor(feature, host, editing, onDone = { adding = false; editing = null }, onCancel = { adding = false; editing = null })
        return
    }
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.safeDrawingPadding().padding(24.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(stringResource(R.string.shell_server_switch), style = MaterialTheme.typography.headlineSmall)
            profiles.forEach { profile ->
                Card {
                    Column(Modifier.fillMaxWidth().clickable(enabled = !busy) {
                        busy = true
                        scope.launch {
                            try { feature.select(profile.id); onClose() } catch (_: java.io.IOException) { failed = true } finally { busy = false }
                        }
                    }.padding(16.dp)) {
                        Text(profile.name.ifBlank { profile.baseUrl }, style = MaterialTheme.typography.titleMedium)
                        Text(profile.baseUrl)
                        if (serverOrigin(profile.baseUrl) == current && feature.selectedId == profile.id) Text(stringResource(R.string.shell_server_current))
                        Row {
                            TextButton(enabled = !busy, onClick = { editing = profile }) { Text(stringResource(R.string.shell_server_edit)) }
                            TextButton(enabled = !busy, onClick = { deleting = profile }) { Text(stringResource(R.string.shell_server_delete)) }
                        }
                    }
                }
            }
            if (failed) Text(stringResource(R.string.shell_server_storage_error), color = MaterialTheme.colorScheme.error)
            Button(enabled = !busy, onClick = { adding = true }) { Text(stringResource(R.string.shell_server_add)) }
            TextButton(enabled = !busy, onClick = onClose) { Text(stringResource(R.string.shell_server_close)) }
        }
    }
    deleting?.let { profile ->
        AlertDialog(onDismissRequest = { if (!busy) deleting = null }, title = { Text(stringResource(R.string.shell_server_delete)) },
            text = { Text(stringResource(R.string.shell_server_delete_confirm, profile.name.ifBlank { profile.baseUrl })) },
            confirmButton = { TextButton(enabled = !busy, onClick = {
                busy = true
                scope.launch {
                    try {
                        feature.delete(profile.id)
                        deleting = null
                        if (feature.profiles.value.isEmpty()) onClose()
                    } catch (_: java.io.IOException) { failed = true; deleting = null } finally { busy = false }
                }
            }) { Text(stringResource(R.string.shell_server_delete)) } },
            dismissButton = { TextButton(enabled = !busy, onClick = { deleting = null }) { Text(stringResource(R.string.shell_server_cancel)) } })
    }
}

@Composable
internal fun ServerEditor(feature: ServerFeature, host: ShellHost, profile: ServerProfile?, onDone: () -> Unit, onCancel: (() -> Unit)? = null) {
    var address by rememberSaveable(profile?.id) { mutableStateOf(profile?.baseUrl ?: "") }
    var name by rememberSaveable(profile?.id) { mutableStateOf(profile?.name ?: "") }
    var busy by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<Int?>(null) }
    var pending by remember { mutableStateOf<ServerProfile?>(null) }
    val scope = rememberCoroutineScope()
    fun persist(value: ServerProfile) {
        busy = true
        scope.launch {
            try { feature.save(value); pending = null; onDone() }
            catch (_: java.io.IOException) { pending = null; error = R.string.shell_server_storage_error }
            finally { busy = false }
        }
    }
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.safeDrawingPadding().imePadding().padding(24.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(stringResource(R.string.shell_server_add), style = MaterialTheme.typography.headlineSmall)
            OutlinedTextField(address, { address = it; error = null }, enabled = !busy, singleLine = true,
                label = { Text(stringResource(R.string.shell_server_address)) }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(name, { name = it }, enabled = !busy, singleLine = true,
                label = { Text(stringResource(R.string.shell_server_name)) }, modifier = Modifier.fillMaxWidth())
            error?.let { Text(stringResource(it), color = MaterialTheme.colorScheme.error) }
            Button(enabled = !busy, onClick = {
                val origin = serverOrigin(address)
                if (origin == null) { error = R.string.shell_server_invalid; return@Button }
                val value = ServerProfile(profile?.id ?: UUID.randomUUID().toString(), name.trim(), origin)
                busy = true
                scope.launch {
                    val problem = probe(origin)
                    busy = false
                    if (problem == null) persist(value) else { error = problem; pending = value }
                }
            }) { Text(stringResource(if (busy) R.string.shell_server_checking else R.string.shell_server_save)) }
            onCancel?.let { TextButton(enabled = !busy, onClick = it) { Text(stringResource(R.string.shell_server_cancel)) } }
        }
    }
    pending?.let { value ->
        AlertDialog(onDismissRequest = { if (!busy) pending = null }, title = { Text(stringResource(R.string.shell_server_probe_failed)) },
            text = { Text(stringResource(error ?: R.string.shell_server_unreachable)) },
            confirmButton = { TextButton(enabled = !busy, onClick = { persist(value) }) { Text(stringResource(R.string.shell_server_save_anyway)) } },
            dismissButton = { TextButton(enabled = !busy, onClick = { pending = null }) { Text(stringResource(R.string.shell_server_cancel)) } })
    }
}

private val probeClient = OkHttpClient.Builder().connectTimeout(8, TimeUnit.SECONDS)
    .readTimeout(8, TimeUnit.SECONDS).callTimeout(10, TimeUnit.SECONDS)
    .followRedirects(false).followSslRedirects(false).build()

private suspend fun probe(origin: String): Int? = withContext(Dispatchers.IO) {
    try {
        probeClient.newCall(Request.Builder().url("$origin/").get().build()).execute().use { }
        null
    } catch (_: SocketTimeoutException) { R.string.shell_server_timeout }
    catch (_: java.io.InterruptedIOException) { R.string.shell_server_timeout }
    catch (_: SSLException) { R.string.shell_server_certificate }
    catch (_: java.io.IOException) { R.string.shell_server_unreachable }
}

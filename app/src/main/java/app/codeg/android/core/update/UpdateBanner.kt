package app.codeg.android.core.update

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import java.io.File

@Composable
fun UpdateBanner(viewModel: UpdateViewModel = hiltViewModel()) {
    val state by viewModel.ui.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showNotes by remember { mutableStateOf(false) }
    var pendingPermission by rememberSaveable { mutableStateOf(false) }
    fun install(file: File) {
        try {
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.updates", file)
            context.startActivity(Intent(Intent.ACTION_VIEW).setDataAndType(uri, "application/vnd.android.package-archive")
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK))
        } catch (_: Exception) { viewModel.installFailed() }
        viewModel.installerHandled()
    }
    val permission = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        pendingPermission = false
        state.installer?.let { file ->
            if (context.packageManager.canRequestPackageInstalls()) install(file)
            else { viewModel.installerHandled(); viewModel.installFailed() }
        }
    }
    LaunchedEffect(Unit) { viewModel.check() }
    LaunchedEffect(state.installer) {
        val file = state.installer ?: return@LaunchedEffect
        if (context.packageManager.canRequestPackageInstalls()) install(file)
        else if (!pendingPermission) {
            pendingPermission = true
            try {
                permission.launch(Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:${context.packageName}")))
            } catch (_: Exception) {
                pendingPermission = false
                viewModel.installerHandled()
                viewModel.installFailed()
            }
        }
    }
    val toaster = LocalCodegToaster.current
    val failureMessage = state.message?.let { stringResource(it) }
    LaunchedEffect(state.message) {
        if (state.message in setOf(R.string.update_download_failed, R.string.update_install_failed) && failureMessage != null) toaster.error(failureMessage)
    }
    val colors = CodegTheme.colors
    if (state.showBanner) Column(Modifier.fillMaxWidth().background(colors.bgElevated).windowInsetsPadding(WindowInsets.statusBars)) {
        val update = state.update
        if (update != null && state.dismissed != update.versionCode) {
            Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                .clip(CodegShapes.extraSmall)
                .border(1.dp, colors.accent, CodegShapes.extraSmall)
                .padding(horizontal = 14.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.update_available, update.versionName), color = colors.textPrimary, modifier = Modifier.weight(1f))
                CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, onClick = { showNotes = true }) { Text(stringResource(R.string.update_action)) }
                CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, onClick = { viewModel.dismiss() }) { Text(stringResource(R.string.update_dismiss)) }
            }
        }
        if (state.downloading) {
            Text(stringResource(R.string.update_downloading), color = colors.textPrimary, modifier = Modifier.padding(horizontal = 16.dp))
            val progress = state.progress

            if (progress == null) CodegProgress(Modifier.fillMaxWidth())
            else CodegProgress(progress = progress, modifier = Modifier.fillMaxWidth())
        }

    }
    if (showNotes) UpdateNotes(state, { showNotes = false }) { showNotes = false; viewModel.download() }
}

@Composable
fun UpdateAbout(viewModel: UpdateViewModel = hiltViewModel()) {
    val state by viewModel.ui.collectAsStateWithLifecycle()
    var showNotes by remember { mutableStateOf(false) }
    var manual by remember { mutableStateOf(false) }
    DisposableEffect(viewModel) { onDispose { viewModel.clearCheckMessage() } }
    LaunchedEffect(state.checking, state.update) {
        if (manual && !state.checking && state.update != null) { showNotes = true; manual = false }
    }
    CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !state.checking && !state.downloading, onClick = { manual = true; viewModel.check(true) }) {
        Text(stringResource(if (state.checking) R.string.update_checking else R.string.update_check))
    }
    val toaster = LocalCodegToaster.current
    val message = state.message?.let { stringResource(it) }
    LaunchedEffect(state.message, state.checking) {
        if (manual && !state.checking && message != null) {
            if (state.message in setOf(R.string.update_check_failed, R.string.update_download_failed, R.string.update_install_failed)) toaster.error(message)
            else toaster.info(message)
            manual = false
        }
    }
    if (showNotes) UpdateNotes(state, { showNotes = false }) { showNotes = false; viewModel.download() }
}

@Composable
private fun UpdateNotes(state: UpdateUi, dismiss: () -> Unit, download: () -> Unit) {
    val update = state.update ?: return
    CodegDialog(onDismiss = dismiss,
        title = stringResource(R.string.update_available, update.versionName),
        content = { Column(Modifier.fillMaxWidth()) {
                Text(update.notes.ifBlank { stringResource(R.string.update_no_notes) })
                Text(stringResource(R.string.update_size, update.size / (1024.0 * 1024.0)))
            } },
        footer = {
            CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, onClick = dismiss) { Text(stringResource(R.string.update_later)) }
            CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !state.downloading, onClick = download) { Text(stringResource(R.string.update_action)) }
    })
}

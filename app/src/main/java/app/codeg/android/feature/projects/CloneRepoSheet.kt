package app.codeg.android.feature.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import app.codeg.android.core.designsystem.component.CodegIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegSheet
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.PrimaryButton
import app.codeg.android.core.designsystem.component.SecretField
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.DirectoryEntry
import app.codeg.android.core.model.GitCredentials
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import app.codeg.android.core.designsystem.component.LocalCodegToaster
import app.codeg.android.core.designsystem.icon.*

/** Clone-a-repository sheet. Port of iOS `CloneRepoView`. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloneRepoSheet(
    loadHome: suspend () -> String,
    loadDirs: suspend (String) -> List<DirectoryEntry>,
    clone: suspend (String, String, GitCredentials?) -> String?,
    onCloned: () -> Unit,
    onDismiss: () -> Unit,
) {
    val toaster = LocalCodegToaster.current
    val failureTitle = stringResource(R.string.t56_operation_failed)
    val doneTitle = stringResource(R.string.t56_operation_completed)
    val colors = CodegTheme.colors
    val scope = rememberCoroutineScope()
    var url by remember { mutableStateOf("") }
    var parentDir by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cloning by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(error) {
        error?.let { toaster.error(failureTitle, it); error = null }
    }
    var showBrowser by remember { mutableStateOf(false) }

    val repoName = remember(url) { url.trim().trimEnd('/').substringAfterLast('/').removeSuffix(".git").ifEmpty { "repo" } }
    val targetDir = if (parentDir.isBlank()) "" else "${parentDir.trim().trimEnd('/')}/$repoName"
    val canClone = url.isNotBlank() && parentDir.isNotBlank() && !cloning

    CodegSheet(title = stringResource(R.string.projects_clone_repo), onDismiss = { if (!cloning) onDismiss() }, footer = {
        PrimaryButton(
            text = stringResource(R.string.clone_clone),
            onClick = {
                cloning = true; error = null
                scope.launch {
                    val creds = if (username.isBlank() && password.isBlank()) null else GitCredentials(username.trim(), password)
                    val err = clone(url.trim(), targetDir, creds)
                    cloning = false
                    if (err == null) { toaster.success(doneTitle); onCloned() } else error = err
                }
            },
            enabled = canClone,
            loading = cloning,
        )
    }) {
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CodegTextField(url, { url = it }, label = stringResource(R.string.clone_repository_url), placeholder = stringResource(R.string.clone_url_placeholder), keyboardType = KeyboardType.Uri, mono = true)
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CodegTextField(parentDir, { parentDir = it }, label = stringResource(R.string.clone_parent_directory), mono = true, modifier = Modifier.weight(1f))
                CodegIconButton(onClick = { showBrowser = true }) { Icon(Lucide.FolderPlus, contentDescription = stringResource(R.string.clone_browse), tint = colors.accent, modifier = Modifier.size(16.dp)) }
            }
            CodegTextField(username, { username = it }, label = stringResource(R.string.clone_username_optional))
            SecretField(password, { password = it }, label = stringResource(R.string.clone_password_token_optional))
            if (targetDir.isNotEmpty()) {
                Text(stringResource(R.string.clone_clones_into, targetDir), fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = colors.textSecondary)
            }

        }
    }

    if (showBrowser) {
        DirectoryBrowserSheet(
            title = stringResource(R.string.clone_choose_directory),
            confirmLabel = stringResource(R.string.clone_select_this_folder),
            loadHome = loadHome,
            loadDirs = loadDirs,
            onSelect = { parentDir = it; showBrowser = false },
            onDismiss = { showBrowser = false },
        )
    }
}

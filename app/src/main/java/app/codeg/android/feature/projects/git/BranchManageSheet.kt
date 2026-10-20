package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.diff.DiffView
import app.codeg.android.core.designsystem.diff.UnifiedDiff
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.BranchManageEntry
import app.codeg.android.core.model.GitCredentials

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BranchManageSheet(viewModel: BranchManageViewModel, onDismiss: () -> Unit) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    var deleting by remember { mutableStateOf<BranchManageEntry?>(null) }
    var force by remember { mutableStateOf(false) }
    var deleteReady by remember { mutableStateOf(false) }
    val colors = CodegTheme.colors
    CodegSheet(title = stringResource(R.string.bm_title), onDismiss = { if (!ui.busy) onDismiss() }) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // TODO(T52): CodegToaster for operation feedback.
            ui.error?.let { Text(it, color = colors.danger) }
            if (ui.completed) Text(stringResource(R.string.bm_done), color = colors.textSecondary)
            ui.conflict?.let { Text(stringResource(R.string.bm_conflicts, it.joinToString("\n")), color = colors.danger) }
            if (ui.busy) LoadingView(stringResource(R.string.common_loading))
            CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { viewModel.refresh() }, enabled = !ui.busy) { Text(stringResource(R.string.bm_refresh)) }
            if (!ui.busy && ui.branches.isEmpty()) Text(stringResource(R.string.bm_empty), color = colors.textSecondary)
            for (remote in listOf(false, true)) {
                CodegSectionHeader(title = stringResource(if (remote) R.string.branch_remote else R.string.branch_local))
                ui.branches.filter { it.isRemote == remote }.forEach { branch ->
                    Column {
                        CodegListRow(title = branch.name, onClick = {}, subtitle =
                            if (branch.ahead != null || branch.behind != null)
                                stringResource(R.string.bm_tracking, branch.ahead ?: 0, branch.behind ?: 0) else null)
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            if ("update" !in ui.hiddenActions) CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { viewModel.update(branch) }, enabled = !ui.busy) { Text(stringResource(R.string.bm_update)) }
                            if ("compare" !in ui.hiddenActions) CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { viewModel.compare(branch) }, enabled = !ui.busy) { Text(stringResource(R.string.bm_compare)) }
                            if ((if (remote) "remoteDelete" else "delete") !in ui.hiddenActions) CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost,
                                onClick = { force = false; deleteReady = branch.isRemote; deleting = branch }, enabled = !ui.busy,
                            ) { Text(stringResource(R.string.bm_delete), color = colors.danger) }
                        }
                    }
                }
            }
            // TODO(T53): CodegCollapsible for branch comparison.
            ui.diff?.let { raw ->
                CodegSectionHeader(title = stringResource(R.string.bm_comparison, ui.diffBranch.orEmpty()))
                val files = remember(raw) { UnifiedDiff.parse(raw) }
                when {
                    raw.isBlank() -> Text(stringResource(R.string.bm_no_diff), color = colors.textSecondary)
                    files.isNullOrEmpty() -> Text(raw, color = colors.textPrimary)
                    else -> DiffView(files)
                }
            }
        }
    }
    deleting?.let { branch ->
        val message = stringResource(if (branch.isRemote) R.string.bm_confirm_remote else R.string.bm_confirm_local, branch.name)
        if (!deleteReady) {
            CodegDialog(title = stringResource(R.string.bm_delete), description = message,
                onDismiss = { deleting = null }, footer = {
                    CodegButton(onClick = { deleting = null }, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.bm_cancel)) }
                    CodegButton(onClick = { deleteReady = true }) { Text(stringResource(R.string.bm_delete)) }
                }) {
                CodegCheckbox(checked = force, onCheckedChange = { force = it }, label = stringResource(R.string.bm_force))
            }
        } else {
            CodegConfirmDialog(title = stringResource(R.string.bm_delete), message = message,
                target = branch.name, confirmLabel = stringResource(R.string.bm_delete), destructive = true,
                onConfirm = { deleting = null; viewModel.delete(branch, force) }, onDismiss = { deleting = null })
        }
    }
    if (ui.credentialsRequested) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        CodegDialog(title = stringResource(R.string.bm_credentials),
            onDismiss = { viewModel.submitCredentials(null) }, footer = {
                CodegButton(onClick = { viewModel.submitCredentials(null) }, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.bm_cancel)) }
                CodegButton(onClick = { viewModel.submitCredentials(GitCredentials(username, password)) }, enabled = username.isNotBlank() && password.isNotBlank()) { Text(stringResource(R.string.bm_retry)) }
            }) {
            CodegTextField(username, { username = it }, label = stringResource(R.string.bm_username))
            SecretField(password, { password = it }, label = stringResource(R.string.bm_password))
        }
    }
}

package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegTopBar
import app.codeg.android.core.designsystem.diff.DiffView
import app.codeg.android.core.designsystem.diff.UnifiedDiff
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.BranchManageEntry
import app.codeg.android.core.model.GitCredentials

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BranchManageSheet(viewModel: BranchManageViewModel, onDismiss: () -> Unit) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    var deleting by remember { mutableStateOf<BranchManageEntry?>(null) }
    var force by remember { mutableStateOf(false) }
    val colors = CodegTheme.colors
    ModalBottomSheet(
        onDismissRequest = { if (!ui.busy) onDismiss() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = colors.bgElevated,
    ) {
        Column(Modifier.fillMaxWidth().heightIn(max = 640.dp).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CodegTopBar(title = stringResource(R.string.bm_title), showDivider = true)
            ui.error?.let { Text(it, color = colors.danger) }
            if (ui.completed) Text(stringResource(R.string.bm_done), color = colors.textSecondary)
            ui.conflict?.let { Text(stringResource(R.string.bm_conflicts, it.joinToString("\n")), color = colors.danger) }
            if (ui.busy) CircularProgressIndicator(color = colors.accent)
            TextButton(onClick = { viewModel.refresh() }, enabled = !ui.busy) { Text(stringResource(R.string.bm_refresh)) }
            if (!ui.busy && ui.branches.isEmpty()) Text(stringResource(R.string.bm_empty), color = colors.textSecondary)
            for (remote in listOf(false, true)) {
                Text(stringResource(if (remote) R.string.branch_remote else R.string.branch_local), color = colors.textSecondary)
                ui.branches.filter { it.isRemote == remote }.forEach { branch ->
                    Column {
                        Text(branch.name, color = colors.textPrimary)
                        if (branch.ahead != null || branch.behind != null) Text(
                            stringResource(R.string.bm_tracking, branch.ahead ?: 0, branch.behind ?: 0), color = colors.textSecondary,
                        )
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            if ("update" !in ui.hiddenActions) TextButton(onClick = { viewModel.update(branch) }, enabled = !ui.busy) { Text(stringResource(R.string.bm_update)) }
                            if ("compare" !in ui.hiddenActions) TextButton(onClick = { viewModel.compare(branch) }, enabled = !ui.busy) { Text(stringResource(R.string.bm_compare)) }
                            if ((if (remote) "remoteDelete" else "delete") !in ui.hiddenActions) TextButton(
                                onClick = { force = false; deleting = branch }, enabled = !ui.busy,
                            ) { Text(stringResource(R.string.bm_delete), color = colors.danger) }
                        }
                    }
                }
            }
            ui.diff?.let { raw ->
                Text(stringResource(R.string.bm_comparison, ui.diffBranch.orEmpty()), color = colors.textPrimary)
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
        AlertDialog(
            onDismissRequest = { deleting = null },
            title = { Text(stringResource(R.string.bm_delete)) },
            text = {
                Column {
                    Text(stringResource(if (branch.isRemote) R.string.bm_confirm_remote else R.string.bm_confirm_local, branch.name))
                    if (!branch.isRemote) Row {
                        Checkbox(checked = force, onCheckedChange = { force = it })
                        Text(stringResource(R.string.bm_force))
                    }
                }
            },
            confirmButton = { TextButton(onClick = { deleting = null; viewModel.delete(branch, force) }) { Text(stringResource(R.string.bm_delete)) } },
            dismissButton = { TextButton(onClick = { deleting = null }) { Text(stringResource(R.string.bm_cancel)) } },
        )
    }
    if (ui.credentialsRequested) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { viewModel.submitCredentials(null) },
            title = { Text(stringResource(R.string.bm_credentials)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(username, { username = it }, label = { Text(stringResource(R.string.bm_username)) }, singleLine = true)
                    OutlinedTextField(password, { password = it }, label = { Text(stringResource(R.string.bm_password)) }, singleLine = true, visualTransformation = PasswordVisualTransformation())
                }
            },
            confirmButton = { TextButton(onClick = { viewModel.submitCredentials(GitCredentials(username, password)) }, enabled = username.isNotBlank() && password.isNotBlank()) { Text(stringResource(R.string.bm_retry)) } },
            dismissButton = { TextButton(onClick = { viewModel.submitCredentials(null) }) { Text(stringResource(R.string.bm_cancel)) } },
        )
    }
}

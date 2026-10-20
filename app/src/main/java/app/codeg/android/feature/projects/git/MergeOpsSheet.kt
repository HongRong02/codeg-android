package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.network.CodegClient

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MergeOpsSheet(vm: MergeOpsViewModel, client: CodegClient, path: String, branch: String?, onDismiss: () -> Unit) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    var action by remember { mutableStateOf("merge") }
    var target by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("mixed") }
    var confirm by remember { mutableStateOf(false) }
    var typed by remember { mutableStateOf("") }
    var hardVerified by remember { mutableStateOf(false) }
    val toaster = LocalCodegToaster.current
    val failureTitle = stringResource(R.string.t56_operation_failed)
    val doneTitle = stringResource(R.string.t56_operation_completed)
    LaunchedEffect(ui.error, ui.completed) {
        ui.error?.let { toaster.error(failureTitle, it) }
        if (ui.completed) toaster.success(doneTitle)
    }
    CodegSheet(title = stringResource(R.string.gm_title), onDismiss = { if (!ui.busy) onDismiss() }) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(stringResource(R.string.gm_current, branch.orEmpty()))
            if (ui.busy) LoadingView(stringResource(R.string.common_loading))
            if (!ui.ready) ui.error?.let { Text(it, color = app.codeg.android.core.designsystem.theme.CodegTheme.colors.danger) }
            CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { vm.refresh(client, path) }, enabled = !ui.busy) { Text(stringResource(R.string.gm_refresh)) }
            if (ui.active || ui.conflicts.isNotEmpty()) {
                ConflictListScreen(vm, client, path, branch.orEmpty())
            } else {
                val actions = listOf("merge", "rebase", "reset")
                CodegTabs(selected = actions.indexOf(action), tabs = actions.map { operationLabel(it) }, onSelect = { if (!ui.busy) { action = actions[it]; target = "" } })
                if (action == "reset") {
                    CodegTextField(value = target, onValueChange = { if (!ui.busy) target = it }, label = stringResource(R.string.gm_commit))
                    CodegRadioGroup(options = listOf("soft", "mixed", "hard"), value = mode, onSelect = { mode = it }, enabled = !ui.busy)
                } else {
                    CodegSelect(label = stringResource(R.string.branch_switch_branch), value = target,
                        options = ui.branches.filter { it != branch }, onSelect = { target = it }, enabled = !ui.busy)
                }
                CodegButton(onClick = { typed = ""; hardVerified = false; confirm = true }, enabled = ui.ready && !ui.busy && target.isNotBlank() && !branch.isNullOrBlank()) {
                    Text(operationLabel(action))
                }
            }
        }
    }
    if (confirm) {
        val message = stringResource(when (action) { "merge" -> R.string.gm_confirm_merge; "rebase" -> R.string.gm_confirm_rebase; else -> R.string.gm_confirm_reset }, target, branch.orEmpty(), mode)
        if (action == "reset" && mode == "hard" && !hardVerified) {
            CodegConfirmDialog(title = operationLabel(action), message = message,
                target = "${branch.orEmpty()} → ${target.trim()}", confirmLabel = stringResource(R.string.gm_confirm),
                destructive = true, busy = ui.busy, confirmEnabled = typed == branch,
                onConfirm = { hardVerified = true }, onDismiss = { confirm = false }, content = {
                    Text(stringResource(R.string.gm_hard, branch.orEmpty()))
                    CodegTextField(value = typed, onValueChange = { typed = it }, enabled = !ui.busy,
                        label = stringResource(R.string.git_misc_branch))
                })
        } else {
            CodegConfirmDialog(title = operationLabel(action), message = message,
                target = "${branch.orEmpty()} → ${target.trim()}", confirmLabel = stringResource(R.string.gm_confirm),
                destructive = action == "reset" || action == "rebase", busy = ui.busy,
                onConfirm = { confirm = false; vm.execute(client, path, action, target.trim(), mode) },
                onDismiss = { confirm = false })
        }
    }
}

@Composable
internal fun operationLabel(value: String): String = stringResource(when (value) {
    "merge" -> R.string.gm_merge
    "rebase" -> R.string.gm_rebase
    else -> R.string.gm_reset
})

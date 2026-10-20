package app.codeg.android.feature.projects.git

import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.network.CodegClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MergeOpsSheet(vm: MergeOpsViewModel, client: CodegClient, path: String, branch: String?, onDismiss: () -> Unit) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    var action by remember { mutableStateOf("merge") }
    var target by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf("mixed") }
    var confirm by remember { mutableStateOf(false) }
    var typed by remember { mutableStateOf("") }
    ModalBottomSheet(contentWindowInsets = { WindowInsets.safeDrawing }, onDismissRequest = { if (!ui.busy) onDismiss() }) {
        Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(stringResource(R.string.gm_title), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.gm_current, branch.orEmpty()))
            if (ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth())
            ui.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            if (ui.completed) Text(stringResource(R.string.gm_done))
            TextButton(onClick = { vm.refresh(client, path) }, enabled = !ui.busy) { Text(stringResource(R.string.gm_refresh)) }
            if (ui.active || ui.conflicts.isNotEmpty()) {
                ConflictListScreen(vm, client, path, branch.orEmpty())
            } else {
                Row {
                    listOf("merge", "rebase", "reset").forEach { value ->
                        FilterChip(selected = action == value, onClick = { action = value; target = "" }, enabled = !ui.busy,
                            label = { Text(operationLabel(value)) })
                    }
                }
                if (action == "reset") {
                    OutlinedTextField(value = target, onValueChange = { target = it }, enabled = !ui.busy,
                        label = { Text(stringResource(R.string.gm_commit)) })
                    Row {
                        listOf("soft", "mixed", "hard").forEach { value ->
                            FilterChip(selected = mode == value, onClick = { mode = value }, enabled = !ui.busy, label = { Text(value) })
                        }
                    }
                } else {
                    ui.branches.filter { it != branch }.forEach { value ->
                        TextButton(onClick = { target = value }, enabled = !ui.busy) { Text(if (target == value) "✓ $value" else value) }
                    }
                }
                Button(onClick = { typed = ""; confirm = true }, enabled = ui.ready && !ui.busy && target.isNotBlank() && !branch.isNullOrBlank()) {
                    Text(operationLabel(action))
                }
            }
        }
    }
    if (confirm) AlertDialog(onDismissRequest = { confirm = false },
        modifier = Modifier.imePadding(),
        properties = DialogProperties(decorFitsSystemWindows = false), title = { Text(operationLabel(action)) }, text = {
        Column {
            Text(stringResource(when (action) { "merge" -> R.string.gm_confirm_merge; "rebase" -> R.string.gm_confirm_rebase; else -> R.string.gm_confirm_reset }, target, branch.orEmpty(), mode))
            if (action == "reset" && mode == "hard") {
                Text(stringResource(R.string.gm_hard, branch.orEmpty()))
                OutlinedTextField(value = typed, onValueChange = { typed = it })
            }
        }
    }, confirmButton = {
        TextButton(onClick = { confirm = false; vm.execute(client, path, action, target.trim(), mode) },
            enabled = action != "reset" || mode != "hard" || typed == branch) { Text(stringResource(R.string.gm_confirm)) }
    }, dismissButton = { TextButton(onClick = { confirm = false }) { Text(stringResource(R.string.gm_cancel)) } })
}

@Composable
internal fun operationLabel(value: String): String = stringResource(when (value) {
    "merge" -> R.string.gm_merge
    "rebase" -> R.string.gm_rebase
    else -> R.string.gm_reset
})

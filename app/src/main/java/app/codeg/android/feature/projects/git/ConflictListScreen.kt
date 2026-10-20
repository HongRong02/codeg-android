package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.network.CodegClient

@Composable
fun ConflictListScreen(vm: MergeOpsViewModel, client: CodegClient, path: String, branch: String) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    var pending by remember { mutableStateOf<String?>(null) }
    var version by remember { mutableStateOf("base") }
    Text(stringResource(R.string.gm_conflicts), style = MaterialTheme.typography.titleMedium)
    Text(stringResource(R.string.gm_operation_hint))
    Row {
        listOf("merge", "rebase").forEach { value ->
            FilterChip(selected = ui.operation == value, onClick = { vm.operation(value) }, enabled = !ui.busy,
                label = { Text(operationLabel(value)) })
        }
    }
    ui.conflicts.forEach { file ->
        TextButton(onClick = { version = "base"; vm.versions(client, path, file) }, enabled = !ui.busy) { Text(file) }
    }
    if (ui.conflicts.isEmpty()) Text(stringResource(R.string.gm_resolved))
    ui.versions?.let { versions ->
        Text(ui.file.orEmpty())
        Row {
            listOf("base", "ours", "theirs").forEach { value ->
                FilterChip(selected = version == value, onClick = { version = value }, label = { Text(versionLabel(value)) })
            }
        }
        SelectionContainer { Text(when (version) { "ours" -> versions.ours; "theirs" -> versions.theirs; else -> versions.base }) }
        Text(stringResource(R.string.gm_rebase_sides))
        Row {
            TextButton(onClick = { pending = "ours" }, enabled = !ui.busy) { Text(stringResource(R.string.gm_choose_ours)) }
            TextButton(onClick = { pending = "theirs" }, enabled = !ui.busy) { Text(stringResource(R.string.gm_choose_theirs)) }
        }
    }
    Row {
        TextButton(onClick = { pending = "continue" }, enabled = ui.active && ui.conflicts.isEmpty() && !ui.busy) { Text(stringResource(R.string.gm_continue)) }
        TextButton(onClick = { pending = "abort" }, enabled = !ui.busy) { Text(stringResource(R.string.gm_abort)) }
    }
    pending?.let { choice ->
        AlertDialog(onDismissRequest = { pending = null }, title = { Text(stringResource(R.string.gm_confirm)) }, text = {
            Column {
                if (choice == "ours" || choice == "theirs") Text(stringResource(R.string.gm_resolve_confirm, ui.file.orEmpty(), versionLabel(choice)))
                else Text(stringResource(if (choice == "abort") R.string.gm_abort_confirm else R.string.gm_continue_confirm, ui.operation, branch))
            }
        }, confirmButton = {
            TextButton(onClick = {
                pending = null
                if (choice == "ours" || choice == "theirs") vm.resolve(client, path, choice == "ours")
                else vm.finish(client, path, choice == "abort")
            }) { Text(stringResource(R.string.gm_confirm)) }
        }, dismissButton = { TextButton(onClick = { pending = null }) { Text(stringResource(R.string.gm_cancel)) } })
    }
}

@Composable
private fun versionLabel(value: String): String = stringResource(when (value) {
    "ours" -> R.string.gm_ours
    "theirs" -> R.string.gm_theirs
    else -> R.string.gm_base
})

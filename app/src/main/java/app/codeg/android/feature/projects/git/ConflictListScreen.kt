package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.network.CodegClient

@Composable
fun ConflictListScreen(vm: MergeOpsViewModel, client: CodegClient, path: String, branch: String) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    var pending by remember { mutableStateOf<String?>(null) }
    var version by remember { mutableStateOf("base") }
    CodegSectionHeader(title = stringResource(R.string.gm_conflicts))
    Text(stringResource(R.string.gm_operation_hint))
    val operations = listOf("merge", "rebase")
    CodegTabs(selected = operations.indexOf(ui.operation).coerceAtLeast(0), tabs = operations.map { operationLabel(it) },
        onSelect = { if (!ui.busy) vm.operation(operations[it]) })
    ui.conflicts.forEach { file ->
        CodegListRow(title = file, onClick = { if (!ui.busy) { version = "base"; vm.versions(client, path, file) } }, selected = ui.file == file)
    }
    if (ui.conflicts.isEmpty()) Text(stringResource(R.string.gm_resolved))
    // TODO(T53): CodegCommandList for conflict file selection.
    ui.versions?.let { versions ->
        Text(ui.file.orEmpty())
        val sides = listOf("base", "ours", "theirs")
        CodegTabs(selected = sides.indexOf(version), tabs = sides.map { versionLabel(it) }, onSelect = { version = sides[it] })
        SelectionContainer { Text(when (version) { "ours" -> versions.ours; "theirs" -> versions.theirs; else -> versions.base }) }
        Text(stringResource(R.string.gm_rebase_sides))
        Row {
            CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { pending = "ours" }, enabled = !ui.busy) { Text(stringResource(R.string.gm_choose_ours)) }
            CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { pending = "theirs" }, enabled = !ui.busy) { Text(stringResource(R.string.gm_choose_theirs)) }
        }
    }
    Row {
        CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { pending = "continue" }, enabled = ui.active && ui.conflicts.isEmpty() && !ui.busy) { Text(stringResource(R.string.gm_continue)) }
        CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { pending = "abort" }, enabled = !ui.busy) { Text(stringResource(R.string.gm_abort)) }
    }
    pending?.let { choice ->
        CodegConfirmDialog(title = stringResource(R.string.gm_confirm),
            message = if (choice == "ours" || choice == "theirs") stringResource(R.string.gm_resolve_confirm, ui.file.orEmpty(), versionLabel(choice))
                else stringResource(if (choice == "abort") R.string.gm_abort_confirm else R.string.gm_continue_confirm, ui.operation, branch),
            target = if (choice == "ours" || choice == "theirs") ui.file.orEmpty() else branch,
            confirmLabel = stringResource(R.string.gm_confirm), destructive = choice != "continue",
            onConfirm = {
                pending = null
                if (choice == "ours" || choice == "theirs") vm.resolve(client, path, choice == "ours")
                else vm.finish(client, path, choice == "abort")
            }, onDismiss = { pending = null })
    }
}

@Composable
private fun versionLabel(value: String): String = stringResource(when (value) {
    "ours" -> R.string.gm_ours
    "theirs" -> R.string.gm_theirs
    else -> R.string.gm_base
})

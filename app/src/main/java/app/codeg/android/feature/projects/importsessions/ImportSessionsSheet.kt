package app.codeg.android.feature.projects.importsessions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.importableKeys
import app.codeg.android.core.model.wire.ImportSessionKey

@Composable
fun ImportSessionsSheet(ui: ImportSessionsUi, viewModel: ImportSessionsViewModel, onDismiss: () -> Unit, onImported: () -> Unit) {
    var confirmAll by remember { mutableStateOf(false) }
    val all = ui.scan?.folders.orEmpty().flatMap { it.sessions }.importableKeys()
    Dialog(onDismissRequest = { if (!ui.busy) onDismiss() }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(modifier = Modifier.fillMaxSize(), color = CodegTheme.colors.bg) {
            Column(Modifier.fillMaxSize().safeDrawingPadding().padding(16.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(stringResource(R.string.imp_title), style = MaterialTheme.typography.titleLarge)
                    TextButton(enabled = !ui.busy, onClick = onDismiss) { Text(stringResource(R.string.imp_close)) }
                }
                if (ui.busy) { LinearProgressIndicator(Modifier.fillMaxWidth()); Text(stringResource(R.string.imp_working)) }
                ui.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                ui.result?.let { result ->
                    Text(stringResource(R.string.imp_result, result.imported, result.updated, result.skipped, result.failed, result.notFound))
                }
                Row {
                    TextButton(enabled = !ui.busy, onClick = viewModel::scan) { Text(stringResource(R.string.imp_scan)) }
                    TextButton(enabled = !ui.busy && ui.result == null && all.isNotEmpty(), onClick = { viewModel.toggle(all) }) {
                        Text(stringResource(R.string.imp_select_all))
                    }
                }
                LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ui.result?.errors?.forEach { error -> item { Text(error, color = MaterialTheme.colorScheme.error) } }
                    if (ui.scan?.folders?.isEmpty() == true) item { Text(stringResource(R.string.imp_empty)) }
                    if ((ui.scan?.noFolderCount ?: 0) > 0) item { Text(stringResource(R.string.imp_no_folder, ui.scan!!.noFolderCount)) }
                    ui.scan?.folders.orEmpty().forEach { folder ->
                        folder.sessions.groupBy { it.agentType }.forEach { (agent, sessions) ->
                            val keys = sessions.importableKeys()
                            item {
                                Row {
                                    Checkbox(checked = keys.isNotEmpty() && keys.all(ui.selected::contains),
                                        enabled = !ui.busy && ui.result == null && keys.isNotEmpty(),
                                        onCheckedChange = { viewModel.toggle(keys) })
                                    Column { Text(agent, style = MaterialTheme.typography.titleMedium); Text(folder.path, style = MaterialTheme.typography.bodySmall) }
                                }
                            }
                            sessions.forEach { session ->
                                item {
                                    Row {
                                        Checkbox(checked = session.key in ui.selected,
                                            enabled = !ui.busy && ui.result == null && session.status == "new",
                                            onCheckedChange = { viewModel.toggle(listOf(session.key)) })
                                        Column {
                                            Text(session.title ?: session.externalId)
                                            Text(session.startedAt, style = MaterialTheme.typography.bodySmall)
                                            Text(stringResource(when (session.status) {
                                                "new" -> R.string.imp_new
                                                "imported" -> R.string.imp_imported
                                                "deleted" -> R.string.imp_deleted
                                                else -> R.string.imp_unavailable
                                            }), style = MaterialTheme.typography.bodySmall)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Row {
                    Button(enabled = !ui.busy && ui.result == null && ui.selected.isNotEmpty(), onClick = { viewModel.import(onImported = onImported) }) {
                        Text(stringResource(R.string.imp_selected, ui.selected.size))
                    }
                    TextButton(enabled = !ui.busy && ui.result == null && all.isNotEmpty(), onClick = { confirmAll = true }) { Text(stringResource(R.string.imp_all)) }
                }
            }
        }
        if (confirmAll) AlertDialog(onDismissRequest = { confirmAll = false },
            title = { Text(stringResource(R.string.imp_all)) },
            text = { Text(stringResource(R.string.imp_confirm_all, all.size)) },
            confirmButton = { TextButton(onClick = { confirmAll = false; viewModel.import(all = true, onImported = onImported) }) { Text(stringResource(R.string.imp_all)) } },
            dismissButton = { TextButton(onClick = { confirmAll = false }) { Text(stringResource(R.string.imp_cancel)) } })
    }
}

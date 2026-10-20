package app.codeg.android.feature.projects.importsessions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.model.importableKeys

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImportSessionsSheet(ui: ImportSessionsUi, viewModel: ImportSessionsViewModel, onDismiss: () -> Unit, onImported: () -> Unit) {
    var confirmAll by remember { mutableStateOf(false) }
    val toaster = LocalCodegToaster.current
    val failureTitle = stringResource(R.string.t56_operation_failed)
    LaunchedEffect(ui.error) { ui.error?.let { toaster.error(failureTitle, it) } }
    val all = ui.scan?.folders.orEmpty().flatMap { it.sessions }.importableKeys()
    CodegFullScreenSheet(title = stringResource(R.string.imp_title), onDismiss = { if (!ui.busy) onDismiss() }) {
        if (ui.busy) {
            CodegProgress()
            Text(stringResource(R.string.imp_working))
        }
        ui.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        ui.result?.let { result ->
            Text(stringResource(R.string.imp_result, result.imported, result.updated, result.skipped, result.failed, result.notFound))
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CodegButton(enabled = !ui.busy, onClick = viewModel::scan, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.imp_scan)) }
            CodegButton(enabled = !ui.busy && ui.result == null && all.isNotEmpty(), onClick = { viewModel.toggle(all) }, variant = CodegButtonVariant.Ghost) {
                Text(stringResource(R.string.imp_select_all))
            }
        }
        ui.result?.errors?.forEach { Text(it, color = MaterialTheme.colorScheme.error) }
        if (ui.scan?.folders?.isEmpty() == true) Text(stringResource(R.string.imp_empty))
        if ((ui.scan?.noFolderCount ?: 0) > 0) Text(stringResource(R.string.imp_no_folder, ui.scan!!.noFolderCount))
        ui.scan?.folders.orEmpty().forEach { folder ->
            CodegSectionHeader(title = folder.path, count = folder.sessions.size)
            folder.sessions.groupBy { it.agentType }.forEach { (agent, sessions) ->
                val keys = sessions.importableKeys()
                val groupEnabled = !ui.busy && ui.result == null && keys.isNotEmpty()
                CodegListRow(title = agent, subtitle = folder.path,
                    onClick = { if (groupEnabled) viewModel.toggle(keys) },
                    leading = {
                        CodegCheckbox(checked = keys.isNotEmpty() && keys.all(ui.selected::contains), enabled = groupEnabled,
                            onCheckedChange = { viewModel.toggle(keys) })
                    })
                sessions.forEach { session ->
                    val enabled = !ui.busy && ui.result == null && session.status == "new"
                    CodegListRow(title = session.title ?: session.externalId,
                        subtitle = app.codeg.android.feature.worktasks.formatWorkTaskTime(session.startedAt),
                        selected = session.key in ui.selected,
                        onClick = { if (enabled) viewModel.toggle(listOf(session.key)) },
                        leading = {
                            CodegCheckbox(checked = session.key in ui.selected, enabled = enabled,
                                onCheckedChange = { viewModel.toggle(listOf(session.key)) })
                        },
                        trailing = {
                            CodegBadge(stringResource(when (session.status) {
                                "new" -> R.string.imp_new
                                "imported" -> R.string.imp_imported
                                "deleted" -> R.string.imp_deleted
                                else -> R.string.imp_unavailable
                            }), variant = CodegBadgeVariant.Outline)
                        })
                }
                CodegSeparator()
            }
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CodegButton(enabled = !ui.busy && ui.result == null && ui.selected.isNotEmpty(), onClick = { viewModel.import(onImported = onImported) }) {
                Text(stringResource(R.string.imp_selected, ui.selected.size))
            }
            CodegButton(enabled = !ui.busy && ui.result == null && all.isNotEmpty(), onClick = { confirmAll = true }, variant = CodegButtonVariant.Outline) {
                Text(stringResource(R.string.imp_all))
            }
        }
    }
    if (confirmAll) CodegConfirmDialog(title = stringResource(R.string.imp_all),
        message = stringResource(R.string.imp_confirm_all, all.size),
        target = stringResource(R.string.imp_selected, all.size),
        confirmLabel = stringResource(R.string.imp_all),
        onConfirm = { confirmAll = false; viewModel.import(all = true, onImported = onImported) },
        onDismiss = { confirmAll = false })
}

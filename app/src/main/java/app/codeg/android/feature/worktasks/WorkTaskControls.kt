package app.codeg.android.feature.worktasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.model.WorkTask
import app.codeg.android.core.model.wire.WorkTaskDraft
import app.codeg.android.core.designsystem.theme.CodegTheme
import kotlinx.serialization.json.*
import java.time.OffsetDateTime

fun actionLabel(action: String): Int = when (action) {
    "start" -> R.string.wtb_start
    "delete" -> R.string.wtb_delete
    "cancel" -> R.string.wtb_cancel_task
    "retry" -> R.string.wtb_retry
    "requeue" -> R.string.wtb_requeue
    "return" -> R.string.wtb_return
    "schedule" -> R.string.wtb_schedule
    "defer" -> R.string.wtb_defer
    "complete" -> R.string.wtb_complete
    "merge" -> R.string.wtb_merge
    "merge_unqueue" -> R.string.wtb_merge_unqueue
    "deliver_pr" -> R.string.wtb_deliver_pr
    "archive" -> R.string.wtb_archive
    else -> R.string.wtb_cleanup
}

@Composable
fun WorkTaskActions(task: WorkTask, ui: WorkTaskUi, viewModel: WorkTaskViewModel) {
    var pending by remember(task.id) { mutableStateOf<String?>(null) }
    var text by remember { mutableStateOf("") }
    Column {
        if (task.status in setOf("todo", "failed") && "update" !in ui.hiddenActions) {
            TextButton(enabled = !ui.busy, onClick = { viewModel.edit(task) }) { Text(stringResource(R.string.wtb_edit)) }
        }
        task.availableActions().filter { it !in ui.hiddenActions }.chunked(2).forEach { row ->
            Row {
                row.forEach { action ->
                    val label = when {
                        action == "defer" && task.deferredAt != null -> R.string.wtb_undefer
                        action == "archive" && task.archivedAt != null -> R.string.wtb_unarchive
                        else -> actionLabel(action)
                    }
                    TextButton(enabled = !ui.busy, onClick = {
                        if (action in setOf("delete", "cancel", "merge", "deliver_pr", "cleanup", "return", "schedule")) {
                            text = if (action == "schedule") task.scheduledAt.orEmpty() else ""
                            pending = action
                        } else viewModel.action(task, action)
                    }) { Text(stringResource(label)) }
                }
            }
        }
    }
    pending?.let { action ->
        val valid = when (action) {
            "return" -> text.isNotBlank()
            "schedule" -> text.isBlank() || runCatching { OffsetDateTime.parse(text) }.isSuccess
            else -> true
        }
        AlertDialog(onDismissRequest = { pending = null },
            title = { Text(stringResource(actionLabel(action))) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(stringResource(R.string.wtb_confirm_target, task.title, task.id))
                    if (action in setOf("return", "schedule")) {
                        OutlinedTextField(value = text, onValueChange = { text = it },
                            label = { Text(stringResource(if (action == "return") R.string.wtb_feedback else R.string.wtb_schedule_hint)) },
                            isError = !valid, modifier = Modifier.fillMaxWidth())
                    }
                }
            },
            confirmButton = { TextButton(enabled = valid && !ui.busy, onClick = {
                viewModel.action(task, action, text.trim()); pending = null
            }) { Text(stringResource(R.string.wtb_confirm)) } },
            dismissButton = { TextButton(onClick = { pending = null }) { Text(stringResource(R.string.wtb_close)) } })
    }
}

@Composable
private fun Choice(label: String, value: String, choices: List<Pair<String, () -> Unit>>, enabled: Boolean) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextButton(enabled = enabled, onClick = { expanded = true }) { Text("$label: $value") }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            choices.forEach { (name, select) ->
                DropdownMenuItem(text = { Text(name) }, onClick = { select(); expanded = false })
            }
        }
    }
}

@Composable
fun WorkTaskEditor(ui: WorkTaskUi, viewModel: WorkTaskViewModel) {
    val task = ui.editing
    var title by remember { mutableStateOf(task?.title.orEmpty()) }
    var original by remember { mutableStateOf(task?.config ?: JsonObject(emptyMap())) }
    var description by remember { mutableStateOf((original["display_text"] as? JsonPrimitive)?.contentOrNull.orEmpty()) }
    var folderId by remember { mutableStateOf(task?.folderId) }
    var agent by remember { mutableStateOf((original["agent_type"] as? JsonPrimitive)?.contentOrNull) }
    val inherit = stringResource(R.string.wtb_inherit)
    AlertDialog(onDismissRequest = viewModel::dismissEditor,
        title = { Text(stringResource(if (task == null) R.string.wtb_create else R.string.wtb_edit)) },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ui.error?.let { Text(it, color = CodegTheme.colors.danger) }
                if (task == null && ui.templates.isNotEmpty()) {
                    Choice(stringResource(R.string.wtb_template), stringResource(R.string.wtb_choose), ui.templates.map { template ->
                        template.name to {
                            title = template.title; original = template.config
                            description = (original["display_text"] as? JsonPrimitive)?.contentOrNull.orEmpty()
                            agent = (original["agent_type"] as? JsonPrimitive)?.contentOrNull
                        }
                    }, !ui.busy)
                }
                OutlinedTextField(title, { title = it }, label = { Text(stringResource(R.string.wtb_title)) }, enabled = !ui.busy)
                OutlinedTextField(description, { description = it }, label = { Text(stringResource(R.string.wtb_description)) }, minLines = 3, enabled = !ui.busy)
                Choice(stringResource(R.string.wtb_project), ui.folders.find { it.id == folderId }?.name ?: stringResource(R.string.wtb_choose),
                    ui.folders.map { it.name to { folderId = it.id } }, !ui.busy)
                Choice(stringResource(R.string.wtb_agent), agent ?: inherit,
                    listOf(inherit to { agent = null }) + ui.agents.map { it.name.ifBlank { it.agentType.wire } to { agent = it.agentType.wire } }, !ui.busy)
            }
        },
        confirmButton = {
            TextButton(enabled = !ui.busy && title.isNotBlank() && description.isNotBlank() && ui.folders.any { it.id == folderId }, onClick = {
                viewModel.save(WorkTaskDraft(folderId!!, title.trim(), taskConfig(original, description.trim(), agent)))
            }) { Text(stringResource(R.string.wtb_save)) }
        },
        dismissButton = { TextButton(enabled = !ui.busy, onClick = viewModel::dismissEditor) { Text(stringResource(R.string.wtb_close)) } })
}

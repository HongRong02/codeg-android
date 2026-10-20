package app.codeg.android.feature.worktasks

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.WorkTask
import app.codeg.android.core.model.wire.WorkTaskDraft
import java.time.OffsetDateTime
import kotlinx.serialization.json.*

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
fun WorkTaskActions(task: WorkTask, ui: WorkTaskUi, viewModel: WorkTaskViewModel, compact: Boolean = false) {
    var pending by remember(task.id) { mutableStateOf<String?>(null) }
    var text by remember { mutableStateOf("") }
    Column {
        if (!compact && task.status in setOf("todo", "failed") && "update" !in ui.hiddenActions) {
            CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = { viewModel.edit(task) }) { Text(stringResource(R.string.wtb_edit)) }
        }
        task.availableActions().filter { it !in ui.hiddenActions && (!compact || it in setOf("start", "delete")) }.chunked(2).forEach { row ->
            Row {
                row.forEach { action ->
                    val label = when {
                        action == "defer" && task.deferredAt != null -> R.string.wtb_undefer
                        action == "archive" && task.archivedAt != null -> R.string.wtb_unarchive
                        else -> actionLabel(action)
                    }
                    CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = {
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
        CodegConfirmDialog(
            onDismiss = { pending = null },
            title = stringResource(actionLabel(action)),
            message = stringResource(R.string.wtb_confirm_target, task.title, task.id),
            target = "${task.title} (#${task.id})",
            confirmLabel = stringResource(R.string.wtb_confirm),
            destructive = action in setOf("delete", "cancel", "cleanup"),
            busy = ui.busy, confirmEnabled = valid,
            onConfirm = { viewModel.action(task, action, text.trim()); pending = null },
            content = {
                if (action in setOf("return", "schedule")) {
                    CodegTextField(value = text, onValueChange = { text = it },
                        label = stringResource(if (action == "return") R.string.wtb_feedback else R.string.wtb_schedule_hint),
                        isError = !valid, enabled = !ui.busy, modifier = Modifier.fillMaxWidth())
                }
            },
        )
    }
}

@Composable
private fun Choice(label: String, value: String, choices: List<Pair<String, () -> Unit>>, enabled: Boolean, selectedIndex: Int = choices.indexOfFirst { it.first == value }) {
    CodegSelect(label = label, selected = choices.getOrNull(selectedIndex),
        options = choices, optionLabel = { it.first }, onSelect = { it.second() }, enabled = enabled)

}

@Composable
fun WorkTaskEditor(ui: WorkTaskUi, viewModel: WorkTaskViewModel) {
    val task = ui.editing
    var title by remember { mutableStateOf(task?.title.orEmpty()) }
    var original by remember { mutableStateOf(task?.config ?: JsonObject(emptyMap())) }
    var description by remember { mutableStateOf((original["display_text"] as? JsonPrimitive)?.contentOrNull.orEmpty()) }
    var folderId by remember { mutableStateOf(task?.folderId) }
    var agent by remember { mutableStateOf((original["agent_type"] as? JsonPrimitive)?.contentOrNull) }
    var templateName by remember { mutableStateOf<String?>(null) }
    val inherit = stringResource(R.string.wtb_inherit)
    CodegDialog(onDismiss = viewModel::dismissEditor,
        title = stringResource(if (task == null) R.string.wtb_create else R.string.wtb_edit),
        content = {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ui.error?.let { Text(it, color = CodegTheme.colors.danger) }
                if (task == null && ui.templates.isNotEmpty()) {
                    Choice(stringResource(R.string.wtb_template), templateName ?: stringResource(R.string.wtb_choose), ui.templates.map { template ->
                            template.name to {
                                templateName = template.name
                                title = template.title; original = template.config
                                description = (original["display_text"] as? JsonPrimitive)?.contentOrNull.orEmpty()
                                agent = (original["agent_type"] as? JsonPrimitive)?.contentOrNull
                            }
                        }, !ui.busy)
                }

                CodegTextField(title, { title = it }, label = stringResource(R.string.wtb_title), enabled = !ui.busy)
                CodegTextField(description, { description = it }, label = stringResource(R.string.wtb_description), singleLine = false, minLines = 3, enabled = !ui.busy)
                Choice(stringResource(R.string.wtb_project), ui.folders.find { it.id == folderId }?.name ?: stringResource(R.string.wtb_choose),
                    ui.folders.map { it.name to { folderId = it.id } }, !ui.busy,
                    selectedIndex = ui.folders.indexOfFirst { it.id == folderId })
                Choice(stringResource(R.string.wtb_agent), agent ?: inherit,
                    listOf(inherit to { agent = null }) + ui.agents.map { it.name.ifBlank { it.agentType.wire } to { agent = it.agentType.wire } }, !ui.busy,
                    selectedIndex = (listOf<String?>(null) + ui.agents.map { it.agentType.wire }).indexOf(agent))
            }
        },
        footer = {
            CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = viewModel::dismissEditor) { Text(stringResource(R.string.wtb_close)) }
            CodegButton(variant = CodegButtonVariant.Default, size = CodegButtonSize.Sm, enabled = !ui.busy && title.isNotBlank() && description.isNotBlank() && ui.folders.any { it.id == folderId }, onClick = {
                    viewModel.save(WorkTaskDraft(folderId!!, title.trim(), taskConfig(original, description.trim(), agent)))
            }) { Text(stringResource(R.string.wtb_save)) }
    })
}

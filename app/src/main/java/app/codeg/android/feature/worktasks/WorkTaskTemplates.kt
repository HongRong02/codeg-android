package app.codeg.android.feature.worktasks

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.WorkTaskTemplate
import app.codeg.android.core.model.wire.WorkTaskTemplateDraft
import kotlinx.serialization.json.*

@Composable
fun WorkTaskManagementMenu(ui: WorkTaskUi, viewModel: WorkTaskViewModel) {
    var expanded by remember { mutableStateOf(false) }
    if (ui.templatesOpen) WorkTaskTemplates(ui, viewModel)
    if (ui.settingsOpen) WorkTaskSettingsSheet(ui, viewModel)
    if (ui.selected != null || !ui.supported || (!ui.templatesSupported && !ui.settingsSupported)) return
    Box {
        CodegIconButton(onClick = { expanded = true }, enabled = !ui.busy) {
            Icon(Icons.Rounded.MoreVert, stringResource(R.string.wtt_menu))
        }
        CodegDropdownMenu(expanded, { expanded = false }) {
            if (ui.templatesSupported) CodegMenuItem(text = { Text(stringResource(R.string.wtt_templates)) }, onClick = {
                expanded = false; viewModel.openTemplates()
            })
            if (ui.settingsSupported) CodegMenuItem(text = { Text(stringResource(R.string.wtt_settings)) }, onClick = {
                expanded = false; viewModel.openSettings()
            })
        }
    }
}

@Composable
fun WorkTaskTemplates(ui: WorkTaskUi, viewModel: WorkTaskViewModel) {
    var editing by remember { mutableStateOf(false) }
    var original by remember { mutableStateOf<WorkTaskTemplate?>(null) }
    var name by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deleting by remember { mutableStateOf<WorkTaskTemplate?>(null) }
    LaunchedEffect(ui.templateRevision) { editing = false }
    CodegDialog(onDismiss = viewModel::closeManagement,
        title = stringResource(R.string.wtt_templates),
        content = {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ui.error?.let { Text(it, color = CodegTheme.colors.danger) }
                // CodegProgress has no indeterminate variant; retain loading semantics.
                if (ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth())
                if (editing) {
                    OutlinedTextField(name, { name = it }, label = { Text(stringResource(R.string.wtt_name)) }, enabled = !ui.busy && original == null)
                    if (original == null) Text(stringResource(R.string.wtt_upsert_hint))
                    OutlinedTextField(title, { title = it }, label = { Text(stringResource(R.string.wtb_title)) }, enabled = !ui.busy)
                    OutlinedTextField(description, { description = it }, label = { Text(stringResource(R.string.wtb_description)) }, minLines = 3, enabled = !ui.busy)
                } else {
                    CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = {
                            original = null; name = ""; title = ""; description = ""; editing = true
                    }) { Text(stringResource(R.string.wtt_new)) }
                    if (ui.templates.isEmpty() && !ui.busy) Text(stringResource(R.string.wtt_empty))
                    ui.templates.forEach { template ->
                        val editTemplate = {
                            original = template; name = template.name; title = template.title
                            description = (template.config["display_text"] as? JsonPrimitive)?.contentOrNull.orEmpty()
                            editing = true
                        }
                        CodegListRow(title = template.name, subtitle = template.title,
                            onClick = { if (!ui.busy) editTemplate() }, trailing = {
                                CodegButton(variant = CodegButtonVariant.Destructive, size = CodegButtonSize.Sm,
                                    enabled = !ui.busy, onClick = { deleting = template }) { Text(stringResource(R.string.wtt_delete)) }
                            })
                    }
                }
            }
        },
        footer = {
            if (editing) CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = { editing = false }) { Text(stringResource(R.string.wtt_cancel)) }
            if (editing) {
                CodegButton(variant = CodegButtonVariant.Default, size = CodegButtonSize.Sm, enabled = !ui.busy && name.isNotBlank() && title.isNotBlank() && description.isNotBlank(), onClick = {
                        val config = original?.config ?: JsonObject(emptyMap())
                        val agent = (config["agent_type"] as? JsonPrimitive)?.contentOrNull
                        viewModel.saveTemplate(WorkTaskTemplateDraft(name.trim(), title.trim(), taskConfig(config, description.trim(), agent)))
                }) { Text(stringResource(R.string.wtb_save)) }
            } else CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = viewModel::closeManagement) { Text(stringResource(R.string.wtb_close)) }
    })
    deleting?.let { target ->
        CodegDialog(onDismiss = { deleting = null },
            title = stringResource(R.string.wtt_delete),
            content = { Text(stringResource(R.string.wtt_delete_confirm, target.name)) },
            footer = {
                CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, onClick = { deleting = null }) { Text(stringResource(R.string.wtt_cancel)) }
                CodegButton(variant = CodegButtonVariant.Destructive, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = { deleting = null; viewModel.deleteTemplate(target.id) }) { Text(stringResource(R.string.wtt_delete)) }
        })
    }
}

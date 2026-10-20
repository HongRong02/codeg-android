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
import app.codeg.android.core.designsystem.theme.CodegTheme
import kotlinx.serialization.json.*

internal fun settingsValue(key: String, text: String): JsonElement? = when (key) {
    "max_concurrent", "auto_compact_percent", "preflight_command_id" -> {
        if (key == "preflight_command_id" && text.isBlank()) JsonNull
        else text.toIntOrNull()?.takeIf {
            when (key) {
                "auto_compact_percent" -> it in 0..100
                "preflight_command_id" -> it > 0
                else -> it >= 0
            }
        }?.let(::JsonPrimitive)
    }
    "config_values", "stage_prompts" -> runCatching {
        (Json.parseToJsonElement(text) as? JsonObject)?.takeIf { map ->
            map.values.all { it is JsonPrimitive && it.isString }
        }
    }.getOrNull()
    "label_snapshot" -> if (text.isBlank()) JsonNull else runCatching { Json.parseToJsonElement(text) }.getOrNull()
    "merge_strategy" -> text.takeIf { it in setOf("merge", "squash") }?.let(::JsonPrimitive)
    else -> if (text.isBlank()) JsonNull else JsonPrimitive(text)
}

private val settingsLabels = linkedMapOf(
    "default_agent_type" to R.string.wtt_default_agent_type,
    "mode_id" to R.string.wtt_mode_id,
    "config_values" to R.string.wtt_config_values,
    "label_snapshot" to R.string.wtt_label_snapshot,
    "auto_process" to R.string.wtt_auto_process,
    "max_concurrent" to R.string.wtt_max_concurrent,
    "merge_strategy" to R.string.wtt_merge_strategy,
    "auto_merge" to R.string.wtt_auto_merge,
    "delete_worktree_default" to R.string.wtt_delete_worktree_default,
    "worktree_root" to R.string.wtt_worktree_root,
    "preflight_command_id" to R.string.wtt_preflight_command_id,
    "preflight_command" to R.string.wtt_preflight_command,
    "init_command" to R.string.wtt_init_command,
    "auto_compact_percent" to R.string.wtt_auto_compact_percent,
    "compact_command" to R.string.wtt_compact_command,
    "stage_prompts" to R.string.wtt_stage_prompts,
)
private val booleanSettings = setOf("auto_process", "auto_merge", "delete_worktree_default")

@Composable
fun WorkTaskSettingsSheet(ui: WorkTaskUi, viewModel: WorkTaskViewModel) {
    var projectMenu by remember { mutableStateOf(false) }
    var reset by remember { mutableStateOf(false) }
    val source = ui.ownSettings ?: ui.effectiveSettings
    var fields by remember(ui.settingsFolder, source) {
        mutableStateOf(source?.mapValues { (_, value) ->
            when (value) {
                JsonNull -> ""
                is JsonPrimitive -> value.content
                else -> value.toString()
            }
        }.orEmpty())
    }
    val parsed = fields.mapValues { (key, text) ->
        if (key in booleanSettings) text.toBooleanStrictOrNull()?.let(::JsonPrimitive)
        else if (key in settingsLabels) settingsValue(key, text)
        else source?.get(key)
    }
    val enabled = !ui.busy && !ui.settingsLoading
    val folder = ui.folders.find { it.id == ui.settingsFolder }
    AlertDialog(onDismissRequest = viewModel::closeManagement,
        title = { Text(stringResource(R.string.wtt_settings)) },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ui.error?.let { Text(it, color = CodegTheme.colors.danger) }
                Box {
                    TextButton(enabled = enabled, onClick = { projectMenu = true }) {
                        Text(folder?.name ?: stringResource(R.string.wtb_project))
                    }
                    DropdownMenu(projectMenu, { projectMenu = false }) {
                        ui.folders.forEach { project ->
                            DropdownMenuItem(text = { Text(project.name) }, onClick = {
                                projectMenu = false; viewModel.loadSettings(project.id)
                            })
                        }
                    }
                }
                if (ui.settingsLoading || ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth())
                ui.effectiveSettings?.let { effective ->
                    Text(stringResource(R.string.wtt_effective), style = MaterialTheme.typography.titleSmall)
                    SettingsSummary(effective)
                    Text(stringResource(R.string.wtt_own), style = MaterialTheme.typography.titleSmall)
                    if (ui.ownSettings == null) Text(stringResource(R.string.wtt_inherited))
                    else SettingsSummary(ui.ownSettings)
                    Text(stringResource(R.string.wtt_edit_settings), style = MaterialTheme.typography.titleSmall)
                    settingsLabels.forEach { (key, label) ->
                        if (key in fields) {
                            val value = fields.getValue(key)
                            when {
                                key in booleanSettings -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(stringResource(label), modifier = Modifier.weight(1f))
                                    Switch(value == "true", { checked -> fields = fields + (key to checked.toString()) }, enabled = enabled)
                                }
                                key == "merge_strategy" -> {
                                    var expanded by remember { mutableStateOf(false) }
                                    Box {
                                        TextButton(enabled = enabled, onClick = { expanded = true }) {
                                            Text(stringResource(label) + ": " + stringResource(if (value == "merge") R.string.wtt_merge else R.string.wtt_squash))
                                        }
                                        DropdownMenu(expanded, { expanded = false }) {
                                            listOf("squash" to R.string.wtt_squash, "merge" to R.string.wtt_merge).forEach { (choice, name) ->
                                                DropdownMenuItem(text = { Text(stringResource(name)) }, onClick = {
                                                    fields = fields + (key to choice); expanded = false
                                                })
                                            }
                                        }
                                    }
                                }
                                else -> OutlinedTextField(value, { fields = fields + (key to it) },
                                    label = { Text(stringResource(label)) }, enabled = enabled,
                                    isError = parsed[key] == null,
                                    supportingText = { if (parsed[key] == null) Text(stringResource(R.string.wtt_invalid)) })
                            }
                        }
                    }
                    TextButton(enabled = enabled && ui.ownSettings != null, onClick = { reset = true }) {
                        Text(stringResource(R.string.wtt_reset))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(enabled = enabled && source != null && folder != null && parsed.values.none { it == null }, onClick = {
                viewModel.saveSettings(folder!!.id, JsonObject(parsed.mapValues { it.value!! }))
            }) { Text(stringResource(R.string.wtb_save)) }
        },
        dismissButton = { TextButton(enabled = !ui.busy, onClick = viewModel::closeManagement) { Text(stringResource(R.string.wtb_close)) } })
    if (reset && folder != null) AlertDialog(onDismissRequest = { reset = false },
        title = { Text(stringResource(R.string.wtt_reset)) },
        text = { Text(stringResource(R.string.wtt_reset_confirm, folder.name)) },
        confirmButton = { TextButton(enabled = enabled, onClick = { reset = false; viewModel.resetSettings(folder.id) }) { Text(stringResource(R.string.wtt_reset)) } },
        dismissButton = { TextButton(onClick = { reset = false }) { Text(stringResource(R.string.wtt_cancel)) } })
}

@Composable
private fun SettingsSummary(settings: JsonObject) {
    settingsLabels.forEach { (key, label) ->
        settings[key]?.let { value ->
            val display = when (value) {
                JsonNull -> stringResource(R.string.wtt_unset)
                is JsonPrimitive -> when (value.booleanOrNull) {
                    true -> stringResource(R.string.wtt_on)
                    false -> stringResource(R.string.wtt_off)
                    null -> value.content
                }
                else -> value.toString()
            }
            Text(stringResource(label) + ": " + display, style = MaterialTheme.typography.bodySmall)
        }
    }
}

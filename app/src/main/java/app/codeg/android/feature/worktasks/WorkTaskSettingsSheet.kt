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
    CodegDialog(onDismiss = viewModel::closeManagement,
        title = stringResource(R.string.wtt_settings),
        content = {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ui.error?.let { Text(it, color = CodegTheme.colors.danger) }
                Box {
                    CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = enabled, onClick = { projectMenu = true }) {
                        Text(folder?.name ?: stringResource(R.string.wtb_project))
                    }
                    CodegDropdownMenu(projectMenu, { projectMenu = false }) {
                        ui.folders.forEach { project ->
                            CodegMenuItem(text = { Text(project.name) }, onClick = {
                                    projectMenu = false; viewModel.loadSettings(project.id)
                            })
                        }
                    }
                }
                // CodegProgress has no indeterminate variant; retain loading semantics.
                if (ui.settingsLoading || ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth())
                ui.effectiveSettings?.let { effective ->
                    CodegSectionHeader(title = stringResource(R.string.wtt_effective))
                    SettingsSummary(effective)
                    CodegSectionHeader(title = stringResource(R.string.wtt_own))
                    if (ui.ownSettings == null) Text(stringResource(R.string.wtt_inherited))
                    else SettingsSummary(ui.ownSettings)
                    CodegSectionHeader(title = stringResource(R.string.wtt_edit_settings))
                    settingsLabels.forEach { (key, label) ->
                        if (key in fields) {
                            val value = fields.getValue(key)
                            when {
                                key in booleanSettings -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(stringResource(label), modifier = Modifier.weight(1f))
                                    CodegSwitch(value == "true", { checked -> fields = fields + (key to checked.toString()) }, enabled = enabled)
                                }
                                key == "merge_strategy" -> {
                                    var expanded by remember { mutableStateOf(false) }
                                    Box {
                                        CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = enabled, onClick = { expanded = true }) {
                                            Text(stringResource(label) + ": " + stringResource(if (value == "merge") R.string.wtt_merge else R.string.wtt_squash))
                                        }
                                        CodegDropdownMenu(expanded, { expanded = false }) {
                                            listOf("squash" to R.string.wtt_squash, "merge" to R.string.wtt_merge).forEach { (choice, name) ->
                                                CodegMenuItem(text = { Text(stringResource(name)) }, onClick = {
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
                    CodegButton(variant = CodegButtonVariant.Destructive, size = CodegButtonSize.Sm, enabled = enabled && ui.ownSettings != null, onClick = { reset = true }) {
                        Text(stringResource(R.string.wtt_reset))
                    }
                }
            }
        },
        footer = {
            CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = viewModel::closeManagement) { Text(stringResource(R.string.wtb_close)) }
            CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = enabled && source != null && folder != null && parsed.values.none { it == null }, onClick = {
                    viewModel.saveSettings(folder!!.id, JsonObject(parsed.mapValues { it.value!! }))
            }) { Text(stringResource(R.string.wtb_save)) }
    })
    if (reset && folder != null) CodegDialog(onDismiss = { reset = false },
        title = stringResource(R.string.wtt_reset),
        content = { Text(stringResource(R.string.wtt_reset_confirm, folder.name)) },
        footer = {
            CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, onClick = { reset = false }) { Text(stringResource(R.string.wtt_cancel)) }
            CodegButton(variant = CodegButtonVariant.Destructive, size = CodegButtonSize.Sm, enabled = enabled, onClick = { reset = false; viewModel.resetSettings(folder.id) }) { Text(stringResource(R.string.wtt_reset)) }
    })
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

package app.codeg.android.feature.settings

import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegCard
import app.codeg.android.core.designsystem.component.CodegConfirmDialog
import app.codeg.android.core.designsystem.component.CodegFullScreenSheet
import app.codeg.android.core.designsystem.component.CodegIconButton
import app.codeg.android.core.designsystem.component.CodegSkeleton
import app.codeg.android.core.designsystem.component.CodegSwitch
import app.codeg.android.core.designsystem.component.CodegTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.withOpacity
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.AssistantDraft

@Composable
fun AssistantsContent(viewModel: AssistantsViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors
    var deleting by remember(ui.generation) { mutableStateOf<Assistant?>(null) }
    Column(Modifier.fillMaxSize().padding(horizontal = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (ui.supported) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CodegButton(onClick = { viewModel.load() }, enabled = !ui.loading && !ui.busy, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.assistants_refresh)) }
                CodegButton(onClick = { viewModel.edit(null) }, enabled = !ui.loading && !ui.busy, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.assistants_create)) }
            }
        }
        if (ui.loading || ui.busy) CodegSkeleton(Modifier.fillMaxWidth().height(4.dp))
        if (!ui.editing) ui.error?.let { Text(it, color = colors.danger) }
        if (!ui.supported) Text(stringResource(R.string.assistants_unsupported), color = colors.textSecondary)
        else if (!ui.loading && ui.rows.isEmpty()) Text(stringResource(R.string.assistants_empty), color = colors.textSecondary)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(bottom = 16.dp)) {
            itemsIndexed(ui.rows, key = { _, row -> row.id }) { index, row ->
                CodegCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            val tint = when (row.avatar.color) {
                                "red", "pink" -> colors.danger
                                "slate" -> colors.textSecondary
                                else -> colors.accent
                            }
                            // TODO(T53): CodegAvatar
                            Box(Modifier.size(40.dp).background(tint.withOpacity(0.16f), CircleShape), contentAlignment = Alignment.Center) {
                                val glyph = if (row.avatar.kind == "emoji") row.avatar.emoji.orEmpty() else assistantMonogram(row.name)
                                Text(glyph, color = tint, fontSize = 20.sp)
                            }
                            Column(Modifier.weight(1f)) {
                                Text(row.name, color = colors.textPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(row.labelSnapshot.agent, color = colors.textSecondary, fontSize = 12.sp)
                                if (row.description.isNotBlank()) Text(row.description, color = colors.textSecondary, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            }
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            CodegIconButton(onClick = { viewModel.move(row, -1) }, enabled = index > 0 && !ui.busy && !ui.loading) { Icon(Icons.Rounded.ArrowUpward, stringResource(R.string.assistants_up)) }
                            CodegIconButton(onClick = { viewModel.move(row, 1) }, enabled = index < ui.rows.lastIndex && !ui.busy && !ui.loading) { Icon(Icons.Rounded.ArrowDownward, stringResource(R.string.assistants_down)) }
                            CodegIconButton(onClick = { viewModel.edit(row) }, enabled = !ui.busy && !ui.loading) { Icon(Icons.Rounded.Edit, stringResource(R.string.assistants_edit)) }
                            CodegIconButton(onClick = { deleting = row }, enabled = !ui.busy && !ui.loading) { Icon(Icons.Rounded.Delete, stringResource(R.string.assistants_delete), tint = colors.danger) }
                        }
                    }
                }
            }
        }
    }
    deleting?.let { row ->
        CodegConfirmDialog(
            title = stringResource(R.string.assistants_delete),
            message = stringResource(R.string.assistants_delete_confirm, row.name),
            target = row.name,
            confirmLabel = stringResource(R.string.common_delete),
            destructive = true,
            onConfirm = { if (!ui.busy) { deleting = null; viewModel.delete(row) } },
            onDismiss = { deleting = null },
        )
    }
    if (ui.editing) key(ui.generation, ui.editor?.id) { AssistantEditor(ui, viewModel) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssistantEditor(ui: AssistantsUi, vm: AssistantsViewModel) {
    val old = ui.editor
    val colors = CodegTheme.colors
    var name by rememberSaveable { mutableStateOf(old?.name.orEmpty()) }
    var description by rememberSaveable { mutableStateOf(old?.description.orEmpty()) }
    var instructions by rememberSaveable { mutableStateOf(old?.instructions.orEmpty()) }
    var enabled by rememberSaveable { mutableStateOf(old?.enabled ?: true) }
    var emoji by rememberSaveable { mutableStateOf(old?.avatar?.emoji.orEmpty()) }
    var color by rememberSaveable { mutableStateOf(old?.avatar?.color ?: "blue") }
    var avatarTouched by rememberSaveable { mutableStateOf(false) }
    var agent by rememberSaveable { mutableStateOf(old?.agentType) }
    var agentTouched by rememberSaveable { mutableStateOf(false) }
    var mode by rememberSaveable { mutableStateOf<String?>(null) }
    var values by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    val snapshot = ui.options
    val agentLabel = ui.agents.find { it.agentType == agent }?.name ?: agent?.displayName.orEmpty()
    val config = if (!agentTouched && old != null) null else agent?.let { a -> snapshot?.let { assistantConfig(a, agentLabel, it, mode, values) } }
    val valid = name.isNotBlank() && name.codePointCount(0, name.length) <= 40 && description.codePointCount(0, description.length) <= 200 && instructions.codePointCount(0, instructions.length) <= 20000 && (old != null && !agentTouched || config != null)
    CodegFullScreenSheet(title = stringResource(if (old == null) R.string.assistants_create else R.string.assistants_edit), onDismiss = { if (!ui.busy) vm.closeEditor() }) {

        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AssistantField(name, { name = it }, R.string.assistants_name, !ui.busy)
            AssistantField(description, { description = it }, R.string.assistants_description, !ui.busy)
            AssistantField(emoji, { emoji = it; avatarTouched = true }, R.string.assistants_emoji, !ui.busy)
            val colorChoices = listOf(
                "slate" to stringResource(R.string.assistants_color_slate), "red" to stringResource(R.string.assistants_color_red),
                "orange" to stringResource(R.string.assistants_color_orange), "amber" to stringResource(R.string.assistants_color_amber),
                "green" to stringResource(R.string.assistants_color_green), "teal" to stringResource(R.string.assistants_color_teal),
                "sky" to stringResource(R.string.assistants_color_sky), "blue" to stringResource(R.string.assistants_color_blue),
                "violet" to stringResource(R.string.assistants_color_violet), "pink" to stringResource(R.string.assistants_color_pink),
            )
            AssistantChoice(stringResource(R.string.assistants_color), colorChoices.find { it.first == color }?.second.orEmpty(), colorChoices, !ui.busy) { color = it; avatarTouched = true }
            AssistantChoice(stringResource(R.string.assistants_agent), agentLabel,
                ui.agents.filter { it.enabled && it.available || it.agentType == old?.agentType }.map { it.agentType.wire to it.name }, !ui.busy) { wire ->
                    agent = AgentType.knownFromWire(wire)
                    agentTouched = true
                    mode = null
                    values = emptyMap()
                    agent?.let(vm::probeAgent)
                }
            if (old != null && !agentTouched) {
                Text(stringResource(R.string.assistants_preserved), color = colors.textSecondary)
                old.labelSnapshot.mode?.let { Text(it, color = colors.textSecondary) }
                old.labelSnapshot.options.forEach { Text("${it.name}: ${it.valueLabel}", color = colors.textSecondary) }
                CodegButton(onClick = { agentTouched = true; old.agentType.let(vm::probeAgent) }, enabled = !ui.busy, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.assistants_configure)) }
            }
            if (ui.probing) CircularProgressIndicator(color = colors.accent)
            ui.optionsError?.let {
                Text(it, color = colors.danger)
                CodegButton(onClick = { agent?.let(vm::probeAgent) }, enabled = !ui.busy, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.assistants_retry)) }
            }
            snapshot?.let { options ->
                val modes = options.modes?.availableModes.orEmpty()
                if (modes.isNotEmpty()) AssistantChoice(stringResource(R.string.assistants_mode), modes.find { it.id == mode }?.name.orEmpty(), modes.map { it.id to it.name }, !ui.busy) { mode = it }
                options.configOptions.forEach { option ->
                    val choices = if (option.kind.type == "boolean") listOf("true" to stringResource(R.string.assistants_on), "false" to stringResource(R.string.assistants_off)) else (option.kind.options + option.kind.groups.flatMap { it.options }).map { it.value to it.name }
                    AssistantChoice(option.name, choices.find { it.first == values[option.id] }?.second.orEmpty(), choices, !ui.busy) { value -> values = values + (option.id to value) }
                }
            }
            AssistantField(instructions, { instructions = it }, R.string.assistants_instructions, !ui.busy, singleLine = false)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.assistants_enabled), color = colors.textPrimary, modifier = Modifier.weight(1f))
                CodegSwitch(checked = enabled, onCheckedChange = { enabled = it }, enabled = !ui.busy)
            }
            Text(stringResource(R.string.assistants_limits), color = colors.textSecondary, fontSize = 12.sp)
            ui.error?.let { Text(it, color = colors.danger) }
        }
        Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.End) {
            CodegButton(onClick = { vm.closeEditor() }, enabled = !ui.busy, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.assistants_cancel)) }
            CodegButton(onClick = {
                val avatar = if (!avatarTouched && old != null) old.avatar else AssistantAvatar(if (emoji.isBlank()) "monogram" else "emoji", color, emoji.takeIf { it.isNotBlank() })
                vm.save(AssistantDraft(name.trim(), description, avatar, config, instructions, enabled))
            }, enabled = valid && !ui.busy && !ui.probing, variant = CodegButtonVariant.Default) { Text(stringResource(if (ui.busy) R.string.assistants_saving else R.string.assistants_save)) }
        }
    }
}

@Composable
private fun AssistantField(value: String, onChange: (String) -> Unit, label: Int, enabled: Boolean, singleLine: Boolean = true) {
    CodegTextField(value = value, onValueChange = { if (enabled) (onChange)(it) }, label = stringResource(label), modifier = Modifier.fillMaxWidth(), singleLine = singleLine)
}

@Composable
private fun AssistantChoice(label: String, value: String, choices: List<Pair<String, String>>, enabled: Boolean, onPick: (String) -> Unit) {
    SettingsChoice(label, choices.firstOrNull { it.second == value }?.first ?: value, choices, enabled, onPick)
}

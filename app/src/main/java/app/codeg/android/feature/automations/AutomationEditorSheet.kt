package app.codeg.android.feature.automations

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
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.AutomationDraft
import kotlinx.serialization.json.*
import java.time.ZoneId

internal fun automationDraft(
    original: Automation?, name: String, folderId: Int, agent: AgentType,
    prompt: String, cron: String, timezone: String, scheduled: Boolean,
): AutomationDraft {
    val config = original?.config.orEmpty().toMutableMap()
    if ("action" !in config) config["action"] = JsonPrimitive("launch_session")
    config["display_text"] = JsonPrimitive(prompt.trim())
    val originalPrompt = (original?.config?.get("display_text") as? JsonPrimitive)?.content
    if (prompt.trim() != originalPrompt?.trim() || "prompt_blocks" !in config) {
        config["prompt_blocks"] = buildJsonArray { add(buildJsonObject {
            put("type", "text"); put("text", prompt.trim())
        }) }
    }
    if (original != null && original.agentType != agent) {
        config.remove("mode_id")
        config.remove("config_values")
        config.remove("label_snapshot")
    }
    return AutomationDraft(name.trim(), original?.enabled ?: true,
        if (scheduled) TriggerKind.SCHEDULE else TriggerKind.MANUAL,
        if (scheduled) cron.trim() else null, timezone.trim(), agent, folderId,
        original?.isolation ?: IsolationMode.WORKTREE_PER_RUN,
        original?.branch, original?.isRemoteBranch ?: false, JsonObject(config))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AutomationEditorSheet(ui: AutomationUiState, viewModel: AutomationViewModel) {
    val original = ui.editing
    var name by remember { mutableStateOf(original?.name.orEmpty()) }
    var folderId by remember { mutableStateOf(original?.rootFolderId) }
    var agent by remember { mutableStateOf(original?.agentType ?: AgentType.CLAUDE_CODE) }
    var prompt by remember { mutableStateOf((original?.config?.get("display_text") as? JsonPrimitive)?.content.orEmpty()) }
    var cron by remember { mutableStateOf(original?.cron ?: "0 * * * *") }
    var timezone by remember { mutableStateOf(original?.timezone ?: ZoneId.systemDefault().id) }
    var scheduled by remember { mutableStateOf(original?.triggerKind != TriggerKind.MANUAL) }
    LaunchedEffect(cron, timezone, scheduled) {
        if (scheduled) viewModel.preview(cron.trim(), timezone.trim())
    }
    ModalBottomSheet(onDismissRequest = viewModel::closeEditor) {
        Column(Modifier.fillMaxWidth().imePadding().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(stringResource(if (original == null) R.string.aw_create else R.string.aw_edit))
            OutlinedTextField(name, { name = it }, label = { Text(stringResource(R.string.aw_name)) }, modifier = Modifier.fillMaxWidth())
            Choice(stringResource(R.string.aw_project), ui.folders.map { it.id to it.name }, folderId, { folderId = it })
            Choice(stringResource(R.string.aw_agent), AgentType.entries.map { it to it.displayName }, agent, { agent = it })
            OutlinedTextField(prompt, { prompt = it }, label = { Text(stringResource(R.string.aw_prompt)) }, minLines = 3, modifier = Modifier.fillMaxWidth())
            Row { Switch(scheduled, { scheduled = it }); Text(stringResource(R.string.aw_scheduled)) }
            if (scheduled) {
                OutlinedTextField(cron, { cron = it }, label = { Text(stringResource(R.string.aw_cron)) }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(timezone, { timezone = it }, label = { Text(stringResource(R.string.aw_timezone)) }, modifier = Modifier.fillMaxWidth())
                if (ui.previewLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
                ui.previewError?.let { Text(it, color = CodegTheme.colors.danger) }
                ui.preview?.let { Text(stringResource(R.string.aw_next, it)) }
            }
            ui.error?.let { Text(it, color = CodegTheme.colors.danger) }
            Button(onClick = {
                viewModel.save(automationDraft(original, name, requireNotNull(folderId), agent, prompt, cron, timezone, scheduled))
            }, enabled = !ui.busy && name.isNotBlank() && prompt.isNotBlank() && folderId != null &&
                (!scheduled || (!ui.previewLoading && ui.previewSchedule == (cron.trim() to timezone.trim()) && ui.previewError == null && ui.preview != null && ui.preview != "null"))) {
                Text(stringResource(R.string.aw_save))
            }
        }
    }
}

@Composable
private fun <T> Choice(label: String, items: List<Pair<T, String>>, selected: T?, onSelect: (T) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextButton(onClick = { expanded = true }) {
            Text("$label: ${items.firstOrNull { it.first == selected }?.second.orEmpty()}")
        }
        DropdownMenu(expanded, { expanded = false }) {
            items.forEach { (value, title) -> DropdownMenuItem(text = { Text(title) }, onClick = { onSelect(value); expanded = false }) }
        }
    }
}

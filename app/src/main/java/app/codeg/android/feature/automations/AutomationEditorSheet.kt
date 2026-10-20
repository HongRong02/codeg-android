package app.codeg.android.feature.automations

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.AutomationDraft
import java.time.ZoneId
import kotlinx.serialization.json.*

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
    CodegSheet(title = stringResource(if (original == null) R.string.aw_create else R.string.aw_edit), onDismiss = viewModel::closeEditor,
        footer = {
            CodegButton(onClick = {
                viewModel.save(automationDraft(original, name, requireNotNull(folderId), agent, prompt, cron, timezone, scheduled))
            }, enabled = !ui.busy && name.isNotBlank() && prompt.isNotBlank() && folderId != null &&
                (!scheduled || (!ui.previewLoading && ui.previewSchedule == (cron.trim() to timezone.trim()) && ui.previewError == null && ui.preview != null && ui.preview != "null"))) {
                Text(stringResource(R.string.aw_save))
            }
        }) {
        Column(Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            CodegTextField(name, { name = it }, label = stringResource(R.string.aw_name))
            Choice(stringResource(R.string.aw_project), ui.folders.map { it.id to it.name }, folderId, { folderId = it })
            Choice(stringResource(R.string.aw_agent), AgentType.entries.map { it to it.displayName }, agent, { agent = it })
            CodegTextField(prompt, { prompt = it }, label = stringResource(R.string.aw_prompt), singleLine = false)
            CodegSwitch(scheduled, { scheduled = it }, label = stringResource(R.string.aw_scheduled))
            if (scheduled) {
                CodegTextField(cron, { cron = it }, label = stringResource(R.string.aw_cron))
                CodegTextField(timezone, { timezone = it }, label = stringResource(R.string.aw_timezone))
                // CodegProgress has no indeterminate variant; retain loading semantics.
                if (ui.previewLoading) LinearProgressIndicator(Modifier.fillMaxWidth())
                ui.previewError?.let { Text(it, color = CodegTheme.colors.danger) }
                ui.preview?.let { Text(stringResource(R.string.aw_next, it)) }
            }
            ui.error?.let { Text(it, color = CodegTheme.colors.danger) }
        }
    }
}

@Composable
private fun <T> Choice(label: String, items: List<Pair<T, String>>, selected: T?, onSelect: (T) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, onClick = { expanded = true }) {
            Text("$label: ${items.firstOrNull { it.first == selected }?.second.orEmpty()}")
        }
        CodegDropdownMenu(expanded, { expanded = false }) {
            items.forEach { (value, title) -> CodegMenuItem(text = { Text(title) }, onClick = { onSelect(value); expanded = false }) }
        }
    }
}

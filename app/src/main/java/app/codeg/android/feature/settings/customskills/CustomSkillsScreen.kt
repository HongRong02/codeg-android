package app.codeg.android.feature.settings.customskills

import app.codeg.android.core.designsystem.component.AgentIcon
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegCard
import app.codeg.android.core.designsystem.component.CodegCheckbox
import app.codeg.android.core.designsystem.component.CodegConfirmDialog
import app.codeg.android.core.designsystem.component.CodegFullScreenSheet
import app.codeg.android.core.designsystem.component.CodegRadioGroup
import app.codeg.android.core.designsystem.component.CodegSkeleton
import app.codeg.android.core.designsystem.component.CodegSwitch
import app.codeg.android.core.designsystem.component.CodegTextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.AgentSkillItem
import app.codeg.android.core.model.CustomSkill
import app.codeg.android.core.model.ExpertLinkState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

/** Kept outside the per-agent empty state so the library works without an enabled agent. */
@Composable
fun CustomSkillsEntry(onClosed: () -> Unit, viewModel: CustomSkillsViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    var open by remember { mutableStateOf(false) }
    if (ui.supported) {
        CodegButton(onClick = { open = true; viewModel.refresh() }, modifier = Modifier.fillMaxWidth(), variant = CodegButtonVariant.Ghost) {
            Text(stringResource(R.string.custom_skills_title))
        }
    }
    if (ui.error != null && !open) {
        CustomSkillsError(ui.error)
        CodegButton(enabled = !ui.loading, onClick = viewModel::refresh, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.common_retry)) }
    }
    if (open && ui.supported) {
        CustomSkillsPage(ui, viewModel) { open = false; onClosed() }
    }
}

private enum class EditorMode { CREATE, EDIT, DUPLICATE }
private data class EditorTarget(val mode: EditorMode, val id: String = "")

@Composable
private fun CustomSkillsPage(ui: CustomSkillsUi, viewModel: CustomSkillsViewModel, onDismiss: () -> Unit) {
    var editor by remember { mutableStateOf<EditorTarget?>(null) }
    var deleting by remember { mutableStateOf<CustomSkill?>(null) }
    var importing by remember { mutableStateOf(false) }
    CustomSkillsFrame(stringResource(R.string.custom_skills_title), onDismiss) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CodegButton(enabled = !ui.busy && !ui.loading, onClick = { editor = EditorTarget(EditorMode.CREATE) }, variant = CodegButtonVariant.Ghost) {
                Text(stringResource(R.string.custom_skills_create))
            }
            CodegButton(enabled = !ui.busy && !ui.loading && ui.agents.isNotEmpty(), onClick = { importing = true }, variant = CodegButtonVariant.Ghost) {
                Text(stringResource(R.string.custom_skills_import))
            }
            CodegButton(enabled = !ui.busy && !ui.loading, onClick = viewModel::refresh, variant = CodegButtonVariant.Ghost) {
                Text(stringResource(R.string.common_retry))
            }
        }
        CustomSkillsError(ui.error)
        if (ui.loading || ui.busy) CodegSkeleton(Modifier.fillMaxWidth().height(4.dp))
        LazyColumn(Modifier.heightIn(max = androidx.compose.ui.platform.LocalConfiguration.current.screenHeightDp.dp * 0.65f), contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (ui.skills.isEmpty() && !ui.loading) item {
                Text(stringResource(R.string.custom_skills_empty), color = CodegTheme.colors.textSecondary)
            }
            items(ui.skills, key = { it.id }) { skill ->
                CodegCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(skill.name.ifBlank { skill.id }, color = CodegTheme.colors.textPrimary, style = MaterialTheme.typography.titleMedium)
                        if (skill.name.isNotBlank() && skill.name != skill.id) Text(skill.id, color = CodegTheme.colors.textSecondary)
                        skill.description?.takeIf { it.isNotBlank() }?.let { Text(it, color = CodegTheme.colors.textSecondary) }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            CodegButton(enabled = !ui.busy && !ui.loading, onClick = { editor = EditorTarget(EditorMode.EDIT, skill.id) }, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.custom_skills_edit)) }
                            CodegButton(enabled = !ui.busy && !ui.loading, onClick = { editor = EditorTarget(EditorMode.DUPLICATE, skill.id) }, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.custom_skills_duplicate)) }
                            CodegButton(enabled = !ui.busy && !ui.loading, onClick = { deleting = skill }, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.common_delete)) }
                        }
                        ui.agents.forEach { agent ->
                            val status = ui.statuses.find { it.expertId == skill.id && it.agentType == agent }
                            val enabled = status?.state == ExpertLinkState.LINKED_TO_CODEG
                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                AgentIcon(agent, size = 20.dp)
                                Column(Modifier.weight(1f)) {
                                    Text(agent.displayName, color = CodegTheme.colors.textPrimary)
                                    Text(stringResource(linkStateLabel(status?.state)), color = CodegTheme.colors.textSecondary, style = MaterialTheme.typography.bodySmall)
                                }
                                val switchLabel = stringResource(R.string.custom_skills_link_label, skill.id, agent.displayName)
                                CodegSwitch(modifier = Modifier.semantics { contentDescription = switchLabel }, checked = enabled, enabled = !ui.busy && !ui.loading && (enabled || canEnableCustomSkill(status?.state)), onCheckedChange = { viewModel.toggle(skill.id, agent, it) })
                            }
                        }
                    }
                }
            }
        }
    }
    editor?.let { target -> CustomSkillsEditor(target, ui, viewModel) { editor = null } }
    if (importing) CustomSkillsImport(ui, viewModel) { importing = false }
    deleting?.let { skill ->
        CodegConfirmDialog(
            title = stringResource(R.string.common_delete),
            message = stringResource(R.string.custom_skills_delete_confirm, skill.id),
            target = skill.id,
            confirmLabel = stringResource(R.string.common_delete),
            destructive = true,
            onConfirm = { if (!ui.busy) { viewModel.delete(skill.id); deleting = null } },
            onDismiss = { deleting = null },
        )
    }
}

private fun linkStateLabel(state: ExpertLinkState?): Int = when (state) {
    ExpertLinkState.LINKED_TO_CODEG -> R.string.custom_skills_linked
    ExpertLinkState.LINKED_ELSEWHERE -> R.string.custom_skills_elsewhere
    ExpertLinkState.BLOCKED_BY_REAL_DIRECTORY -> R.string.custom_skills_blocked
    ExpertLinkState.BROKEN -> R.string.custom_skills_broken
    else -> R.string.custom_skills_unlinked
}

@Composable
private fun CustomSkillsEditor(target: EditorTarget, ui: CustomSkillsUi, viewModel: CustomSkillsViewModel, onDismiss: () -> Unit) {
    val scope = rememberCoroutineScope()
    var id by remember(target) { mutableStateOf(if (target.mode == EditorMode.EDIT) target.id else "") }
    var content by remember(target) { mutableStateOf("") }
    var loading by remember(target) { mutableStateOf(target.mode == EditorMode.EDIT) }
    var loaded by remember(target) { mutableStateOf(target.mode != EditorMode.EDIT) }
    var error by remember(target) { mutableStateOf<String?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    LaunchedEffect(target, retry) {
        if (target.mode == EditorMode.EDIT) {
            loading = true
            try { content = viewModel.read(target.id); loaded = true; error = null }
            catch (e: CancellationException) { throw e }
            catch (e: Exception) { error = e.message }
            finally { loading = false }
        }
    }
    val title = when (target.mode) {
        EditorMode.CREATE -> R.string.custom_skills_create
        EditorMode.EDIT -> R.string.custom_skills_edit
        EditorMode.DUPLICATE -> R.string.custom_skills_duplicate
    }
    CustomSkillsFrame(stringResource(title), { if (!ui.busy) onDismiss() }) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (target.mode == EditorMode.DUPLICATE) Text(stringResource(R.string.custom_skills_source, target.id), color = CodegTheme.colors.textSecondary)
            if (target.mode == EditorMode.EDIT) Text(target.id, color = CodegTheme.colors.textSecondary)
            else CodegTextField(id, { id = it }, label = stringResource(R.string.custom_skills_id), mono = true)
            if (loading) CircularProgressIndicator(color = CodegTheme.colors.accent)
            if (loaded && target.mode != EditorMode.DUPLICATE) {
                CodegTextField(content, { content = it }, label = stringResource(R.string.custom_skills_content), mono = true, singleLine = false)
            }
            CustomSkillsError(error ?: ui.error)
            if (!loaded && !loading) CodegButton(onClick = { retry++ }, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.common_retry)) }
            CodegButton(enabled = loaded && !loading && !ui.busy && !ui.loading && id.trim().isNotEmpty() &&
                (target.mode == EditorMode.DUPLICATE || content.isNotBlank()), onClick = {
                    val savedId = id.trim()
                    val savedContent = content
                    scope.launch {
                        val ok = if (target.mode == EditorMode.DUPLICATE) viewModel.duplicate(target.id, savedId)
                        else viewModel.save(savedId, savedContent, target.mode == EditorMode.CREATE)
                        if (ok) onDismiss()
                    }
                }, variant = CodegButtonVariant.Default) { Text(stringResource(R.string.common_save)) }
            if (ui.busy) CodegSkeleton(Modifier.fillMaxWidth().height(4.dp))
        }
    }
}

@Composable
private fun CustomSkillsImport(ui: CustomSkillsUi, viewModel: CustomSkillsViewModel, onDismiss: () -> Unit) {
    val scope = rememberCoroutineScope()
    var agent by remember { mutableStateOf(ui.agents.firstOrNull()) }
    var candidates by remember { mutableStateOf<List<AgentSkillItem>>(emptyList()) }
    var selected by remember { mutableStateOf<Set<String>>(emptySet()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    LaunchedEffect(agent, retry) {
        candidates = emptyList(); selected = emptySet(); error = null
        val current = agent ?: return@LaunchedEffect
        loading = true
        try { candidates = viewModel.importCandidates(current) }
        catch (e: CancellationException) { throw e }
        catch (e: Exception) { error = e.message }
        finally { loading = false }
    }
    CustomSkillsFrame(stringResource(R.string.custom_skills_import), { if (!ui.busy) onDismiss() }) {
        CustomSkillsError(error ?: ui.error)
        LazyColumn(Modifier.heightIn(max = androidx.compose.ui.platform.LocalConfiguration.current.screenHeightDp.dp * 0.65f), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                CodegRadioGroup(options = ui.agents.map { it.displayName }, value = agent?.displayName.orEmpty(),
                    enabled = !ui.busy, onSelect = { name -> agent = ui.agents.firstOrNull { it.displayName == name } })
            }
            if (loading) item { CircularProgressIndicator(color = CodegTheme.colors.accent) }
            if (error != null && !loading) item { CodegButton(onClick = { retry++ }, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.common_retry)) } }
            if (!loading && error == null && candidates.isEmpty()) item { Text(stringResource(R.string.custom_skills_import_empty), color = CodegTheme.colors.textSecondary) }
            items(candidates, key = { it.id }) { skill ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CodegCheckbox(checked = skill.id in selected, enabled = !ui.busy && !loading, onCheckedChange = { checked -> selected = if (checked) selected + skill.id else selected - skill.id })
                    Column {
                        Text(skill.name.ifBlank { skill.id }, color = CodegTheme.colors.textPrimary)
                        Text(skill.id, color = CodegTheme.colors.textSecondary)
                    }
                }
            }
        }
        CodegButton(enabled = !ui.busy && !loading && selected.isNotEmpty(), onClick = {
            val current = agent ?: return@CodegButton
            val ids = selected.toList()
            scope.launch { if (viewModel.importFromAgent(current, ids)) onDismiss() }
        }, modifier = Modifier.fillMaxWidth(), variant = CodegButtonVariant.Default) { Text(stringResource(R.string.custom_skills_import_selected, selected.size)) }
        if (ui.busy) CodegSkeleton(Modifier.fillMaxWidth().height(4.dp))
    }
}

@Composable
private fun CustomSkillsError(error: String?) {
    error?.let { Text(it, color = CodegTheme.colors.danger, modifier = Modifier.padding(12.dp)) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomSkillsFrame(title: String, onDismiss: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    CodegFullScreenSheet(title = title, onDismiss = onDismiss) {

        content()
    }
}

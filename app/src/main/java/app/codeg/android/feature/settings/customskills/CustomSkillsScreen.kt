package app.codeg.android.feature.settings.customskills

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.AgentType
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
        TextButton(onClick = { open = true; viewModel.refresh() }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.custom_skills_title), color = CodegTheme.colors.accent)
        }
    }
    if (ui.error != null && !open) {
        CustomSkillsError(ui.error)
        TextButton(enabled = !ui.loading, onClick = viewModel::refresh) { Text(stringResource(R.string.common_retry)) }
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
            TextButton(enabled = !ui.busy && !ui.loading, onClick = { editor = EditorTarget(EditorMode.CREATE) }) {
                Text(stringResource(R.string.custom_skills_create))
            }
            TextButton(enabled = !ui.busy && !ui.loading && ui.agents.isNotEmpty(), onClick = { importing = true }) {
                Text(stringResource(R.string.custom_skills_import))
            }
            TextButton(enabled = !ui.busy && !ui.loading, onClick = viewModel::refresh) {
                Text(stringResource(R.string.common_retry))
            }
        }
        CustomSkillsError(ui.error)
        if (ui.loading || ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth(), color = CodegTheme.colors.accent)
        LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (ui.skills.isEmpty() && !ui.loading) item {
                Text(stringResource(R.string.custom_skills_empty), color = CodegTheme.colors.textSecondary)
            }
            items(ui.skills, key = { it.id }) { skill ->
                Column(Modifier.fillMaxWidth().webSurface(CodegShapes.extraSmall).padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(skill.name.ifBlank { skill.id }, color = CodegTheme.colors.textPrimary, style = MaterialTheme.typography.titleMedium)
                    if (skill.name.isNotBlank() && skill.name != skill.id) Text(skill.id, color = CodegTheme.colors.textSecondary)
                    skill.description?.takeIf { it.isNotBlank() }?.let { Text(it, color = CodegTheme.colors.textSecondary) }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextButton(enabled = !ui.busy && !ui.loading, onClick = { editor = EditorTarget(EditorMode.EDIT, skill.id) }) { Text(stringResource(R.string.custom_skills_edit)) }
                        TextButton(enabled = !ui.busy && !ui.loading, onClick = { editor = EditorTarget(EditorMode.DUPLICATE, skill.id) }) { Text(stringResource(R.string.custom_skills_duplicate)) }
                        TextButton(enabled = !ui.busy && !ui.loading, onClick = { deleting = skill }) { Text(stringResource(R.string.common_delete), color = CodegTheme.colors.danger) }
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
                            Switch(modifier = Modifier.semantics { contentDescription = switchLabel }, checked = enabled, enabled = !ui.busy && !ui.loading && (enabled || canEnableCustomSkill(status?.state)),
                                onCheckedChange = { viewModel.toggle(skill.id, agent, it) })
                        }
                    }
                }
            }
        }
    }
    editor?.let { target -> CustomSkillsEditor(target, ui, viewModel) { editor = null } }
    if (importing) CustomSkillsImport(ui, viewModel) { importing = false }
    deleting?.let { skill ->
        AlertDialog(
            onDismissRequest = { deleting = null },
            containerColor = CodegTheme.colors.bgElevated,
            title = { Text(stringResource(R.string.common_delete)) },
            text = { Text(stringResource(R.string.custom_skills_delete_confirm, skill.id)) },
            confirmButton = { TextButton(onClick = { viewModel.delete(skill.id); deleting = null }) { Text(stringResource(R.string.common_delete), color = CodegTheme.colors.danger) } },
            dismissButton = { TextButton(onClick = { deleting = null }) { Text(stringResource(R.string.common_cancel)) } },
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
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (target.mode == EditorMode.DUPLICATE) Text(stringResource(R.string.custom_skills_source, target.id), color = CodegTheme.colors.textSecondary)
            if (target.mode == EditorMode.EDIT) Text(target.id, color = CodegTheme.colors.textSecondary)
            else CodegTextField(id, { id = it }, label = stringResource(R.string.custom_skills_id), mono = true)
            if (loading) CircularProgressIndicator(color = CodegTheme.colors.accent)
            if (loaded && target.mode != EditorMode.DUPLICATE) {
                CodegTextField(content, { content = it }, label = stringResource(R.string.custom_skills_content), mono = true, singleLine = false)
            }
            CustomSkillsError(error ?: ui.error)
            if (!loaded && !loading) TextButton(onClick = { retry++ }) { Text(stringResource(R.string.common_retry)) }
            TextButton(enabled = loaded && !loading && !ui.busy && !ui.loading && id.trim().isNotEmpty() &&
                (target.mode == EditorMode.DUPLICATE || content.isNotBlank()), onClick = {
                val savedId = id.trim()
                val savedContent = content
                scope.launch {
                    val ok = if (target.mode == EditorMode.DUPLICATE) viewModel.duplicate(target.id, savedId)
                    else viewModel.save(savedId, savedContent, target.mode == EditorMode.CREATE)
                    if (ok) onDismiss()
                }
            }) { Text(stringResource(R.string.common_save)) }
            if (ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth(), color = CodegTheme.colors.accent)
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
        LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(ui.agents) { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = item == agent, enabled = !ui.busy, onClick = { agent = item })
                    Text(item.displayName, color = CodegTheme.colors.textPrimary)
                }
            }
            if (loading) item { CircularProgressIndicator(color = CodegTheme.colors.accent) }
            if (error != null && !loading) item { TextButton(onClick = { retry++ }) { Text(stringResource(R.string.common_retry)) } }
            if (!loading && error == null && candidates.isEmpty()) item { Text(stringResource(R.string.custom_skills_import_empty), color = CodegTheme.colors.textSecondary) }
            items(candidates, key = { it.id }) { skill ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = skill.id in selected, enabled = !ui.busy && !loading, onCheckedChange = { checked -> selected = if (checked) selected + skill.id else selected - skill.id })
                    Column {
                        Text(skill.name.ifBlank { skill.id }, color = CodegTheme.colors.textPrimary)
                        Text(skill.id, color = CodegTheme.colors.textSecondary)
                    }
                }
            }
        }
        TextButton(enabled = !ui.busy && !loading && selected.isNotEmpty(), onClick = {
            val current = agent ?: return@TextButton
            val ids = selected.toList()
            scope.launch { if (viewModel.importFromAgent(current, ids)) onDismiss() }
        }, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.custom_skills_import_selected, selected.size)) }
        if (ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth(), color = CodegTheme.colors.accent)
    }
}

@Composable
private fun CustomSkillsError(error: String?) {
    error?.let { Text(it, color = CodegTheme.colors.danger, modifier = Modifier.padding(12.dp)) }
}

@Composable
private fun CustomSkillsFrame(title: String, onDismiss: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)) {
        Column(Modifier.fillMaxSize().background(CodegTheme.colors.bg).windowInsetsPadding(WindowInsets.safeDrawing).imePadding()) {
            CodegTopBar(title = title, navigationIcon = {
                CodegIconButton(onClick = onDismiss) { Icon(Icons.Rounded.Close, contentDescription = stringResource(R.string.common_cancel)) }
            }, showDivider = true)
            content()
        }
    }
}

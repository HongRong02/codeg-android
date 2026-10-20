package app.codeg.android.feature.settings

import app.codeg.android.core.designsystem.component.CodegSectionHeader
import app.codeg.android.core.designsystem.component.CodegBadge
import app.codeg.android.core.designsystem.component.CodegBadgeVariant
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegConfirmDialog
import app.codeg.android.core.designsystem.component.CodegFilterChip
import app.codeg.android.core.designsystem.component.CodegFullScreenSheet
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.EmptyState
import app.codeg.android.core.designsystem.component.InlineError
import app.codeg.android.core.designsystem.component.LoadingView
import app.codeg.android.core.designsystem.component.webSurface
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.markdown.MarkdownContent
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.withOpacity
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.AgentSkillItem
import app.codeg.android.core.model.AgentType
import kotlinx.coroutines.launch

/** Editor target: a new skill, or an existing one to edit/view. */
private sealed interface SkillEditTarget {
    data object Add : SkillEditTarget
    data class Edit(val skill: AgentSkillItem) : SkillEditTarget
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SkillsContent(viewModel: SkillsViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors
    var editor by remember { mutableStateOf<SkillEditTarget?>(null) }
    var pendingDelete by remember { mutableStateOf<Pair<AgentSkillItem, AgentType>?>(null) }

    Column(Modifier.fillMaxSize()) {
        app.codeg.android.feature.settings.customskills.CustomSkillsEntry(onClosed = { viewModel.reloadCurrent() })
        Box(Modifier.weight(1f)) {
            when {
                ui.phase == SkillsPhase.LOADING && ui.agents.isEmpty() ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { LoadingView(stringResource(R.string.common_loading)) }
                ui.phase == SkillsPhase.FAILED && ui.agents.isEmpty() ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    InlineError(Icons.Rounded.Description, stringResource(R.string.skills_couldnt_load), ui.error ?: stringResource(R.string.skills_unknown_error), onRetry = { viewModel.load() })
                }
                ui.agents.isEmpty() ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    EmptyState(Icons.Rounded.Description, stringResource(R.string.skills_no_agents), stringResource(R.string.skills_enable_agent_hint))
                }
                else -> Column(Modifier.fillMaxSize()) {
                    AgentFilterBar(ui.agents, ui.selectedAgent) { viewModel.select(it) }
                    SkillsArea(
                        ui = ui,
                        onAdd = { editor = SkillEditTarget.Add },
                        onOpen = { editor = SkillEditTarget.Edit(it) },
                        onDelete = { skill -> ui.resultAgent?.let { pendingDelete = skill to it } },
                        onRetry = { viewModel.reloadCurrent() },
                        onDismissError = { viewModel.dismissRefreshError() },
                    )
                }
            }

            // Floating "add" button — only when the agent supports skills.
            if (ui.selectedAgent != null && ui.result?.supported == true) {
                CodegButton(onClick = { editor = SkillEditTarget.Add }, modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp), variant = CodegButtonVariant.Default) { Icon(Icons.Rounded.Add, contentDescription = stringResource(R.string.skills_new_skill)) }
            }
        }
    }

    editor?.let { target ->
        val agent = ui.resultAgent ?: ui.selectedAgent
        if (agent != null) {
            SkillEditorDialog(
                target = target,
                agent = agent,
                loadContent = { viewModel.content(it, agent) },
                onSave = { id, scope, content, layout -> viewModel.save(id, scope, content, layout, agent) },
                onDismiss = { editor = null },
            )
        }
    }

    pendingDelete?.let { (skill, agent) ->
        CodegConfirmDialog(
            title = stringResource(R.string.skills_delete_title),
            message = stringResource(R.string.skills_delete_confirm, skill.id),
            target = skill.id,
            confirmLabel = stringResource(R.string.common_delete),
            destructive = true,
            onConfirm = { viewModel.delete(skill, agent); pendingDelete = null },
            onDismiss = { pendingDelete = null },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AgentFilterBar(agents: List<AgentType>, selected: AgentType?, onSelect: (AgentType) -> Unit) {
    val colors = CodegTheme.colors
    Row(
        Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (agent in agents) {
            val on = agent == selected
            CodegFilterChip(label = agent.shortName, selected = on, onClick = { onSelect(agent) })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SkillsArea(
    ui: SkillsUiState,
    onAdd: () -> Unit,
    onOpen: (AgentSkillItem) -> Unit,
    onDelete: (AgentSkillItem) -> Unit,
    onRetry: () -> Unit,
    onDismissError: () -> Unit,
) {
    val colors = CodegTheme.colors
    val result = ui.result
    when {
        ui.skillsLoading && result == null ->
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { LoadingView(stringResource(R.string.skills_loading_skills)) }
        result == null && ui.refreshError != null ->
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            InlineError(Icons.Rounded.Description, stringResource(R.string.skills_couldnt_load), ui.refreshError, onRetry = onRetry)
        }
        result != null && !result.supported ->
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EmptyState(Icons.Rounded.Description, stringResource(R.string.skills_not_supported), result.message ?: stringResource(R.string.skills_not_supported_message))
        }
        ui.grouped.isEmpty() ->
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EmptyState(Icons.Rounded.Description, stringResource(R.string.skills_no_skills), stringResource(R.string.skills_create_hint), actionLabel = stringResource(R.string.skills_new_skill), onAction = onAdd)
        }
        else -> LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (ui.refreshError != null) {
                item("err") { RefreshErrorBanner(ui.refreshError, onRetry, onDismissError) }
            }
            for ((scope, rows) in ui.grouped) {
                item("h-$scope") {
                    CodegSectionHeader(title = scope.uppercase(), count = rows.size)
                }
                items(rows, key = { "$scope-${it.id}" }) { skill ->
                    SkillRow(skill, onClick = { onOpen(skill) }, onDelete = { onDelete(skill) })
                }
            }
        }
    }
}

@Composable
private fun SkillRow(skill: AgentSkillItem, onClick: () -> Unit, onDelete: () -> Unit) {
    val colors = CodegTheme.colors
    CodegListRow(title = skill.name.ifEmpty { skill.id },
        subtitle = listOfNotNull(skill.description?.takeIf { it.isNotEmpty() }, skill.path).joinToString(" · "),
        onClick = onClick, onLongClick = { if (!skill.readOnly) onDelete() },
        leading = { Icon(if (skill.layout == "skill_directory") Icons.Rounded.Folder else Icons.Rounded.Description,
            null, tint = colors.accent, modifier = Modifier.size(18.dp)) },
        trailing = { if (skill.readOnly) CodegBadge(text = stringResource(R.string.skills_read_only), variant = CodegBadgeVariant.Outline) },
        onMoreClick = if (skill.readOnly) null else onDelete)
}

@Composable
private fun RefreshErrorBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit) {
    val colors = CodegTheme.colors
    Row(
        Modifier.fillMaxWidth().clip(CodegShapes.extraSmall).background(colors.danger.withOpacity(0.12f)).padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(message, fontSize = 12.sp, color = colors.danger, modifier = Modifier.weight(1f), maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(stringResource(R.string.common_retry), fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = colors.accent, modifier = Modifier.clickable { onRetry() })
        Icon(Icons.Rounded.Close, stringResource(R.string.common_dismiss), tint = colors.web.mutedForeground, modifier = Modifier.size(16.dp).clickable { onDismiss() })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SkillEditorDialog(
    target: SkillEditTarget,
    agent: AgentType,
    loadContent: suspend (AgentSkillItem) -> String,
    onSave: suspend (id: String, scope: String, content: String, layout: String?) -> Unit,
    onDismiss: () -> Unit,
) {
    val colors = CodegTheme.colors
    val scope = rememberCoroutineScope()
    val existing = (target as? SkillEditTarget.Edit)?.skill
    val isReadOnly = existing?.readOnly == true
    val skillScope = existing?.scope ?: "global"
    val layout = existing?.layout ?: "skill_directory"

    var skillId by remember { mutableStateOf(existing?.id ?: "") }
    var content by remember { mutableStateOf("") }
    var loadingContent by remember { mutableStateOf(existing != null) }
    var saving by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(existing?.id) {
        if (existing != null) {
            runCatching { loadContent(existing) }
                .onSuccess { content = it }
                .onFailure { error = it.message }
            loadingContent = false
        }
    }

    val canSave = !isReadOnly && skillId.trim().isNotEmpty() && content.trim().isNotEmpty() && !saving && !loadingContent

    CodegFullScreenSheet(title = if (existing == null) stringResource(R.string.skills_new_skill) else if (isReadOnly) stringResource(R.string.skills_view_skill) else stringResource(R.string.skills_edit_skill), onDismiss = onDismiss, actions = {
        if (!isReadOnly) {
            CodegButton(enabled = canSave, onClick = {
                saving = true
                scope.launch {
                    runCatching { onSave(skillId.trim(), skillScope, content, layout) }
                        .onSuccess { onDismiss() }
                        .onFailure { error = it.message; saving = false }
                }
            }, variant = CodegButtonVariant.Default) {
                Text(stringResource(R.string.common_save), fontWeight = FontWeight.SemiBold)
            }
        }
    }) {

        Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            val scopeWord = if (skillScope == "global") stringResource(R.string.skills_scope_global) else stringResource(R.string.skills_scope_project)
            Text(
                if (existing != null) stringResource(R.string.skills_scope_for_agent, scopeWord, agent.displayName) else stringResource(R.string.skills_new_global_for_agent, agent.displayName),
                fontSize = 12.sp, color = colors.web.mutedForeground, modifier = Modifier.padding(top = 4.dp),
            )
            if (existing == null) {
                CodegTextField(skillId, { skillId = it }, label = stringResource(R.string.skills_skill_id), placeholder = stringResource(R.string.skills_skill_id_placeholder), mono = true)
            } else {
                Text("ID: ${skillId}", fontSize = 13.sp, fontFamily = FontFamily.Monospace, color = colors.textSecondary)
            }
            when {
                loadingContent -> Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CircularProgressIndicator(color = colors.accent, modifier = Modifier.size(18.dp))
                    Text(stringResource(R.string.common_loading), fontSize = 13.sp, color = colors.textSecondary)
                }
                isReadOnly -> {
                    Text(stringResource(R.string.skills_content), fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = colors.textSecondary)
                    if (content.isBlank()) Text(stringResource(R.string.skills_no_content), fontSize = 13.sp, color = colors.web.mutedForeground)
                    else MarkdownContent(content)
                }
                else -> CodegTextField(content, { content = it }, label = stringResource(R.string.skills_content_markdown), mono = true, singleLine = false)
            }
            error?.let { Text(it, fontSize = 13.sp, color = colors.danger) }
            Spacer(Modifier.height(24.dp))
        }
    }
}

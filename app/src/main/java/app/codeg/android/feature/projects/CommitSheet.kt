package app.codeg.android.feature.projects

import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegSectionHeader
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegCheckbox
import app.codeg.android.core.designsystem.component.CodegSheet
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.EmptyState
import app.codeg.android.core.designsystem.component.InlineError
import app.codeg.android.core.designsystem.component.LoadingView
import app.codeg.android.core.designsystem.component.PrimaryButton
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.GitChange
import app.codeg.android.core.model.GitStatusEntry
import app.codeg.android.core.network.CodegClient
import app.codeg.android.core.network.displayMessage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect
import app.codeg.android.core.designsystem.component.LocalCodegToaster
import app.codeg.android.core.designsystem.icon.*

/**
 * The commit composer, presented from the Changes tab. Lists the working-tree
 * changes with per-file selection (tracked auto-selected, untracked unselected —
 * web/iOS parity), takes a message, and commits the selected files (optionally
 * pushing afterward). The server stages the chosen files, so untracked paths can be
 * committed directly. Port of iOS `CommitSheet`.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommitSheet(
    viewModel: ProjectDetailViewModel,
    client: CodegClient,
    rootPath: String,
    onDismiss: () -> Unit,
    onCommitted: (push: Boolean) -> Unit,
) {
    val context = LocalContext.current
    val toaster = LocalCodegToaster.current
    val failureTitle = stringResource(R.string.t56_operation_failed)
    val colors = CodegTheme.colors
    val scope = rememberCoroutineScope()

    var entries by remember { mutableStateOf<List<GitStatusEntry>?>(null) }
    var selected by remember { mutableStateOf(setOf<String>()) }
    var message by remember { mutableStateOf("") }
    var loadError by remember { mutableStateOf<String?>(null) }
    var committing by remember { mutableStateOf(false) }
    var commitError by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(commitError) {
        commitError?.let { toaster.error(failureTitle, it); commitError = null }
    }

    suspend fun load() {
        loadError = null
        runCatching { client.gitStatus(rootPath, showAllUntracked = true) }
            .onSuccess { result ->
                entries = result
                // Tracked changes start selected; untracked start unselected.
                selected = result.filter { it.change != GitChange.UNTRACKED }.map { it.path }.toSet()
            }
            .onFailure { loadError = it.displayMessage(context) }
    }

    androidx.compose.runtime.LaunchedEffect(rootPath) { load() }

    val all = entries.orEmpty()
    val trimmedMessage = message.trim()
    val canCommit = trimmedMessage.isNotEmpty() && selected.isNotEmpty() && !committing

    fun runCommit(push: Boolean) {
        if (!canCommit) return
        committing = true
        commitError = null
        scope.launch {
            try {
                viewModel.gitCommit(trimmedMessage, selected.toList())
                committing = false
                onCommitted(push)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                committing = false
                commitError = e.displayMessage(context)
            }
        }
    }

    CodegSheet(title = stringResource(R.string.commit_title), onDismiss = { if (!committing) onDismiss() }, footer = {
        if (all.isNotEmpty() && loadError == null) {
            PrimaryButton(
                text = pluralStringResource(R.plurals.commit_button, selected.size, selected.size),
                onClick = { runCommit(push = false) },
                enabled = canCommit,
                loading = committing,
            )
            CodegButton(variant = CodegButtonVariant.Ghost,
                onClick = { runCommit(push = true) },
                enabled = canCommit,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.commit_and_push), color = if (canCommit) colors.accent else colors.textSecondary, fontWeight = FontWeight.Medium)
            }
        }
    }) {
        when {
            entries == null && loadError == null ->
                Box(Modifier.fillMaxWidth().heightIn(min = 160.dp), contentAlignment = Alignment.Center) { LoadingView(stringResource(R.string.common_loading)) }
            loadError != null ->
                Box(Modifier.fillMaxWidth().heightIn(min = 160.dp), contentAlignment = Alignment.Center) {
                    InlineError(Lucide.CircleCheckBig, stringResource(R.string.commit_load_failed), loadError!!, onRetry = { entries = null; scope.launch { load() } })
                }
            all.isEmpty() ->
                Box(Modifier.fillMaxWidth().heightIn(min = 160.dp), contentAlignment = Alignment.Center) {
                    EmptyState(Lucide.CircleCheckBig, stringResource(R.string.commit_nothing_title), stringResource(R.string.commit_nothing_message))
                }
            else -> {
                val tracked = all.filter { it.change != GitChange.UNTRACKED }
                val untracked = all.filter { it.change == GitChange.UNTRACKED }
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Box(Modifier.padding(top = 8.dp)) {
                        CodegTextField(
                            value = message,
                            onValueChange = { message = it },
                            label = stringResource(R.string.commit_message_label),
                            placeholder = stringResource(R.string.commit_message_placeholder),
                            singleLine = false,
                        )
                    }
                    SelectionHeader(
                        selectedCount = selected.size,
                        total = all.size,
                        allSelected = selected.size == all.size,
                        enabled = !committing,
                        onToggleAll = {
                            selected = if (selected.size == all.size) emptySet() else all.map { it.path }.toSet()
                        },
                    )
                    if (tracked.isNotEmpty()) {
                        FileSection(stringResource(R.string.commit_section_changes), tracked, selected, enabled = !committing) { path ->
                            selected = if (path in selected) selected - path else selected + path
                        }
                    }
                    if (untracked.isNotEmpty()) {
                        FileSection(stringResource(R.string.commit_section_untracked), untracked, selected, enabled = !committing) { path ->
                            selected = if (path in selected) selected - path else selected + path
                        }
                    }

                }

            }
        }
    }
}

@Composable
private fun SelectionHeader(
    selectedCount: Int,
    total: Int,
    allSelected: Boolean,
    enabled: Boolean,
    onToggleAll: () -> Unit,
) {
    val colors = CodegTheme.colors
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            stringResource(R.string.commit_selected, selectedCount, total),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = colors.textSecondary,
        )
        Box(Modifier.weight(1f))
        CodegButton(variant = CodegButtonVariant.Ghost, onClick = onToggleAll, enabled = enabled) {
            Text(
                stringResource(if (allSelected) R.string.commit_deselect_all else R.string.commit_select_all),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.accent,
            )
        }
    }
}

@Composable
private fun FileSection(
    title: String,
    entries: List<GitStatusEntry>,
    selected: Set<String>,
    enabled: Boolean,
    onToggle: (String) -> Unit,
) {
    val colors = CodegTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        CodegSectionHeader(title = title, count = entries.size)
        for (entry in entries) {
            val isSelected = entry.path in selected
            CodegListRow(
                title = entry.path.substringAfterLast('/'),
                subtitle = entry.path.substringBeforeLast('/', "").takeIf { it.isNotEmpty() },
                selected = isSelected,
                onClick = { if (enabled) onToggle(entry.path) },
                leading = {
                    CodegCheckbox(checked = isSelected, onCheckedChange = { onToggle(entry.path) }, enabled = enabled)
                },
                trailing = { GitChangeBadge(entry.change) },
            )
        }
    }
}

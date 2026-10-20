package app.codeg.android.feature.worktasks

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.diff.DiffView
import app.codeg.android.core.designsystem.diff.UnifiedDiff
import app.codeg.android.core.designsystem.theme.CodegTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkTaskScreen(onBack: () -> Unit, viewModel: WorkTaskViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val selected = ui.selected
    val back = { if (selected != null) viewModel.select(null) else onBack() }
    BackHandler(enabled = selected != null) { viewModel.select(null) }
    Scaffold(
        containerColor = CodegTheme.colors.bg,
        topBar = {
            CodegTopBar(
                title = selected?.title ?: stringResource(R.string.work_tasks_title),
                navigationIcon = {
                    CodegIconButton(onClick = back) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.common_back))
                    }
                },
                actions = {
                    CodegIconButton(onClick = { if (selected != null) viewModel.select(selected) else viewModel.load() }) {
                        Icon(Icons.Rounded.Refresh, stringResource(R.string.work_tasks_refresh))
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (selected == null) {
                item { Text(stringResource(R.string.work_tasks_attention, ui.attention), color = CodegTheme.colors.textSecondary) }
                when {
                    ui.loading -> item { LoadingView(stringResource(R.string.work_tasks_loading)) }
                    ui.error != null -> item {
                        InlineError(Icons.Rounded.Schedule, stringResource(R.string.work_tasks_error), ui.error!!,
                            onRetry = { viewModel.load() }, retryLabel = stringResource(R.string.work_tasks_retry))
                    }
                    !ui.supported -> item { Text(stringResource(R.string.work_tasks_unavailable)) }
                    ui.tasks.isEmpty() -> item {
                        EmptyState(Icons.Rounded.Schedule, stringResource(R.string.work_tasks_empty), stringResource(R.string.work_tasks_empty_hint))
                    }
                    else -> ui.tasks.groupBy { it.status }.toSortedMap().forEach { (status, tasks) ->
                        item { Text(status, style = MaterialTheme.typography.titleSmall, color = CodegTheme.colors.textSecondary) }
                        items(tasks.sortedByDescending { it.updatedAt }, key = { it.id }) { task ->
                            Column(Modifier.fillMaxWidth().clickable { viewModel.select(task) }.padding(vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(task.title, color = CodegTheme.colors.textPrimary, style = MaterialTheme.typography.titleMedium)
                                Text(stringResource(R.string.work_tasks_agent, task.agentType ?: stringResource(R.string.work_tasks_unknown_agent)), color = CodegTheme.colors.textSecondary)
                                Text(task.status, color = CodegTheme.colors.textSecondary)
                                Text(stringResource(R.string.work_tasks_updated, task.updatedAt), color = CodegTheme.colors.textTertiary)
                            }
                            HorizontalDivider(color = CodegTheme.colors.hairline)
                        }
                    }
                }
            } else {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(selected.status, color = CodegTheme.colors.accent)
                        Text(stringResource(R.string.work_tasks_agent, selected.agentType ?: stringResource(R.string.work_tasks_unknown_agent)))
                        Text(stringResource(R.string.work_tasks_updated, selected.updatedAt))
                        selected.workBranch?.let { Text(it) }
                        selected.resultSummary?.let { Text(it) }
                        selected.failureReason?.let { Text(it, color = CodegTheme.colors.danger) }
                        selected.lastError?.let { Text(it, color = CodegTheme.colors.danger) }
                    }
                }
                if (ui.detailLoading) item { LoadingView(stringResource(R.string.work_tasks_loading)) }
                items(ui.detailErrors) { Text(it, color = CodegTheme.colors.danger) }
                item { Text(stringResource(R.string.work_tasks_events), style = MaterialTheme.typography.titleMedium) }
                if (!ui.detailLoading && ui.events.isEmpty()) item { Text(stringResource(R.string.work_tasks_no_events)) }
                items(ui.events, key = { it.id }) { event ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(event.kind, color = CodegTheme.colors.textPrimary)
                        Text(stringResource(R.string.work_tasks_event_meta, event.actor, event.createdAt), color = CodegTheme.colors.textSecondary)
                        event.payload?.let { Text(it.toString(), style = MaterialTheme.typography.bodySmall) }
                    }
                }
                item { Text(stringResource(R.string.work_tasks_files), style = MaterialTheme.typography.titleMedium) }
                if (!ui.detailLoading && ui.files.isEmpty()) item { Text(stringResource(R.string.work_tasks_no_files)) }
                items(ui.files, key = { it.file }) { file ->
                    Text(stringResource(R.string.work_tasks_file_stats, file.file, file.additions, file.deletions))
                }
                item { Text(stringResource(R.string.work_tasks_diff), style = MaterialTheme.typography.titleMedium) }
                item {
                    val parsed = remember(ui.diff) { UnifiedDiff.parse(ui.diff) }
                    if (!parsed.isNullOrEmpty()) DiffView(parsed)
                    else if (ui.diff.isNotBlank()) Text(ui.diff, style = MaterialTheme.typography.bodySmall)
                    else if (!ui.detailLoading) Text(stringResource(R.string.work_tasks_no_diff))
                }
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

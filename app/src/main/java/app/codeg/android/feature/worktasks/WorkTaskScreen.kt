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
    var column by remember { mutableIntStateOf(0) }
    var showCanceled by remember { mutableStateOf(false) }
    val visible = boardTasks(ui.tasks, showCanceled)
    val selected = ui.selected
    if (ui.editorOpen) WorkTaskEditor(ui, viewModel)
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
                    WorkTaskManagementMenu(ui, viewModel)
                    CodegIconButton(onClick = { if (selected != null) viewModel.select(selected) else viewModel.load() }) {
                        Icon(Icons.Rounded.Refresh, stringResource(R.string.work_tasks_refresh))
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (ui.busy) item { LinearProgressIndicator(Modifier.fillMaxWidth()) }
            if (ui.error != null && selected != null) item { Text(ui.error!!, color = CodegTheme.colors.danger) }
            if (selected == null) {
                if (ui.supported) {
                    item {
                        Row {
                            if ("create" !in ui.hiddenActions) TextButton(enabled = !ui.busy, onClick = { viewModel.edit() }) { Text(stringResource(R.string.wtb_create)) }
                            if ("start_all" !in ui.hiddenActions) TextButton(enabled = !ui.busy && ui.tasks.any { it.status == "todo" }, onClick = viewModel::startAll) { Text(stringResource(R.string.wtb_start_all)) }
                        }
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Checkbox(showCanceled, { showCanceled = it })
                            Text(stringResource(R.string.wtb_show_canceled))
                        }
                        TabRow(selectedTabIndex = column, containerColor = CodegTheme.colors.bg, contentColor = CodegTheme.colors.accent) {
                            listOf(R.string.wtb_todo, R.string.wtb_progress, R.string.wtb_attention, R.string.wtb_done).forEachIndexed { index, label ->
                                val count = visible.count { it.boardColumn() == index }
                                Tab(selected = column == index, onClick = { column = index }, text = {
                                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                                        Text(stringResource(label), style = MaterialTheme.typography.labelSmall)
                                        Row {
                                            Text(count.toString())
                                            if (index == 2 && count > 0) Text(" •", color = CodegTheme.colors.danger)
                                        }
                                    }
                                })
                            }
                        }
                    }
                }
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
                    else -> {
                        val tasks = visible.filter { it.boardColumn() == column }
                        if (tasks.isEmpty()) item { Text(stringResource(R.string.work_tasks_empty)) }
                        items(tasks, key = { it.id }) { task ->
                            Column(Modifier.fillMaxWidth().clickable(enabled = !ui.busy) { viewModel.select(task) }.padding(vertical = 12.dp)) {
                                Text(task.title, color = CodegTheme.colors.textPrimary, style = MaterialTheme.typography.titleMedium)
                                Text(stringResource(R.string.work_tasks_agent, task.agentType ?: stringResource(R.string.work_tasks_unknown_agent)), color = CodegTheme.colors.textSecondary)
                                Text(workTaskStatusLabel(task.status), color = CodegTheme.colors.textSecondary)
                                WorkTaskActions(task, ui, viewModel, compact = true)
                                if ("reorder" !in ui.hiddenActions && task.status in setOf("todo", "queued")) Row {
                                    val project = tasks.filter { it.folderId == task.folderId }
                                    val index = project.indexOf(task)
                                    TextButton(enabled = !ui.busy && index > 0, onClick = { viewModel.move(task, -1) }) { Text(stringResource(R.string.wtb_up)) }
                                    TextButton(enabled = !ui.busy && index < project.lastIndex, onClick = { viewModel.move(task, 1) }) { Text(stringResource(R.string.wtb_down)) }
                                }
                            }
                            HorizontalDivider(color = CodegTheme.colors.hairline)
                        }
                    }
                }
            } else {
                if (ui.supported) item { WorkTaskActions(selected, ui, viewModel) }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(workTaskStatusLabel(selected.status), color = CodegTheme.colors.accent)
                        Text(stringResource(R.string.work_tasks_agent, selected.agentType ?: stringResource(R.string.work_tasks_unknown_agent)))
                        Text(stringResource(R.string.work_tasks_updated, formatWorkTaskTime(selected.updatedAt)))
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
                        Text(stringResource(R.string.work_tasks_event_meta, event.actor, formatWorkTaskTime(event.createdAt)), color = CodegTheme.colors.textSecondary)
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

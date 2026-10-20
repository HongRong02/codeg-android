package app.codeg.android.feature.worktasks

import app.codeg.android.core.designsystem.icon.*
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
                        CodegIcon(Lucide.ArrowLeft, stringResource(R.string.common_back))
                    }
                },
                actions = {
                    WorkTaskManagementMenu(ui, viewModel)
                    CodegIconButton(onClick = { if (selected != null) viewModel.select(selected) else viewModel.load() }) {
                        CodegIcon(Lucide.RefreshCw, stringResource(R.string.work_tasks_refresh))
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

            if (ui.busy) item { CodegProgress(Modifier.fillMaxWidth()) }
            if (ui.error != null && selected != null) item { Text(ui.error!!, color = CodegTheme.colors.danger) }
            if (selected == null) {
                if (ui.supported) {
                    item {
                        Row {
                            if ("create" !in ui.hiddenActions) CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = { viewModel.edit() }) { Text(stringResource(R.string.wtb_create)) }
                            if ("start_all" !in ui.hiddenActions) CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy && ui.tasks.any { it.status == "todo" }, onClick = viewModel::startAll) { Text(stringResource(R.string.wtb_start_all)) }
                        }
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            CodegCheckbox(showCanceled, { showCanceled = it }, label = stringResource(R.string.wtb_show_canceled))
                        }
                        CodegTabs(selected = column,
                            tabs = listOf(R.string.wtb_todo, R.string.wtb_progress, R.string.wtb_attention, R.string.wtb_done).mapIndexed { index, label ->
                                stringResource(label) + " (" + visible.count { it.boardColumn() == index } + ")"
                            }, onSelect = { column = it }, modifier = Modifier.fillMaxWidth())
                    }
                }
                item { Text(stringResource(R.string.work_tasks_attention, ui.attention), color = CodegTheme.colors.textSecondary) }
                when {
                    ui.loading -> item { LoadingView(stringResource(R.string.work_tasks_loading)) }
                    ui.error != null -> item {
                        InlineError(Lucide.Clock, stringResource(R.string.work_tasks_error), ui.error!!,
                            onRetry = { viewModel.load() }, retryLabel = stringResource(R.string.work_tasks_retry))
                    }
                    !ui.supported -> item { Text(stringResource(R.string.work_tasks_unavailable)) }
                    ui.tasks.isEmpty() -> item {
                        EmptyState(Lucide.Clock, stringResource(R.string.work_tasks_empty), stringResource(R.string.work_tasks_empty_hint))
                    }
                    else -> {
                        val tasks = visible.filter { it.boardColumn() == column }
                        if (tasks.isEmpty()) item { Text(stringResource(R.string.work_tasks_empty)) }
                        items(tasks, key = { it.id }) { task ->
                            Column(Modifier.fillMaxWidth()) {
                                CodegListRow(title = task.title,
                                    subtitle = stringResource(R.string.work_tasks_agent, task.agentType ?: stringResource(R.string.work_tasks_unknown_agent)),
                                    onClick = { if (!ui.busy) viewModel.select(task) },
                                    trailing = { CodegBadge(workTaskStatusLabel(task.status), variant = CodegBadgeVariant.Secondary) })
                                WorkTaskActions(task, ui, viewModel, compact = true)
                                if ("reorder" !in ui.hiddenActions && task.status in setOf("todo", "queued")) Row {
                                    val project = tasks.filter { it.folderId == task.folderId }
                                    val index = project.indexOf(task)
                                    CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy && index > 0, onClick = { viewModel.move(task, -1) }) { Text(stringResource(R.string.wtb_up)) }
                                    CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy && index < project.lastIndex, onClick = { viewModel.move(task, 1) }) { Text(stringResource(R.string.wtb_down)) }
                                }
                            }
                            CodegSeparator()
                        }
                    }
                }
            } else {
                if (ui.supported) item { WorkTaskActions(selected, ui, viewModel) }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        CodegBadge(workTaskStatusLabel(selected.status), variant = CodegBadgeVariant.Secondary)
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
                item { CodegSectionHeader(title = stringResource(R.string.work_tasks_events)) }
                if (!ui.detailLoading && ui.events.isEmpty()) item { Text(stringResource(R.string.work_tasks_no_events)) }
                items(ui.events, key = { it.id }) { event ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(event.kind, color = CodegTheme.colors.textPrimary)
                        Text(stringResource(R.string.work_tasks_event_meta, event.actor, formatWorkTaskTime(event.createdAt)), color = CodegTheme.colors.textSecondary)
                        event.payload?.let { Text(it.toString(), style = MaterialTheme.typography.bodySmall) }
                    }
                }
                item { CodegSectionHeader(title = stringResource(R.string.work_tasks_files)) }
                if (!ui.detailLoading && ui.files.isEmpty()) item { Text(stringResource(R.string.work_tasks_no_files)) }
                items(ui.files, key = { it.file }) { file ->
                    Text(stringResource(R.string.work_tasks_file_stats, file.file, file.additions, file.deletions))
                }
                item { CodegSectionHeader(title = stringResource(R.string.work_tasks_diff)) }
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

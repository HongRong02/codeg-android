package app.codeg.android.feature.automations

import app.codeg.android.core.designsystem.icon.*
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.common.RelativeTime
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.withOpacity
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.Automation
import app.codeg.android.core.model.AutomationRun
import app.codeg.android.core.model.AutomationRunStatus
import app.codeg.android.core.model.IsolationMode
import app.codeg.android.core.model.TriggerKind
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutomationScreen(
    onBack: () -> Unit,
    viewModel: AutomationViewModel = hiltViewModel(),
) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors

    var deleteTarget by remember { mutableStateOf<Automation?>(null) }
    var cancelTarget by remember { mutableStateOf<AutomationRun?>(null) }
    val selected = ui.selectedAutomation
    if (ui.editorOpen) AutomationEditorSheet(ui, viewModel)
    deleteTarget?.let { target ->
        CodegConfirmDialog(onDismiss = { deleteTarget = null },
            title = stringResource(R.string.aw_delete), message = stringResource(R.string.aw_delete_confirm, target.name),
            target = target.name, confirmLabel = stringResource(R.string.aw_confirm),
            destructive = true, busy = ui.busy,
            onConfirm = { viewModel.delete(target); deleteTarget = null })
    }
    cancelTarget?.let { target ->
        CodegConfirmDialog(onDismiss = { cancelTarget = null },
            title = stringResource(R.string.aw_cancel_run), message = stringResource(R.string.aw_cancel_confirm, target.id, selected?.name.orEmpty()),
            target = target.id.toString(), confirmLabel = stringResource(R.string.aw_confirm),
            destructive = true, busy = ui.busy,
            onConfirm = { viewModel.cancelRun(target.id); cancelTarget = null })
    }
    BackHandler(enabled = selected != null) {
        viewModel.selectAutomation(null)
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CodegTopBar(
                title = selected?.name ?: stringResource(R.string.automations_title),
                showDivider = true,
                navigationIcon = {
                    CodegIconButton(onClick = {
                        if (selected != null) {
                            viewModel.selectAutomation(null)
                        } else {
                            onBack()
                        }
                    }) {
                        CodegIcon(
                            Lucide.ArrowLeft,
                            contentDescription = stringResource(R.string.common_back),
                        )
                    }
                },
                actions = {
                    if (selected == null && ui.writesSupported) {
                        CodegButton(variant = CodegButtonVariant.Default, size = CodegButtonSize.Sm, enabled = !ui.busy && !ui.loading, onClick = { viewModel.openEditor(null) }) { Text(stringResource(R.string.aw_create)) }
                    }
                    CodegIconButton(onClick = { viewModel.load() }) {
                        CodegIcon(
                            Lucide.RefreshCw,
                            contentDescription = stringResource(R.string.automations_refresh),
                        )
                    }
                },
            )
        },
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                ui.loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CodegSpinner(size = 24.dp)
                    }
                }
                selected != null -> {
                    AutomationDetailView(
                        automation = selected,
                        runs = ui.runs,
                        detailLoading = ui.detailLoading,
                        onToggleEnabled = { viewModel.toggleEnabled(selected) },
                        onRefreshRuns = { viewModel.loadRuns(selected.id) },
                        actions = {
                            ui.error?.let { Text(it, color = colors.danger) }
                            if (ui.writesSupported) {
                                Row {
                                    CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = { viewModel.openEditor(selected) }) { Text(stringResource(R.string.aw_edit)) }
                                    CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = { viewModel.runNow(selected.id) }) { Text(stringResource(R.string.aw_run)) }
                                    CodegButton(variant = CodegButtonVariant.Destructive, size = CodegButtonSize.Sm, enabled = !ui.busy, onClick = { deleteTarget = selected }) { Text(stringResource(R.string.aw_delete)) }
                                }
                            }
                        },
                        onCancelRun = { cancelTarget = it },
                        canCancel = ui.writesSupported && !ui.busy,
                    )
                }
                else -> {
                    AutomationListView(
                        ui = ui,
                        onFilterChanged = { viewModel.setFilter(it) },
                        onSelectAutomation = { viewModel.selectAutomation(it.id) },
                        onToggleEnabled = { viewModel.toggleEnabled(it) },
                    )
                }
            }
        }
    }
}

@Composable
private fun AutomationListView(
    ui: AutomationUiState,
    onFilterChanged: (AutomationFilter) -> Unit,
    onSelectAutomation: (Automation) -> Unit,
    onToggleEnabled: (Automation) -> Unit,
) {
    val colors = CodegTheme.colors

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val filterOptions = listOf(
            stringResource(R.string.automations_filter_all),
            stringResource(R.string.automations_filter_enabled),
            stringResource(R.string.automations_filter_disabled),
        )
        CodegTabs(
            tabs = filterOptions,
            selected = ui.filter.ordinal,
            onSelect = { onFilterChanged(AutomationFilter.entries[it]) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
        )

        val items = ui.filteredAutomations
        if (items.isEmpty()) {
            EmptyState(
                icon = Lucide.Zap,
                title = stringResource(R.string.automations_empty),
                message = stringResource(R.string.automations_empty_hint),
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(items, key = { it.id }) { automation ->
                    AutomationListItem(
                        automation = automation,
                        onClick = { onSelectAutomation(automation) },
                        onToggle = { onToggleEnabled(automation) },
                    )
                }
            }
        }
    }
}

@Composable
private fun AutomationListItem(
    automation: Automation,
    onClick: () -> Unit,
    onToggle: () -> Unit,
) {
    val colors = CodegTheme.colors

    CodegListRow(title = automation.name, subtitle = formatAutomationSubtitle(automation), onClick = onClick,
        leading = {
            Box(contentAlignment = Alignment.BottomEnd) {
            AgentIcon(
                agent = automation.agentType,
                size = 28.dp,
            )
            val dotColor = when {
                !automation.enabled -> colors.textSecondary.withOpacity(0.4f)
                automation.lastRunStatus == "running" -> colors.textSecondary
                automation.lastRunStatus == "succeeded" -> colors.accent
                automation.lastRunStatus == "failed" -> colors.danger
                else -> colors.accent
            }
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
        }
        },
        trailing = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CodegSwitch(checked = automation.enabled, onCheckedChange = { onToggle() })
                CodegIcon(Lucide.ChevronRight, contentDescription = null,
                    tint = colors.textSecondary, modifier = Modifier.size(16.dp))
            }
        })
}

@Composable
private fun AutomationDetailView(
    automation: Automation,
    runs: List<AutomationRun>,
    detailLoading: Boolean,
    onToggleEnabled: () -> Unit,
    onRefreshRuns: () -> Unit,
    actions: @Composable () -> Unit,
    onCancelRun: (AutomationRun) -> Unit,
    canCancel: Boolean,
) {
    val colors = CodegTheme.colors

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        actions()
        CodegCard {
            CodegCardHeader(title = automation.name,
                description = stringResource(if (automation.enabled) R.string.automations_enabled else R.string.automations_disabled),
                action = { CodegSwitch(checked = automation.enabled, onCheckedChange = { onToggleEnabled() }) })
        }

        // Section: Schedule / Configuration
        CodegCard {
            CodegCardContent {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    CodegSectionHeader(title = stringResource(R.string.automations_section_schedule))

                    DetailRow(
                        icon = Lucide.Clock,
                        label = stringResource(R.string.automations_trigger),
                        value = if (automation.triggerKind == TriggerKind.SCHEDULE && automation.cron != null) {
                            stringResource(R.string.automations_trigger_schedule) + " (" + automation.cron + ")"
                        } else {
                            stringResource(R.string.automations_trigger_manual)
                        },
                    )

                    DetailRow(
                        icon = Lucide.Play,
                        label = stringResource(R.string.automations_agent),
                        value = automation.agentType.displayName,
                    )

                    if (automation.isolation == IsolationMode.WORKTREE_PER_RUN) {
                        DetailRow(
                            icon = Lucide.Layers,
                            label = stringResource(R.string.automations_isolation),
                            value = stringResource(R.string.automations_isolation_worktree),
                        )
                    } else {
                        DetailRow(
                            icon = Lucide.Folder,
                            label = stringResource(R.string.automations_isolation),
                            value = stringResource(R.string.automations_isolation_shared) + (if (automation.branch != null) " (" + automation.branch + ")" else ""),
                        )
                    }

                    if (automation.nextRunAt != null) {
                        DetailRow(
                            icon = Lucide.Calendar,
                            label = stringResource(R.string.automations_next_run),
                            value = formatDateTime(automation.nextRunAt),
                        )
                    }

                    if (automation.lastRunAt != null) {
                        DetailRow(
                            icon = Lucide.History,
                            label = stringResource(R.string.automations_last_run),
                            value = RelativeTime.compact(automation.lastRunAt),
                        )
                    }
                }
            }
        }

        // Section: Prompt
        val prompt = automation.promptText
        if (prompt.isNotEmpty()) {
            CodegCard {
                CodegCardContent {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        CodegSectionHeader(title = stringResource(R.string.automations_section_prompt))
                        Text(
                            prompt,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            color = colors.textPrimary,
                        )
                    }
                }
            }
        }

        // Section: Run History
        CodegCard {
            CodegCardContent {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    CodegSectionHeader(title = stringResource(R.string.automations_section_runs), action = {
                        CodegIconButton(onClick = onRefreshRuns) {
                            CodegIcon(Lucide.RefreshCw, contentDescription = stringResource(R.string.automations_refresh),
                                modifier = Modifier.size(16.dp), tint = colors.textSecondary)
                        }
                    })

                    if (detailLoading && runs.isEmpty()) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CodegSpinner(size = 24.dp)
                        }
                    } else if (runs.isEmpty()) {
                        Text(
                            stringResource(R.string.automations_no_runs),
                            fontSize = 13.sp,
                            color = colors.textSecondary,
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            runs.forEachIndexed { index, run ->
                                RunHistoryItem(run = run)
                                if (canCancel && run.status == AutomationRunStatus.RUNNING) {
                                    CodegButton(variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm, onClick = { onCancelRun(run) }) { Text(stringResource(R.string.aw_cancel_run)) }
                                }
                                if (index < runs.lastIndex) {
                                    CodegSeparator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RunHistoryItem(run: AutomationRun) {
    val colors = CodegTheme.colors

    val statusLabel = when (run.status) {
        AutomationRunStatus.RUNNING -> stringResource(R.string.automations_status_running)
        AutomationRunStatus.SUCCEEDED -> stringResource(R.string.automations_status_succeeded)
        AutomationRunStatus.FAILED -> stringResource(R.string.automations_status_failed)
        AutomationRunStatus.CANCELLED -> stringResource(R.string.automations_status_cancelled)
        AutomationRunStatus.SKIPPED -> stringResource(R.string.automations_status_skipped)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                CodegBadge(text = statusLabel, variant = when (run.status) {
                    AutomationRunStatus.FAILED -> CodegBadgeVariant.Destructive
                    AutomationRunStatus.SUCCEEDED -> CodegBadgeVariant.Default
                    else -> CodegBadgeVariant.Secondary
                })
            }
            Text(
                run.startedAt?.let { RelativeTime.compact(it) } ?: "—",
                fontSize = 12.sp,
                color = colors.textSecondary,
            )
        }

        if (!run.error.isNullOrBlank()) {
            Text(
                run.error,
                fontSize = 12.sp,
                color = colors.danger,
                maxLines = 2,
            )
        } else if (!run.summary.isNullOrBlank()) {
            Text(
                run.summary,
                fontSize = 12.sp,
                color = colors.textSecondary,
                maxLines = 2,
            )
        }
    }
}

@Composable
private fun DetailRow(
    icon: ImageVector,
    label: String,
    value: String,
) {
    val colors = CodegTheme.colors
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CodegIcon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = colors.textSecondary,
        )
        Text(
            label,
            fontSize = 13.sp,
            color = colors.textSecondary,
            modifier = Modifier.width(90.dp),
        )
        Text(
            value,
            fontSize = 13.sp,
            color = colors.textPrimary,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun formatAutomationSubtitle(automation: Automation): String {
    val parts = mutableListOf<String>()
    if (automation.triggerKind == TriggerKind.SCHEDULE && !automation.cron.isNullOrBlank()) {
        parts.add(automation.cron)
    } else {
        parts.add(stringResource(R.string.automations_trigger_manual))
    }
    if (automation.nextRunAt != null && automation.enabled) {
        parts.add(stringResource(R.string.automations_next_run) + ": " + RelativeTime.compact(automation.nextRunAt))
    } else if (automation.lastRunAt != null) {
        parts.add(stringResource(R.string.automations_last_run) + ": " + RelativeTime.compact(automation.lastRunAt))
    }
    return parts.joinToString(" · ")
}

private val dateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault())

private fun formatDateTime(instant: Instant): String =
    instant.atZone(ZoneId.systemDefault()).format(dateTimeFormatter)

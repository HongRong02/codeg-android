package app.codeg.android.feature.automations

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Layers
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.common.RelativeTime
import app.codeg.android.core.designsystem.component.AgentIcon
import app.codeg.android.core.designsystem.component.CodegIconButton
import app.codeg.android.core.designsystem.component.CodegSegmented
import app.codeg.android.core.designsystem.component.CodegTopBar
import app.codeg.android.core.designsystem.component.webSurface
import app.codeg.android.core.designsystem.theme.CodegShapes
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

    val selected = ui.selectedAutomation
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
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.common_back),
                        )
                    }
                },
                actions = {
                    CodegIconButton(onClick = { viewModel.load() }) {
                        Icon(
                            Icons.Rounded.Refresh,
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
                        CircularProgressIndicator(color = colors.accent)
                    }
                }
                selected != null -> {
                    AutomationDetailView(
                        automation = selected,
                        runs = ui.runs,
                        detailLoading = ui.detailLoading,
                        onToggleEnabled = { viewModel.toggleEnabled(selected) },
                        onRefreshRuns = { viewModel.loadRuns(selected.id) },
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
        CodegSegmented(
            options = filterOptions,
            selectedIndex = ui.filter.ordinal,
            onSelect = { onFilterChanged(AutomationFilter.entries[it]) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
        )

        val items = ui.filteredAutomations
        if (items.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        stringResource(R.string.automations_empty),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.textPrimary,
                    )
                    Text(
                        stringResource(R.string.automations_empty_hint),
                        fontSize = 13.sp,
                        color = colors.textSecondary,
                    )
                }
            }
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .webSurface(CodegShapes.small)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Agent Icon with Status Dot overlay
        Box(contentAlignment = Alignment.BottomEnd) {
            AgentIcon(
                agent = automation.agentType,
                size = 28.dp,
            )
            val dotColor = when {
                !automation.enabled -> colors.textSecondary.copy(alpha = 0.4f)
                automation.lastRunStatus == "running" -> Color(0xFFF59E0B)
                automation.lastRunStatus == "succeeded" -> Color(0xFF10B981)
                automation.lastRunStatus == "failed" -> colors.danger
                else -> Color(0xFF10B981)
            }
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                automation.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (automation.enabled) colors.textPrimary else colors.textSecondary,
                maxLines = 1,
            )
            val subtitle = formatAutomationSubtitle(automation)
            if (subtitle.isNotEmpty()) {
                Text(
                    subtitle,
                    fontSize = 12.sp,
                    color = colors.textSecondary,
                    maxLines = 1,
                )
            }
        }

        Switch(
            checked = automation.enabled,
            onCheckedChange = { onToggle() },
        )

        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = colors.textSecondary,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Composable
private fun AutomationDetailView(
    automation: Automation,
    runs: List<AutomationRun>,
    detailLoading: Boolean,
    onToggleEnabled: () -> Unit,
    onRefreshRuns: () -> Unit,
) {
    val colors = CodegTheme.colors

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Top card: Status & Switch
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .webSurface(CodegShapes.small)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    automation.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textPrimary,
                )
                Text(
                    if (automation.enabled) stringResource(R.string.automations_enabled) else stringResource(R.string.automations_disabled),
                    fontSize = 13.sp,
                    color = if (automation.enabled) Color(0xFF10B981) else colors.textSecondary,
                )
            }
            Switch(
                checked = automation.enabled,
                onCheckedChange = { onToggleEnabled() },
            )
        }

        // Section: Schedule / Configuration
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .webSurface(CodegShapes.small)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                stringResource(R.string.automations_section_schedule),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = colors.textSecondary,
            )

            DetailRow(
                icon = Icons.Rounded.Schedule,
                label = stringResource(R.string.automations_trigger),
                value = if (automation.triggerKind == TriggerKind.SCHEDULE && automation.cron != null) {
                    stringResource(R.string.automations_trigger_schedule) + " (" + automation.cron + ")"
                } else {
                    stringResource(R.string.automations_trigger_manual)
                },
            )

            DetailRow(
                icon = Icons.Rounded.PlayArrow,
                label = stringResource(R.string.automations_agent),
                value = automation.agentType.displayName,
            )

            if (automation.isolation == IsolationMode.WORKTREE_PER_RUN) {
                DetailRow(
                    icon = Icons.Rounded.Layers,
                    label = stringResource(R.string.automations_isolation),
                    value = stringResource(R.string.automations_isolation_worktree),
                )
            } else {
                DetailRow(
                    icon = Icons.Rounded.Folder,
                    label = stringResource(R.string.automations_isolation),
                    value = stringResource(R.string.automations_isolation_shared) + (if (automation.branch != null) " (" + automation.branch + ")" else ""),
                )
            }

            if (automation.nextRunAt != null) {
                DetailRow(
                    icon = Icons.Rounded.CalendarToday,
                    label = stringResource(R.string.automations_next_run),
                    value = formatDateTime(automation.nextRunAt),
                )
            }

            if (automation.lastRunAt != null) {
                DetailRow(
                    icon = Icons.Rounded.History,
                    label = stringResource(R.string.automations_last_run),
                    value = RelativeTime.compact(automation.lastRunAt),
                )
            }
        }

        // Section: Prompt
        val prompt = automation.promptText
        if (prompt.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .webSurface(CodegShapes.small)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    stringResource(R.string.automations_section_prompt),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.textSecondary,
                )
                Text(
                    prompt,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = colors.textPrimary,
                )
            }
        }

        // Section: Run History
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .webSurface(CodegShapes.small)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    stringResource(R.string.automations_section_runs),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.textSecondary,
                )
                CodegIconButton(onClick = onRefreshRuns) {
                    Icon(
                        Icons.Rounded.Refresh,
                        contentDescription = stringResource(R.string.automations_refresh),
                        modifier = Modifier.size(16.dp),
                        tint = colors.textSecondary,
                    )
                }
            }

            if (detailLoading && runs.isEmpty()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = colors.accent,
                    )
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
                        if (index < runs.lastIndex) {
                            HorizontalDivider(color = colors.hairline)
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

    val statusColor = when (run.status) {
        AutomationRunStatus.RUNNING -> Color(0xFFF59E0B)
        AutomationRunStatus.SUCCEEDED -> Color(0xFF10B981)
        AutomationRunStatus.FAILED -> colors.danger
        AutomationRunStatus.CANCELLED, AutomationRunStatus.SKIPPED -> colors.textSecondary
    }

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
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(statusColor)
                )
                Text(
                    statusLabel,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = statusColor,
                )
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
        Icon(
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

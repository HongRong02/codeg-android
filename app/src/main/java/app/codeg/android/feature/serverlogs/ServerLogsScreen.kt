package app.codeg.android.feature.serverlogs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodeBlock
import app.codeg.android.core.designsystem.component.CodegIconButton
import app.codeg.android.core.designsystem.component.CodegTopBar
import app.codeg.android.core.designsystem.component.webSurface
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ServerLogsScreen(onBack: () -> Unit, viewModel: ServerLogsViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors
    var tab by remember { mutableIntStateOf(0) }
    var query by remember(ui.selectedFile, tab) { mutableStateOf("") }
    val tabs = buildList {
        if (LogsSection.RECENT !in ui.unsupported) add(0 to R.string.logs_stats_recent)
        if (LogsSection.FILES !in ui.unsupported) add(1 to R.string.logs_stats_files)
        if (LogsSection.STATS !in ui.unsupported || LogsSection.TOKENS !in ui.unsupported) add(2 to R.string.logs_stats_usage)
    }
    LaunchedEffect(tabs) { if (tabs.none { it.first == tab }) tab = tabs.firstOrNull()?.first ?: 0 }
    BackHandler(ui.selectedFile != null) { viewModel.selectFile(null) }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CodegTopBar(
                title = stringResource(R.string.logs_stats_title), showDivider = true,
                navigationIcon = {
                    CodegIconButton(onClick = { if (ui.selectedFile != null) viewModel.selectFile(null) else onBack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.common_back))
                    }
                },
                actions = {
                    CodegIconButton(onClick = viewModel::load) {
                        Icon(Icons.Rounded.Refresh, stringResource(R.string.logs_stats_refresh))
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding).fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            item {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    tabs.forEach { (id, label) ->
                        FilterChip(selected = tab == id, onClick = { tab = id; viewModel.selectFile(null) }, label = { Text(stringResource(label)) })
                    }
                }
                if (ui.loading || ui.fileLoading) LinearProgressIndicator(Modifier.fillMaxWidth(), color = colors.accent)
                if (!ui.available) Text(stringResource(R.string.logs_stats_unavailable), color = colors.textSecondary)
            }
            val sections = when (tab) {
                0 -> listOf(LogsSection.RECENT)
                1 -> listOf(LogsSection.FILES)
                else -> listOf(LogsSection.STATS, LogsSection.TOKENS)
            }
            sections.forEach { section ->
                ui.errors[section]?.let { message -> item { Text(message, color = colors.danger) } }
            }
            if (tab == 0 && LogsSection.RECENT !in ui.unsupported) {
                item {
                    Text(stringResource(R.string.logs_stats_min_level), color = colors.textSecondary)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        val levels = listOf(null, "error", "warn", "info", "debug", "trace")
                        val labels = listOf(R.string.logs_stats_all, R.string.logs_stats_error, R.string.logs_stats_warn, R.string.logs_stats_info, R.string.logs_stats_debug, R.string.logs_stats_trace)
                        levels.forEachIndexed { index, level ->
                            FilterChip(selected = ui.level == level, onClick = { viewModel.setLevel(level) }, label = { Text(stringResource(labels[index])) })
                        }
                    }
                    Text(stringResource(R.string.logs_stats_recent_limit), color = colors.textSecondary)
                }
            }
            if ((tab == 0 && LogsSection.RECENT !in ui.unsupported) || (tab == 1 && ui.selectedFile != null && LogsSection.FILES !in ui.unsupported)) {
                item {
                    OutlinedTextField(query, { query = it }, modifier = Modifier.fillMaxWidth(), singleLine = true,
                        label = { Text(stringResource(R.string.logs_stats_search)) })
                }
                val rawText = if (tab == 0) ui.recent.joinToString("\n") { it.line() } else ui.fileText.orEmpty()
                val text = filterLogText(rawText, query)
                if (tab == 1) item {
                    Text(ui.selectedFile.orEmpty(), color = colors.textPrimary)
                    Text(stringResource(R.string.logs_stats_file_limit), color = colors.textSecondary)
                }
                item {
                    if (text.isEmpty() && !ui.loading && !ui.fileLoading) Text(stringResource(R.string.logs_stats_empty), color = colors.textSecondary)
                    else if (text.isNotEmpty()) CodeBlock(code = text, language = stringResource(R.string.logs_stats_log), collapseAfter = 100)
                }
            }
            if (tab == 1 && ui.selectedFile == null && LogsSection.FILES !in ui.unsupported) {
                if (ui.files.isEmpty() && !ui.loading) item { Text(stringResource(R.string.logs_stats_empty), color = colors.textSecondary) }
                items(ui.files, key = { it.name }) { file ->
                    Column(Modifier.fillMaxWidth().webSurface(CodegShapes.small).clickable { viewModel.selectFile(file.name) }.padding(12.dp)) {
                        Text(file.name, color = colors.textPrimary)
                        Text(stringResource(R.string.logs_stats_file_meta, file.sizeBytes, java.time.Instant.ofEpochMilli(file.modifiedMs).toString()), color = colors.textSecondary)
                    }
                }
            }
            if (tab == 2) {
                if (LogsSection.STATS !in ui.unsupported) ui.stats?.let { stats ->
                    item {
                        Text(stringResource(R.string.logs_stats_sessions), style = MaterialTheme.typography.titleMedium, color = colors.textPrimary)
                        StatRow(stringResource(R.string.logs_stats_conversations), stats.totalConversations)
                        StatRow(stringResource(R.string.logs_stats_messages), stats.totalMessages)
                        stats.byAgent.forEach { StatRow(it.agentType, it.conversationCount) }
                    }
                }
                if (LogsSection.TOKENS !in ui.unsupported) {
                    item {
                        Text(stringResource(R.string.logs_stats_token_usage), style = MaterialTheme.typography.titleMedium, color = colors.textPrimary)
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(7 to R.string.logs_stats_week, 30 to R.string.logs_stats_month, null to R.string.logs_stats_all_time).forEach { (days, label) ->
                                FilterChip(selected = ui.days == days, onClick = { viewModel.setDays(days) }, label = { Text(stringResource(label)) })
                            }
                        }
                    }
                    ui.report?.let { report ->
                        item {
                            val totals = report.totals
                            StatRow(stringResource(R.string.logs_stats_total_tokens), totals.totalTokens)
                            StatRow(stringResource(R.string.logs_stats_input), totals.inputTokens)
                            StatRow(stringResource(R.string.logs_stats_output), totals.outputTokens)
                            StatRow(stringResource(R.string.logs_stats_cache_read), totals.cacheReadTokens)
                            StatRow(stringResource(R.string.logs_stats_cache_write), totals.cacheCreationTokens)
                            StatRow(stringResource(R.string.logs_stats_conversations), totals.conversationCount)
                            StatRow(stringResource(R.string.logs_stats_turns), totals.turnCount)
                            if (report.truncated) Text(stringResource(R.string.logs_stats_truncated), color = colors.textSecondary)
                            Text(stringResource(R.string.logs_stats_daily), style = MaterialTheme.typography.titleSmall, color = colors.textPrimary)
                            if (report.series.isEmpty()) Text(stringResource(R.string.logs_stats_empty), color = colors.textSecondary)
                        }
                        items(report.series, key = { it.bucketKey }) { point -> StatRow(point.bucketKey, point.totalTokens) }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: Long) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, modifier = Modifier.weight(1f), color = CodegTheme.colors.textSecondary)
        Text(java.text.NumberFormat.getIntegerInstance().format(value), color = CodegTheme.colors.textPrimary)
    }
}

package app.codeg.android.feature.activity

import app.codeg.android.core.designsystem.icon.*
import android.text.format.DateUtils
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegInstantCollapsible
import androidx.compose.runtime.key
import androidx.compose.foundation.layout.Column
import app.codeg.android.core.designsystem.component.CodegIconButton
import app.codeg.android.core.designsystem.component.CodegSectionHeader
import app.codeg.android.core.designsystem.component.CodegTopBar
import app.codeg.android.core.designsystem.component.EmptyState
import app.codeg.android.core.designsystem.component.InlineError
import app.codeg.android.core.designsystem.component.LoadingView
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.feature.sessions.SessionRow
import java.time.Instant

/**
 * The Activity tab: a live feed split into **Running** and **Last 24 Hours** sections.
 * Mirrors the Chats list's polish — the grouping is computed off the main thread
 * ([ActivityViewModel.sections]), the sections share the prominent collapsible header,
 * and the list recycles per item type.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    onOpenConversation: (Int) -> Unit,
    viewModel: ActivityViewModel = hiltViewModel(),
) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val sections by viewModel.sections.collectAsStateWithLifecycle()
    var collapsed by rememberSaveable { mutableStateOf(emptySet<String>()) }
    val colors = CodegTheme.colors

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CodegTopBar(
                title = stringResource(R.string.tab_activity),
                showDivider = true,
                actions = {
                    CodegIconButton(
                        onClick = viewModel::refresh,
                        visualSize = 32.dp,
                    ) {
                        CodegIcon(
                            Lucide.RefreshCw,
                            contentDescription = stringResource(R.string.activity_refresh),
                            tint = colors.textSecondary,
                        )
                    }
                },
            )
        },
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when {
                ui.loading && !ui.hasLoaded ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingView(stringResource(R.string.common_loading))
                    }

                !ui.hasLoaded && ui.error != null ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        InlineError(
                            icon = Lucide.AudioLines,
                            title = stringResource(R.string.activity_load_failed),
                            message = ui.error!!,
                            onRetry = viewModel::refresh,
                            retryLabel = stringResource(R.string.common_retry),
                        )
                    }

                ui.hasLoaded && ui.isEmptyFeed ->
                    PullToRefreshBox(
                        isRefreshing = ui.refreshing,
                        onRefresh = viewModel::refresh,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            contentAlignment = Alignment.Center,
                        ) {
                            EmptyState(
                                icon = Lucide.Moon,
                                title = stringResource(R.string.activity_empty_title),
                                message = stringResource(R.string.activity_empty_message),
                            )
                        }
                    }

                else -> PullToRefreshBox(
                    isRefreshing = ui.refreshing,
                    onRefresh = viewModel::refresh,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val toggle: (String) -> Unit = { id ->
                        collapsed = if (id in collapsed) collapsed - id else collapsed + id
                    }
                    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)) {
                        sections.forEach { section ->
                            val isCollapsed = section.id in collapsed
                            item(key = "section-${section.id}", contentType = "section") {
                                val style = activitySectionStyle(section.kind)
                                CodegInstantCollapsible(expanded = !isCollapsed,
                                    onExpandedChange = { if (section.count > 0) toggle(section.id) },
                                    modifier = Modifier.animateItem(),
                                    header = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            CodegIcon(style.icon, contentDescription = null, tint = style.tint)
                                            CodegSectionHeader(title = style.label, count = section.count,
                                                modifier = Modifier.weight(1f))
                                        }
                                    }) {
                                    Column {
                                        section.rows.forEach { row ->
                                            key(row.conversation.id) {
                                                SessionRow(row.conversation,
                                                    onClick = { onOpenConversation(row.conversation.id) },
                                                    folderName = row.folderName)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        ui.lastRefreshed?.let { ts ->
                            item("updated", contentType = "footer") { UpdatedFooter(ts) }
                        }
                    }
                }
            }
        }
    }
}

/** Resolved header presentation for an [ActivityKind] (icon + theme-aware tint + label). */
private data class ActivityStyle(val icon: ImageVector, val tint: Color, val label: String)

@Composable
private fun activitySectionStyle(kind: ActivityKind): ActivityStyle {
    val colors = CodegTheme.colors
    return when (kind) {
        ActivityKind.RUNNING -> ActivityStyle(
            icon = Lucide.AudioLines,
            tint = colors.accent,
            label = stringResource(R.string.activity_running),
        )
        ActivityKind.RECENT -> ActivityStyle(
            icon = Lucide.History,
            tint = colors.textSecondary,
            label = stringResource(R.string.activity_recent),
        )
    }
}

/** "Updated 2 minutes ago" — a dim, centred footer echoing the iOS Activity feed. */
@Composable
private fun UpdatedFooter(timestamp: Instant) {
    val relative = DateUtils.getRelativeTimeSpanString(
        timestamp.toEpochMilli(),
        System.currentTimeMillis(),
        DateUtils.MINUTE_IN_MILLIS,
    ).toString()
    Text(
        text = stringResource(R.string.activity_updated, relative),
        style = MaterialTheme.typography.labelSmall,
        color = CodegTheme.colors.textSecondary,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp),
    )
}

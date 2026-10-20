package app.codeg.android.feature.worktasks

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.codeg.android.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/** Localized label for a work task's DB status (web: tasks.status*); unknown values pass through. */
@Composable
fun workTaskStatusLabel(status: String): String = when (status) {
    "todo" -> stringResource(R.string.wt_status_todo)
    "queued" -> stringResource(R.string.wt_status_queued)
    "preparing" -> stringResource(R.string.wt_status_preparing)
    "running" -> stringResource(R.string.wt_status_running)
    "awaiting_input" -> stringResource(R.string.wt_status_awaiting_input)
    "review" -> stringResource(R.string.wt_status_review)
    "merging" -> stringResource(R.string.wt_status_merging)
    "done" -> stringResource(R.string.wt_status_done)
    "failed" -> stringResource(R.string.wt_status_failed)
    "canceled" -> stringResource(R.string.wt_status_canceled)
    "interrupted" -> stringResource(R.string.wt_status_interrupted)
    else -> status
}

private val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

/** Server RFC 3339 timestamp in the device's zone; unparseable input is shown as-is. */
fun formatWorkTaskTime(iso: String): String =
    runCatching { timeFormatter.format(Instant.parse(iso).atZone(ZoneId.systemDefault())) }.getOrDefault(iso)

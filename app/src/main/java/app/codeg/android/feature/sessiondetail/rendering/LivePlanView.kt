package app.codeg.android.feature.sessiondetail.rendering

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegSpinner
import app.codeg.android.core.designsystem.icon.*
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.PlanEntry

private val SuccessGreen: androidx.compose.ui.graphics.Color @Composable @ReadOnlyComposable get() = CodegTheme.colors.success

/** The agent's live plan/TODO checklist (display-only). Port of iOS `LivePlanView`. */
@Composable
fun LivePlanView(entries: List<PlanEntry>, modifier: Modifier = Modifier) {
    if (entries.isEmpty()) return
    val colors = CodegTheme.colors
    val done = entries.count { it.normalizedStatus == PlanEntry.Status.COMPLETED }

    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(colors.textPrimary.copy(alpha = 0.03f))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Lucide.ListTodo, null, tint = colors.textSecondary, modifier = Modifier.size(16.dp))
            Text(stringResource(R.string.liveplan_title), fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = colors.textSecondary, modifier = Modifier.weight(1f))
            Text("$done/${entries.size}", fontSize = 11.sp, color = colors.textTertiary)
        }
        for (entry in entries) PlanRow(entry)
    }
}

@Composable
private fun PlanRow(entry: PlanEntry) {
    val colors = CodegTheme.colors
    val completed = entry.normalizedStatus == PlanEntry.Status.COMPLETED
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
        when (entry.normalizedStatus) {
            PlanEntry.Status.COMPLETED ->
                Icon(Lucide.CircleCheckBig, null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
            PlanEntry.Status.IN_PROGRESS ->
                CodegSpinner(modifier = Modifier.size(13.dp))
            PlanEntry.Status.PENDING ->
                Icon(Lucide.CircleDashed, null, tint = colors.textTertiary, modifier = Modifier.size(16.dp))
        }
        Text(
            text = entry.content,
            fontSize = 13.sp,
            color = if (completed) colors.textTertiary else colors.textPrimary,
            textDecoration = if (completed) TextDecoration.LineThrough else null,
            modifier = Modifier.weight(1f),
        )
        if (entry.normalizedPriority == PlanEntry.Priority.HIGH) {
            Text(
                stringResource(R.string.liveplan_priority_high),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = colors.danger,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(colors.danger.copy(alpha = 0.14f))
                    .padding(horizontal = 5.dp, vertical = 1.dp),
            )
        }
        Box(Modifier.size(0.dp))
    }
}

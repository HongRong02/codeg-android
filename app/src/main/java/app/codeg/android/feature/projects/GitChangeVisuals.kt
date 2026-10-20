package app.codeg.android.feature.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.core.designsystem.theme.withOpacity
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.GitChange

/** Per-category tint for a working-tree change, shared by the Changes tab + commit sheet. */
/** The colored letter badge (M / A / U / …) for a change category. */
@Composable
internal fun GitChangeBadge(change: GitChange, modifier: Modifier = Modifier) {
    val colors = CodegTheme.colors
    val tint = when (change) {
        GitChange.DELETED, GitChange.CONFLICTED -> colors.danger
        GitChange.ADDED, GitChange.UNTRACKED -> colors.accent
        else -> colors.textSecondary
    }
    Box(
        modifier.size(20.dp).clip(RoundedCornerShape(CodegTheme.dimens.radiusMd)).background(tint.withOpacity(0.16f)),
        contentAlignment = Alignment.Center,
    ) {
        Text(change.badge, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = tint)
    }
}

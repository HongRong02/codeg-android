package app.codeg.android.core.designsystem.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.AgentType
import app.codeg.android.core.model.ConversationStatus

private val Capsule = RoundedCornerShape(percent = 50)

/** Conversation-status dot colour (web `STATUS_COLORS`: yellow-400 / blue-500 / green-500 / red-500). */
@Composable
fun statusColor(status: ConversationStatus): Color = when (status) {
    ConversationStatus.IN_PROGRESS -> Color(0xFFFACC15)
    ConversationStatus.PENDING_REVIEW -> Color(0xFF3B82F6)
    ConversationStatus.COMPLETED -> Color(0xFF22C55E)
    ConversationStatus.CANCELLED -> Color(0xFFEF4444)
    ConversationStatus.OTHER -> CodegTheme.colors.textTertiary
}

/** Status label (web `STATUS_LABELS`). */
fun statusLabel(status: ConversationStatus): String = when (status) {
    ConversationStatus.IN_PROGRESS -> "In Progress"
    ConversationStatus.PENDING_REVIEW -> "Review"
    ConversationStatus.COMPLETED -> "Completed"
    ConversationStatus.CANCELLED -> "Cancelled"
    ConversationStatus.OTHER -> "—"
}

/** Circular agent avatar: the brand icon on a `--muted` circle with a `--border` ring. */
@Composable
fun AgentAvatar(
    agent: AgentType,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
) {
    val colors = CodegTheme.colors
    Box(
        modifier
            .size(size)
            .clip(CircleShape)
            .background(colors.web.muted)
            .border(1.dp, colors.surfaceStroke, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        AgentIcon(agent, size = size * 0.56f)
    }
}

/** Agent type pill, the web `Badge` secondary variant: brand icon + short name. */
@Composable
fun AgentBadge(agent: AgentType, modifier: Modifier = Modifier) {
    val web = CodegTheme.colors.web
    Row(
        modifier
            .clip(Capsule)
            .background(web.secondary)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AgentIcon(agent, size = 12.dp)
        Text(
            text = "  ${agent.shortName}",
            color = web.secondaryForeground,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

/** Status pill, the web `Badge` outline variant: status-coloured dot + label. */
@Composable
fun StatusBadge(status: ConversationStatus, modifier: Modifier = Modifier) {
    val colors = CodegTheme.colors
    Row(
        modifier
            .clip(Capsule)
            .border(1.dp, colors.surfaceStroke, Capsule)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(6.dp).clip(CircleShape).background(statusColor(status)))
        Text(
            text = "  ${statusLabel(status)}",
            color = colors.textPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

/**
 * Circular, tinted section icon (iOS `SectionBadgeIcon`): a glyph centred on a
 * tinted circle that mirrors [AgentAvatar]'s shape, so a group header visually
 * rhymes with the agent avatars in its rows and — at the same 26dp diameter —
 * lines the header title up with the row titles beneath it.
 */
@Composable
fun SectionBadgeIcon(
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    size: Dp = 26.dp,
) {
    Box(
        modifier
            .size(size)
            .clip(CircleShape)
            .background(tint.copy(alpha = 0.16f))
            .border(1.dp, tint.copy(alpha = 0.32f), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(size * 0.5f))
    }
}

/**
 * Leading icon tile for Settings rows: a glyph on a `--muted` rounded-md square,
 * like the icons in the web settings navigation. Pass [tint] to colour it.
 */
@Composable
fun SettingsIconBadge(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = CodegTheme.colors.textPrimary,
    size: Dp = 28.dp,
) {
    Box(
        modifier
            .size(size)
            .clip(RoundedCornerShape(CodegTheme.dimens.radiusSm))
            .background(CodegTheme.colors.web.muted),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(size * 0.55f))
    }
}

/** Square folder badge (iOS `FolderBadge`). */
@Composable
fun FolderBadge(
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
) {
    Box(
        modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.3f))
            .background(color.copy(alpha = 0.18f))
            .border(1.dp, color.copy(alpha = 0.40f), RoundedCornerShape(size * 0.3f)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.Folder,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(size * 0.45f),
        )
    }
}

/** Small count pill (web `Badge` secondary variant). */
@Composable
fun CountBadge(count: Int, modifier: Modifier = Modifier) {
    val web = CodegTheme.colors.web
    Text(
        text = count.toString(),
        color = web.secondaryForeground,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier
            .clip(Capsule)
            .background(web.secondary)
            .padding(horizontal = 6.dp, vertical = 1.dp),
    )
}

/** A pulsing "live" indicator: the web's in-progress (yellow) dot + expanding ring. */
@Composable
fun LivePulse(modifier: Modifier = Modifier, dotSize: Dp = 8.dp) {
    val accent = statusColor(ConversationStatus.IN_PROGRESS)
    val transition = rememberInfiniteTransition(label = "live-pulse")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100),
            repeatMode = RepeatMode.Restart,
        ),
        label = "pulse-progress",
    )
    Box(modifier.size(dotSize), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(dotSize)
                .scale(1f + progress * 1.2f)
                .drawBehind {
                    drawCircle(color = accent.copy(alpha = (1f - progress) * 0.8f))
                },
        )
        Box(Modifier.size(dotSize).clip(CircleShape).background(accent))
    }
}

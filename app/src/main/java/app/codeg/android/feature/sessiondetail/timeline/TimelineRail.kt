package app.codeg.android.feature.sessiondetail.timeline

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.codeg.android.core.model.AgentType
import app.codeg.android.feature.sessiondetail.rendering.ToolCallState
import app.codeg.android.feature.sessiondetail.rendering.ToolKindBucket

/**
 * Spacing for the message thread, after the web `MessageThread` (`gap-8`
 * between messages) and the tighter rhythm inside one reply.
 */
object TimelineMetrics {
    val rowSpacing: Dp = 12.dp          // between parts of one reply (text, tool, reasoning)
    val groupGap: Dp = 20.dp            // extra space above a new turn: 12 + 20 = the web's 32
    val blockGap: Dp = 8.dp             // between a split message's block rows (≈ MarkdownContent spacedBy)
    /** The web caps the user bubble at `max-w-[88%]`. */
    const val userMaxWidthFraction: Float = 0.88f
}

/**
 * How a row participates in a split message. A long assistant message is one
 * LazyColumn item per Markdown block: [Head]/[Middle]/[Tail] rows sit tight so
 * the reply still reads as a single unit. [Standalone] is a one-block message
 * (or any non-split node).
 */
enum class RailStyle { Standalone, Head, Middle, Tail }

/**
 * What kind of node a row holds. The web thread draws no per-node markers; the
 * kind still decides alignment (user messages sit on the right).
 */
sealed interface MarkerKind {
    data object User : MarkerKind
    data class Assistant(val agent: AgentType) : MarkerKind
    data object Reasoning : MarkerKind
    data class Tool(val bucket: ToolKindBucket, val state: ToolCallState) : MarkerKind
    data class ToolGroup(val error: Boolean, val streaming: Boolean) : MarkerKind
    data object Image : MarkerKind
    data object System : MarkerKind
    data object Plan : MarkerKind
    data object Thinking : MarkerKind
    data object Error : MarkerKind
    data object Footer : MarkerKind
    data object Compaction : MarkerKind
}

/**
 * One row of the message thread, laid out like the web `MessageThread`: no
 * gutter or avatars, user messages right-aligned and capped at 88% of the
 * width, everything else full width. [connectTop]/[connectBottom] are kept for
 * the caller's node model; the thread has no connecting spine to draw.
 */
@Composable
fun TimelineRow(
    marker: MarkerKind,
    @Suppress("UNUSED_PARAMETER") connectTop: Boolean,
    @Suppress("UNUSED_PARAMETER") connectBottom: Boolean,
    startsGroup: Boolean,
    modifier: Modifier = Modifier,
    rail: RailStyle = RailStyle.Standalone,
    content: @Composable () -> Unit,
) {
    val topPad = when (rail) {
        RailStyle.Standalone, RailStyle.Head ->
            TimelineMetrics.rowSpacing / 2 + (if (startsGroup) TimelineMetrics.groupGap else 0.dp)
        RailStyle.Middle, RailStyle.Tail -> 0.dp
    }
    val bottomPad = when (rail) {
        RailStyle.Standalone, RailStyle.Tail -> TimelineMetrics.rowSpacing / 2
        RailStyle.Head, RailStyle.Middle -> TimelineMetrics.blockGap
    }
    val rowModifier = modifier.fillMaxWidth().padding(top = topPad, bottom = bottomPad)
    if (marker is MarkerKind.User) {
        BoxWithConstraints(rowModifier, contentAlignment = Alignment.TopEnd) {
            Box(Modifier.widthIn(max = maxWidth * TimelineMetrics.userMaxWidthFraction)) { content() }
        }
    } else {
        Box(rowModifier) { content() }
    }
}

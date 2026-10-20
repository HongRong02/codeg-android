package app.codeg.android.feature.sessions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.PushPin
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.codeg.android.core.model.ConversationTag
import app.codeg.android.feature.sessions.tags.ConversationTagChip
import app.codeg.android.R
import app.codeg.android.core.common.RelativeTime
import app.codeg.android.core.designsystem.component.AgentAvatar
import app.codeg.android.core.designsystem.component.LivePulse
import app.codeg.android.core.designsystem.component.statusColor
import app.codeg.android.core.designsystem.theme.PillShape
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.ConversationSummary
import androidx.compose.ui.res.stringResource

/** Web sidebar row with a 48dp touch target and a long-press Pin/Unpin menu. */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SessionRow(
    conversation: ConversationSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    folderName: String? = null,
    selected: Boolean = false,
    onTogglePin: (() -> Unit)? = null,
    tags: List<ConversationTag> = emptyList(),
    onEditTags: (() -> Unit)? = null,
) {
    val colors = CodegTheme.colors
    var menuOpen by remember { mutableStateOf(false) }

    Box(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .clip(PillShape)
                .then(
                    if (selected) Modifier.background(colors.web.sidebarAccent) else Modifier,
                )
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = if (onTogglePin != null || onEditTags != null) {
                        { menuOpen = true }
                    } else {
                        null
                    },
                )
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box {
                AgentAvatar(conversation.agentType, size = 26.dp)
                Box(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 2.dp, y = 2.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(statusColor(conversation.status))
                        .border(1.5.dp, colors.bg, CircleShape),
                )
            }

            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text = conversation.trimmedTitle ?: stringResource(R.string.session_untitled),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (tags.isNotEmpty()) Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    tags.take(2).forEach { ConversationTagChip(it) }
                    if (tags.size > 2) Text(stringResource(R.string.ct_more, tags.size - 2),
                        style = MaterialTheme.typography.labelSmall, color = colors.textSecondary)
                }
            }

            if (folderName != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier.widthIn(max = 108.dp),
                ) {
                    Icon(
                        Icons.Rounded.Folder,
                        contentDescription = null,
                        tint = colors.web.mutedForeground,
                        modifier = Modifier.size(11.dp),
                    )
                    Text(
                        text = folderName,
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.web.mutedForeground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            if (conversation.status.isLive) {
                LivePulse()
            } else {
                Text(
                    text = RelativeTime.compact(conversation.updatedAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.web.mutedForeground,
                )
            }
        }

        if (onTogglePin != null || onEditTags != null) {
            DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                if (onEditTags != null) DropdownMenuItem(
                    text = { Text(stringResource(R.string.ct_assign)) },
                    onClick = { menuOpen = false; onEditTags() },
                )
                if (onTogglePin != null) DropdownMenuItem(
                    text = {
                        Text(
                            if (conversation.isPinned) {
                                stringResource(R.string.session_unpin)
                            } else {
                                stringResource(R.string.session_pin)
                            },
                        )
                    },
                    leadingIcon = { Icon(Icons.Rounded.PushPin, contentDescription = null) },
                    onClick = {
                        menuOpen = false
                        onTogglePin()
                    },
                )
            }
        }
    }
}

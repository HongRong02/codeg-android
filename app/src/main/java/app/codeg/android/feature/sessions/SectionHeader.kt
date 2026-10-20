package app.codeg.android.feature.sessions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CountBadge
import app.codeg.android.core.designsystem.theme.CodegTheme

/** Compact web-style group label with a full-row touch target for folding. */
@Composable
fun CollapsibleSectionHeader(
    icon: ImageVector,
    tint: Color,
    label: String,
    count: Int,
    collapsed: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    collapsible: Boolean = true,
) {
    val colors = CodegTheme.colors
    val rotation by animateFloatAsState(
        targetValue = if (collapsed) 0f else 180f,
        label = "section-chevron",
    )
    val toggleLabel = stringResource(
        if (collapsed) R.string.sessions_expand else R.string.sessions_collapse,
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp) // inter-section gap, kept outside the ripple
            .clip(MaterialTheme.shapes.extraSmall)
            .heightIn(min = 48.dp)
            .then(
                if (collapsible) {
                    Modifier.clickable(onClickLabel = toggleLabel, role = Role.Button, onClick = onToggle)
                } else {
                    Modifier
                },
            )
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.web.mutedForeground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        CountBadge(count)
        if (collapsible) {
            Icon(
                Icons.Rounded.ExpandMore,
                contentDescription = null,
                tint = colors.web.mutedForeground,
                modifier = Modifier.size(16.dp).rotate(rotation),
            )
        }
    }
}

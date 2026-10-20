package app.codeg.android.feature.sessions

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.codeg.android.core.designsystem.component.CodegSectionHeader

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
    // TODO(T53): CodegCollapsible owns the section body in the next phase.
    CodegSectionHeader(
        title = label,
        count = count,
        collapsed = collapsed,
        onToggle = if (collapsible) onToggle else null,
        modifier = modifier.padding(top = 12.dp),
        action = { Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp)) },
    )
}

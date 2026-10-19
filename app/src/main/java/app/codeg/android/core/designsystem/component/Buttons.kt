package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.PillShape
import app.codeg.android.core.designsystem.theme.withOpacity

/**
 * Full-width primary button, matching the web `Button` (default variant):
 * `--primary` fill, pill shape, medium-weight label, 50% opacity when disabled.
 * Shows a spinner while [loading] and disables itself.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: ImageVector? = null,
) {
    val colors = CodegTheme.colors
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        modifier = modifier.fillMaxWidth().heightIn(min = 40.dp),
        shape = PillShape,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.accent,
            contentColor = colors.onAccent,
            disabledContainerColor = colors.accent.copy(alpha = 0.50f),
            disabledContentColor = colors.onAccent.copy(alpha = 0.50f),
        ),
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = colors.onAccent,
                strokeWidth = 2.dp,
            )
        } else {
            if (icon != null) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp))
            }
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = if (icon != null) 8.dp else 0.dp),
            )
        }
    }
}

/**
 * Single-choice segmented control, matching the web `Tabs` list: a pill
 * `--muted` track with the active rounded-xl segment raised on `--background`
 * (an `--input` wash in dark mode, as the web does).
 */
@Composable
fun CodegSegmented(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = CodegTheme.colors
    val web = colors.web
    val track = PillShape
    val tab = RoundedCornerShape(CodegTheme.dimens.radiusMd)
    Row(
        modifier
            .fillMaxWidth()
            .clip(track)
            .background(web.muted)
            .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        options.forEachIndexed { index, label ->
            val selected = index == selectedIndex
            val fill = when {
                !selected -> Color.Transparent
                colors.isDark -> web.input.withOpacity(0.30f)
                else -> web.background
            }
            Box(
                Modifier
                    .weight(1f)
                    .heightIn(min = 32.dp)
                    .clip(tab)
                    .background(fill)
                    .then(if (selected && colors.isDark) Modifier.border(1.dp, web.input, tab) else Modifier)
                    .clickable(role = Role.Tab) { onSelect(index) }
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (selected) colors.textPrimary else colors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

/**
 * Material 3 [FilterChip] in the web's toggle look: outlined with `--border`
 * when off, `--secondary` fill when on, pill-shaped. [icon] renders as the
 * leading icon (pass `Icons.Rounded.Check` when selected for multi-select).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodegFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val colors = CodegTheme.colors
    val web = colors.web
    FilterChip(
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = PillShape,
        label = { Text(label) },
        leadingIcon = icon?.let {
            { Icon(it, contentDescription = null, modifier = Modifier.size(FilterChipDefaults.IconSize)) }
        },
        colors = FilterChipDefaults.filterChipColors(
            labelColor = colors.textSecondary,
            iconColor = colors.textSecondary,
            selectedContainerColor = web.secondary,
            selectedLabelColor = web.secondaryForeground,
            selectedLeadingIconColor = web.secondaryForeground,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = enabled,
            selected = selected,
            borderColor = colors.surfaceStroke,
            selectedBorderColor = colors.surfaceStroke,
        ),
    )
}

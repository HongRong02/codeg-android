package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.codeg.android.core.designsystem.theme.CodegTheme

/** A 1dp `--border` stroke (the web's `border`). Defaults to the surface stroke. */
@Composable
fun Modifier.hairlineBorder(
    shape: Shape,
    color: Color = CodegTheme.colors.surfaceStroke,
    width: Dp = CodegTheme.dimens.hairlineWidth,
): Modifier = this.border(width, color, shape)

/**
 * A card surface, matching the web `Card`: opaque `--card` fill, 1dp
 * `--border` stroke, rounded-xl corners. (Kept under its original name so the
 * screens built on it didn't need touching.)
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = CodegTheme.dimens.radiusLg,
    padding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = CodegTheme.colors
    val shape = RoundedCornerShape(cornerRadius)
    Column(
        modifier
            .clip(shape)
            .background(colors.bgElevated)
            .hairlineBorder(shape, colors.surfaceStroke)
            .padding(padding),
        content = content,
    )
}

/**
 * A list row surface: rounded-lg card with a 1dp border. Selected rows use the
 * web's `--accent` wash, like the active item in the web sidebar.
 */
@Composable
fun GlassRow(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = CodegTheme.colors
    val shape = RoundedCornerShape(CodegTheme.dimens.radiusMd)
    val fill = if (selected) colors.web.accent else colors.bgElevated
    val stroke = colors.surfaceStroke
    Column(
        modifier
            .clip(shape)
            .background(fill)
            .hairlineBorder(shape, stroke)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        content = content,
    )
}

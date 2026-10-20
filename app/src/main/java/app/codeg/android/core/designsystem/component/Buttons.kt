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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.alpha
import app.codeg.android.core.designsystem.theme.CodegShapes
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

enum class CodegButtonVariant { Default, Secondary, Outline, Ghost, Destructive, Link }
enum class CodegButtonSize { Default, Sm, Lg, Icon }

@Composable
fun CodegButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: CodegButtonVariant = CodegButtonVariant.Default,
    size: CodegButtonSize = CodegButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit,
) {
    val colors = CodegTheme.colors
    val web = colors.web
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val focused by source.collectIsFocusedAsState()
    val background = when (variant) {
        CodegButtonVariant.Default -> web.primary
        CodegButtonVariant.Secondary -> web.secondary
        CodegButtonVariant.Outline -> if (colors.isDark) web.input.withOpacity(.30f) else web.background
        CodegButtonVariant.Destructive -> web.destructive.withOpacity(if (colors.isDark) .60f else 1f)
        CodegButtonVariant.Ghost -> if (pressed) web.accent else Color.Transparent
        CodegButtonVariant.Link -> Color.Transparent
    }
    val foreground = when (variant) {
        CodegButtonVariant.Default, CodegButtonVariant.Destructive -> web.primaryForeground
        CodegButtonVariant.Secondary -> web.secondaryForeground
        CodegButtonVariant.Link -> web.primary
        else -> web.foreground
    }
    val shape = CodegShapes.extraSmall
    val height = when (size) { CodegButtonSize.Sm -> 32.dp; CodegButtonSize.Lg -> 40.dp; else -> 36.dp }
    val inset = when (size) { CodegButtonSize.Sm -> 12.dp; CodegButtonSize.Lg -> 24.dp; CodegButtonSize.Icon -> 0.dp; else -> 16.dp }
    androidx.compose.material3.ProvideTextStyle(androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp, fontWeight = FontWeight.Medium,
        textDecoration = if (variant == CodegButtonVariant.Link) androidx.compose.ui.text.style.TextDecoration.Underline else null,
    )) {
        androidx.compose.runtime.CompositionLocalProvider(androidx.compose.material3.LocalContentColor provides foreground) {
            Row(modifier.alpha(if (enabled) 1f else .5f)
                .then(if (size == CodegButtonSize.Icon) Modifier.size(height) else Modifier.heightIn(min = height))
                .clip(shape).background(background)
                .then(if (focused || variant == CodegButtonVariant.Outline) Modifier.border(1.dp, if (focused) web.ring else web.input, shape) else Modifier)
                .clickable(enabled = enabled && !loading, role = Role.Button, interactionSource = source, indication = null, onClick = onClick)
                .padding(horizontal = inset, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            ) {
                if (loading) CodegSpinner()
                else leadingIcon?.invoke()
                content()
            }
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: ImageVector? = null,
) {
    CodegButton(onClick, modifier.fillMaxWidth(), enabled = enabled, loading = loading,
        leadingIcon = icon?.let { { Icon(it, null, Modifier.size(16.dp)) } }) { Text(text) }
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

@androidx.compose.ui.tooling.preview.Preview(name = "Button Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "Button Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ButtonPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        CodegButton({}) { Text(label) }
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "PrimaryButton Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "PrimaryButton Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrimaryButtonPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        PrimaryButton(label, {})
    }
}

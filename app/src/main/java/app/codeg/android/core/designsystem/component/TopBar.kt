package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.material3.LocalContentColor
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.PillShape
import app.codeg.android.core.designsystem.theme.withOpacity

/**
 * Web header with a 48dp minimum touch row, 14sp medium title and no tonal elevation.
 * [navigationIcon] is empty by default; supply a [CodegIconButton] for back navigation.
 * [titleContent] supports interactive titles such as the server switcher.
 * Attach [scrollBehavior]'s nested scroll connection to the screen's scroll container.
 * [showDivider] shows the web border permanently; otherwise it appears after scrolling.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodegTopBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    showDivider: Boolean = false,
    titleContent: (@Composable () -> Unit)? = null,
) {
    val colors = CodegTheme.colors
    Column(modifier) {
        TopAppBar(
            title = {
                Column {
                    ProvideTextStyle(TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)) {
                        if (titleContent != null) titleContent() else Text(
                            title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    if (subtitle != null) Text(
                        subtitle,
                        fontSize = 12.sp,
                        color = colors.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            },
            navigationIcon = navigationIcon,
            actions = actions,
            expandedHeight = 48.dp,
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colors.web.background,
                scrolledContainerColor = colors.web.background,
                navigationIconContentColor = colors.textSecondary,
                titleContentColor = colors.textPrimary,
                actionIconContentColor = colors.textSecondary,
            ),
        )
        if (showDivider || (scrollBehavior?.state?.overlappedFraction ?: 0f) > 0f) {
            HorizontalDivider(thickness = CodegTheme.dimens.hairlineWidth, color = colors.web.border)
        }
    }
}

/**
 * Ghost icon button with a [visualSize] web pill (32dp or 36dp) inside a 48dp touch target.
 * [content] should provide an icon with an accessible content description; icons default
 * to the web's 16dp size. Hover, focus and press use the theme's muted background.
 */
@Composable
fun CodegIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    visualSize: Dp = 36.dp,
    content: @Composable () -> Unit,
) {
    val colors = CodegTheme.colors
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val active = enabled && (pressed || hovered || focused)
    IconButton(
        onClick = onClick,
        modifier = modifier.size(48.dp),
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        Box(
            Modifier.size(visualSize).background(
                if (active) colors.web.muted.withOpacity(if (colors.isDark) 0.5f else 1f)
                else androidx.compose.ui.graphics.Color.Transparent,
                PillShape,
            ),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(
                LocalContentColor provides colors.textSecondary.withOpacity(if (enabled) 1f else 0.5f),
            ) {
                Box(Modifier.size(16.dp), contentAlignment = Alignment.Center) { content() }
            }
        }
    }
}

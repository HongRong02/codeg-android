package app.codeg.android.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Corner radii and spacing tokens. Radii follow the web UI (rounded-md 8,
 * rounded-lg 10, rounded-xl 14 for cards, 18 for sheets); borders are 1px.
 */
@Immutable
data class CodegDimens(
    val radiusXl: Dp = 18.dp,
    val radiusLg: Dp = 14.dp,
    val radiusMd: Dp = 10.dp,
    val radiusSm: Dp = 8.dp,
    val screenHMargin: Dp = 16.dp,
    val blockSpacing: Dp = 12.dp,
    val hairlineWidth: Dp = 1.dp,
)

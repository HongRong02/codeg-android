package app.codeg.android.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Corner radii and spacing tokens. Radii follow the web UI's scale:
 * sm = rounded-md (8, small tiles), md = rounded-xl (14, list rows, menu items,
 * textareas), lg = rounded-2xl (18, cards), xl = rounded-4xl (26, dialogs and
 * sheets). Borders are 1px.
 */
@Immutable
data class CodegDimens(
    val radiusXl: Dp = 26.dp,
    val radiusLg: Dp = 18.dp,
    val radiusMd: Dp = 14.dp,
    val radiusSm: Dp = 8.dp,
    val screenHMargin: Dp = 16.dp,
    val blockSpacing: Dp = 12.dp,
    val hairlineWidth: Dp = 1.dp,
)

package app.codeg.android.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Material3 shape scale mapped onto the web UI's radii: `--radius` is 0.625rem
 * (10px), with Tailwind's sm/md/lg/xl = radius-4 / radius-2 / radius / radius+4.
 */
val CodegShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(10.dp),
    large = RoundedCornerShape(14.dp),
    extraLarge = RoundedCornerShape(18.dp),
)

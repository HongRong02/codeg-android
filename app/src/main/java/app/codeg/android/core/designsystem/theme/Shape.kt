package app.codeg.android.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Material3 shape scale mapped onto the web UI's radii. `--radius` is 0.625rem
 * (10px) and the web scales it multiplicatively: md 8, xl 14, 2xl 18, 3xl 22,
 * 4xl 26 (globals.css `--radius-*`).
 */
val CodegShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(26.dp),
)

/** Fully rounded ends: the web's `rounded-4xl` on 36px-high buttons, inputs and tab bars. */
val PillShape = RoundedCornerShape(percent = 50)

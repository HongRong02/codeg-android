package app.codeg.android.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.codeg.android.R

/**
 * Inter, the web UI's default font (`--font-sans`), bundled as the variable
 * font (SIL OFL 1.1, see res/raw/inter_ofl.txt). One file serves every weight.
 * CJK text falls back to the system font, as it does on the web.
 */
@OptIn(ExperimentalTextApi::class)
val CodegSans: FontFamily = FontFamily(
    listOf(FontWeight.Normal, FontWeight.Medium, FontWeight.SemiBold, FontWeight.Bold).map { weight ->
        Font(
            R.font.inter,
            weight = weight,
            variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight)),
        )
    },
)

/**
 * Type scale. UI text uses [CodegSans] (Inter, like the web UI); code uses
 * [FontFamily.Monospace]. A bundled developer mono can be dropped in later by
 * swapping [CodegMono].
 */
val CodegMono: FontFamily = FontFamily.Monospace

/** Shared style for inline code / commands / monospaced values (iOS `Font.mono(13)`). */
val CodeTextStyle = TextStyle(
    fontFamily = CodegMono,
    fontSize = 13.sp,
    lineHeight = 18.sp,
)

/**
 * Type scale aligned to the Material 3 reference scale (16sp body, 14sp label),
 * so text reads at native Android sizes rather than the iOS 17pt body. Titles
 * keep a SemiBold weight for brand emphasis; labels use Material line-heights.
 */
private val BaseTypography = Typography(
    titleLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 22.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 24.sp),
    titleSmall = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp),
)

/** [BaseTypography] with every style set in [CodegSans]. */
val CodegTypography: Typography = BaseTypography.copy(
    displayLarge = BaseTypography.displayLarge.copy(fontFamily = CodegSans),
    displayMedium = BaseTypography.displayMedium.copy(fontFamily = CodegSans),
    displaySmall = BaseTypography.displaySmall.copy(fontFamily = CodegSans),
    headlineLarge = BaseTypography.headlineLarge.copy(fontFamily = CodegSans),
    headlineMedium = BaseTypography.headlineMedium.copy(fontFamily = CodegSans),
    headlineSmall = BaseTypography.headlineSmall.copy(fontFamily = CodegSans),
    titleLarge = BaseTypography.titleLarge.copy(fontFamily = CodegSans),
    titleMedium = BaseTypography.titleMedium.copy(fontFamily = CodegSans),
    titleSmall = BaseTypography.titleSmall.copy(fontFamily = CodegSans),
    bodyLarge = BaseTypography.bodyLarge.copy(fontFamily = CodegSans),
    bodyMedium = BaseTypography.bodyMedium.copy(fontFamily = CodegSans),
    bodySmall = BaseTypography.bodySmall.copy(fontFamily = CodegSans),
    labelLarge = BaseTypography.labelLarge.copy(fontFamily = CodegSans),
    labelMedium = BaseTypography.labelMedium.copy(fontFamily = CodegSans),
    labelSmall = BaseTypography.labelSmall.copy(fontFamily = CodegSans),
)

package app.codeg.android.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * The semantic colours used across the app. Resolved by [codegColors] from the
 * active web theme preset ([WebThemePreset]) so every screen renders codeg's
 * web palette; [web] exposes the raw shadcn tokens for components that mirror a
 * web component one-to-one. Provided via `LocalCodegColors`.
 */
@Immutable
data class CodegColors(
    val isDark: Boolean,
    val bg: Color,
    val bgElevated: Color,
    val surfaceStroke: Color,
    val hairline: Color,
    val codeSurface: Color,
    val rail: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val danger: Color,
    val accent: Color,
    val onAccent: Color,
    val accentDim: Color,
    val web: WebTokens,
)

/**
 * Tailwind's opacity modifier (`bg-input/30`): scales the colour's own alpha,
 * which matters for dark-mode tokens such as `--input` (white at 15%).
 */
fun Color.withOpacity(fraction: Float): Color = copy(alpha = alpha * fraction)

/**
 * Map the web tokens onto the app's semantic slots: page = `--background`,
 * surfaces = `--card`, strokes = `--border`, code/inset = `--muted`, text =
 * `--foreground` / `--muted-foreground`, accent = `--primary`, soft accent
 * wash = `--accent`.
 */
fun codegColors(isDark: Boolean, preset: WebThemePreset): CodegColors {
    val w = preset.resolve(isDark)
    return CodegColors(
        isDark = isDark,
        bg = w.background,
        bgElevated = w.card,
        surfaceStroke = w.border,
        hairline = w.border,
        codeSurface = w.muted,
        rail = w.border,
        textPrimary = w.foreground,
        textSecondary = w.mutedForeground,
        // The web has no third text level; muted text at reduced emphasis.
        textTertiary = w.mutedForeground.copy(alpha = 0.75f),
        danger = w.destructive,
        accent = w.primary,
        onAccent = w.primaryForeground,
        accentDim = w.accent,
        web = w,
    )
}

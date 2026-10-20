package app.codeg.android.shell.display

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// shadcn neutral palette: background/foreground, muted, border and primary.
private val LightColors = lightColorScheme(
    primary = Color(0xFF171717), onPrimary = Color(0xFFFAFAFA),
    primaryContainer = Color(0xFFE5E5E5), onPrimaryContainer = Color(0xFF171717),
    inversePrimary = Color(0xFFD4D4D4),
    secondary = Color(0xFFF5F5F5), onSecondary = Color(0xFF171717),
    secondaryContainer = Color(0xFFF5F5F5), onSecondaryContainer = Color(0xFF171717),
    tertiary = Color(0xFF737373), onTertiary = Color.White,
    tertiaryContainer = Color(0xFFF5F5F5), onTertiaryContainer = Color(0xFF171717),
    background = Color.White, onBackground = Color(0xFF0A0A0A),
    surface = Color.White, onSurface = Color(0xFF0A0A0A),
    surfaceVariant = Color(0xFFF5F5F5), onSurfaceVariant = Color(0xFF737373),
    surfaceTint = Color(0xFF171717),
    surfaceDim = Color(0xFFE5E5E5), surfaceBright = Color.White,
    surfaceContainerLowest = Color.White, surfaceContainerLow = Color(0xFFFAFAFA),
    surfaceContainer = Color(0xFFF5F5F5), surfaceContainerHigh = Color(0xFFF5F5F5),
    surfaceContainerHighest = Color(0xFFE5E5E5),
    outline = Color(0xFFE5E5E5), outlineVariant = Color(0xFFE5E5E5),
    inverseSurface = Color(0xFF171717), inverseOnSurface = Color(0xFFFAFAFA),
    error = Color(0xFFDC2626), onError = Color.White,
    errorContainer = Color(0xFFFEE2E2), onErrorContainer = Color(0xFF7F1D1D),
    scrim = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFE5E5E5), onPrimary = Color(0xFF171717),
    primaryContainer = Color(0xFF404040), onPrimaryContainer = Color(0xFFFAFAFA),
    inversePrimary = Color(0xFF171717),
    secondary = Color(0xFF262626), onSecondary = Color(0xFFFAFAFA),
    secondaryContainer = Color(0xFF262626), onSecondaryContainer = Color(0xFFFAFAFA),
    tertiary = Color(0xFFA3A3A3), onTertiary = Color(0xFF171717),
    tertiaryContainer = Color(0xFF262626), onTertiaryContainer = Color(0xFFFAFAFA),
    background = Color(0xFF0A0A0A), onBackground = Color(0xFFFAFAFA),
    surface = Color(0xFF0A0A0A), onSurface = Color(0xFFFAFAFA),
    surfaceVariant = Color(0xFF262626), onSurfaceVariant = Color(0xFFA3A3A3),
    surfaceTint = Color(0xFFE5E5E5),
    surfaceDim = Color(0xFF0A0A0A), surfaceBright = Color(0xFF262626),
    surfaceContainerLowest = Color(0xFF0A0A0A), surfaceContainerLow = Color(0xFF171717),
    surfaceContainer = Color(0xFF171717), surfaceContainerHigh = Color(0xFF262626),
    surfaceContainerHighest = Color(0xFF404040),
    outline = Color(0xFF404040), outlineVariant = Color(0xFF262626),
    inverseSurface = Color(0xFFFAFAFA), inverseOnSurface = Color(0xFF171717),
    error = Color(0xFFF87171), onError = Color(0xFF450A0A),
    errorContainer = Color(0xFF7F1D1D), onErrorContainer = Color(0xFFFEE2E2),
    scrim = Color.Black,
)

/** Shared by the native server screens, error page and update dialogs. */
@Composable
fun ShellTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        content = content,
    )
}

package app.codeg.android.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.codeg.android.core.designsystem.theme.CodegTheme

/**
 * The app backdrop: the flat `--background` of the active web theme preset,
 * matching codeg's web UI (no decorative glows).
 */
@Composable
fun CodegBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CodegTheme.colors.bg),
        content = { content() },
    )
}

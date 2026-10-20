package app.codeg.android.shell.display

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.codeg.android.shell.ShellFeature
import app.codeg.android.shell.ShellHost

/** S2 — insets, keyboard, system bars, dark mode. SKELETON STUB. */
class DisplayFeature : ShellFeature {
    @Composable
    override fun webViewModifier(host: ShellHost): Modifier = Modifier.systemBarsPadding().imePadding()
}

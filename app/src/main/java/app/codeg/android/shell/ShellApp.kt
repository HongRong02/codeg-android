package app.codeg.android.shell

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import app.codeg.android.shell.display.ShellTheme

/**
 * Root UI: no server yet → server setup; server manager open → manager; otherwise the
 * WebView with every feature's overlay on top.
 */
@Composable
fun ShellApp(host: ShellActivityHost) {
    val features = host.features
    val server by features.server.current.collectAsState()
    val managerOpen by host.serverManagerOpen.collectAsState()
    val generation by host.webViewGeneration.collectAsState()
    ShellTheme {
        when {
            server == null -> features.server.SetupScreen(host)
            managerOpen -> features.server.ManagerScreen(host, onClose = { host.closeServerManager() })
            else -> {
                var modifier: Modifier = Modifier.fillMaxSize()
                features.all.forEach { modifier = modifier.then(it.webViewModifier(host)) }
                Box(Modifier.fillMaxSize()) {
                    // A new generation (renderer crash, server switch) builds a brand-new WebView.
                    key(generation, server) {
                        AndroidView(
                            modifier = modifier,
                            factory = { context -> host.createWebView(context) },
                            onRelease = { host.releaseWebView(it) },
                        )
                    }
                    features.all.forEach { it.Overlay(host) }
                }
            }
        }
    }
}

internal fun WebView.applyShellLayout() {
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
}

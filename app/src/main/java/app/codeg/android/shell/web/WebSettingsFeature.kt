package app.codeg.android.shell.web

import android.annotation.SuppressLint
import android.webkit.WebView
import app.codeg.android.shell.ShellFeature
import app.codeg.android.shell.ShellHost

/** S8 — WebView settings, cookies, JS bridges, permissions. SKELETON STUB: the minimum to run the web app. */
class WebSettingsFeature : ShellFeature {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onWebViewCreated(host: ShellHost, webView: WebView) {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
    }
}

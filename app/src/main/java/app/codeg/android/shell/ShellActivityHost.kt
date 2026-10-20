package app.codeg.android.shell

import android.content.Context
import android.content.Intent
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** The single [ShellHost] implementation, owned by MainActivity. */
class ShellActivityHost(
    override val activity: ComponentActivity,
    val features: ShellFeatures = ShellFeatures(),
) : ShellHost {
    override val scope: CoroutineScope get() = activity.lifecycleScope

    override var webView: WebView? = null
        private set

    override val currentServerUrl: String? get() = features.server.current.value

    private val managerOpen = MutableStateFlow(false)
    val serverManagerOpen: StateFlow<Boolean> = managerOpen.asStateFlow()

    private val generation = MutableStateFlow(0)
    val webViewGeneration: StateFlow<Int> = generation.asStateFlow()

    internal fun createWebView(context: Context): WebView {
        val view = WebView(context)
        view.applyShellLayout()
        view.webViewClient = ShellWebViewClient(this, features.all)
        view.webChromeClient = ShellChromeClient(this, features.all)
        webView = view
        features.all.forEach { it.onWebViewCreated(this, view) }
        currentServerUrl?.let { view.loadUrl(it) }
        return view
    }

    internal fun releaseWebView(view: WebView) {
        if (webView === view) webView = null
        view.stopLoading()
        view.destroy()
    }

    override fun loadUrl(url: String) {
        webView?.loadUrl(url)
    }

    override fun reload() {
        val view = webView ?: return
        if (view.url.isNullOrEmpty()) currentServerUrl?.let { view.loadUrl(it) } else view.reload()
    }

    override fun recreateWebView() {
        generation.value += 1
    }

    override fun openServerManager() {
        managerOpen.value = true
    }

    fun closeServerManager() {
        managerOpen.value = false
    }

    fun onCreate() = features.all.forEach { it.onCreate(this) }
    fun onResume() = features.all.forEach { it.onResume(this) }
    fun onPause() = features.all.forEach { it.onPause(this) }
    fun onIntent(intent: Intent) = features.all.any { it.onIntent(this, intent) }

    /** Back: the open server manager first, then features; false = let the activity finish. */
    fun onBack(): Boolean {
        if (managerOpen.value && currentServerUrl != null) {
            closeServerManager()
            return true
        }
        return features.all.any { it.onBack(this) }
    }
}

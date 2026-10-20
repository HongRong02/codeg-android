package app.codeg.android.shell.error

import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import app.codeg.android.R
import app.codeg.android.shell.ShellFeature
import app.codeg.android.shell.ShellHost
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** Owns only main-document failures; API and asset failures remain with the website. */
class ErrorFeature : ShellFeature {
    private var reason by mutableStateOf<Int?>(null)
    private var status by mutableIntStateOf(0)
    private var progress by mutableIntStateOf(100)
    private var loading by mutableStateOf(false)
    private var failedLoad = false
    private var retryJob: Job? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun onCreate(host: ShellHost) {
        host.activity.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) stopRecovery(host)
        })
    }

    override fun onWebViewCreated(host: ShellHost, webView: WebView) {
        clearError(host)
        loading = false
        progress = 100
    }

    override fun onPageStarted(host: ShellHost, view: WebView, url: String, favicon: Bitmap?) {
        if (view !== host.webView) return
        failedLoad = false
        loading = true
        progress = 0
    }

    override fun onProgressChanged(host: ShellHost, view: WebView, progress: Int) {
        if (view !== host.webView || !loading) return
        this.progress = progress.coerceIn(0, 100)
        // Completion is decided by onPageFinished: Chromium also reports 100 for errors.
    }

    override fun onPageFinished(host: ShellHost, view: WebView, url: String) {
        if (view !== host.webView) return
        loading = false
        progress = 100
        if (!failedLoad && url != "chrome-error://chromewebdata/") clearError(host)
    }

    override fun onReceivedError(host: ShellHost, view: WebView, request: WebResourceRequest, error: WebResourceError) {
        if (view !== host.webView || !request.isForMainFrame) return
        showError(host, when (error.errorCode) {
            WebViewClient.ERROR_HOST_LOOKUP -> R.string.shell_error_dns
            WebViewClient.ERROR_TIMEOUT -> R.string.shell_error_timeout
            WebViewClient.ERROR_FAILED_SSL_HANDSHAKE -> R.string.shell_error_ssl
            else -> R.string.shell_error_connection
        })
    }

    override fun onReceivedHttpError(host: ShellHost, view: WebView, request: WebResourceRequest, response: WebResourceResponse) {
        if (view !== host.webView || !request.isForMainFrame || response.statusCode !in 500..599) return
        status = response.statusCode
        showError(host, R.string.shell_error_http)
    }

    private fun showError(host: ShellHost, message: Int) {
        failedLoad = true
        loading = false
        val firstFailure = reason == null
        reason = message
        if (!firstFailure) return // Failed retries must not reset the six-attempt budget.
        retryJob = host.scope.launch {
            repeat(6) {
                delay(10_000)
                if (reason == null) return@launch
                host.reload()
            }
        }
        val manager = host.activity.getSystemService(ConnectivityManager::class.java)
        val callback = object : ConnectivityManager.NetworkCallback() {
            private var recovered = false
            private var wasOffline = manager.activeNetwork?.let {
                manager.getNetworkCapabilities(it)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } != true
            override fun onLost(network: Network) { wasOffline = true }
            override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
                // Initial registration also delivers the current network. Only retry a recovery.
                if (!wasOffline || recovered || !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) return
                recovered = true
                host.scope.launch { if (reason != null) host.reload() }
            }
        }
        try {
            manager.registerDefaultNetworkCallback(callback)
            networkCallback = callback
        } catch (_: SecurityException) {
            // Periodic and manual retries remain available if network observation is restricted.
        }
    }

    private fun stopRecovery(host: ShellHost) {
        retryJob?.cancel()
        retryJob = null
        networkCallback?.let {
            host.activity.getSystemService(ConnectivityManager::class.java).unregisterNetworkCallback(it)
        }
        networkCallback = null
    }

    private fun clearError(host: ShellHost) {
        reason = null
        stopRecovery(host)
    }

    override fun onResume(host: ShellHost) {
        if (reason != null) host.reload()
    }

    override fun onRenderProcessGone(host: ShellHost, view: WebView, detail: RenderProcessGoneDetail): Boolean {
        if (view === host.webView) {
            clearError(host)
            host.recreateWebView()
            Toast.makeText(host.activity, R.string.shell_error_reloaded, Toast.LENGTH_SHORT).show()
        }
        return true
    }

    @Composable
    override fun Overlay(host: ShellHost) {
        Box(Modifier.fillMaxSize().systemBarsPadding()) {
            reason?.let { message ->
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(
                        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("⚠", style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(24.dp))
                        Text(stringResource(R.string.shell_error_title), style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(12.dp))
                        Text(if (message == R.string.shell_error_http) stringResource(message, status) else stringResource(message))
                        Spacer(Modifier.height(12.dp))
                        Text(host.currentServerUrl.orEmpty(), style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(28.dp))
                        Button(onClick = { host.reload() }) { Text(stringResource(R.string.shell_error_retry)) }
                        TextButton(onClick = { host.openServerManager() }) { Text(stringResource(R.string.shell_error_switch)) }
                    }
                }
            }
            AnimatedVisibility(visible = loading, exit = fadeOut(), modifier = Modifier.align(Alignment.TopCenter)) {
                LinearProgressIndicator(progress = { progress / 100f }, modifier = Modifier.fillMaxWidth().height(2.dp))
            }
        }
    }
}

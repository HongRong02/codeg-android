package app.codeg.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.codeg.android.shell.ShellActivityHost
import app.codeg.android.shell.ShellApp
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single-activity WebView shell: shows the codeg web UI served by the chosen server.
 * All behaviour lives in `shell/` features; this class only forwards lifecycle events.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var host: ShellActivityHost

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        host = ShellActivityHost(this)
        host.onCreate()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!host.onBack()) {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        })
        setContent { ShellApp(host) }
        intent?.let { host.onIntent(it) }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        host.onIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        host.onResume()
    }

    override fun onPause() {
        host.onPause()
        super.onPause()
    }
}

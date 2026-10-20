package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme

/** Centered web empty state with optional action. Caller icons remain source compatible. */
@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val colors = CodegTheme.colors.web
    Box(modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
        Column(
            Modifier.widthIn(max = 320.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(Modifier.size(48.dp).background(colors.muted, CodegShapes.small), contentAlignment = Alignment.Center) {
                // TODO(T51): Lucide.Inbox (caller-supplied icons retain their page semantics).
                Icon(icon, contentDescription = null, tint = colors.mutedForeground, modifier = Modifier.size(24.dp))
            }
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = colors.foreground, textAlign = TextAlign.Center)
            Text(message, fontSize = 12.sp, color = colors.mutedForeground, textAlign = TextAlign.Center)
            if (actionLabel != null && onAction != null) {
                Button(
                    onClick = onAction,
                    shape = CodegShapes.extraSmall,
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary, contentColor = colors.primaryForeground),
                ) { Text(actionLabel, fontSize = 14.sp, fontWeight = FontWeight.Medium) }
            }
        }
    }
}

/** Compact muted spinner and label, matching Loader2 animate-spin on the web. */
@Composable
fun LoadingView(message: String, modifier: Modifier = Modifier) {
    val colors = CodegTheme.colors.web
    Row(
        modifier = modifier.fillMaxWidth().padding(32.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // TODO(T51): Lucide.Loader2; keep native indeterminate animation until icon migration.
        CircularProgressIndicator(color = colors.mutedForeground, strokeWidth = 2.dp, modifier = Modifier.size(16.dp))
        Text(message, modifier = Modifier.weight(1f, fill = false), fontSize = 14.sp, color = colors.mutedForeground)
    }
}

/** Destructive bordered error; retry may be hidden for non-recoverable failures. */
@Composable
fun InlineError(
    icon: ImageVector,
    title: String,
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    retryLabel: String = stringResource(R.string.ds_states_retry),
    showRetry: Boolean = true,
) {
    val colors = CodegTheme.colors.web
    Box(modifier.fillMaxWidth().padding(28.dp), contentAlignment = Alignment.Center) {
        Column(
            Modifier.widthIn(max = 340.dp).border(1.dp, colors.destructive, CodegShapes.extraSmall).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // TODO(T51): Lucide.CircleAlert (caller-supplied icons retain their page semantics).
            Icon(icon, contentDescription = null, tint = colors.destructive, modifier = Modifier.size(24.dp))
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = colors.destructive, textAlign = TextAlign.Center)
            Text(message, fontSize = 12.sp, color = colors.destructive, textAlign = TextAlign.Center)
            if (showRetry) {
                TextButton(
                    onClick = onRetry,
                    shape = CodegShapes.extraSmall,
                    colors = ButtonDefaults.textButtonColors(contentColor = colors.destructive),
                ) { Text(retryLabel, fontSize = 14.sp) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun T54Gallery() {
    val title = stringResource(R.string.ds_states_gallery_title)
    val message = stringResource(R.string.ds_states_gallery_message)
    Column(Modifier.background(CodegTheme.colors.web.background).verticalScroll(rememberScrollState())) {
        CodegTopBar(title = title, subtitle = message, navigationIcon = {
            CodegIconButton(onClick = {}) {
                // TODO(T51): Lucide.X
                Icon(Icons.Rounded.Close, stringResource(R.string.ds_states_close))
            }
        }, actions = {
            CodegIconButton(onClick = {}, enabled = false, visualSize = 32.dp) {
                // TODO(T51): Lucide.Info
                Icon(Icons.Rounded.Info, title)
            }
        })
        CodegTopBar(title = title, showDivider = false)
        EmptyState(Icons.Rounded.Info, title, message)
        EmptyState(Icons.Rounded.Info, title, message, actionLabel = stringResource(R.string.ds_states_retry), onAction = {})
        LoadingView(stringResource(R.string.ds_states_loading))
        InlineError(Icons.Rounded.Info, title, message, onRetry = {})
        InlineError(Icons.Rounded.Info, title, message, onRetry = {}, showRetry = false)
    }
}

@Preview(name = "T54 Light", showBackground = true, heightDp = 1100)
@Composable
private fun T54LightPreview() {
    CodegTheme(themeMode = ThemeMode.LIGHT) { T54Gallery() }
}

@Preview(name = "T54 Dark", showBackground = true, heightDp = 1100)
@Composable
private fun T54DarkPreview() {
    CodegTheme(themeMode = ThemeMode.DARK) { T54Gallery() }
}

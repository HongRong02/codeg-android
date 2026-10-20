package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.withOpacity

/** A bounded, scrollable dialog. Callers control visibility through [onDismiss]. */
@Composable
fun CodegDialog(
    title: String,
    description: String? = null,
    onDismiss: () -> Unit,
    footer: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        // Own the scrim so opacity is identical across Android versions.
        val view = androidx.compose.ui.platform.LocalView.current
        SideEffect {
            (view.parent as? androidx.compose.ui.window.DialogWindowProvider)?.window?.setDimAmount(0f)
        }
        Box(
            Modifier.fillMaxSize().background(Color.Black.withOpacity(0.5f))
                .clickable(remember { MutableInteractionSource() }, indication = null, onClick = onDismiss)
                .safeDrawingPadding().imePadding().padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth()
                    .clickable(remember { MutableInteractionSource() }, indication = null) {},
                shape = CodegShapes.small,
                color = CodegTheme.colors.web.background,
                contentColor = CodegTheme.colors.web.foreground,
                border = androidx.compose.foundation.BorderStroke(1.dp, CodegTheme.colors.web.border),
            ) {
                Column(Modifier.verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    T44PanelHeading(title, description)
                    content()
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                        verticalAlignment = Alignment.CenterVertically, content = footer)
                }
            }
        }
    }
}

@Composable
internal fun T44PanelHeading(title: String, description: String?) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, Modifier.semantics { heading() }, color = CodegTheme.colors.web.foreground, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        description?.let { Text(it, color = CodegTheme.colors.web.mutedForeground, fontSize = 14.sp) }
    }
}

/** Confirmation always displays the exact target; only the confirm button invokes [onConfirm]. */
@Composable
fun CodegConfirmDialog(
    title: String,
    message: String,
    target: String,
    confirmLabel: String,
    destructive: Boolean = false,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmEnabled: Boolean = true,
    busy: Boolean = false,
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    val web = CodegTheme.colors.web
    CodegDialog(title, message, { if (!busy) onDismiss() }, footer = {
        CodegButton(onClick = onDismiss, enabled = !busy, variant = CodegButtonVariant.Outline) {
            Text(stringResource(R.string.ds_dialogs_cancel))
        }
        CodegButton(onClick = onConfirm, enabled = confirmEnabled && !busy, loading = busy,
            variant = if (destructive) CodegButtonVariant.Destructive else CodegButtonVariant.Default) { Text(confirmLabel) }
    }) {
        Text(target, color = web.foreground, fontWeight = FontWeight.SemiBold)
        content?.invoke(this)
    }
}

/** Interactive entry point for the second-stage component preview page. */
@Composable
internal fun T44Gallery() {
    var panel by remember { mutableStateOf<String?>(null) }
    val title = stringResource(R.string.ds_dialogs_title)
    val description = stringResource(R.string.ds_dialogs_description)
    val confirm = stringResource(R.string.ds_dialogs_confirm)
    Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        T44CardsGallery()
        listOf("dialog" to R.string.ds_dialogs_dialog, "confirm" to R.string.ds_dialogs_confirm,
            "destructive" to R.string.ds_dialogs_destructive, "sheet" to R.string.ds_dialogs_sheet,
            "busy" to R.string.ds_dialogs_confirm, "disabled" to R.string.ds_dialogs_confirm,
            "full" to R.string.ds_dialogs_full).forEach { (key, label) ->
            TextButton(onClick = { panel = key }) { Text(stringResource(label)) }
        }
    }
    val dismiss = { panel = null }
    when (panel) {
        "dialog" -> CodegDialog(title, description, dismiss, footer = {
            TextButton(onClick = dismiss) { Text(confirm) }
        }) { Text(stringResource(R.string.ds_dialogs_message)) }
        "confirm", "destructive" -> CodegConfirmDialog(title, description,
            stringResource(R.string.ds_dialogs_target), confirm, panel == "destructive", dismiss, dismiss)
        "busy", "disabled" -> CodegConfirmDialog(title, description,
            stringResource(R.string.ds_dialogs_target), confirm, onConfirm = dismiss, onDismiss = dismiss,
            confirmEnabled = false, busy = panel == "busy", content = { CodegTextField("", {}, title) })
        "sheet" -> CodegSheet(title, description, dismiss, footer = {
            TextButton(onClick = dismiss) { Text(confirm) }
        }) { T44SheetSampleContent() }
        "full" -> CodegFullScreenSheet(title, dismiss, actions = {
            TextButton(onClick = dismiss) { Text(confirm) }
        }) { T44SheetSampleContent() }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) {
    CodegDialog(stringResource(R.string.ds_dialogs_title), stringResource(R.string.ds_dialogs_description), {})
} }

@Preview(showBackground = true)
@Composable
private fun DialogDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) {
    CodegDialog(stringResource(R.string.ds_dialogs_title), stringResource(R.string.ds_dialogs_description), {})
} }

@Preview
@Composable
private fun ConfirmLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T44ConfirmPreview() } }
@Preview
@Composable
private fun ConfirmDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T44ConfirmPreview() } }
@Composable
private fun T44ConfirmPreview() {
    CodegConfirmDialog(stringResource(R.string.ds_dialogs_title), stringResource(R.string.ds_dialogs_message),
        stringResource(R.string.ds_dialogs_target), stringResource(R.string.ds_dialogs_confirm), true, {}, {})
}

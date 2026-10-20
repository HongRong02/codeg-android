package app.codeg.android.core.designsystem.component

import app.codeg.android.core.designsystem.icon.Lucide
import app.codeg.android.core.designsystem.icon.X
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.withOpacity

/** Bottom drawer with independently scrolling body and a pinned footer. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodegSheet(
    title: String,
    description: String? = null,
    onDismiss: () -> Unit,
    footer: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    val web = CodegTheme.colors.web
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = CodegShapes.extraLarge.copy(
            bottomStart = androidx.compose.foundation.shape.CornerSize(0.dp),
            bottomEnd = androidx.compose.foundation.shape.CornerSize(0.dp),
        ),
        containerColor = web.background,
        contentColor = web.foreground,
        scrimColor = Color.Black.withOpacity(0.5f),
        // Insets are owned here rather than by the body, so the footer clears both keyboard and navigation.
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) },
        dragHandle = {
            Box(Modifier.padding(top = 16.dp, bottom = 8.dp).size(width = 100.dp, height = 8.dp)
                .background(web.muted, RoundedCornerShape(50)))
        },
    ) {
        CodegModalToaster {
            Column(Modifier.fillMaxWidth().imePadding().navigationBarsPadding()) {
                Box(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                    T44PanelHeading(title, description)
                }
                Column(
                    Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState()).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp), content = content,
                )
                Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp), content = footer)
            }
        }
    }
}

/** Full-window panel with a persistent close/title/action bar. */
@Composable
fun CodegFullScreenSheet(
    title: String,
    onDismiss: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(
        usePlatformDefaultWidth = false, decorFitsSystemWindows = false,
    )) {
        CodegModalToaster {
            Surface(Modifier.fillMaxSize(), color = CodegTheme.colors.web.background, contentColor = CodegTheme.colors.web.foreground) {
                Column(Modifier.safeDrawingPadding().imePadding()) {
                    Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onDismiss) {
                            Icon(Lucide.X, contentDescription = stringResource(R.string.ds_dialogs_close))
                        }
                        Box(Modifier.weight(1f).padding(horizontal = 8.dp)) { T44PanelHeading(title, null) }
                        actions()
                    }
                    Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()).padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp), content = content)
                }
            }
        }
    }
}

@Composable
internal fun T44SheetSampleContent() {
    var value by remember { mutableStateOf("") }
    repeat(8) { Text(stringResource(R.string.ds_dialogs_message)) }
    OutlinedTextField(value, onValueChange = { value = it }, label = { Text(stringResource(R.string.ds_dialogs_input)) })
}

@Preview
@Composable
private fun SheetLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T44SheetPreview() } }
@Preview
@Composable
private fun SheetDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T44SheetPreview() } }
@Composable
private fun T44SheetPreview() {
    CodegSheet(stringResource(R.string.ds_dialogs_title), stringResource(R.string.ds_dialogs_description), {}, footer = {
        Text(stringResource(R.string.ds_dialogs_confirm))
    }) { T44SheetSampleContent() }
}

@Preview
@Composable
private fun FullSheetLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T44FullSheetPreview() } }
@Preview
@Composable
private fun FullSheetDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T44FullSheetPreview() } }
@Composable
private fun T44FullSheetPreview() {
    CodegFullScreenSheet(stringResource(R.string.ds_dialogs_title), {}, actions = {
        Text(stringResource(R.string.ds_dialogs_confirm))
    }) { T44SheetSampleContent() }
}

package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.withOpacity
import androidx.compose.ui.tooling.preview.Preview
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegTheme

@Composable
fun CodegInputGroup(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    placeholder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val web = CodegTheme.colors.web
    var focused by remember { mutableStateOf(false) }
    val stroke = if (isError) web.destructive else if (focused) web.ring else web.input
    val ring = if (isError) web.destructive.withOpacity(if (CodegTheme.colors.isDark) .4f else .2f) else web.ring.withOpacity(.5f)
    val shape = CodegShapes.extraLarge // rounded-4xl = 26dp
    val outline = if (focused || isError) Modifier.drawBehind {
        val width = 3.dp.toPx()
        drawRoundRect(ring, topLeft = Offset(-width / 2, -width / 2),
            size = Size(size.width + width, size.height + width),
            cornerRadius = CornerRadius(26.dp.toPx()), style = Stroke(width))
    } else Modifier
    Row(modifier.then(outline)
        .heightIn(min = 36.dp).fillMaxWidth().background(web.input.withOpacity(.3f), shape)
        .border(1.dp, stroke, shape).padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        CompositionLocalProvider(LocalContentColor provides web.mutedForeground) {
            leading?.invoke()
            BasicTextField(value, onValueChange, Modifier.weight(1f).onFocusChanged { focused = it.isFocused }
                .padding(vertical = 8.dp), enabled = enabled, readOnly = readOnly, singleLine = true,
                textStyle = TextStyle(color = web.foreground.withOpacity(if (enabled) 1f else .5f), fontSize = 14.sp),
                cursorBrush = SolidColor(web.foreground), keyboardOptions = keyboardOptions, keyboardActions = keyboardActions,
                decorationBox = { inner ->
                    Box { if (value.isEmpty()) Text(placeholder, color = web.mutedForeground, fontSize = 14.sp, maxLines = 1); inner() }
                })
            trailing?.invoke()
        }
    }
}
@Composable internal fun T53InputGroupGallery() {
    var text by remember { mutableStateOf("") }
    Column(Modifier.padding(6.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CodegInputGroup(text, { text = it }, placeholder = stringResource(R.string.ds_misc_search),
            leading = {
                // TODO(T51): Lucide.Search
                Icon(Icons.Default.Search, null, Modifier.size(16.dp))
            }, trailing = {
                // TODO(T51): Lucide.X
                IconButton({ text = "" }, Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, stringResource(R.string.ds_misc_clear), Modifier.size(14.dp))
                }
            })
        CodegInputGroup(text, {}, isError = true, readOnly = true)
        CodegInputGroup(text, {}, enabled = false, placeholder = stringResource(R.string.ds_misc_search))
    }
}
@Preview @Composable private fun InputGroupLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T53InputGroupGallery() } }
@Preview @Composable private fun InputGroupDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T53InputGroupGallery() } }

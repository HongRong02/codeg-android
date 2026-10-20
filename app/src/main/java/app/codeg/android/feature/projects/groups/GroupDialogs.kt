package app.codeg.android.feature.projects.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.WebThemePreset
import app.codeg.android.core.designsystem.theme.colorFromHex
import app.codeg.android.core.model.FolderDetail
import app.codeg.android.core.model.FolderGroup

@Composable
fun folderTint(value: String): androidx.compose.ui.graphics.Color {
    val colors = CodegTheme.colors
    if (value.isBlank() || value == "inherit") return colors.accent
    colorFromHex(value)?.let { return it }
    val preset = WebThemePreset.from(value)
    return (if (colors.isDark) preset.dark else preset.light).primary
}

@Composable
fun GroupTextDialog(title: String, initial: String, allowEmpty: Boolean = false, onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var value by remember { mutableStateOf(initial) }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(title) }, text = {
        OutlinedTextField(value = value, onValueChange = { value = it }, singleLine = true, label = { Text(title) })
    }, confirmButton = {
        TextButton(enabled = allowEmpty || value.isNotBlank(), onClick = { onSave(value.trim()); onDismiss() }) {
            Text(stringResource(R.string.fg_save))
        }
    }, dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.fg_cancel)) } })
}

@Composable
fun FolderEditDialog(folder: FolderDetail, groups: List<FolderGroup>, onDismiss: () -> Unit,
    onAlias: (String) -> Unit, onColor: (String) -> Unit, onGroup: (Int?) -> Unit) {
    var page by remember { mutableStateOf(0) }
    if (page == 1) {
        GroupTextDialog(stringResource(R.string.fg_alias), folder.alias.orEmpty(), true, onDismiss, onAlias)
        return
    }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(folderDisplayName(folder)) }, text = {
        LazyColumn {
            if (page == 0) {
                item { TextButton(onClick = { page = 1 }) { Text(stringResource(R.string.fg_alias)) } }
                item { TextButton(onClick = { page = 2 }) { Text(stringResource(R.string.fg_color)) } }
                item { TextButton(onClick = { page = 3 }) { Text(stringResource(R.string.fg_move)) } }
            } else if (page == 2) {
                val presets = listOf("inherit", "neutral", "zinc", "slate", "stone", "gray", "red", "rose", "orange", "green", "blue", "yellow", "violet")
                val labels = listOf(R.string.fg_inherit, R.string.fg_neutral, R.string.fg_zinc, R.string.fg_slate, R.string.fg_stone, R.string.fg_gray, R.string.fg_red, R.string.fg_rose, R.string.fg_orange, R.string.fg_green, R.string.fg_blue, R.string.fg_yellow, R.string.fg_violet)
                presets.forEachIndexed { index, color ->
                    item {
                        TextButton(onClick = { onColor(color); onDismiss() }) {
                            Box(Modifier.size(12.dp).background(folderTint(color), CircleShape))
                            Spacer(Modifier.width(12.dp))
                            Text(stringResource(labels[index]))
                        }
                    }
                }
            } else {
                item { TextButton(onClick = { onGroup(null); onDismiss() }) { Text(stringResource(R.string.fg_ungrouped)) } }
                groups.forEach { group -> item {
                    TextButton(onClick = { onGroup(group.id); onDismiss() }) { Text(group.name) }
                } }
            }
        }
    }, confirmButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.fg_cancel)) } })
}

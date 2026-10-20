package app.codeg.android.feature.projects.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import app.codeg.android.core.designsystem.component.*
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
    CodegDialog(title = title, onDismiss = onDismiss, footer = {
        CodegButton(onClick = onDismiss, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.fg_cancel)) }
        CodegButton(enabled = allowEmpty || value.isNotBlank(), onClick = { onSave(value.trim()); onDismiss() }) {
            Text(stringResource(R.string.fg_save))
        }
    }) {
        CodegTextField(value = value, onValueChange = { value = it }, label = title)
    }
}

@Composable
fun FolderEditDialog(folder: FolderDetail, groups: List<FolderGroup>, onDismiss: () -> Unit,
    onAlias: (String) -> Unit, onColor: (String) -> Unit, onGroup: (Int?) -> Unit) {
    var page by remember { mutableStateOf(0) }
    if (page == 1) {
        GroupTextDialog(stringResource(R.string.fg_alias), folder.alias.orEmpty(), true, onDismiss, onAlias)
        return
    }
    CodegDialog(title = folderDisplayName(folder), onDismiss = onDismiss, footer = {
        CodegButton(onClick = onDismiss, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.fg_cancel)) }
    }) {
        if (page == 0) {
            CodegListRow(title = stringResource(R.string.fg_alias), onClick = { page = 1 })
            CodegListRow(title = stringResource(R.string.fg_color), onClick = { page = 2 })
            CodegListRow(title = stringResource(R.string.fg_move), onClick = { page = 3 })
        } else if (page == 2) {
            val presets = listOf("inherit", "neutral", "zinc", "slate", "stone", "gray", "red", "rose", "orange", "green", "blue", "yellow", "violet")
            val labels = listOf(R.string.fg_inherit, R.string.fg_neutral, R.string.fg_zinc, R.string.fg_slate, R.string.fg_stone, R.string.fg_gray, R.string.fg_red, R.string.fg_rose, R.string.fg_orange, R.string.fg_green, R.string.fg_blue, R.string.fg_yellow, R.string.fg_violet)
            presets.forEachIndexed { index, color ->
                CodegListRow(title = stringResource(labels[index]), onClick = { onColor(color); onDismiss() },
                    leading = { Box(Modifier.size(12.dp).background(folderTint(color), CircleShape)) })
            }
        } else {
            val ungrouped = stringResource(R.string.fg_ungrouped)
            val options = listOf(null to ungrouped) + groups.map { it.id to it.name }
            CodegSelect<Pair<Int?, String>>(label = stringResource(R.string.fg_move),
                selected = options.firstOrNull { it.first == folder.groupId }, options = options,
                optionLabel = { it.second }, key = { it.first ?: "ungrouped" },
                onSelect = { onGroup(it.first); onDismiss() })
        }
    }
}

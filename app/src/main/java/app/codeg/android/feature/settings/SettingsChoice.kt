package app.codeg.android.feature.settings

import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegDropdownMenu
import app.codeg.android.core.designsystem.component.CodegField
import app.codeg.android.core.designsystem.component.CodegMenuItem
import app.codeg.android.core.designsystem.component.CodegSelect
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.material3.Text

/** Keeps server option IDs distinct when display names are repeated. */
@Composable
internal fun SettingsChoice(label: String, value: String, choices: List<Pair<String, String>>, enabled: Boolean = true, onPick: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val names = choices.map { it.second }
    if (names.distinct().size == names.size) {
        CodegSelect(label = label, value = choices.firstOrNull { it.first == value }?.second ?: value,
            options = names, enabled = enabled && choices.isNotEmpty(), onSelect = { name ->
                choices.firstOrNull { it.second == name }?.let { onPick(it.first) }
        })
        return
    }
    CodegField(label = label) {
        Box {
            CodegButton(onClick = { expanded = true }, enabled = enabled && choices.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(), variant = CodegButtonVariant.Outline) {
                    Text(choices.firstOrNull { it.first == value }?.second ?: value)
                }
            CodegDropdownMenu(expanded = expanded && enabled, onDismissRequest = { expanded = false }) {
                choices.forEach { (id, name) ->
                    CodegMenuItem(text = { Text(name) }, onClick = { expanded = false; onPick(id) })
                }
            }
        }
    }
}

package app.codeg.android.feature.settings

import androidx.compose.runtime.Composable
import app.codeg.android.core.designsystem.component.CodegSelect

/** Bind selection to IDs, including options with identical display names. */
@Composable
internal fun SettingsChoice(label: String, value: String, choices: List<Pair<String, String>>, enabled: Boolean = true, onPick: (String) -> Unit) {
    CodegSelect(
        label = label,
        selected = choices.firstOrNull { it.first == value } ?: value.takeIf { it.isNotBlank() }?.let { it to it },
        options = choices,
        optionLabel = { it.second },
        optionDescription = { it.first },
        key = { it.first },
        enabled = enabled && choices.isNotEmpty(),
        onSelect = { onPick(it.first) },
    )
}

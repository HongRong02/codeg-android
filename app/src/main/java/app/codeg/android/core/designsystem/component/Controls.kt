package app.codeg.android.core.designsystem.component

import app.codeg.android.core.designsystem.icon.*

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.PillShape
import app.codeg.android.core.designsystem.theme.withOpacity

@Composable
fun CodegSelect(label: String, value: String, options: List<String>, onSelect: (String) -> Unit,
    modifier: Modifier = Modifier, enabled: Boolean = true,
) = CodegSelect(label, value, options, { it }, onSelect, modifier, enabled)

@Composable
fun CodegCombobox(label: String, value: String, options: List<String>, onSelect: (String) -> Unit,
    modifier: Modifier = Modifier, enabled: Boolean = true,
) = CodegCombobox(label, value, options, { it }, onSelect, modifier, enabled)

@Composable
fun <T> CodegSelect(label: String, selected: T?, options: List<T>, optionLabel: (T) -> String,
    onSelect: (T) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true,
    optionDescription: ((T) -> String?)? = null, key: ((T) -> Any)? = null,
) = ChoiceControl(label, selected, options, optionLabel, onSelect, modifier, enabled, false, optionDescription, key)

@Composable
fun <T> CodegCombobox(label: String, selected: T?, options: List<T>, optionLabel: (T) -> String,
    onSelect: (T) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true,
    optionDescription: ((T) -> String?)? = null, key: ((T) -> Any)? = null,
) = ChoiceControl(label, selected, options, optionLabel, onSelect, modifier, enabled, true, optionDescription, key)

internal fun <T> choiceMatches(option: T, query: String, label: (T) -> String,
    description: ((T) -> String?)? = null,
): Boolean = label(option).contains(query, ignoreCase = true) ||
    description?.invoke(option)?.contains(query, ignoreCase = true) == true

internal fun <T> choiceSelected(option: T, selected: T?, key: ((T) -> Any)? = null): Boolean =
    selected != null && if (key != null) key(option) == key(selected) else option == selected

/** Self-contained popup: deliberately independent of the T43 menu components. */
@Composable
private fun <T> ChoiceControl(label: String, value: T?, options: List<T>, optionLabel: (T) -> String, onSelect: (T) -> Unit,
    modifier: Modifier, enabled: Boolean, searchable: Boolean,
    optionDescription: ((T) -> String?)?, optionKey: ((T) -> Any)?,
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    val web = CodegTheme.colors.web
    CodegField(label, modifier = modifier) {
        Box {
            Row(Modifier.fillMaxWidth().heightIn(min = 36.dp).alpha(if (enabled) 1f else .5f)
                .clip(PillShape).background(web.input.withOpacity(.30f)).border(1.dp, web.input, PillShape)
                .clickable(enabled = enabled, role = Role.DropdownList) { query = ""; expanded = true }
                .padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(value?.let(optionLabel)?.ifEmpty { label } ?: label, Modifier.weight(1f), color = web.foreground, fontSize = 14.sp)
                Icon(Lucide.ChevronDown, null, Modifier.size(16.dp), tint = web.mutedForeground)
            }
            DropdownMenu(expanded = expanded && enabled, onDismissRequest = { expanded = false },
                shape = CodegShapes.medium, containerColor = web.popover,
                border = androidx.compose.foundation.BorderStroke(1.dp, web.border),
                modifier = Modifier.widthIn(min = 200.dp, max = 360.dp).heightIn(max = 320.dp),
            ) {
                if (searchable) CodegTextField(query, { query = it }, stringResource(R.string.ds_controls_search), Modifier.padding(4.dp))
                val matches = options.filter { choiceMatches(it, query, optionLabel, optionDescription) }
                if (matches.isEmpty()) Text(stringResource(R.string.ds_controls_empty), Modifier.padding(12.dp), color = web.mutedForeground)
                matches.forEach { option ->
                    key(optionKey?.invoke(option) ?: option) {
                        val isSelected = choiceSelected(option, value, optionKey)
                        Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp).clip(CodegShapes.small)
                            .background(if (isSelected) web.accent else androidx.compose.ui.graphics.Color.Transparent)
                            .selectable(isSelected, role = Role.RadioButton) { onSelect(option); expanded = false }
                            .padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(optionLabel(option), color = web.popoverForeground, fontSize = 14.sp)
                                optionDescription?.invoke(option)?.let { Text(it, color = web.mutedForeground, fontSize = 12.sp) }
                            }
                            if (isSelected) Icon(Lucide.Check, null, Modifier.size(16.dp), tint = web.popoverForeground)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CodegRadioGroup(options: List<String>, value: String, onSelect: (String) -> Unit,
    modifier: Modifier = Modifier, enabled: Boolean = true,
) {
    val web = CodegTheme.colors.web
    Column(modifier.selectableGroup().alpha(if (enabled) 1f else .5f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        options.forEach { option ->
            Row(Modifier.fillMaxWidth().selectable(option == value, enabled, Role.RadioButton) { onSelect(option) }
                .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.size(16.dp).border(1.dp, if (option == value) web.primary else web.input, CircleShape), contentAlignment = Alignment.Center) {
                    if (option == value) Box(Modifier.size(8.dp).background(web.primary, CircleShape))
                }
                Text(option, color = web.foreground, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun CodegCheckbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier, enabled: Boolean = true, label: String? = null,
) {
    val web = CodegTheme.colors.web
    val shape = RoundedCornerShape(4.dp)
    Row(modifier.alpha(if (enabled) 1f else .5f).toggleable(checked, enabled, Role.Checkbox, onCheckedChange)
        .padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(Modifier.size(16.dp).clip(shape).background(if (checked) web.primary else web.input.withOpacity(.30f))
            .border(1.dp, if (checked) web.primary else web.input, shape), contentAlignment = Alignment.Center) {
            if (checked) Icon(Lucide.Check, null, Modifier.size(14.dp), tint = web.primaryForeground)
        }
        label?.let { Text(it, color = web.foreground, fontSize = 14.sp) }
    }
}

@Composable
fun CodegSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier, enabled: Boolean = true, label: String? = null,
) {
    val web = CodegTheme.colors.web
    Row(modifier.alpha(if (enabled) 1f else .5f).toggleable(checked, enabled, Role.Switch, onCheckedChange)
        .padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(Modifier.size(32.dp, 18.4.dp).background(if (checked) web.primary else web.input, PillShape).padding(2.dp),
            contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart) {
            Box(Modifier.size(14.4.dp).background(web.background, CircleShape))
        }
        label?.let { Text(it, color = web.foreground, fontSize = 14.sp) }
    }
}

/** Interactive catalogue for the second-stage component preview screen. */
@Composable
internal fun T45Gallery() {
    val example = stringResource(R.string.ds_controls_example)
    val options = listOf(stringResource(R.string.ds_controls_first), stringResource(R.string.ds_controls_second))
    var value by remember { mutableStateOf(options.first()) }
    var text by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    Column(Modifier.background(CodegTheme.colors.web.background).verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CodegButtonVariant.entries.forEach { variant ->
            CodegButton({}, variant = variant) { Text(stringResource(when (variant) {
                CodegButtonVariant.Default -> R.string.ds_controls_default
                CodegButtonVariant.Secondary -> R.string.ds_controls_secondary
                CodegButtonVariant.Outline -> R.string.ds_controls_outline
                CodegButtonVariant.Ghost -> R.string.ds_controls_ghost
                CodegButtonVariant.Destructive -> R.string.ds_controls_destructive
                CodegButtonVariant.Link -> R.string.ds_controls_link
            })) }
        }
        CodegButtonSize.entries.forEach { size ->
            CodegButton({}, size = size) { if (size == CodegButtonSize.Icon) Icon(Lucide.Check, example, Modifier.size(16.dp)) else Text(example) }
        }
        CodegButton({}, loading = true) { Text(example) }
        CodegButton({}, enabled = false) { Text(example) }
        PrimaryButton(example, {})
        CodegTextField(text, { text = it }, example)
        CodegTextField(text, { text = it }, example, singleLine = false)
        CodegField(example, stringResource(R.string.ds_controls_description), stringResource(R.string.ds_controls_required)) {
            CodegTextField(text, { text = it }, example, isError = true, supportingText = stringResource(R.string.ds_controls_required))
        }
        CodegTextField(text, { text = it }, example, enabled = false)
        CodegTextField(text, {}, example, readOnly = true, leading = { Icon(Lucide.Check, null) }, trailing = { CodegSpinner() })
        CodegTextField(text, { text = it }, example, singleLine = false, minLines = 2, maxLines = 4,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            keyboardActions = androidx.compose.foundation.text.KeyboardActions(onDone = {}))
        val entities = listOf(1, 2)
        var entity by remember { mutableStateOf<Int?>(null) }
        CodegSelect(example, entity, entities, { example }, { entity = it }, optionDescription = { it.toString() }, key = { it })
        CodegCombobox(example, entity, entities, { example }, { entity = it }, optionDescription = { it.toString() }, key = { it })
        SecretField(text, { text = it }, example)
        CodegSelect(example, value, options, { value = it })
        CodegCombobox(example, value, options, { value = it })
        CodegRadioGroup(options, value, { value = it })
        CodegCheckbox(checked, { checked = it }, label = example)
        CodegSwitch(checked, { checked = it }, label = example)
        CodegCheckbox(true, {}, enabled = false, label = example)
        CodegSwitch(false, {}, enabled = false, label = example)
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "T45 Light", showBackground = true, heightDp = 1800)
@androidx.compose.ui.tooling.preview.Preview(name = "T45 Dark", showBackground = true, heightDp = 1800, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun T45Preview() { CodegTheme { T45Gallery() } }

@androidx.compose.ui.tooling.preview.Preview(name = "Select Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "Select Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SelectPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        CodegSelect(label, label, listOf(label), {})
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "Combobox Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "Combobox Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ComboboxPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        CodegCombobox(label, label, listOf(label), {})
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "RadioGroup Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "RadioGroup Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RadioGroupPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        CodegRadioGroup(listOf(label), label, {})
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "Checkbox Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "Checkbox Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CheckboxPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        CodegCheckbox(true, {}, label = label)
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "Switch Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "Switch Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SwitchPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        CodegSwitch(true, {}, label = label)
    }
}

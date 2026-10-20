package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegShapes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.PillShape
import app.codeg.android.core.designsystem.theme.withOpacity

/**
 * A labelled text field in the web `Input` look: `--input` wash and stroke,
 * `--ring` stroke when focused, pill-shaped (rounded-xl when multi-line, like
 * the web `Textarea`).
 */
@Composable
fun CodegTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    mono: Boolean = false,
    singleLine: Boolean = true,
    isError: Boolean = false,
    supportingText: String? = null,
) {
    val colors = CodegTheme.colors
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = placeholder?.let { { Text(it) } },
        singleLine = singleLine,
        isError = isError,
        supportingText = supportingText?.let { { Text(it) } },
        textStyle = if (mono) LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace) else LocalTextStyle.current,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth().heightIn(min = if (singleLine) 36.dp else 64.dp),
        shape = if (singleLine) PillShape else CodegShapes.small,
        colors = fieldColors(),
    )
}

/** Token entry: masked by default with a show/hide toggle, monospaced (iOS `SecretField`). */
@Composable
fun SecretField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace),
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    imageVector = if (visible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                    contentDescription = stringResource(if (visible) R.string.ds_controls_hide else R.string.ds_controls_show),
                    tint = CodegTheme.colors.textTertiary,
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
        shape = PillShape,
        colors = fieldColors(),
    )
}

@Composable
private fun fieldColors(): TextFieldColors {
    val colors = CodegTheme.colors
    val web = colors.web
    return OutlinedTextFieldDefaults.colors(
        focusedContainerColor = web.input.withOpacity(0.30f),
        unfocusedContainerColor = web.input.withOpacity(0.30f),
        focusedBorderColor = web.ring,
        unfocusedBorderColor = web.input,
        focusedLabelColor = colors.textPrimary,
        unfocusedLabelColor = colors.textSecondary,
        focusedPlaceholderColor = colors.textSecondary,
        unfocusedPlaceholderColor = colors.textSecondary,
        cursorColor = colors.textPrimary,
        focusedTextColor = colors.textPrimary,
        unfocusedTextColor = colors.textPrimary,
        errorBorderColor = colors.danger,
    )
}

@Composable
fun CodegField(
    label: String,
    description: String? = null,
    error: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val web = CodegTheme.colors.web
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(label, color = if (error != null) web.destructive else web.foreground, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        content()
        description?.let { Text(it, color = web.mutedForeground, fontSize = 14.sp) }
        error?.let { Text(it, color = web.destructive, fontSize = 14.sp) }
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "TextField Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "TextField Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TextFieldPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        CodegTextField("", {}, label, singleLine = false, isError = true, supportingText = label)
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "Field Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "Field Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FieldPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        CodegField(label, label, label) { CodegTextField("", {}, label) }
    }
}

@androidx.compose.ui.tooling.preview.Preview(name = "SecretField Light", showBackground = true)
@androidx.compose.ui.tooling.preview.Preview(name = "SecretField Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SecretFieldPreview() {
    CodegTheme {
        val label = androidx.compose.ui.res.stringResource(app.codeg.android.R.string.ds_controls_example)
        SecretField("", {}, label)
    }
}

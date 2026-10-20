package app.codeg.android.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.component.webSurface

@Composable
fun GeneralContent(viewModel: GeneralViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Section(stringResource(R.string.general_conversation_tools)) {
            ToggleRow(stringResource(R.string.general_live_feedback_title), stringResource(R.string.general_live_feedback_subtitle), ui.feedbackEnabled, viewModel::setFeedback)
            HorizontalDivider(color = colors.hairline, modifier = Modifier.padding(horizontal = 16.dp))
            ToggleRow(stringResource(R.string.general_ask_questions_title), stringResource(R.string.general_ask_questions_subtitle), ui.questionEnabled, viewModel::setQuestion)
        }
        Section(stringResource(R.string.general_multi_agent_delegation)) {
            ToggleRow(stringResource(R.string.general_enable_delegation_title), stringResource(R.string.general_enable_delegation_subtitle), ui.delegationEnabled, viewModel::setDelegationEnabled)
            if (ui.delegationEnabled) {
                HorizontalDivider(color = colors.hairline, modifier = Modifier.padding(horizontal = 16.dp))
                ListItem(
                    headlineContent = { Text(stringResource(R.string.general_max_depth_title), fontSize = 14.sp) },
                    supportingContent = { Text(stringResource(R.string.general_max_depth_subtitle), fontSize = 12.sp, lineHeight = 18.sp) },
                    trailingContent = {
                        Stepper(ui.depthLimit, onDec = { viewModel.setDepth(ui.depthLimit - 1) }, onInc = { viewModel.setDepth(ui.depthLimit + 1) })
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = colors.textPrimary,
                        supportingColor = colors.textSecondary,
                    ),
                )
            }
        }
    }
}

@Composable
private fun Section(title: String, content: @Composable () -> Unit) {
    val colors = CodegTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = colors.textSecondary, modifier = Modifier.padding(start = 12.dp, bottom = 4.dp))
        Column(Modifier.fillMaxWidth().webSurface(CodegShapes.small)) { content() }
    }
}

@Composable
private fun ToggleRow(title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    val colors = CodegTheme.colors
    ListItem(
        headlineContent = { Text(title, fontSize = 14.sp) },
        supportingContent = { Text(subtitle, fontSize = 12.sp, lineHeight = 18.sp) },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onChange,
                colors = SwitchDefaults.colors(checkedTrackColor = colors.accent, checkedThumbColor = colors.onAccent),
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
            headlineColor = colors.textPrimary,
            supportingColor = colors.textSecondary,
        ),
        modifier = Modifier.clickable { onChange(!checked) },
    )
}

@Composable
private fun Stepper(value: Int, onDec: () -> Unit, onInc: () -> Unit) {
    val colors = CodegTheme.colors
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        StepButton(Icons.Rounded.Remove, onDec)
        Text("$value", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = colors.textPrimary, modifier = Modifier.padding(horizontal = 8.dp))
        StepButton(Icons.Rounded.Add, onInc)
    }
}

@Composable
private fun StepButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    val colors = CodegTheme.colors
    FilledTonalIconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp),
        colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = colors.web.muted,
            contentColor = colors.textPrimary,
        ),
    ) {
        Icon(icon, contentDescription = stringResource(if (icon == Icons.Rounded.Remove) R.string.settings_core_decrease else R.string.settings_core_increase), modifier = Modifier.size(18.dp))
    }
}

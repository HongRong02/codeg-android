package app.codeg.android.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.component.webSurface

@Composable
fun VersionControlContent(viewModel: VersionControlViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column(Modifier.fillMaxWidth().webSurface(CodegShapes.small).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(stringResource(R.string.settings_core_git), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = colors.textPrimary)
            Column(Modifier.fillMaxWidth().webSurface(CodegShapes.extraSmall).padding(14.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(Modifier.size(8.dp).clip(RoundedCornerShape(50)).background(if (ui.detect.installed) colors.accent else colors.danger))
                    Text(if (ui.detect.installed) stringResource(R.string.vcs_git_installed) else stringResource(R.string.vcs_git_not_found), fontSize = 14.sp, color = colors.textPrimary)
                    ui.detect.version?.let { Text(it, fontSize = 12.sp, color = colors.textSecondary) }
                }
                ui.detect.path?.let { Text(it, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = colors.textSecondary) }
            }
            CodegTextField(ui.customPath, viewModel::onPath, label = stringResource(R.string.vcs_custom_git_path), mono = true)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Pill(stringResource(R.string.vcs_test), ui.testing, viewModel::test, filled = false)
                Pill(stringResource(R.string.common_save), ui.saving, viewModel::save, filled = true)
            }
            ui.testResult?.let { r ->
                val pathLabel = stringResource(R.string.vcs_path_fallback)
                Text(
                    if (r.installed) stringResource(R.string.vcs_valid_git_at, "${r.path ?: pathLabel}${r.version?.let { " ($it)" } ?: ""}") else stringResource(R.string.vcs_no_git_at_path),
                    fontSize = 12.sp, color = if (r.installed) colors.accent else colors.danger,
                )
            }
        }
        Column(Modifier.fillMaxWidth().webSurface(CodegShapes.small).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(stringResource(R.string.vcs_github_accounts), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = colors.textPrimary)
            if (ui.accounts.isEmpty()) {
                Box(Modifier.fillMaxWidth().webSurface(CodegShapes.extraSmall).padding(horizontal = 16.dp, vertical = 24.dp), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.vcs_no_linked_accounts), fontSize = 12.sp, color = colors.textSecondary)
                }
            } else {
                Column(Modifier.fillMaxWidth().webSurface(CodegShapes.extraSmall)) {
                    for (acc in ui.accounts) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(acc.username, fontSize = 15.sp, color = colors.textPrimary)
                                Text(acc.serverUrl, fontSize = 12.sp, color = colors.textSecondary)
                            }
                            if (acc.isDefault) Text(stringResource(R.string.vcs_default_badge), fontSize = 11.sp, color = colors.web.accentForeground, modifier = Modifier.clip(CodegShapes.extraSmall).background(colors.web.accent).padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                }
            }
        }
        ui.error?.let {
            Text(it, fontSize = 12.sp, lineHeight = 18.sp, color = colors.danger, modifier = Modifier.fillMaxWidth().webSurface(CodegShapes.extraSmall).padding(12.dp))
        }
    }
}

@Composable
private fun Pill(label: String, loading: Boolean, onClick: () -> Unit, filled: Boolean) {
    val colors = CodegTheme.colors
    if (filled) {
        Button(
            onClick = onClick,
            enabled = !loading,
            shape = CodegShapes.extraSmall,
            colors = ButtonDefaults.buttonColors(containerColor = colors.accent, contentColor = colors.onAccent),
        ) {
            if (loading) CircularProgressIndicator(Modifier.size(15.dp), strokeWidth = 2.dp, color = colors.onAccent)
            else Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    } else {
        FilledTonalButton(
            onClick = onClick,
            enabled = !loading,
            shape = CodegShapes.extraSmall,
            colors = ButtonDefaults.filledTonalButtonColors(containerColor = colors.web.muted, contentColor = colors.textPrimary),
        ) {
            if (loading) CircularProgressIndicator(Modifier.size(15.dp), strokeWidth = 2.dp, color = colors.accent)
            else Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

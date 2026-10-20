package app.codeg.android.feature.sessiondetail.interactive

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Assignment
import androidx.compose.material.icons.rounded.GppMaybe
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodeBlock
import app.codeg.android.core.designsystem.component.webSurface
import app.codeg.android.core.designsystem.diff.DiffView
import app.codeg.android.core.designsystem.markdown.MarkdownContent
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.withOpacity
import app.codeg.android.core.model.PermissionOption

/** A tool-permission approval card shown above the composer. Port of iOS `PermissionRequestCard`. */
@Composable
fun PermissionRequestCard(
    parsed: ParsedPermission,
    options: List<PermissionOption>,
    onRespond: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = CodegTheme.colors
    var submitting by remember(parsed.jsonPreview) { mutableStateOf<String?>(null) }
    val accent = colors.accent

    Column(
        modifier
            .fillMaxWidth()
            .webSurface(CodegShapes.small)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        // Header.
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                if (parsed.isPlan) Icons.AutoMirrored.Rounded.Assignment else Icons.Rounded.GppMaybe,
                contentDescription = null,
                tint = if (parsed.isPlan) accent else colors.danger,
                modifier = Modifier.size(20.dp),
            )
            Column(Modifier.weight(1f)) {
                Text(parsed.title.ifBlank { stringResource(R.string.perm_request_title) }, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = colors.textPrimary)
                Text(
                    if (parsed.isPlan) stringResource(R.string.perm_plan_subtitle) else stringResource(R.string.perm_permission_subtitle),
                    fontSize = 12.sp,
                    color = colors.textSecondary,
                )
            }
            if (!parsed.isPlan) {
                Text(
                    parsed.kind,
                    fontSize = 11.sp,
                    color = colors.web.mutedForeground,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(colors.web.muted)
                        .padding(horizontal = 7.dp, vertical = 2.dp),
                )
            }
        }

        // Body.
        val scrolls = parsed.diffFiles.isNotEmpty() || parsed.planMarkdown != null ||
            parsed.planEntries.size > 4 || parsed.allowedPrompts.size > 4
        Column(
            modifier = if (scrolls) Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState()) else Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PermissionBody(parsed)
        }

        // Option buttons.
        for (option in options) {
            OptionButton(
                option = option,
                submitting = submitting == option.optionId,
                enabled = submitting == null,
                onClick = {
                    submitting = option.optionId
                    onRespond(option.optionId)
                },
            )
        }
    }
}

@Composable
private fun PermissionBody(parsed: ParsedPermission) {
    val colors = CodegTheme.colors
    if (!parsed.hasStructuredBody) {
        CodeBlock(code = parsed.jsonPreview, language = "json")
        return
    }
    parsed.command?.let { cmd ->
        CodeBlock(code = cmd, language = "bash")
        parsed.cwd?.let { Text("cwd: $it", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = colors.web.mutedForeground) }
    }
    if (parsed.diffFiles.isNotEmpty()) DiffView(parsed.diffFiles)
    parsed.planMarkdown?.let { MarkdownContent(it) }
    if (parsed.planEntries.isNotEmpty()) {
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            for (entry in parsed.planEntries) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("•", color = colors.web.mutedForeground, fontSize = 13.sp)
                    Text(entry.text, fontSize = 13.sp, color = colors.textPrimary, modifier = Modifier.weight(1f))
                }
            }
        }
    }
    if (parsed.allowedPrompts.isNotEmpty()) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            for (ap in parsed.allowedPrompts) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.Top) {
                    if (ap.tool.isNotEmpty()) {
                        Text(
                            ap.tool,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = colors.accent,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(colors.accent.withOpacity(0.12f))
                                .padding(horizontal = 5.dp, vertical = 1.dp),
                        )
                    }
                    Text(ap.prompt, fontSize = 12.sp, color = colors.textSecondary, modifier = Modifier.weight(1f))
                }
            }
        }
    }
    parsed.modeTarget?.let { Text("→ $it", fontSize = 12.sp, color = colors.textSecondary) }
    if (parsed.url != null || parsed.query != null || (parsed.prompt != null && parsed.command == null)) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            parsed.url?.let { Text(it, fontSize = 12.sp, fontFamily = FontFamily.Monospace, color = colors.textSecondary) }
            parsed.query?.let { Text(it, fontSize = 12.sp, color = colors.textSecondary) }
            if (parsed.command == null) parsed.prompt?.let { Text(it, fontSize = 12.sp, color = colors.textSecondary) }
        }
    }
}

@Composable
private fun OptionButton(option: PermissionOption, submitting: Boolean, enabled: Boolean, onClick: () -> Unit) {
    val colors = CodegTheme.colors
    val isReject = option.isReject
    val shape = CodegShapes.extraSmall
    // Keep the actively-submitting button enabled-looking (spinner) while the
    // others disable; clicking is gated by `enabled` regardless.
    val clickable = enabled
    if (isReject) {
        OutlinedButton(
            onClick = onClick,
            enabled = clickable,
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            border = BorderStroke(0.5.dp, colors.web.border),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.textPrimary),
        ) {
            if (submitting) {
                CircularProgressIndicator(Modifier.size(16.dp), strokeWidth = 2.dp, color = colors.textPrimary)
            } else {
                Text(option.name, fontWeight = FontWeight.SemiBold)
            }
        }
    } else {
        Button(
            onClick = onClick,
            enabled = clickable,
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.accent,
                contentColor = colors.onAccent,
                disabledContainerColor = colors.accent.withOpacity(0.4f),
                disabledContentColor = colors.onAccent.withOpacity(0.8f),
            ),
        ) {
            if (submitting) {
                CircularProgressIndicator(Modifier.size(16.dp), strokeWidth = 2.dp, color = colors.onAccent)
            } else {
                Text(option.name, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

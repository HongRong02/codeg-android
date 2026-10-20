package app.codeg.android.feature.sessiondetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.common.RelativeTime
import app.codeg.android.core.designsystem.component.AgentAvatar
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegConfirmDialog
import app.codeg.android.core.designsystem.component.CodegDialog
import app.codeg.android.core.designsystem.component.CodegDropdownMenu
import app.codeg.android.core.designsystem.component.CodegIconButton
import app.codeg.android.core.designsystem.component.CodegMenuItem
import app.codeg.android.core.designsystem.component.CodegMenuItemVariant
import app.codeg.android.core.designsystem.component.CodegMenuSeparator
import app.codeg.android.core.designsystem.component.CodegRadioGroup
import app.codeg.android.core.designsystem.component.CodegSeparator
import app.codeg.android.core.designsystem.component.CodegSheet
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.statusColor
import app.codeg.android.core.designsystem.component.statusLabel
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.ConversationStatus

/**
 * The session detail toolbar's overflow menu: Rename / Pin / Session Details /
 * Change Status / Delete. Mirrors iOS `SessionActionsMenu` + `SessionDetailsSheet`.
 * Only shown once the session is server-linked and its summary has loaded.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionActionsMenu(
    ui: SessionDetailUiState,
    viewModel: SessionDetailViewModel,
    onDeleted: () -> Unit,
) {
    val colors = CodegTheme.colors
    val summary = ui.summary ?: return
    val connectionActions by viewModel.sessionActions.collectAsState()
    var menu by remember { mutableStateOf(false) }
    var showRename by remember { mutableStateOf(false) }
    var showStatus by remember { mutableStateOf(false) }
    var showDetails by remember { mutableStateOf(false) }
    var showDelete by remember { mutableStateOf(false) }

    Box {
        CodegIconButton(onClick = { menu = true }) {
            Icon(Icons.Rounded.MoreVert, contentDescription = stringResource(R.string.sessionactions_session_actions), tint = colors.textPrimary)
        }
        CodegDropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
            CodegMenuItem(text = { Text(stringResource(R.string.sessionactions_rename)) }, onClick = { menu = false; showRename = true })
            CodegMenuItem(text = { Text(if (summary.isPinned) stringResource(R.string.sessionactions_unpin) else stringResource(R.string.sessionactions_pin)) }, onClick = { menu = false; viewModel.togglePin() })
            CodegMenuItem(text = { Text(stringResource(R.string.sessionactions_session_details)) }, onClick = { menu = false; showDetails = true })
            CodegMenuItem(text = { Text(stringResource(R.string.sessionactions_change_status)) }, onClick = { menu = false; showStatus = true })
            if (connectionActions.forkSupported) {
                CodegMenuItem(text = { Text(stringResource(R.string.session_fork_action)) },
                    enabled = !ui.isInFlight && !ui.loading && !connectionActions.busy,
                    onClick = { menu = false; viewModel.forkSession() })
            }
            if (connectionActions.disconnectSupported) {
                CodegMenuItem(text = { Text(stringResource(R.string.session_fork_disconnect)) },
                    enabled = !ui.isInFlight && !ui.loading && !connectionActions.busy,
                    onClick = { menu = false; viewModel.disconnectSession() })
            }
            CodegMenuSeparator()
            CodegMenuItem(variant = CodegMenuItemVariant.Destructive, text = { Text(stringResource(R.string.common_delete)) }, onClick = { menu = false; showDelete = true })
        }
    }

    if (showRename) {
        var name by remember { mutableStateOf(summary.trimmedTitle ?: "") }
        CodegDialog(
            title = stringResource(R.string.sessionactions_rename_session),
            onDismiss = { showRename = false },
            footer = {
                CodegButton(variant = CodegButtonVariant.Ghost, onClick = { showRename = false }) {
                    Text(stringResource(R.string.common_cancel))
                }
                CodegButton(enabled = name.trim().isNotEmpty(), onClick = { viewModel.rename(name); showRename = false }) {
                    Text(stringResource(R.string.sessionactions_rename))
                }
            },
        ) {
            CodegTextField(value = name, onValueChange = { name = it }, label = stringResource(R.string.sessionactions_rename))
        }
    }

    if (showStatus) {
        CodegDialog(
            title = stringResource(R.string.sessionactions_change_status),
            onDismiss = { showStatus = false },
            footer = {
                CodegButton(variant = CodegButtonVariant.Ghost, onClick = { showStatus = false }) {
                    Text(stringResource(R.string.common_cancel))
                }
            },
        ) {
            val statuses = ConversationStatus.selectable
            CodegRadioGroup(
                options = statuses.map { statusLabel(it) },
                value = statusLabel(summary.status),
                onSelect = { label ->
                    statuses.firstOrNull { statusLabel(it) == label }?.let { viewModel.setStatus(it) }
                    showStatus = false
                },
            )
        }
    }

    if (showDelete) {
        CodegConfirmDialog(
            title = stringResource(R.string.sessionactions_delete_session_confirm),
            message = stringResource(R.string.sessionactions_delete_session_message),
            target = summary.trimmedTitle ?: stringResource(R.string.sessionactions_untitled),
            confirmLabel = stringResource(R.string.common_delete),
            destructive = true,
            onConfirm = { showDelete = false; viewModel.deleteConversation(onDeleted) },
            onDismiss = { showDelete = false },
        )
    }

    if (showDetails) {
        CodegSheet(title = stringResource(R.string.sessionactions_session_details), onDismiss = { showDetails = false }) {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 32.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AgentAvatar(ui.agent, size = 40.dp)
                    Text(summary.trimmedTitle ?: stringResource(R.string.sessionactions_untitled), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = colors.textPrimary, modifier = Modifier.weight(1f))
                }
                CodegSeparator()
                DetailRow(stringResource(R.string.sessionactions_detail_status), statusLabel(summary.status), statusColor(summary.status))
                ui.folderName?.let { DetailRow(stringResource(R.string.sessionactions_detail_folder), it) }
                DetailRow(stringResource(R.string.sessionactions_detail_messages), summary.messageCount.toString())
                summary.model?.takeIf { it.isNotBlank() }?.let { DetailRow(stringResource(R.string.sessionactions_detail_model), it) }
                summary.gitBranch?.takeIf { it.isNotBlank() }?.let { DetailRow(stringResource(R.string.sessionactions_detail_branch), it) }
                DetailRow(stringResource(R.string.sessionactions_detail_created), RelativeTime.compact(summary.createdAt))
                DetailRow(stringResource(R.string.sessionactions_detail_updated), RelativeTime.compact(summary.updatedAt))
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, valueColor: androidx.compose.ui.graphics.Color? = null) {
    val colors = CodegTheme.colors
    Row(Modifier.fillMaxWidth().heightIn(min = 40.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.Top) {
        Text(label, fontSize = 12.sp, color = colors.web.mutedForeground, modifier = Modifier.weight(1f))
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = valueColor ?: colors.textPrimary, modifier = Modifier.weight(2f), textAlign = TextAlign.End)
    }
}

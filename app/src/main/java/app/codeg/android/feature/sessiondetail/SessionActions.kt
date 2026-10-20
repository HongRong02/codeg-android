package app.codeg.android.feature.sessiondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.common.RelativeTime
import app.codeg.android.core.designsystem.component.AgentAvatar
import app.codeg.android.core.designsystem.component.CodegIconButton
import app.codeg.android.core.designsystem.component.statusColor
import app.codeg.android.core.designsystem.component.statusLabel
import app.codeg.android.core.designsystem.theme.CodegShapes
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
        DropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
            DropdownMenuItem(text = { Text(stringResource(R.string.sessionactions_rename)) }, onClick = { menu = false; showRename = true })
            DropdownMenuItem(text = { Text(if (summary.isPinned) stringResource(R.string.sessionactions_unpin) else stringResource(R.string.sessionactions_pin)) }, onClick = { menu = false; viewModel.togglePin() })
            DropdownMenuItem(text = { Text(stringResource(R.string.sessionactions_session_details)) }, onClick = { menu = false; showDetails = true })
            DropdownMenuItem(text = { Text(stringResource(R.string.sessionactions_change_status)) }, onClick = { menu = false; showStatus = true })
            if (connectionActions.forkSupported) {
                DropdownMenuItem(text = { Text(stringResource(R.string.session_fork_action)) },
                    enabled = !ui.isInFlight && !ui.loading && !connectionActions.busy,
                    onClick = { menu = false; viewModel.forkSession() })
            }
            if (connectionActions.disconnectSupported) {
                DropdownMenuItem(text = { Text(stringResource(R.string.session_fork_disconnect)) },
                    enabled = !ui.isInFlight && !ui.loading && !connectionActions.busy,
                    onClick = { menu = false; viewModel.disconnectSession() })
            }
            HorizontalDivider(color = colors.web.border)
            DropdownMenuItem(text = { Text(stringResource(R.string.common_delete), color = colors.danger) }, onClick = { menu = false; showDelete = true })
        }
    }

    if (showRename) {
        var name by remember { mutableStateOf(summary.trimmedTitle ?: "") }
        AlertDialog(
            onDismissRequest = { showRename = false },
            containerColor = colors.bgElevated,
            title = { Text(stringResource(R.string.sessionactions_rename_session), color = colors.textPrimary) },
            text = {
                OutlinedTextField(value = name, onValueChange = { name = it }, singleLine = true, modifier = Modifier.fillMaxWidth())
            },
            confirmButton = {
                TextButton(enabled = name.trim().isNotEmpty(), onClick = { viewModel.rename(name); showRename = false }) {
                    Text(stringResource(R.string.sessionactions_rename), color = if (name.trim().isNotEmpty()) colors.accent else colors.web.mutedForeground)
                }
            },
            dismissButton = { TextButton(onClick = { showRename = false }) { Text(stringResource(R.string.common_cancel), color = colors.textSecondary) } },
        )
    }

    if (showStatus) {
        AlertDialog(
            onDismissRequest = { showStatus = false },
            containerColor = colors.bgElevated,
            title = { Text(stringResource(R.string.sessionactions_change_status), color = colors.textPrimary) },
            text = {
                Column {
                    for (status in ConversationStatus.selectable) {
                        Row(
                            Modifier.fillMaxWidth().clip(CodegShapes.extraSmall)
                                .selectable(selected = summary.status == status, role = Role.RadioButton) { viewModel.setStatus(status); showStatus = false }
                                .padding(vertical = 4.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            RadioButton(
                                selected = summary.status == status,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(selectedColor = colors.accent, unselectedColor = colors.web.mutedForeground),
                            )
                            Box(Modifier.size(9.dp).clip(RoundedCornerShape(50)).background(statusColor(status)))
                            Text(statusLabel(status), color = colors.textPrimary, modifier = Modifier.weight(1f))
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { showStatus = false }) { Text(stringResource(R.string.common_cancel), color = colors.textSecondary) } },
        )
    }

    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            containerColor = colors.bgElevated,
            title = { Text(stringResource(R.string.sessionactions_delete_session_confirm), color = colors.textPrimary) },
            text = { Text(stringResource(R.string.sessionactions_delete_session_message), color = colors.textSecondary) },
            confirmButton = { TextButton(onClick = { showDelete = false; viewModel.deleteConversation(onDeleted) }) { Text(stringResource(R.string.common_delete), color = colors.danger) } },
            dismissButton = { TextButton(onClick = { showDelete = false }) { Text(stringResource(R.string.common_cancel), color = colors.textSecondary) } },
        )
    }

    if (showDetails) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(onDismissRequest = { showDetails = false }, sheetState = sheetState, containerColor = colors.bgElevated) {
            Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp).padding(bottom = 32.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AgentAvatar(ui.agent, size = 40.dp)
                    Text(summary.trimmedTitle ?: stringResource(R.string.sessionactions_untitled), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = colors.textPrimary, modifier = Modifier.weight(1f))
                }
                HorizontalDivider(color = colors.web.border)
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

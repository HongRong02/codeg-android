package app.codeg.android.feature.sessions.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.PillShape
import app.codeg.android.core.designsystem.theme.colorFromHex
import app.codeg.android.core.designsystem.theme.withOpacity
import app.codeg.android.core.model.ConversationSummary
import app.codeg.android.core.model.ConversationTag
import app.codeg.android.feature.sessions.SessionListUiState
import app.codeg.android.feature.sessions.SessionListViewModel

@Composable
fun ConversationTagChip(tag: ConversationTag) {
    val color = colorFromHex(tag.color) ?: CodegTheme.colors.accent
    Text(tag.name, color = color, style = MaterialTheme.typography.labelSmall,
        maxLines = 1, overflow = TextOverflow.Ellipsis,
        modifier = Modifier.widthIn(max = 96.dp)
            .background(color.withOpacity(0.12f), PillShape)
            .border(1.dp, color.withOpacity(0.35f), PillShape)
            .padding(horizontal = 6.dp, vertical = 2.dp))
}

@Composable
fun ConversationTagFilter(ui: SessionListUiState, viewModel: SessionListViewModel, onManage: () -> Unit) {
    Column {
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onManage) { Text(stringResource(R.string.ct_manage)) }
            ui.tags.forEach { tag ->
                FilterChip(selected = tag.id in ui.selectedTags, onClick = { viewModel.toggleTagFilter(tag.id) },
                    label = { ConversationTagChip(tag) }, modifier = Modifier.padding(end = 4.dp))
            }
        }
        if (ui.selectedTags.isNotEmpty()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = ui.matchAllTags, onCheckedChange = viewModel::setMatchAllTags)
                Text(stringResource(R.string.ct_match_all), modifier = Modifier.weight(1f))
                TextButton(onClick = viewModel::clearTagFilter) { Text(stringResource(R.string.ct_clear)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationTagSheet(
    conversation: ConversationSummary?,
    ui: SessionListUiState,
    viewModel: SessionListViewModel,
    onDismiss: () -> Unit,
) {
    var selected by remember(conversation?.id) { mutableStateOf(conversation?.tagIds.orEmpty().toSet()) }
    var editing by remember { mutableStateOf<ConversationTag?>(null) }
    var formOpen by remember { mutableStateOf(false) }
    var deleting by remember { mutableStateOf<ConversationTag?>(null) }
    // A global tag is applicable everywhere; folder tags are scoped to the root repository.
    val folder = ui.folders.firstOrNull { it.id == conversation?.folderId }
    val rootId = folder?.parentId ?: conversation?.folderId
    val visible = ui.tags.filter { conversation == null || it.folderId == null || it.folderId == rootId || it.id in conversation.tagIds.orEmpty() }
    ModalBottomSheet(onDismissRequest = { if (!ui.tagsBusy) onDismiss() }) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            Text(stringResource(R.string.ct_title), style = MaterialTheme.typography.titleLarge)
            if (ui.error != null) Text(ui.error, color = CodegTheme.colors.danger)
            TextButton(enabled = !ui.tagsBusy, onClick = { editing = null; formOpen = true }) {
                Text(stringResource(R.string.ct_create))
            }
            if (visible.isEmpty()) Text(stringResource(R.string.ct_empty))
            LazyColumn(Modifier.weight(1f, fill = false).heightIn(max = 400.dp)) {
                items(visible, key = { it.id }) { tag ->
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        if (conversation != null) Checkbox(checked = tag.id in selected, enabled = !ui.tagsBusy,
                            onCheckedChange = { selected = if (it) selected + tag.id else selected - tag.id })
                        Box(Modifier.weight(1f)) { ConversationTagChip(tag) }
                        TextButton(enabled = !ui.tagsBusy, onClick = { editing = tag; formOpen = true }) {
                            Text(stringResource(R.string.ct_edit))
                        }
                        TextButton(enabled = !ui.tagsBusy, onClick = { deleting = tag }) {
                            Text(stringResource(R.string.ct_delete))
                        }
                    }
                }
            }
            if (conversation != null) Button(enabled = !ui.tagsBusy, onClick = {
                viewModel.saveConversationTags(conversation.id, selected.intersect(ui.tags.map { it.id }.toSet()), onDismiss)
            }, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.ct_save)) }
            Spacer(Modifier.height(24.dp))
        }
    }
    if (formOpen) TagForm(editing, ui.tagsBusy, onDismiss = { formOpen = false }, onSave = { name, color ->
        viewModel.saveTag(editing, name, color) { formOpen = false }
    })
    deleting?.let { tag ->
        AlertDialog(onDismissRequest = { if (!ui.tagsBusy) deleting = null },
            title = { Text(stringResource(R.string.ct_delete)) },
            text = { Text(stringResource(R.string.ct_delete_confirm, tag.name)) },
            confirmButton = { TextButton(enabled = !ui.tagsBusy, onClick = {
                viewModel.deleteTag(tag) { selected = selected - tag.id; deleting = null }
            }) { Text(stringResource(R.string.ct_delete)) } },
            dismissButton = { TextButton(enabled = !ui.tagsBusy, onClick = { deleting = null }) { Text(stringResource(R.string.ct_cancel)) } })
    }
}

@Composable
private fun TagForm(tag: ConversationTag?, busy: Boolean, onDismiss: () -> Unit, onSave: (String, String) -> Unit) {
    var name by remember(tag?.id) { mutableStateOf(tag?.name.orEmpty()) }
    var color by remember(tag?.id) { mutableStateOf(tag?.color ?: "#3b82f6") }
    val valid = name.isNotBlank() && Regex("#[0-9a-fA-F]{6}").matches(color)
    AlertDialog(onDismissRequest = { if (!busy) onDismiss() },
        title = { Text(stringResource(if (tag == null) R.string.ct_create else R.string.ct_edit)) },
        text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, enabled = !busy, singleLine = true,
                label = { Text(stringResource(R.string.ct_name)) })
            OutlinedTextField(value = color, onValueChange = { color = it }, enabled = !busy, singleLine = true,
                label = { Text(stringResource(R.string.ct_color)) })
        } },
        confirmButton = { TextButton(enabled = valid && !busy, onClick = { onSave(name, color) }) { Text(stringResource(R.string.ct_save)) } },
        dismissButton = { TextButton(enabled = !busy, onClick = onDismiss) { Text(stringResource(R.string.ct_cancel)) } })
}

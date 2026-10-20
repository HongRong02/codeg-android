package app.codeg.android.feature.settings

import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegConfirmDialog
import app.codeg.android.core.designsystem.component.CodegDialog
import app.codeg.android.core.designsystem.component.CodegDropdownMenu
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegMenuItem
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.EmptyState
import app.codeg.android.core.designsystem.component.LoadingView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.QuickMessage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuickMessagesContent(viewModel: QuickMessagesViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors
    var editor by remember { mutableStateOf<EditorTarget?>(null) }

    Box(Modifier.fillMaxSize()) {
        when {
            ui.loading && ui.messages.isEmpty() ->
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { LoadingView(stringResource(R.string.common_loading)) }
            else -> LazyColumn(Modifier.fillMaxSize(), contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 88.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (ui.messages.isEmpty() && !ui.loading) {
                    item("empty") { Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) { EmptyState(Icons.AutoMirrored.Rounded.Chat, stringResource(R.string.quickmsg_empty_title), stringResource(R.string.quickmsg_empty_subtitle)) } }
                }
                items(ui.messages, key = { it.id }) { msg ->
                    MessageRow(msg, onEdit = { editor = EditorTarget(msg) }, onDelete = { viewModel.delete(msg) })
                }
            }
        }
        if (!(ui.loading && ui.messages.isEmpty())) {
            CodegButton(onClick = { editor = EditorTarget(null) }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp), variant = CodegButtonVariant.Default) { Icon(Icons.Rounded.Add, contentDescription = stringResource(R.string.quickmsg_new_cd)) }
        }
    }

    editor?.let { target ->
        QuickMessageEditor(
            existing = target.message,
            onDismiss = { editor = null },
            onSave = { title, content -> viewModel.save(target.message, title, content); editor = null },
        )
    }
}

private data class EditorTarget(val message: QuickMessage?)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MessageRow(msg: QuickMessage, onEdit: () -> Unit, onDelete: () -> Unit) {
    val colors = CodegTheme.colors
    var menu by remember { mutableStateOf(false) }
    var deleting by remember { mutableStateOf(false) }
    Box {
        CodegListRow(title = msg.title, subtitle = msg.content, onClick = onEdit,
            onLongClick = { menu = true }, onMoreClick = { menu = true })
        CodegDropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
            CodegMenuItem(text = { Text(stringResource(R.string.common_delete)) }, onClick = { menu = false; deleting = true })
        }
    }
    if (deleting) CodegConfirmDialog(
        title = stringResource(R.string.common_delete),
        message = stringResource(R.string.settings_delete_target_message),
        target = msg.title,
        confirmLabel = stringResource(R.string.common_delete),
        destructive = true,
        onConfirm = { deleting = false; onDelete() },
        onDismiss = { deleting = false },
    )
}

@Composable
private fun QuickMessageEditor(existing: QuickMessage?, onDismiss: () -> Unit, onSave: (String, String) -> Unit) {
    var title by remember { mutableStateOf(existing?.title ?: "") }
    var content by remember { mutableStateOf(existing?.content ?: "") }
    CodegDialog(
        title = if (existing == null) stringResource(R.string.quickmsg_new_title) else stringResource(R.string.quickmsg_edit_title),
        onDismiss = onDismiss,
        footer = {
            CodegButton(onClick = onDismiss, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.common_cancel)) }
            CodegButton(onClick = { onSave(title, content) }, enabled = title.isNotBlank() && content.isNotBlank(), variant = CodegButtonVariant.Default) { Text(stringResource(R.string.common_save)) }
        },
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CodegTextField(title, { title = it }, label = stringResource(R.string.quickmsg_title_label))
                CodegTextField(content, { content = it }, label = stringResource(R.string.quickmsg_content_label), singleLine = false)
            }
        },
    )
}

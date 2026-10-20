package app.codeg.android.feature.projects.forge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.markdown.MarkdownContent

@Composable
internal fun ForgeDetail(ui: ForgeUi, vm: ForgeViewModel, modifier: Modifier = Modifier) {
    val row = ui.selected ?: return
    var comment by remember(row.number, row.kind) { mutableStateOf("") }
    var confirmation by remember(row.number, row.kind) { mutableStateOf<String?>(null) }
    var method by remember(ui.options) { mutableStateOf(ui.options?.default_method ?: "merge") }
    Column(modifier) {
        Row {
            TextButton(onClick = { vm.refresh() }, enabled = !ui.busy) { Text(stringResource(R.string.forge_back)) }
            TextButton(onClick = { vm.open(row) }, enabled = !ui.busy) { Text(stringResource(R.string.forge_refresh)) }
        }
        LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Text("#${row.number} ${row.title}", style = MaterialTheme.typography.titleLarge)
                Text(listOfNotNull(row.author, row.updated_at).joinToString(" · "))
                Text(row.labels.joinToString(" · ") { it.name })
                Text(stringResource(when (row.state) { "open" -> R.string.forge_open; "merged" -> R.string.forge_merged; else -> R.string.forge_closed }))
                row.body?.let { MarkdownContent(it) }
            }
            ui.detail?.let { detail -> item {
                Text("${detail.head_ref} → ${detail.base_ref}")
                detail.merge_state?.let { Text(it) }
            } }
            item {
                Row {
                    if (row.state != "merged") TextButton(onClick = { confirmation = "state" }, enabled = !ui.busy) {
                        Text(stringResource(if (row.state == "open") R.string.forge_close_item else R.string.forge_reopen))
                    }
                    TextButton(onClick = vm::createTask, enabled = !ui.busy && ui.taskId == null) {
                        Text(if (ui.taskId != null) stringResource(R.string.forge_existing, ui.taskId) else stringResource(R.string.forge_task))
                    }
                }
                if (ui.mismatch) Text(stringResource(R.string.forge_mismatch), color = MaterialTheme.colorScheme.error)
                if (row.is_pr && row.state == "open" && !row.draft && ui.options != null) {
                    ui.options.methods.ifEmpty { listOf("merge") }.forEach { candidate ->
                        FilterChip(selected = method == candidate, onClick = { method = candidate }, enabled = !ui.busy,
                            label = { Text(stringResource(when (candidate) { "squash" -> R.string.forge_squash; "rebase" -> R.string.forge_rebase; else -> R.string.forge_merge })) })
                    }
                    TextButton(onClick = { confirmation = "merge" }, enabled = !ui.busy && ui.detail != null && ui.detail.mergeable != false) {
                        Text(stringResource(R.string.forge_merge))
                    }
                }
            }
            items(ui.files.files) { file ->
                Text(file.path, style = MaterialTheme.typography.titleSmall)
                if (file.patch != null) Text(file.patch, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.bodySmall)
                else Text(stringResource(R.string.forge_no_patch))
            }
            if (ui.files.has_next) item { TextButton(vm::moreFiles, enabled = !ui.busy) { Text(stringResource(R.string.forge_more_files)) } }
            items(ui.comments.comments, key = { it.id }) { entry ->
                HorizontalDivider()
                Text(listOfNotNull(entry.author, entry.created_at).joinToString(" · "), style = MaterialTheme.typography.labelMedium)
                MarkdownContent(entry.body)
            }
            if (ui.comments.has_next) item { TextButton(vm::moreComments, enabled = !ui.busy) { Text(stringResource(R.string.forge_more_comments)) } }
        }
        OutlinedTextField(comment, { comment = it }, label = { Text(stringResource(R.string.forge_comment)) }, modifier = Modifier.fillMaxWidth(), enabled = !ui.busy, maxLines = 5)
        TextButton(onClick = { vm.comment(comment) { comment = "" } }, enabled = !ui.busy && comment.isNotBlank()) { Text(stringResource(R.string.forge_send)) }
    }
    confirmation?.let { action ->
        val actionLabel = stringResource(if (action == "merge") R.string.forge_merge else if (row.state == "open") R.string.forge_close_item else R.string.forge_reopen)
        AlertDialog(onDismissRequest = { confirmation = null },
            title = { Text(actionLabel) },
            text = { Text(stringResource(R.string.forge_confirm_target, actionLabel, row.number, row.title, if (action == "merge") method else "")) },
            confirmButton = { TextButton(onClick = { confirmation = null; if (action == "merge") vm.merge(method) else vm.setState() }) { Text(stringResource(R.string.forge_confirm)) } },
            dismissButton = { TextButton(onClick = { confirmation = null }) { Text(stringResource(R.string.forge_cancel)) } },
        )
    }
}

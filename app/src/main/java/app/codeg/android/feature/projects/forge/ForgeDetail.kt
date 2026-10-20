package app.codeg.android.feature.projects.forge

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import app.codeg.android.core.designsystem.component.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.markdown.MarkdownContent

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ForgeDetail(ui: ForgeUi, vm: ForgeViewModel, modifier: Modifier = Modifier) {
    val row = ui.selected ?: return
    var comment by remember(row.number, row.kind) { mutableStateOf("") }
    var confirmation by remember(row.number, row.kind) { mutableStateOf<String?>(null) }
    var method by remember(ui.options) { mutableStateOf(ui.options?.default_method ?: "merge") }
    Column(modifier) {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CodegButton(variant = CodegButtonVariant.Ghost, onClick = { vm.refresh() }, enabled = !ui.busy) { Text(stringResource(R.string.forge_back)) }
            CodegButton(variant = CodegButtonVariant.Ghost, onClick = { vm.open(row) }, enabled = !ui.busy) { Text(stringResource(R.string.forge_refresh)) }
        }
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            run {
                Text("#${row.number} ${row.title}", style = MaterialTheme.typography.titleLarge)
                Text(listOfNotNull(row.author, row.updated_at).joinToString(" · "))
                row.labels.forEach { CodegBadge(it.name, variant = CodegBadgeVariant.Outline) }
                CodegBadge(stringResource(when (row.state) { "open" -> R.string.forge_open; "merged" -> R.string.forge_merged; else -> R.string.forge_closed }))
                row.body?.let { MarkdownContent(it) }
            }
            ui.detail?.let { detail -> run {
                Text("${detail.head_ref} → ${detail.base_ref}")
                detail.merge_state?.let { Text(it) }
            } }
            run {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (row.state != "merged") CodegButton(variant = CodegButtonVariant.Ghost, onClick = { confirmation = "state" }, enabled = !ui.busy) {
                        Text(stringResource(if (row.state == "open") R.string.forge_close_item else R.string.forge_reopen))
                    }
                    CodegButton(variant = CodegButtonVariant.Ghost, onClick = vm::createTask, enabled = !ui.busy && ui.taskId == null) {
                        Text(if (ui.taskId != null) stringResource(R.string.forge_existing, ui.taskId) else stringResource(R.string.forge_task))
                    }
                }
                if (ui.mismatch) Text(stringResource(R.string.forge_mismatch), color = MaterialTheme.colorScheme.error)
                if (row.is_pr && row.state == "open" && !row.draft && ui.options != null) {
                    ui.options.methods.ifEmpty { listOf("merge") }.forEach { candidate ->
                        CodegFilterChip(selected = method == candidate, onClick = { method = candidate }, enabled = !ui.busy,
                            label = stringResource(when (candidate) { "squash" -> R.string.forge_squash; "rebase" -> R.string.forge_rebase; else -> R.string.forge_merge }))
                    }
                    CodegButton(variant = CodegButtonVariant.Ghost, onClick = { confirmation = "merge" }, enabled = !ui.busy && ui.detail != null && ui.detail.mergeable != false) {
                        Text(stringResource(R.string.forge_merge))
                    }
                }
            }
            ui.files.files.forEach { file ->
                CodegSectionHeader(title = file.path)
                if (file.patch != null) Text(file.patch, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.bodySmall)
                else Text(stringResource(R.string.forge_no_patch))
            }
            if (ui.files.has_next) run { CodegButton(variant = CodegButtonVariant.Ghost, onClick = vm::moreFiles, enabled = !ui.busy) { Text(stringResource(R.string.forge_more_files)) } }
            ui.comments.comments.forEach { entry ->
                CodegSeparator()
                Text(listOfNotNull(entry.author, entry.created_at).joinToString(" · "), style = MaterialTheme.typography.labelMedium)
                MarkdownContent(entry.body)
            }
            if (ui.comments.has_next) run { CodegButton(variant = CodegButtonVariant.Ghost, onClick = vm::moreComments, enabled = !ui.busy) { Text(stringResource(R.string.forge_more_comments)) } }
        }
        CodegTextField(comment, { if (!ui.busy) comment = it }, label = stringResource(R.string.forge_comment), modifier = Modifier.fillMaxWidth(), singleLine = false)
        CodegButton(variant = CodegButtonVariant.Ghost, onClick = { vm.comment(comment) { comment = "" } }, enabled = !ui.busy && comment.isNotBlank()) { Text(stringResource(R.string.forge_send)) }
    }
    confirmation?.let { action ->
        val actionLabel = stringResource(if (action == "merge") R.string.forge_merge else if (row.state == "open") R.string.forge_close_item else R.string.forge_reopen)
        CodegConfirmDialog(
            title = actionLabel,
            message = stringResource(R.string.forge_confirm_target, actionLabel, row.number, row.title, if (action == "merge") method else ""),
            target = "#${row.number} ${row.title}",
            confirmLabel = stringResource(R.string.forge_confirm),
            destructive = action == "merge" || row.state == "open",
            onConfirm = { confirmation = null; if (action == "merge") vm.merge(method) else vm.setState() },
            onDismiss = { confirmation = null },
        )
    }
}

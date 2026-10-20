package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.diff.DiffView
import app.codeg.android.core.designsystem.diff.UnifiedDiff
import app.codeg.android.core.designsystem.theme.CodegTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StashSheet(viewModel: StashViewModel, onDismiss: () -> Unit, onChanged: () -> Unit) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    var message by remember { mutableStateOf("") }
    var keepIndex by remember { mutableStateOf(false) }
    var confirmation by remember { mutableStateOf<Pair<String, String?>?>(null) }
    val colors = CodegTheme.colors
    val toaster = LocalCodegToaster.current
    val failureTitle = stringResource(R.string.t56_operation_failed)
    val doneTitle = stringResource(R.string.t56_operation_completed)
    LaunchedEffect(ui.error, ui.result) {
        ui.error?.let { toaster.error(failureTitle, it) }
        ui.result?.let { toaster.success(it) }
    }
    CodegSheet(title = stringResource(R.string.stash_title), onDismiss = { if (!ui.busy) onDismiss() }) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CodegTextField(message, { message = it }, label = stringResource(R.string.stash_message))
            CodegSwitch(checked = keepIndex, onCheckedChange = { keepIndex = it }, enabled = !ui.busy, label = stringResource(R.string.stash_keep_index))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, enabled = !ui.busy, onClick = { viewModel.change("push", null, message, keepIndex, onChanged) }) { Text(stringResource(R.string.stash_save)) }
                CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, enabled = !ui.busy, onClick = viewModel::refresh) { Text(stringResource(R.string.stash_refresh)) }
                CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, enabled = !ui.busy && ui.entries.isNotEmpty(), onClick = { confirmation = "clear" to null }) { Text(stringResource(R.string.stash_clear)) }
            }
            if (ui.busy) LoadingView(stringResource(R.string.common_loading))
            if (ui.entries.isEmpty()) ui.error?.let { Text(it, color = colors.danger) }
            if (!ui.busy && ui.entries.isEmpty()) Text(stringResource(R.string.stash_empty), color = colors.textSecondary)
            ui.entries.forEach { entry ->
                CodegSeparator()
                CodegListRow(title = entry.refName, subtitle = entry.message + " · " + entry.date, onClick = { if (!ui.busy) viewModel.show(entry.refName) })
                FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, enabled = !ui.busy, onClick = { viewModel.show(entry.refName) }) { Text(stringResource(R.string.stash_show)) }
                    CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, enabled = !ui.busy, onClick = { confirmation = "apply" to entry.refName }) { Text(stringResource(R.string.stash_apply)) }
                    CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, enabled = !ui.busy, onClick = { confirmation = "pop" to entry.refName }) { Text(stringResource(R.string.stash_pop)) }
                    CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, enabled = !ui.busy, onClick = { confirmation = "drop" to entry.refName }) { Text(stringResource(R.string.stash_drop)) }
                }
                if (ui.diffRef == entry.refName) {
                    var expanded by remember(ui.diff) { mutableStateOf(true) }
                    CodegCollapsible(expanded, { expanded = it }, header = { Text(entry.refName) }) {
                        val files = remember(ui.diff) { UnifiedDiff.parse(ui.diff.orEmpty()) }
                        if (files != null) DiffView(files) else Text(ui.diff?.takeIf { it.isNotBlank() } ?: stringResource(R.string.stash_no_diff), color = colors.textSecondary)
                    }
                }
            }
        }
    }
    confirmation?.let { (action, ref) ->
        val actionLabel = stringResource(when (action) {
            "clear" -> R.string.stash_clear
            "drop" -> R.string.stash_drop
            "pop" -> R.string.stash_pop
            else -> R.string.stash_apply
        })
        CodegConfirmDialog(title = actionLabel,
            message = stringResource(R.string.stash_confirm, actionLabel, ref ?: pathLabel(ui.entries.size)),
            target = ref ?: pathLabel(ui.entries.size), confirmLabel = actionLabel,
            destructive = action == "clear" || action == "drop" || action == "pop",
            onConfirm = { confirmation = null; viewModel.change(action, ref, message, keepIndex, onChanged) },
            onDismiss = { confirmation = null })
    }
}

@Composable
private fun pathLabel(count: Int): String = stringResource(R.string.stash_all, count)

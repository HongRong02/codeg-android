package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.CodegTopBar
import app.codeg.android.core.designsystem.diff.DiffView
import app.codeg.android.core.designsystem.diff.UnifiedDiff
import app.codeg.android.core.designsystem.theme.CodegTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StashSheet(viewModel: StashViewModel, onDismiss: () -> Unit, onChanged: () -> Unit) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    var message by remember { mutableStateOf("") }
    var keepIndex by remember { mutableStateOf(false) }
    var confirmation by remember { mutableStateOf<Pair<String, String?>?>(null) }
    val colors = CodegTheme.colors
    ModalBottomSheet(
        contentWindowInsets = { WindowInsets.safeDrawing },
        onDismissRequest = { if (!ui.busy) onDismiss() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = colors.bgElevated,
    ) {
        Column(Modifier.fillMaxWidth().heightIn(max = 680.dp).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CodegTopBar(title = stringResource(R.string.stash_title), showDivider = true)
            CodegTextField(message, { message = it }, label = stringResource(R.string.stash_message))
            Row {
                Switch(checked = keepIndex, onCheckedChange = { keepIndex = it }, enabled = !ui.busy)
                Text(stringResource(R.string.stash_keep_index), modifier = Modifier.padding(12.dp), color = colors.textPrimary)
            }
            Row {
                TextButton(enabled = !ui.busy, onClick = { viewModel.change("push", null, message, keepIndex, onChanged) }) { Text(stringResource(R.string.stash_save)) }
                TextButton(enabled = !ui.busy, onClick = viewModel::refresh) { Text(stringResource(R.string.stash_refresh)) }
                TextButton(enabled = !ui.busy && ui.entries.isNotEmpty(), onClick = { confirmation = "clear" to null }) { Text(stringResource(R.string.stash_clear)) }
            }
            if (ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth(), color = colors.accent)
            ui.error?.let { Text(it, color = colors.danger) }
            ui.result?.let { Text(it, color = colors.textSecondary) }
            if (!ui.busy && ui.entries.isEmpty()) Text(stringResource(R.string.stash_empty), color = colors.textSecondary)
            ui.entries.forEach { entry ->
                HorizontalDivider(color = colors.hairline)
                Text(entry.refName, color = colors.textPrimary)
                Text(entry.message, color = colors.textPrimary)
                Text(entry.date, color = colors.textSecondary)
                Row {
                    TextButton(enabled = !ui.busy, onClick = { viewModel.show(entry.refName) }) { Text(stringResource(R.string.stash_show)) }
                    TextButton(enabled = !ui.busy, onClick = { confirmation = "apply" to entry.refName }) { Text(stringResource(R.string.stash_apply)) }
                    TextButton(enabled = !ui.busy, onClick = { confirmation = "pop" to entry.refName }) { Text(stringResource(R.string.stash_pop)) }
                    TextButton(enabled = !ui.busy, onClick = { confirmation = "drop" to entry.refName }) { Text(stringResource(R.string.stash_drop)) }
                }
                if (ui.diffRef == entry.refName) {
                    val files = remember(ui.diff) { UnifiedDiff.parse(ui.diff.orEmpty()) }
                    if (files != null) DiffView(files) else Text(ui.diff?.takeIf { it.isNotBlank() } ?: stringResource(R.string.stash_no_diff), color = colors.textSecondary)
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
        AlertDialog(
            onDismissRequest = { confirmation = null },
            title = { Text(actionLabel) },
            text = { Text(stringResource(R.string.stash_confirm, actionLabel, ref ?: pathLabel(ui.entries.size))) },
            confirmButton = { TextButton(onClick = {
                confirmation = null
                viewModel.change(action, ref, message, keepIndex, onChanged)
            }) { Text(actionLabel) } },
            dismissButton = { TextButton(onClick = { confirmation = null }) { Text(stringResource(R.string.stash_cancel)) } },
        )
    }
}

@Composable
private fun pathLabel(count: Int): String = stringResource(R.string.stash_all, count)

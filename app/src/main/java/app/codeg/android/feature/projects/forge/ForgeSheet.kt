package app.codeg.android.feature.projects.forge

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.markdown.MarkdownContent
import app.codeg.android.core.network.displayMessage

@Composable
fun ForgeSheet(vm: ForgeViewModel, onDismiss: () -> Unit) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    var newIssue by remember { mutableStateOf(false) }
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)) {
        BackHandler(ui.selected != null) { vm.back() }
        Surface(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize().systemBarsPadding().imePadding().padding(16.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(stringResource(R.string.forge_title), style = MaterialTheme.typography.titleLarge)
                    TextButton(onClick = onDismiss) { Text(stringResource(R.string.forge_done)) }
                }
                ui.error?.let {
                    Text(it.displayMessage(LocalContext.current), color = MaterialTheme.colorScheme.error)
                    Text(stringResource(R.string.forge_token_hint), style = MaterialTheme.typography.bodySmall)
                }
                if (ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth())
                if (ui.selected != null) {
                    ForgeDetail(ui, vm, Modifier.weight(1f))
                } else {
                    Row {
                        listOf("issues" to R.string.forge_issues, "prs" to R.string.forge_prs).forEach { (tab, label) ->
                            FilterChip(selected = ui.tab == tab, enabled = !ui.busy,
                                onClick = { vm.refresh(tab = tab) },
                                label = { Text("${stringResource(label)} (${ui.counts[tab] ?: "—"})") })
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(ui.filter == "open", { vm.refresh(filter = "open") }, enabled = !ui.busy,
                            label = { Text(stringResource(R.string.forge_open)) })
                        FilterChip(ui.filter == "closed", { vm.refresh(filter = "closed") }, enabled = !ui.busy,
                            label = { Text(stringResource(R.string.forge_closed)) })
                        TextButton(onClick = { newIssue = true; vm.loadLabels() }, enabled = !ui.busy) {
                            Text(stringResource(R.string.forge_new))
                        }
                    }
                    if (ui.list.incomplete) Text(stringResource(R.string.forge_incomplete))
                    LazyColumn(Modifier.weight(1f)) {
                        if (ui.list.rows.isEmpty() && !ui.busy) item { Text(stringResource(R.string.forge_empty)) }
                        items(ui.list.rows, key = { it.number }) { row ->
                            Column(Modifier.fillMaxWidth().clickable(enabled = !ui.busy) { vm.open(row) }.padding(vertical = 12.dp)) {
                                Text("#${row.number} ${row.title}", style = MaterialTheme.typography.titleMedium)
                                Text(listOfNotNull(row.author, row.updated_at).joinToString(" · "))
                                if (row.labels.isNotEmpty()) Text(row.labels.joinToString(" · ") { it.name }, style = MaterialTheme.typography.labelMedium)
                            }
                            HorizontalDivider()
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        TextButton(onClick = { vm.refresh(page = ui.list.page - 1) }, enabled = !ui.busy && ui.list.page > 1) {
                            Text(stringResource(R.string.forge_previous))
                        }
                        Text(ui.list.page.toString())
                        TextButton(onClick = { vm.refresh(page = ui.list.page + 1) }, enabled = !ui.busy && ui.list.has_next) {
                            Text(stringResource(R.string.forge_next))
                        }
                        TextButton(onClick = { vm.refresh(page = ui.list.page) }, enabled = !ui.busy) { Text(stringResource(R.string.forge_refresh)) }
                    }
                }
            }
        }
        if (newIssue) ForgeNewIssue(ui, vm) { newIssue = false }
    }
}

@Composable
private fun ForgeNewIssue(ui: ForgeUi, vm: ForgeViewModel, dismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var labels by remember { mutableStateOf(setOf<String>()) }
    AlertDialog(onDismissRequest = { if (!ui.busy) dismiss() },
        title = { Text(stringResource(R.string.forge_new)) },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item { OutlinedTextField(title, { title = it }, label = { Text(stringResource(R.string.forge_issue_title)) }, enabled = !ui.busy) }
                item { OutlinedTextField(body, { body = it }, label = { Text(stringResource(R.string.forge_body)) }, enabled = !ui.busy, minLines = 3) }
                if (ui.labels.truncated) item { Text(stringResource(R.string.forge_incomplete)) }
                items(ui.labels.labels) { label ->
                    FilterChip(label.name in labels, { labels = if (label.name in labels) labels - label.name else labels + label.name },
                        label = { Text(label.name) }, enabled = !ui.busy)
                }
                ui.error?.let { error -> item { Text(error.displayMessage(LocalContext.current), color = MaterialTheme.colorScheme.error) } }
            }
        },
        confirmButton = { TextButton(onClick = { vm.createIssue(title, body, labels, dismiss) }, enabled = !ui.busy && title.isNotBlank()) { Text(stringResource(R.string.forge_create)) } },
        dismissButton = { TextButton(onClick = dismiss, enabled = !ui.busy) { Text(stringResource(R.string.forge_cancel)) } },
    )
}

package app.codeg.android.feature.projects.forge

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.network.displayMessage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ForgeSheet(vm: ForgeViewModel, onDismiss: () -> Unit) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    var newIssue by remember { mutableStateOf(false) }
    CodegFullScreenSheet(title = stringResource(R.string.forge_title), onDismiss = onDismiss) {
        BackHandler(ui.selected != null) { vm.back() }
        ui.error?.let {
            // TODO(T52): CodegToaster for operation feedback.
            Text(it.displayMessage(LocalContext.current), color = MaterialTheme.colorScheme.error)
            Text(stringResource(R.string.forge_token_hint), style = MaterialTheme.typography.bodySmall)
        }
        if (ui.busy) CodegSkeleton(Modifier.fillMaxWidth().height(4.dp))
        if (ui.selected != null) {
            ForgeDetail(ui, vm)
        } else {
            CodegTabs(
                selected = if (ui.tab == "prs") 1 else 0,
                tabs = listOf("issues" to R.string.forge_issues, "prs" to R.string.forge_prs).map { (tab, label) ->
                    "${stringResource(label)} (${ui.counts[tab] ?: "—"})"
                },
                onSelect = { if (!ui.busy) vm.refresh(tab = if (it == 0) "issues" else "prs") },
            )
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CodegFilterChip(label = stringResource(R.string.forge_open), selected = ui.filter == "open",
                    onClick = { vm.refresh(filter = "open") }, enabled = !ui.busy)
                CodegFilterChip(label = stringResource(R.string.forge_closed), selected = ui.filter == "closed",
                    onClick = { vm.refresh(filter = "closed") }, enabled = !ui.busy)
                CodegButton(onClick = { newIssue = true; vm.loadLabels() }, enabled = !ui.busy, size = CodegButtonSize.Sm) {
                    Text(stringResource(R.string.forge_new))
                }
            }
            if (ui.list.incomplete) Text(stringResource(R.string.forge_incomplete))
            if (ui.list.rows.isEmpty() && !ui.busy) Text(stringResource(R.string.forge_empty))
            ui.list.rows.forEach { row ->
                Column {
                    CodegListRow(title = "#${row.number} ${row.title}",
                        subtitle = listOfNotNull(row.author, row.updated_at).joinToString(" · "),
                        onClick = { if (!ui.busy) vm.open(row) })
                    if (row.labels.isNotEmpty()) {
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            row.labels.forEach { CodegBadge(it.name, variant = CodegBadgeVariant.Outline) }
                        }
                    }
                    CodegSeparator()
                }
            }
            FlowRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CodegButton(onClick = { vm.refresh(page = ui.list.page - 1) }, variant = CodegButtonVariant.Ghost,
                    enabled = !ui.busy && ui.list.page > 1) { Text(stringResource(R.string.forge_previous)) }
                Text(ui.list.page.toString())
                CodegButton(onClick = { vm.refresh(page = ui.list.page + 1) }, variant = CodegButtonVariant.Ghost,
                    enabled = !ui.busy && ui.list.has_next) { Text(stringResource(R.string.forge_next)) }
                CodegButton(onClick = { vm.refresh(page = ui.list.page) }, variant = CodegButtonVariant.Ghost,
                    enabled = !ui.busy) { Text(stringResource(R.string.forge_refresh)) }
            }
        }
    }
    if (newIssue) ForgeNewIssue(ui, vm) { newIssue = false }
}

@Composable
private fun ForgeNewIssue(ui: ForgeUi, vm: ForgeViewModel, dismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var labels by remember { mutableStateOf(setOf<String>()) }
    CodegDialog(title = stringResource(R.string.forge_new), onDismiss = { if (!ui.busy) dismiss() },
        footer = {
            CodegButton(onClick = dismiss, variant = CodegButtonVariant.Ghost, enabled = !ui.busy) { Text(stringResource(R.string.forge_cancel)) }
            CodegButton(onClick = { vm.createIssue(title, body, labels, dismiss) }, enabled = !ui.busy && title.isNotBlank()) { Text(stringResource(R.string.forge_create)) }
        }) {
        CodegTextField(title, { if (!ui.busy) title = it }, label = stringResource(R.string.forge_issue_title))
        CodegTextField(body, { if (!ui.busy) body = it }, label = stringResource(R.string.forge_body), singleLine = false)
        if (ui.labels.truncated) Text(stringResource(R.string.forge_incomplete))
        ui.labels.labels.forEach { label ->
            CodegFilterChip(label = label.name, selected = label.name in labels,
                onClick = { labels = if (label.name in labels) labels - label.name else labels + label.name }, enabled = !ui.busy)
        }
        ui.error?.let { Text(it.displayMessage(LocalContext.current), color = MaterialTheme.colorScheme.error) }
    }
}

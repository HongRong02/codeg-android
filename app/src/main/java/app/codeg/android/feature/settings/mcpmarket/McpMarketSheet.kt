package app.codeg.android.feature.settings.mcpmarket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.AgentType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun McpMarketSheet(viewModel: McpMarketViewModel, onDismiss: () -> Unit, onInstalled: () -> Unit) {
    val ui by viewModel.ui.collectAsState()
    val colors = CodegTheme.colors
    var confirm by remember { mutableStateOf(false) }
    val close = { if (!ui.installing) onDismiss() }
    Dialog(onDismissRequest = close, properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)) {
        Column(Modifier.fillMaxSize().background(colors.bg).windowInsetsPadding(WindowInsets.safeDrawing).imePadding()) {
            CodegTopBar(
                title = ui.detail?.name ?: stringResource(R.string.mcp_market_title),
                navigationIcon = { CodegIconButton(onClick = close, enabled = !ui.installing) { Icon(Icons.Rounded.Close, stringResource(R.string.mcp_close_cd)) } },
                showDivider = true,
            )
            LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ui.error?.let { message -> item { Text(message, color = colors.danger) } }
                if (ui.loading) item { LoadingView(stringResource(R.string.common_loading)) }
                val detail = ui.detail
                if (detail == null) {
                    item {
                        MarketSelect(ui.providers.find { it.id == ui.provider }?.name ?: stringResource(R.string.mcp_market_source), ui.providers.map { it.id to it.name }, !ui.loading) { viewModel.provider(it) }
                    }
                    item {
                        CodegTextField(ui.query, viewModel::query, stringResource(R.string.mcp_market_query))
                        TextButton(onClick = { viewModel.search() }, enabled = !ui.loading) { Text(stringResource(R.string.mcp_market_search)) }
                    }
                    items(ui.items, key = { it.provider_id + ":" + it.server_id }) { server ->
                        TextButton(onClick = { viewModel.detail(server) }, enabled = !ui.loading, modifier = Modifier.fillMaxWidth()) {
                            Column(Modifier.fillMaxWidth()) {
                                Text(server.name, color = colors.textPrimary)
                                Text(server.description, color = colors.textSecondary)
                            }
                        }
                    }
                    if (!ui.loading && ui.items.isEmpty()) item { Text(stringResource(R.string.mcp_market_empty), color = colors.textSecondary) }
                    if (ui.canLoadMore) item { TextButton(onClick = { viewModel.search(true) }, enabled = !ui.loading) { Text(stringResource(R.string.mcp_market_more)) } }
                    if (ui.error != null) item { TextButton(onClick = viewModel::load) { Text(stringResource(R.string.mcp_market_retry)) } }
                } else {
                    item { TextButton(onClick = viewModel::back, enabled = !ui.installing) { Text(stringResource(R.string.mcp_market_back)) } }
                    item { Text(detail.description, color = colors.textPrimary) }
                    item { MarketSelect(ui.option?.label.orEmpty(), detail.install_options.map { it.id to "${it.label} · ${it.protocol}" }, !ui.installing) { id -> detail.install_options.find { it.id == id }?.let(viewModel::option) } }
                    items(ui.option?.parameters.orEmpty(), key = { it.key }) { field ->
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            val label = field.label + if (field.required) " *" else ""
                            val value = ui.draft[field.key].orEmpty()
                            if (field.secret) SecretField(value, { if (!ui.installing) viewModel.field(field.key, it) }, label)
                            else if (field.enum_values.isNotEmpty() || field.kind == "boolean") {
                                val choices = field.enum_values.ifEmpty { listOf("true", "false") }
                                MarketSelect("$label: $value", choices.map { it to it }, !ui.installing) { viewModel.field(field.key, it) }
                            } else CodegTextField(value, { if (!ui.installing) viewModel.field(field.key, it) }, label, placeholder = field.placeholder, singleLine = field.kind != "json")
                            field.description?.let { Text(it, color = colors.textSecondary) }
                            field.location?.let { Text(it, color = colors.textSecondary) }
                        }
                    }
                    item {
                        Text(stringResource(R.string.mcp_enabled_for), color = colors.textSecondary)
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AgentType.entries.forEach { agent ->
                                CodegFilterChip(label = agent.shortName, selected = agent.wire in ui.apps, onClick = { if (!ui.installing) viewModel.app(agent.wire) })
                            }
                        }
                    }
                    if (ui.option == null) item { Text(stringResource(R.string.mcp_market_no_option), color = colors.textSecondary) }
                    item {
                        TextButton(onClick = { confirm = true }, enabled = !ui.installing && ui.option != null && ui.apps.isNotEmpty()) { Text(stringResource(R.string.mcp_market_install)) }
                    }
                }
            }
        }
        if (confirm) AlertDialog(
            onDismissRequest = { confirm = false },
            title = { Text(stringResource(R.string.mcp_market_install)) },
            text = { Text(stringResource(R.string.mcp_market_confirm, ui.detail?.name.orEmpty())) },
            confirmButton = { TextButton(onClick = { confirm = false; viewModel.install(onInstalled) }) { Text(stringResource(R.string.mcp_market_install)) } },
            dismissButton = { TextButton(onClick = { confirm = false }) { Text(stringResource(R.string.mcp_market_cancel)) } },
        )
    }
}

@Composable
private fun MarketSelect(label: String, choices: List<Pair<String, String>>, enabled: Boolean, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        TextButton(onClick = { expanded = true }, enabled = enabled && choices.isNotEmpty()) { Text(label) }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            choices.forEach { (id, name) -> DropdownMenuItem(text = { Text(name) }, onClick = { expanded = false; onSelect(id) }) }
        }
    }
}

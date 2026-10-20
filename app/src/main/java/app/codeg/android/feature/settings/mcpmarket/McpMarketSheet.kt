package app.codeg.android.feature.settings.mcpmarket

import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegDialog
import app.codeg.android.core.designsystem.component.CodegFilterChip
import app.codeg.android.core.designsystem.component.CodegFullScreenSheet
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.LoadingView
import app.codeg.android.core.designsystem.component.SecretField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.AgentType

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun McpMarketSheet(viewModel: McpMarketViewModel, onDismiss: () -> Unit, onInstalled: () -> Unit) {
    val ui by viewModel.ui.collectAsState()
    val colors = CodegTheme.colors
    var confirm by remember { mutableStateOf(false) }
    val close = { if (!ui.installing) onDismiss() }
    CodegFullScreenSheet(title = ui.detail?.name ?: stringResource(R.string.mcp_market_title), onDismiss = close) {

        LazyColumn(Modifier.heightIn(max = androidx.compose.ui.platform.LocalConfiguration.current.screenHeightDp.dp * 0.65f), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ui.error?.let { message -> item { Text(message, color = colors.danger) } }
            if (ui.loading) item { LoadingView(stringResource(R.string.common_loading)) }
            val detail = ui.detail
            if (detail == null) {
                item {
                    MarketSelect(ui.providers.find { it.id == ui.provider }?.name ?: stringResource(R.string.mcp_market_source), ui.providers.map { it.id to it.name }, !ui.loading) { viewModel.provider(it) }
                }
                item {
                    // TODO(T53): CodegInputGroup
                    CodegTextField(ui.query, viewModel::query, stringResource(R.string.mcp_market_query))
                    CodegButton(onClick = { viewModel.search() }, enabled = !ui.loading, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.mcp_market_search)) }
                }
                items(ui.items, key = { it.provider_id + ":" + it.server_id }) { server ->
                    CodegListRow(title = server.name, subtitle = server.description,
                        onClick = { if (!ui.loading) viewModel.detail(server) })
                }
                if (!ui.loading && ui.items.isEmpty()) item { Text(stringResource(R.string.mcp_market_empty), color = colors.textSecondary) }
                if (ui.canLoadMore) item { CodegButton(onClick = { viewModel.search(true) }, enabled = !ui.loading, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.mcp_market_more)) } }
                if (ui.error != null) item { CodegButton(onClick = viewModel::load, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.mcp_market_retry)) } }
            } else {
                item { CodegButton(onClick = viewModel::back, enabled = !ui.installing, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.mcp_market_back)) } }
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
                    CodegButton(onClick = { confirm = true }, enabled = !ui.installing && ui.option != null && ui.apps.isNotEmpty(), variant = CodegButtonVariant.Default) { Text(stringResource(R.string.mcp_market_install)) }
                }
            }
        }
    }
    if (confirm) CodegDialog(
        title = stringResource(R.string.mcp_market_install),
        onDismiss = { confirm = false },
        footer = {
            CodegButton(onClick = { confirm = false }, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.mcp_market_cancel)) }
            CodegButton(onClick = { confirm = false; viewModel.install(onInstalled) }, variant = CodegButtonVariant.Default) { Text(stringResource(R.string.mcp_market_install)) }
        },
        content = { Text(stringResource(R.string.mcp_market_confirm, ui.detail?.name.orEmpty())) },
    )
}

@Composable
private fun MarketSelect(label: String, choices: List<Pair<String, String>>, enabled: Boolean, onSelect: (String) -> Unit) {
    app.codeg.android.feature.settings.SettingsChoice(label, label, choices, enabled, onSelect)
}

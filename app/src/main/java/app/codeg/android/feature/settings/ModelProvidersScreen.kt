package app.codeg.android.feature.settings

import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegConfirmDialog
import app.codeg.android.core.designsystem.component.CodegDialog
import app.codeg.android.core.designsystem.component.CodegDropdownMenu
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegMenuItem
import app.codeg.android.core.designsystem.component.CodegSectionHeader
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.EmptyState
import app.codeg.android.core.designsystem.component.LoadingView
import app.codeg.android.core.designsystem.component.SecretField
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material3.ExperimentalMaterial3Api
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
import app.codeg.android.core.model.AgentType
import app.codeg.android.core.model.ModelProviderInfo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModelProvidersContent(viewModel: ModelProvidersViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors
    var editor by remember { mutableStateOf<ProviderEditorTarget?>(null) }

    Box(Modifier.fillMaxSize()) {
        when {
            ui.loading && ui.providers.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { LoadingView(stringResource(R.string.common_loading)) }
            else -> LazyColumn(Modifier.fillMaxSize(), contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 88.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (ui.providers.isEmpty() && !ui.loading) {
                    item("empty") { Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) { EmptyState(Icons.Rounded.Memory, stringResource(R.string.providers_empty_title), stringResource(R.string.providers_empty_subtitle)) } }
                }
                for (agent in ui.supportedAgents) {
                    val group = ui.providers.filter { it.agentType == agent }
                    if (group.isNotEmpty()) {
                        item("h-${agent.wire}") {
                            CodegSectionHeader(title = agent.displayName)
                        }
                        items(group, key = { it.id }) { p -> ProviderRow(p, onEdit = { editor = ProviderEditorTarget(p) }, onDelete = { viewModel.delete(p) }) }
                    }
                }
            }
        }
        if (!(ui.loading && ui.providers.isEmpty())) {
            CodegButton(onClick = { editor = ProviderEditorTarget(null) }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp), variant = CodegButtonVariant.Default) { Icon(Icons.Rounded.Add, contentDescription = stringResource(R.string.providers_new_cd)) }
        }
    }

    editor?.let { target ->
        ProviderEditor(
            existing = target.provider,
            supportedAgents = ui.supportedAgents,
            onDismiss = { editor = null },
            onSave = { name, url, key, agent, model -> viewModel.save(target.provider, name, url, key, agent, model); editor = null },
        )
    }
}

private data class ProviderEditorTarget(val provider: ModelProviderInfo?)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProviderRow(p: ModelProviderInfo, onEdit: () -> Unit, onDelete: () -> Unit) {
    val colors = CodegTheme.colors
    var menu by remember { mutableStateOf(false) }
    var deleting by remember { mutableStateOf(false) }
    Box {
        CodegListRow(title = p.name,
            subtitle = listOf(p.apiUrl, p.apiKeyMasked).filter { it.isNotEmpty() }.joinToString(" · "),
            onClick = onEdit, onLongClick = { menu = true }, onMoreClick = { menu = true })
        CodegDropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
            CodegMenuItem(text = { Text(stringResource(R.string.common_delete)) }, onClick = { menu = false; deleting = true })
        }
    }
    if (deleting) CodegConfirmDialog(
        title = stringResource(R.string.common_delete),
        message = stringResource(R.string.settings_delete_target_message),
        target = p.name,
        confirmLabel = stringResource(R.string.common_delete),
        destructive = true,
        onConfirm = { deleting = false; onDelete() },
        onDismiss = { deleting = false },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProviderEditor(
    existing: ModelProviderInfo?,
    supportedAgents: List<AgentType>,
    onDismiss: () -> Unit,
    onSave: (String, String, String, AgentType, String?) -> Unit,
) {
    val colors = CodegTheme.colors
    var name by remember { mutableStateOf(existing?.name ?: "") }
    var url by remember { mutableStateOf(existing?.apiUrl ?: "") }
    var key by remember { mutableStateOf("") }
    var agent by remember { mutableStateOf(existing?.agentType ?: supportedAgents.first()) }
    var model by remember { mutableStateOf(existing?.model ?: "") }

    CodegDialog(
        title = if (existing == null) stringResource(R.string.providers_new_title) else stringResource(R.string.providers_edit_title),
        onDismiss = onDismiss,
        footer = {
            CodegButton(onClick = onDismiss, variant = CodegButtonVariant.Ghost) { Text(stringResource(R.string.common_cancel)) }
            CodegButton(onClick = { onSave(name, url, key, agent, model.ifBlank { null }) }, enabled = name.isNotBlank() && url.isNotBlank(), variant = CodegButtonVariant.Default) { Text(stringResource(R.string.common_save)) }
        },
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CodegTextField(name, { name = it }, label = stringResource(R.string.server_name))
                CodegTextField(url, { url = it }, label = stringResource(R.string.providers_api_url), mono = true)
                SecretField(key, { key = it }, label = if (existing == null) stringResource(R.string.providers_api_key) else stringResource(R.string.providers_api_key_keep))
                SettingsChoice(stringResource(R.string.providers_agent), agent.wire,
                    supportedAgents.map { it.wire to it.displayName }) { wire ->
                        supportedAgents.firstOrNull { it.wire == wire }?.let { agent = it }
                    }
                CodegTextField(model, { model = it }, label = stringResource(R.string.providers_model_optional), mono = true)
            }
        },
    )
}

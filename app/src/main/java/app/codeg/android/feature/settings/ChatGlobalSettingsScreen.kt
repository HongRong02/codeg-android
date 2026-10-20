package app.codeg.android.feature.settings

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import app.codeg.android.core.designsystem.component.CodegSpinner
import app.codeg.android.core.designsystem.icon.*
import app.codeg.android.core.designsystem.component.CodegConfirmDialog
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonSize
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegCard
import app.codeg.android.core.designsystem.component.CodegCardContent
import app.codeg.android.core.designsystem.component.CodegCardHeader
import app.codeg.android.core.designsystem.component.CodegFilterChip
import app.codeg.android.core.designsystem.component.CodegFullScreenSheet
import app.codeg.android.core.designsystem.component.CodegSeparator
import app.codeg.android.core.designsystem.component.CodegSwitch
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.webSurface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.ChatEventCatalog
import app.codeg.android.core.model.ChatLanguageCatalog

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatGlobalSettingsDialog(onDismiss: () -> Unit, viewModel: ChatGlobalSettingsViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    var removingWebhook by remember { mutableStateOf<Pair<Int, String>?>(null) }
    val colors = CodegTheme.colors
    var newWebhook by remember { mutableStateOf("") }
    var prefixField by remember(ui.commandPrefix) { mutableStateOf(ui.commandPrefix) }

    CodegFullScreenSheet(title = stringResource(R.string.chatglobal_title), onDismiss = onDismiss) {

        if (ui.loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CodegSpinner() }
        } else {
            Column(Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Spacer(Modifier.height(2.dp))

                // Command prefix.
                GlobalSection(stringResource(R.string.chatglobal_command_prefix_title), stringResource(R.string.chatglobal_command_prefix_subtitle)) {
                    CodegTextField(prefixField, { prefixField = it }, label = stringResource(R.string.chatglobal_prefix_label), mono = true)
                    CodegButton(onClick = { viewModel.setCommandPrefix(prefixField) }, modifier = Modifier.padding(top = 8.dp), variant = CodegButtonVariant.Secondary) {
                        Text(stringResource(R.string.chatglobal_save_prefix))
                    }
                }

                // Language.
                GlobalSection(stringResource(R.string.chatglobal_reply_language_title), stringResource(R.string.chatglobal_reply_language_subtitle)) {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        for ((code, label) in ChatLanguageCatalog.options) {
                            CodegFilterChip(label = label, selected = ui.language.lowercase() == code, onClick = { viewModel.setLanguage(code) })
                        }
                    }
                }

                // Event filter.
                GlobalSection(stringResource(R.string.chatglobal_forwarded_events_title), stringResource(R.string.chatglobal_forwarded_events_subtitle)) {
                    CodegCard(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier) {
                            ChatEventCatalog.all.forEachIndexed { index, item ->
                                if (index > 0) CodegSeparator()
                                Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 11.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Column(Modifier.weight(1f)) {
                                        Text(item.label, fontSize = 14.sp, color = colors.textPrimary)
                                        item.note?.let { Text(it, fontSize = 11.sp, color = colors.web.mutedForeground) }
                                    }
                                    CodegSwitch(checked = ui.enabledEvents.contains(item.id), onCheckedChange = { viewModel.toggleEvent(item.id, it) })
                                }
                            }
                        }
                    }
                }

                // Webhooks.
                GlobalSection(stringResource(R.string.chatglobal_webhooks_title), stringResource(R.string.chatglobal_webhooks_subtitle)) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ui.webhooks.forEachIndexed { index, hook ->
                            Row(Modifier.fillMaxWidth().webSurface(CodegShapes.extraSmall).padding(start = 12.dp, top = 4.dp, bottom = 4.dp, end = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(hook.url, fontSize = 12.sp, fontFamily = FontFamily.Monospace, color = colors.textPrimary, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                CodegSwitch(checked = hook.enabled, onCheckedChange = { viewModel.setWebhookEnabled(index, it) })
                                IconButton(onClick = { removingWebhook = index to hook.url }) {
                                    Icon(Lucide.Trash2, stringResource(R.string.common_remove), tint = colors.danger, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(Modifier.weight(1f)) { CodegTextField(newWebhook, { newWebhook = it }, label = "https://…", mono = true) }
                            CodegButton(onClick = { viewModel.addWebhook(newWebhook); newWebhook = "" }, enabled = newWebhook.isNotBlank(), modifier = Modifier.size(48.dp), variant = CodegButtonVariant.Secondary, size = CodegButtonSize.Icon) {
                                Icon(Lucide.Plus, stringResource(R.string.chatglobal_add_webhook_cd), modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }

                ui.error?.let { Text(it, fontSize = 13.sp, color = colors.danger) }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
    removingWebhook?.let { (index, url) ->
        CodegConfirmDialog(title = stringResource(R.string.common_remove),
            message = stringResource(R.string.settings_delete_target_message), target = url,
            confirmLabel = stringResource(R.string.common_remove), destructive = true,
            onDismiss = { removingWebhook = null }, onConfirm = {
                // Refreshes may reorder the list while the confirmation is open.
                if (ui.webhooks.getOrNull(index)?.url == url) viewModel.removeWebhook(index)
                removingWebhook = null
        })
    }

}

@Composable
private fun GlobalSection(title: String, subtitle: String, content: @Composable () -> Unit) {
    CodegCard {
        CodegCardHeader(title = title, description = subtitle)
        CodegCardContent { content() }
    }
}

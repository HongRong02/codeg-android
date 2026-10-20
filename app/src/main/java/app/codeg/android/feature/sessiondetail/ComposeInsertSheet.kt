package app.codeg.android.feature.sessiondetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegSheet
import app.codeg.android.core.designsystem.component.CodegTabs
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.AgentType
import app.codeg.android.core.model.AvailableCommandInfo
import app.codeg.android.core.model.ExpertListItem
import app.codeg.android.core.model.QuickMessage

/** Draft transforms for the compose "+" inserts. Port of iOS `ComposeInsertModel`. */
object ComposeInsert {
    fun appendMessage(content: String, draft: String): String =
        if (draft.isBlank()) content else "${draft.trimEnd()} $content"

    fun appendCommand(name: String, draft: String): String =
        if (draft.isBlank()) "/$name " else "${draft.trimEnd()} /$name "

    /** Prepend `<prefix><id> `, replacing a leading expert mention if its id is known. */
    fun applyExpert(id: String, draft: String, agent: AgentType, knownIds: Set<String>): String {
        val prefix = if (agent == AgentType.CODEX) "$" else "/"
        val regex = Regex("^" + Regex.escape(prefix) + "([A-Za-z0-9_-]+)\\s+([\\s\\S]*)$")
        val rest = regex.find(draft)?.let { m -> if (knownIds.contains(m.groupValues[1])) m.groupValues[2] else null } ?: draft
        return if (rest.isBlank()) "$prefix$id " else "$prefix$id ${rest.trimStart()}"
    }
}

private enum class InsertSource { QUICK, EXPERTS, COMMANDS }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeInsertSheet(
    viewModel: SessionDetailViewModel,
    onInsert: ((String) -> String) -> Unit,
    onDismiss: () -> Unit,
) {
    val colors = CodegTheme.colors
    var source by remember { mutableStateOf(InsertSource.QUICK) }
    var loading by remember { mutableStateOf(true) }
    var quick by remember { mutableStateOf<List<QuickMessage>>(emptyList()) }
    var experts by remember { mutableStateOf<List<ExpertListItem>>(emptyList()) }
    var commands by remember { mutableStateOf<List<AvailableCommandInfo>>(emptyList()) }
    var knownIds by remember { mutableStateOf<Set<String>>(emptySet()) }

    LaunchedEffect(source) {
        loading = true
        when (source) {
            InsertSource.QUICK -> quick = viewModel.loadQuickMessages()
            InsertSource.EXPERTS -> { experts = viewModel.loadExpertsForInsert(); knownIds = viewModel.knownExpertIds() }
            InsertSource.COMMANDS -> commands = viewModel.loadSlashCommands()
        }
        loading = false
    }

    CodegSheet(title = stringResource(when (source) {
        InsertSource.QUICK -> R.string.insert_quick_messages
        InsertSource.EXPERTS -> R.string.insert_experts
        InsertSource.COMMANDS -> R.string.insert_commands
    }), onDismiss = onDismiss) {
        Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Segmented selector.
            val sources = InsertSource.entries
            val sourceLabels = listOf(
                stringResource(R.string.insert_quick_messages),
                stringResource(R.string.insert_experts),
                stringResource(R.string.insert_commands),
            )
            CodegTabs(
                tabs = sourceLabels,
                selected = sources.indexOf(source),
                onSelect = { source = sources[it] },
            )

            Box(Modifier.fillMaxWidth().heightIn(min = 120.dp, max = 420.dp)) {
                when {
                    loading -> Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = colors.accent) }
                    source == InsertSource.QUICK -> InsertList(quick.isEmpty(), stringResource(R.string.insert_empty_quick)) {
                        items(quick, key = { it.id }) { qm ->
                            InsertRow(qm.title.ifEmpty { qm.content }, qm.content) {
                                onInsert { draft -> ComposeInsert.appendMessage(qm.content, draft) }; onDismiss()
                            }
                        }
                    }
                    source == InsertSource.EXPERTS -> InsertList(experts.isEmpty(), stringResource(R.string.insert_empty_experts)) {
                        items(experts, key = { it.id }) { ex ->
                            InsertRow(ex.metadata.title(), ex.metadata.desc()) {
                                onInsert { draft -> ComposeInsert.applyExpert(ex.id, draft, viewModel.agentForInsert, knownIds) }; onDismiss()
                            }
                        }
                    }
                    else -> InsertList(commands.isEmpty(), stringResource(R.string.insert_empty_commands)) {
                        items(commands, key = { it.name }) { cmd ->
                            InsertRow("/${cmd.name}", cmd.description ?: "") {
                                onInsert { draft -> ComposeInsert.appendCommand(cmd.name, draft) }; onDismiss()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InsertList(empty: Boolean, emptyText: String, content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit) {
    val colors = CodegTheme.colors
    if (empty) {
        Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) { Text(emptyText, fontSize = 13.sp, color = colors.textTertiary) }
    } else {
        LazyColumn(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp), content = content)
    }
}

@Composable
private fun InsertRow(title: String, subtitle: String, onClick: () -> Unit) {
    // TODO(T53): CodegCommandList for slash commands and expert search.
    CodegListRow(title = title, subtitle = subtitle.takeIf { it.isNotEmpty() }, onClick = onClick)
}

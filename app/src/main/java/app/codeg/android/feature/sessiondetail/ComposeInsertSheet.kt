package app.codeg.android.feature.sessiondetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegCommandGroup
import app.codeg.android.core.designsystem.component.CodegCommandItem
import app.codeg.android.core.designsystem.component.CodegCommandList
import app.codeg.android.core.designsystem.component.CodegSheet
import app.codeg.android.core.designsystem.component.CodegSpinner
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
    var query by remember(source) { mutableStateOf("") }
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
                    loading -> Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) { CodegSpinner() }
                    else -> {
                        val entries = when (source) {
                            InsertSource.QUICK -> quick.map { CodegCommandItem(it.id.toString(), it.title.ifEmpty { it.content } + if (it.title.isNotEmpty()) " · ${it.content}" else "", listOf(it.content)) }
                            InsertSource.EXPERTS -> experts.map { CodegCommandItem(it.id, it.metadata.title() + " · ${it.metadata.desc()}", listOf(it.metadata.desc())) }
                            InsertSource.COMMANDS -> commands.map { CodegCommandItem(it.name, "/${it.name}" + (it.description?.let { desc -> " · $desc" } ?: ""), listOf(it.description.orEmpty())) }
                        }
                        CodegCommandList(query = query, onQueryChange = { query = it },
                            groups = listOf(CodegCommandGroup(sourceLabels[sources.indexOf(source)], entries)),
                            empty = stringResource(when (source) {
                                InsertSource.QUICK -> R.string.insert_empty_quick
                                InsertSource.EXPERTS -> R.string.insert_empty_experts
                                InsertSource.COMMANDS -> R.string.insert_empty_commands
                            }),
                            onSelect = { item ->
                                when (source) {
                                    InsertSource.QUICK -> quick.firstOrNull { it.id.toString() == item.id }?.let { qm ->
                                        onInsert { draft -> ComposeInsert.appendMessage(qm.content, draft) }
                                    }
                                    InsertSource.EXPERTS -> onInsert { draft -> ComposeInsert.applyExpert(item.id, draft, viewModel.agentForInsert, knownIds) }
                                    InsertSource.COMMANDS -> onInsert { draft -> ComposeInsert.appendCommand(item.id, draft) }
                                }
                                onDismiss()
                            })
                    }
                }
            }
        }
    }
}

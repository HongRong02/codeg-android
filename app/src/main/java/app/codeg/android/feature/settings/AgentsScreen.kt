package app.codeg.android.feature.settings

import app.codeg.android.core.designsystem.component.AgentAvatar
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegSwitch
import app.codeg.android.core.designsystem.component.EmptyState
import app.codeg.android.core.designsystem.component.LoadingView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.model.AcpAgentInfo
import app.codeg.android.core.model.AgentType

@Composable
fun AgentsContent(onOpen: (AgentType) -> Unit, viewModel: AgentsViewModel = hiltViewModel()) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize()) {
        when {
            ui.loading && ui.agents.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { LoadingView(stringResource(R.string.common_loading)) }
            ui.agents.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { EmptyState(Icons.Rounded.Person, stringResource(R.string.agents_empty_title), ui.error ?: stringResource(R.string.agents_empty_subtitle)) }
            else -> LazyColumn(Modifier.fillMaxSize(), contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(ui.agents, key = { it.agentType.wire }) { agent ->
                    AgentRow(agent, onToggle = { viewModel.setEnabled(agent, it) }, onOpen = { onOpen(agent.agentType) })
                }
            }
        }
    }
}

@Composable
private fun AgentRow(agent: AcpAgentInfo, onToggle: (Boolean) -> Unit, onOpen: () -> Unit) {
    CodegListRow(title = agent.name.ifEmpty { agent.agentType.displayName },
        subtitle = listOfNotNull(agent.installedVersion?.let { "v$it" }, agent.description.takeIf { it.isNotEmpty() }).joinToString(" · "),
        onClick = onOpen, modifier = Modifier.alpha(if (agent.available) 1f else 0.5f),
        leading = { AgentAvatar(agent.agentType, size = 38.dp) },
        trailing = { CodegSwitch(checked = agent.enabled, onCheckedChange = onToggle, enabled = agent.available) })
}

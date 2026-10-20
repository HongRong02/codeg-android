package app.codeg.android.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.codeg.android.R
import app.codeg.android.core.model.AcpAgentInfo

/** Read-only projection; never sends configuration or credential writes. */
@Composable
fun AgentConfigZCodeView(agent: AcpAgentInfo) {
    FormSection(
        title = stringResource(R.string.agent_zcode_title),
        footer = stringResource(R.string.agent_zcode_hint),
    ) {
        AgentConfigPresence(stringResource(R.string.agent_zcode_config), agent.configJson)
    }
}

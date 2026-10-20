package app.codeg.android.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.codeg.android.R
import app.codeg.android.core.model.AcpAgentInfo

/** Read-only projection; never sends configuration or credential writes. */
@Composable
fun AgentConfigKiroView(agent: AcpAgentInfo) {
    FormSection(
        title = stringResource(R.string.agent_kiro_title),
        footer = stringResource(R.string.agent_kiro_hint),
    ) {
        AgentConfigPresence(stringResource(R.string.agent_kiro_key), agent.env["KIRO_API_KEY"])
        AgentConfigReadOnlyValue(stringResource(R.string.agent_kiro_trust), agent.env["KIRO_TRUST_ALL_TOOLS"])
    }
}

package app.codeg.android.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.codeg.android.R
import app.codeg.android.core.model.AcpAgentInfo

/** Read-only projection; never sends configuration or credential writes. */
@Composable
fun AgentConfigTraeView(agent: AcpAgentInfo) {
    FormSection(
        title = stringResource(R.string.agent_trae_title),
        footer = stringResource(R.string.agent_trae_hint),
    ) {
        AgentConfigReadOnlyValue(stringResource(R.string.agent_trae_host), agent.env["TRAECLI_HOST"])
        AgentConfigPresence(stringResource(R.string.agent_trae_token), agent.env["TRAECLI_PERSONAL_ACCESS_TOKEN"])
    }
}

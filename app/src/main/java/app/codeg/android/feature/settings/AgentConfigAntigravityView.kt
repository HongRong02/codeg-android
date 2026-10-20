package app.codeg.android.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.codeg.android.R
import app.codeg.android.core.model.AcpAgentInfo

/** Read-only projection; never sends configuration or credential writes. */
@Composable
fun AgentConfigAntigravityView(agent: AcpAgentInfo) {
    FormSection(
        title = stringResource(R.string.agent_antigravity_title),
        footer = stringResource(R.string.agent_antigravity_hint),
    ) {
        AgentConfigReadOnlyValue(stringResource(R.string.agent_antigravity_method), agent.env["AGY_AUTH_METHOD"])
        AgentConfigPresence(stringResource(R.string.agent_antigravity_gemini_key), agent.env["GEMINI_API_KEY"])
        AgentConfigPresence(stringResource(R.string.agent_antigravity_google_key), agent.env["GOOGLE_API_KEY"])
        AgentConfigReadOnlyValue(stringResource(R.string.agent_antigravity_project), agent.env["GOOGLE_CLOUD_PROJECT"])
        AgentConfigReadOnlyValue(stringResource(R.string.agent_antigravity_location), agent.env["GOOGLE_CLOUD_LOCATION"])
    }
}

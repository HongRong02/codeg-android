package app.codeg.android.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.codeg.android.R
import app.codeg.android.core.model.AcpAgentInfo

/** Read-only projection; never sends configuration or credential writes. */
@Composable
fun AgentConfigCommandCodeView(agent: AcpAgentInfo) {
    FormSection(
        title = stringResource(R.string.agent_command_code_title),
        footer = stringResource(R.string.agent_command_code_hint),
    ) {
        AgentConfigPresence(stringResource(R.string.agent_command_code_key), agent.env["COMMAND_CODE_API_KEY"])
    }
}

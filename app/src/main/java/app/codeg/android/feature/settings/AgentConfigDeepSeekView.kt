package app.codeg.android.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.AcpAgentInfo

/** Read-only projection; never sends configuration or credential writes. */
@Composable
fun AgentConfigDeepSeekView(agent: AcpAgentInfo) {
    FormSection(
        title = stringResource(R.string.agent_deepseek_title),
        footer = stringResource(R.string.agent_deepseek_hint),
    ) {
        AgentConfigReadOnlyValue(stringResource(R.string.agent_deepseek_endpoint), agent.env["DEEPSEEK_BASE_URL"])
        AgentConfigPresence(stringResource(R.string.agent_deepseek_key), agent.env["DEEPSEEK_API_KEY"])
    }
}

/** Shared read-only fields intentionally omit raw secrets and document contents. */
@Composable
internal fun AgentConfigPresence(label: String, value: String?) {
    AgentConfigReadOnlyValue(
        label,
        if (value.isNullOrBlank()) null else stringResource(R.string.agent_deepseek_present),
    )
}

@Composable
internal fun AgentConfigReadOnlyValue(label: String, value: String?) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(label, color = CodegTheme.colors.textSecondary)
        Text(
            text = value?.takeIf { it.isNotBlank() } ?: stringResource(R.string.agent_deepseek_unset),
            color = CodegTheme.colors.textPrimary,
        )
    }
}

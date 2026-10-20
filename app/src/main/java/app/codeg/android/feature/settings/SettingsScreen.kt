package app.codeg.android.feature.settings

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import app.codeg.android.core.designsystem.icon.*
import app.codeg.android.core.designsystem.component.CodegCard
import app.codeg.android.core.designsystem.component.CodegIconButton
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegSectionHeader
import app.codeg.android.core.designsystem.component.CodegSeparator
import app.codeg.android.core.designsystem.component.CodegTabs
import app.codeg.android.core.designsystem.component.CodegTopBar
import app.codeg.android.core.designsystem.component.SettingsIconBadge
import androidx.compose.foundation.layout.imePadding
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.PillShape
import app.codeg.android.core.designsystem.theme.WebThemePreset
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.model.AgentType

private enum class SettingsLeaf(@StringRes val titleRes: Int, val icon: ImageVector, @StringRes val groupRes: Int, val implemented: Boolean) {
    APPEARANCE(R.string.settings_leaf_appearance, Lucide.Palette, R.string.settings_group_personalization, true),
    LANGUAGE(R.string.settings_leaf_language, Lucide.Globe, R.string.settings_group_personalization, true),
    GENERAL(R.string.settings_leaf_general, Lucide.SlidersHorizontal, R.string.settings_group_personalization, true),
    QUICK_MESSAGES(R.string.settings_leaf_quick_messages, Lucide.MessageSquareText, R.string.settings_group_personalization, true),
    AGENTS(R.string.settings_leaf_agents, Lucide.Bot, R.string.settings_group_ai_agents, true),
    MODEL_PROVIDERS(R.string.settings_leaf_model_providers, Lucide.Server, R.string.settings_group_ai_agents, true),
    ASSISTANTS(R.string.assistants_title, Lucide.User, R.string.settings_group_ai_agents, true),
    EXPERTS(R.string.settings_leaf_experts, Lucide.GraduationCap, R.string.settings_group_ai_agents, true),
    SKILLS(R.string.settings_leaf_skills, Lucide.BookOpenText, R.string.settings_group_ai_agents, true),
    MCP(R.string.settings_leaf_mcp, Lucide.PlugZap, R.string.settings_group_ai_agents, true),
    VERSION_CONTROL(R.string.settings_leaf_version_control, Lucide.GitBranch, R.string.settings_group_integrations, true),
    WORK_TASKS(R.string.work_tasks_title, Lucide.Clock, R.string.settings_group_integrations, true),
    SERVER_LOGS(R.string.logs_stats_title, Lucide.Info, R.string.settings_group_system, true),
    AUTOMATIONS(R.string.automations_title, Lucide.Clock, R.string.settings_group_integrations, true),
    CHAT_CHANNELS(R.string.settings_leaf_chat_channels, Lucide.SendHorizontal, R.string.settings_group_integrations, true),
    SYSTEM(R.string.settings_leaf_system, Lucide.Settings, R.string.settings_group_system, true),
    OPEN_SOURCE_LICENSES(R.string.settings_leaf_open_source_licenses, Lucide.Shield, R.string.settings_group_system, true),
    ABOUT(R.string.settings_leaf_about, Lucide.Info, R.string.settings_group_system, true),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onOpenAutomations: () -> Unit = {},
    onOpenWorkTasks: () -> Unit = {},
    onOpenServerLogs: () -> Unit = {},
    logsViewModel: app.codeg.android.feature.serverlogs.ServerLogsViewModel = hiltViewModel(),
    viewModel: SettingsViewModel = hiltViewModel(),
    onPageChanged: () -> Unit = {},
) {
    val logsUi by logsViewModel.ui.collectAsStateWithLifecycle()
    var leaf by remember { mutableStateOf<SettingsLeaf?>(null) }
    // A second level beneath the Agents leaf: the per-agent config editor.
    var agentDetail by remember { mutableStateOf<AgentType?>(null) }
    val inAgentDetail = leaf == SettingsLeaf.AGENTS && agentDetail != null
    // Internal pages share one navigation entry, so notify the shell explicitly.
    LaunchedEffect(leaf, agentDetail) {
        onPageChanged()
    }
    BackHandler(enabled = leaf != null) { if (inAgentDetail) agentDetail = null else leaf = null }

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CodegTopBar(
                title = when {
                    inAgentDetail -> agentDetail!!.displayName
                    leaf != null -> stringResource(leaf!!.titleRes)
                    else -> stringResource(R.string.tab_settings)
                },
                showDivider = true,
                navigationIcon = {
                    if (leaf != null) CodegIconButton(onClick = { if (inAgentDetail) agentDetail = null else leaf = null }) {
                        Icon(Lucide.ArrowLeft, contentDescription = stringResource(R.string.common_back), modifier = Modifier.size(16.dp))
                    }
                },
            )
        },
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize().imePadding()) {
            when (leaf) {
                null -> SettingsRoot(
                    onSelect = {
                        if (it == SettingsLeaf.SERVER_LOGS) {
                            onOpenServerLogs()
                        } else if (it == SettingsLeaf.AUTOMATIONS) {
                            onOpenAutomations()
                        } else {
                            leaf = it
                            agentDetail = null
                        }
                    },
                    onOpenAutomations = onOpenAutomations,
                    onOpenWorkTasks = onOpenWorkTasks,
                    showServerLogs = logsUi.available,
                )
                SettingsLeaf.APPEARANCE -> AppearanceSettings(viewModel)
                SettingsLeaf.LANGUAGE -> LanguageContent()
                SettingsLeaf.ABOUT -> AboutScreen(viewModel)
                SettingsLeaf.QUICK_MESSAGES -> QuickMessagesContent()
                SettingsLeaf.GENERAL -> GeneralContent()
                SettingsLeaf.MODEL_PROVIDERS -> ModelProvidersContent()
                SettingsLeaf.SYSTEM -> SystemContent()
                SettingsLeaf.OPEN_SOURCE_LICENSES -> OpenSourceLicensesScreen()
                SettingsLeaf.MCP -> McpContent()
                SettingsLeaf.AGENTS ->
                if (agentDetail == null) AgentsContent(onOpen = { agentDetail = it })
                else AgentDetailContent(agentDetail!!, onClose = { agentDetail = null })
                SettingsLeaf.SKILLS -> SkillsContent()
                SettingsLeaf.EXPERTS -> ExpertsContent()
                SettingsLeaf.ASSISTANTS -> AssistantsContent()
                SettingsLeaf.CHAT_CHANNELS -> ChatChannelsContent()
                SettingsLeaf.VERSION_CONTROL -> VersionControlContent()
                else -> ComingSoon(leaf!!.titleRes)
            }
        }
    }
}

@Composable
private fun SettingsRoot(
    onSelect: (SettingsLeaf) -> Unit,
    onOpenAutomations: () -> Unit,
    onOpenWorkTasks: () -> Unit,
    showServerLogs: Boolean,
) {
    val workTasks: app.codeg.android.feature.worktasks.WorkTaskViewModel = hiltViewModel()
    val workTaskUi by workTasks.ui.collectAsStateWithLifecycle()
    val assistants: AssistantsViewModel = hiltViewModel()
    val assistantUi by assistants.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 12.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (group in SettingsLeaf.entries.map { it.groupRes }.distinct()) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                CodegSectionHeader(title = stringResource(group))
                CodegCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier) {
                        val leaves = SettingsLeaf.entries.filter {
                            it.groupRes == group &&
                            (it != SettingsLeaf.WORK_TASKS || workTaskUi.supported) &&
                            (it != SettingsLeaf.SERVER_LOGS || showServerLogs) &&
                            (it != SettingsLeaf.ASSISTANTS || (!assistantUi.loading && assistantUi.supported))
                        }
                        leaves.forEachIndexed { index, l ->
                            CodegListRow(title = stringResource(l.titleRes), onClick = {
                                if (l == SettingsLeaf.WORK_TASKS) {
                                    onOpenWorkTasks()
                                } else if (l == SettingsLeaf.AUTOMATIONS) {
                                    onOpenAutomations()
                                } else {
                                    onSelect(l)
                                }
                            }, modifier = Modifier, leading = { SettingsIconBadge(l.icon) }, trailing = {
                                Icon(
                                    Lucide.ChevronRight,
                                    contentDescription = null,
                                    tint = colors.textSecondary,
                                    modifier = Modifier.size(16.dp),
                                )
                            })
                            if (index < leaves.lastIndex) {
                                CodegSeparator(modifier = Modifier.padding(start = 60.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun AppearanceSettings(viewModel: SettingsViewModel) {
    val colors = CodegTheme.colors
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CodegCard(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(stringResource(R.string.settings_theme), style = MaterialTheme.typography.titleSmall, color = colors.textSecondary)
                val modes = ThemeMode.entries
                CodegTabs(tabs = modes.map { mode ->
                    stringResource(when (mode) {
                        ThemeMode.SYSTEM -> R.string.settings_core_theme_system
                        ThemeMode.LIGHT -> R.string.settings_core_theme_light
                        ThemeMode.DARK -> R.string.settings_core_theme_dark
                    })
                }, selected = modes.indexOf(settings.themeMode), onSelect = { viewModel.setThemeMode(modes[it]) })
            }
        }
        CodegCard(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(stringResource(R.string.settings_accent), fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = colors.textSecondary)
                FlowRow(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    for (preset in WebThemePreset.entries) {
                        val selected = WebThemePreset.from(settings.accentId) == preset
                        val swatch = preset.resolve(colors.isDark)
                        Box(
                            Modifier.size(34.dp).clip(CircleShape).background(swatch.primary)
                                .border(1.dp, swatch.border, CircleShape)
                                .then(if (selected) Modifier.border(3.dp, colors.web.ring, CircleShape) else Modifier)
                                .clickable { viewModel.setAccent(preset.id) },
                        )
                    }
                }
            }
        }
        // Live preview.
        CodegCard(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.settings_preview), fontSize = 13.sp, color = colors.textSecondary)
                Text(stringResource(R.string.settings_accent_preview_caption), fontSize = 14.sp, color = colors.textPrimary)
                Box(Modifier.clip(PillShape).background(colors.accent).padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(stringResource(R.string.settings_accent_button), color = colors.onAccent, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun AboutScreen(viewModel: SettingsViewModel) {
    val colors = CodegTheme.colors
    val about by viewModel.about.collectAsStateWithLifecycle()
    val appVersion = "${app.codeg.android.BuildConfig.VERSION_NAME} (${app.codeg.android.BuildConfig.VERSION_CODE})"
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineSmall, color = colors.textPrimary)
        Text(stringResource(R.string.settings_version, appVersion), fontSize = 13.sp, color = colors.textSecondary)
        app.codeg.android.core.update.UpdateAbout()
        about.serverName?.let { Text(stringResource(R.string.settings_server, it), fontSize = 13.sp, color = colors.textSecondary) }
        about.serverVersion?.let { Text(stringResource(R.string.settings_codeg_version, it), fontSize = 13.sp, color = colors.textSecondary) }
        Text(
            stringResource(R.string.settings_about_description),
            fontSize = 13.sp, color = colors.textSecondary, modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Composable
private fun ComingSoon(@StringRes title: Int) {
    val colors = CodegTheme.colors
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Lucide.Zap, contentDescription = null, tint = colors.textSecondary, modifier = Modifier.size(36.dp))
            Text(stringResource(title), color = colors.textPrimary, fontWeight = FontWeight.SemiBold)
            Text(stringResource(R.string.settings_coming_soon), fontSize = 13.sp, color = colors.textSecondary)
        }
    }
}

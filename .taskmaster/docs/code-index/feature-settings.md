# feature/settings

Source root: `app/src/main/java/app/codeg/android/`

## feature/settings/AgentConfigAntigravityView.kt

| Line | Declaration |
|---|---|
| 10 | `@Composable fun AgentConfigAntigravityView(agent: AcpAgentInfo)` |

## feature/settings/AgentConfigCommandCodeView.kt

| Line | Declaration |
|---|---|
| 10 | `@Composable fun AgentConfigCommandCodeView(agent: AcpAgentInfo)` |

## feature/settings/AgentConfigDeepSeekView.kt

| Line | Declaration |
|---|---|
| 15 | `@Composable fun AgentConfigDeepSeekView(agent: AcpAgentInfo)` |
| 27 | `@Composable internal fun AgentConfigPresence(label: String, value: String?)` |
| 35 | `@Composable internal fun AgentConfigReadOnlyValue(label: String, value: String?)` |

## feature/settings/AgentConfigKimiView.kt

| Line | Declaration |
|---|---|
| 64 | `private data class KimiBanner(val text: String, val isError: Boolean)` |
| 78 | `@Composable fun AgentConfigKimiView(agent: AcpAgentInfo, viewModel: AgentsViewModel)` |
| 79 |   `val context` |
| 80 |   `val colors` |
| 81 |   `val scope` |
| 85 |   `val seed` |
| 86 |   `val seedInterface` |
| 87 |   `var mode by remember` |
| 88 |   `var interfaceType by remember` |
| 89 |   `var region by remember` |
| 90 |   `var baseUrl by remember` |
| 91 |   `var authType by remember` |
| 92 |   `var apiKey by remember` |
| 93 |   `var modelId by remember` |
| 94 |   `var maxContext by remember` |
| 95 |   `var vertexProject by remember` |
| 96 |   `var vertexLocation by remember` |
| 97 |   `var rawConfig by remember` |
| 98 |   `var models by remember` |
| 99 |   `var saving by remember` |
| 100 |   `var fetchingModels by remember` |
| 101 |   `var banner by remember` |
| 103 |   `val meta` |
| 104 |   `val isKimi` |
| 105 |   `val isVertex` |
| 106 |   `val effectiveBaseUrl` |
| 107 |   `val liveConfig` |
| 115 |   `val savedMsg` |
| 116 |   `val modelRequiredMsg` |
| 117 |   `val enterKeyMsg` |
| 118 |   `val modelsNoneMsg` |
| 119 |   `val modelsErrMsg` |
| 120 |   `val modelsFoundFmt` |
| 122 |   `fun runSave(body: UpdateKimiCodeConfigBody)` |
| 137 |   `fun handleSave()` |
| 161 |   `fun fetchModels()` |
| 183 |   `fun setInterface(next: KimiInterfaceType)` |
| 312 | `@Composable private fun KimiGateBanner(present: Boolean, mode: KimiAuthMode)` |
| 313 |   `val colors` |
| 314 |   `val tint` |
| 315 |   `val text` |
| 330 | `@Composable private fun KimiResultBanner(banner: KimiBanner)` |
| 331 |   `val colors` |
| 332 |   `val tint` |
| 344 | `@Composable internal fun Caption(text: String)` |

## feature/settings/AgentConfigKiroView.kt

| Line | Declaration |
|---|---|
| 10 | `@Composable fun AgentConfigKiroView(agent: AcpAgentInfo)` |

## feature/settings/AgentConfigPiView.kt

| Line | Declaration |
|---|---|
| 64 | `private data class PiBanner(val text: String, val isError: Boolean)` |
| 76 | `@Composable fun AgentConfigPiView(agent: AcpAgentInfo, viewModel: AgentsViewModel)` |
| 77 |   `val context` |
| 78 |   `val colors` |
| 79 |   `val scope` |
| 81 |   `val seedEnv` |
| 83 |   `val seedCmd` |
| 84 |   `var mode by remember` |
| 85 |   `var command by remember` |
| 86 |   `var configDir by remember` |
| 87 |   `var sessionDir by remember` |
| 88 |   `var validation by remember` |
| 89 |   `var validating by remember` |
| 90 |   `var piStatus by remember` |
| 91 |   `var checkingPi by remember` |
| 92 |   `var confirmUninstall by remember` |
| 93 |   `var piOp by remember` |
| 94 |   `var savingRuntime by remember` |
| 97 |   `var selectedProvider by remember` |
| 98 |   `var customId by remember` |
| 99 |   `var customBaseUrl by remember` |
| 100 |   `var customApi by remember` |
| 101 |   `var customProviders by remember` |
| 102 |   `var modelText by remember` |
| 103 |   `var thinkingLevel by remember` |
| 104 |   `var apiKey by remember` |
| 105 |   `var authProviders by remember` |
| 106 |   `var savingCreds by remember` |
| 107 |   `var loadingCreds by remember` |
| 110 |   `var trustWorkspace by remember` |
| 111 |   `var savingTrust by remember` |
| 113 |   `var banner by remember` |
| 115 |   `val isCustomProvider` |
| 116 |   `val effectiveProvider` |
| 117 |   `val providerHasKey` |
| 118 |   `val customIncomplete` |
| 119 |   `val credsIncomplete` |
| 122 |   `val providerOptions` |
| 132 |   `val runtimeSaved` |
| 133 |   `val configSaved` |
| 134 |   `val providerModelRequired` |
| 135 |   `val baseUrlRequired` |
| 136 |   `val installedMsg` |
| 137 |   `val uninstalledMsg` |
| 138 |   `val installFailed` |
| 139 |   `val uninstallFailed` |
| 140 |   `val trustFailed` |
| 171 |   `fun detectPi()` |
| 179 |   `fun handleValidate()` |
| 190 |   `fun handleSaveRuntime()` |
| 206 |   `fun handleSaveCreds()` |
| 246 |   `fun handlePiOp(install: Boolean)` |
| 262 |   `fun toggleTrust(next: Boolean)` |
| 420 | `@Composable private fun PiActionButton(text: String, busy: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 421 |   `val colors` |
| 429 | `@Composable private fun PiResultBanner(banner: PiBanner)` |
| 430 |   `val colors` |
| 431 |   `val tint` |
| 442 | `@Composable private fun piThinkingLabel(level: String): String` |

## feature/settings/AgentConfigQoderView.kt

| Line | Declaration |
|---|---|
| 10 | `@Composable fun AgentConfigQoderView(agent: AcpAgentInfo)` |

## feature/settings/AgentConfigTraeView.kt

| Line | Declaration |
|---|---|
| 10 | `@Composable fun AgentConfigTraeView(agent: AcpAgentInfo)` |

## feature/settings/AgentConfigZCodeView.kt

| Line | Declaration |
|---|---|
| 10 | `@Composable fun AgentConfigZCodeView(agent: AcpAgentInfo)` |

## feature/settings/AgentDetailScreen.kt

| Line | Declaration |
|---|---|
| 101 | `@Composable fun AgentDetailContent( agentType: AgentType, onClose: () -> Unit, viewModel: AgentsViewModel = hiltViewModel(), )` |
| 106 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 107 |   `val detail` |
| 120 |   `val selfContained` |
| 174 | `@Composable private fun AgentHeader(state: AgentDetailState, onToggle: (Boolean) -> Unit)` |
| 175 |   `val colors` |
| 176 |   `val agent` |
| 208 | `@Composable private fun VersionLine(state: AgentDetailState)` |
| 209 |   `val colors` |
| 210 |   `val installed` |
| 211 |   `val latest` |
| 212 |   `val text` |
| 222 | `@Composable private fun SaveBar(canSave: Boolean, saving: Boolean, error: String?, onSave: () -> Unit)` |
| 223 |   `val colors` |
| 238 | `@Composable private fun ClaudeSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 239 |   `val d` |
| 284 | `private fun claudeFooterRes(mode: ClaudeAuthMode): Int` |
| 291 | `@Composable private fun CodexSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 292 |   `val colors` |
| 293 |   `val context` |
| 294 |   `val d` |
| 332 | `private fun codexEffortLabelRes(e: CodexReasoningEffort): Int` |
| 340 | `@Composable private fun GeminiSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 341 |   `val colors` |
| 342 |   `val d` |
| 392 | `@Composable private fun OpenClawSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 393 |   `val d` |
| 402 | `@Composable private fun ClineSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 403 |   `val d` |
| 418 | `@Composable private fun OpenCodeSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 419 |   `val d` |
| 427 | `@Composable private fun HermesSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 428 |   `val d` |
| 429 |   `val option` |
| 443 | `private fun hermesFooterRes(kind: HermesProviderKind?): Int` |
| 457 | `@Composable private fun CodeBuddySection(state: AgentDetailState, vm: AgentsViewModel)` |
| 458 |   `val colors` |
| 459 |   `val d` |
| 460 |   `val selfHosted` |
| 504 | `@Composable private fun ServerManagedSection()` |
| 514 | `@Composable private fun GrokSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 515 |   `val colors` |
| 516 |   `val d` |
| 517 |   `val useDefault` |
| 571 | `@Composable private fun CursorSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 572 |   `val colors` |
| 573 |   `val d` |
| 574 |   `val custom` |
| 576 |   `var auth by remember` |
| 577 |   `var authLoading by remember` |
| 578 |   `var models by remember` |
| 579 |   `var modelsError by remember` |
| 580 |   `var modelsLoading by remember` |
| 583 |   `val probeKey` |
| 584 |   `var refreshTick by remember` |
| 665 |   `val savedModel` |
| 727 | `@Composable private fun CursorStatusRow(auth: CursorAuthStatus?, loading: Boolean, onRefresh: () -> Unit)` |
| 728 |   `val colors` |
| 729 |   `val installed` |
| 730 |   `val signedIn` |
| 731 |   `val tint` |
| 736 |   `val text` |
| 777 | `@Composable private fun RuleEditor( label: String, rules: List<String>, placeholder: String, tone: Color, onChange: (List<String>) -> Unit, )` |
| 784 |   `val colors` |
| 812 | `@Composable private fun ColumnScope.ProviderPicker(state: AgentDetailState, vm: AgentsViewModel)` |
| 813 |   `val colors` |
| 831 | `@Composable private fun ProviderSummary(p: ModelProviderInfo)` |
| 840 | `@Composable private fun ReadonlyRow(label: String, value: String)` |
| 841 |   `val colors` |
| 853 | `@Composable internal fun FormSection(title: String, footer: String? = null, content: @Composable ColumnScope.() -> Unit)` |
| 863 | `@Composable internal fun <T> SelectField(label: String, value: T, options: List<Pair<T, String>>, onSelect: (T) -> Unit)` |
| 864 |   `val keys` |
| 865 |   `val selected` |
| 871 | `@Composable private fun HermesProviderField(value: String, onSelect: (String) -> Unit)` |
| 872 |   `var expanded by remember` |
| 893 | `private fun kindLabelRes(kind: HermesProviderKind): Int` |
| 900 | `@Composable private fun ToggleRow(title: String, checked: Boolean, onChange: (Boolean) -> Unit)` |
| 907 | `@Composable internal fun ColumnScope.ExpandableGroup(title: String, content: @Composable ColumnScope.() -> Unit)` |
| 908 |   `val colors` |
| 909 |   `var open by remember` |
| 910 |   `val rotation by animateFloatAsState(if (open) 180f else 0f, label = "chevron")` |
| 925 | `@Composable private fun TonalAction(text: String, onClick: () -> Unit)` |
| 926 |   `val colors` |
| 932 | `@Composable private fun AdvancedSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 933 |   `val colors` |
| 934 |   `var open by remember` |
| 935 |   `val rotation by animateFloatAsState(if (open) 180f else 0f, label = "advanced")` |
| 936 |   `val label` |
| 941 |   `val text` |

## feature/settings/AgentsScreen.kt

| Line | Declaration |
|---|---|
| 30 | `@Composable fun AgentsContent(onOpen: (AgentType) -> Unit, viewModel: AgentsViewModel = hiltViewModel())` |
| 31 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 47 | `@Composable private fun AgentRow(agent: AcpAgentInfo, onToggle: (Boolean) -> Unit, onOpen: () -> Unit)` |

## feature/settings/AgentsViewModel.kt

| Line | Declaration |
|---|---|
| 49 | `class AgentsViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 51 |   `private val repository: ServerRepository` |
| 54 |   `private val _ui` |
| 55 |   `val ui: StateFlow<AgentsUiState>` |
| 57 |   `private var client: CodegClient?` |
| 65 |   `private val opMutex` |
| 71 |   `fun load()` |
| 75 |   `private suspend fun reload()` |
| 101 |   `fun setEnabled(agent: AcpAgentInfo, enabled: Boolean)` |
| 124 |   `private fun applyEnabled(type: AgentType, enabled: Boolean)` |
| 142 |   `fun openDetail(agentType: AgentType)` |
| 167 |   `fun closeDetail()` |
| 170 |   `fun editDraft(transform: (AgentDraft) -> AgentDraft)` |
| 180 |   `fun rebakeDraft()` |
| 183 |   `fun editRawConfig(text: String)` |
| 193 |   `fun setModelProvider(id: Int?)` |
| 195 |   `fun setClaudeAuthMode(mode: ClaudeAuthMode)` |
| 208 |   `fun setCodexAuthMode(mode: CodexAuthMode)` |
| 216 |   `fun setGeminiAuthMode(mode: GeminiAuthMode)` |
| 221 |   `fun setHermesProvider(id: String)` |
| 233 |   `fun save(onSuccess: () -> Unit)` |
| 321 |   `suspend fun saveKimiCodeConfig(body: UpdateKimiCodeConfigBody)` |
| 330 |   `suspend fun fetchKimiModels(baseUrl: String, apiKey: String): List<String>` |
| 344 |   `suspend fun updatePiEnv(agent: AcpAgentInfo, env: Map<String, String>)` |
| 353 |   `suspend fun loadPiConfig(): PiConfigProjection` |
| 362 |   `suspend fun savePiConfig(body: UpdatePiConfigBody)` |
| 368 |   `suspend fun validatePiCommand(command: String): PiCommandValidation` |
| 374 |   `suspend fun installPiBinary()` |
| 379 |   `suspend fun uninstallPiBinary()` |
| 393 |   `suspend fun cursorAuthStatus(apiKey: String): CursorAuthStatus` |
| 399 |   `suspend fun cursorListModels(apiKey: String): CursorModelsResult` |
| 406 |   `private fun updateDetail(block: (AgentDetailState) -> AgentDetailState)` |
| 409 |   `private fun agentSupportsProviders(agent: AgentType): Boolean` |
| 412 |   `private data class ConfigPayload( val configJson: String?, val opencodeAuthJson: String?, val codexAuthJson: String?, val codexConfigToml: String?, val grokConfigToml: String? = null, val cursorCliConfigJson: String? = null, val cursorStructured: CursorStructuredConfig? = null, )` |
| 423 |   `private fun makeConfigBody(agentType: AgentType, draft: AgentDraft, original: AcpAgentInfo): ConfigPayload` |
| 471 |   `private data class HermesBody(val provider: String, val model: String?, val apiKey: FieldEdit, val baseUrl: FieldEdit)` |
| 473 |   `private fun hermesBody(d: AgentDraft): HermesBody` |
| 494 | `internal fun toggleWriteSource(agents: List<AcpAgentInfo>, captured: AcpAgentInfo): AcpAgentInfo` |
| 507 | `internal fun grokConfigTomlForSave(draft: AgentDraft, snapshot: AcpAgentInfo, freshToml: String): String?` |
| 509 |   `val pmChanged` |
| 510 |   `val reChanged` |
| 523 | `private val nativeRawConfigAgents` |
| 531 | `internal fun cursorCliConfigForSave(draft: AgentDraft, original: AcpAgentInfo): String?` |
| 548 | `internal fun cursorStructuredForSave(draft: AgentDraft, original: AcpAgentInfo): CursorStructuredConfig?` |
| 557 | `data class AgentsUiState( val agents: List<AcpAgentInfo> = emptyList(), val loading: Boolean = false, val error: String? = null, val detail: AgentDetailState? = null, )` |
| 558 |   `val agents: List<AcpAgentInfo>` |
| 559 |   `val loading: Boolean` |
| 560 |   `val error: String?` |
| 561 |   `val detail: AgentDetailState?` |
| 564 | `data class AgentDetailState( val agentType: AgentType, val agent: AcpAgentInfo, val draft: AgentDraft, val original: AgentDraft, val providers: List<ModelProviderInfo> = emptyList(), val saving: Boolean = false, val saveError: String? = null, )` |
| 565 |   `val agentType: AgentType` |
| 566 |   `val agent: AcpAgentInfo` |
| 567 |   `val draft: AgentDraft` |
| 568 |   `val original: AgentDraft` |
| 569 |   `val providers: List<ModelProviderInfo>` |
| 570 |   `val saving: Boolean` |
| 571 |   `val saveError: String?` |
| 573 |   `val dirty: Boolean get()` |
| 574 |   `val missingProvider: Boolean get()` |
| 576 |   `val missingCodeBuddyBaseUrl: Boolean get()` |
| 578 |   `val missingCursorApiKey: Boolean get()` |
| 579 |   `val canSave: Boolean` |
| 581 |   `val configError: String?` |
| 584 |   `val provider: ModelProviderInfo? get()` |

## feature/settings/AssistantsScreen.kt

| Line | Declaration |
|---|---|
| 37 | `@Composable fun AssistantsContent(viewModel: AssistantsViewModel = hiltViewModel())` |
| 38 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 39 |   `val colors` |
| 40 |   `var deleting by remember(ui.generation)` |
| 100 | `@Composable private fun AssistantEditor(ui: AssistantsUi, vm: AssistantsViewModel)` |
| 101 |   `val old` |
| 102 |   `val colors` |
| 103 |   `var name by rememberSaveable` |
| 104 |   `var description by rememberSaveable` |
| 105 |   `var instructions by rememberSaveable` |
| 106 |   `var enabled by rememberSaveable` |
| 107 |   `var emoji by rememberSaveable` |
| 108 |   `var color by rememberSaveable` |
| 109 |   `var avatarTouched by rememberSaveable` |
| 110 |   `var agent by rememberSaveable` |
| 111 |   `var agentTouched by rememberSaveable` |
| 112 |   `var mode by rememberSaveable` |
| 113 |   `var values by remember` |
| 114 |   `val snapshot` |
| 115 |   `val agentLabel` |
| 116 |   `val config` |
| 117 |   `val valid` |
| 178 | `@Composable private fun AssistantField(value: String, onChange: (String) -> Unit, label: Int, enabled: Boolean, singleLine: Boolean = true)` |
| 183 | `@Composable private fun AssistantChoice(label: String, value: String, choices: List<Pair<String, String>>, enabled: Boolean, onPick: (String) -> Unit)` |

## feature/settings/AssistantsViewModel.kt

| Line | Declaration |
|---|---|
| 21 | `data class AssistantsUi( val generation: Int = 0, val rows: List<Assistant> = emptyList(), val agents: List<AcpAgentInfo> = emptyList(), val loading: Boolean = true, val busy: Boolean = false, val supported: Boolean = true, val error: String? = null, val editing: Boolean = false, val editor: Assistant? = null, val options: AgentOptionsSnapshot? = null, val probing: Boolean = false, val optionsError: String? = null, )` |
| 22 |   `val generation: Int` |
| 23 |   `val rows: List<Assistant>` |
| 24 |   `val agents: List<AcpAgentInfo>` |
| 25 |   `val loading: Boolean` |
| 26 |   `val busy: Boolean` |
| 27 |   `val supported: Boolean` |
| 28 |   `val error: String?` |
| 29 |   `val editing: Boolean` |
| 30 |   `val editor: Assistant?` |
| 31 |   `val options: AgentOptionsSnapshot?` |
| 32 |   `val probing: Boolean` |
| 33 |   `val optionsError: String?` |
| 37 | `class AssistantsViewModel @Inject constructor( private val repository: ServerRepository, @ApplicationContext private val context: Context, ) : ViewModel()` |
| 38 |   `private val repository: ServerRepository` |
| 41 |   `private val state` |
| 42 |   `val ui` |
| 43 |   `private var client: CodegClient?` |
| 44 |   `private var operation: Job?` |
| 45 |   `private var probe: Job?` |
| 66 |   `private fun failure(e: Exception)` |
| 71 |   `private suspend fun loadFrom(c: CodegClient)` |
| 77 |   `fun load()` |
| 86 |   `fun edit(row: Assistant?)` |
| 92 |   `fun closeEditor()` |
| 98 |   `fun probeAgent(agent: AgentType)` |
| 111 |   `fun save(draft: AssistantDraft)` |
| 117 |   `fun delete(row: Assistant)` |
| 122 |   `fun move(row: Assistant, delta: Int)` |
| 128 |   `private fun mutate(block: suspend CodegClient.() -> Unit)` |

## feature/settings/ChatChannelsScreen.kt

| Line | Declaration |
|---|---|
| 104 | `internal fun channelTint(type: ChannelType): Color` |
| 110 | `internal fun channelIcon(type: ChannelType): ImageVector` |
| 117 | `@Composable internal fun statusTint(status: ChannelConnectionStatus): Color` |
| 126 | `@Composable internal fun ChannelTypeAvatar(type: ChannelType, size: Dp)` |
| 127 |   `val tint` |
| 134 | `@Composable internal fun ChannelStatusPill(status: ChannelConnectionStatus)` |
| 145 | `private sealed interface ChannelEditorTarget` |
| 146 |   `data object Add : ChannelEditorTarget` |
| 147 |   `data class Edit(val channel: ChatChannelInfo) : ChannelEditorTarget` |
| 152 | `@Composable fun ChatChannelsContent(viewModel: ChatChannelsViewModel = hiltViewModel())` |
| 153 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 154 |   `val colors` |
| 155 |   `var editor by remember` |
| 156 |   `var showGlobal by remember` |
| 157 |   `var pendingDelete by remember` |
| 262 | `@Composable private fun ChannelRow( channel: ChatChannelInfo, status: ChannelConnectionStatus, toggling: Boolean, onOpen: () -> Unit, onToggle: (Boolean) -> Unit, onDelete: () -> Unit, )` |
| 270 |   `val colors` |
| 271 |   `val summary` |
| 289 | `@Composable private fun ChatRefreshBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 290 |   `val colors` |
| 305 | `@Composable private fun ChatChannelDetailDialog( channel: ChatChannelInfo, detail: ChannelDetailState, viewModel: ChatChannelsViewModel, onEdit: () -> Unit, onDismiss: () -> Unit, )` |
| 312 |   `val colors` |
| 313 |   `var showQR by remember` |
| 314 |   `var confirmRemoveToken by remember` |
| 423 | `@Composable private fun MessageLogRow(message: ChatChannelMessageLog)` |
| 424 |   `val colors` |
| 435 | `@Composable private fun ActionButton(title: String, icon: ImageVector, tint: Color, enabled: Boolean, onClick: () -> Unit)` |
| 444 | `@Composable private fun DetailSection(title: String, content: @Composable () -> Unit)` |
| 452 | `@Composable private fun SettingRow(content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit)` |
| 457 | `@Composable private fun Divider()` |
| 465 | `private enum class QrPhase` |
| 470 | `@Composable private fun WeixinQRDialog(channelId: Int, viewModel: ChatChannelsViewModel, onConnected: () -> Unit, onDismiss: () -> Unit)` |
| 471 |   `val colors` |
| 472 |   `val loadingQrText` |
| 473 |   `val couldntLoadQrText` |
| 474 |   `val scanWithWechatText` |
| 475 |   `val connectedText` |
| 476 |   `val qrExpiredText` |
| 477 |   `val scannedConfirmText` |
| 478 |   `var attempt by remember` |
| 479 |   `var phase by remember` |
| 480 |   `var statusText by remember` |
| 481 |   `var image by remember` |
| 529 | `private fun decodeQrImage(raw: String): androidx.compose.ui.graphics.ImageBitmap?` |
| 530 |   `var b64` |
| 531 |   `val comma` |
| 543 | `private val lenientJson` |
| 545 | `private fun normalizedObjectJson(raw: String): String?` |
| 546 |   `val obj` |
| 552 | `@Composable private fun ChatChannelEditorDialog(existing: ChatChannelInfo?, viewModel: ChatChannelsViewModel, onDismiss: () -> Unit)` |
| 553 |   `val colors` |
| 554 |   `val scope` |
| 555 |   `val isEdit` |
| 557 |   `var name by remember` |
| 558 |   `var type by remember` |
| 559 |   `val parsed` |
| 560 |   `var chatId by remember` |
| 561 |   `var appId by remember` |
| 562 |   `var baseUrl by remember` |
| 563 |   `var rawMode by remember` |
| 564 |   `var rawText by remember` |
| 565 |   `var token by remember` |
| 566 |   `var hasToken by remember` |
| 567 |   `var enabled by remember` |
| 568 |   `var dailyEnabled by remember` |
| 569 |   `var dailyTime by remember` |
| 570 |   `var saving by remember` |
| 571 |   `var error by remember` |
| 572 |   `val couldntSaveText` |
| 578 |   `fun currentConfig()` |
| 579 |   `val configJson: String?` |
| 580 |   `val configValid` |
| 585 |   `val tokenRequired` |
| 586 |   `val canSave` |
| 588 |   `fun save()` |
| 670 | `private fun buildUpdateBody(existing: ChatChannelInfo, name: String, enabled: Boolean, configJson: String, dailyEnabled: Boolean, timeString: String?): UpdateChatChannelBody` |
| 671 |   `val configChanged` |
| 672 |   `val timeEdit: FieldEdit` |

## feature/settings/ChatChannelsViewModel.kt

| Line | Declaration |
|---|---|
| 35 | `class ChatChannelsViewModel @Inject constructor( private val repository: ServerRepository, @ApplicationContext private val appContext: Context, ) : ViewModel()` |
| 36 |   `private val repository: ServerRepository` |
| 40 |   `private val _ui` |
| 41 |   `val ui: StateFlow<ChatChannelsUiState>` |
| 43 |   `private var client: CodegClient?` |
| 47 |   `fun load()` |
| 64 |   `private suspend fun loadStatuses()` |
| 70 |   `fun statusFor(channel: ChatChannelInfo): ChannelConnectionStatus` |
| 73 |   `fun dismissRefreshError()` |
| 74 |   `fun dismissToast()` |
| 78 |   `fun setEnabled(channel: ChatChannelInfo, on: Boolean)` |
| 104 |   `suspend fun create(name: String, type: ChannelType, configJson: String, enabled: Boolean, dailyReportEnabled: Boolean, dailyReportTime: String?, token: String?)` |
| 119 |   `suspend fun update(body: UpdateChatChannelBody, token: String?)` |
| 128 |   `fun delete(channel: ChatChannelInfo)` |
| 144 |   `fun openDetail(channel: ChatChannelInfo)` |
| 149 |   `fun closeDetail()` |
| 150 |   `fun dismissActionError()` |
| 151 |   `fun dismissDetailToast()` |
| 153 |   `private fun updateDetail(transform: (ChannelDetailState) -> ChannelDetailState)` |
| 157 |   `fun channelForDetail(detail: ChannelDetailState): ChatChannelInfo?` |
| 160 |   `private suspend fun loadDetail(channelId: Int)` |
| 169 |   `private suspend fun refreshDetailStatus(channelId: Int)` |
| 175 |   `fun connect()` |
| 176 |   `fun disconnect()` |
| 177 |   `fun test()` |
| 179 |   `private fun detailAction(note: String, poll: Boolean = false, op: suspend (CodegClient, Int) -> Unit)` |
| 200 |   `private suspend fun pollUntilSettled(channelId: Int)` |
| 208 |   `fun removeToken()` |
| 222 |   `fun qrConnected()` |
| 231 |   `suspend fun hasTokenFor(channelId: Int): Boolean` |
| 233 |   `suspend fun weixinQrcode(): WeixinQrcode?` |
| 235 |   `suspend fun weixinCheck(channelId: Int, qrcode: String): String?` |
| 241 | `enum class ChatChannelsPhase` |
| 243 | `data class ChannelDetailState( val channelId: Int, val status: ChannelConnectionStatus = ChannelConnectionStatus.DISCONNECTED, val messages: List<ChatChannelMessageLog> = emptyList(), val hasToken: Boolean = false, val busy: Boolean = false, val actionError: String? = null, val toast: String? = null, )` |
| 244 |   `val channelId: Int` |
| 245 |   `val status: ChannelConnectionStatus` |
| 246 |   `val messages: List<ChatChannelMessageLog>` |
| 247 |   `val hasToken: Boolean` |
| 248 |   `val busy: Boolean` |
| 249 |   `val actionError: String?` |
| 250 |   `val toast: String?` |
| 253 | `data class ChatChannelsUiState( val phase: ChatChannelsPhase = ChatChannelsPhase.LOADING, val channels: List<ChatChannelInfo> = emptyList(), val statuses: Map<Int, ChannelConnectionStatus> = emptyMap(), val togglingEnabled: Set<Int> = emptySet(), val refreshError: String? = null, val error: String? = null, val toast: String? = null, val detail: ChannelDetailState? = null, )` |
| 254 |   `val phase: ChatChannelsPhase` |
| 255 |   `val channels: List<ChatChannelInfo>` |
| 256 |   `val statuses: Map<Int, ChannelConnectionStatus>` |
| 257 |   `val togglingEnabled: Set<Int>` |
| 258 |   `val refreshError: String?` |
| 259 |   `val error: String?` |
| 260 |   `val toast: String?` |
| 261 |   `val detail: ChannelDetailState?` |

## feature/settings/ChatGlobalSettingsScreen.kt

| Line | Declaration |
|---|---|
| 58 | `@Composable fun ChatGlobalSettingsDialog(onDismiss: () -> Unit, viewModel: ChatGlobalSettingsViewModel = hiltViewModel())` |
| 59 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 60 |   `var removingWebhook by remember` |
| 61 |   `val colors` |
| 62 |   `var newWebhook by remember` |
| 63 |   `var prefixField by remember(ui.commandPrefix)` |
| 148 | `@Composable private fun GlobalSection(title: String, subtitle: String, content: @Composable () -> Unit)` |

## feature/settings/ChatGlobalSettingsViewModel.kt

| Line | Declaration |
|---|---|
| 24 | `class ChatGlobalSettingsViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 26 |   `private val repository: ServerRepository` |
| 29 |   `private val _ui` |
| 30 |   `val ui: StateFlow<ChatGlobalUiState>` |
| 32 |   `private var client: CodegClient?` |
| 36 |   `fun load()` |
| 63 |   `fun setCommandPrefix(prefix: String)` |
| 70 |   `fun setLanguage(code: String)` |
| 76 |   `fun toggleEvent(id: String, on: Boolean)` |
| 85 |   `fun addWebhook(url: String)` |
| 91 |   `fun setWebhookEnabled(index: Int, on: Boolean)` |
| 98 |   `fun removeWebhook(index: Int)` |
| 105 |   `private fun persistWebhooks(next: List<WebhookConfig>)` |
| 114 |   `fun dismissError()` |
| 117 | `data class ChatGlobalUiState( val loading: Boolean = false, val commandPrefix: String = "/", val language: String = "en", val enabledEvents: Set<String> = ChatEventCatalog.defaultEnabled, val webhooks: List<WebhookConfig> = emptyList(), val error: String? = null, )` |
| 118 |   `val loading: Boolean` |
| 119 |   `val commandPrefix: String` |
| 120 |   `val language: String` |
| 121 |   `val enabledEvents: Set<String>` |
| 122 |   `val webhooks: List<WebhookConfig>` |
| 123 |   `val error: String?` |

## feature/settings/customskills/CustomSkillPolicy.kt

| Line | Declaration |
|---|---|
| 6 | `fun canEnableCustomSkill(state: ExpertLinkState?): Boolean` |
| 9 | `fun importableCustomSkills(skills: List<AgentSkillItem>, libraryIds: Set<String>): List<AgentSkillItem>` |

## feature/settings/customskills/CustomSkillsScreen.kt

| Line | Declaration |
|---|---|
| 37 | `@Composable fun CustomSkillsEntry(onClosed: () -> Unit, viewModel: CustomSkillsViewModel = hiltViewModel())` |
| 38 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 39 |   `var open by remember` |
| 54 | `private enum class EditorMode` |
| 55 | `private data class EditorTarget(val mode: EditorMode, val id: String = "")` |
| 58 | `@Composable private fun CustomSkillsPage(ui: CustomSkillsUi, viewModel: CustomSkillsViewModel, onDismiss: () -> Unit)` |
| 59 |   `var editor by remember` |
| 60 |   `var deleting by remember` |
| 61 |   `var importing by remember` |
| 124 | `private fun linkStateLabel(state: ExpertLinkState?): Int` |
| 133 | `@Composable private fun CustomSkillsEditor(target: EditorTarget, ui: CustomSkillsUi, viewModel: CustomSkillsViewModel, onDismiss: () -> Unit)` |
| 134 |   `val scope` |
| 135 |   `var id by remember(target)` |
| 136 |   `var content by remember(target)` |
| 137 |   `var loading by remember(target)` |
| 138 |   `var loaded by remember(target)` |
| 139 |   `var error by remember(target)` |
| 140 |   `var retry by remember` |
| 150 |   `val title` |
| 182 | `@Composable private fun CustomSkillsImport(ui: CustomSkillsUi, viewModel: CustomSkillsViewModel, onDismiss: () -> Unit)` |
| 183 |   `val scope` |
| 184 |   `var agent by remember` |
| 185 |   `var candidates by remember` |
| 186 |   `var selected by remember` |
| 187 |   `var loading by remember` |
| 188 |   `var error by remember` |
| 189 |   `var retry by remember` |
| 229 | `@Composable private fun CustomSkillsError(error: String?)` |
| 235 | `@Composable private fun CustomSkillsFrame(title: String, onDismiss: () -> Unit, content: @Composable ColumnScope.() -> Unit)` |

## feature/settings/customskills/CustomSkillsViewModel.kt

| Line | Declaration |
|---|---|
| 20 | `data class CustomSkillsUi( val supported: Boolean = false, val loading: Boolean = true, val busy: Boolean = false, val skills: List<CustomSkill> = emptyList(), val agents: List<AgentType> = emptyList(), val statuses: List<ExpertInstallStatus> = emptyList(), val error: String? = null, )` |
| 21 |   `val supported: Boolean` |
| 22 |   `val loading: Boolean` |
| 23 |   `val busy: Boolean` |
| 24 |   `val skills: List<CustomSkill>` |
| 25 |   `val agents: List<AgentType>` |
| 26 |   `val statuses: List<ExpertInstallStatus>` |
| 27 |   `val error: String?` |
| 31 | `class CustomSkillsViewModel @Inject constructor( private val repository: ServerRepository, @ApplicationContext private val context: Context, ) : ViewModel()` |
| 32 |   `private val repository: ServerRepository` |
| 35 |   `private val state` |
| 36 |   `val ui` |
| 37 |   `private var client: CodegClient?` |
| 39 |   `private var refreshing` |
| 43 |   `fun refresh()` |
| 61 |   `private suspend fun reload(c: CodegClient)` |
| 70 |   `private fun report(e: Exception)` |
| 79 |   `private fun currentClient(): CodegClient` |
| 81 |   `suspend fun read(id: String): String` |
| 84 |   `private suspend fun mutate(action: suspend (CodegClient) -> Unit): Boolean` |
| 98 |   `suspend fun save(id: String, content: String, create: Boolean): Boolean` |
| 102 |   `suspend fun duplicate(sourceId: String, newId: String): Boolean` |
| 104 |   `fun delete(id: String)` |
| 115 |   `fun toggle(id: String, agent: AgentType, enable: Boolean)` |
| 134 |   `suspend fun importCandidates(agent: AgentType): List<AgentSkillItem>` |
| 140 |   `suspend fun importFromAgent(agent: AgentType, ids: List<String>): Boolean` |

## feature/settings/ExpertsScreen.kt

| Line | Declaration |
|---|---|
| 62 | `@Composable fun ExpertsContent(viewModel: ExpertsViewModel = hiltViewModel())` |
| 63 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 64 |   `val colors` |
| 110 | `@Composable private fun ExpertRow(expert: ExpertListItem, onClick: () -> Unit)` |
| 117 | `@Composable private fun ExpertIconTile(size: androidx.compose.ui.unit.Dp)` |
| 118 |   `val colors` |
| 128 | `@Composable private fun ExpertsRefreshBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 129 |   `val colors` |
| 142 | `@Composable private fun ExpertDetailDialog( detail: DetailState, agents: List<AgentType>, onToggle: (AgentType, Boolean) -> Unit, onDismiss: () -> Unit, onDismissError: () -> Unit, )` |
| 149 |   `val colors` |
| 150 |   `val expert` |
| 214 | `@Composable private fun AgentLinkRow(agent: AgentType, status: ExpertInstallStatus?, toggling: Boolean, onToggle: (Boolean) -> Unit)` |
| 215 |   `val colors` |
| 216 |   `val state` |
| 217 |   `val isLinked` |
| 218 |   `val blocked` |
| 219 |   `val caption` |
| 236 | `@Composable private fun linkCaption(state: ExpertLinkState?, copyMode: Boolean): Pair<String, Color>?` |
| 237 |   `val colors` |
| 248 | `private fun stripFrontmatter(content: String): String` |
| 249 |   `val lines` |
| 251 |   `val end` |

## feature/settings/ExpertsViewModel.kt

| Line | Declaration |
|---|---|
| 30 | `class ExpertsViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 32 |   `private val repository: ServerRepository` |
| 35 |   `private val _ui` |
| 36 |   `val ui: StateFlow<ExpertsUiState>` |
| 38 |   `private var client: CodegClient?` |
| 42 |   `fun load()` |
| 62 |   `fun dismissRefreshError()` |
| 66 |   `fun openDetail(expert: ExpertListItem)` |
| 88 |   `fun closeDetail()` |
| 90 |   `fun dismissDetailError()` |
| 93 |   `private fun updateDetail(transform: (DetailState) -> DetailState)` |
| 96 |   `fun toggleLink(agent: AgentType, on: Boolean)` |
| 131 | `enum class ExpertsPhase` |
| 133 | `data class DetailState( val expert: ExpertListItem, val loading: Boolean = true, val content: String = "", val statusByAgent: Map<AgentType, ExpertInstallStatus> = emptyMap(), val toggling: Set<AgentType> = emptySet(), val error: String? = null, )` |
| 134 |   `val expert: ExpertListItem` |
| 135 |   `val loading: Boolean` |
| 136 |   `val content: String` |
| 137 |   `val statusByAgent: Map<AgentType, ExpertInstallStatus>` |
| 138 |   `val toggling: Set<AgentType>` |
| 139 |   `val error: String?` |
| 142 | `data class ExpertsUiState( val phase: ExpertsPhase = ExpertsPhase.LOADING, val experts: List<ExpertListItem> = emptyList(), val agents: List<AgentType> = emptyList(), val refreshError: String? = null, val error: String? = null, val detail: DetailState? = null, )` |
| 143 |   `val phase: ExpertsPhase` |
| 144 |   `val experts: List<ExpertListItem>` |
| 145 |   `val agents: List<AgentType>` |
| 146 |   `val refreshError: String?` |
| 147 |   `val error: String?` |
| 148 |   `val detail: DetailState?` |
| 151 |   `val grouped: List<Triple<String, String, List<ExpertListItem>>>` |
| 165 | `object ExpertCategory` |
| 166 |   `private val known` |
| 176 |   `fun rank(key: String): Int` |
| 178 |   `fun label(key: String): String` |

## feature/settings/GeneralScreen.kt

| Line | Declaration |
|---|---|
| 38 | `@Composable fun GeneralContent(viewModel: GeneralViewModel = hiltViewModel())` |
| 39 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 40 |   `val colors` |
| 61 | `@Composable private fun Section(title: String, content: @Composable () -> Unit)` |
| 69 | `@Composable private fun ToggleRow(title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit)` |
| 70 |   `val colors` |
| 77 | `@Composable private fun Stepper(value: Int, onDec: () -> Unit, onInc: () -> Unit)` |
| 78 |   `val colors` |
| 87 | `@Composable private fun StepButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit)` |
| 88 |   `val colors` |

## feature/settings/GeneralViewModel.kt

| Line | Declaration |
|---|---|
| 28 | `class GeneralViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 30 |   `private val repository: ServerRepository` |
| 33 |   `private val _ui` |
| 34 |   `val ui: StateFlow<GeneralUiState>` |
| 36 |   `private var client: CodegClient?` |
| 37 |   `private var delegationRaw: JsonObject` |
| 41 |   `fun load()` |
| 71 |   `fun setFeedback(enabled: Boolean)` |
| 77 |   `fun setQuestion(enabled: Boolean)` |
| 83 |   `fun setDelegationEnabled(enabled: Boolean)` |
| 88 |   `fun setDepth(depth: Int)` |
| 94 |   `private fun patchDelegation(key: String, value: JsonPrimitive)` |
| 101 |   `private fun reload()` |
| 104 | `data class GeneralUiState( val loading: Boolean = false, val feedbackEnabled: Boolean = true, val questionEnabled: Boolean = true, val delegationEnabled: Boolean = false, val depthLimit: Int = 3, val error: String? = null, )` |
| 105 |   `val loading: Boolean` |
| 106 |   `val feedbackEnabled: Boolean` |
| 107 |   `val questionEnabled: Boolean` |
| 108 |   `val delegationEnabled: Boolean` |
| 109 |   `val depthLimit: Int` |
| 110 |   `val error: String?` |

## feature/settings/LanguageScreen.kt

| Line | Declaration |
|---|---|
| 35 | `@Composable fun LanguageContent()` |
| 36 |   `val context` |
| 37 |   `var selected by remember` |
| 59 | `private tailrec fun Context.findActivity(): Activity?` |

## feature/settings/mcpmarket/McpMarketParameters.kt

| Line | Declaration |
|---|---|
| 6 | `class MarketParameterException(val field: String) : IllegalArgumentException()` |
| 8 | `fun parseMarketParameters(fields: List<McpMarketParameter>, draft: Map<String, String>): JsonObject` |

## feature/settings/mcpmarket/McpMarketSheet.kt

| Line | Declaration |
|---|---|
| 26 | `@Composable fun McpMarketSheet(viewModel: McpMarketViewModel, onDismiss: () -> Unit, onInstalled: () -> Unit)` |
| 27 |   `val ui by viewModel.ui.collectAsState()` |
| 28 |   `val colors` |
| 29 |   `var confirm by remember` |
| 30 |   `val close` |
| 97 | `@Composable private fun MarketSelect(label: String, choices: List<Pair<String, String>>, enabled: Boolean, onSelect: (String) -> Unit)` |

## feature/settings/mcpmarket/McpMarketViewModel.kt

| Line | Declaration |
|---|---|
| 22 | `class McpMarketViewModel @Inject constructor( private val repository: ServerRepository, @ApplicationContext private val context: Context, ) : ViewModel()` |
| 23 |   `private val repository: ServerRepository` |
| 26 |   `private val _ui` |
| 27 |   `val ui` |
| 28 |   `private var client: CodegClient?` |
| 29 |   `private var searchJob: Job?` |
| 30 |   `private var detailJob: Job?` |
| 34 |   `fun load()` |
| 48 |   `fun query(value: String)` |
| 49 |   `fun provider(id: String)` |
| 54 |   `fun search(more: Boolean = false)` |
| 68 |   `fun detail(item: McpMarketItem)` |
| 79 |   `fun back()` |
| 80 |   `fun option(option: McpMarketOption)` |
| 81 |   `fun field(key: String, value: String)` |
| 82 |   `fun app(app: String)` |
| 83 |   `fun install(onInstalled: () -> Unit)` |
| 101 |   `private fun failure(e: Exception)` |
| 106 |   `private fun defaults(option: McpMarketOption?): Map<String, String>` |
| 113 | `data class McpMarketUi( val available: Boolean = false, val loading: Boolean = false, val installing: Boolean = false, val providers: List<McpMarketProvider> = emptyList(), val provider: String? = null, val query: String = "", val items: List<McpMarketItem> = emptyList(), val limit: Int = 30, val canLoadMore: Boolean = false, val detail: McpMarketDetail? = null, val option: McpMarketOption? = null, val draft: Map<String, String> = emptyMap(), val apps: Set<String> = AgentType.entries.map { it.wire }.toSet(), val error: String? = null, )` |
| 114 |   `val available: Boolean` |
| 115 |   `val providers: List<McpMarketProvider>` |
| 116 |   `val query: String` |
| 117 |   `val canLoadMore: Boolean` |
| 118 |   `val draft: Map<String, String>` |
| 119 |   `val error: String?` |

## feature/settings/McpScreen.kt

| Line | Declaration |
|---|---|
| 54 | `private const val SPEC_TEMPLATE` |
| 58 | `@Composable fun McpContent(viewModel: McpViewModel = hiltViewModel())` |
| 59 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 60 |   `val colors` |
| 61 |   `val marketViewModel: McpMarketViewModel` |
| 62 |   `val marketUi by marketViewModel.ui.collectAsStateWithLifecycle()` |
| 63 |   `var marketOpen by remember` |
| 64 |   `var editor by remember` |
| 97 | `private data class McpEditorTarget(val server: LocalMcpServer?)` |
| 101 | `@Composable private fun McpRow(s: LocalMcpServer, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 102 |   `val colors` |
| 103 |   `var menu by remember` |
| 104 |   `var deleting by remember` |
| 126 | `@Composable private fun McpEditor( existing: LocalMcpServer?, onDismiss: () -> Unit, onSave: (String, String, List<String>, (String) -> Unit) -> Unit, )` |
| 131 |   `val colors` |
| 132 |   `var id by remember` |
| 133 |   `var spec by remember` |
| 134 |   `val apps: SnapshotStateList<String>` |
| 135 |   `var error by remember` |

## feature/settings/McpViewModel.kt

| Line | Declaration |
|---|---|
| 24 | `class McpViewModel @Inject constructor( private val repository: ServerRepository, @ApplicationContext private val appContext: Context, ) : ViewModel()` |
| 25 |   `private val repository: ServerRepository` |
| 29 |   `private val _ui` |
| 30 |   `val ui: StateFlow<McpUiState>` |
| 32 |   `private var client: CodegClient?` |
| 33 |   `private val lenient` |
| 37 |   `fun load()` |
| 52 |   `fun save(originalId: String?, serverId: String, specText: String, apps: List<String>, onResult: (String?) -> Unit)` |
| 68 |   `fun delete(server: LocalMcpServer)` |
| 78 | `data class McpUiState( val servers: List<LocalMcpServer> = emptyList(), val loading: Boolean = false, val error: String? = null, )` |
| 79 |   `val servers: List<LocalMcpServer>` |
| 80 |   `val loading: Boolean` |
| 81 |   `val error: String?` |

## feature/settings/ModelProvidersScreen.kt

| Line | Declaration |
|---|---|
| 48 | `@Composable fun ModelProvidersContent(viewModel: ModelProvidersViewModel = hiltViewModel())` |
| 49 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 50 |   `val colors` |
| 51 |   `var editor by remember` |
| 86 | `private data class ProviderEditorTarget(val provider: ModelProviderInfo?)` |
| 90 | `@Composable private fun ProviderRow(p: ModelProviderInfo, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 91 |   `val colors` |
| 92 |   `var menu by remember` |
| 93 |   `var deleting by remember` |
| 115 | `@Composable private fun ProviderEditor( existing: ModelProviderInfo?, supportedAgents: List<AgentType>, onDismiss: () -> Unit, onSave: (String, String, String, AgentType, String?) -> Unit, )` |
| 121 |   `val colors` |
| 122 |   `var name by remember` |
| 123 |   `var url by remember` |
| 124 |   `var key by remember` |
| 125 |   `var agent by remember` |
| 126 |   `var model by remember` |

## feature/settings/ModelProvidersViewModel.kt

| Line | Declaration |
|---|---|
| 23 | `class ModelProvidersViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 25 |   `private val repository: ServerRepository` |
| 28 |   `private val _ui` |
| 29 |   `val ui: StateFlow<ModelProvidersUiState>` |
| 31 |   `private var client: CodegClient?` |
| 35 |   `fun load()` |
| 49 |   `fun save(existing: ModelProviderInfo?, name: String, apiUrl: String, apiKey: String, agentType: AgentType, model: String?)` |
| 72 |   `fun delete(provider: ModelProviderInfo)` |
| 82 | `data class ModelProvidersUiState( val providers: List<ModelProviderInfo> = emptyList(), val loading: Boolean = false, val error: String? = null, )` |
| 83 |   `val providers: List<ModelProviderInfo>` |
| 84 |   `val loading: Boolean` |
| 85 |   `val error: String?` |
| 88 |   `val supportedAgents: List<AgentType>` |

## feature/settings/OpenSourceLicensesScreen.kt

| Line | Declaration |
|---|---|
| 26 | `private const val NoticeAsset` |
| 27 | `private const val LinesPerChunk` |
| 31 | `@Composable fun OpenSourceLicensesScreen()` |
| 32 |   `val context` |
| 33 |   `val colors` |
| 34 |   `val chunks` |

## feature/settings/QuickMessagesScreen.kt

| Line | Declaration |
|---|---|
| 44 | `@Composable fun QuickMessagesContent(viewModel: QuickMessagesViewModel = hiltViewModel())` |
| 45 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 46 |   `val colors` |
| 47 |   `var editor by remember` |
| 76 | `private data class EditorTarget(val message: QuickMessage?)` |
| 80 | `@Composable private fun MessageRow(msg: QuickMessage, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 81 |   `val colors` |
| 82 |   `var menu by remember` |
| 83 |   `var deleting by remember` |
| 103 | `@Composable private fun QuickMessageEditor(existing: QuickMessage?, onDismiss: () -> Unit, onSave: (String, String) -> Unit)` |
| 104 |   `var title by remember` |
| 105 |   `var content by remember` |

## feature/settings/QuickMessagesViewModel.kt

| Line | Declaration |
|---|---|
| 23 | `class QuickMessagesViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 25 |   `private val repository: ServerRepository` |
| 28 |   `private val _ui` |
| 29 |   `val ui: StateFlow<QuickMessagesUiState>` |
| 31 |   `private var client: CodegClient?` |
| 35 |   `fun load()` |
| 52 |   `fun save(existing: QuickMessage?, title: String, content: String)` |
| 64 |   `fun delete(message: QuickMessage)` |
| 78 | `data class QuickMessagesUiState( val messages: List<QuickMessage> = emptyList(), val loading: Boolean = false, val error: String? = null, )` |
| 79 |   `val messages: List<QuickMessage>` |
| 80 |   `val loading: Boolean` |
| 81 |   `val error: String?` |

## feature/settings/SettingsChoice.kt

| Line | Declaration |
|---|---|
| 17 | `@Composable internal fun SettingsChoice(label: String, value: String, choices: List<Pair<String, String>>, enabled: Boolean = true, onPick: (String) -> Unit)` |
| 18 |   `var expanded by remember` |
| 19 |   `val names` |

## feature/settings/SettingsScreen.kt

| Line | Declaration |
|---|---|
| 75 | `private enum class SettingsLeaf(@StringRes val titleRes: Int, val icon: ImageVector, @StringRes val groupRes: Int, val implemented: Boolean)` |
| 98 | `@Composable fun SettingsScreen( onOpenAutomations: () -> Unit = {}, onOpenWorkTasks: () -> Unit = {}, onOpenServerLogs: () -> Unit = {}, logsViewModel: app.codeg.android.feature.serverlogs.ServerLogsViewModel = hiltViewModel(), viewModel: SettingsViewModel = hiltViewModel(), )` |
| 105 |   `val logsUi by logsViewModel.ui.collectAsStateWithLifecycle()` |
| 106 |   `var leaf by remember` |
| 108 |   `var agentDetail by remember` |
| 109 |   `val inAgentDetail` |
| 172 | `@Composable private fun SettingsRoot( onSelect: (SettingsLeaf) -> Unit, onOpenAutomations: () -> Unit, onOpenWorkTasks: () -> Unit, showServerLogs: Boolean, )` |
| 178 |   `val workTasks: app.codeg.android.feature.worktasks.WorkTaskViewModel` |
| 179 |   `val workTaskUi by workTasks.ui.collectAsStateWithLifecycle()` |
| 180 |   `val assistants: AssistantsViewModel` |
| 181 |   `val assistantUi by assistants.ui.collectAsStateWithLifecycle()` |
| 182 |   `val colors` |
| 225 | `@Composable private fun AppearanceSettings(viewModel: SettingsViewModel)` |
| 226 |   `val colors` |
| 227 |   `val settings by viewModel.settings.collectAsStateWithLifecycle()` |
| 277 | `@Composable private fun AboutScreen(viewModel: SettingsViewModel)` |
| 278 |   `val colors` |
| 279 |   `val about by viewModel.about.collectAsStateWithLifecycle()` |
| 280 |   `val appVersion` |
| 295 | `@Composable private fun ComingSoon(@StringRes title: Int)` |
| 296 |   `val colors` |

## feature/settings/SettingsViewModel.kt

| Line | Declaration |
|---|---|
| 22 | `class SettingsViewModel @Inject constructor( private val appSettingsStore: AppSettingsStore, private val repository: ServerRepository, ) : ViewModel()` |
| 23 |   `private val appSettingsStore: AppSettingsStore` |
| 24 |   `private val repository: ServerRepository` |
| 27 |   `val settings: StateFlow<AppSettings>` |
| 30 |   `private val _about` |
| 31 |   `val about: StateFlow<AboutInfo>` |
| 45 |   `fun setThemeMode(mode: ThemeMode)` |
| 46 |   `fun setAccent(accentId: String)` |
| 49 | `data class AboutInfo( val serverName: String? = null, val serverVersion: String? = null, )` |
| 50 |   `val serverName: String?` |
| 51 |   `val serverVersion: String?` |

## feature/settings/SkillsScreen.kt

| Line | Declaration |
|---|---|
| 75 | `private sealed interface SkillEditTarget` |
| 76 |   `data object Add : SkillEditTarget` |
| 77 |   `data class Edit(val skill: AgentSkillItem) : SkillEditTarget` |
| 82 | `@Composable fun SkillsContent(viewModel: SkillsViewModel = hiltViewModel())` |
| 83 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 84 |   `val colors` |
| 85 |   `var editor by remember` |
| 86 |   `var pendingDelete by remember` |
| 150 | `@Composable private fun AgentFilterBar(agents: List<AgentType>, selected: AgentType?, onSelect: (AgentType) -> Unit)` |
| 151 |   `val colors` |
| 165 | `@Composable private fun SkillsArea( ui: SkillsUiState, onAdd: () -> Unit, onOpen: (AgentSkillItem) -> Unit, onDelete: (AgentSkillItem) -> Unit, onRetry: () -> Unit, onDismissError: () -> Unit, )` |
| 173 |   `val colors` |
| 174 |   `val result` |
| 211 | `@Composable private fun SkillRow(skill: AgentSkillItem, onClick: () -> Unit, onDelete: () -> Unit)` |
| 212 |   `val colors` |
| 223 | `@Composable private fun RefreshErrorBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 224 |   `val colors` |
| 237 | `@Composable private fun SkillEditorDialog( target: SkillEditTarget, agent: AgentType, loadContent: suspend (AgentSkillItem) -> String, onSave: suspend (id: String, scope: String, content: String, layout: String?) -> Unit, onDismiss: () -> Unit, )` |
| 244 |   `val colors` |
| 245 |   `val scope` |
| 246 |   `val existing` |
| 247 |   `val isReadOnly` |
| 248 |   `val skillScope` |
| 249 |   `val layout` |
| 251 |   `var skillId by remember` |
| 252 |   `var content by remember` |
| 253 |   `var loadingContent by remember` |
| 254 |   `var saving by remember` |
| 255 |   `var error by remember` |
| 266 |   `val canSave` |

## feature/settings/SkillsViewModel.kt

| Line | Declaration |
|---|---|
| 30 | `class SkillsViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 32 |   `private val repository: ServerRepository` |
| 35 |   `private val _ui` |
| 36 |   `val ui: StateFlow<SkillsUiState>` |
| 38 |   `private var client: CodegClient?` |
| 39 |   `private var loadToken` |
| 43 |   `fun load()` |
| 63 |   `fun select(agent: AgentType)` |
| 69 |   `fun reloadCurrent()` |
| 73 |   `private fun loadSkills(agent: AgentType)` |
| 90 |   `suspend fun content(skill: AgentSkillItem, agent: AgentType): String` |
| 96 |   `suspend fun save(skillId: String, scope: String, content: String, layout: String?, agent: AgentType)` |
| 102 |   `fun delete(skill: AgentSkillItem, agent: AgentType)` |
| 115 |   `fun dismissRefreshError()` |
| 118 | `enum class SkillsPhase` |
| 120 | `data class SkillsUiState( val phase: SkillsPhase = SkillsPhase.LOADING, val agents: List<AgentType> = emptyList(), val selectedAgent: AgentType? = null, val result: AgentSkillsListResult? = null, /** The agent [result] actually belongs to (mutations target THIS, not [selectedAgent]). */ val resultAgent: AgentType? = null, val skillsLoading: Boolean = false, val refreshError: String? = null, val error: String? = null, )` |
| 121 |   `val phase: SkillsPhase` |
| 122 |   `val agents: List<AgentType>` |
| 123 |   `val selectedAgent: AgentType?` |
| 124 |   `val result: AgentSkillsListResult?` |
| 126 |   `val resultAgent: AgentType?` |
| 127 |   `val skillsLoading: Boolean` |
| 128 |   `val refreshError: String?` |
| 129 |   `val error: String?` |
| 132 |   `val grouped: List<Pair<String, List<AgentSkillItem>>>` |

## feature/settings/SystemScreen.kt

| Line | Declaration |
|---|---|
| 34 | `@Composable fun SystemContent(viewModel: SystemViewModel = hiltViewModel())` |
| 35 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 36 |   `val colors` |
| 80 | `@Composable private fun PillButton(label: String, loading: Boolean, onClick: () -> Unit)` |
| 81 |   `val colors` |

## feature/settings/SystemViewModel.kt

| Line | Declaration |
|---|---|
| 22 | `class SystemViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 24 |   `private val repository: ServerRepository` |
| 27 |   `private val _ui` |
| 28 |   `val ui: StateFlow<SystemUiState>` |
| 30 |   `private var client: CodegClient?` |
| 34 |   `fun load()` |
| 49 |   `fun setProxyEnabled(enabled: Boolean)` |
| 50 |   `fun onProxyUrl(url: String)` |
| 52 |   `fun saveProxy()` |
| 66 |   `fun checkUpdate()` |
| 79 | `data class SystemUiState( val loading: Boolean = false, val proxyEnabled: Boolean = false, val proxyUrl: String = "", val savingProxy: Boolean = false, val checking: Boolean = false, val updateResult: AppUpdateCheckResult? = null, val error: String? = null, )` |
| 80 |   `val loading: Boolean` |
| 81 |   `val proxyEnabled: Boolean` |
| 82 |   `val proxyUrl: String` |
| 83 |   `val savingProxy: Boolean` |
| 84 |   `val checking: Boolean` |
| 85 |   `val updateResult: AppUpdateCheckResult?` |
| 86 |   `val error: String?` |

## feature/settings/VersionControlScreen.kt

| Line | Declaration |
|---|---|
| 41 | `@Composable fun VersionControlContent(viewModel: VersionControlViewModel = hiltViewModel())` |
| 42 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 43 |   `val colors` |
| 104 | `@Composable private fun Pill(label: String, loading: Boolean, onClick: () -> Unit, filled: Boolean)` |
| 105 |   `val colors` |

## feature/settings/VersionControlViewModel.kt

| Line | Declaration |
|---|---|
| 25 | `class VersionControlViewModel @Inject constructor( @ApplicationContext private val appContext: Context, private val repository: ServerRepository, ) : ViewModel()` |
| 27 |   `private val repository: ServerRepository` |
| 30 |   `private val _ui` |
| 31 |   `val ui: StateFlow<VersionControlUiState>` |
| 33 |   `private var client: CodegClient?` |
| 37 |   `fun load()` |
| 57 |   `fun onPath(path: String)` |
| 59 |   `fun test()` |
| 70 |   `fun save()` |
| 85 | `data class VersionControlUiState( val loading: Boolean = false, val detect: GitDetectResult = GitDetectResult(), val customPath: String = "", val accounts: List<GitHubAccount> = emptyList(), val testing: Boolean = false, val testResult: GitDetectResult? = null, val saving: Boolean = false, val error: String? = null, )` |
| 86 |   `val loading: Boolean` |
| 87 |   `val detect: GitDetectResult` |
| 88 |   `val customPath: String` |
| 89 |   `val accounts: List<GitHubAccount>` |
| 90 |   `val testing: Boolean` |
| 91 |   `val testResult: GitDetectResult?` |
| 92 |   `val saving: Boolean` |
| 93 |   `val error: String?` |

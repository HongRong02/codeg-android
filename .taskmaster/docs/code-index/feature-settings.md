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
| 61 | `private data class KimiBanner(val text: String, val isError: Boolean)` |
| 75 | `@Composable fun AgentConfigKimiView(agent: AcpAgentInfo, viewModel: AgentsViewModel)` |
| 76 |   `val context` |
| 77 |   `val colors` |
| 78 |   `val scope` |
| 82 |   `val seed` |
| 83 |   `val seedInterface` |
| 84 |   `var mode by remember` |
| 85 |   `var interfaceType by remember` |
| 86 |   `var region by remember` |
| 87 |   `var baseUrl by remember` |
| 88 |   `var authType by remember` |
| 89 |   `var apiKey by remember` |
| 90 |   `var modelId by remember` |
| 91 |   `var maxContext by remember` |
| 92 |   `var vertexProject by remember` |
| 93 |   `var vertexLocation by remember` |
| 94 |   `var rawConfig by remember` |
| 95 |   `var models by remember` |
| 96 |   `var saving by remember` |
| 97 |   `var fetchingModels by remember` |
| 98 |   `var banner by remember` |
| 100 |   `val meta` |
| 101 |   `val isKimi` |
| 102 |   `val isVertex` |
| 103 |   `val effectiveBaseUrl` |
| 104 |   `val liveConfig` |
| 108 |   `val savedMsg` |
| 109 |   `val modelRequiredMsg` |
| 110 |   `val enterKeyMsg` |
| 111 |   `val modelsNoneMsg` |
| 112 |   `val modelsErrMsg` |
| 113 |   `val modelsFoundFmt` |
| 115 |   `fun runSave(body: UpdateKimiCodeConfigBody)` |
| 130 |   `fun handleSave()` |
| 154 |   `fun fetchModels()` |
| 176 |   `fun setInterface(next: KimiInterfaceType)` |
| 310 | `@Composable private fun KimiGateBanner(present: Boolean, mode: KimiAuthMode)` |
| 311 |   `val colors` |
| 312 |   `val tint` |
| 313 |   `val text` |
| 330 | `@Composable internal fun Caption(text: String)` |

## feature/settings/AgentConfigKiroView.kt

| Line | Declaration |
|---|---|
| 10 | `@Composable fun AgentConfigKiroView(agent: AcpAgentInfo)` |

## feature/settings/AgentConfigPiView.kt

| Line | Declaration |
|---|---|
| 62 | `private data class PiBanner(val text: String, val isError: Boolean)` |
| 74 | `@Composable fun AgentConfigPiView(agent: AcpAgentInfo, viewModel: AgentsViewModel)` |
| 75 |   `val context` |
| 76 |   `val colors` |
| 77 |   `val scope` |
| 79 |   `val seedEnv` |
| 81 |   `val seedCmd` |
| 82 |   `var mode by remember` |
| 83 |   `var command by remember` |
| 84 |   `var configDir by remember` |
| 85 |   `var sessionDir by remember` |
| 86 |   `var validation by remember` |
| 87 |   `var validating by remember` |
| 88 |   `var piStatus by remember` |
| 89 |   `var checkingPi by remember` |
| 90 |   `var confirmUninstall by remember` |
| 91 |   `var piOp by remember` |
| 92 |   `var savingRuntime by remember` |
| 95 |   `var selectedProvider by remember` |
| 96 |   `var customId by remember` |
| 97 |   `var customBaseUrl by remember` |
| 98 |   `var customApi by remember` |
| 99 |   `var customProviders by remember` |
| 100 |   `var modelText by remember` |
| 101 |   `var thinkingLevel by remember` |
| 102 |   `var apiKey by remember` |
| 103 |   `var authProviders by remember` |
| 104 |   `var savingCreds by remember` |
| 105 |   `var loadingCreds by remember` |
| 108 |   `var trustWorkspace by remember` |
| 109 |   `var savingTrust by remember` |
| 111 |   `var banner by remember` |
| 113 |   `val isCustomProvider` |
| 114 |   `val effectiveProvider` |
| 115 |   `val providerHasKey` |
| 116 |   `val customIncomplete` |
| 117 |   `val credsIncomplete` |
| 120 |   `val providerOptions` |
| 130 |   `val runtimeSaved` |
| 131 |   `val configSaved` |
| 132 |   `val providerModelRequired` |
| 133 |   `val baseUrlRequired` |
| 134 |   `val installedMsg` |
| 135 |   `val uninstalledMsg` |
| 136 |   `val installFailed` |
| 137 |   `val uninstallFailed` |
| 138 |   `val trustFailed` |
| 168 |   `fun detectPi()` |
| 176 |   `fun handleValidate()` |
| 187 |   `fun handleSaveRuntime()` |
| 203 |   `fun handleSaveCreds()` |
| 243 |   `fun handlePiOp(install: Boolean)` |
| 259 |   `fun toggleTrust(next: Boolean)` |
| 422 | `@Composable private fun PiActionButton(text: String, busy: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 423 |   `val colors` |
| 432 | `@Composable private fun piThinkingLabel(level: String): String` |

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
| 99 | `@Composable fun AgentDetailContent( agentType: AgentType, onClose: () -> Unit, viewModel: AgentsViewModel = hiltViewModel(), )` |
| 104 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 105 |   `val detail` |
| 118 |   `val selfContained` |
| 172 | `@Composable private fun AgentHeader(state: AgentDetailState, onToggle: (Boolean) -> Unit)` |
| 173 |   `val colors` |
| 174 |   `val agent` |
| 206 | `@Composable private fun VersionLine(state: AgentDetailState)` |
| 207 |   `val colors` |
| 208 |   `val installed` |
| 209 |   `val latest` |
| 210 |   `val text` |
| 220 | `@Composable private fun SaveBar(canSave: Boolean, saving: Boolean, error: String?, onSave: () -> Unit)` |
| 221 |   `val colors` |
| 236 | `@Composable private fun ClaudeSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 237 |   `val d` |
| 282 | `private fun claudeFooterRes(mode: ClaudeAuthMode): Int` |
| 289 | `@Composable private fun CodexSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 290 |   `val colors` |
| 291 |   `val context` |
| 292 |   `val d` |
| 330 | `private fun codexEffortLabelRes(e: CodexReasoningEffort): Int` |
| 338 | `@Composable private fun GeminiSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 339 |   `val colors` |
| 340 |   `val d` |
| 390 | `@Composable private fun OpenClawSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 391 |   `val d` |
| 400 | `@Composable private fun ClineSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 401 |   `val d` |
| 416 | `@Composable private fun OpenCodeSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 417 |   `val d` |
| 425 | `@Composable private fun HermesSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 426 |   `val d` |
| 427 |   `val option` |
| 441 | `private fun hermesFooterRes(kind: HermesProviderKind?): Int` |
| 455 | `@Composable private fun CodeBuddySection(state: AgentDetailState, vm: AgentsViewModel)` |
| 456 |   `val colors` |
| 457 |   `val d` |
| 458 |   `val selfHosted` |
| 502 | `@Composable private fun ServerManagedSection()` |
| 512 | `@Composable private fun GrokSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 513 |   `val colors` |
| 514 |   `val d` |
| 515 |   `val useDefault` |
| 569 | `@Composable private fun CursorSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 570 |   `val colors` |
| 571 |   `val d` |
| 572 |   `val custom` |
| 574 |   `var auth by remember` |
| 575 |   `var authLoading by remember` |
| 576 |   `var models by remember` |
| 577 |   `var modelsError by remember` |
| 578 |   `var modelsLoading by remember` |
| 581 |   `val probeKey` |
| 582 |   `var refreshTick by remember` |
| 663 |   `val savedModel` |
| 725 | `@Composable private fun CursorStatusRow(auth: CursorAuthStatus?, loading: Boolean, onRefresh: () -> Unit)` |
| 726 |   `val colors` |
| 727 |   `val installed` |
| 728 |   `val signedIn` |
| 729 |   `val tint` |
| 734 |   `val text` |
| 775 | `@Composable private fun RuleEditor( label: String, rules: List<String>, placeholder: String, tone: Color, onChange: (List<String>) -> Unit, )` |
| 782 |   `val colors` |
| 810 | `@Composable private fun ColumnScope.ProviderPicker(state: AgentDetailState, vm: AgentsViewModel)` |
| 811 |   `val colors` |
| 829 | `@Composable private fun ProviderSummary(p: ModelProviderInfo)` |
| 838 | `@Composable private fun ReadonlyRow(label: String, value: String)` |
| 839 |   `val colors` |
| 851 | `@Composable internal fun FormSection(title: String, footer: String? = null, content: @Composable ColumnScope.() -> Unit)` |
| 861 | `@Composable internal fun <T> SelectField(label: String, value: T, options: List<Pair<T, String>>, onSelect: (T) -> Unit)` |
| 869 | `@Composable private fun HermesProviderField(value: String, onSelect: (String) -> Unit)` |
| 870 |   `var expanded by remember` |
| 891 | `private fun kindLabelRes(kind: HermesProviderKind): Int` |
| 898 | `@Composable private fun ToggleRow(title: String, checked: Boolean, onChange: (Boolean) -> Unit)` |
| 904 | `@Composable internal fun ColumnScope.ExpandableGroup(title: String, content: @Composable ColumnScope.() -> Unit)` |
| 905 |   `val colors` |
| 906 |   `var open by remember` |
| 917 | `@Composable private fun TonalAction(text: String, onClick: () -> Unit)` |
| 918 |   `val colors` |
| 923 | `@Composable private fun AdvancedSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 924 |   `val colors` |
| 925 |   `var open by remember` |
| 926 |   `val label` |
| 931 |   `val text` |

## feature/settings/AgentsScreen.kt

| Line | Declaration |
|---|---|
| 29 | `@Composable fun AgentsContent(onOpen: (AgentType) -> Unit, viewModel: AgentsViewModel = hiltViewModel())` |
| 30 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 46 | `@Composable private fun AgentRow(agent: AcpAgentInfo, onToggle: (Boolean) -> Unit, onOpen: () -> Unit)` |

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
| 38 | `@Composable fun AssistantsContent(viewModel: AssistantsViewModel = hiltViewModel())` |
| 39 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 40 |   `val colors` |
| 41 |   `var deleting by remember(ui.generation)` |
| 104 | `@Composable private fun AssistantEditor(ui: AssistantsUi, vm: AssistantsViewModel)` |
| 105 |   `val old` |
| 106 |   `val colors` |
| 107 |   `var name by rememberSaveable` |
| 108 |   `var description by rememberSaveable` |
| 109 |   `var instructions by rememberSaveable` |
| 110 |   `var enabled by rememberSaveable` |
| 111 |   `var emoji by rememberSaveable` |
| 112 |   `var color by rememberSaveable` |
| 113 |   `var avatarTouched by rememberSaveable` |
| 114 |   `var agent by rememberSaveable` |
| 115 |   `var agentTouched by rememberSaveable` |
| 116 |   `var mode by rememberSaveable` |
| 117 |   `var values by remember` |
| 118 |   `val snapshot` |
| 119 |   `val agentLabel` |
| 120 |   `val config` |
| 121 |   `val valid` |
| 182 | `@Composable private fun AssistantField(value: String, onChange: (String) -> Unit, label: Int, enabled: Boolean, singleLine: Boolean = true)` |
| 187 | `@Composable private fun AssistantChoice(label: String, value: String, choices: List<Pair<String, String>>, enabled: Boolean, onPick: (String) -> Unit)` |

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
| 102 | `@Composable internal fun channelTint(type: ChannelType): Color` |
| 108 | `internal fun channelIcon(type: ChannelType): ImageVector` |
| 115 | `@Composable internal fun statusTint(status: ChannelConnectionStatus): Color` |
| 123 | `@Composable internal fun ChannelTypeAvatar(type: ChannelType, size: Dp)` |
| 124 |   `val source` |
| 125 |   `val tint` |
| 127 |   `val painter` |
| 128 |     `object : Painter()` |
| 142 | `@Composable internal fun ChannelStatusPill(status: ChannelConnectionStatus)` |
| 153 | `private sealed interface ChannelEditorTarget` |
| 154 |   `data object Add : ChannelEditorTarget` |
| 155 |   `data class Edit(val channel: ChatChannelInfo) : ChannelEditorTarget` |
| 160 | `@Composable fun ChatChannelsContent(viewModel: ChatChannelsViewModel = hiltViewModel())` |
| 161 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 162 |   `val colors` |
| 163 |   `var editor by remember` |
| 164 |   `var showGlobal by remember` |
| 165 |   `var pendingDelete by remember` |
| 268 | `@Composable private fun ChannelRow( channel: ChatChannelInfo, status: ChannelConnectionStatus, toggling: Boolean, onOpen: () -> Unit, onToggle: (Boolean) -> Unit, onDelete: () -> Unit, )` |
| 276 |   `val colors` |
| 277 |   `val summary` |
| 295 | `@Composable private fun ChatRefreshBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 296 |   `val colors` |
| 311 | `@Composable private fun ChatChannelDetailDialog( channel: ChatChannelInfo, detail: ChannelDetailState, viewModel: ChatChannelsViewModel, onEdit: () -> Unit, onDismiss: () -> Unit, )` |
| 318 |   `val colors` |
| 319 |   `var showQR by remember` |
| 320 |   `var confirmRemoveToken by remember` |
| 415 |   `val toaster` |
| 431 | `@Composable private fun MessageLogRow(message: ChatChannelMessageLog)` |
| 432 |   `val colors` |
| 443 | `@Composable private fun ActionButton(title: String, icon: ImageVector, tint: Color, enabled: Boolean, onClick: () -> Unit)` |
| 452 | `@Composable private fun DetailSection(title: String, content: @Composable () -> Unit)` |
| 460 | `@Composable private fun SettingRow(content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit)` |
| 465 | `@Composable private fun Divider()` |
| 473 | `private enum class QrPhase` |
| 478 | `@Composable private fun WeixinQRDialog(channelId: Int, viewModel: ChatChannelsViewModel, onConnected: () -> Unit, onDismiss: () -> Unit)` |
| 479 |   `val colors` |
| 480 |   `val loadingQrText` |
| 481 |   `val couldntLoadQrText` |
| 482 |   `val scanWithWechatText` |
| 483 |   `val connectedText` |
| 484 |   `val qrExpiredText` |
| 485 |   `val scannedConfirmText` |
| 486 |   `var preview by remember` |
| 487 |   `var attempt by remember` |
| 488 |   `var phase by remember` |
| 489 |   `var statusText by remember` |
| 490 |   `var image by remember` |
| 548 | `private fun decodeQrImage(raw: String): androidx.compose.ui.graphics.ImageBitmap?` |
| 549 |   `var b64` |
| 550 |   `val comma` |
| 562 | `private val lenientJson` |
| 564 | `private fun normalizedObjectJson(raw: String): String?` |
| 565 |   `val obj` |
| 571 | `@Composable private fun ChatChannelEditorDialog(existing: ChatChannelInfo?, viewModel: ChatChannelsViewModel, onDismiss: () -> Unit)` |
| 572 |   `val colors` |
| 573 |   `val scope` |
| 574 |   `val isEdit` |
| 576 |   `var name by remember` |
| 577 |   `var type by remember` |
| 578 |   `val parsed` |
| 579 |   `var chatId by remember` |
| 580 |   `var appId by remember` |
| 581 |   `var baseUrl by remember` |
| 582 |   `var rawMode by remember` |
| 583 |   `var rawText by remember` |
| 584 |   `var token by remember` |
| 585 |   `var hasToken by remember` |
| 586 |   `var enabled by remember` |
| 587 |   `var dailyEnabled by remember` |
| 588 |   `var dailyTime by remember` |
| 589 |   `var saving by remember` |
| 590 |   `var error by remember` |
| 591 |   `val couldntSaveText` |
| 597 |   `fun currentConfig()` |
| 598 |   `val configJson: String?` |
| 599 |   `val configValid` |
| 604 |   `val tokenRequired` |
| 605 |   `val canSave` |
| 607 |   `fun save()` |
| 689 | `private fun buildUpdateBody(existing: ChatChannelInfo, name: String, enabled: Boolean, configJson: String, dailyEnabled: Boolean, timeString: String?): UpdateChatChannelBody` |
| 690 |   `val configChanged` |
| 691 |   `val timeEdit: FieldEdit` |

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
| 253 | `data class ChatChannelsUiState( val phase: ChatChannelsPhase = ChatChannelsPhase.LOADING, val channels: List<ChatChannelInfo> = emptyList(), val statuses: Map<Int, ChannelConnectionStatus> = emptyMap(), val togglingEnabled: Set<Int> = emptySet(), val refreshError: String? = null, val error: String? = null, val toast: String? = null, val toastIsError: Boolean = false, val detail: ChannelDetailState? = null, )` |
| 254 |   `val phase: ChatChannelsPhase` |
| 255 |   `val channels: List<ChatChannelInfo>` |
| 256 |   `val statuses: Map<Int, ChannelConnectionStatus>` |
| 257 |   `val togglingEnabled: Set<Int>` |
| 258 |   `val refreshError: String?` |
| 259 |   `val error: String?` |
| 260 |   `val toast: String?` |
| 261 |   `val toastIsError: Boolean` |
| 262 |   `val detail: ChannelDetailState?` |

## feature/settings/ChatGlobalSettingsScreen.kt

| Line | Declaration |
|---|---|
| 56 | `@Composable fun ChatGlobalSettingsDialog(onDismiss: () -> Unit, viewModel: ChatGlobalSettingsViewModel = hiltViewModel())` |
| 57 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 58 |   `var removingWebhook by remember` |
| 59 |   `val colors` |
| 60 |   `var newWebhook by remember` |
| 61 |   `var prefixField by remember(ui.commandPrefix)` |
| 146 | `@Composable private fun GlobalSection(title: String, subtitle: String, content: @Composable () -> Unit)` |

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
| 38 | `@Composable fun CustomSkillsEntry(onClosed: () -> Unit, viewModel: CustomSkillsViewModel = hiltViewModel())` |
| 39 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 40 |   `var open by remember` |
| 55 | `private enum class EditorMode` |
| 56 | `private data class EditorTarget(val mode: EditorMode, val id: String = "")` |
| 59 | `@Composable private fun CustomSkillsPage(ui: CustomSkillsUi, viewModel: CustomSkillsViewModel, onDismiss: () -> Unit)` |
| 60 |   `var editor by remember` |
| 61 |   `var deleting by remember` |
| 62 |   `var importing by remember` |
| 125 | `private fun linkStateLabel(state: ExpertLinkState?): Int` |
| 134 | `@Composable private fun CustomSkillsEditor(target: EditorTarget, ui: CustomSkillsUi, viewModel: CustomSkillsViewModel, onDismiss: () -> Unit)` |
| 135 |   `val scope` |
| 136 |   `var id by remember(target)` |
| 137 |   `var content by remember(target)` |
| 138 |   `var loading by remember(target)` |
| 139 |   `var loaded by remember(target)` |
| 140 |   `var error by remember(target)` |
| 141 |   `var retry by remember` |
| 151 |   `val title` |
| 183 | `@Composable private fun CustomSkillsImport(ui: CustomSkillsUi, viewModel: CustomSkillsViewModel, onDismiss: () -> Unit)` |
| 184 |   `val scope` |
| 185 |   `var agent by remember` |
| 186 |   `var candidates by remember` |
| 187 |   `var selected by remember` |
| 188 |   `var loading by remember` |
| 189 |   `var error by remember` |
| 190 |   `var retry by remember` |
| 230 | `@Composable private fun CustomSkillsError(error: String?)` |
| 236 | `@Composable private fun CustomSkillsFrame(title: String, onDismiss: () -> Unit, content: @Composable ColumnScope.() -> Unit)` |

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
| 59 | `@Composable fun ExpertsContent(viewModel: ExpertsViewModel = hiltViewModel())` |
| 60 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 61 |   `val colors` |
| 107 | `@Composable private fun ExpertRow(expert: ExpertListItem, onClick: () -> Unit)` |
| 114 | `@Composable private fun ExpertIconTile(size: androidx.compose.ui.unit.Dp)` |
| 115 |   `val colors` |
| 125 | `@Composable private fun ExpertsRefreshBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 126 |   `val colors` |
| 139 | `@Composable private fun ExpertDetailDialog( detail: DetailState, agents: List<AgentType>, onToggle: (AgentType, Boolean) -> Unit, onDismiss: () -> Unit, onDismissError: () -> Unit, )` |
| 146 |   `val colors` |
| 147 |   `val expert` |
| 211 | `@Composable private fun AgentLinkRow(agent: AgentType, status: ExpertInstallStatus?, toggling: Boolean, onToggle: (Boolean) -> Unit)` |
| 212 |   `val colors` |
| 213 |   `val state` |
| 214 |   `val isLinked` |
| 215 |   `val blocked` |
| 216 |   `val caption` |
| 233 | `@Composable private fun linkCaption(state: ExpertLinkState?, copyMode: Boolean): Pair<String, Color>?` |
| 234 |   `val colors` |
| 245 | `private fun stripFrontmatter(content: String): String` |
| 246 |   `val lines` |
| 248 |   `val end` |

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
| 36 | `@Composable fun GeneralContent(viewModel: GeneralViewModel = hiltViewModel())` |
| 37 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 38 |   `val colors` |
| 59 | `@Composable private fun Section(title: String, content: @Composable () -> Unit)` |
| 67 | `@Composable private fun ToggleRow(title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit)` |
| 68 |   `val colors` |
| 75 | `@Composable private fun Stepper(value: Int, onDec: () -> Unit, onInc: () -> Unit)` |
| 76 |   `val colors` |
| 85 | `@Composable private fun StepButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit)` |
| 86 |   `val colors` |

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
| 28 | `@Composable fun McpMarketSheet(viewModel: McpMarketViewModel, onDismiss: () -> Unit, onInstalled: () -> Unit)` |
| 29 |   `val ui by viewModel.ui.collectAsState()` |
| 30 |   `val colors` |
| 31 |   `var confirm by remember` |
| 32 |   `val close` |
| 100 | `@Composable private fun MarketSelect(label: String, value: String, choices: List<Pair<String, String>>, enabled: Boolean, onSelect: (String) -> Unit)` |

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
| 52 | `private const val SPEC_TEMPLATE` |
| 56 | `@Composable fun McpContent(viewModel: McpViewModel = hiltViewModel())` |
| 57 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 58 |   `val colors` |
| 59 |   `val marketViewModel: McpMarketViewModel` |
| 60 |   `val marketUi by marketViewModel.ui.collectAsStateWithLifecycle()` |
| 61 |   `var marketOpen by remember` |
| 62 |   `var editor by remember` |
| 95 | `private data class McpEditorTarget(val server: LocalMcpServer?)` |
| 99 | `@Composable private fun McpRow(s: LocalMcpServer, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 100 |   `val colors` |
| 101 |   `var menu by remember` |
| 102 |   `var deleting by remember` |
| 124 | `@Composable private fun McpEditor( existing: LocalMcpServer?, onDismiss: () -> Unit, onSave: (String, String, List<String>, (String) -> Unit) -> Unit, )` |
| 129 |   `val colors` |
| 130 |   `var id by remember` |
| 131 |   `var spec by remember` |
| 132 |   `val apps: SnapshotStateList<String>` |
| 133 |   `var error by remember` |

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
| 47 | `@Composable fun ModelProvidersContent(viewModel: ModelProvidersViewModel = hiltViewModel())` |
| 48 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 49 |   `val colors` |
| 50 |   `var editor by remember` |
| 85 | `private data class ProviderEditorTarget(val provider: ModelProviderInfo?)` |
| 89 | `@Composable private fun ProviderRow(p: ModelProviderInfo, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 90 |   `val colors` |
| 91 |   `var menu by remember` |
| 92 |   `var deleting by remember` |
| 114 | `@Composable private fun ProviderEditor( existing: ModelProviderInfo?, supportedAgents: List<AgentType>, onDismiss: () -> Unit, onSave: (String, String, String, AgentType, String?) -> Unit, )` |
| 120 |   `val colors` |
| 121 |   `var name by remember` |
| 122 |   `var url by remember` |
| 123 |   `var key by remember` |
| 124 |   `var agent by remember` |
| 125 |   `var model by remember` |

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
| 43 | `@Composable fun QuickMessagesContent(viewModel: QuickMessagesViewModel = hiltViewModel())` |
| 44 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 45 |   `val colors` |
| 46 |   `var editor by remember` |
| 75 | `private data class EditorTarget(val message: QuickMessage?)` |
| 79 | `@Composable private fun MessageRow(msg: QuickMessage, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 80 |   `val colors` |
| 81 |   `var menu by remember` |
| 82 |   `var deleting by remember` |
| 102 | `@Composable private fun QuickMessageEditor(existing: QuickMessage?, onDismiss: () -> Unit, onSave: (String, String) -> Unit)` |
| 103 |   `var title by remember` |
| 104 |   `var content by remember` |

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
| 8 | `@Composable internal fun SettingsChoice(label: String, value: String, choices: List<Pair<String, String>>, enabled: Boolean = true, onPick: (String) -> Unit)` |

## feature/settings/SettingsScreen.kt

| Line | Declaration |
|---|---|
| 59 | `private enum class SettingsLeaf(@StringRes val titleRes: Int, val icon: ImageVector, @StringRes val groupRes: Int, val implemented: Boolean)` |
| 82 | `@Composable fun SettingsScreen( onOpenAutomations: () -> Unit = {}, onOpenWorkTasks: () -> Unit = {}, onOpenServerLogs: () -> Unit = {}, logsViewModel: app.codeg.android.feature.serverlogs.ServerLogsViewModel = hiltViewModel(), viewModel: SettingsViewModel = hiltViewModel(), )` |
| 89 |   `val logsUi by logsViewModel.ui.collectAsStateWithLifecycle()` |
| 90 |   `var leaf by remember` |
| 92 |   `var agentDetail by remember` |
| 93 |   `val inAgentDetail` |
| 156 | `@Composable private fun SettingsRoot( onSelect: (SettingsLeaf) -> Unit, onOpenAutomations: () -> Unit, onOpenWorkTasks: () -> Unit, showServerLogs: Boolean, )` |
| 162 |   `val workTasks: app.codeg.android.feature.worktasks.WorkTaskViewModel` |
| 163 |   `val workTaskUi by workTasks.ui.collectAsStateWithLifecycle()` |
| 164 |   `val assistants: AssistantsViewModel` |
| 165 |   `val assistantUi by assistants.ui.collectAsStateWithLifecycle()` |
| 166 |   `val colors` |
| 209 | `@Composable private fun AppearanceSettings(viewModel: SettingsViewModel)` |
| 210 |   `val colors` |
| 211 |   `val settings by viewModel.settings.collectAsStateWithLifecycle()` |
| 261 | `@Composable private fun AboutScreen(viewModel: SettingsViewModel)` |
| 262 |   `val colors` |
| 263 |   `val about by viewModel.about.collectAsStateWithLifecycle()` |
| 264 |   `val appVersion` |
| 279 | `@Composable private fun ComingSoon(@StringRes title: Int)` |
| 280 |   `val colors` |

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
| 69 | `private sealed interface SkillEditTarget` |
| 70 |   `data object Add : SkillEditTarget` |
| 71 |   `data class Edit(val skill: AgentSkillItem) : SkillEditTarget` |
| 76 | `@Composable fun SkillsContent(viewModel: SkillsViewModel = hiltViewModel())` |
| 77 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 78 |   `val colors` |
| 79 |   `var editor by remember` |
| 80 |   `var pendingDelete by remember` |
| 144 | `@Composable private fun AgentFilterBar(agents: List<AgentType>, selected: AgentType?, onSelect: (AgentType) -> Unit)` |
| 145 |   `val colors` |
| 159 | `@Composable private fun SkillsArea( ui: SkillsUiState, onAdd: () -> Unit, onOpen: (AgentSkillItem) -> Unit, onDelete: (AgentSkillItem) -> Unit, onRetry: () -> Unit, onDismissError: () -> Unit, )` |
| 167 |   `val colors` |
| 168 |   `val result` |
| 205 | `@Composable private fun SkillRow(skill: AgentSkillItem, onClick: () -> Unit, onDelete: () -> Unit)` |
| 206 |   `val colors` |
| 217 | `@Composable private fun RefreshErrorBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 218 |   `val colors` |
| 231 | `@Composable private fun SkillEditorDialog( target: SkillEditTarget, agent: AgentType, loadContent: suspend (AgentSkillItem) -> String, onSave: suspend (id: String, scope: String, content: String, layout: String?) -> Unit, onDismiss: () -> Unit, )` |
| 238 |   `val colors` |
| 239 |   `val scope` |
| 240 |   `val existing` |
| 241 |   `val isReadOnly` |
| 242 |   `val skillScope` |
| 243 |   `val layout` |
| 245 |   `var skillId by remember` |
| 246 |   `var content by remember` |
| 247 |   `var loadingContent by remember` |
| 248 |   `var saving by remember` |
| 249 |   `var error by remember` |
| 260 |   `val canSave` |

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

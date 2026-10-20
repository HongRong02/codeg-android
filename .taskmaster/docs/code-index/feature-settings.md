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
| 72 | `private data class KimiBanner(val text: String, val isError: Boolean)` |
| 86 | `@Composable fun AgentConfigKimiView(agent: AcpAgentInfo, viewModel: AgentsViewModel)` |
| 87 |   `val context` |
| 88 |   `val colors` |
| 89 |   `val scope` |
| 93 |   `val seed` |
| 94 |   `val seedInterface` |
| 95 |   `var mode by remember` |
| 96 |   `var interfaceType by remember` |
| 97 |   `var region by remember` |
| 98 |   `var baseUrl by remember` |
| 99 |   `var authType by remember` |
| 100 |   `var apiKey by remember` |
| 101 |   `var modelId by remember` |
| 102 |   `var maxContext by remember` |
| 103 |   `var vertexProject by remember` |
| 104 |   `var vertexLocation by remember` |
| 105 |   `var rawConfig by remember` |
| 106 |   `var models by remember` |
| 107 |   `var saving by remember` |
| 108 |   `var fetchingModels by remember` |
| 109 |   `var banner by remember` |
| 111 |   `val meta` |
| 112 |   `val isKimi` |
| 113 |   `val isVertex` |
| 114 |   `val effectiveBaseUrl` |
| 115 |   `val liveConfig` |
| 123 |   `val savedMsg` |
| 124 |   `val modelRequiredMsg` |
| 125 |   `val enterKeyMsg` |
| 126 |   `val modelsNoneMsg` |
| 127 |   `val modelsErrMsg` |
| 128 |   `val modelsFoundFmt` |
| 130 |   `fun runSave(body: UpdateKimiCodeConfigBody)` |
| 145 |   `fun handleSave()` |
| 169 |   `fun fetchModels()` |
| 191 |   `fun setInterface(next: KimiInterfaceType)` |
| 335 | `@Composable private fun KimiGateBanner(present: Boolean, mode: KimiAuthMode)` |
| 336 |   `val colors` |
| 337 |   `val tint` |
| 338 |   `val text` |
| 353 | `@Composable private fun KimiResultBanner(banner: KimiBanner)` |
| 354 |   `val colors` |
| 355 |   `val tint` |
| 367 | `@Composable internal fun Caption(text: String)` |

## feature/settings/AgentConfigKiroView.kt

| Line | Declaration |
|---|---|
| 10 | `@Composable fun AgentConfigKiroView(agent: AcpAgentInfo)` |

## feature/settings/AgentConfigPiView.kt

| Line | Declaration |
|---|---|
| 65 | `private data class PiBanner(val text: String, val isError: Boolean)` |
| 77 | `@Composable fun AgentConfigPiView(agent: AcpAgentInfo, viewModel: AgentsViewModel)` |
| 78 |   `val context` |
| 79 |   `val colors` |
| 80 |   `val scope` |
| 82 |   `val seedEnv` |
| 84 |   `val seedCmd` |
| 85 |   `var mode by remember` |
| 86 |   `var command by remember` |
| 87 |   `var configDir by remember` |
| 88 |   `var sessionDir by remember` |
| 89 |   `var validation by remember` |
| 90 |   `var validating by remember` |
| 91 |   `var piStatus by remember` |
| 92 |   `var checkingPi by remember` |
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
| 102 | `@Composable fun AgentDetailContent( agentType: AgentType, onClose: () -> Unit, viewModel: AgentsViewModel = hiltViewModel(), )` |
| 107 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 108 |   `val detail` |
| 121 |   `val selfContained` |
| 175 | `@Composable private fun AgentHeader(state: AgentDetailState, onToggle: (Boolean) -> Unit)` |
| 176 |   `val colors` |
| 177 |   `val agent` |
| 215 | `@Composable private fun VersionLine(state: AgentDetailState)` |
| 216 |   `val colors` |
| 217 |   `val installed` |
| 218 |   `val latest` |
| 219 |   `val text` |
| 229 | `@Composable private fun SaveBar(canSave: Boolean, saving: Boolean, error: String?, onSave: () -> Unit)` |
| 230 |   `val colors` |
| 245 | `@Composable private fun ClaudeSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 246 |   `val d` |
| 291 | `private fun claudeFooterRes(mode: ClaudeAuthMode): Int` |
| 298 | `@Composable private fun CodexSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 299 |   `val colors` |
| 300 |   `val context` |
| 301 |   `val d` |
| 339 | `private fun codexEffortLabelRes(e: CodexReasoningEffort): Int` |
| 347 | `@Composable private fun GeminiSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 348 |   `val colors` |
| 349 |   `val d` |
| 399 | `@Composable private fun OpenClawSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 400 |   `val d` |
| 409 | `@Composable private fun ClineSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 410 |   `val d` |
| 425 | `@Composable private fun OpenCodeSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 426 |   `val d` |
| 434 | `@Composable private fun HermesSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 435 |   `val d` |
| 436 |   `val option` |
| 450 | `private fun hermesFooterRes(kind: HermesProviderKind?): Int` |
| 464 | `@Composable private fun CodeBuddySection(state: AgentDetailState, vm: AgentsViewModel)` |
| 465 |   `val colors` |
| 466 |   `val d` |
| 467 |   `val selfHosted` |
| 511 | `@Composable private fun ServerManagedSection()` |
| 521 | `@Composable private fun GrokSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 522 |   `val colors` |
| 523 |   `val d` |
| 524 |   `val useDefault` |
| 578 | `@Composable private fun CursorSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 579 |   `val colors` |
| 580 |   `val d` |
| 581 |   `val custom` |
| 583 |   `var auth by remember` |
| 584 |   `var authLoading by remember` |
| 585 |   `var models by remember` |
| 586 |   `var modelsError by remember` |
| 587 |   `var modelsLoading by remember` |
| 590 |   `val probeKey` |
| 591 |   `var refreshTick by remember` |
| 672 |   `val savedModel` |
| 734 | `@Composable private fun CursorStatusRow(auth: CursorAuthStatus?, loading: Boolean, onRefresh: () -> Unit)` |
| 735 |   `val colors` |
| 736 |   `val installed` |
| 737 |   `val signedIn` |
| 738 |   `val tint` |
| 743 |   `val text` |
| 784 | `@Composable private fun RuleEditor( label: String, rules: List<String>, placeholder: String, tone: Color, onChange: (List<String>) -> Unit, )` |
| 791 |   `val colors` |
| 828 | `@Composable private fun ColumnScope.ProviderPicker(state: AgentDetailState, vm: AgentsViewModel)` |
| 829 |   `val colors` |
| 847 | `@Composable private fun ProviderSummary(p: ModelProviderInfo)` |
| 856 | `@Composable private fun ReadonlyRow(label: String, value: String)` |
| 857 |   `val colors` |
| 869 | `@Composable internal fun FormSection(title: String, footer: String? = null, content: @Composable ColumnScope.() -> Unit)` |
| 870 |   `val colors` |
| 884 | `@Composable internal fun <T> SelectField(label: String, value: T, options: List<Pair<T, String>>, onSelect: (T) -> Unit)` |
| 885 |   `val colors` |
| 886 |   `var expanded by remember` |
| 887 |   `val display` |
| 908 | `@Composable private fun HermesProviderField(value: String, onSelect: (String) -> Unit)` |
| 909 |   `val colors` |
| 910 |   `var expanded by remember` |
| 911 |   `val display` |
| 940 | `private fun kindLabelRes(kind: HermesProviderKind): Int` |
| 947 | `@Composable private fun ToggleRow(title: String, checked: Boolean, onChange: (Boolean) -> Unit)` |
| 948 |   `val colors` |
| 964 | `@Composable internal fun ColumnScope.ExpandableGroup(title: String, content: @Composable ColumnScope.() -> Unit)` |
| 965 |   `val colors` |
| 966 |   `var open by remember` |
| 967 |   `val rotation by animateFloatAsState(if (open) 180f else 0f, label = "chevron")` |
| 982 | `@Composable private fun TonalAction(text: String, onClick: () -> Unit)` |
| 983 |   `val colors` |
| 994 | `@Composable private fun AdvancedSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 995 |   `val colors` |
| 996 |   `var open by remember` |
| 997 |   `val rotation by animateFloatAsState(if (open) 180f else 0f, label = "advanced")` |
| 998 |   `val label` |
| 1003 |   `val text` |
| 1047 | `@Composable private fun dropdownColors(colors: CodegColors)` |

## feature/settings/AgentsScreen.kt

| Line | Declaration |
|---|---|
| 44 | `@Composable fun AgentsContent(onOpen: (AgentType) -> Unit, viewModel: AgentsViewModel = hiltViewModel())` |
| 45 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 61 | `@Composable private fun AgentRow(agent: AcpAgentInfo, onToggle: (Boolean) -> Unit, onOpen: () -> Unit)` |
| 62 |   `val colors` |

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

## feature/settings/ChatChannelsScreen.kt

| Line | Declaration |
|---|---|
| 111 | `internal fun channelTint(type: ChannelType): Color` |
| 117 | `internal fun channelIcon(type: ChannelType): ImageVector` |
| 124 | `@Composable internal fun statusTint(status: ChannelConnectionStatus): Color` |
| 132 | `@Composable internal fun ChannelTypeAvatar(type: ChannelType, size: Dp)` |
| 133 |   `val tint` |
| 140 | `@Composable internal fun ChannelStatusPill(status: ChannelConnectionStatus)` |
| 141 |   `val tint` |
| 150 | `private sealed interface ChannelEditorTarget` |
| 151 |   `data object Add : ChannelEditorTarget` |
| 152 |   `data class Edit(val channel: ChatChannelInfo) : ChannelEditorTarget` |
| 157 | `@Composable fun ChatChannelsContent(viewModel: ChatChannelsViewModel = hiltViewModel())` |
| 158 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 159 |   `val colors` |
| 160 |   `var editor by remember` |
| 161 |   `var showGlobal by remember` |
| 162 |   `var pendingDelete by remember` |
| 279 | `@Composable private fun ChannelRow( channel: ChatChannelInfo, status: ChannelConnectionStatus, toggling: Boolean, onOpen: () -> Unit, onToggle: (Boolean) -> Unit, onDelete: () -> Unit, )` |
| 287 |   `val colors` |
| 288 |   `val summary` |
| 315 | `@Composable private fun ChatRefreshBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 316 |   `val colors` |
| 331 | `@Composable private fun ChatChannelDetailDialog( channel: ChatChannelInfo, detail: ChannelDetailState, viewModel: ChatChannelsViewModel, onEdit: () -> Unit, onDismiss: () -> Unit, )` |
| 338 |   `val colors` |
| 339 |   `var showQR by remember` |
| 340 |   `var confirmRemoveToken by remember` |
| 467 | `@Composable private fun MessageLogRow(message: ChatChannelMessageLog)` |
| 468 |   `val colors` |
| 479 | `@Composable private fun ActionButton(title: String, icon: ImageVector, tint: Color, enabled: Boolean, onClick: () -> Unit)` |
| 497 | `@Composable private fun DetailSection(title: String, content: @Composable () -> Unit)` |
| 498 |   `val colors` |
| 506 | `@Composable private fun SettingRow(content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit)` |
| 511 | `@Composable private fun Divider()` |
| 519 | `private enum class QrPhase` |
| 523 | `@Composable private fun WeixinQRDialog(channelId: Int, viewModel: ChatChannelsViewModel, onConnected: () -> Unit, onDismiss: () -> Unit)` |
| 524 |   `val colors` |
| 525 |   `val loadingQrText` |
| 526 |   `val couldntLoadQrText` |
| 527 |   `val scanWithWechatText` |
| 528 |   `val connectedText` |
| 529 |   `val qrExpiredText` |
| 530 |   `val scannedConfirmText` |
| 531 |   `var attempt by remember` |
| 532 |   `var phase by remember` |
| 533 |   `var statusText by remember` |
| 534 |   `var image by remember` |
| 594 | `private fun decodeQrImage(raw: String): androidx.compose.ui.graphics.ImageBitmap?` |
| 595 |   `var b64` |
| 596 |   `val comma` |
| 608 | `private val lenientJson` |
| 610 | `private fun normalizedObjectJson(raw: String): String?` |
| 611 |   `val obj` |
| 617 | `@Composable private fun ChatChannelEditorDialog(existing: ChatChannelInfo?, viewModel: ChatChannelsViewModel, onDismiss: () -> Unit)` |
| 618 |   `val colors` |
| 619 |   `val scope` |
| 620 |   `val isEdit` |
| 622 |   `var name by remember` |
| 623 |   `var type by remember` |
| 624 |   `val parsed` |
| 625 |   `var chatId by remember` |
| 626 |   `var appId by remember` |
| 627 |   `var baseUrl by remember` |
| 628 |   `var rawMode by remember` |
| 629 |   `var rawText by remember` |
| 630 |   `var token by remember` |
| 631 |   `var hasToken by remember` |
| 632 |   `var enabled by remember` |
| 633 |   `var dailyEnabled by remember` |
| 634 |   `var dailyTime by remember` |
| 635 |   `var saving by remember` |
| 636 |   `var error by remember` |
| 637 |   `val couldntSaveText` |
| 643 |   `fun currentConfig()` |
| 644 |   `val configJson: String?` |
| 645 |   `val configValid` |
| 650 |   `val tokenRequired` |
| 651 |   `val canSave` |
| 653 |   `fun save()` |
| 744 | `private fun buildUpdateBody(existing: ChatChannelInfo, name: String, enabled: Boolean, configJson: String, dailyEnabled: Boolean, timeString: String?): UpdateChatChannelBody` |
| 745 |   `val configChanged` |
| 746 |   `val timeEdit: FieldEdit` |

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
| 73 | `@Composable fun ChatGlobalSettingsDialog(onDismiss: () -> Unit, viewModel: ChatGlobalSettingsViewModel = hiltViewModel())` |
| 74 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 75 |   `val colors` |
| 76 |   `var newWebhook by remember` |
| 77 |   `var prefixField by remember(ui.commandPrefix)` |
| 168 | `@Composable private fun GlobalSection(title: String, subtitle: String, content: @Composable () -> Unit)` |
| 169 |   `val colors` |

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

## feature/settings/ExpertsScreen.kt

| Line | Declaration |
|---|---|
| 71 | `@Composable fun ExpertsContent(viewModel: ExpertsViewModel = hiltViewModel())` |
| 72 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 73 |   `val colors` |
| 119 | `@Composable private fun ExpertRow(expert: ExpertListItem, onClick: () -> Unit)` |
| 120 |   `val colors` |
| 138 | `@Composable private fun ExpertIconTile(size: androidx.compose.ui.unit.Dp)` |
| 139 |   `val colors` |
| 149 | `@Composable private fun ExpertsRefreshBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 150 |   `val colors` |
| 163 | `@Composable private fun ExpertDetailDialog( detail: DetailState, agents: List<AgentType>, onToggle: (AgentType, Boolean) -> Unit, onDismiss: () -> Unit, onDismissError: () -> Unit, )` |
| 170 |   `val colors` |
| 171 |   `val expert` |
| 241 | `@Composable private fun AgentLinkRow(agent: AgentType, status: ExpertInstallStatus?, toggling: Boolean, onToggle: (Boolean) -> Unit)` |
| 242 |   `val colors` |
| 243 |   `val state` |
| 244 |   `val isLinked` |
| 245 |   `val blocked` |
| 246 |   `val caption` |
| 268 | `@Composable private fun linkCaption(state: ExpertLinkState?, copyMode: Boolean): Pair<String, Color>?` |
| 269 |   `val colors` |
| 280 | `private fun stripFrontmatter(content: String): String` |
| 281 |   `val lines` |
| 283 |   `val end` |

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
| 42 | `@Composable fun GeneralContent(viewModel: GeneralViewModel = hiltViewModel())` |
| 43 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 44 |   `val colors` |
| 74 | `@Composable private fun Section(title: String, content: @Composable () -> Unit)` |
| 75 |   `val colors` |
| 83 | `@Composable private fun ToggleRow(title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit)` |
| 84 |   `val colors` |
| 105 | `@Composable private fun Stepper(value: Int, onDec: () -> Unit, onInc: () -> Unit)` |
| 106 |   `val colors` |
| 115 | `@Composable private fun StepButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit)` |
| 116 |   `val colors` |

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
| 49 | `@Composable fun LanguageContent()` |
| 50 |   `val context` |
| 51 |   `val colors` |
| 52 |   `var selected by remember` |
| 98 | `private tailrec fun Context.findActivity(): Activity?` |

## feature/settings/McpScreen.kt

| Line | Declaration |
|---|---|
| 69 | `private const val SPEC_TEMPLATE` |
| 73 | `@Composable fun McpContent(viewModel: McpViewModel = hiltViewModel())` |
| 74 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 75 |   `val colors` |
| 76 |   `var editor by remember` |
| 107 | `private data class McpEditorTarget(val server: LocalMcpServer?)` |
| 111 | `@Composable private fun McpRow(s: LocalMcpServer, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 112 |   `val colors` |
| 113 |   `var menu by remember` |
| 132 | `@Composable private fun McpEditor( existing: LocalMcpServer?, onDismiss: () -> Unit, onSave: (String, String, List<String>, (String) -> Unit) -> Unit, )` |
| 137 |   `val colors` |
| 138 |   `var id by remember` |
| 139 |   `var spec by remember` |
| 140 |   `val apps: SnapshotStateList<String>` |
| 141 |   `var error by remember` |

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
| 61 | `@Composable fun ModelProvidersContent(viewModel: ModelProvidersViewModel = hiltViewModel())` |
| 62 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 63 |   `val colors` |
| 64 |   `var editor by remember` |
| 104 | `private data class ProviderEditorTarget(val provider: ModelProviderInfo?)` |
| 108 | `@Composable private fun ProviderRow(p: ModelProviderInfo, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 109 |   `val colors` |
| 110 |   `var menu by remember` |
| 129 | `@Composable private fun ProviderEditor( existing: ModelProviderInfo?, supportedAgents: List<AgentType>, onDismiss: () -> Unit, onSave: (String, String, String, AgentType, String?) -> Unit, )` |
| 135 |   `val colors` |
| 136 |   `var name by remember` |
| 137 |   `var url by remember` |
| 138 |   `var key by remember` |
| 139 |   `var agent by remember` |
| 140 |   `var model by remember` |
| 141 |   `var agentMenu by remember` |

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
| 52 | `@Composable fun QuickMessagesContent(viewModel: QuickMessagesViewModel = hiltViewModel())` |
| 53 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 54 |   `val colors` |
| 55 |   `var editor by remember` |
| 89 | `private data class EditorTarget(val message: QuickMessage?)` |
| 93 | `@Composable private fun MessageRow(msg: QuickMessage, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 94 |   `val colors` |
| 95 |   `var menu by remember` |
| 112 | `@Composable private fun QuickMessageEditor(existing: QuickMessage?, onDismiss: () -> Unit, onSave: (String, String) -> Unit)` |
| 113 |   `var title by remember` |
| 114 |   `var content by remember` |

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

## feature/settings/SettingsScreen.kt

| Line | Declaration |
|---|---|
| 76 | `private enum class SettingsLeaf(@StringRes val titleRes: Int, val icon: ImageVector, @StringRes val groupRes: Int, val implemented: Boolean)` |
| 97 | `@Composable fun SettingsScreen( onOpenAutomations: () -> Unit = {}, onOpenWorkTasks: () -> Unit = {}, viewModel: SettingsViewModel = hiltViewModel(), )` |
| 102 |   `var leaf by remember` |
| 104 |   `var agentDetail by remember` |
| 105 |   `val inAgentDetail` |
| 164 | `@Composable private fun SettingsRoot(onSelect: (SettingsLeaf) -> Unit, onOpenAutomations: () -> Unit, onOpenWorkTasks: () -> Unit)` |
| 165 |   `val workTasks: app.codeg.android.feature.worktasks.WorkTaskViewModel` |
| 166 |   `val workTaskUi by workTasks.ui.collectAsStateWithLifecycle()` |
| 167 |   `val colors` |
| 212 | `@Composable private fun AppearanceSettings(viewModel: SettingsViewModel)` |
| 213 |   `val colors` |
| 214 |   `val settings by viewModel.settings.collectAsStateWithLifecycle()` |
| 262 | `@Composable private fun AboutScreen(viewModel: SettingsViewModel)` |
| 263 |   `val colors` |
| 264 |   `val about by viewModel.about.collectAsStateWithLifecycle()` |
| 265 |   `val context` |
| 266 |   `val appVersion` |
| 282 | `@Composable private fun ComingSoon(@StringRes title: Int)` |
| 283 |   `val colors` |

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
| 82 | `private sealed interface SkillEditTarget` |
| 83 |   `data object Add : SkillEditTarget` |
| 84 |   `data class Edit(val skill: AgentSkillItem) : SkillEditTarget` |
| 89 | `@Composable fun SkillsContent(viewModel: SkillsViewModel = hiltViewModel())` |
| 90 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 91 |   `val colors` |
| 92 |   `var editor by remember` |
| 93 |   `var pendingDelete by remember` |
| 162 | `@Composable private fun AgentFilterBar(agents: List<AgentType>, selected: AgentType?, onSelect: (AgentType) -> Unit)` |
| 163 |   `val colors` |
| 193 | `@Composable private fun SkillsArea( ui: SkillsUiState, onAdd: () -> Unit, onOpen: (AgentSkillItem) -> Unit, onDelete: (AgentSkillItem) -> Unit, onRetry: () -> Unit, onDismissError: () -> Unit, )` |
| 201 |   `val colors` |
| 202 |   `val result` |
| 244 | `@Composable private fun SkillRow(skill: AgentSkillItem, onClick: () -> Unit, onDelete: () -> Unit)` |
| 245 |   `val colors` |
| 246 |   `val isDir` |
| 272 | `@Composable private fun RefreshErrorBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 273 |   `val colors` |
| 286 | `@Composable private fun SkillEditorDialog( target: SkillEditTarget, agent: AgentType, loadContent: suspend (AgentSkillItem) -> String, onSave: suspend (id: String, scope: String, content: String, layout: String?) -> Unit, onDismiss: () -> Unit, )` |
| 293 |   `val colors` |
| 294 |   `val scope` |
| 295 |   `val existing` |
| 296 |   `val isReadOnly` |
| 297 |   `val skillScope` |
| 298 |   `val layout` |
| 300 |   `var skillId by remember` |
| 301 |   `var content by remember` |
| 302 |   `var loadingContent by remember` |
| 303 |   `var saving by remember` |
| 304 |   `var error by remember` |
| 315 |   `val canSave` |

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
| 35 | `@Composable fun SystemContent(viewModel: SystemViewModel = hiltViewModel())` |
| 36 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 37 |   `val colors` |
| 75 | `@Composable private fun PillButton(label: String, loading: Boolean, onClick: () -> Unit)` |
| 76 |   `val colors` |

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
| 39 | `@Composable fun VersionControlContent(viewModel: VersionControlViewModel = hiltViewModel())` |
| 40 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 41 |   `val colors` |
| 94 | `@Composable private fun Pill(label: String, loading: Boolean, onClick: () -> Unit, filled: Boolean)` |
| 95 |   `val colors` |

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

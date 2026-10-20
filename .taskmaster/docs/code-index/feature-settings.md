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
| 71 | `private data class KimiBanner(val text: String, val isError: Boolean)` |
| 85 | `@Composable fun AgentConfigKimiView(agent: AcpAgentInfo, viewModel: AgentsViewModel)` |
| 86 |   `val colors` |
| 87 |   `val scope` |
| 91 |   `val seed` |
| 92 |   `val seedInterface` |
| 93 |   `var mode by remember` |
| 94 |   `var interfaceType by remember` |
| 95 |   `var region by remember` |
| 96 |   `var baseUrl by remember` |
| 97 |   `var authType by remember` |
| 98 |   `var apiKey by remember` |
| 99 |   `var modelId by remember` |
| 100 |   `var maxContext by remember` |
| 101 |   `var vertexProject by remember` |
| 102 |   `var vertexLocation by remember` |
| 103 |   `var rawConfig by remember` |
| 104 |   `var models by remember` |
| 105 |   `var saving by remember` |
| 106 |   `var fetchingModels by remember` |
| 107 |   `var banner by remember` |
| 109 |   `val meta` |
| 110 |   `val isKimi` |
| 111 |   `val isVertex` |
| 112 |   `val effectiveBaseUrl` |
| 113 |   `val liveConfig` |
| 121 |   `val savedMsg` |
| 122 |   `val modelRequiredMsg` |
| 123 |   `val enterKeyMsg` |
| 124 |   `val modelsNoneMsg` |
| 125 |   `val modelsErrMsg` |
| 126 |   `val modelsFoundFmt` |
| 128 |   `fun runSave(body: UpdateKimiCodeConfigBody)` |
| 143 |   `fun handleSave()` |
| 167 |   `fun fetchModels()` |
| 189 |   `fun setInterface(next: KimiInterfaceType)` |
| 333 | `@Composable private fun KimiGateBanner(present: Boolean, mode: KimiAuthMode)` |
| 334 |   `val colors` |
| 335 |   `val tint` |
| 336 |   `val text` |
| 351 | `@Composable private fun KimiResultBanner(banner: KimiBanner)` |
| 352 |   `val colors` |
| 353 |   `val tint` |
| 365 | `@Composable internal fun Caption(text: String)` |

## feature/settings/AgentConfigKiroView.kt

| Line | Declaration |
|---|---|
| 10 | `@Composable fun AgentConfigKiroView(agent: AcpAgentInfo)` |

## feature/settings/AgentConfigPiView.kt

| Line | Declaration |
|---|---|
| 64 | `private data class PiBanner(val text: String, val isError: Boolean)` |
| 76 | `@Composable fun AgentConfigPiView(agent: AcpAgentInfo, viewModel: AgentsViewModel)` |
| 77 |   `val colors` |
| 78 |   `val scope` |
| 80 |   `val seedEnv` |
| 82 |   `val seedCmd` |
| 83 |   `var mode by remember` |
| 84 |   `var command by remember` |
| 85 |   `var configDir by remember` |
| 86 |   `var sessionDir by remember` |
| 87 |   `var validation by remember` |
| 88 |   `var validating by remember` |
| 89 |   `var piStatus by remember` |
| 90 |   `var checkingPi by remember` |
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
| 169 |   `fun detectPi()` |
| 177 |   `fun handleValidate()` |
| 188 |   `fun handleSaveRuntime()` |
| 204 |   `fun handleSaveCreds()` |
| 244 |   `fun handlePiOp(install: Boolean)` |
| 260 |   `fun toggleTrust(next: Boolean)` |
| 418 | `@Composable private fun PiActionButton(text: String, busy: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 419 |   `val colors` |
| 427 | `@Composable private fun PiResultBanner(banner: PiBanner)` |
| 428 |   `val colors` |
| 429 |   `val tint` |
| 440 | `@Composable private fun piThinkingLabel(level: String): String` |

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
| 47 | `class AgentsViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 48 |   `private val repository: ServerRepository` |
| 51 |   `private val _ui` |
| 52 |   `val ui: StateFlow<AgentsUiState>` |
| 54 |   `private var client: CodegClient?` |
| 62 |   `private val opMutex` |
| 68 |   `fun load()` |
| 72 |   `private suspend fun reload()` |
| 98 |   `fun setEnabled(agent: AcpAgentInfo, enabled: Boolean)` |
| 121 |   `private fun applyEnabled(type: AgentType, enabled: Boolean)` |
| 139 |   `fun openDetail(agentType: AgentType)` |
| 164 |   `fun closeDetail()` |
| 167 |   `fun editDraft(transform: (AgentDraft) -> AgentDraft)` |
| 177 |   `fun rebakeDraft()` |
| 180 |   `fun editRawConfig(text: String)` |
| 190 |   `fun setModelProvider(id: Int?)` |
| 192 |   `fun setClaudeAuthMode(mode: ClaudeAuthMode)` |
| 205 |   `fun setCodexAuthMode(mode: CodexAuthMode)` |
| 213 |   `fun setGeminiAuthMode(mode: GeminiAuthMode)` |
| 218 |   `fun setHermesProvider(id: String)` |
| 230 |   `fun save(onSuccess: () -> Unit)` |
| 318 |   `suspend fun saveKimiCodeConfig(body: UpdateKimiCodeConfigBody)` |
| 327 |   `suspend fun fetchKimiModels(baseUrl: String, apiKey: String): List<String>` |
| 341 |   `suspend fun updatePiEnv(agent: AcpAgentInfo, env: Map<String, String>)` |
| 350 |   `suspend fun loadPiConfig(): PiConfigProjection` |
| 359 |   `suspend fun savePiConfig(body: UpdatePiConfigBody)` |
| 365 |   `suspend fun validatePiCommand(command: String): PiCommandValidation` |
| 371 |   `suspend fun installPiBinary()` |
| 376 |   `suspend fun uninstallPiBinary()` |
| 390 |   `suspend fun cursorAuthStatus(apiKey: String): CursorAuthStatus` |
| 396 |   `suspend fun cursorListModels(apiKey: String): CursorModelsResult` |
| 403 |   `private fun updateDetail(block: (AgentDetailState) -> AgentDetailState)` |
| 406 |   `private fun agentSupportsProviders(agent: AgentType): Boolean` |
| 409 |   `private data class ConfigPayload( val configJson: String?, val opencodeAuthJson: String?, val codexAuthJson: String?, val codexConfigToml: String?, val grokConfigToml: String? = null, val cursorCliConfigJson: String? = null, val cursorStructured: CursorStructuredConfig? = null, )` |
| 420 |   `private fun makeConfigBody(agentType: AgentType, draft: AgentDraft, original: AcpAgentInfo): ConfigPayload` |
| 468 |   `private data class HermesBody(val provider: String, val model: String?, val apiKey: FieldEdit, val baseUrl: FieldEdit)` |
| 470 |   `private fun hermesBody(d: AgentDraft): HermesBody` |
| 491 | `internal fun toggleWriteSource(agents: List<AcpAgentInfo>, captured: AcpAgentInfo): AcpAgentInfo` |
| 504 | `internal fun grokConfigTomlForSave(draft: AgentDraft, snapshot: AcpAgentInfo, freshToml: String): String?` |
| 506 |   `val pmChanged` |
| 507 |   `val reChanged` |
| 520 | `private val nativeRawConfigAgents` |
| 528 | `internal fun cursorCliConfigForSave(draft: AgentDraft, original: AcpAgentInfo): String?` |
| 545 | `internal fun cursorStructuredForSave(draft: AgentDraft, original: AcpAgentInfo): CursorStructuredConfig?` |
| 554 | `data class AgentsUiState( val agents: List<AcpAgentInfo> = emptyList(), val loading: Boolean = false, val error: String? = null, val detail: AgentDetailState? = null, )` |
| 555 |   `val agents: List<AcpAgentInfo>` |
| 556 |   `val loading: Boolean` |
| 557 |   `val error: String?` |
| 558 |   `val detail: AgentDetailState?` |
| 561 | `data class AgentDetailState( val agentType: AgentType, val agent: AcpAgentInfo, val draft: AgentDraft, val original: AgentDraft, val providers: List<ModelProviderInfo> = emptyList(), val saving: Boolean = false, val saveError: String? = null, )` |
| 562 |   `val agentType: AgentType` |
| 563 |   `val agent: AcpAgentInfo` |
| 564 |   `val draft: AgentDraft` |
| 565 |   `val original: AgentDraft` |
| 566 |   `val providers: List<ModelProviderInfo>` |
| 567 |   `val saving: Boolean` |
| 568 |   `val saveError: String?` |
| 570 |   `val dirty: Boolean get()` |
| 571 |   `val missingProvider: Boolean get()` |
| 573 |   `val missingCodeBuddyBaseUrl: Boolean get()` |
| 575 |   `val missingCursorApiKey: Boolean get()` |
| 576 |   `val canSave: Boolean` |
| 578 |   `val configError: String?` |
| 581 |   `val provider: ModelProviderInfo? get()` |

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
| 22 | `class ChatGlobalSettingsViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 23 |   `private val repository: ServerRepository` |
| 26 |   `private val _ui` |
| 27 |   `val ui: StateFlow<ChatGlobalUiState>` |
| 29 |   `private var client: CodegClient?` |
| 33 |   `fun load()` |
| 60 |   `fun setCommandPrefix(prefix: String)` |
| 67 |   `fun setLanguage(code: String)` |
| 73 |   `fun toggleEvent(id: String, on: Boolean)` |
| 82 |   `fun addWebhook(url: String)` |
| 88 |   `fun setWebhookEnabled(index: Int, on: Boolean)` |
| 95 |   `fun removeWebhook(index: Int)` |
| 102 |   `private fun persistWebhooks(next: List<WebhookConfig>)` |
| 111 |   `fun dismissError()` |
| 114 | `data class ChatGlobalUiState( val loading: Boolean = false, val commandPrefix: String = "/", val language: String = "en", val enabledEvents: Set<String> = ChatEventCatalog.defaultEnabled, val webhooks: List<WebhookConfig> = emptyList(), val error: String? = null, )` |
| 115 |   `val loading: Boolean` |
| 116 |   `val commandPrefix: String` |
| 117 |   `val language: String` |
| 118 |   `val enabledEvents: Set<String>` |
| 119 |   `val webhooks: List<WebhookConfig>` |
| 120 |   `val error: String?` |

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
| 28 | `class ExpertsViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 29 |   `private val repository: ServerRepository` |
| 32 |   `private val _ui` |
| 33 |   `val ui: StateFlow<ExpertsUiState>` |
| 35 |   `private var client: CodegClient?` |
| 39 |   `fun load()` |
| 59 |   `fun dismissRefreshError()` |
| 63 |   `fun openDetail(expert: ExpertListItem)` |
| 85 |   `fun closeDetail()` |
| 87 |   `fun dismissDetailError()` |
| 90 |   `private fun updateDetail(transform: (DetailState) -> DetailState)` |
| 93 |   `fun toggleLink(agent: AgentType, on: Boolean)` |
| 128 | `enum class ExpertsPhase` |
| 130 | `data class DetailState( val expert: ExpertListItem, val loading: Boolean = true, val content: String = "", val statusByAgent: Map<AgentType, ExpertInstallStatus> = emptyMap(), val toggling: Set<AgentType> = emptySet(), val error: String? = null, )` |
| 131 |   `val expert: ExpertListItem` |
| 132 |   `val loading: Boolean` |
| 133 |   `val content: String` |
| 134 |   `val statusByAgent: Map<AgentType, ExpertInstallStatus>` |
| 135 |   `val toggling: Set<AgentType>` |
| 136 |   `val error: String?` |
| 139 | `data class ExpertsUiState( val phase: ExpertsPhase = ExpertsPhase.LOADING, val experts: List<ExpertListItem> = emptyList(), val agents: List<AgentType> = emptyList(), val refreshError: String? = null, val error: String? = null, val detail: DetailState? = null, )` |
| 140 |   `val phase: ExpertsPhase` |
| 141 |   `val experts: List<ExpertListItem>` |
| 142 |   `val agents: List<AgentType>` |
| 143 |   `val refreshError: String?` |
| 144 |   `val error: String?` |
| 145 |   `val detail: DetailState?` |
| 148 |   `val grouped: List<Triple<String, String, List<ExpertListItem>>>` |
| 162 | `object ExpertCategory` |
| 163 |   `private val known` |
| 173 |   `fun rank(key: String): Int` |
| 175 |   `fun label(key: String): String` |

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
| 26 | `class GeneralViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 27 |   `private val repository: ServerRepository` |
| 30 |   `private val _ui` |
| 31 |   `val ui: StateFlow<GeneralUiState>` |
| 33 |   `private var client: CodegClient?` |
| 34 |   `private var delegationRaw: JsonObject` |
| 38 |   `fun load()` |
| 68 |   `fun setFeedback(enabled: Boolean)` |
| 74 |   `fun setQuestion(enabled: Boolean)` |
| 80 |   `fun setDelegationEnabled(enabled: Boolean)` |
| 85 |   `fun setDepth(depth: Int)` |
| 91 |   `private fun patchDelegation(key: String, value: JsonPrimitive)` |
| 98 |   `private fun reload()` |
| 101 | `data class GeneralUiState( val loading: Boolean = false, val feedbackEnabled: Boolean = true, val questionEnabled: Boolean = true, val delegationEnabled: Boolean = false, val depthLimit: Int = 3, val error: String? = null, )` |
| 102 |   `val loading: Boolean` |
| 103 |   `val feedbackEnabled: Boolean` |
| 104 |   `val questionEnabled: Boolean` |
| 105 |   `val delegationEnabled: Boolean` |
| 106 |   `val depthLimit: Int` |
| 107 |   `val error: String?` |

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
| 21 | `class ModelProvidersViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 22 |   `private val repository: ServerRepository` |
| 25 |   `private val _ui` |
| 26 |   `val ui: StateFlow<ModelProvidersUiState>` |
| 28 |   `private var client: CodegClient?` |
| 32 |   `fun load()` |
| 46 |   `fun save(existing: ModelProviderInfo?, name: String, apiUrl: String, apiKey: String, agentType: AgentType, model: String?)` |
| 69 |   `fun delete(provider: ModelProviderInfo)` |
| 79 | `data class ModelProvidersUiState( val providers: List<ModelProviderInfo> = emptyList(), val loading: Boolean = false, val error: String? = null, )` |
| 80 |   `val providers: List<ModelProviderInfo>` |
| 81 |   `val loading: Boolean` |
| 82 |   `val error: String?` |
| 85 |   `val supportedAgents: List<AgentType>` |

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
| 21 | `class QuickMessagesViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 22 |   `private val repository: ServerRepository` |
| 25 |   `private val _ui` |
| 26 |   `val ui: StateFlow<QuickMessagesUiState>` |
| 28 |   `private var client: CodegClient?` |
| 32 |   `fun load()` |
| 49 |   `fun save(existing: QuickMessage?, title: String, content: String)` |
| 61 |   `fun delete(message: QuickMessage)` |
| 75 | `data class QuickMessagesUiState( val messages: List<QuickMessage> = emptyList(), val loading: Boolean = false, val error: String? = null, )` |
| 76 |   `val messages: List<QuickMessage>` |
| 77 |   `val loading: Boolean` |
| 78 |   `val error: String?` |

## feature/settings/SettingsScreen.kt

| Line | Declaration |
|---|---|
| 75 | `private enum class SettingsLeaf(@StringRes val titleRes: Int, val icon: ImageVector, @StringRes val groupRes: Int, val implemented: Boolean)` |
| 94 | `@Composable fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel())` |
| 95 |   `var leaf by remember` |
| 97 |   `var agentDetail by remember` |
| 98 |   `val inAgentDetail` |
| 146 | `@Composable private fun SettingsRoot(onSelect: (SettingsLeaf) -> Unit)` |
| 147 |   `val colors` |
| 184 | `@Composable private fun AppearanceSettings(viewModel: SettingsViewModel)` |
| 185 |   `val colors` |
| 186 |   `val settings by viewModel.settings.collectAsStateWithLifecycle()` |
| 234 | `@Composable private fun AboutScreen(viewModel: SettingsViewModel)` |
| 235 |   `val colors` |
| 236 |   `val about by viewModel.about.collectAsStateWithLifecycle()` |
| 237 |   `val context` |
| 238 |   `val appVersion` |
| 254 | `@Composable private fun ComingSoon(@StringRes title: Int)` |
| 255 |   `val colors` |

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
| 28 | `class SkillsViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 29 |   `private val repository: ServerRepository` |
| 32 |   `private val _ui` |
| 33 |   `val ui: StateFlow<SkillsUiState>` |
| 35 |   `private var client: CodegClient?` |
| 36 |   `private var loadToken` |
| 40 |   `fun load()` |
| 60 |   `fun select(agent: AgentType)` |
| 66 |   `fun reloadCurrent()` |
| 70 |   `private fun loadSkills(agent: AgentType)` |
| 87 |   `suspend fun content(skill: AgentSkillItem, agent: AgentType): String` |
| 93 |   `suspend fun save(skillId: String, scope: String, content: String, layout: String?, agent: AgentType)` |
| 99 |   `fun delete(skill: AgentSkillItem, agent: AgentType)` |
| 112 |   `fun dismissRefreshError()` |
| 115 | `enum class SkillsPhase` |
| 117 | `data class SkillsUiState( val phase: SkillsPhase = SkillsPhase.LOADING, val agents: List<AgentType> = emptyList(), val selectedAgent: AgentType? = null, val result: AgentSkillsListResult? = null, /** The agent [result] actually belongs to (mutations target THIS, not [selectedAgent]). */ val resultAgent: AgentType? = null, val skillsLoading: Boolean = false, val refreshError: String? = null, val error: String? = null, )` |
| 118 |   `val phase: SkillsPhase` |
| 119 |   `val agents: List<AgentType>` |
| 120 |   `val selectedAgent: AgentType?` |
| 121 |   `val result: AgentSkillsListResult?` |
| 123 |   `val resultAgent: AgentType?` |
| 124 |   `val skillsLoading: Boolean` |
| 125 |   `val refreshError: String?` |
| 126 |   `val error: String?` |
| 129 |   `val grouped: List<Pair<String, List<AgentSkillItem>>>` |

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
| 20 | `class SystemViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 21 |   `private val repository: ServerRepository` |
| 24 |   `private val _ui` |
| 25 |   `val ui: StateFlow<SystemUiState>` |
| 27 |   `private var client: CodegClient?` |
| 31 |   `fun load()` |
| 46 |   `fun setProxyEnabled(enabled: Boolean)` |
| 47 |   `fun onProxyUrl(url: String)` |
| 49 |   `fun saveProxy()` |
| 63 |   `fun checkUpdate()` |
| 76 | `data class SystemUiState( val loading: Boolean = false, val proxyEnabled: Boolean = false, val proxyUrl: String = "", val savingProxy: Boolean = false, val checking: Boolean = false, val updateResult: AppUpdateCheckResult? = null, val error: String? = null, )` |
| 77 |   `val loading: Boolean` |
| 78 |   `val proxyEnabled: Boolean` |
| 79 |   `val proxyUrl: String` |
| 80 |   `val savingProxy: Boolean` |
| 81 |   `val checking: Boolean` |
| 82 |   `val updateResult: AppUpdateCheckResult?` |
| 83 |   `val error: String?` |

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
| 23 | `class VersionControlViewModel @Inject constructor( private val repository: ServerRepository, ) : ViewModel()` |
| 24 |   `private val repository: ServerRepository` |
| 27 |   `private val _ui` |
| 28 |   `val ui: StateFlow<VersionControlUiState>` |
| 30 |   `private var client: CodegClient?` |
| 34 |   `fun load()` |
| 54 |   `fun onPath(path: String)` |
| 56 |   `fun test()` |
| 67 |   `fun save()` |
| 82 | `data class VersionControlUiState( val loading: Boolean = false, val detect: GitDetectResult = GitDetectResult(), val customPath: String = "", val accounts: List<GitHubAccount> = emptyList(), val testing: Boolean = false, val testResult: GitDetectResult? = null, val saving: Boolean = false, val error: String? = null, )` |
| 83 |   `val loading: Boolean` |
| 84 |   `val detect: GitDetectResult` |
| 85 |   `val customPath: String` |
| 86 |   `val accounts: List<GitHubAccount>` |
| 87 |   `val testing: Boolean` |
| 88 |   `val testResult: GitDetectResult?` |
| 89 |   `val saving: Boolean` |
| 90 |   `val error: String?` |

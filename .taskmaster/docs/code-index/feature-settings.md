# feature/settings

Source root: `app/src/main/java/app/codeg/android/`

## feature/settings/AgentConfigKimiView.kt

| Line | Declaration |
|---|---|
| 68 | `private val WarnAmber` |
| 69 | `private val SuccessGreen` |
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

## feature/settings/AgentConfigPiView.kt

| Line | Declaration |
|---|---|
| 61 | `private val SuccessGreen` |
| 63 | `private data class PiBanner(val text: String, val isError: Boolean)` |
| 75 | `@Composable fun AgentConfigPiView(agent: AcpAgentInfo, viewModel: AgentsViewModel)` |
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
| 90 |   `var piOp by remember` |
| 91 |   `var savingRuntime by remember` |
| 94 |   `var selectedProvider by remember` |
| 95 |   `var customId by remember` |
| 96 |   `var customBaseUrl by remember` |
| 97 |   `var customApi by remember` |
| 98 |   `var customProviders by remember` |
| 99 |   `var modelText by remember` |
| 100 |   `var thinkingLevel by remember` |
| 101 |   `var apiKey by remember` |
| 102 |   `var authProviders by remember` |
| 103 |   `var savingCreds by remember` |
| 104 |   `var loadingCreds by remember` |
| 107 |   `var trustWorkspace by remember` |
| 108 |   `var savingTrust by remember` |
| 110 |   `var banner by remember` |
| 112 |   `val isCustomProvider` |
| 113 |   `val effectiveProvider` |
| 114 |   `val providerHasKey` |
| 115 |   `val customIncomplete` |
| 116 |   `val credsIncomplete` |
| 119 |   `val providerOptions` |
| 129 |   `val runtimeSaved` |
| 130 |   `val configSaved` |
| 131 |   `val providerModelRequired` |
| 132 |   `val baseUrlRequired` |
| 133 |   `val installedMsg` |
| 134 |   `val uninstalledMsg` |
| 135 |   `val installFailed` |
| 136 |   `val uninstallFailed` |
| 137 |   `val trustFailed` |
| 168 |   `fun detectPi()` |
| 176 |   `fun handleValidate()` |
| 187 |   `fun handleSaveRuntime()` |
| 203 |   `fun handleSaveCreds()` |
| 243 |   `fun handlePiOp(install: Boolean)` |
| 259 |   `fun toggleTrust(next: Boolean)` |
| 417 | `@Composable private fun PiActionButton(text: String, busy: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 418 |   `val colors` |
| 426 | `@Composable private fun PiResultBanner(banner: PiBanner)` |
| 427 |   `val colors` |
| 428 |   `val tint` |
| 439 | `@Composable private fun piThinkingLabel(level: String): String` |

## feature/settings/AgentDetailScreen.kt

| Line | Declaration |
|---|---|
| 99 | `@Composable fun AgentDetailContent( agentType: AgentType, onClose: () -> Unit, viewModel: AgentsViewModel = hiltViewModel(), )` |
| 104 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 105 |   `val detail` |
| 118 |   `val selfContained` |
| 167 | `@Composable private fun AgentHeader(state: AgentDetailState, onToggle: (Boolean) -> Unit)` |
| 168 |   `val colors` |
| 169 |   `val agent` |
| 207 | `@Composable private fun VersionLine(state: AgentDetailState)` |
| 208 |   `val colors` |
| 209 |   `val installed` |
| 210 |   `val latest` |
| 211 |   `val text` |
| 221 | `@Composable private fun SaveBar(canSave: Boolean, saving: Boolean, error: String?, onSave: () -> Unit)` |
| 222 |   `val colors` |
| 237 | `@Composable private fun ClaudeSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 238 |   `val d` |
| 283 | `private fun claudeFooterRes(mode: ClaudeAuthMode): Int` |
| 290 | `@Composable private fun CodexSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 291 |   `val colors` |
| 292 |   `val context` |
| 293 |   `val d` |
| 331 | `private fun codexEffortLabelRes(e: CodexReasoningEffort): Int` |
| 339 | `@Composable private fun GeminiSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 340 |   `val colors` |
| 341 |   `val d` |
| 391 | `@Composable private fun OpenClawSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 392 |   `val d` |
| 401 | `@Composable private fun ClineSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 402 |   `val d` |
| 417 | `@Composable private fun OpenCodeSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 418 |   `val d` |
| 426 | `@Composable private fun HermesSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 427 |   `val d` |
| 428 |   `val option` |
| 442 | `private fun hermesFooterRes(kind: HermesProviderKind?): Int` |
| 456 | `@Composable private fun CodeBuddySection(state: AgentDetailState, vm: AgentsViewModel)` |
| 457 |   `val colors` |
| 458 |   `val d` |
| 459 |   `val selfHosted` |
| 503 | `@Composable private fun ServerManagedSection()` |
| 513 | `@Composable private fun GrokSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 514 |   `val colors` |
| 515 |   `val d` |
| 516 |   `val useDefault` |
| 570 | `@Composable private fun CursorSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 571 |   `val colors` |
| 572 |   `val d` |
| 573 |   `val custom` |
| 575 |   `var auth by remember` |
| 576 |   `var authLoading by remember` |
| 577 |   `var models by remember` |
| 578 |   `var modelsError by remember` |
| 579 |   `var modelsLoading by remember` |
| 582 |   `val probeKey` |
| 583 |   `var refreshTick by remember` |
| 664 |   `val savedModel` |
| 726 | `@Composable private fun CursorStatusRow(auth: CursorAuthStatus?, loading: Boolean, onRefresh: () -> Unit)` |
| 727 |   `val colors` |
| 728 |   `val installed` |
| 729 |   `val signedIn` |
| 730 |   `val tint` |
| 735 |   `val text` |
| 776 | `@Composable private fun RuleEditor( label: String, rules: List<String>, placeholder: String, tone: Color, onChange: (List<String>) -> Unit, )` |
| 783 |   `val colors` |
| 817 | `private val CursorActionableAmber` |
| 824 | `@Composable private fun ColumnScope.ProviderPicker(state: AgentDetailState, vm: AgentsViewModel)` |
| 825 |   `val colors` |
| 843 | `@Composable private fun ProviderSummary(p: ModelProviderInfo)` |
| 852 | `@Composable private fun ReadonlyRow(label: String, value: String)` |
| 853 |   `val colors` |
| 865 | `@Composable internal fun FormSection(title: String, footer: String? = null, content: @Composable ColumnScope.() -> Unit)` |
| 866 |   `val colors` |
| 880 | `@Composable internal fun <T> SelectField(label: String, value: T, options: List<Pair<T, String>>, onSelect: (T) -> Unit)` |
| 881 |   `val colors` |
| 882 |   `var expanded by remember` |
| 883 |   `val display` |
| 904 | `@Composable private fun HermesProviderField(value: String, onSelect: (String) -> Unit)` |
| 905 |   `val colors` |
| 906 |   `var expanded by remember` |
| 907 |   `val display` |
| 936 | `private fun kindLabelRes(kind: HermesProviderKind): Int` |
| 943 | `@Composable private fun ToggleRow(title: String, checked: Boolean, onChange: (Boolean) -> Unit)` |
| 944 |   `val colors` |
| 960 | `@Composable internal fun ColumnScope.ExpandableGroup(title: String, content: @Composable ColumnScope.() -> Unit)` |
| 961 |   `val colors` |
| 962 |   `var open by remember` |
| 963 |   `val rotation by animateFloatAsState(if (open) 180f else 0f, label = "chevron")` |
| 978 | `@Composable private fun TonalAction(text: String, onClick: () -> Unit)` |
| 979 |   `val colors` |
| 990 | `@Composable private fun AdvancedSection(state: AgentDetailState, vm: AgentsViewModel)` |
| 991 |   `val colors` |
| 992 |   `var open by remember` |
| 993 |   `val rotation by animateFloatAsState(if (open) 180f else 0f, label = "advanced")` |
| 994 |   `val label` |
| 999 |   `val text` |
| 1043 | `@Composable private fun dropdownColors(colors: CodegColors)` |

## feature/settings/AgentsScreen.kt

| Line | Declaration |
|---|---|
| 41 | `@Composable fun AgentsContent(onOpen: (AgentType) -> Unit, viewModel: AgentsViewModel = hiltViewModel())` |
| 42 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 58 | `@Composable private fun AgentRow(agent: AcpAgentInfo, onToggle: (Boolean) -> Unit, onOpen: () -> Unit)` |
| 59 |   `val colors` |

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
| 108 | `internal fun channelTint(type: ChannelType): Color` |
| 114 | `internal fun channelIcon(type: ChannelType): ImageVector` |
| 121 | `@Composable internal fun statusTint(status: ChannelConnectionStatus): Color` |
| 129 | `@Composable internal fun ChannelTypeAvatar(type: ChannelType, size: Dp)` |
| 130 |   `val tint` |
| 137 | `@Composable internal fun ChannelStatusPill(status: ChannelConnectionStatus)` |
| 138 |   `val tint` |
| 147 | `private sealed interface ChannelEditorTarget` |
| 148 |   `data object Add : ChannelEditorTarget` |
| 149 |   `data class Edit(val channel: ChatChannelInfo) : ChannelEditorTarget` |
| 154 | `@Composable fun ChatChannelsContent(viewModel: ChatChannelsViewModel = hiltViewModel())` |
| 155 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 156 |   `val colors` |
| 157 |   `var editor by remember` |
| 158 |   `var showGlobal by remember` |
| 159 |   `var pendingDelete by remember` |
| 276 | `@Composable private fun ChannelRow( channel: ChatChannelInfo, status: ChannelConnectionStatus, toggling: Boolean, onOpen: () -> Unit, onToggle: (Boolean) -> Unit, onDelete: () -> Unit, )` |
| 284 |   `val colors` |
| 285 |   `val summary` |
| 312 | `@Composable private fun ChatRefreshBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 313 |   `val colors` |
| 328 | `@Composable private fun ChatChannelDetailDialog( channel: ChatChannelInfo, detail: ChannelDetailState, viewModel: ChatChannelsViewModel, onEdit: () -> Unit, onDismiss: () -> Unit, )` |
| 335 |   `val colors` |
| 336 |   `var showQR by remember` |
| 337 |   `var confirmRemoveToken by remember` |
| 464 | `@Composable private fun MessageLogRow(message: ChatChannelMessageLog)` |
| 465 |   `val colors` |
| 476 | `@Composable private fun ActionButton(title: String, icon: ImageVector, tint: Color, enabled: Boolean, onClick: () -> Unit)` |
| 494 | `@Composable private fun DetailSection(title: String, content: @Composable () -> Unit)` |
| 495 |   `val colors` |
| 503 | `@Composable private fun SettingRow(content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit)` |
| 508 | `@Composable private fun Divider()` |
| 516 | `private enum class QrPhase` |
| 520 | `@Composable private fun WeixinQRDialog(channelId: Int, viewModel: ChatChannelsViewModel, onConnected: () -> Unit, onDismiss: () -> Unit)` |
| 521 |   `val colors` |
| 522 |   `val loadingQrText` |
| 523 |   `val couldntLoadQrText` |
| 524 |   `val scanWithWechatText` |
| 525 |   `val connectedText` |
| 526 |   `val qrExpiredText` |
| 527 |   `val scannedConfirmText` |
| 528 |   `var attempt by remember` |
| 529 |   `var phase by remember` |
| 530 |   `var statusText by remember` |
| 531 |   `var image by remember` |
| 591 | `private fun decodeQrImage(raw: String): androidx.compose.ui.graphics.ImageBitmap?` |
| 592 |   `var b64` |
| 593 |   `val comma` |
| 605 | `private val lenientJson` |
| 607 | `private fun normalizedObjectJson(raw: String): String?` |
| 608 |   `val obj` |
| 614 | `@Composable private fun ChatChannelEditorDialog(existing: ChatChannelInfo?, viewModel: ChatChannelsViewModel, onDismiss: () -> Unit)` |
| 615 |   `val colors` |
| 616 |   `val scope` |
| 617 |   `val isEdit` |
| 619 |   `var name by remember` |
| 620 |   `var type by remember` |
| 621 |   `val parsed` |
| 622 |   `var chatId by remember` |
| 623 |   `var appId by remember` |
| 624 |   `var baseUrl by remember` |
| 625 |   `var rawMode by remember` |
| 626 |   `var rawText by remember` |
| 627 |   `var token by remember` |
| 628 |   `var hasToken by remember` |
| 629 |   `var enabled by remember` |
| 630 |   `var dailyEnabled by remember` |
| 631 |   `var dailyTime by remember` |
| 632 |   `var saving by remember` |
| 633 |   `var error by remember` |
| 634 |   `val couldntSaveText` |
| 640 |   `fun currentConfig()` |
| 641 |   `val configJson: String?` |
| 642 |   `val configValid` |
| 647 |   `val tokenRequired` |
| 648 |   `val canSave` |
| 650 |   `fun save()` |
| 741 | `private fun buildUpdateBody(existing: ChatChannelInfo, name: String, enabled: Boolean, configJson: String, dailyEnabled: Boolean, timeString: String?): UpdateChatChannelBody` |
| 742 |   `val configChanged` |
| 743 |   `val timeEdit: FieldEdit` |

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
| 71 | `@Composable fun ChatGlobalSettingsDialog(onDismiss: () -> Unit, viewModel: ChatGlobalSettingsViewModel = hiltViewModel())` |
| 72 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 73 |   `val colors` |
| 74 |   `var newWebhook by remember` |
| 75 |   `var prefixField by remember(ui.commandPrefix)` |
| 166 | `@Composable private fun GlobalSection(title: String, subtitle: String, content: @Composable () -> Unit)` |
| 167 |   `val colors` |

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
| 70 | `@Composable fun ExpertsContent(viewModel: ExpertsViewModel = hiltViewModel())` |
| 71 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 72 |   `val colors` |
| 118 | `@Composable private fun ExpertRow(expert: ExpertListItem, onClick: () -> Unit)` |
| 119 |   `val colors` |
| 137 | `@Composable private fun ExpertIconTile(size: androidx.compose.ui.unit.Dp)` |
| 138 |   `val colors` |
| 148 | `@Composable private fun ExpertsRefreshBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 149 |   `val colors` |
| 162 | `@Composable private fun ExpertDetailDialog( detail: DetailState, agents: List<AgentType>, onToggle: (AgentType, Boolean) -> Unit, onDismiss: () -> Unit, onDismissError: () -> Unit, )` |
| 169 |   `val colors` |
| 170 |   `val expert` |
| 240 | `@Composable private fun AgentLinkRow(agent: AgentType, status: ExpertInstallStatus?, toggling: Boolean, onToggle: (Boolean) -> Unit)` |
| 241 |   `val colors` |
| 242 |   `val state` |
| 243 |   `val isLinked` |
| 244 |   `val blocked` |
| 245 |   `val caption` |
| 267 | `@Composable private fun linkCaption(state: ExpertLinkState?, copyMode: Boolean): Pair<String, Color>?` |
| 268 |   `val colors` |
| 279 | `private fun stripFrontmatter(content: String): String` |
| 280 |   `val lines` |
| 282 |   `val end` |

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
| 45 | `@Composable fun GeneralContent(viewModel: GeneralViewModel = hiltViewModel())` |
| 46 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 47 |   `val colors` |
| 75 | `@Composable private fun Section(title: String, content: @Composable () -> Unit)` |
| 76 |   `val colors` |
| 84 | `@Composable private fun ToggleRow(title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit)` |
| 85 |   `val colors` |
| 106 | `@Composable private fun Stepper(value: Int, onDec: () -> Unit, onInc: () -> Unit)` |
| 107 |   `val colors` |
| 116 | `@Composable private fun StepButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit)` |
| 117 |   `val colors` |

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
| 54 | `@Composable fun LanguageContent()` |
| 55 |   `val context` |
| 56 |   `val colors` |
| 57 |   `var selected by remember` |
| 103 | `private tailrec fun Context.findActivity(): Activity?` |

## feature/settings/McpScreen.kt

| Line | Declaration |
|---|---|
| 68 | `private const val SPEC_TEMPLATE` |
| 72 | `@Composable fun McpContent(viewModel: McpViewModel = hiltViewModel())` |
| 73 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 74 |   `val colors` |
| 75 |   `var editor by remember` |
| 106 | `private data class McpEditorTarget(val server: LocalMcpServer?)` |
| 110 | `@Composable private fun McpRow(s: LocalMcpServer, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 111 |   `val colors` |
| 112 |   `var menu by remember` |
| 131 | `@Composable private fun McpEditor( existing: LocalMcpServer?, onDismiss: () -> Unit, onSave: (String, String, List<String>, (String) -> Unit) -> Unit, )` |
| 136 |   `val colors` |
| 137 |   `var id by remember` |
| 138 |   `var spec by remember` |
| 139 |   `val apps: SnapshotStateList<String>` |
| 140 |   `var error by remember` |

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
| 59 | `@Composable fun ModelProvidersContent(viewModel: ModelProvidersViewModel = hiltViewModel())` |
| 60 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 61 |   `val colors` |
| 62 |   `var editor by remember` |
| 102 | `private data class ProviderEditorTarget(val provider: ModelProviderInfo?)` |
| 106 | `@Composable private fun ProviderRow(p: ModelProviderInfo, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 107 |   `val colors` |
| 108 |   `var menu by remember` |
| 127 | `@Composable private fun ProviderEditor( existing: ModelProviderInfo?, supportedAgents: List<AgentType>, onDismiss: () -> Unit, onSave: (String, String, String, AgentType, String?) -> Unit, )` |
| 133 |   `val colors` |
| 134 |   `var name by remember` |
| 135 |   `var url by remember` |
| 136 |   `var key by remember` |
| 137 |   `var agent by remember` |
| 138 |   `var model by remember` |
| 139 |   `var agentMenu by remember` |

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
| 49 | `@Composable fun QuickMessagesContent(viewModel: QuickMessagesViewModel = hiltViewModel())` |
| 50 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 51 |   `val colors` |
| 52 |   `var editor by remember` |
| 86 | `private data class EditorTarget(val message: QuickMessage?)` |
| 90 | `@Composable private fun MessageRow(msg: QuickMessage, onEdit: () -> Unit, onDelete: () -> Unit)` |
| 91 |   `val colors` |
| 92 |   `var menu by remember` |
| 109 | `@Composable private fun QuickMessageEditor(existing: QuickMessage?, onDismiss: () -> Unit, onSave: (String, String) -> Unit)` |
| 110 |   `var title by remember` |
| 111 |   `var content by remember` |

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
| 77 | `private enum class SettingsLeaf(@StringRes val titleRes: Int, val icon: ImageVector, @StringRes val groupRes: Int, val implemented: Boolean)` |
| 96 | `@Composable fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel())` |
| 97 |   `val colors` |
| 98 |   `var leaf by remember` |
| 100 |   `var agentDetail by remember` |
| 101 |   `val inAgentDetail` |
| 152 | `@Composable private fun SettingsRoot(onSelect: (SettingsLeaf) -> Unit)` |
| 153 |   `val colors` |
| 190 | `@Composable private fun AppearanceSettings(viewModel: SettingsViewModel)` |
| 191 |   `val colors` |
| 192 |   `val settings by viewModel.settings.collectAsStateWithLifecycle()` |
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
| 81 | `private sealed interface SkillEditTarget` |
| 82 |   `data object Add : SkillEditTarget` |
| 83 |   `data class Edit(val skill: AgentSkillItem) : SkillEditTarget` |
| 88 | `@Composable fun SkillsContent(viewModel: SkillsViewModel = hiltViewModel())` |
| 89 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 90 |   `val colors` |
| 91 |   `var editor by remember` |
| 92 |   `var pendingDelete by remember` |
| 161 | `@Composable private fun AgentFilterBar(agents: List<AgentType>, selected: AgentType?, onSelect: (AgentType) -> Unit)` |
| 162 |   `val colors` |
| 192 | `@Composable private fun SkillsArea( ui: SkillsUiState, onAdd: () -> Unit, onOpen: (AgentSkillItem) -> Unit, onDelete: (AgentSkillItem) -> Unit, onRetry: () -> Unit, onDismissError: () -> Unit, )` |
| 200 |   `val colors` |
| 201 |   `val result` |
| 243 | `@Composable private fun SkillRow(skill: AgentSkillItem, onClick: () -> Unit, onDelete: () -> Unit)` |
| 244 |   `val colors` |
| 245 |   `val isDir` |
| 271 | `@Composable private fun RefreshErrorBanner(message: String, onRetry: () -> Unit, onDismiss: () -> Unit)` |
| 272 |   `val colors` |
| 285 | `@Composable private fun SkillEditorDialog( target: SkillEditTarget, agent: AgentType, loadContent: suspend (AgentSkillItem) -> String, onSave: suspend (id: String, scope: String, content: String, layout: String?) -> Unit, onDismiss: () -> Unit, )` |
| 292 |   `val colors` |
| 293 |   `val scope` |
| 294 |   `val existing` |
| 295 |   `val isReadOnly` |
| 296 |   `val skillScope` |
| 297 |   `val layout` |
| 299 |   `var skillId by remember` |
| 300 |   `var content by remember` |
| 301 |   `var loadingContent by remember` |
| 302 |   `var saving by remember` |
| 303 |   `var error by remember` |
| 314 |   `val canSave` |

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
| 37 | `@Composable fun SystemContent(viewModel: SystemViewModel = hiltViewModel())` |
| 38 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 39 |   `val colors` |
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
| 37 | `private val Ok` |
| 40 | `@Composable fun VersionControlContent(viewModel: VersionControlViewModel = hiltViewModel())` |
| 41 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 42 |   `val colors` |
| 91 | `@Composable private fun Pill(label: String, loading: Boolean, onClick: () -> Unit, filled: Boolean)` |
| 92 |   `val colors` |

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

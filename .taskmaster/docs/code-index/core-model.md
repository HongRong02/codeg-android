# core/model

Source root: `app/src/main/java/app/codeg/android/`

## core/model/AcpEvent.kt

| Line | Declaration |
|---|---|
| 18 | `enum class ConnectionStatus(val wire: String)` |
| 25 |   `companion object` |
| 26 |     `fun fromWire(raw: String?): ConnectionStatus` |
| 33 | `sealed interface UserMessageBlock` |
| 34 |   `data class Text(val text: String) : UserMessageBlock` |
| 35 |   `data class Image(val image: ImageData) : UserMessageBlock` |
| 36 |   `data object Unknown : UserMessageBlock` |
| 38 |   `companion object` |
| 39 |     `fun fromWire(obj: JsonObject): UserMessageBlock` |
| 60 | `sealed interface AcpEvent` |
| 61 |   `data class ContentDelta(val text: String) : AcpEvent` |
| 62 |   `data class Thinking(val text: String) : AcpEvent` |
| 65 |   `data class ToolCall( val id: String, val title: String, val kind: String, val status: String, val content: String?, val rawInput: String?, val rawOutput: String?, val meta: JsonObject? = null, ) : AcpEvent` |
| 77 |   `data class ToolCallUpdate( val id: String, val title: String?, val status: String?, val content: String?, val rawInput: String?, val rawOutput: String?, val append: Boolean, val meta: JsonObject? = null, ) : AcpEvent` |
| 87 |   `data class TurnComplete(val stopReason: String) : AcpEvent` |
| 88 |   `data class SessionStarted(val sessionId: String) : AcpEvent` |
| 89 |   `data class ConversationLinked(val conversationId: Int, val folderId: Int) : AcpEvent` |
| 90 |   `data class ConversationStatusChanged(val conversationId: Int, val status: ConversationStatus) : AcpEvent` |
| 91 |   `data class StatusChanged(val status: ConnectionStatus) : AcpEvent` |
| 92 |   `data class UsageUpdate(val used: Long, val size: Long) : AcpEvent` |
| 93 |   `data class UserMessage(val messageId: String, val blocks: List<UserMessageBlock>) : AcpEvent` |
| 94 |   `data class UserPromptSent(val textPreview: String) : AcpEvent` |
| 95 |   `data class Error(val message: String, val code: String?) : AcpEvent` |
| 98 |   `data class PermissionRequest( val requestId: String, val toolCall: JsonElement, val options: List<PermissionOption>, ) : AcpEvent` |
| 103 |   `data class PermissionResolved(val requestId: String) : AcpEvent` |
| 106 |   `data class QuestionRequest(val questionId: String, val questions: List<QuestionSpec>) : AcpEvent` |
| 107 |   `data class QuestionResolved(val questionId: String) : AcpEvent` |
| 114 |   `data class PlanApprovalRequest( val approvalId: String, val toolCallId: String, val planMarkdown: String, ) : AcpEvent` |
| 121 |   `data class PlanApprovalResolved(val approvalId: String) : AcpEvent` |
| 123 |   `data class PlanUpdate(val entries: List<PlanEntry>) : AcpEvent` |
| 124 |   `data class Unknown(val type: String) : AcpEvent` |
| 126 |   `companion object` |
| 132 |     `fun fromWire(obj: JsonObject, json: Json): AcpEvent` |
| 208 |     `private fun JsonObject.intOrZero(key: String): Int` |
| 212 |     `private fun <T> decodeList(json: Json, element: JsonElement?, serializer: KSerializer<T>): List<T>` |
| 225 | `data class EventEnvelope( val seq: Long, val connectionId: String, val event: AcpEvent, )` |
| 226 |   `val seq: Long` |
| 227 |   `val connectionId: String` |
| 228 |   `val event: AcpEvent` |
| 230 |   `companion object` |
| 231 |     `fun fromWire(obj: JsonObject, json: Json): EventEnvelope` |
| 249 | `data class LiveSessionSnapshot( val connectionId: String?, val conversationId: Int?, val folderId: Int?, val status: ConnectionStatus?, val externalId: String?, val eventSeq: Long?, val liveMessage: LiveMessageSnapshot?, val activeToolCalls: List<ToolCallStateSnapshot>, val pendingPermission: PendingPermissionSnapshot?, val pendingQuestion: PendingQuestionSnapshot?, val pendingPlanApproval: PendingPlanApprovalSnapshot?, )` |
| 250 |   `val connectionId: String?` |
| 251 |   `val conversationId: Int?` |
| 252 |   `val folderId: Int?` |
| 253 |   `val status: ConnectionStatus?` |
| 254 |   `val externalId: String?` |
| 255 |   `val eventSeq: Long?` |
| 256 |   `val liveMessage: LiveMessageSnapshot?` |
| 257 |   `val activeToolCalls: List<ToolCallStateSnapshot>` |
| 258 |   `val pendingPermission: PendingPermissionSnapshot?` |
| 259 |   `val pendingQuestion: PendingQuestionSnapshot?` |
| 260 |   `val pendingPlanApproval: PendingPlanApprovalSnapshot?` |
| 262 |   `companion object` |
| 263 |     `fun fromWire(obj: JsonObject, json: Json): LiveSessionSnapshot` |
| 284 |     `private fun JsonObject.intOrNull(key: String): Int?` |
| 291 | `data class LiveMessageSnapshot(val content: List<LiveContentBlockSnapshot>)` |
| 292 |   `companion object` |
| 293 |     `fun fromWire(obj: JsonObject, json: Json): LiveMessageSnapshot` |
| 304 | `sealed interface LiveContentBlockSnapshot` |
| 305 |   `data class Text(val text: String) : LiveContentBlockSnapshot` |
| 306 |   `data class Thinking(val text: String) : LiveContentBlockSnapshot` |
| 307 |   `data class ToolCallRef(val toolCallId: String) : LiveContentBlockSnapshot` |
| 308 |   `data class Plan(val entries: List<PlanEntry>) : LiveContentBlockSnapshot` |
| 309 |   `data object Unknown : LiveContentBlockSnapshot` |
| 311 |   `companion object` |
| 312 |     `fun fromWire(obj: JsonObject, json: Json): LiveContentBlockSnapshot` |
| 331 | `data class ToolCallStateSnapshot( val id: String, val kind: String, val label: String, val status: String, val input: JsonElement?, val output: JsonElement?, val content: String?, /** The agent's opaque ACP metadata, as on the live event path. */ val meta: JsonObject? = null, )` |
| 332 |   `val id: String` |
| 333 |   `val kind: String` |
| 334 |   `val label: String` |
| 335 |   `val status: String` |
| 336 |   `val input: JsonElement?` |
| 337 |   `val output: JsonElement?` |
| 338 |   `val content: String?` |
| 340 |   `val meta: JsonObject?` |
| 344 |   `val inputPreview: String?` |
| 352 |   `val outputText: String` |
| 366 |   `val normalizedStatus: String` |
| 375 |   `companion object` |
| 376 |     `fun fromWire(obj: JsonObject): ToolCallStateSnapshot` |
| 391 | `data class PendingPermissionSnapshot( val requestId: String, val toolCall: JsonElement, val options: List<PermissionOption>, )` |
| 392 |   `val requestId: String` |
| 393 |   `val toolCall: JsonElement` |
| 394 |   `val options: List<PermissionOption>` |
| 396 |   `companion object` |
| 397 |     `fun fromWire(obj: JsonObject, json: Json): PendingPermissionSnapshot` |
| 415 | `data class PendingPlanApprovalSnapshot( val approvalId: String, val toolCallId: String, val planMarkdown: String, )` |
| 416 |   `val approvalId: String` |
| 417 |   `val toolCallId: String` |
| 418 |   `val planMarkdown: String` |
| 420 |   `companion object` |
| 421 |     `fun fromWire(obj: JsonObject): PendingPlanApprovalSnapshot` |
| 431 | `data class PendingQuestionSnapshot( val questionId: String, val questions: List<QuestionSpec>, )` |
| 432 |   `val questionId: String` |
| 433 |   `val questions: List<QuestionSpec>` |
| 435 |   `companion object` |
| 436 |     `fun fromWire(obj: JsonObject, json: Json): PendingQuestionSnapshot` |

## core/model/AgentConfig.kt

| Line | Declaration |
|---|---|
| 29 | `enum class ClaudeAuthMode(val wire: String)` |
| 35 | `enum class CodexAuthMode(val wire: String)` |
| 41 | `enum class GeminiAuthMode(val wire: String)` |
| 52 | `enum class ClaudeEffortLevel(val wire: String)` |
| 59 |   `companion object` |
| 60 |     `fun fromWire(raw: String?): ClaudeEffortLevel` |
| 68 | `enum class CodexReasoningEffort(val wire: String)` |
| 74 |   `companion object` |
| 76 |     `fun fromWire(raw: String?): CodexReasoningEffort` |
| 88 | `enum class CodeBuddyEnvironment(val wire: String)` |
| 94 |   `companion object` |
| 95 |     `fun fromWire(raw: String?): CodeBuddyEnvironment?` |
| 104 | `object AgentEnvKeys` |
| 105 |   `const val CLAUDE_MAIN_MODEL` |
| 106 |   `const val CLAUDE_REASONING_MODEL` |
| 107 |   `const val CLAUDE_DEFAULT_HAIKU_MODEL` |
| 108 |   `const val CLAUDE_DEFAULT_SONNET_MODEL` |
| 109 |   `const val CLAUDE_DEFAULT_OPUS_MODEL` |
| 110 |   `const val CLAUDE_EFFORT_CONFIG_KEY` |
| 112 |   `object Gemini` |
| 125 |   `object OpenClaw` |
| 131 |   `object CodeBuddy` |
| 137 |   `object Grok` |
| 143 |   `data class ImportantKeys(val apiBaseUrl: List<String>, val apiKey: List<String>, val model: List<String>)` |
| 149 |   `fun important(agent: AgentType): ImportantKeys` |
| 185 | `val clineProviders: List<Pair<String, String>>` |
| 197 | `data class CodexEffortOption(val value: CodexReasoningEffort, val label: String, val description: String)` |
| 199 | `val codexReasoningEffortOptions: List<CodexEffortOption>` |
| 206 | `const val CODEX_DEFAULT_MODEL_PROVIDER` |
| 212 | `val openCodeNpmOptions: List<String>` |
| 222 | `object JsonConfig` |
| 223 |   `private val parser` |
| 226 |   `private val pretty` |
| 228 |   `data class Parsed(val config: Map<String, JsonElement>, val error: String?)` |
| 234 |   `fun parse(text: String): Parsed` |
| 252 |   `fun serialize(obj: Map<String, JsonElement>): String` |
| 257 |   `private fun sortKeys(el: JsonElement): JsonElement` |
| 268 |   `fun markRemovedKeysNull( original: Map<String, JsonElement>, current: Map<String, JsonElement>, ): Map<String, JsonElement>` |
| 285 |   `fun normalize(text: String): String` |
| 293 |   `fun envFromConfig(config: Map<String, JsonElement>): Map<String, String>` |
| 306 |   `fun pickFirstString(source: Map<String, JsonElement>, keys: List<String>): String?` |
| 316 | `internal fun JsonElement.asStringOrNull(): String?` |
| 323 | `object EnvText` |
| 324 |   `fun toText(env: Map<String, String>): String` |
| 327 |   `fun parse(envText: String): Map<String, String>` |
| 343 |   `fun patch(envText: String, patch: Map<String, String?>): String` |
| 352 |   `fun find(env: Map<String, String>, keys: List<String>): String` |
| 365 | `data class AgentDraft( val enabled: Boolean = true, /** Raw flat env (`acp_update_agent_env`) — kept in lockstep with structured fields. */ val envText: String = "", /** Raw config.json (`acp_update_agent_config`) — source of truth for structured fields. */ val configText: String = "", val modelProviderId: Int? = null, val apiBaseUrl: String = "", val apiKey: String = "", val model: String = "", val claudeAuthMode: ClaudeAuthMode = ClaudeAuthMode.OFFICIAL_SUBSCRIPTION, val claudeMainModel: String = "", val claudeReasoningModel: String = "", val claudeDefaultHaikuModel: String = "", val claudeDefaultSonnetModel: String = "", val claudeDefaultOpusModel: String = "", val claudeEffortLevel: ClaudeEffortLevel = ClaudeEffortLevel.DEFAULT, val codexAuthMode: CodexAuthMode = CodexAuthMode.API_KEY, val codexReasoningEffort: CodexReasoningEffort = CodexReasoningEffort.HIGH, val codexSupportsWebsockets: Boolean = false, val codexSkills: Boolean = false, val codexServiceTierFast: Boolean = false, val codexAuthJsonText: String = "", val codexConfigTomlText: String = "", val geminiAuthMode: GeminiAuthMode = GeminiAuthMode.LOGIN_GOOGLE, val geminiApiKey: String = "", val googleApiKey: String = "", val googleCloudProject: String = "", val googleCloudLocation: String = "", val googleApplicationCredentials: String = ""` |
| 366 |   `val enabled: Boolean` |
| 368 |   `val envText: String` |
| 370 |   `val configText: String` |
| 371 |   `val modelProviderId: Int?` |
| 374 |   `val apiBaseUrl: String` |
| 375 |   `val apiKey: String` |
| 376 |   `val model: String` |
| 379 |   `val claudeAuthMode: ClaudeAuthMode` |
| 380 |   `val claudeMainModel: String` |
| 381 |   `val claudeReasoningModel: String` |
| 382 |   `val claudeDefaultHaikuModel: String` |
| 383 |   `val claudeDefaultSonnetModel: String` |
| 384 |   `val claudeDefaultOpusModel: String` |
| 385 |   `val claudeEffortLevel: ClaudeEffortLevel` |
| 388 |   `val codexAuthMode: CodexAuthMode` |
| 389 |   `val codexReasoningEffort: CodexReasoningEffort` |
| 390 |   `val codexSupportsWebsockets: Boolean` |
| 391 |   `val codexSkills: Boolean` |
| 392 |   `val codexServiceTierFast: Boolean` |
| 393 |   `val codexAuthJsonText: String` |
| 394 |   `val codexConfigTomlText: String` |
| 397 |   `val geminiAuthMode: GeminiAuthMode` |
| 398 |   `val geminiApiKey: String` |
| 399 |   `val googleApiKey: String` |
| 400 |   `val googleCloudProject: String` |
| 401 |   `val googleCloudLocation: String` |
| 402 |   `val googleApplicationCredentials: String` |
| 405 |   `val openClawGatewayUrl: String` |
| 406 |   `val openClawGatewayToken: String` |
| 407 |   `val openClawSessionKey: String` |
| 410 |   `val codeBuddyEnvironment: CodeBuddyEnvironment` |
| 411 |   `val codeBuddyBaseUrl: String` |
| 417 |   `val grokPermissionMode: String` |
| 418 |   `val grokReasoningEffort: String` |
| 419 |   `val grokConfigTomlText: String` |
| 426 |   `val cursorAuthMode: CursorAuthMethod` |
| 427 |   `val cursorModel: String` |
| 428 |   `val cursorForce: Boolean` |
| 429 |   `val cursorSandboxMode: String` |
| 430 |   `val cursorAllowRules: List<String>` |
| 431 |   `val cursorDenyRules: List<String>` |
| 432 |   `val cursorCliConfigText: String` |
| 435 |   `val clineProvider: String` |
| 436 |   `val clineApiKey: String` |
| 437 |   `val clineModel: String` |
| 438 |   `val clineBaseUrl: String` |
| 441 |   `val openCodeMainModel: String` |
| 442 |   `val openCodeSmallModel: String` |
| 443 |   `val openCodeAuthJsonText: String` |
| 444 |   `val hermesProvider: String` |
| 451 |   `fun reapplied(agentType: AgentType): AgentDraft` |
| 461 |   `companion object` |
| 463 |     `fun fromAgent(agent: AcpAgentInfo): AgentDraft` |
| 615 | `object AgentConfig` |
| 621 |   `fun usesMerge(agent: AgentType): Boolean` |
| 628 |   `fun missingModelProvider(agent: AgentType, draft: AgentDraft): Boolean` |
| 639 |   `fun ensureOpenCodeProviderNpm(configText: String): String` |
| 662 |   `data class ImportantValues( val apiBaseUrl: String = "", val apiKey: String = "", val model: String = "", val claudeMainModel: String = "", val claudeReasoningModel: String = "", val claudeDefaultHaikuModel: String = "", val claudeDefaultSonnetModel: String = "", val claudeDefaultOpusModel: String = "", val claudeEffortLevel: ClaudeEffortLevel = ClaudeEffortLevel.DEFAULT, )` |
| 674 |   `fun extractImportant(agent: AgentType, env: Map<String, String>, configText: String): ImportantValues` |
| 702 |   `data class GeminiValues( val authMode: GeminiAuthMode = GeminiAuthMode.LOGIN_GOOGLE, val apiBaseUrl: String = "", val geminiApiKey: String = "", val googleApiKey: String = "", val googleCloudProject: String = "", val googleCloudLocation: String = "", val googleApplicationCredentials: String = "", val model: String = "", )` |
| 713 |   `fun extractGemini(env: Map<String, String>, configText: String): GeminiValues` |
| 729 |   `fun inferGeminiAuthMode(v: GeminiValues): GeminiAuthMode` |
| 744 |   `fun applyGeminiAuthMode(draft: AgentDraft, mode: GeminiAuthMode): AgentDraft` |
| 768 |   `data class OpenClawValues(val gatewayUrl: String = "", val gatewayToken: String = "", val sessionKey: String = "")` |
| 770 |   `fun extractOpenClaw(env: Map<String, String>, configText: String): OpenClawValues` |
| 781 |   `data class CodeBuddyValues( val apiKey: String = "", val environment: CodeBuddyEnvironment = CodeBuddyEnvironment.OVERSEAS, val baseUrl: String = "", )` |
| 793 |   `fun extractCodeBuddy(env: Map<String, String>): CodeBuddyValues` |
| 808 |   `data class ClineValues( val provider: String = "anthropic", val apiKey: String = "", val model: String = "", val baseUrl: String = "", )` |
| 815 |   `fun extractCline(configText: String): ClineValues` |
| 825 |   `data class OpenCodeValues(val mainModel: String = "", val smallModel: String = "")` |
| 827 |   `fun extractOpenCode(configText: String): OpenCodeValues` |
| 838 |   `data class Applied( val configText: String, val envText: String, val codexConfigTomlText: String, val codexAuthJsonText: String, )` |
| 852 |   `fun reapply(agent: AgentType, draft: AgentDraft): Applied` |
| 924 |   `private fun applyClaudeConfig(configText: String, d: AgentDraft): String` |
| 927 |     `fun assignEnv(key: String, value: String)` |
| 944 |   `private fun applyClaudeEnv(envText: String, d: AgentDraft): String` |
| 955 |   `private fun applyClaudeEffort(configText: String, level: ClaudeEffortLevel): String` |
| 963 |   `val claudeCredentialAliasKeys` |
| 973 |   `fun clearClaudeCredentialAliases(configText: String, envText: String): Pair<String, String>` |
| 985 |   `private fun applyGeminiConfig(configText: String, d: AgentDraft): String` |
| 989 |     `fun assign(key: String, value: String)` |
| 1006 |   `private fun applyGeminiEnv(envText: String, d: AgentDraft): String` |
| 1023 |   `private fun stripKeysFromConfigEnv(configText: String, keys: List<String>): String` |
| 1034 |   `private fun applyOpenClawEnv(envText: String, d: AgentDraft): String` |
| 1052 |   `private fun applyCodeBuddyEnv(envText: String, d: AgentDraft): String` |
| 1077 |   `private fun applyGrokEnv(envText: String, d: AgentDraft): String` |
| 1081 |   `fun normalizeCodeBuddyBaseUrl(value: String): String` |
| 1085 |   `fun isValidCodeBuddyBaseUrl(value: String): Boolean` |
| 1098 |   `fun missingCodeBuddyBaseUrl(agent: AgentType, draft: AgentDraft): Boolean` |
| 1108 |   `fun missingCursorApiKey(agent: AgentType, draft: AgentDraft): Boolean` |
| 1113 |   `private fun buildClineConfig(d: AgentDraft): String` |
| 1124 |   `private fun applyOpenCodeConfig(configText: String, d: AgentDraft): String` |
| 1126 |     `fun assign(key: String, value: String)` |
| 1137 |   `data class HermesValues( val provider: String = "openrouter", val model: String = "", val baseUrl: String = "", val apiKey: String = "", )` |
| 1145 |   `fun parseHermes(configText: String): HermesValues` |
| 1160 | `enum class HermesProviderKind` |
| 1162 | `data class HermesProviderOption( val id: String, val label: String, val needsBaseUrl: Boolean, val kind: HermesProviderKind, )` |
| 1163 |   `val id: String` |
| 1164 |   `val label: String` |
| 1165 |   `val needsBaseUrl: Boolean` |
| 1166 |   `val kind: HermesProviderKind` |
| 1170 | `val hermesProviders: List<HermesProviderOption>` |
| 1213 | `fun hermesProvider(id: String): HermesProviderOption?` |

## core/model/AgentConfigCursor.kt

| Line | Declaration |
|---|---|
| 15 | `object CursorConfig` |
| 20 |   `const val API_KEY` |
| 26 |   `const val API_BASE_URL` |
| 29 |   `const val MODEL` |
| 35 |   `const val FORCE` |
| 42 |   `const val AUTH_MODE` |
| 50 |   `fun inferMode(env: Map<String, String>): CursorAuthMethod` |
| 60 |   `fun isForceEnabled(env: Map<String, String>): Boolean` |
| 68 |   `fun hasForceKnob(env: Map<String, String>): Boolean` |
| 71 |   `fun forceOrDefault(env: Map<String, String>): Boolean` |
| 79 |   `fun loginCommand(binaryPath: String?): String` |
| 105 |   `fun applyEnv( envText: String, mode: CursorAuthMethod, apiKey: String, model: String, force: Boolean, ): String` |
| 128 | `enum class CursorAuthMethod(val wire: String)` |
| 132 |   `companion object` |
| 133 |     `fun fromWire(raw: String?): CursorAuthMethod?` |

## core/model/AgentConfigKimi.kt

| Line | Declaration |
|---|---|
| 18 | `enum class KimiAuthMode(val wire: String)` |
| 24 | `enum class KimiInterfaceType(val wire: String)` |
| 32 |   `companion object` |
| 33 |     `fun fromWire(raw: String?): KimiInterfaceType?` |
| 38 | `enum class KimiNativeAuthType(val wire: String)` |
| 42 |   `companion object` |
| 43 |     `fun fromWire(raw: String?): KimiNativeAuthType?` |
| 48 | `enum class KimiEndpointRegion` |
| 50 | `const val KIMI_BASE_URL_INTERNATIONAL` |
| 51 | `const val KIMI_BASE_URL_CHINA` |
| 53 | `const val KIMI_MODEL_PLACEHOLDER` |
| 55 | `data class KimiInterfaceTypeMeta( val value: KimiInterfaceType, /** Product label (proper noun — intentionally not localized). */ val label: String, /** Base URL pre-filled when this interface is selected ("" → SDK default). */ val defaultBaseUrl: String, /** vertexai authenticates via GCP ADC, so it exposes no API key field. */ val usesApiKey: Boolean, )` |
| 56 |   `val value: KimiInterfaceType` |
| 58 |   `val label: String` |
| 60 |   `val defaultBaseUrl: String` |
| 62 |   `val usesApiKey: Boolean` |
| 65 | `val kimiInterfaceTypes: List<KimiInterfaceTypeMeta>` |
| 74 | `fun kimiInterfaceMeta(type: KimiInterfaceType): KimiInterfaceTypeMeta` |
| 81 | `fun kimiEndpointRegionFromBaseUrl(baseUrl: String): KimiEndpointRegion` |
| 82 |   `val raw` |
| 91 | `fun kimiBaseUrlForRegion(region: KimiEndpointRegion, customUrl: String): String` |
| 102 | `data class KimiManagedConfig( val interfaceType: KimiInterfaceType? = null, val baseUrl: String? = null, val key: String? = null, val authType: KimiNativeAuthType? = null, val modelId: String? = null, val maxContextSize: Int? = null, val vertexProject: String? = null, val vertexLocation: String? = null, val hasManagedBlock: Boolean? = null, /** Whether `kimi acp`'s session gate is satisfied (a token file is present). */ val credentialPresent: Boolean? = null, /** Whether that gate token is codeg's synthetic one (vs a real OAuth login). */ val credentialSynthetic: Boolean? = null, val rawConfigToml: String? = null, )` |
| 103 |   `val interfaceType: KimiInterfaceType?` |
| 104 |   `val baseUrl: String?` |
| 105 |   `val key: String?` |
| 106 |   `val authType: KimiNativeAuthType?` |
| 107 |   `val modelId: String?` |
| 108 |   `val maxContextSize: Int?` |
| 109 |   `val vertexProject: String?` |
| 110 |   `val vertexLocation: String?` |
| 111 |   `val hasManagedBlock: Boolean?` |
| 113 |   `val credentialPresent: Boolean?` |
| 115 |   `val credentialSynthetic: Boolean?` |
| 116 |   `val rawConfigToml: String?` |
| 118 |   `companion object` |
| 124 |     `fun parse(configJson: String?): KimiManagedConfig` |
| 154 | `fun kimiInitialMode(config: KimiManagedConfig): KimiAuthMode` |

## core/model/AgentConfigPi.kt

| Line | Declaration |
|---|---|
| 20 | `data class PiConfigProjection( val defaultProvider: String? = null, val defaultModel: String? = null, val defaultThinkingLevel: String? = null, val authProviders: List<String> = emptyList(), val customProviders: List<PiCustomProvider> = emptyList(), )` |
| 21 |   `val defaultProvider: String?` |
| 22 |   `val defaultModel: String?` |
| 23 |   `val defaultThinkingLevel: String?` |
| 24 |   `val authProviders: List<String>` |
| 25 |   `val customProviders: List<PiCustomProvider>` |
| 29 | `data class PiCustomProvider(val id: String, val baseUrl: String, val api: String)` |
| 33 | `data class PiCommandValidation( val found: Boolean = false, val resolvedPath: String? = null, val version: String? = null, )` |
| 34 |   `val found: Boolean` |
| 35 |   `val resolvedPath: String?` |
| 36 |   `val version: String?` |
| 40 | `enum class PiRuntimeMode` |
| 42 | `object PiEnvKeys` |
| 43 |   `const val COMMAND` |
| 44 |   `const val CONFIG_DIR` |
| 45 |   `const val SESSION_DIR` |
| 47 |   `const val TRUST_WORKSPACE` |
| 51 | `const val PI_CUSTOM_PROVIDER_SENTINEL` |
| 53 | `val piThinkingLevels: List<String>` |
| 56 | `val piCustomApiProtocols: List<String>` |
| 64 | `val piBuiltinProviders: List<Pair<String, String>>` |
| 95 | `object PiConfig` |
| 102 |   `fun buildRuntimeEnv( prevEnv: Map<String, String>, mode: PiRuntimeMode, command: String, configDir: String, sessionDir: String, ): Map<String, String>` |
| 123 |   `private fun assign(env: MutableMap<String, String>, key: String, value: String)` |

## core/model/AgentConfigToml.kt

| Line | Declaration |
|---|---|
| 15 | `object AgentToml` |
| 17 |   `private val lenientJson` |
| 21 |   `private fun lines(text: String): List<String>` |
| 23 |   `private fun join(lines: List<String>): String` |
| 29 |   `private fun capture(pattern: String, s: String): String?` |
| 32 |   `private fun matchesRe(pattern: String, s: String): Boolean` |
| 35 |   `fun jsonQuote(s: String): String` |
| 54 |   `private fun parseAssignmentKey(rawLine: String): String?` |
| 64 |   `private fun jsonDecodeStringLiteral(literal: String): String?` |
| 70 |   `private fun parseStringLiteral(raw: String): String?` |
| 96 |   `private fun parseStringAssignment(rawLine: String): Pair<String, String>?` |
| 105 |   `private fun parseBooleanAssignment(rawLine: String): Pair<String, Boolean>?` |
| 121 |   `data class CodexTomlValues( val model: String = "", val modelProvider: String = "", val modelReasoningEffort: CodexReasoningEffort = CodexReasoningEffort.FALLBACK, val providerNames: List<String> = emptyList(), val providerBaseUrls: Map<String, String> = emptyMap(), val providerSupportsWebsockets: Map<String, Boolean> = emptyMap(), val featureResponsesWebsocketsV2: Boolean = false, val featureSkills: Boolean = false, val serviceTierFast: Boolean = false, )` |
| 133 |   `fun extractCodexToml(configTomlText: String): CodexTomlValues` |
| 228 |   `data class CodexValues( val apiBaseUrl: String = "", val apiKey: String? = "", val model: String = "", val modelProvider: String = "", val reasoningEffort: CodexReasoningEffort = CodexReasoningEffort.FALLBACK, val supportsWebsockets: Boolean = false, val skills: Boolean = false, val serviceTierFast: Boolean = false, )` |
| 239 |   `fun extractCodex(authJsonText: String, configTomlText: String): CodexValues` |
| 272 |   `private data class AuthParsed(val obj: Map<String, JsonElement>?, val error: String?)` |
| 274 |   `private fun parseAuthObject(text: String): AuthParsed` |
| 281 |   `fun inferCodexAuthMode(authJsonText: String): CodexAuthMode` |
| 295 |   `fun patchCodexAuth(authJsonText: String, apiKey: String): String` |
| 314 |   `private fun rootEndIndex(lines: List<String>): Int` |
| 321 |   `private fun rootAssignmentIndex(lines: List<String>, key: String): Int` |
| 327 |   `private fun preferredRootInsertion(lines: List<String>, key: String): Int` |
| 341 |   `fun setRootString(tomlText: String, key: String, value: String): String` |
| 355 |   `fun setRootBool(tomlText: String, key: String, value: Boolean): String` |
| 367 |   `private fun sectionRange(lines: List<String>, name: String): Pair<Int, Int>?` |
| 386 |   `fun upsertSectionBool(tomlText: String, name: String, key: String, value: Boolean?): String` |
| 439 |   `fun upsertSectionString(tomlText: String, name: String, key: String, value: String?): String` |
| 494 |   `fun patchGrok(tomlText: String, permissionMode: String, reasoningEffort: String): String` |
| 500 |   `private fun providerSectionRange(lines: List<String>, provider: String): Pair<Int, Int>?` |
| 515 |   `private fun patchProviderBaseUrl(tomlText: String, provider: String, apiBaseUrl: String): String` |
| 542 |   `private fun patchProviderField(tomlText: String, provider: String, key: String, lineText: String): String` |
| 565 |   `private fun ensureProviderDefaults(tomlText: String, provider: String): String` |
| 585 |   `fun patchCodex(tomlText: String, d: AgentDraft): String` |

## core/model/AgentType.kt

| Line | Declaration |
|---|---|
| 22 | `enum class AgentType(val wire: String)` |
| 43 |   `val displayName: String` |
| 67 |   `val shortName: String` |
| 96 |   `val isServerManagedConfig: Boolean` |
| 99 |   `companion object` |
| 110 |     `fun fromWire(raw: String): AgentType` |
| 113 |     `fun knownFromWire(raw: String): AgentType?` |
| 116 |   `object AgentTypeSerializer : KSerializer<AgentType>` |
| 120 |     `override fun serialize(encoder: Encoder, value: AgentType)` |
| 123 |     `override fun deserialize(decoder: Decoder): AgentType` |

## core/model/BranchManage.kt

| Line | Declaration |
|---|---|
| 5 | `data class BranchManageEntry( val name: String, val isRemote: Boolean = false, val ahead: Int? = null, val behind: Int? = null, )` |
| 6 |   `val name: String` |
| 7 |   `val isRemote: Boolean` |
| 8 |   `val ahead: Int?` |
| 9 |   `val behind: Int?` |
| 11 |   `val remote: String get()` |
| 12 |   `val remoteBranch: String get()` |
| 15 | `fun parseManagedBranches(value: JsonElement): List<BranchManageEntry>` |

## core/model/ChatChannel.kt

| Line | Declaration |
|---|---|
| 23 | `enum class ChannelType(val wire: String)` |
| 28 |   `val displayName: String` |
| 37 |   `val secretLabel: String?` |
| 44 |   `companion object` |
| 45 |     `fun fromWire(raw: String): ChannelType` |
| 52 | `enum class ChannelConnectionStatus(val wire: String)` |
| 58 |   `val label: String` |
| 66 |   `object Serializer : KSerializer<ChannelConnectionStatus>` |
| 69 |     `override fun serialize(encoder: Encoder, value: ChannelConnectionStatus)` |
| 70 |     `override fun deserialize(decoder: Decoder): ChannelConnectionStatus` |
| 78 | `data class ChatChannelInfo( val id: Int, val name: String, val channelType: ChannelType, val enabled: Boolean = false, val configJson: String = "{}", val eventFilterJson: String? = null, val dailyReportEnabled: Boolean = false, val dailyReportTime: String? = null, )` |
| 79 |   `val id: Int` |
| 80 |   `val name: String` |
| 81 |   `val channelType: ChannelType` |
| 82 |   `val enabled: Boolean` |
| 83 |   `val configJson: String` |
| 84 |   `val eventFilterJson: String?` |
| 85 |   `val dailyReportEnabled: Boolean` |
| 86 |   `val dailyReportTime: String?` |
| 88 |   `fun withEnabled(value: Boolean): ChatChannelInfo` |
| 93 | `data class ChannelStatusInfo( val channelId: Int, val name: String = "", val channelType: ChannelType, val status: ChannelConnectionStatus = ChannelConnectionStatus.DISCONNECTED, )` |
| 94 |   `val channelId: Int` |
| 95 |   `val name: String` |
| 96 |   `val channelType: ChannelType` |
| 97 |   `val status: ChannelConnectionStatus` |
| 102 | `data class ChatChannelMessageLog( val id: Int, val channelId: Int, val direction: String = "", val messageType: String = "", val contentPreview: String = "", val status: String = "", val errorDetail: String? = null, val createdAt: String? = null, )` |
| 103 |   `val id: Int` |
| 104 |   `val channelId: Int` |
| 105 |   `val direction: String` |
| 106 |   `val messageType: String` |
| 107 |   `val contentPreview: String` |
| 108 |   `val status: String` |
| 109 |   `val errorDetail: String?` |
| 110 |   `val createdAt: String?` |
| 112 |   `val isInbound: Boolean get()` |
| 113 |   `val failed: Boolean get()` |
| 118 | `data class WebhookConfig(val url: String, val enabled: Boolean)` |
| 123 | `data class WeixinQrcode(val qrcodeId: String, val qrcodeImgContent: String)` |
| 127 | `data class WeixinQrStatus(val status: String = "")` |
| 131 | `data class ChannelConfig( var chatId: String = "", var appId: String = "", var baseUrl: String = "", )` |
| 132 |   `var chatId: String` |
| 133 |   `var appId: String` |
| 134 |   `var baseUrl: String` |
| 137 |   `fun toJson(type: ChannelType): String` |
| 147 |   `fun summary(type: ChannelType): String` |
| 153 |   `companion object` |
| 158 |     `fun parse(json: String?): ChannelConfig` |
| 170 | `object ChatEventCatalog` |
| 171 |   `data class Item(val id: String, val label: String, val defaultOn: Boolean, val note: String? = null)` |
| 173 |   `val all: List<Item>` |
| 181 |   `val defaultEnabled: Set<String> get()` |
| 185 | `object ChatLanguageCatalog` |
| 186 |   `val options: List<Pair<String, String>>` |
| 192 |   `fun label(code: String): String` |

## core/model/Conversation.kt

| Line | Declaration |
|---|---|
| 13 | `data class ConversationSummary( val id: Int, val folderId: Int, val title: String? = null, val agentType: AgentType, val status: ConversationStatus, val model: String? = null, val gitBranch: String? = null, val externalId: String? = null, val messageCount: Int = 0, @Serializable(with = InstantSerializer::class) val createdAt: Instant, @Serializable(with = InstantSerializer::class) val updatedAt: Instant, /** Server `pinned_at`, or null when not pinned. Pinning does NOT bump * `updated_at` — the "Pinned" group sorts by this instead. */ @Serializable(with = InstantSerializer::class) val pinnedAt: Instant? = null, )` |
| 14 |   `val id: Int` |
| 15 |   `val folderId: Int` |
| 16 |   `val title: String?` |
| 17 |   `val agentType: AgentType` |
| 18 |   `val status: ConversationStatus` |
| 19 |   `val model: String?` |
| 20 |   `val gitBranch: String?` |
| 21 |   `val externalId: String?` |
| 22 |   `val messageCount: Int` |
| 24 |   `val createdAt: Instant` |
| 26 |   `val updatedAt: Instant` |
| 30 |   `val pinnedAt: Instant?` |
| 35 |   `val trimmedTitle: String?` |
| 38 |   `val isPinned: Boolean get()` |
| 43 | `data class SessionStats( val totalUsage: TurnUsage? = null, val totalTokens: Int? = null, val totalDurationMs: Int = 0, val contextWindowUsedTokens: Int? = null, val contextWindowMaxTokens: Int? = null, val contextWindowUsagePercent: Double? = null, )` |
| 44 |   `val totalUsage: TurnUsage?` |
| 45 |   `val totalTokens: Int?` |
| 46 |   `val totalDurationMs: Int` |
| 47 |   `val contextWindowUsedTokens: Int?` |
| 48 |   `val contextWindowMaxTokens: Int?` |
| 49 |   `val contextWindowUsagePercent: Double?` |
| 58 | `data class ConversationDetail( val summary: ConversationSummary, val turns: List<MessageTurn> = emptyList(), val sessionStats: SessionStats? = null, val inFlightUserTurnId: String? = null, )` |
| 59 |   `val summary: ConversationSummary` |
| 60 |   `val turns: List<MessageTurn>` |
| 61 |   `val sessionStats: SessionStats?` |
| 62 |   `val inFlightUserTurnId: String?` |
| 68 | `data class ConversationConnectionInfo( val connectionId: String, val eventSeq: Long = 0, )` |
| 69 |   `val connectionId: String` |
| 70 |   `val eventSeq: Long` |
| 75 | `data class HealthResponse( val status: String, val version: String, )` |
| 76 |   `val status: String` |
| 77 |   `val version: String` |

## core/model/ConversationStatus.kt

| Line | Declaration |
|---|---|
| 17 | `enum class ConversationStatus(val wire: String)` |
| 24 |   `val isLive: Boolean get()` |
| 26 |   `companion object` |
| 27 |     `fun fromWire(raw: String): ConversationStatus` |
| 38 |   `object ConversationStatusSerializer : KSerializer<ConversationStatus>` |
| 42 |     `override fun serialize(encoder: Encoder, value: ConversationStatus)` |
| 45 |     `override fun deserialize(decoder: Decoder): ConversationStatus` |

## core/model/Folder.kt

| Line | Declaration |
|---|---|
| 16 | `data class FolderDetail( val id: Int, val name: String, val path: String, val gitBranch: String? = null, val defaultAgentType: AgentType? = null, @Serializable(with = InstantSerializer::class) val lastOpenedAt: Instant? = null, val sortOrder: Int = 0, val color: String = "", val parentId: Int? = null, val isChat: Boolean = false, )` |
| 17 |   `val id: Int` |
| 18 |   `val name: String` |
| 19 |   `val path: String` |
| 20 |   `val gitBranch: String?` |
| 21 |   `val defaultAgentType: AgentType?` |
| 23 |   `val lastOpenedAt: Instant?` |
| 24 |   `val sortOrder: Int` |
| 25 |   `val color: String` |
| 26 |   `val parentId: Int?` |
| 27 |   `val isChat: Boolean` |

## core/model/FolderVisibility.kt

| Line | Declaration |
|---|---|
| 12 | `object FolderVisibility` |
| 15 |   `fun isWorktree(folder: FolderDetail): Boolean` |
| 21 |   `fun filterTopLevel(folders: List<FolderDetail>): List<FolderDetail>` |
| 28 |   `fun resolveRoot(folder: FolderDetail, all: List<FolderDetail>): FolderDetail` |
| 37 |   `fun displayName(folder: FolderDetail, all: List<FolderDetail>): String` |
| 45 |   `sealed interface BranchSwitchPlan` |
| 47 |     `data object Noop : BranchSwitchPlan` |
| 49 |     `data class NavigateRegistered(val folderId: Int) : BranchSwitchPlan` |
| 54 |     `data class NavigateExternal(val path: String, val rootId: Int) : BranchSwitchPlan` |
| 59 |     `data class CheckoutInRoot(val root: FolderDetail) : BranchSwitchPlan` |
| 67 |   `fun planBranchSwitch( active: FolderDetail, resolution: WorktreeResolution?, allFolders: List<FolderDetail>, isRemote: Boolean, ): BranchSwitchPlan` |

## core/model/Git.kt

| Line | Declaration |
|---|---|
| 8 | `data class DirectoryEntry( val name: String, val path: String, val hasChildren: Boolean = false, )` |
| 9 |   `val name: String` |
| 10 |   `val path: String` |
| 11 |   `val hasChildren: Boolean` |
| 20 | `data class DirectoryItem( val name: String, val path: String, val isDir: Boolean = false, val hasChildren: Boolean = false, val size: Long? = null, )` |
| 21 |   `val name: String` |
| 22 |   `val path: String` |
| 23 |   `val isDir: Boolean` |
| 24 |   `val hasChildren: Boolean` |
| 25 |   `val size: Long?` |
| 30 | `data class FilePreviewContent( val path: String, val content: String, )` |
| 31 |   `val path: String` |
| 32 |   `val content: String` |
| 37 | `data class GitStatusEntry( val status: String, val file: String, )` |
| 38 |   `val status: String` |
| 39 |   `val file: String` |
| 42 |   `val path: String` |
| 46 |   `val renamedFrom: String?` |
| 49 |   `val change: GitChange get()` |
| 53 | `enum class GitChange(val badge: String)` |
| 57 |   `companion object` |
| 58 |     `fun from(status: String): GitChange` |
| 75 | `data class GitLogResult( val entries: List<GitLogEntry> = emptyList(), val hasUpstream: Boolean = false, )` |
| 76 |   `val entries: List<GitLogEntry>` |
| 77 |   `val hasUpstream: Boolean` |
| 82 | `data class GitLogEntry( val hash: String, val fullHash: String, val author: String = "", val date: String = "", val message: String = "", val files: List<GitLogFileChange> = emptyList(), val pushed: Boolean? = null, )` |
| 83 |   `val hash: String` |
| 84 |   `val fullHash: String` |
| 85 |   `val author: String` |
| 86 |   `val date: String` |
| 87 |   `val message: String` |
| 88 |   `val files: List<GitLogFileChange>` |
| 89 |   `val pushed: Boolean?` |
| 91 |   `val subject: String get()` |
| 92 |   `val body: String` |
| 94 |   `val totalAdditions: Int get()` |
| 95 |   `val totalDeletions: Int get()` |
| 100 | `data class GitLogFileChange( val path: String, val status: String = "", val additions: Int = 0, val deletions: Int = 0, )` |
| 101 |   `val path: String` |
| 102 |   `val status: String` |
| 103 |   `val additions: Int` |
| 104 |   `val deletions: Int` |
| 109 | `data class GitBranchList( val local: List<String> = emptyList(), val remote: List<String> = emptyList(), @SerialName("worktree_branches") val worktreeBranches: List<String> = emptyList(), )` |
| 110 |   `val local: List<String>` |
| 111 |   `val remote: List<String>` |
| 113 |   `val worktreeBranches: List<String>` |
| 118 | `data class GitCommitResult( val committedFiles: Int = 0, )` |
| 119 |   `val committedFiles: Int` |
| 127 | `data class GitPushResult( val pushedCommits: Int = 0, val upstreamSet: Boolean = false, )` |
| 128 |   `val pushedCommits: Int` |
| 129 |   `val upstreamSet: Boolean` |
| 137 | `data class GitPullResult( val updatedFiles: Int = 0, val conflict: GitConflictInfo? = null, )` |
| 138 |   `val updatedFiles: Int` |
| 139 |   `val conflict: GitConflictInfo?` |
| 147 | `data class GitConflictInfo( val hasConflicts: Boolean = false, val conflictedFiles: List<String> = emptyList(), val operation: String = "", val upstreamCommit: String? = null, )` |
| 148 |   `val hasConflicts: Boolean` |
| 149 |   `val conflictedFiles: List<String>` |
| 150 |   `val operation: String` |
| 151 |   `val upstreamCommit: String?` |
| 159 | `data class GitPushInfo( val branch: String = "", val remotes: List<GitRemote> = emptyList(), val trackingRemote: String? = null, )` |
| 160 |   `val branch: String` |
| 161 |   `val remotes: List<GitRemote>` |
| 162 |   `val trackingRemote: String?` |
| 165 |   `val uniqueRemotes: List<GitRemote>` |
| 174 | `data class GitRemote( val name: String, val url: String, )` |
| 175 |   `val name: String` |
| 176 |   `val url: String` |
| 190 | `data class WorktreeResolution( val path: String? = null, @SerialName("folder_id") val folderId: Int? = null, )` |
| 191 |   `val path: String?` |
| 193 |   `val folderId: Int?` |

## core/model/Interactive.kt

| Line | Declaration |
|---|---|
| 17 | `data class PermissionOption( val optionId: String, val name: String, val kind: String, )` |
| 18 |   `val optionId: String` |
| 19 |   `val name: String` |
| 20 |   `val kind: String` |
| 23 |   `val isReject: Boolean get()` |
| 28 | `data class QuestionOption( val label: String, val description: String = "", )` |
| 29 |   `val label: String` |
| 30 |   `val description: String` |
| 38 | `data class QuestionSpec( val id: String, val question: String, val header: String = "", val multiSelect: Boolean = false, val options: List<QuestionOption> = emptyList(), )` |
| 39 |   `val id: String` |
| 40 |   `val question: String` |
| 41 |   `val header: String` |
| 42 |   `val multiSelect: Boolean` |
| 43 |   `val options: List<QuestionOption>` |
| 52 | `data class QuestionAnswerItem( val questionId: String, val labels: List<String>, )` |
| 53 |   `val questionId: String` |
| 54 |   `val labels: List<String>` |
| 62 | `data class QuestionAnswer( val answers: List<QuestionAnswerItem> = emptyList(), val declined: Boolean = false, )` |
| 63 |   `val answers: List<QuestionAnswerItem>` |
| 64 |   `val declined: Boolean` |
| 66 |   `companion object` |
| 76 | `enum class PlanApprovalDecision(val wire: String)` |
| 84 |   `object Serializer : KSerializer<PlanApprovalDecision>` |
| 88 |     `override fun serialize(encoder: Encoder, value: PlanApprovalDecision)` |
| 91 |     `override fun deserialize(decoder: Decoder): PlanApprovalDecision` |
| 98 | `data class PlanEntry( val content: String, val priority: String = "medium", val status: String = "pending", )` |
| 99 |   `val content: String` |
| 100 |   `val priority: String` |
| 101 |   `val status: String` |
| 103 |   `enum class Status` |
| 105 |   `val normalizedStatus: Status` |
| 112 |   `enum class Priority` |
| 114 |   `val normalizedPriority: Priority` |

## core/model/Message.kt

| Line | Declaration |
|---|---|
| 21 | `enum class TurnRole(val wire: String)` |
| 26 |   `companion object` |
| 27 |     `fun fromWire(raw: String): TurnRole` |
| 31 |   `object TurnRoleSerializer : KSerializer<TurnRole>` |
| 38 |     `override fun serialize(encoder: Encoder, value: TurnRole)` |
| 41 |     `override fun deserialize(decoder: Decoder): TurnRole` |
| 48 | `data class TurnUsage( val inputTokens: Int = 0, val outputTokens: Int = 0, val cacheCreationInputTokens: Int = 0, val cacheReadInputTokens: Int = 0, )` |
| 49 |   `val inputTokens: Int` |
| 50 |   `val outputTokens: Int` |
| 51 |   `val cacheCreationInputTokens: Int` |
| 52 |   `val cacheReadInputTokens: Int` |
| 54 |   `val total: Int` |
| 60 | `data class ImageData( val data: String, val mimeType: String = "image/png", val uri: String? = null, )` |
| 61 |   `val data: String` |
| 62 |   `val mimeType: String` |
| 63 |   `val uri: String?` |
| 76 | `sealed interface ContentBlock` |
| 77 |   `data class Text(val text: String) : ContentBlock` |
| 78 |   `data class Thinking(val text: String) : ContentBlock` |
| 79 |   `data class Image(val image: ImageData) : ContentBlock` |
| 80 |   `data class ImageGeneration(val revisedPrompt: String?, val image: ImageData?) : ContentBlock` |
| 86 |   `data class ToolUse( val id: String?, val name: String, val inputPreview: String?, val meta: JsonObject? = null, ) : ContentBlock` |
| 92 |   `data class ToolResult(val id: String?, val outputPreview: String?, val isError: Boolean) : ContentBlock` |
| 93 |   `data class Unknown(val type: String) : ContentBlock` |
| 95 |   `companion object` |
| 96 |     `fun fromWire(obj: JsonObject): ContentBlock` |
| 133 |   `object ContentBlockSerializer : KSerializer<ContentBlock>` |
| 137 |     `override fun deserialize(decoder: Decoder): ContentBlock` |
| 143 |     `override fun serialize(encoder: Encoder, value: ContentBlock)` |
| 151 | `data class MessageTurn( val id: String, val role: TurnRole, val blocks: List<ContentBlock> = emptyList(), @Serializable(with = InstantSerializer::class) val timestamp: Instant, val usage: TurnUsage? = null, val durationMs: Int? = null, val model: String? = null, @Serializable(with = InstantSerializer::class) val completedAt: Instant? = null, )` |
| 152 |   `val id: String` |
| 153 |   `val role: TurnRole` |
| 154 |   `val blocks: List<ContentBlock>` |
| 156 |   `val timestamp: Instant` |
| 157 |   `val usage: TurnUsage?` |
| 158 |   `val durationMs: Int?` |
| 159 |   `val model: String?` |
| 161 |   `val completedAt: Instant?` |

## core/model/SessionSnapshot.kt

| Line | Declaration |
|---|---|
| 11 | `data class AvailableCommandInfo( val name: String, val description: String? = null, val inputHint: String? = null, )` |
| 12 |   `val name: String` |
| 13 |   `val description: String?` |
| 14 |   `val inputHint: String?` |
| 24 | `data class SessionSnapshot( val selectorsReady: Boolean = false, val availableCommands: List<AvailableCommandInfo> = emptyList(), val modes: SessionModeState? = null, val configOptions: List<SessionConfigOption> = emptyList(), )` |
| 25 |   `val selectorsReady: Boolean` |
| 26 |   `val availableCommands: List<AvailableCommandInfo>` |
| 27 |   `val modes: SessionModeState?` |
| 28 |   `val configOptions: List<SessionConfigOption>` |
| 33 | `data class SessionModeState( val currentModeId: String? = null, val availableModes: List<SessionModeInfo> = emptyList(), )` |
| 34 |   `val currentModeId: String?` |
| 35 |   `val availableModes: List<SessionModeInfo>` |
| 39 | `data class SessionModeInfo( val id: String, val name: String = "", val description: String? = null, )` |
| 40 |   `val id: String` |
| 41 |   `val name: String` |
| 42 |   `val description: String?` |
| 47 | `data class SessionConfigOption( val id: String, val name: String = "", val description: String? = null, val category: String? = null, val kind: SessionConfigKind = SessionConfigKind(), )` |
| 48 |   `val id: String` |
| 49 |   `val name: String` |
| 50 |   `val description: String?` |
| 51 |   `val category: String?` |
| 52 |   `val kind: SessionConfigKind` |
| 56 | `data class SessionConfigKind( val type: String = "select", val currentValue: String? = null, val options: List<SessionConfigSelectOption> = emptyList(), val groups: List<SessionConfigSelectGroup> = emptyList(), )` |
| 57 |   `val type: String` |
| 58 |   `val currentValue: String?` |
| 59 |   `val options: List<SessionConfigSelectOption>` |
| 60 |   `val groups: List<SessionConfigSelectGroup>` |
| 64 | `data class SessionConfigSelectOption( val value: String, val name: String = "", val description: String? = null, )` |
| 65 |   `val value: String` |
| 66 |   `val name: String` |
| 67 |   `val description: String?` |
| 71 | `data class SessionConfigSelectGroup( val group: String = "", val name: String = "", val options: List<SessionConfigSelectOption> = emptyList(), )` |
| 72 |   `val group: String` |
| 73 |   `val name: String` |
| 74 |   `val options: List<SessionConfigSelectOption>` |
| 79 | `data class AgentOptionsSnapshot( val modes: SessionModeState? = null, val configOptions: List<SessionConfigOption> = emptyList(), )` |
| 80 |   `val modes: SessionModeState?` |
| 81 |   `val configOptions: List<SessionConfigOption>` |

## core/model/Settings.kt

| Line | Declaration |
|---|---|
| 8 | `data class QuickMessage( val id: Int, val title: String, val content: String, val sortOrder: Int = 0, )` |
| 9 |   `val id: Int` |
| 10 |   `val title: String` |
| 11 |   `val content: String` |
| 12 |   `val sortOrder: Int` |
| 17 | `data class EnabledFlag(val enabled: Boolean)` |
| 21 | `data class EnabledSettingsBody(val settings: EnabledFlag)` |
| 25 | `data class ModelProviderInfo( val id: Int, val name: String, val apiUrl: String, val apiKeyMasked: String = "", val agentType: AgentType, val model: String? = null, )` |
| 26 |   `val id: Int` |
| 27 |   `val name: String` |
| 28 |   `val apiUrl: String` |
| 29 |   `val apiKeyMasked: String` |
| 30 |   `val agentType: AgentType` |
| 31 |   `val model: String?` |
| 36 | `data class SystemProxySettings( val enabled: Boolean = false, val proxyUrl: String? = null, )` |
| 37 |   `val enabled: Boolean` |
| 38 |   `val proxyUrl: String?` |
| 43 | `data class AppUpdateInfo( val version: String, val body: String = "", val date: String? = null, )` |
| 44 |   `val version: String` |
| 45 |   `val body: String` |
| 46 |   `val date: String?` |
| 51 | `data class AppUpdateCheckResult( val currentVersion: String = "", val update: AppUpdateInfo? = null, val selfUpdateSupported: Boolean = false, )` |
| 52 |   `val currentVersion: String` |
| 53 |   `val update: AppUpdateInfo?` |
| 54 |   `val selfUpdateSupported: Boolean` |
| 59 | `data class LocalMcpServer( val id: String, val spec: JsonElement, val apps: List<String> = emptyList(), )` |
| 60 |   `val id: String` |
| 61 |   `val spec: JsonElement` |
| 62 |   `val apps: List<String>` |
| 67 | `data class AgentSkillItem( val id: String, val name: String = "", val scope: String = "global", val layout: String = "markdown_file", val path: String = "", val description: String? = null, val readOnly: Boolean = false, )` |
| 68 |   `val id: String` |
| 69 |   `val name: String` |
| 70 |   `val scope: String` |
| 71 |   `val layout: String` |
| 72 |   `val path: String` |
| 73 |   `val description: String?` |
| 74 |   `val readOnly: Boolean` |
| 79 | `data class AgentSkillsListResult( val supported: Boolean = true, val message: String? = null, val skills: List<AgentSkillItem> = emptyList(), )` |
| 80 |   `val supported: Boolean` |
| 81 |   `val message: String?` |
| 82 |   `val skills: List<AgentSkillItem>` |
| 87 | `data class AgentSkillContent( val skill: AgentSkillItem, val content: String = "", )` |
| 88 |   `val skill: AgentSkillItem` |
| 89 |   `val content: String` |
| 94 | `data class ExpertListItem(val metadata: ExpertMetadata)` |
| 95 |   `val id: String get()` |
| 99 | `data class ExpertMetadata( val id: String, val category: String = "", val icon: String? = null, val sortOrder: Int = 0, val displayName: Map<String, String> = emptyMap(), val description: Map<String, String> = emptyMap(), )` |
| 100 |   `val id: String` |
| 101 |   `val category: String` |
| 102 |   `val icon: String?` |
| 103 |   `val sortOrder: Int` |
| 104 |   `val displayName: Map<String, String>` |
| 105 |   `val description: Map<String, String>` |
| 108 |   `fun title(): String` |
| 109 |   `fun desc(): String` |
| 115 | `enum class ExpertLinkState` |
| 123 |   `val isLinked: Boolean get()` |
| 129 | `data class ExpertInstallStatus( val expertId: String = "", val agentType: AgentType, val state: ExpertLinkState = ExpertLinkState.NOT_LINKED, val linkPath: String = "", val targetPath: String? = null, val expectedTargetPath: String = "", val copyMode: Boolean = false, )` |
| 130 |   `val expertId: String` |
| 131 |   `val agentType: AgentType` |
| 132 |   `val state: ExpertLinkState` |
| 133 |   `val linkPath: String` |
| 134 |   `val targetPath: String?` |
| 135 |   `val expectedTargetPath: String` |
| 136 |   `val copyMode: Boolean` |
| 141 | `data class GitDetectResult( val installed: Boolean = false, val version: String? = null, val path: String? = null, )` |
| 142 |   `val installed: Boolean` |
| 143 |   `val version: String?` |
| 144 |   `val path: String?` |
| 149 | `data class GitSettings(val customPath: String? = null)` |
| 160 | `data class GitHubAccount( val id: String, val serverUrl: String = "github.com", val username: String = "", val isDefault: Boolean = false, val scopes: List<String> = emptyList(), val avatarUrl: String? = null, val createdAt: String? = null, )` |
| 161 |   `val id: String` |
| 162 |   `val serverUrl: String` |
| 163 |   `val username: String` |
| 164 |   `val isDefault: Boolean` |
| 165 |   `val scopes: List<String>` |
| 166 |   `val avatarUrl: String?` |
| 167 |   `val createdAt: String?` |
| 171 | `data class GitHubAccountList(val accounts: List<GitHubAccount> = emptyList())` |
| 178 | `data class GitHubTokenValidation( val success: Boolean = false, val username: String? = null, val scopes: List<String> = emptyList(), val avatarUrl: String? = null, val message: String? = null, )` |
| 179 |   `val success: Boolean` |
| 180 |   `val username: String?` |
| 181 |   `val scopes: List<String>` |
| 182 |   `val avatarUrl: String?` |
| 183 |   `val message: String?` |
| 195 | `data class AcpAgentInfo( val agentType: AgentType, val name: String = "", val description: String = "", val available: Boolean = true, val enabled: Boolean = false, val sortOrder: Int = 0, val installedVersion: String? = null, val env: Map<String, String> = emptyMap(), val modelProviderId: Int? = null, /** Latest version from the npm/GitHub registry, when known. */ val registryVersion: String? = null, /** `"binary" | "npx" | "uvx" | "system"` — how the agent is distributed. */ val distributionType: String? = null, /** Display-only path of the on-disk config file (e.g. `~/.claude/settings.json`). */ val configFilePath: String? = null, /** Raw `config.json` (or, for Hermes, a read-only projection). */ val configJson: String? = null, /** `~/.config/opencode/auth.json` (OpenCode only). */ val opencodeAuthJson: String? = null, /** `~/.codex/auth.json` (Codex only; OAuth tokens are read-only). */ val codexAuthJson: String? = null, /** `~/.codex/config.toml` (Codex only). */ val codexConfigToml: String? = null, /** `~/.hermes/config.yaml` (Hermes only). */ val hermesConfigYaml: String? = null, /** Raw `~/.grok/config.toml` (Grok only; the Advanced escape-hatch editor source). */ val grokConfigToml: String? = null, /** `grok` parsed scalar settings backing the structured controls — derived * server-side from [grokConfigToml] (`null` fields ⇒ the key is absent). */ val grokSettings: GrokSettings? = null, /** Raw `~/.cursor/cli-config.json` (Cursor only; the Advanced escape-hatch editor * source, shared with the Cursor CLI's own `/config` UI). */ val cursorCliConfigJson: String? = null, /** `cursor` parsed scalar settings backing the structured controls — derived * server-side from [cursorCliConfigJson]. */ val cursorSettings: CursorSettings? = null, )` |
| 196 |   `val agentType: AgentType` |
| 197 |   `val name: String` |
| 198 |   `val description: String` |
| 199 |   `val available: Boolean` |
| 200 |   `val enabled: Boolean` |
| 201 |   `val sortOrder: Int` |
| 202 |   `val installedVersion: String?` |
| 203 |   `val env: Map<String, String>` |
| 204 |   `val modelProviderId: Int?` |
| 206 |   `val registryVersion: String?` |
| 208 |   `val distributionType: String?` |
| 210 |   `val configFilePath: String?` |
| 212 |   `val configJson: String?` |
| 214 |   `val opencodeAuthJson: String?` |
| 216 |   `val codexAuthJson: String?` |
| 218 |   `val codexConfigToml: String?` |
| 220 |   `val hermesConfigYaml: String?` |
| 222 |   `val grokConfigToml: String?` |
| 225 |   `val grokSettings: GrokSettings?` |
| 228 |   `val cursorCliConfigJson: String?` |
| 231 |   `val cursorSettings: CursorSettings?` |
| 241 | `data class GrokSettings( val permissionMode: String? = null, val defaultReasoningEffort: String? = null, )` |
| 242 |   `val permissionMode: String?` |
| 243 |   `val defaultReasoningEffort: String?` |
| 254 | `data class CursorSettings( val sandboxMode: String? = null, val permissionsAllow: List<String> = emptyList(), val permissionsDeny: List<String> = emptyList(), )` |
| 255 |   `val sandboxMode: String?` |
| 256 |   `val permissionsAllow: List<String>` |
| 257 |   `val permissionsDeny: List<String>` |
| 266 | `data class CursorAuthStatus( /** A launchable cursor-agent binary was found (cache or system install). */ val installed: Boolean = false, val isAuthenticated: Boolean = false, /** The CLI's own status string (e.g. `"unauthenticated"`). */ val rawStatus: String? = null, /** Account email when signed in. */ val email: String? = null, /** Membership/plan label when the CLI reports one (usually absent). */ val membership: String? = null, /** Probe failure detail (spawn error / timeout / non-JSON output). */ val error: String? = null, /** Absolute path of the binary codeg would launch — the source for * [CursorConfig.loginCommand]. Null when nothing is installed. */ val binaryPath: String? = null, )` |
| 268 |   `val installed: Boolean` |
| 269 |   `val isAuthenticated: Boolean` |
| 271 |   `val rawStatus: String?` |
| 273 |   `val email: String?` |
| 275 |   `val membership: String?` |
| 277 |   `val error: String?` |
| 280 |   `val binaryPath: String?` |
| 286 | `data class CursorModelInfo( val id: String = "", val label: String = "", val isDefault: Boolean = false, )` |
| 287 |   `val id: String` |
| 288 |   `val label: String` |
| 289 |   `val isDefault: Boolean` |
| 292 |   `val displayLabel: String get()` |
| 298 | `data class CursorModelsResult( val models: List<CursorModelInfo> = emptyList(), val defaultModel: String? = null, val error: String? = null, )` |
| 299 |   `val models: List<CursorModelInfo>` |
| 300 |   `val defaultModel: String?` |
| 301 |   `val error: String?` |

## core/model/WireRequests.kt

| Line | Declaration |
|---|---|
| 13 | `sealed interface PromptInputBlock` |
| 16 |   `data class Text(val text: String) : PromptInputBlock` |
| 20 |   `data class Image( val data: String, @SerialName("mime_type") val mimeType: String, val uri: String? = null, ) : PromptInputBlock` |
| 31 | `object EmptyBody` |
| 34 | `data class ListConversationsBody( val folderIds: List<Int>? = null, val agentType: String? = null, val search: String? = null, val sortBy: String? = null, val status: String? = null, val includeChildren: Boolean? = null, )` |
| 35 |   `val folderIds: List<Int>?` |
| 36 |   `val agentType: String?` |
| 37 |   `val search: String?` |
| 38 |   `val sortBy: String?` |
| 39 |   `val status: String?` |
| 40 |   `val includeChildren: Boolean?` |
| 44 | `data class ConversationIdBody(val conversationId: Int)` |
| 47 | `data class CreateConversationBody( val folderId: Int, val agentType: AgentType, val title: String? = null, )` |
| 48 |   `val folderId: Int` |
| 49 |   `val agentType: AgentType` |
| 50 |   `val title: String?` |
| 55 | `data class FindConnectionBody( val conversationId: Int, val sessionId: String? = null, val agentType: AgentType, )` |
| 56 |   `val conversationId: Int` |
| 57 |   `val sessionId: String?` |
| 58 |   `val agentType: AgentType` |
| 62 | `data class ConnectBody( val agentType: AgentType, val workingDir: String? = null, val sessionId: String? = null, /** Last-used mode/config to start the session with (server applies before * reporting state). Omitted when null. */ val preferredModeId: String? = null, val preferredConfigValues: Map<String, String>? = null, )` |
| 63 |   `val agentType: AgentType` |
| 64 |   `val workingDir: String?` |
| 65 |   `val sessionId: String?` |
| 68 |   `val preferredModeId: String?` |
| 69 |   `val preferredConfigValues: Map<String, String>?` |
| 73 | `data class PromptBody( val connectionId: String, val blocks: List<PromptInputBlock>, val folderId: Int? = null, val conversationId: Int? = null, val clientMessageId: String? = null, )` |
| 74 |   `val connectionId: String` |
| 75 |   `val blocks: List<PromptInputBlock>` |
| 76 |   `val folderId: Int?` |
| 77 |   `val conversationId: Int?` |
| 78 |   `val clientMessageId: String?` |
| 82 | `data class ConnectionIdBody(val connectionId: String)` |
| 87 | `data class RespondPermissionBody( val connectionId: String, val requestId: String, val optionId: String, )` |
| 88 |   `val connectionId: String` |
| 89 |   `val requestId: String` |
| 90 |   `val optionId: String` |
| 95 | `data class AnswerQuestionBody( val connectionId: String, val questionId: String, val answer: QuestionAnswer, )` |
| 96 |   `val connectionId: String` |
| 97 |   `val questionId: String` |
| 98 |   `val answer: QuestionAnswer` |
| 107 | `data class AnswerPlanApprovalBody( val connectionId: String, val approvalId: String, val answer: PlanApprovalAnswer, )` |
| 108 |   `val connectionId: String` |
| 109 |   `val approvalId: String` |
| 110 |   `val answer: PlanApprovalAnswer` |
| 114 | `data class PlanApprovalAnswer( val decision: PlanApprovalDecision, val feedback: String? = null, )` |
| 115 |   `val decision: PlanApprovalDecision` |
| 116 |   `val feedback: String?` |
| 120 | `data class UpdateConversationPinnedBody(val conversationId: Int, val pinned: Boolean)` |
| 123 | `data class UpdateConversationTitleBody(val conversationId: Int, val title: String)` |
| 126 | `data class UpdateConversationStatusBody(val conversationId: Int, val status: String)` |
| 130 | `data class PathBody(val path: String)` |
| 135 | `data class ReadFilePreviewBody(val rootPath: String, val path: String)` |
| 138 | `data class GitStatusBody(val path: String, val showAllUntracked: Boolean = true)` |
| 141 | `data class GitLogBody( val path: String, val limit: Int? = null, val branch: String? = null, val remote: String? = null, )` |
| 142 |   `val path: String` |
| 143 |   `val limit: Int?` |
| 144 |   `val branch: String?` |
| 145 |   `val remote: String?` |
| 149 | `data class GitDiffBody(val path: String, val file: String? = null)` |
| 152 | `data class GitShowDiffBody(val path: String, val commit: String, val file: String? = null)` |
| 156 | `data class GitCredentials(val username: String, val password: String)` |
| 159 | `data class CloneRepositoryBody( val url: String, val targetDir: String, val credentials: GitCredentials? = null, )` |
| 160 |   `val url: String` |
| 161 |   `val targetDir: String` |
| 162 |   `val credentials: GitCredentials?` |
| 166 | `data class GitCheckoutBody(val path: String, val branchName: String)` |
| 169 | `data class GitNewBranchBody(val path: String, val branchName: String, val startPoint: String? = null)` |
| 173 | `data class ResolveWorktreeFolderBody(val repoPath: String, val branch: String)` |
| 177 | `data class OpenWorktreeFolderBody(val path: String, val sourceFolderId: Int)` |
| 185 | `data class GitCommitBody( val folderId: Int? = null, val path: String, val message: String, val files: List<String>, )` |
| 186 |   `val folderId: Int?` |
| 187 |   `val path: String` |
| 188 |   `val message: String` |
| 189 |   `val files: List<String>` |
| 197 | `data class GitPushBody( val folderId: Int? = null, val path: String, val remote: String? = null, val credentials: GitCredentials? = null, )` |
| 198 |   `val folderId: Int?` |
| 199 |   `val path: String` |
| 200 |   `val remote: String?` |
| 201 |   `val credentials: GitCredentials?` |
| 206 | `data class GitPullBody(val path: String, val credentials: GitCredentials? = null)` |
| 210 | `data class GitFetchBody(val path: String, val credentials: GitCredentials? = null)` |
| 214 | `data class GitAddFilesBody(val path: String, val files: List<String>)` |
| 218 | `data class PathFileBody(val path: String, val file: String)` |
| 222 | `data class DeleteFileTreeEntryBody(val rootPath: String, val path: String)` |
| 227 | `data class QuickMessageCreateBody(val title: String, val content: String)` |
| 230 | `data class QuickMessageUpdateBody(val id: Int, val title: String, val content: String)` |
| 233 | `data class IdBody(val id: Int)` |
| 236 | `data class QuickMessageReorderBody(val ids: List<Int>)` |
| 239 | `data class CreateModelProviderBody( val name: String, val apiUrl: String, val apiKey: String, val agentType: AgentType, val model: String? = null, )` |
| 240 |   `val name: String` |
| 241 |   `val apiUrl: String` |
| 242 |   `val apiKey: String` |
| 243 |   `val agentType: AgentType` |
| 244 |   `val model: String?` |
| 248 | `data class UpdateModelProviderBody( val id: Int, val name: String? = null, val apiUrl: String? = null, val apiKey: String? = null, val agentType: AgentType? = null, val model: String? = null, )` |
| 249 |   `val id: Int` |
| 250 |   `val name: String?` |
| 251 |   `val apiUrl: String?` |
| 252 |   `val apiKey: String?` |
| 253 |   `val agentType: AgentType?` |
| 254 |   `val model: String?` |
| 258 | `data class UpdateAgentEnvBody( val agentType: AgentType, val enabled: Boolean, val env: Map<String, String> = emptyMap(), val modelProviderId: Int? = null, )` |
| 259 |   `val agentType: AgentType` |
| 260 |   `val enabled: Boolean` |
| 261 |   `val env: Map<String, String>` |
| 262 |   `val modelProviderId: Int?` |
| 266 | `data class SkillListBody(val agentType: AgentType, val workspacePath: String? = null)` |
| 269 | `data class SkillReadBody(val agentType: AgentType, val scope: String, val skillId: String, val workspacePath: String? = null)` |
| 272 | `data class SkillSaveBody( val agentType: AgentType, val scope: String, val skillId: String, val content: String, val workspacePath: String? = null, val layout: String? = null, )` |
| 273 |   `val agentType: AgentType` |
| 274 |   `val scope: String` |
| 275 |   `val skillId: String` |
| 276 |   `val content: String` |
| 277 |   `val workspacePath: String?` |
| 278 |   `val layout: String?` |
| 282 | `data class SkillDeleteBody(val agentType: AgentType, val scope: String, val skillId: String, val workspacePath: String? = null)` |
| 285 | `data class ExpertAgentBody(val agentType: AgentType)` |
| 288 | `data class ExpertLinkBody(val expertId: String, val agentType: AgentType)` |
| 291 | `data class ExpertIdBody(val expertId: String)` |
| 296 | `data class CreateChatChannelBody( val name: String, val channelType: ChannelType, val configJson: String, val enabled: Boolean, val dailyReportEnabled: Boolean, val dailyReportTime: String? = null, )` |
| 297 |   `val name: String` |
| 298 |   `val channelType: ChannelType` |
| 299 |   `val configJson: String` |
| 300 |   `val enabled: Boolean` |
| 301 |   `val dailyReportEnabled: Boolean` |
| 302 |   `val dailyReportTime: String?` |
| 308 | `sealed interface FieldEdit` |
| 309 |   `data object Keep : FieldEdit` |
| 310 |   `data object Clear : FieldEdit` |
| 311 |   `data class Set(val value: String) : FieldEdit` |
| 315 | `data class UpdateChatChannelBody( val id: Int, val name: String? = null, val enabled: Boolean? = null, val configJson: String? = null, val dailyReportEnabled: Boolean? = null, val dailyReportTime: FieldEdit = FieldEdit.Keep, )` |
| 316 |   `val id: Int` |
| 317 |   `val name: String?` |
| 318 |   `val enabled: Boolean?` |
| 319 |   `val configJson: String?` |
| 320 |   `val dailyReportEnabled: Boolean?` |
| 321 |   `val dailyReportTime: FieldEdit` |
| 325 | `data class ChannelTokenBody(val channelId: Int, val token: String)` |
| 328 | `data class ChannelIdOnlyBody(val channelId: Int)` |
| 331 | `data class ListChannelMessagesBody(val channelId: Int, val limit: Int? = null, val offset: Int? = null)` |
| 334 | `data class WeixinCheckBody(val channelId: Int, val qrcode: String)` |
| 337 | `data class ChatPrefixBody(val prefix: String)` |
| 340 | `data class ChatLanguageBody(val language: String)` |
| 343 | `data class ChatWebhooksBody(val webhooks: List<WebhookConfig>)` |
| 348 | `data class DescribeAgentOptionsBody(val agentType: AgentType, val workingDir: String? = null)` |
| 351 | `data class SetModeBody(val connectionId: String, val modeId: String)` |
| 354 | `data class SetConfigOptionBody(val connectionId: String, val configId: String, val valueId: String)` |
| 360 | `data class ValidateGitHubTokenBody(val serverUrl: String, val token: String)` |
| 364 | `data class AccountTokenBody(val accountId: String, val token: String)` |
| 375 | `data class UpdateKimiCodeConfigBody( val mode: String, val interfaceType: String? = null, val authType: String? = null, val baseUrl: String? = null, val apiKey: String? = null, val model: String? = null, val maxContextSize: Int? = null, val vertexProject: String? = null, val vertexLocation: String? = null, val rawConfigToml: String? = null, )` |
| 376 |   `val mode: String` |
| 377 |   `val interfaceType: String?` |
| 378 |   `val authType: String?` |
| 379 |   `val baseUrl: String?` |
| 380 |   `val apiKey: String?` |
| 381 |   `val model: String?` |
| 382 |   `val maxContextSize: Int?` |
| 383 |   `val vertexProject: String?` |
| 384 |   `val vertexLocation: String?` |
| 385 |   `val rawConfigToml: String?` |
| 390 | `data class FetchKimiModelsBody(val baseUrl: String, val apiKey: String)` |
| 399 | `data class UpdatePiConfigBody( val provider: String, val model: String, val thinkingLevel: String? = null, val apiKey: String? = null, val customBaseUrl: String? = null, val customApi: String? = null, )` |
| 400 |   `val provider: String` |
| 401 |   `val model: String` |
| 402 |   `val thinkingLevel: String?` |
| 403 |   `val apiKey: String?` |
| 404 |   `val customBaseUrl: String?` |
| 405 |   `val customApi: String?` |
| 410 | `data class ValidatePiCommandBody(val command: String)` |
| 414 | `data class TaskIdBody(val taskId: String)` |
| 429 | `data class CursorStructuredConfig( val sandboxMode: String? = null, val permissionsAllow: List<String>? = null, val permissionsDeny: List<String>? = null, )` |
| 430 |   `val sandboxMode: String?` |
| 431 |   `val permissionsAllow: List<String>?` |
| 432 |   `val permissionsDeny: List<String>?` |

## core/model/wire/BranchManageRequests.kt

| Line | Declaration |
|---|---|
| 7 | `data class BranchManageListBody(val path: String)` |
| 9 | `data class BranchDeleteBody(val path: String, val branchName: String, val force: Boolean)` |
| 11 | `data class BranchRemoteDeleteBody(val path: String, val remote: String, val branch: String, val credentials: GitCredentials? = null)` |
| 13 | `data class BranchUpdateBody(val path: String, val branch: String, val isRemote: Boolean, val credentials: GitCredentials? = null)` |
| 15 | `data class BranchCompareBody(val path: String, val branch: String, val file: String? = null)` |

## core/model/wire/InstantSerializer.kt

| Line | Declaration |
|---|---|
| 21 | `object Rfc3339` |
| 22 |   `fun parse(raw: String): Instant?` |
| 38 | `object InstantSerializer : KSerializer<Instant>` |
| 39 |   `override val descriptor` |
| 42 |   `override fun serialize(encoder: Encoder, value: Instant)` |
| 46 |   `override fun deserialize(decoder: Decoder): Instant` |

## core/model/wire/JsonExt.kt

| Line | Declaration |
|---|---|
| 23 | `internal fun JsonObject.stringOrNull(key: String): String?` |
| 24 |   `val p` |
| 29 | `internal fun JsonObject.nonEmptyString(key: String): String?` |
| 32 | `internal fun JsonObject.intOrNull(key: String): Int?` |
| 35 | `internal fun JsonObject.longOrNull(key: String): Long?` |
| 38 | `internal fun JsonObject.boolOrNull(key: String): Boolean?` |
| 41 | `internal fun JsonObject.objectOrNull(key: String): JsonObject?` |
| 44 | `internal fun JsonObject.arrayOrNull(key: String): JsonArray?` |
| 47 | `internal fun JsonElement.asObjectOrNull(): JsonObject?` |

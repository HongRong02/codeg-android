# core/network

Source root: `app/src/main/java/app/codeg/android/`

## core/network/ApiError.kt

| Line | Declaration |
|---|---|
| 9 | `sealed class ApiError(message: String) : Exception(message)` |
| 11 |   `data class Transport(val detail: String) : ApiError(detail)` |
| 14 |   `data object Unauthorized : ApiError("Invalid or missing token")` |
| 17 |   `data object TurnInProgress : ApiError("A turn is already in progress")` |
| 20 |   `data class Server( val status: Int, val code: String?, val serverMessage: String, ) : ApiError(serverMessage)` |
| 27 |   `data class Decoding(val detail: String) : ApiError(detail)` |
| 32 | `data class ServerError( val code: String? = null, val message: String? = null, )` |
| 33 |   `val code: String?` |
| 34 |   `val message: String?` |
| 42 | `val ApiError.isStaleConnection: Boolean` |
| 53 | `val ApiError.isAuthFailure: Boolean` |

## core/network/CodegClient.kt

| Line | Declaration |
|---|---|
| 155 | `class CodegClient( rawBaseUrl: String, private val token: String, private val http: HttpClient, )` |
| 157 |   `private val token: String` |
| 158 |   `private val http: HttpClient` |
| 161 |   `val baseUrl: String` |
| 166 |   `suspend fun health(): HealthResponse` |
| 170 |   `suspend fun listFolders(): List<FolderDetail>` |
| 174 |   `suspend fun listOpenFolders(): List<FolderDetail>` |
| 178 |   `suspend fun listConversations( folderIds: List<Int>? = null, status: String? = null, search: String? = null, sortBy: String? = null, ): List<ConversationSummary>` |
| 200 |   `suspend fun conversationDetail(id: Int): ConversationDetail` |
| 207 |   `suspend fun createConversation(folderId: Int, agentType: AgentType, title: String?): Int` |
| 217 |   `suspend fun connect( agentType: AgentType, workingDir: String?, sessionId: String?, preferredModeId: String? = null, preferredConfigValues: Map<String, String>? = null, ): String` |
| 232 |   `suspend fun findConnection( conversationId: Int, sessionId: String?, agentType: AgentType, ): ConversationConnectionInfo?` |
| 246 |   `suspend fun prompt( connectionId: String, blocks: List<PromptInputBlock>, folderId: Int?, conversationId: Int?, clientMessageId: String?, )` |
| 257 |   `suspend fun cancel(connectionId: String)` |
| 262 |   `suspend fun respondPermission(connectionId: String, requestId: String, optionId: String)` |
| 267 |   `suspend fun answerQuestion(connectionId: String, questionId: String, answer: QuestionAnswer)` |
| 276 |   `suspend fun answerPlanApproval( connectionId: String, approvalId: String, decision: PlanApprovalDecision, feedback: String?, )` |
| 289 |   `suspend fun setPinned(conversationId: Int, pinned: Boolean)` |
| 294 |   `suspend fun renameConversation(conversationId: Int, title: String)` |
| 299 |   `suspend fun setStatus(conversationId: Int, status: String)` |
| 304 |   `suspend fun deleteConversation(conversationId: Int)` |
| 313 |   `suspend fun openFolder(path: String): FolderDetail` |
| 317 |   `suspend fun getHomeDirectory(): String` |
| 321 |   `suspend fun listDirectoryEntries(path: String): List<DirectoryEntry>` |
| 325 |   `suspend fun listDirectoryWithFiles(path: String): List<DirectoryItem>` |
| 329 |   `suspend fun readFilePreview(rootPath: String, path: String): FilePreviewContent` |
| 333 |   `suspend fun gitStatus(path: String, showAllUntracked: Boolean = true): List<GitStatusEntry>` |
| 337 |   `suspend fun gitLog(path: String, limit: Int? = null): GitLogResult` |
| 341 |   `suspend fun gitDiff(path: String, file: String? = null): String` |
| 345 |   `suspend fun gitShowDiff(path: String, commit: String, file: String? = null): String` |
| 349 |   `suspend fun cloneRepository(url: String, targetDir: String, credentials: GitCredentials?)` |
| 354 |   `suspend fun gitListAllBranches(path: String): GitBranchList` |
| 358 |   `suspend fun getGitBranch(path: String): String?` |
| 365 |   `suspend fun gitCheckout(path: String, branchName: String)` |
| 370 |   `suspend fun gitNewBranch(path: String, branchName: String, startPoint: String? = null)` |
| 375 |   `suspend fun resolveWorktreeFolder(repoPath: String, branch: String): WorktreeResolution` |
| 379 |   `suspend fun openWorktreeFolder(path: String, sourceFolderId: Int): FolderDetail` |
| 387 |   `suspend fun gitCommit(path: String, message: String, files: List<String>, folderId: Int? = null): GitCommitResult` |
| 395 |   `suspend fun gitPush(path: String, remote: String? = null, credentials: GitCredentials? = null, folderId: Int? = null): GitPushResult` |
| 399 |   `suspend fun gitPull(path: String, credentials: GitCredentials? = null): GitPullResult` |
| 403 |   `suspend fun gitFetch(path: String, credentials: GitCredentials? = null): String` |
| 407 |   `suspend fun gitPushInfo(path: String): GitPushInfo` |
| 411 |   `suspend fun gitListRemotes(path: String): List<GitRemote>` |
| 415 |   `suspend fun gitAddFiles(path: String, files: List<String>)` |
| 420 |   `suspend fun gitRollbackFile(path: String, file: String)` |
| 429 |   `suspend fun deleteFileTreeEntry(rootPath: String, path: String)` |
| 434 |   `private fun decodeBareString(text: String): String` |
| 440 |   `private fun <T> decodeCamel(text: String, deserializer: DeserializationStrategy<T>): T` |
| 449 |   `suspend fun quickMessagesList(): List<QuickMessage>` |
| 452 |   `suspend fun quickMessageCreate(title: String, content: String): QuickMessage` |
| 455 |   `suspend fun quickMessageUpdate(id: Int, title: String, content: String): QuickMessage` |
| 458 |   `suspend fun quickMessageDelete(id: Int)` |
| 462 |   `suspend fun quickMessagesReorder(ids: List<Int>)` |
| 467 |   `suspend fun getFeedbackEnabled(): Boolean` |
| 470 |   `suspend fun setFeedbackEnabled(enabled: Boolean)` |
| 475 |   `suspend fun getQuestionEnabled(): Boolean` |
| 478 |   `suspend fun setQuestionEnabled(enabled: Boolean)` |
| 483 |   `suspend fun getDelegationSettings(): JsonObject` |
| 487 |   `suspend fun setDelegationSettings(settings: JsonObject)` |
| 493 |   `suspend fun listModelProviders(): List<ModelProviderInfo>` |
| 496 |   `suspend fun createModelProvider(name: String, apiUrl: String, apiKey: String, agentType: AgentType, model: String?)` |
| 500 |   `suspend fun updateModelProvider(id: Int, name: String?, apiUrl: String?, apiKey: String?, agentType: AgentType?, model: String?)` |
| 504 |   `suspend fun deleteModelProvider(id: Int)` |
| 509 |   `suspend fun getSystemProxySettings(): SystemProxySettings` |
| 512 |   `suspend fun updateSystemProxySettings(enabled: Boolean, proxyUrl: String?)` |
| 523 |   `suspend fun checkAppUpdate(): AppUpdateCheckResult` |
| 527 |   `suspend fun mcpScanLocal(): List<LocalMcpServer>` |
| 535 |   `suspend fun mcpUpsertLocalServer(serverId: String, spec: JsonElement, apps: List<String>)` |
| 544 |   `suspend fun mcpRemoveServer(serverId: String, apps: List<String>? = null)` |
| 561 |   `suspend fun acpListAgents(): List<AcpAgentInfo>` |
| 572 |   `suspend fun acpUpdateAgentEnv(agentType: AgentType, enabled: Boolean, env: Map<String, String>, modelProviderId: Int?)` |
| 595 |   `suspend fun acpUpdateAgentConfig( agentType: AgentType, configJson: String?, opencodeAuthJson: String?, codexAuthJson: String?, codexConfigToml: String?, grokConfigToml: String? = null, cursorCliConfigJson: String? = null, cursorStructured: CursorStructuredConfig? = null, )` |
| 637 |   `suspend fun acpCursorAuthStatus(apiKey: String): CursorAuthStatus` |
| 646 |   `suspend fun acpCursorListModels(apiKey: String): CursorModelsResult` |
| 657 |   `suspend fun acpUpdateHermesConfig( provider: String, model: String?, apiKey: FieldEdit, baseUrl: FieldEdit, rawConfigYaml: String? = null, )` |
| 683 |   `suspend fun acpUpdateKimiCodeConfig(body: UpdateKimiCodeConfigBody): Int` |
| 687 |   `suspend fun acpFetchKimiModels(baseUrl: String, apiKey: String): List<String>` |
| 694 |   `suspend fun acpLoadPiConfig(): PiConfigProjection` |
| 698 |   `suspend fun acpUpdatePiConfig(body: UpdatePiConfigBody)` |
| 703 |   `suspend fun acpValidatePiCommand(command: String): PiCommandValidation` |
| 707 |   `suspend fun acpInstallPiBinary(taskId: String)` |
| 711 |   `suspend fun acpUninstallPiBinary(taskId: String)` |
| 716 |   `suspend fun detectGit(): GitDetectResult` |
| 719 |   `suspend fun testGitPath(path: String): GitDetectResult` |
| 722 |   `suspend fun getGitSettings(): GitSettings` |
| 725 |   `suspend fun updateGitSettings(customPath: String?)` |
| 731 |   `suspend fun getGitHubAccounts(): List<GitHubAccount>` |
| 735 |   `suspend fun validateGithubToken(serverUrl: String, token: String): GitHubTokenValidation` |
| 739 |   `suspend fun saveAccountToken(accountId: String, token: String)` |
| 749 |   `suspend fun updateGithubAccounts(accounts: List<GitHubAccount>)` |
| 771 |   `suspend fun listAgentSkills(agentType: AgentType): AgentSkillsListResult` |
| 774 |   `suspend fun readAgentSkill(agentType: AgentType, scope: String, skillId: String): AgentSkillContent` |
| 777 |   `suspend fun saveAgentSkill(agentType: AgentType, scope: String, skillId: String, content: String, layout: String?)` |
| 781 |   `suspend fun deleteAgentSkill(agentType: AgentType, scope: String, skillId: String)` |
| 786 |   `suspend fun expertsList(): List<ExpertListItem>` |
| 790 |   `suspend fun expertsListForAgent(agentType: AgentType): List<ExpertListItem>` |
| 800 |   `suspend fun expertReadContent(expertId: String): String` |
| 805 |   `suspend fun expertsListAllInstallStatuses(): List<ExpertInstallStatus>` |
| 808 |   `suspend fun expertInstallStatus(expertId: String): List<ExpertInstallStatus>` |
| 812 |   `suspend fun expertLink(expertId: String, agentType: AgentType): ExpertInstallStatus` |
| 815 |   `suspend fun expertUnlink(expertId: String, agentType: AgentType)` |
| 821 |   `suspend fun listChatChannels(): List<ChatChannelInfo>` |
| 824 |   `suspend fun chatChannelStatus(): List<ChannelStatusInfo>` |
| 827 |   `suspend fun createChatChannel( name: String, channelType: ChannelType, configJson: String, enabled: Boolean, dailyReportEnabled: Boolean, dailyReportTime: String?, ): ChatChannelInfo` |
| 835 |   `suspend fun updateChatChannel(body: UpdateChatChannelBody): ChatChannelInfo` |
| 851 |   `suspend fun deleteChatChannel(id: Int)` |
| 852 |   `suspend fun connectChatChannel(id: Int)` |
| 853 |   `suspend fun disconnectChatChannel(id: Int)` |
| 854 |   `suspend fun testChatChannel(id: Int)` |
| 856 |   `suspend fun saveChatChannelToken(channelId: Int, token: String)` |
| 860 |   `suspend fun chatChannelHasToken(channelId: Int): Boolean` |
| 863 |   `suspend fun deleteChatChannelToken(channelId: Int)` |
| 867 |   `suspend fun listChatChannelMessages(channelId: Int, limit: Int? = 50, offset: Int? = null): List<ChatChannelMessageLog>` |
| 870 |   `suspend fun weixinGetQrcode(): WeixinQrcode` |
| 874 |   `suspend fun weixinCheckQrcode(channelId: Int, qrcode: String): String` |
| 879 |   `suspend fun chatCommandPrefix(): String` |
| 882 |   `suspend fun setChatCommandPrefix(prefix: String)` |
| 884 |   `suspend fun chatMessageLanguage(): String` |
| 887 |   `suspend fun setChatMessageLanguage(language: String)` |
| 890 |   `suspend fun chatEventFilter(): List<String>?` |
| 893 |   `suspend fun setChatEventFilter(filter: List<String>?)` |
| 901 |   `suspend fun chatEventWebhooks(): List<WebhookConfig>` |
| 904 |   `suspend fun setChatEventWebhooks(webhooks: List<WebhookConfig>)` |
| 913 |   `suspend fun sessionSnapshotByConversation(conversationId: Int): SessionSnapshot?` |
| 920 |   `suspend fun sessionSnapshot(connectionId: String): SessionSnapshot?` |
| 927 |   `suspend fun describeAgentOptions(agentType: AgentType, workingDir: String?): AgentOptionsSnapshot` |
| 930 |   `suspend fun acpSetMode(connectionId: String, modeId: String)` |
| 934 |   `suspend fun acpSetConfigOption(connectionId: String, configId: String, valueId: String)` |
| 944 |   `suspend fun readFileBase64(path: String, maxBytes: Long = 10_485_760): String` |
| 947 |   `suspend fun gitShowFile(path: String, file: String, refName: String): String` |
| 950 |   `suspend fun gitShowFileBase64(path: String, file: String, refName: String): app.codeg.android.core.model.GitFileImage` |
| 961 |   `private suspend fun send(path: String, bodyJson: String): String` |
| 995 |   `private inline fun <reified T> encode(body: T): String` |
| 998 |   `private fun <T> decode(text: String, deserializer: DeserializationStrategy<T>): T` |
| 1004 |   `companion object` |
| 1006 |     `fun normalizeBaseUrl(raw: String): String` |
| 1014 |     `fun decodeConnectionId(text: String): String` |
| 1030 |     `fun decodeAgentList(text: String): List<AcpAgentInfo>` |
| 1047 |     `fun isJsonNull(text: String): Boolean` |

## core/network/CodegClientFactory.kt

| Line | Declaration |
|---|---|
| 23 | `class CodegClientFactory @Inject constructor( private val http: Lazy<HttpClient>, )` |
| 24 |   `private val http: Lazy<HttpClient>` |
| 26 |   `fun client(baseUrl: String, token: String): CodegClient` |
| 29 |   `fun eventStream(baseUrl: String, token: String): EventStream` |

## core/network/CodegJson.kt

| Line | Declaration |
|---|---|
| 24 | `object CodegJson` |
| 25 |   `val request: Json` |
| 34 |   `val response: Json` |

## core/network/ErrorMessages.kt

| Line | Declaration |
|---|---|
| 4 | `fun Throwable.displayMessage(): String` |

## core/network/EventStream.kt

| Line | Declaration |
|---|---|
| 40 | `class EventStream( rawBaseUrl: String, token: String, private val http: HttpClient, )` |
| 43 |   `private val http: HttpClient` |
| 45 |   `private val url` |
| 46 |   `private val subprotocols` |
| 49 |   `private val outbox` |
| 51 |   `fun attach(subscriptionId: String, connectionId: String, sinceSeq: Long? = null)` |
| 55 |   `fun detach(subscriptionId: String)` |
| 59 |   `fun ping()` |
| 68 |   `fun frames(): Flow<StreamFrame>` |
| 102 |   `companion object` |
| 112 |     `fun parseFrame(text: String, json: Json): StreamFrame?` |
| 143 |     `fun websocketUrl(rawBaseUrl: String): String` |

## core/network/WsFrames.kt

| Line | Declaration |
|---|---|
| 13 | `object WsClientMessage` |
| 14 |   `fun attach(subscriptionId: String, connectionId: String, sinceSeq: Long?): String` |
| 22 |   `fun detach(subscriptionId: String): String` |
| 28 |   `fun ping(): String` |
| 36 | `sealed interface StreamFrame` |
| 38 |   `data object Ready : StreamFrame` |
| 39 |   `data class Snapshot(val snapshot: LiveSessionSnapshot) : StreamFrame` |
| 40 |   `data class Replay(val events: List<EventEnvelope>) : StreamFrame` |
| 41 |   `data class Event(val envelope: EventEnvelope) : StreamFrame` |
| 42 |   `data class Detached(val reason: String) : StreamFrame` |
| 43 |   `data object Pong : StreamFrame` |
| 45 |   `data class Closed(val reason: String?) : StreamFrame` |

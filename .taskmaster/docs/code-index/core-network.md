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
| 160 | `class CodegClient( rawBaseUrl: String, private val token: String, private val http: HttpClient, )` |
| 162 |   `private val token: String` |
| 163 |   `private val http: HttpClient` |
| 166 |   `val baseUrl: String` |
| 171 |   `suspend fun health(): HealthResponse` |
| 175 |   `suspend fun listFolders(): List<FolderDetail>` |
| 179 |   `suspend fun listOpenFolders(): List<FolderDetail>` |
| 183 |   `suspend fun listConversations( folderIds: List<Int>? = null, status: String? = null, search: String? = null, sortBy: String? = null, ): List<ConversationSummary>` |
| 205 |   `suspend fun conversationDetail(id: Int): ConversationDetail` |
| 212 |   `suspend fun createConversation(folderId: Int, agentType: AgentType, title: String?): Int` |
| 222 |   `suspend fun connect( agentType: AgentType, workingDir: String?, sessionId: String?, preferredModeId: String? = null, preferredConfigValues: Map<String, String>? = null, ): String` |
| 237 |   `suspend fun findConnection( conversationId: Int, sessionId: String?, agentType: AgentType, ): ConversationConnectionInfo?` |
| 251 |   `suspend fun prompt( connectionId: String, blocks: List<PromptInputBlock>, folderId: Int?, conversationId: Int?, clientMessageId: String?, )` |
| 262 |   `suspend fun cancel(connectionId: String)` |
| 267 |   `suspend fun respondPermission(connectionId: String, requestId: String, optionId: String)` |
| 272 |   `suspend fun answerQuestion(connectionId: String, questionId: String, answer: QuestionAnswer)` |
| 281 |   `suspend fun answerPlanApproval( connectionId: String, approvalId: String, decision: PlanApprovalDecision, feedback: String?, )` |
| 294 |   `suspend fun setPinned(conversationId: Int, pinned: Boolean)` |
| 299 |   `suspend fun renameConversation(conversationId: Int, title: String)` |
| 304 |   `suspend fun setStatus(conversationId: Int, status: String)` |
| 309 |   `suspend fun deleteConversation(conversationId: Int)` |
| 318 |   `suspend fun openFolder(path: String): FolderDetail` |
| 322 |   `suspend fun getHomeDirectory(): String` |
| 326 |   `suspend fun listDirectoryEntries(path: String): List<DirectoryEntry>` |
| 330 |   `suspend fun listDirectoryWithFiles(path: String): List<DirectoryItem>` |
| 334 |   `suspend fun readFilePreview(rootPath: String, path: String): FilePreviewContent` |
| 338 |   `suspend fun gitStatus(path: String, showAllUntracked: Boolean = true): List<GitStatusEntry>` |
| 342 |   `suspend fun gitLog(path: String, limit: Int? = null): GitLogResult` |
| 346 |   `suspend fun gitDiff(path: String, file: String? = null): String` |
| 350 |   `suspend fun gitShowDiff(path: String, commit: String, file: String? = null): String` |
| 354 |   `suspend fun cloneRepository(url: String, targetDir: String, credentials: GitCredentials?)` |
| 359 |   `suspend fun gitListAllBranches(path: String): GitBranchList` |
| 363 |   `suspend fun getGitBranch(path: String): String?` |
| 370 |   `suspend fun gitCheckout(path: String, branchName: String)` |
| 375 |   `suspend fun gitNewBranch(path: String, branchName: String, startPoint: String? = null)` |
| 380 |   `suspend fun resolveWorktreeFolder(repoPath: String, branch: String): WorktreeResolution` |
| 384 |   `suspend fun openWorktreeFolder(path: String, sourceFolderId: Int): FolderDetail` |
| 392 |   `suspend fun gitCommit(path: String, message: String, files: List<String>, folderId: Int? = null): GitCommitResult` |
| 400 |   `suspend fun gitPush(path: String, remote: String? = null, credentials: GitCredentials? = null, folderId: Int? = null): GitPushResult` |
| 404 |   `suspend fun gitPull(path: String, credentials: GitCredentials? = null): GitPullResult` |
| 408 |   `suspend fun gitFetch(path: String, credentials: GitCredentials? = null): String` |
| 412 |   `suspend fun gitPushInfo(path: String): GitPushInfo` |
| 416 |   `suspend fun gitListRemotes(path: String): List<GitRemote>` |
| 420 |   `suspend fun gitAddFiles(path: String, files: List<String>)` |
| 425 |   `suspend fun gitRollbackFile(path: String, file: String)` |
| 434 |   `suspend fun deleteFileTreeEntry(rootPath: String, path: String)` |
| 439 |   `private fun decodeBareString(text: String): String` |
| 445 |   `private fun <T> decodeCamel(text: String, deserializer: DeserializationStrategy<T>): T` |
| 454 |   `suspend fun quickMessagesList(): List<QuickMessage>` |
| 457 |   `suspend fun quickMessageCreate(title: String, content: String): QuickMessage` |
| 460 |   `suspend fun quickMessageUpdate(id: Int, title: String, content: String): QuickMessage` |
| 463 |   `suspend fun quickMessageDelete(id: Int)` |
| 467 |   `suspend fun quickMessagesReorder(ids: List<Int>)` |
| 472 |   `suspend fun getFeedbackEnabled(): Boolean` |
| 475 |   `suspend fun setFeedbackEnabled(enabled: Boolean)` |
| 480 |   `suspend fun getQuestionEnabled(): Boolean` |
| 483 |   `suspend fun setQuestionEnabled(enabled: Boolean)` |
| 488 |   `suspend fun getDelegationSettings(): JsonObject` |
| 492 |   `suspend fun setDelegationSettings(settings: JsonObject)` |
| 498 |   `suspend fun listModelProviders(): List<ModelProviderInfo>` |
| 501 |   `suspend fun createModelProvider(name: String, apiUrl: String, apiKey: String, agentType: AgentType, model: String?)` |
| 505 |   `suspend fun updateModelProvider(id: Int, name: String?, apiUrl: String?, apiKey: String?, agentType: AgentType?, model: String?)` |
| 509 |   `suspend fun deleteModelProvider(id: Int)` |
| 514 |   `suspend fun getSystemProxySettings(): SystemProxySettings` |
| 517 |   `suspend fun updateSystemProxySettings(enabled: Boolean, proxyUrl: String?)` |
| 528 |   `suspend fun checkAppUpdate(): AppUpdateCheckResult` |
| 532 |   `suspend fun mcpScanLocal(): List<LocalMcpServer>` |
| 540 |   `suspend fun mcpUpsertLocalServer(serverId: String, spec: JsonElement, apps: List<String>)` |
| 549 |   `suspend fun mcpRemoveServer(serverId: String, apps: List<String>? = null)` |
| 566 |   `suspend fun acpListAgents(): List<AcpAgentInfo>` |
| 577 |   `suspend fun acpUpdateAgentEnv(agentType: AgentType, enabled: Boolean, env: Map<String, String>, modelProviderId: Int?)` |
| 600 |   `suspend fun acpUpdateAgentConfig( agentType: AgentType, configJson: String?, opencodeAuthJson: String?, codexAuthJson: String?, codexConfigToml: String?, grokConfigToml: String? = null, cursorCliConfigJson: String? = null, cursorStructured: CursorStructuredConfig? = null, )` |
| 642 |   `suspend fun acpCursorAuthStatus(apiKey: String): CursorAuthStatus` |
| 651 |   `suspend fun acpCursorListModels(apiKey: String): CursorModelsResult` |
| 662 |   `suspend fun acpUpdateHermesConfig( provider: String, model: String?, apiKey: FieldEdit, baseUrl: FieldEdit, rawConfigYaml: String? = null, )` |
| 688 |   `suspend fun acpUpdateKimiCodeConfig(body: UpdateKimiCodeConfigBody): Int` |
| 692 |   `suspend fun acpFetchKimiModels(baseUrl: String, apiKey: String): List<String>` |
| 699 |   `suspend fun acpLoadPiConfig(): PiConfigProjection` |
| 703 |   `suspend fun acpUpdatePiConfig(body: UpdatePiConfigBody)` |
| 708 |   `suspend fun acpValidatePiCommand(command: String): PiCommandValidation` |
| 712 |   `suspend fun acpInstallPiBinary(taskId: String)` |
| 716 |   `suspend fun acpUninstallPiBinary(taskId: String)` |
| 721 |   `suspend fun detectGit(): GitDetectResult` |
| 724 |   `suspend fun testGitPath(path: String): GitDetectResult` |
| 727 |   `suspend fun getGitSettings(): GitSettings` |
| 730 |   `suspend fun updateGitSettings(customPath: String?)` |
| 736 |   `suspend fun getGitHubAccounts(): List<GitHubAccount>` |
| 740 |   `suspend fun validateGithubToken(serverUrl: String, token: String): GitHubTokenValidation` |
| 744 |   `suspend fun saveAccountToken(accountId: String, token: String)` |
| 754 |   `suspend fun updateGithubAccounts(accounts: List<GitHubAccount>)` |
| 776 |   `suspend fun listAgentSkills(agentType: AgentType): AgentSkillsListResult` |
| 779 |   `suspend fun readAgentSkill(agentType: AgentType, scope: String, skillId: String): AgentSkillContent` |
| 782 |   `suspend fun saveAgentSkill(agentType: AgentType, scope: String, skillId: String, content: String, layout: String?)` |
| 786 |   `suspend fun deleteAgentSkill(agentType: AgentType, scope: String, skillId: String)` |
| 791 |   `suspend fun expertsList(): List<ExpertListItem>` |
| 795 |   `suspend fun expertsListForAgent(agentType: AgentType): List<ExpertListItem>` |
| 805 |   `suspend fun expertReadContent(expertId: String): String` |
| 810 |   `suspend fun expertsListAllInstallStatuses(): List<ExpertInstallStatus>` |
| 813 |   `suspend fun expertInstallStatus(expertId: String): List<ExpertInstallStatus>` |
| 817 |   `suspend fun expertLink(expertId: String, agentType: AgentType): ExpertInstallStatus` |
| 820 |   `suspend fun expertUnlink(expertId: String, agentType: AgentType)` |
| 826 |   `suspend fun listChatChannels(): List<ChatChannelInfo>` |
| 829 |   `suspend fun chatChannelStatus(): List<ChannelStatusInfo>` |
| 832 |   `suspend fun createChatChannel( name: String, channelType: ChannelType, configJson: String, enabled: Boolean, dailyReportEnabled: Boolean, dailyReportTime: String?, ): ChatChannelInfo` |
| 840 |   `suspend fun updateChatChannel(body: UpdateChatChannelBody): ChatChannelInfo` |
| 856 |   `suspend fun deleteChatChannel(id: Int)` |
| 857 |   `suspend fun connectChatChannel(id: Int)` |
| 858 |   `suspend fun disconnectChatChannel(id: Int)` |
| 859 |   `suspend fun testChatChannel(id: Int)` |
| 861 |   `suspend fun saveChatChannelToken(channelId: Int, token: String)` |
| 865 |   `suspend fun chatChannelHasToken(channelId: Int): Boolean` |
| 868 |   `suspend fun deleteChatChannelToken(channelId: Int)` |
| 872 |   `suspend fun listChatChannelMessages(channelId: Int, limit: Int? = 50, offset: Int? = null): List<ChatChannelMessageLog>` |
| 875 |   `suspend fun weixinGetQrcode(): WeixinQrcode` |
| 879 |   `suspend fun weixinCheckQrcode(channelId: Int, qrcode: String): String` |
| 884 |   `suspend fun chatCommandPrefix(): String` |
| 887 |   `suspend fun setChatCommandPrefix(prefix: String)` |
| 889 |   `suspend fun chatMessageLanguage(): String` |
| 892 |   `suspend fun setChatMessageLanguage(language: String)` |
| 895 |   `suspend fun chatEventFilter(): List<String>?` |
| 898 |   `suspend fun setChatEventFilter(filter: List<String>?)` |
| 906 |   `suspend fun chatEventWebhooks(): List<WebhookConfig>` |
| 909 |   `suspend fun setChatEventWebhooks(webhooks: List<WebhookConfig>)` |
| 918 |   `suspend fun sessionSnapshotByConversation(conversationId: Int): SessionSnapshot?` |
| 925 |   `suspend fun sessionSnapshot(connectionId: String): SessionSnapshot?` |
| 932 |   `suspend fun describeAgentOptions(agentType: AgentType, workingDir: String?): AgentOptionsSnapshot` |
| 935 |   `suspend fun acpSetMode(connectionId: String, modeId: String)` |
| 939 |   `suspend fun acpSetConfigOption(connectionId: String, configId: String, valueId: String)` |
| 949 |   `suspend fun forkSession(connectionId: String, conversationId: Int?, folderId: Int?): app.codeg.android.core.model.SessionForkResult` |
| 953 |   `suspend fun disconnectSession(connectionId: String)` |
| 957 |   `suspend fun preflightSession(agentType: AgentType): app.codeg.android.core.model.SessionPreflightResult` |
| 965 |   `suspend fun readFileBase64(path: String, maxBytes: Long = 10_485_760): String` |
| 968 |   `suspend fun gitShowFile(path: String, file: String, refName: String): String` |
| 971 |   `suspend fun gitShowFileBase64(path: String, file: String, refName: String): app.codeg.android.core.model.GitFileImage` |
| 977 |   `suspend fun gitListManagedBranches(path: String): List<app.codeg.android.core.model.BranchManageEntry>` |
| 982 |   `suspend fun gitDeleteBranch(path: String, branch: String, force: Boolean): String` |
| 985 |   `suspend fun gitDeleteRemoteBranch(path: String, remote: String, branch: String, credentials: GitCredentials? = null)` |
| 989 |   `suspend fun gitUpdateBranch(path: String, branch: String, isRemote: Boolean, credentials: GitCredentials? = null): GitPullResult` |
| 992 |   `suspend fun gitDiffWithBranch(path: String, branch: String): String` |
| 998 |   `suspend fun gitMerge(path: String, branch: String)` |
| 1001 |   `suspend fun gitRebase(path: String, branch: String)` |
| 1004 |   `suspend fun gitReset(path: String, commit: String, mode: String)` |
| 1007 |   `suspend fun gitHasMergeHead(path: String): Boolean` |
| 1009 |   `suspend fun gitListConflicts(path: String): List<String>` |
| 1011 |   `suspend fun gitConflictFileVersions(path: String, file: String): app.codeg.android.core.model.MergeVersions` |
| 1013 |   `suspend fun gitResolveConflict(path: String, file: String, content: String)` |
| 1016 |   `suspend fun gitContinueOperation(path: String, operation: String)` |
| 1019 |   `suspend fun gitAbortOperation(path: String, operation: String)` |
| 1028 |   `suspend fun gitAddRemote(path: String, name: String, url: String)` |
| 1032 |   `suspend fun gitRemoveRemote(path: String, name: String)` |
| 1036 |   `suspend fun gitSetRemoteUrl(path: String, name: String, url: String)` |
| 1040 |   `suspend fun gitWorktreeAdd(path: String, branchName: String, worktreePath: String, base: String? = null)` |
| 1044 |   `suspend fun gitRemoveWorktree(path: String, branchName: String, sourceFolderId: Int)` |
| 1048 |   `suspend fun gitInit(path: String)` |
| 1050 |   `suspend fun gitFetchRemote(path: String, name: String, credentials: GitCredentials? = null): String` |
| 1057 |   `suspend fun automationList(): List<Automation>` |
| 1060 |   `suspend fun automationGet(id: Int): Automation` |
| 1063 |   `suspend fun automationRuns(automationId: Int, limit: Long = 100): List<AutomationRun>` |
| 1066 |   `suspend fun automationSetEnabled(id: Int, enabled: Boolean): Automation` |
| 1069 |   `suspend fun automationCreate(draft: app.codeg.android.core.model.wire.AutomationDraft): Automation` |
| 1071 |   `suspend fun automationUpdate(id: Int, draft: app.codeg.android.core.model.wire.AutomationDraft): Automation` |
| 1073 |   `suspend fun automationDelete(id: Int)` |
| 1074 |   `suspend fun automationMarkSeen()` |
| 1075 |   `suspend fun automationComputeNextRun(cron: String, timezone: String): String` |
| 1077 |   `suspend fun automationRunNow(id: Int)` |
| 1078 |   `suspend fun automationCancelRun(id: Int)` |
| 1083 |   `suspend fun gitStashList(path: String): List<app.codeg.android.core.model.StashEntry>` |
| 1086 |   `suspend fun gitStashShow(path: String, ref: String): List<GitStatusEntry>` |
| 1089 |   `suspend fun gitStashPush(path: String, message: String?, keepIndex: Boolean): String` |
| 1092 |   `suspend fun gitStashApply(path: String, ref: String): String` |
| 1095 |   `suspend fun gitStashPop(path: String, ref: String): String` |
| 1098 |   `suspend fun gitStashDrop(path: String, ref: String): String` |
| 1101 |   `suspend fun gitStashClear(path: String): String` |
| 1108 |   `suspend fun workTaskList(): List<app.codeg.android.core.model.WorkTask>` |
| 1111 |   `suspend fun workTaskAttentionCount(): Long` |
| 1114 |   `suspend fun workTaskGet(id: Int): app.codeg.android.core.model.WorkTask` |
| 1117 |   `suspend fun workTaskEvents(id: Int): List<app.codeg.android.core.model.WorkTaskEvent>` |
| 1120 |   `suspend fun workTaskChangedFiles(id: Int): List<app.codeg.android.core.model.WorkTaskChangedFile>` |
| 1123 |   `suspend fun workTaskDiff(id: Int, file: String? = null): String` |
| 1126 |   `suspend fun workTaskSave(body: app.codeg.android.core.model.wire.WorkTaskSaveBody): app.codeg.android.core.model.WorkTask` |
| 1129 |   `suspend fun workTaskStartAll()` |
| 1131 |   `suspend fun workTaskReorder(body: app.codeg.android.core.model.wire.WorkTaskReorderBody)` |
| 1135 |   `suspend fun workTaskAction(action: String, body: app.codeg.android.core.model.wire.WorkTaskActionBody)` |
| 1141 |   `suspend fun workTaskTemplates(): List<app.codeg.android.core.model.WorkTaskTemplate>` |
| 1148 |   `suspend fun recentServerLogs(minLevel: String? = null): List<app.codeg.android.core.model.ServerLogRecord>` |
| 1152 |   `suspend fun serverLogFiles(): List<app.codeg.android.core.model.ServerLogFile>` |
| 1155 |   `suspend fun readServerLog(name: String): String` |
| 1158 |   `suspend fun serverConversationStats(): app.codeg.android.core.model.ServerConversationStats` |
| 1161 |   `suspend fun serverTokenReport(filter: app.codeg.android.core.model.wire.ServerTokenFilter): app.codeg.android.core.model.ServerTokenReport` |
| 1169 |   `suspend fun forgeRemote(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeRemote?` |
| 1172 |   `suspend fun forgeIssues(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeIssueList` |
| 1175 |   `suspend fun forgeLabels(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeLabelList` |
| 1178 |   `suspend fun forgeComments(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeCommentList` |
| 1181 |   `suspend fun forgeComment(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeComment` |
| 1184 |   `suspend fun forgeState(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeIssueRow` |
| 1187 |   `suspend fun forgeCreateIssue(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeIssueRow` |
| 1190 |   `suspend fun forgeDetail(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeChangeDetail` |
| 1193 |   `suspend fun forgeFiles(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeChangedFileList` |
| 1196 |   `suspend fun forgeMergeOptions(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeMergeOptions` |
| 1199 |   `suspend fun forgeMerge(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeIssueRow?` |
| 1202 |   `suspend fun forgeCreateTask(body: kotlinx.serialization.json.JsonObject): app.codeg.android.core.model.ForgeCreateResult` |
| 1205 |   `suspend fun forgeCount(body: kotlinx.serialization.json.JsonObject): Long?` |
| 1208 |   `suspend fun forgeLookup(body: kotlinx.serialization.json.JsonObject): List<app.codeg.android.core.model.ForgeTaskLink>` |
| 1215 |   `suspend fun listConversationTags(): List<app.codeg.android.core.model.ConversationTag>` |
| 1219 |   `suspend fun createConversationTag(name: String, color: String): app.codeg.android.core.model.ConversationTag` |
| 1224 |   `suspend fun updateConversationTag(id: Int, name: String, color: String): app.codeg.android.core.model.ConversationTag` |
| 1229 |   `suspend fun deleteConversationTag(id: Int)` |
| 1233 |   `suspend fun updateConversationTags(id: Int, add: List<Int>, remove: List<Int>): ConversationSummary` |
| 1242 |   `suspend fun listFolderGroups(): List<app.codeg.android.core.model.FolderGroup>` |
| 1245 |   `suspend fun createFolderGroup(name: String)` |
| 1249 |   `suspend fun updateFolderGroup(groupId: Int, name: String)` |
| 1253 |   `suspend fun deleteFolderGroup(groupId: Int)` |
| 1257 |   `suspend fun setFolderGroup(folderId: Int, groupId: Int?)` |
| 1261 |   `suspend fun updateFolderAlias(folderId: Int, alias: String?)` |
| 1265 |   `suspend fun updateFolderColor(folderId: Int, color: String)` |
| 1273 |   `suspend fun assistantList(): List<app.codeg.android.core.model.Assistant>` |
| 1276 |   `suspend fun assistantCreate(draft: app.codeg.android.core.model.wire.AssistantDraft): app.codeg.android.core.model.Assistant` |
| 1279 |   `suspend fun assistantUpdate(id: Int, patch: app.codeg.android.core.model.wire.AssistantDraft): app.codeg.android.core.model.Assistant` |
| 1282 |   `suspend fun assistantDelete(id: Int)` |
| 1286 |   `suspend fun assistantReorder(ids: List<Int>): List<app.codeg.android.core.model.Assistant>` |
| 1297 |   `private suspend fun send(path: String, bodyJson: String): String` |
| 1331 |   `private inline fun <reified T> encode(body: T): String` |
| 1334 |   `private fun <T> decode(text: String, deserializer: DeserializationStrategy<T>): T` |
| 1340 |   `companion object` |
| 1342 |     `fun normalizeBaseUrl(raw: String): String` |
| 1350 |     `fun decodeConnectionId(text: String): String` |
| 1366 |     `fun decodeAgentList(text: String): List<AcpAgentInfo>` |
| 1383 |     `fun isJsonNull(text: String): Boolean` |

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
| 7 | `fun Throwable.displayMessage(context: Context): String` |
| 9 | `internal fun Throwable.displayMessage(stringResource: (Int) -> String): String` |
| 20 | `fun Throwable.displayMessage(): String` |

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

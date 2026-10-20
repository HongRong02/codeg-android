# feature/sessiondetail

Source root: `app/src/main/java/app/codeg/android/`

## feature/sessiondetail/AgentOptionsSheet.kt

| Line | Declaration |
|---|---|
| 78 | `private enum class SheetPage` |
| 83 | `private const val MODE_APPLY` |
| 94 | `@Composable fun AgentOptionsSheet( viewModel: SessionDetailViewModel, onOpenSession: (Int) -> Unit, onDismiss: () -> Unit, )` |
| 99 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 100 |   `val colors` |
| 101 |   `val sheetState` |
| 102 |   `val scope` |
| 103 |   `var page by remember` |
| 105 |   `var loading by remember` |
| 106 |   `var data by remember` |
| 107 |   `var currentMode by remember` |
| 108 |   `var currentValues by remember` |
| 109 |   `var note by remember` |
| 112 |   `var applying by remember` |
| 113 |   `val applyFailedNote` |
| 242 | `@Composable private fun AgentPickerSection(agents: List<AgentType>, selected: AgentType, onSelect: (AgentType) -> Unit)` |
| 243 |   `val colors` |
| 283 | `@Composable private fun WorkspaceSection( ui: SessionDetailUiState, onSelectFolder: (FolderDetail) -> Unit, onOpenBranch: () -> Unit, )` |
| 288 |   `val colors` |
| 300 | `@Composable private fun FolderMenuRow(ui: SessionDetailUiState, onSelectFolder: (FolderDetail) -> Unit)` |
| 301 |   `val colors` |
| 302 |   `var expanded by remember` |
| 333 | `@Composable private fun FolderReadOnlyRow(ui: SessionDetailUiState)` |
| 334 |   `val colors` |
| 349 | `@Composable private fun BranchRow(current: String?, isBusy: Boolean, onClick: () -> Unit)` |
| 350 |   `val colors` |
| 372 | `@Composable private fun PathLine(path: String)` |
| 381 | `@Composable private fun BranchPickerContent( viewModel: SessionDetailViewModel, onBack: () -> Unit, onOpenSession: (Int) -> Unit, )` |
| 386 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 387 |   `val colors` |
| 388 |   `val scope` |
| 389 |   `var loading by remember` |
| 390 |   `var list by remember` |
| 391 |   `var query by remember` |
| 392 |   `var switching by remember` |
| 393 |   `var showNew by remember` |
| 394 |   `var newName by remember` |
| 395 |   `var creating by remember` |
| 396 |   `val isBusy` |
| 397 |   `val current` |
| 404 |   `fun pick(display: String, target: String, isRemote: Boolean)` |
| 418 |   `val worktree` |
| 419 |   `val locals` |
| 420 |   `val remotes` |
| 487 | `private fun String.matches(query: String): Boolean` |
| 488 |   `val q` |
| 493 | `@Composable private fun NewBranchInline( show: Boolean, name: String, current: String?, creating: Boolean, onShow: () -> Unit, onName: (String) -> Unit, onCancel: () -> Unit, onCreate: () -> Unit, )` |
| 503 |   `val colors` |
| 536 | `@Composable private fun BranchRowItem( display: String, target: String, isRemote: Boolean, occupied: Boolean, isCurrent: Boolean, switching: Boolean, enabled: Boolean, onClick: () -> Unit, )` |
| 546 |   `val colors` |
| 569 | `@Composable private fun BranchSectionLabel(text: String)` |
| 578 | `@Composable private fun ConfigOptionSection(option: SessionConfigOption, currentValue: String?, optionApplying: Boolean, locked: Boolean, onSelect: (String) -> Unit)` |
| 579 |   `val colors` |
| 580 |   `val flat: List<SessionConfigSelectOption>` |
| 595 | `@Composable private fun OptionGroup(content: @Composable () -> Unit)` |
| 600 | `@Composable private fun GroupDivider()` |
| 605 | `@Composable private fun OptionRow(title: String, description: String?, selected: Boolean, busy: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 606 |   `val colors` |

## feature/sessiondetail/Attachment.kt

| Line | Declaration |
|---|---|
| 20 | `data class Attachment( val id: String, val base64: String, val mimeType: String, )` |
| 21 |   `val id: String` |
| 22 |   `val base64: String` |
| 23 |   `val mimeType: String` |
| 25 |   `val promptBlock: PromptInputBlock get()` |
| 26 |   `val imageData: ImageData get()` |
| 34 | `object AttachmentPrep` |
| 35 |   `const val MAX_DIMENSION` |
| 36 |   `private const val JPEG_QUALITY` |
| 37 |   `const val MAX_BYTES` |
| 38 |   `const val MAX_TOTAL_BYTES` |
| 39 |   `const val MAX_COUNT` |
| 41 |   `suspend fun fromUri(uri: Uri, resolver: ContentResolver): Attachment?` |
| 67 |   `private fun sniffMime(b: ByteArray): String` |

## feature/sessiondetail/ComposeBar.kt

| Line | Declaration |
|---|---|
| 58 | `@Composable fun ComposeBar( text: String, onTextChange: (String) -> Unit, onSend: () -> Unit, isInFlight: Boolean, onStop: () -> Unit, modifier: Modifier = Modifier, onPlus: (() -> Unit)? = null, onAttach: (() -> Unit)? = null, canSendOverride: Boolean = false, attachments: (@Composable ColumnScope.() -> Unit)? = null, )` |
| 70 |   `val colors` |
| 71 |   `val web` |
| 72 |   `val canSend` |
| 73 |   `val interaction` |
| 74 |   `val focused by interaction.collectIsFocusedAsState()` |
| 75 |   `val box` |
| 76 |   `val halo` |
| 145 | `@Composable private fun GhostIconButton(onClick: () -> Unit, content: @Composable () -> Unit)` |
| 160 | `@Composable private fun SendStopButton(isInFlight: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 161 |   `val colors` |
| 162 |   `val stopWash` |

## feature/sessiondetail/ComposeInsertSheet.kt

| Line | Declaration |
|---|---|
| 45 | `object ComposeInsert` |
| 46 |   `fun appendMessage(content: String, draft: String): String` |
| 49 |   `fun appendCommand(name: String, draft: String): String` |
| 53 |   `fun applyExpert(id: String, draft: String, agent: AgentType, knownIds: Set<String>): String` |
| 61 | `private enum class InsertSource` |
| 65 | `@Composable fun ComposeInsertSheet( viewModel: SessionDetailViewModel, onInsert: ((String) -> String) -> Unit, onDismiss: () -> Unit, )` |
| 70 |   `val colors` |
| 71 |   `val sheetState` |
| 72 |   `var source by remember` |
| 73 |   `var loading by remember` |
| 74 |   `var quick by remember` |
| 75 |   `var experts by remember` |
| 76 |   `var commands by remember` |
| 77 |   `var knownIds by remember` |
| 135 | `@Composable private fun InsertList(empty: Boolean, emptyText: String, content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit)` |
| 136 |   `val colors` |
| 145 | `@Composable private fun InsertRow(title: String, subtitle: String, mono: Boolean = false, onClick: () -> Unit)` |
| 146 |   `val colors` |

## feature/sessiondetail/interactive/AskQuestionCard.kt

| Line | Declaration |
|---|---|
| 55 | `private const val OTHER_KEY` |
| 59 | `@Composable fun AskQuestionCard( questions: List<QuestionSpec>, onSubmit: (QuestionAnswer) -> Unit, onSkip: () -> Unit, modifier: Modifier = Modifier, )` |
| 65 |   `val colors` |
| 66 |   `val selections` |
| 67 |   `val otherText` |
| 68 |   `var submitting by remember(questions)` |
| 70 |   `fun answered(q: QuestionSpec): Boolean` |
| 76 |   `val canSubmit` |
| 145 | `@Composable private fun QuestionBlock( question: QuestionSpec, showHeader: Boolean, selected: Set<String>, otherText: String, onToggle: (String) -> Unit, onOtherText: (String) -> Unit, )` |
| 153 |   `val colors` |
| 212 | `@Composable private fun OptionRow(label: String, description: String, checked: Boolean, multi: Boolean, onClick: () -> Unit)` |
| 213 |   `val colors` |
| 247 | `private fun buildAnswer( questions: List<QuestionSpec>, selections: Map<String, Set<String>>, otherText: Map<String, String>, ): QuestionAnswer` |
| 252 |   `val items` |

## feature/sessiondetail/interactive/ParsedPermission.kt

| Line | Declaration |
|---|---|
| 12 | `data class PermissionPlanEntry(val text: String, val status: String?)` |
| 13 | `data class PermissionAllowedPrompt(val prompt: String, val tool: String)` |
| 20 | `data class ParsedPermission( val title: String, val kind: String, val command: String?, val cwd: String?, val diffFiles: List<DiffFile>, val planMarkdown: String?, val planEntries: List<PermissionPlanEntry>, val allowedPrompts: List<PermissionAllowedPrompt>, val modeTarget: String?, val url: String?, val query: String?, val prompt: String?, val jsonPreview: String, )` |
| 21 |   `val title: String` |
| 22 |   `val kind: String` |
| 23 |   `val command: String?` |
| 24 |   `val cwd: String?` |
| 25 |   `val diffFiles: List<DiffFile>` |
| 26 |   `val planMarkdown: String?` |
| 27 |   `val planEntries: List<PermissionPlanEntry>` |
| 28 |   `val allowedPrompts: List<PermissionAllowedPrompt>` |
| 29 |   `val modeTarget: String?` |
| 30 |   `val url: String?` |
| 31 |   `val query: String?` |
| 32 |   `val prompt: String?` |
| 33 |   `val jsonPreview: String` |
| 36 |   `val isPlan: Boolean` |
| 43 |   `val hasStructuredBody: Boolean` |
| 48 |   `companion object` |
| 49 |     `fun parse(toolCall: JsonElement): ParsedPermission` |
| 85 | `private val PJson` |
| 86 | `private val PrettyJson` |
| 88 | `private fun JsonElement?.str(): String?` |
| 89 | `private fun JsonElement?.nonEmpty(): String?` |
| 90 | `private fun JsonElement?.isNullElem(): Boolean` |
| 91 | `private fun parseJsonString(s: String): JsonElement?` |
| 93 | `private fun asObject(v: JsonElement?): JsonObject?` |
| 102 | `private fun pickValue(obj: JsonObject?, keys: List<String>): JsonElement?` |
| 108 | `private fun pickString(obj: JsonObject?, keys: List<String>): String?` |
| 114 | `private fun fallbackTitle(kind: String): String` |
| 115 |   `val normalized` |
| 121 | `private fun extractCommand(value: JsonElement?, depth: Int = 0): String?` |
| 149 | `private fun extractDiffText(rawInput: JsonElement?, rawInputObj: JsonObject?): String?` |
| 150 |   `val candidates` |
| 162 | `private fun buildDiffFromChanges(rawInputObj: JsonObject?, toolCallObj: JsonObject?): String?` |
| 163 |   `val blocks` |
| 183 |   `val joined` |
| 187 | `private fun compactDiff(path: String, oldText: String, newText: String, context: Int = 2): String?` |
| 188 |   `val oldLines` |
| 190 |   `val newLines` |
| 193 |   `var prefix` |
| 195 |   `var suffix` |
| 200 |   `val removed` |
| 201 |   `val added` |
| 204 |   `val before` |
| 205 |   `val after` |
| 207 |   `val oldStart` |
| 208 |   `val oldCount` |
| 209 |   `val newCount` |
| 211 |   `val parts` |
| 219 | `private fun parsePlanEntries(rawInputObj: JsonObject?): List<PermissionPlanEntry>` |
| 234 | `private fun parseAllowedPrompts(rawInputObj: JsonObject?): List<PermissionAllowedPrompt>` |
| 235 |   `val list` |
| 243 | `private fun unescape(text: String): String` |
| 246 | `private fun prettyPrint(element: JsonElement): String` |

## feature/sessiondetail/interactive/PermissionRequestCard.kt

| Line | Declaration |
|---|---|
| 51 | `@Composable fun PermissionRequestCard( parsed: ParsedPermission, options: List<PermissionOption>, onRespond: (String) -> Unit, modifier: Modifier = Modifier, )` |
| 57 |   `val colors` |
| 58 |   `var submitting by remember(parsed.jsonPreview)` |
| 59 |   `val accent` |
| 123 | `@Composable private fun PermissionBody(parsed: ParsedPermission)` |
| 124 |   `val colors` |
| 177 | `@Composable private fun OptionButton(option: PermissionOption, submitting: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 178 |   `val colors` |
| 179 |   `val isReject` |
| 180 |   `val shape` |
| 183 |   `val clickable` |

## feature/sessiondetail/interactive/PlanApprovalCard.kt

| Line | Declaration |
|---|---|
| 64 | `@Composable fun PlanApprovalCard( planMarkdown: String, onAnswer: (PlanApprovalDecision, String?) -> Unit, modifier: Modifier = Modifier, )` |
| 69 |   `val colors` |
| 70 |   `val plan` |
| 71 |   `var submitting by remember(planMarkdown)` |
| 72 |   `var changesOpen by remember(planMarkdown)` |
| 73 |   `var feedback by remember(planMarkdown)` |
| 74 |   `val notes` |
| 76 |   `fun submit(decision: PlanApprovalDecision, text: String?)` |

## feature/sessiondetail/LiveTurn.kt

| Line | Declaration |
|---|---|
| 14 | `data class LiveToolCallState( val id: String, val title: String, val kind: String, val status: String, val rawInput: String?, val rawOutput: String, val content: String?, /** The agent's opaque ACP metadata for this call (context compaction, Grok's * `x.ai/tool` identity, …). Null for most tools and hosts. */ val meta: JsonObject? = null, )` |
| 15 |   `val id: String` |
| 16 |   `val title: String` |
| 17 |   `val kind: String` |
| 18 |   `val status: String` |
| 19 |   `val rawInput: String?` |
| 20 |   `val rawOutput: String` |
| 21 |   `val content: String?` |
| 24 |   `val meta: JsonObject?` |
| 26 |   `val isFinished: Boolean` |
| 28 |   `val isError: Boolean get()` |
| 32 | `sealed interface LiveSegment` |
| 33 |   `val key: String` |
| 35 |   `data class Text(val id: String, val text: String) : LiveSegment` |
| 38 |   `data class Thinking(val id: String, val text: String) : LiveSegment` |
| 41 |   `data class Tool(val call: LiveToolCallState) : LiveSegment` |
| 47 | `data class LiveTurnState( val id: String, val segments: List<LiveSegment>, val livePlan: List<PlanEntry>, val isStreaming: Boolean, val stopReason: String?, val errorMessage: String?, )` |
| 48 |   `val id: String` |
| 49 |   `val segments: List<LiveSegment>` |
| 50 |   `val livePlan: List<PlanEntry>` |
| 51 |   `val isStreaming: Boolean` |
| 52 |   `val stopReason: String?` |
| 53 |   `val errorMessage: String?` |
| 55 |   `val isEmpty: Boolean get()` |
| 64 | `class LiveTurnBuilder(val id: String = "live-${java.util.UUID.randomUUID()}")` |
| 66 |   `private sealed class Seg` |
| 67 |     `class Text(val id: String, val text: StringBuilder) : Seg()` |
| 68 |     `class Thinking(val id: String, val text: StringBuilder) : Seg()` |
| 69 |     `class Tool(val call: ToolBuilder) : Seg()` |
| 72 |   `private class ToolBuilder( val id: String, var title: String, var kind: String, var status: String, var rawInput: String?, val rawOutput: StringBuilder, var content: String?, var meta: JsonObject?, )` |
| 83 |   `private val segments` |
| 84 |   `private val toolIndex` |
| 85 |   `private var seq` |
| 87 |   `var livePlan: List<PlanEntry>` |
| 88 |   `var isStreaming: Boolean` |
| 89 |   `var stopReason: String?` |
| 90 |   `var errorMessage: String?` |
| 92 |   `fun appendText(delta: String)` |
| 98 |   `fun appendThinking(delta: String)` |
| 104 |   `fun upsertToolCall( id: String, title: String, kind: String, status: String, rawInput: String?, rawOutput: String?, content: String?, meta: JsonObject? = null, )` |
| 142 |   `fun updateToolCall( id: String, title: String?, status: String?, rawInput: String?, rawOutput: String?, content: String?, append: Boolean, meta: JsonObject? = null, )` |
| 170 |   `fun updatePlan(entries: List<PlanEntry>)` |
| 179 |   `fun seedFrom(snapshot: LiveSessionSnapshot)` |
| 193 |   `private fun seedTool(call: ToolCallStateSnapshot)` |
| 206 |   `val isEmpty: Boolean get()` |
| 209 |   `fun activeToolTitle(): String?` |
| 218 |   `fun snapshot(): LiveTurnState` |
| 245 |   `fun snapshotAsMessageTurn(): MessageTurn` |

## feature/sessiondetail/rendering/ContextCompaction.kt

| Line | Declaration |
|---|---|
| 18 | `object ContextCompaction` |
| 22 |   `fun matches(meta: JsonObject?): Boolean` |
| 32 |   `fun tokens(meta: JsonObject?): Pair<Int?, Int?>` |
| 35 |   `private fun count(meta: JsonObject?, key: String): Int?` |

## feature/sessiondetail/rendering/InlineImage.kt

| Line | Declaration |
|---|---|
| 27 | `@Composable fun InlineImage(image: ImageData, caption: String?, modifier: Modifier = Modifier)` |
| 28 |   `val colors` |
| 29 |   `val bitmap` |

## feature/sessiondetail/rendering/LivePlanView.kt

| Line | Declaration |
|---|---|
| 32 | `private val SuccessGreen` |
| 36 | `@Composable fun LivePlanView(entries: List<PlanEntry>, modifier: Modifier = Modifier)` |
| 38 |   `val colors` |
| 39 |   `val done` |
| 59 | `@Composable private fun PlanRow(entry: PlanEntry)` |
| 60 |   `val colors` |
| 61 |   `val completed` |

## feature/sessiondetail/rendering/McpResultEnvelope.kt

| Line | Declaration |
|---|---|
| 24 | `object McpResultEnvelope` |
| 29 |   `private val wrapperKeys` |
| 32 |   `private const val MAX_DEPTH` |
| 41 |   `data class Peeled(val obj: JsonObject, val hostError: String?)` |
| 50 |   `fun peel(obj: JsonObject, isResolvable: (JsonObject) -> Boolean): Peeled` |
| 71 |   `fun isCallToolResult(value: JsonObject): Boolean` |
| 80 |   `private fun hostFailureError(obj: JsonObject): String?` |

## feature/sessiondetail/rendering/ReasoningBlock.kt

| Line | Declaration |
|---|---|
| 44 | `@Composable fun ReasoningBlock(text: String, modifier: Modifier = Modifier, initiallyExpanded: Boolean = false)` |
| 45 |   `val colors` |
| 46 |   `var expanded by remember(text)` |
| 47 |   `val chevron by animateFloatAsState(if (expanded) 90f else 0f, label = "reasoning-chevron")` |

## feature/sessiondetail/rendering/ToolCallCard.kt

| Line | Declaration |
|---|---|
| 71 | `private val SuccessGreen` |
| 72 | `private val ErrorRed` |
| 79 | `@Composable fun ToolCallCard(vm: ToolCallVM, modifier: Modifier = Modifier, nested: Boolean = false)` |
| 80 |   `val colors` |
| 81 |   `val autoExpand` |
| 84 |   `var expanded by remember(vm.id)` |
| 85 |   `val hasBody` |
| 142 | `@Composable private fun ToolBody(vm: ToolCallVM)` |
| 143 |   `val diff` |
| 151 |   `val input` |
| 167 | `@Composable private fun BashInputBody(input: String)` |
| 168 |   `val parsed` |
| 169 |   `val args` |
| 170 |   `val cmd` |
| 181 | `@Composable private fun TodoInputBody(input: String)` |
| 182 |   `val colors` |
| 183 |   `val args` |
| 184 |   `val todos` |
| 224 | `@Composable private fun ToolOutputSection(vm: ToolCallVM)` |
| 228 |   `val out` |
| 257 | `@Composable private fun ToolStateIcon(vm: ToolCallVM)` |
| 258 |   `val colors` |
| 269 | `@Composable fun ToolGroupCard(items: List<ToolCallVM>, modifier: Modifier = Modifier, streaming: Boolean = false)` |
| 270 |   `val colors` |
| 273 |   `var expanded by remember(items.firstOrNull()?.id)` |
| 274 |   `val errorCount` |
| 327 | `fun bucketIcon(bucket: ToolKindBucket): ImageVector` |
| 339 | `private fun groupSummary(context: Context, items: List<ToolCallVM>): String` |
| 340 |   `val counts` |
| 341 |   `val order` |
| 345 |   `val res` |
| 346 |   `val phrases` |
| 364 | `private fun JsonObject.str(key: String): String?` |
| 365 |   `val prim` |

## feature/sessiondetail/rendering/ToolCallVM.kt

| Line | Declaration |
|---|---|
| 10 | `enum class ToolCallState` |
| 18 | `data class ToolCallVM( val id: String, val rawName: String, val kind: String, val state: ToolCallState, val input: String?, val output: String?, val content: String?, val isError: Boolean, val displayTitle: String, val bucket: ToolKindBucket, val diffFiles: List<DiffFile>?, /** The agent's opaque ACP metadata for this call (Grok's `x.ai/tool` identity, * the context-compaction tag, …). Null for most tools and hosts. */ val meta: JsonObject? = null, )` |
| 19 |   `val id: String` |
| 20 |   `val rawName: String` |
| 21 |   `val kind: String` |
| 22 |   `val state: ToolCallState` |
| 23 |   `val input: String?` |
| 24 |   `val output: String?` |
| 25 |   `val content: String?` |
| 26 |   `val isError: Boolean` |
| 27 |   `val displayTitle: String` |
| 28 |   `val bucket: ToolKindBucket` |
| 29 |   `val diffFiles: List<DiffFile>?` |
| 32 |   `val meta: JsonObject?` |
| 34 |   `val trimmedOutput: String get()` |
| 35 |   `val hasOutput: Boolean get()` |
| 36 |   `val isCommand: Boolean get()` |
| 43 |   `val isPlanMode: Boolean get()` |
| 45 |   `companion object` |
| 46 |     `fun of( id: String, rawName: String, kind: String, state: ToolCallState, input: String?, output: String?, content: String?, isError: Boolean, meta: JsonObject? = null, ): ToolCallVM` |
| 85 | `sealed interface RenderPart` |
| 86 |   `data class Text(val text: String) : RenderPart` |
| 87 |   `data class Reasoning(val text: String) : RenderPart` |
| 88 |   `data class Tool(val vm: ToolCallVM) : RenderPart` |
| 89 |   `data class ToolGroup(val items: List<ToolCallVM>, val streaming: Boolean) : RenderPart` |
| 90 |   `data class Image(val image: ImageData, val caption: String?) : RenderPart` |
| 96 |   `data class Compaction(val before: Int?, val after: Int?, val running: Boolean) : RenderPart` |
| 97 |   `data class Unknown(val type: String) : RenderPart` |
| 101 | `object MessageRender` |
| 103 |   `fun adaptTurn(turn: MessageTurn): List<RenderPart>` |
| 172 |   `fun groupConsecutiveTools(parts: List<RenderPart>): List<RenderPart>` |
| 176 |     `fun flush()` |
| 200 |   `private fun findResult(blocks: List<ContentBlock>, after: Int, toolID: String?, consumed: Set<Int>): Int?` |

## feature/sessiondetail/rendering/ToolDerive.kt

| Line | Declaration |
|---|---|
| 14 | `enum class ToolKindBucket` |
| 21 | `object ToolDerive` |
| 23 |   `private val lenient` |
| 27 |   `private val readNames` |
| 28 |   `private val editNames` |
| 34 |   `private val searchNames` |
| 38 |   `private val executeNames` |
| 42 |   `private val webNames` |
| 45 |   `private val todoNames` |
| 46 |   `private val taskNames` |
| 51 |   `fun parseJson(s: String?): JsonObject?` |
| 57 |   `fun effectiveArgs(parsed: JsonObject?): JsonObject?` |
| 65 |   `fun title(name: String, parsed: JsonObject?): String` |
| 68 |     `fun str(keys: List<String>): String?` |
| 124 |   `fun bucket(name: String, kind: String): ToolKindBucket` |
| 143 |   `fun diff( bucket: ToolKindBucket, input: String?, output: String?, content: String?, parsed: JsonObject?, ): List<DiffFile>?` |
| 178 |   `fun grokPlanModeName(meta: JsonObject?): String?` |
| 191 |   `fun isPlanModeName(name: String): Boolean` |
| 194 |   `private val planModeNames` |
| 196 |   `private fun canonical(name: String): String` |
| 203 |   `fun display(name: String): String` |
| 210 |   `private fun editFileCount(args: JsonObject?): Int?` |
| 224 |   `private fun todoCounts(args: JsonObject?): Pair<Int, Int>?` |
| 231 |   `private fun firstStringValue(args: JsonObject?): String?` |
| 241 |   `private val shellPrefix` |
| 243 |   `private fun simplifyCommand(c: String): String` |
| 252 |   `private fun host(url: String): String` |
| 255 |   `private fun truncate(s: String, n: Int): String` |
| 260 |   `private fun synthesizeDiff(path: String, old: String, new: String): List<DiffFile>` |
| 273 | `object PathFormat` |
| 274 |   `fun short(path: String): String` |
| 281 | `private fun JsonObject?.string(key: String): String?` |
| 282 |   `val prim` |

## feature/sessiondetail/rendering/ToolValueFormat.kt

| Line | Declaration |
|---|---|
| 57 | `private val lenient` |
| 58 | `private val prettyJson` |
| 62 | `private fun parseAny(s: String?): JsonElement?` |
| 68 | `private fun JsonObject.strField(key: String): String?` |
| 69 |   `val prim` |
| 74 | `private fun scalarString(prim: JsonPrimitive): String` |
| 77 | `private fun prettyPrint(el: JsonElement): String` |
| 84 | `fun prettyPrintJsonString(s: String): String?` |
| 85 |   `val any` |
| 91 | `private val codeFields` |
| 97 | `private val hiddenFields` |
| 105 | `object ToolOutputFormat` |
| 107 |   `enum class Kind` |
| 112 |   `fun commandOutput(output: String): String?` |
| 137 |   `private val metaLine` |
| 141 |   `private val outputSepLine` |
| 143 |   `private fun isMetaLine(s: String)` |
| 147 |   `fun parseCliEnvelope(text: String): String` |
| 178 |   `fun stripMarkdownFence(text: String): String` |
| 187 |   `fun cleanCommandOutput(source: String): String` |
| 194 |   `fun classify(output: String): Kind` |
| 202 |   `fun looksLikeMarkdown(s: String): Boolean` |
| 225 |   `fun mcpResultText(output: String): String?` |
| 243 |   `fun errorFields(errorText: String): List<Pair<String, String>>?` |
| 255 | `object ReadOutputFormat` |
| 256 |   `data class File(val content: String, val startLine: Int)` |
| 258 |   `fun parse(output: String): File?` |
| 274 | `object ToolFieldLabel` |
| 275 |   `private val map` |
| 293 |   `fun label(key: String): String` |
| 296 |   `private fun humanize(key: String): String` |
| 311 | `@Composable fun ToolFieldInline(label: String, value: String)` |
| 312 |   `val colors` |
| 327 | `@Composable fun ToolFieldBlock(label: String, content: @Composable () -> Unit)` |
| 328 |   `val colors` |
| 338 | `@Composable fun PlainTextBlock(text: String)` |
| 339 |   `val colors` |
| 358 | `@Composable fun StructuredJsonView(json: String, inlineMaxLength: Int = 200)` |
| 359 |   `val obj` |
| 377 | `@Composable private fun ToolField(key: String, value: JsonElement, inlineMaxLength: Int)` |
| 378 |   `val label` |
| 393 | `private fun codeLanguage(key: String): String?` |
| 403 | `@Composable fun FileBodyView(file: ReadOutputFormat.File, collapsedLineLimit: Int = 60)` |
| 404 |   `val colors` |
| 405 |   `val content` |
| 406 |   `val lines` |
| 407 |   `val isLong` |
| 408 |   `var expanded by remember(file)` |
| 409 |   `val shown` |
| 410 |   `val maxNo` |
| 411 |   `val digits` |
| 412 |   `val gutterWidth` |
| 467 | `@Composable fun ToolErrorOutput(output: String)` |
| 468 |   `val colors` |
| 469 |   `val fields` |

## feature/sessiondetail/SessionActions.kt

| Line | Declaration |
|---|---|
| 62 | `@Composable fun SessionActionsMenu( ui: SessionDetailUiState, viewModel: SessionDetailViewModel, onDeleted: () -> Unit, )` |
| 67 |   `val colors` |
| 68 |   `val summary` |
| 69 |   `var menu by remember` |
| 70 |   `var showRename by remember` |
| 71 |   `var showStatus by remember` |
| 72 |   `var showDetails by remember` |
| 73 |   `var showDelete by remember` |
| 170 | `@Composable private fun DetailRow(label: String, value: String, valueColor: androidx.compose.ui.graphics.Color? = null)` |
| 171 |   `val colors` |

## feature/sessiondetail/SessionDetailScreen.kt

| Line | Declaration |
|---|---|
| 99 | `@Composable fun SessionDetailScreen( onBack: () -> Unit, onOpenSession: (Int) -> Unit = {}, onOpenServers: () -> Unit = {}, viewModel: SessionDetailViewModel = hiltViewModel(), )` |
| 105 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 106 |   `val streamStatus by viewModel.streamStatus.collectAsStateWithLifecycle()` |
| 111 |   `val colors` |
| 112 |   `var draft by remember` |
| 113 |   `var showInsert by remember` |
| 114 |   `var showOptions by remember` |
| 115 |   `val listState` |
| 116 |   `val scope` |
| 117 |   `val context` |
| 133 |   `val pickImages` |
| 149 |   `val hideReattachDup` |
| 150 |   `val persistedNodes` |
| 153 |   `val nodes` |
| 156 |   `val hasContent` |
| 159 |   `val isAtBottom by remember` |
| 166 |   `var stick by remember` |
| 182 |   `val scrollToId: (String) -> Unit` |
| 192 |   `val title` |
| 197 |   `val agentOptionsLabel` |
| 372 | `@Composable private fun JumpToLatestButton(onClick: () -> Unit)` |
| 373 |   `val colors` |
| 393 | `@Composable private fun SendStatusLine(status: String)` |
| 394 |   `val colors` |
| 407 | `@Composable private fun NoticeBanner( message: String, onDismiss: () -> Unit, actionLabel: String? = null, onAction: () -> Unit = {}, )` |
| 413 |   `val colors` |
| 441 | `@Composable private fun AttachmentStrip(attachments: List<Attachment>, onRemove: (String) -> Unit)` |
| 442 |   `val colors` |

## feature/sessiondetail/SessionDetailViewModel.kt

| Line | Declaration |
|---|---|
| 69 | `private const val REASON_CONNECTION_GONE` |
| 72 | `private const val MODE_KEY` |
| 76 | `private const val RECONCILE_ATTEMPTS` |
| 81 | `private const val STATUS_THINKING` |
| 92 | `class SessionDetailViewModel @Inject constructor( private val repository: ServerRepository, private val selectorPrefs: SelectorPrefsStore, savedStateHandle: SavedStateHandle, @ApplicationContext private val appContext: Context, ) : ViewModel()` |
| 93 |   `private val repository: ServerRepository` |
| 94 |   `private val selectorPrefs: SelectorPrefsStore` |
| 99 |   `private val conversationIdArg: Int?` |
| 101 |   `private val folderIdArg: Int?` |
| 103 |   `private var profile: ServerProfile?` |
| 104 |   `private var client: CodegClient?` |
| 105 |   `private var conversationId: Int?` |
| 106 |   `private var connectionId: String?` |
| 107 |   `private var folder: FolderDetail?` |
| 108 |   `private var allFolders: List<FolderDetail>` |
| 109 |   `private var agent: AgentType` |
| 110 |   `private var externalId: String?` |
| 111 |   `private var lastSeq: Long` |
| 113 |   `private var hasStartedFirstSend` |
| 115 |   `private var currentBranch: String?` |
| 117 |   `private val _streamStatus` |
| 118 |   `val streamStatus: StateFlow<StreamNotice?>` |
| 119 |   `private var streamDisconnected` |
| 120 |   `private var streamPaused` |
| 121 |   `private var streamAuthFailed` |
| 122 |   `private val connectivity` |
| 123 |   `private val networkCallback` |
| 124 |     `override fun onAvailable(network: Network)` |
| 128 |   `private var networkCallbackRegistered` |
| 129 |   `private var stream: EventStream?` |
| 130 |   `private var consumerJob: Job?` |
| 131 |   `private var sendJob: Job?` |
| 132 |   `private var streamGeneration` |
| 133 |   `private var reconnectJob: Job?` |
| 136 |   `private var reconcileJob: Job?` |
| 140 |   `private var pendingPlanFollowUp: ParkedPlanNotes?` |
| 145 |   `private var optionsConnect: OptionsConnect?` |
| 147 |   `private val subscriptionId` |
| 149 |   `private var streamReconnects` |
| 152 |   `private var pendingReattachSeed` |
| 153 |   `private var liveBuilder: LiveTurnBuilder?` |
| 154 |   `private var liveFlushScheduled` |
| 155 |   `private var turnBaseline` |
| 157 |   `private val _ui` |
| 158 |   `val ui: StateFlow<SessionDetailUiState>` |
| 168 |   `fun load()` |
| 247 |   `fun send(text: String)` |
| 256 |   `private fun send(text: String, withAttachments: Boolean)` |
| 303 |   `private suspend fun runSend( text: String, userTurn: MessageTurn, attachments: List<Attachment>, createdConversation: Boolean, )` |
| 343 |   `private suspend fun attemptSend( c: CodegClient, text: String, attachments: List<Attachment>, clientMessageId: String, freshConnection: Boolean, )` |
| 368 |   `private suspend fun connectFresh(c: CodegClient): String` |
| 378 |   `private suspend fun connectWithPreferences( c: CodegClient, connectAgent: AgentType = agent, connectFolder: FolderDetail? = folder, ): String` |
| 393 |   `private suspend fun ensureConversationCreated(firstPrompt: String)` |
| 402 |   `private suspend fun resolveConnection(c: CodegClient): String` |
| 417 |   `private suspend fun openStream(conn: String, seedOnAttach: Boolean = false)` |
| 431 |   `private suspend fun connectStream(conn: String, awaitAttach: Boolean, seedOnAttach: Boolean = false)` |
| 517 |   `fun cancel()` |
| 536 |   `private fun reattachIfLive()` |
| 577 |   `private fun applyEvent(event: AcpEvent, seq: Long)` |
| 654 |   `private fun clearInteractivePrompts()` |
| 671 |   `private fun runningStatus(): String` |
| 680 |   `private fun adoptLinkedConversation(id: Int)` |
| 696 |   `fun respondPermission(optionId: String)` |
| 706 |   `fun answerQuestion(answer: app.codeg.android.core.model.QuestionAnswer)` |
| 716 |   `fun dismissQuestion()` |
| 729 |   `fun answerPlanApproval(decision: PlanApprovalDecision, feedback: String?)` |
| 766 |   `private fun seedLiveFromSnapshot(snap: app.codeg.android.core.model.LiveSessionSnapshot): Boolean` |
| 787 |   `private fun abortIdleReattach()` |
| 798 |   `private fun reconcileAfterMissedLive()` |
| 810 |   `private fun restorePending(snap: app.codeg.android.core.model.LiveSessionSnapshot)` |
| 830 |   `private fun finalizeTurn(stopReason: String)` |
| 858 |   `private fun failLive(message: String)` |
| 869 |   `private fun handleStreamEnded(gen: Int, reason: String?)` |
| 890 |   `fun recoverStream()` |
| 901 |   `fun dismissStreamStatus()` |
| 905 |   `fun resumeStream()` |
| 912 |   `fun pauseStream()` |
| 916 |   `private fun stopForStreamAuth()` |
| 922 |   `private fun reconnectStream(immediate: Boolean = false)` |
| 952 |   `private fun reconcileAfterTurn()` |
| 1005 |   `private fun promoteUnreconciled()` |
| 1035 |   `private fun discardOptimistic( userTurn: MessageTurn, text: String, attachments: List<Attachment>, createdConversation: Boolean, createdConversationId: Int?, notice: String, )` |
| 1085 |   `fun consumeRestoredDraft()` |
| 1089 |   `fun dismissNotice()` |
| 1095 |   `private fun scheduleLiveEmit()` |
| 1105 |   `private fun emitLiveNow()` |
| 1110 |   `private fun closeStream()` |
| 1121 |   `override fun onCleared()` |
| 1133 |   `val canManage: Boolean get()` |
| 1134 |   `val isPinned: Boolean get()` |
| 1136 |   `fun rename(title: String)` |
| 1150 |   `fun togglePin()` |
| 1165 |   `fun setStatus(status: ConversationStatus)` |
| 1178 |   `fun deleteConversation(onDeleted: () -> Unit)` |
| 1193 |   `val agentForInsert: AgentType get()` |
| 1195 |   `suspend fun loadQuickMessages(): List<QuickMessage>` |
| 1199 |   `suspend fun loadExpertsForInsert(): List<ExpertListItem>` |
| 1207 |   `suspend fun loadSlashCommands(): List<AvailableCommandInfo>` |
| 1215 |   `suspend fun knownExpertIds(): Set<String>` |
| 1223 |   `fun addAttachments(uris: List<Uri>, resolver: ContentResolver)` |
| 1242 |   `fun removeAttachment(id: String)` |
| 1252 |   `fun selectAgent(newAgent: AgentType)` |
| 1261 |   `fun selectFolder(newFolder: FolderDetail)` |
| 1278 |   `suspend fun loadBranches(): GitBranchList?` |
| 1297 |   `suspend fun switchBranch(branch: String, isRemote: Boolean): BranchSwitchOutcome` |
| 1331 |   `private suspend fun checkoutInRoot(c: CodegClient, active: FolderDetail, root: FolderDetail, branch: String): BranchSwitchOutcome` |
| 1354 |   `private suspend fun recoverFromWorktreeCheckout(c: CodegClient, branch: String, root: FolderDetail, error: Exception): BranchSwitchOutcome?` |
| 1364 |   `private suspend fun openWorktreeSession(c: CodegClient, path: String, root: FolderDetail): BranchSwitchOutcome?` |
| 1372 |   `private fun worktreePathFromError(message: String): String?` |
| 1380 |   `suspend fun createBranch(name: String, from: String?): Boolean` |
| 1404 |   `suspend fun loadAgentOptions(): AgentOptionsData` |
| 1438 |   `private suspend fun optionsConnection(forAgent: AgentType, forFolder: FolderDetail?): String?` |
| 1494 |   `suspend fun applyMode(modeId: String): String?` |
| 1515 |   `suspend fun applyConfig(configId: String, valueId: String): String?` |
| 1538 |   `private fun dropStaleOptionsConnection(conn: String)` |
| 1546 |   `private suspend fun reconcileSelection(conn: String, key: String, desired: String): String` |
| 1565 | `private class StreamAttachFailed(val reason: String?) : Exception("stream attach failed: ${reason ?: "closed"}")` |
| 1569 | `private class OptionsConnect(val agent: AgentType, val folderId: Int?, val deferred: Deferred<String>)` |
| 1573 | `data class AgentOptionsData( val modes: SessionModeState? = null, val configOptions: List<SessionConfigOption> = emptyList(), val initialModeId: String? = null, val initialConfig: Map<String, String> = emptyMap(), )` |
| 1574 |   `val modes: SessionModeState?` |
| 1575 |   `val configOptions: List<SessionConfigOption>` |
| 1576 |   `val initialModeId: String?` |
| 1577 |   `val initialConfig: Map<String, String>` |
| 1581 | `private fun SessionSnapshot.selectionFor(key: String): String?` |
| 1586 | `private fun List<SessionConfigOption>.currentValues(): Map<String, String>` |
| 1591 | `private fun SessionConfigKind.allSelectValues(): List<String>` |
| 1595 | `sealed interface BranchSwitchOutcome` |
| 1597 |   `data object Noop : BranchSwitchOutcome` |
| 1599 |   `data object SwitchedInPlace : BranchSwitchOutcome` |
| 1601 |   `data class OpenSession(val folderId: Int) : BranchSwitchOutcome` |
| 1603 |   `data object Failed : BranchSwitchOutcome` |
| 1606 | `data class SessionDetailUiState( val isNew: Boolean, val loading: Boolean = false, val summary: ConversationSummary? = null, val folderName: String? = null, val folderPath: String? = null, val selectedFolderId: Int? = null, val currentBranch: String? = null, val isDraftEditable: Boolean = false, val availableFolders: List<FolderDetail> = emptyList(), val availableAgents: List<AgentType> = emptyList(), val turns: List<MessageTurn> = emptyList(), val pendingUserTurns: List<MessageTurn> = emptyList(), val live: LiveTurnState? = null, /** The live turn was rebuilt from a reattach snapshot (owns the in-flight reply). */ val liveFromReattach: Boolean = false, val sessionStats: SessionStats? = null, val agent: AgentType = AgentType.CLAUDE_CODE, val isInFlight: Boolean = false, val sendStatus: String? = null, val notice: String? = null, /** Set when a failed send returns the user's typed text to the composer (consumed once). */ val restoreDraft: String? = null, val error: String? = null, val pendingPermission: PendingPermissionUi? = null, val pendingQuestion: PendingQuestionUi? = null, val pendingPlanApproval: PendingPlanApprovalUi? = null, val attachments: List<Attachment> = emptyList(), )` |
| 1607 |   `val isNew: Boolean` |
| 1608 |   `val loading: Boolean` |
| 1609 |   `val summary: ConversationSummary?` |
| 1610 |   `val folderName: String?` |
| 1611 |   `val folderPath: String?` |
| 1612 |   `val selectedFolderId: Int?` |
| 1613 |   `val currentBranch: String?` |
| 1614 |   `val isDraftEditable: Boolean` |
| 1615 |   `val availableFolders: List<FolderDetail>` |
| 1616 |   `val availableAgents: List<AgentType>` |
| 1617 |   `val turns: List<MessageTurn>` |
| 1618 |   `val pendingUserTurns: List<MessageTurn>` |
| 1619 |   `val live: LiveTurnState?` |
| 1621 |   `val liveFromReattach: Boolean` |
| 1622 |   `val sessionStats: SessionStats?` |
| 1623 |   `val agent: AgentType` |
| 1624 |   `val isInFlight: Boolean` |
| 1625 |   `val sendStatus: String?` |
| 1626 |   `val notice: String?` |
| 1628 |   `val restoreDraft: String?` |
| 1629 |   `val error: String?` |
| 1630 |   `val pendingPermission: PendingPermissionUi?` |
| 1631 |   `val pendingQuestion: PendingQuestionUi?` |
| 1632 |   `val pendingPlanApproval: PendingPlanApprovalUi?` |
| 1633 |   `val attachments: List<Attachment>` |
| 1637 | `internal enum class PlanFollowUp` |
| 1648 | `internal data class ParkedPlanNotes(val turnId: String, val notes: String)` |
| 1650 |   `fun notesFor(turnId: String): String?` |
| 1669 | `internal fun planFollowUpAction( sameTurn: Boolean, inFlight: Boolean, stopReason: String?, errorMessage: String?, ): PlanFollowUp` |
| 1682 | `data class PendingPermissionUi( val requestId: String, val parsed: app.codeg.android.feature.sessiondetail.interactive.ParsedPermission, val options: List<app.codeg.android.core.model.PermissionOption>, )` |
| 1683 |   `val requestId: String` |
| 1684 |   `val parsed: app.codeg.android.feature.sessiondetail.interactive.ParsedPermission` |
| 1685 |   `val options: List<app.codeg.android.core.model.PermissionOption>` |
| 1689 | `data class PendingQuestionUi( val questionId: String, val questions: List<app.codeg.android.core.model.QuestionSpec>, )` |
| 1690 |   `val questionId: String` |
| 1691 |   `val questions: List<app.codeg.android.core.model.QuestionSpec>` |
| 1700 | `data class PendingPlanApprovalUi( val approvalId: String, /** Grok's `toolCallId` for the `exit_plan_mode` call. Not used for the answer (which * keys on [approvalId]) — kept so the card can correlate with the in-stream call. */ val toolCallId: String, /** The plan, read from Grok's `plan.md`. May be empty — the card then shows an * empty-state notice rather than hiding, since the turn is blocked either way. */ val planMarkdown: String, )` |
| 1701 |   `val approvalId: String` |
| 1704 |   `val toolCallId: String` |
| 1707 |   `val planMarkdown: String` |

## feature/sessiondetail/StreamReconnectPolicy.kt

| Line | Declaration |
|---|---|
| 3 | `internal const val MAX_STREAM_RECONNECTS` |
| 5 | `internal fun streamReconnectDelay(attempt: Int): Long` |
| 8 | `internal enum class StreamRecovery` |
| 10 | `internal fun streamRecovery( inFlight: Boolean, authFailed: Boolean, connectionGone: Boolean, attempts: Int, immediate: Boolean, ): StreamRecovery` |
| 24 | `data class StreamNotice(val message: String, val openServers: Boolean = false)` |

## feature/sessiondetail/timeline/TimelineNode.kt

| Line | Declaration |
|---|---|
| 28 | `sealed interface NodeContent` |
| 29 |   `data class User(val turn: MessageTurn) : NodeContent` |
| 30 |   `data class System(val turn: MessageTurn) : NodeContent` |
| 34 |   `data class AssistantBlock(val block: MarkdownBlock, val streaming: Boolean = false) : NodeContent` |
| 35 |   `data class Reasoning(val text: String, val streaming: Boolean = false) : NodeContent` |
| 36 |   `data class Tool(val vm: ToolCallVM) : NodeContent` |
| 37 |   `data class ToolGroup(val items: List<ToolCallVM>, val streaming: Boolean) : NodeContent` |
| 38 |   `data class Image(val image: ImageData, val caption: String?) : NodeContent` |
| 41 |   `data class Compaction(val before: Int?, val after: Int?, val running: Boolean) : NodeContent` |
| 42 |   `data class Footer(val turn: MessageTurn, val questionId: String?) : NodeContent` |
| 43 |   `data class Plan(val entries: List<PlanEntry>) : NodeContent` |
| 44 |   `data object Thinking : NodeContent` |
| 45 |   `data class Error(val message: String) : NodeContent` |
| 46 |   `data class Unsupported(val type: String) : NodeContent` |
| 55 | `data class TimelineNode( val id: String, val content: NodeContent, val agent: AgentType, val connectTop: Boolean = true, val connectBottom: Boolean = true, val startsGroup: Boolean = false, val rail: RailStyle = RailStyle.Standalone, )` |
| 56 |   `val id: String` |
| 57 |   `val content: NodeContent` |
| 58 |   `val agent: AgentType` |
| 59 |   `val connectTop: Boolean` |
| 60 |   `val connectBottom: Boolean` |
| 61 |   `val startsGroup: Boolean` |
| 62 |   `val rail: RailStyle` |
| 64 |   `val marker: MarkerKind` |
| 83 |   `val contentTypeKey: Any` |
| 111 | `internal fun systemText(turn: MessageTurn): String` |
| 117 | `internal fun List<MessageTurn>.dropTrailingAssistantRun(): List<MessageTurn>` |
| 118 |   `var end` |
| 129 | `object TranscriptTimeline` |
| 131 |   `fun build( turns: List<MessageTurn>, pending: List<MessageTurn>, live: LiveTurnState?, agent: AgentType, hideTrailingAssistantReply: Boolean = false, ): List<TimelineNode>` |
| 151 |   `fun buildPersisted( turns: List<MessageTurn>, pending: List<MessageTurn>, agent: AgentType, hideTrailingAssistantReply: Boolean = false, ): List<TimelineNode>` |
| 191 |   `fun withLive(persisted: List<TimelineNode>, live: LiveTurnState?, agent: AgentType): List<TimelineNode>` |
| 203 |   `private fun assistantNodes(merged: MessageTurn, questionId: String?, agent: AgentType): List<TimelineNode>` |
| 212 |   `private fun liveNodes(live: LiveTurnState, agent: AgentType): List<TimelineNode>` |
| 239 |   `private fun liveParts(live: LiveTurnState): List<RenderPart>` |
| 265 |   `private fun nodesFor(part: RenderPart, ownerId: String, index: Int, agent: AgentType, cached: Boolean): List<TimelineNode>` |
| 291 |   `private fun textBlockNodes(text: String, base: String, agent: AgentType, cached: Boolean): List<TimelineNode>` |
| 307 |   `fun merge(group: List<MessageTurn>): MessageTurn` |
| 331 | `private fun LiveToolCallState.toVM(): ToolCallVM` |
| 332 |   `val state` |

## feature/sessiondetail/timeline/TimelineNodeBody.kt

| Line | Declaration |
|---|---|
| 65 | `private val SuccessGreen` |
| 68 | `val LocalTimelineScroll` |
| 76 | `@Composable fun NodeBody(node: TimelineNode, modifier: Modifier = Modifier)` |
| 112 | `@Composable private fun UserNodeBody(turn: MessageTurn, modifier: Modifier)` |
| 113 |   `val colors` |
| 138 | `@Composable private fun SystemNodeBody(turn: MessageTurn, modifier: Modifier)` |
| 139 |   `val text` |
| 150 | `@Composable private fun TurnFooter(turn: MessageTurn, questionId: String?, modifier: Modifier)` |
| 151 |   `val colors` |
| 154 |   `val copyText` |
| 159 |   `val meta` |
| 182 | `@Composable private fun CopyButton(text: String)` |
| 183 |   `val colors` |
| 184 |   `val clipboard` |
| 185 |   `var copied by remember` |
| 208 | `@Composable private fun JumpToQuestionButton(questionId: String)` |
| 209 |   `val scroll` |
| 230 | `@Composable fun ThinkingShimmer(modifier: Modifier = Modifier)` |
| 231 |   `val colors` |
| 232 |   `val transition` |
| 257 | `@Composable private fun BlinkingCaret()` |
| 258 |   `val transition` |
| 259 |   `val a by transition.animateFloat( initialValue = 1f, targetValue = 0f, animationSpec = infiniteRepeatable(tween(530), RepeatMode.Reverse), label = "caret-alpha", )` |
| 279 | `@Composable fun ContextCompactionDivider(before: Int?, after: Int?, running: Boolean, modifier: Modifier = Modifier)` |
| 280 |   `val colors` |
| 281 |   `val label` |
| 302 | `private fun groupDigits(value: Int): String` |
| 306 | `@Composable fun InlineTurnError(message: String, modifier: Modifier = Modifier)` |
| 307 |   `val colors` |
| 323 | `private fun compactTokens(n: Int): String` |
| 329 | `private fun trimNum(v: Double): String` |
| 331 | `private fun compactDuration(ms: Int): String` |
| 332 |   `val seconds` |
| 334 |   `val whole` |
| 335 |   `val m` |
| 336 |   `val s` |

## feature/sessiondetail/timeline/TimelineRail.kt

| Line | Declaration |
|---|---|
| 21 | `object TimelineMetrics` |
| 22 |   `val rowSpacing: Dp` |
| 23 |   `val groupGap: Dp` |
| 24 |   `val blockGap: Dp` |
| 26 |   `const val userMaxWidthFraction: Float` |
| 35 | `enum class RailStyle` |
| 41 | `sealed interface MarkerKind` |
| 42 |   `data object User : MarkerKind` |
| 43 |   `data class Assistant(val agent: AgentType) : MarkerKind` |
| 44 |   `data object Reasoning : MarkerKind` |
| 45 |   `data class Tool(val bucket: ToolKindBucket, val state: ToolCallState) : MarkerKind` |
| 46 |   `data class ToolGroup(val error: Boolean, val streaming: Boolean) : MarkerKind` |
| 47 |   `data object Image : MarkerKind` |
| 48 |   `data object System : MarkerKind` |
| 49 |   `data object Plan : MarkerKind` |
| 50 |   `data object Thinking : MarkerKind` |
| 51 |   `data object Error : MarkerKind` |
| 52 |   `data object Footer : MarkerKind` |
| 53 |   `data object Compaction : MarkerKind` |
| 63 | `@Composable fun TimelineRow( marker: MarkerKind, @Suppress("UNUSED_PARAMETER") connectTop: Boolean, @Suppress("UNUSED_PARAMETER") connectBottom: Boolean, startsGroup: Boolean, modifier: Modifier = Modifier, rail: RailStyle = RailStyle.Standalone, content: @Composable () -> Unit, )` |
| 72 |   `val topPad` |
| 77 |   `val bottomPad` |
| 81 |   `val rowModifier` |

# feature/sessiondetail

Source root: `app/src/main/java/app/codeg/android/`

## feature/sessiondetail/AgentOptionsSheet.kt

| Line | Declaration |
|---|---|
| 75 | `private enum class SheetPage` |
| 80 | `private const val MODE_APPLY` |
| 91 | `@Composable fun AgentOptionsSheet( viewModel: SessionDetailViewModel, onOpenSession: (Int) -> Unit, onDismiss: () -> Unit, )` |
| 96 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 97 |   `val colors` |
| 98 |   `val scope` |
| 99 |   `var page by remember` |
| 101 |   `var loading by remember` |
| 102 |   `var data by remember` |
| 103 |   `var currentMode by remember` |
| 104 |   `var currentValues by remember` |
| 105 |   `val toaster` |
| 108 |   `var applying by remember` |
| 109 |   `val applyFailedNote` |
| 224 | `@Composable private fun AgentPickerSection(agents: List<AgentType>, selected: AgentType, onSelect: (AgentType) -> Unit)` |
| 225 |   `val colors` |
| 269 | `@Composable private fun WorkspaceSection( ui: SessionDetailUiState, onSelectFolder: (FolderDetail) -> Unit, onOpenBranch: () -> Unit, )` |
| 274 |   `val colors` |
| 286 | `@Composable private fun FolderMenuRow(ui: SessionDetailUiState, onSelectFolder: (FolderDetail) -> Unit)` |
| 299 | `@Composable private fun FolderReadOnlyRow(ui: SessionDetailUiState)` |
| 300 |   `val colors` |
| 315 | `@Composable private fun BranchRow(current: String?, isBusy: Boolean, onClick: () -> Unit)` |
| 316 |   `val colors` |
| 328 | `@Composable private fun PathLine(path: String)` |
| 337 | `@Composable private fun BranchPickerContent( viewModel: SessionDetailViewModel, onBack: () -> Unit, onOpenSession: (Int) -> Unit, )` |
| 342 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 343 |   `val colors` |
| 344 |   `val scope` |
| 345 |   `var loading by remember` |
| 346 |   `var list by remember` |
| 347 |   `var query by remember` |
| 348 |   `var switching by remember` |
| 349 |   `var showNew by remember` |
| 350 |   `var newName by remember` |
| 351 |   `var creating by remember` |
| 352 |   `val isBusy` |
| 353 |   `val current` |
| 360 |   `fun pick(display: String, target: String, isRemote: Boolean)` |
| 374 |   `val worktree` |
| 439 | `@Composable private fun NewBranchInline( show: Boolean, name: String, current: String?, creating: Boolean, onShow: () -> Unit, onName: (String) -> Unit, onCancel: () -> Unit, onCreate: () -> Unit, )` |
| 449 |   `val colors` |
| 483 | `@Composable private fun ConfigOptionSection(option: SessionConfigOption, currentValue: String?, optionApplying: Boolean, locked: Boolean, onSelect: (String) -> Unit)` |
| 484 |   `val colors` |
| 485 |   `val flat: List<SessionConfigSelectOption>` |
| 500 | `@Composable private fun OptionGroup(content: @Composable () -> Unit)` |
| 505 | `@Composable private fun GroupDivider()` |
| 510 | `@Composable private fun OptionRow(title: String, description: String?, selected: Boolean, busy: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 511 |   `val colors` |

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
| 55 | `@Composable fun ComposeBar( text: String, onTextChange: (String) -> Unit, onSend: () -> Unit, isInFlight: Boolean, onStop: () -> Unit, modifier: Modifier = Modifier, onPlus: (() -> Unit)? = null, onAttach: (() -> Unit)? = null, canSendOverride: Boolean = false, attachments: (@Composable ColumnScope.() -> Unit)? = null, )` |
| 68 |   `val colors` |
| 69 |   `val web` |
| 70 |   `val canSend` |
| 71 |   `val interaction` |
| 72 |   `val focused by interaction.collectIsFocusedAsState()` |
| 73 |   `val box` |
| 74 |   `val halo` |
| 143 | `@Composable private fun GhostIconButton(onClick: () -> Unit, content: @Composable () -> Unit)` |
| 158 | `@Composable private fun SendStopButton(isInFlight: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 159 |   `val colors` |
| 160 |   `val stopWash` |

## feature/sessiondetail/ComposeInsertSheet.kt

| Line | Declaration |
|---|---|
| 34 | `object ComposeInsert` |
| 35 |   `fun appendMessage(content: String, draft: String): String` |
| 38 |   `fun appendCommand(name: String, draft: String): String` |
| 42 |   `fun applyExpert(id: String, draft: String, agent: AgentType, knownIds: Set<String>): String` |
| 50 | `private enum class InsertSource` |
| 54 | `@Composable fun ComposeInsertSheet( viewModel: SessionDetailViewModel, onInsert: ((String) -> String) -> Unit, onDismiss: () -> Unit, )` |
| 59 |   `val colors` |
| 60 |   `var source by remember` |
| 61 |   `var query by remember(source)` |
| 62 |   `var loading by remember` |
| 63 |   `var quick by remember` |
| 64 |   `var experts by remember` |
| 65 |   `var commands by remember` |
| 66 |   `var knownIds by remember` |

## feature/sessiondetail/interactive/AskQuestionCard.kt

| Line | Declaration |
|---|---|
| 44 | `private const val OTHER_KEY` |
| 48 | `@Composable fun AskQuestionCard( questions: List<QuestionSpec>, onSubmit: (QuestionAnswer) -> Unit, onSkip: () -> Unit, modifier: Modifier = Modifier, )` |
| 54 |   `val colors` |
| 55 |   `val selections` |
| 56 |   `val otherText` |
| 57 |   `var submitting by remember(questions)` |
| 59 |   `fun answered(q: QuestionSpec): Boolean` |
| 65 |   `val canSubmit` |
| 124 | `@Composable private fun QuestionBlock( question: QuestionSpec, showHeader: Boolean, selected: Set<String>, otherText: String, onToggle: (String) -> Unit, onOtherText: (String) -> Unit, )` |
| 132 |   `val colors` |
| 170 | `@Composable private fun OptionRow(label: String, description: String, checked: Boolean, multi: Boolean, onClick: () -> Unit)` |
| 171 |   `val colors` |
| 184 | `private fun buildAnswer( questions: List<QuestionSpec>, selections: Map<String, Set<String>>, otherText: Map<String, String>, ): QuestionAnswer` |
| 189 |   `val items` |

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
| 47 | `@Composable fun PermissionRequestCard( parsed: ParsedPermission, options: List<PermissionOption>, onRespond: (String) -> Unit, modifier: Modifier = Modifier, )` |
| 53 |   `val colors` |
| 54 |   `var submitting by remember(parsed.jsonPreview)` |
| 55 |   `val accent` |
| 107 | `@Composable private fun PermissionBody(parsed: ParsedPermission)` |
| 108 |   `val colors` |
| 161 | `@Composable private fun OptionButton(option: PermissionOption, submitting: Boolean, enabled: Boolean, onClick: () -> Unit)` |
| 162 |   `val colors` |
| 163 |   `val isReject` |
| 166 |   `val clickable` |

## feature/sessiondetail/interactive/PlanApprovalCard.kt

| Line | Declaration |
|---|---|
| 57 | `@Composable fun PlanApprovalCard( planMarkdown: String, onAnswer: (PlanApprovalDecision, String?) -> Unit, modifier: Modifier = Modifier, )` |
| 62 |   `val colors` |
| 63 |   `val plan` |
| 64 |   `var submitting by remember(planMarkdown)` |
| 65 |   `var changesOpen by remember(planMarkdown)` |
| 66 |   `var feedback by remember(planMarkdown)` |
| 67 |   `val notes` |
| 69 |   `fun submit(decision: PlanApprovalDecision, text: String?)` |

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
| 34 | `@Composable fun InlineImage(image: ImageData, caption: String?, modifier: Modifier = Modifier)` |
| 35 |   `var preview by remember(image.data)` |
| 36 |   `val colors` |
| 37 |   `val bitmap` |

## feature/sessiondetail/rendering/LivePlanView.kt

| Line | Declaration |
|---|---|
| 30 | `private val SuccessGreen: androidx.compose.ui.graphics.Color @Composable @ReadOnlyComposable get()` |
| 34 | `@Composable fun LivePlanView(entries: List<PlanEntry>, modifier: Modifier = Modifier)` |
| 36 |   `val colors` |
| 37 |   `val done` |
| 57 | `@Composable private fun PlanRow(entry: PlanEntry)` |
| 58 |   `val colors` |
| 59 |   `val completed` |

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
| 34 | `@Composable fun ReasoningBlock(text: String, modifier: Modifier = Modifier, initiallyExpanded: Boolean = false)` |
| 35 |   `val colors` |
| 36 |   `var expanded by remember(text)` |

## feature/sessiondetail/rendering/ToolCallCard.kt

| Line | Declaration |
|---|---|
| 47 | `private val SuccessGreen: Color @Composable @ReadOnlyComposable get()` |
| 48 | `private val ErrorRed: Color @Composable @ReadOnlyComposable get()` |
| 55 | `@Composable fun ToolCallCard(vm: ToolCallVM, modifier: Modifier = Modifier, nested: Boolean = false)` |
| 56 |   `val colors` |
| 57 |   `val autoExpand` |
| 60 |   `var expanded by remember(vm.id)` |
| 61 |   `val hasBody` |
| 105 | `@Composable private fun ToolBody(vm: ToolCallVM)` |
| 106 |   `val diff` |
| 114 |   `val input` |
| 130 | `@Composable private fun BashInputBody(input: String)` |
| 131 |   `val parsed` |
| 132 |   `val args` |
| 133 |   `val cmd` |
| 144 | `@Composable private fun TodoInputBody(input: String)` |
| 145 |   `val colors` |
| 146 |   `val args` |
| 147 |   `val todos` |
| 187 | `@Composable private fun ToolOutputSection(vm: ToolCallVM)` |
| 191 |   `val out` |
| 220 | `@Composable private fun ToolStateIcon(vm: ToolCallVM)` |
| 221 |   `val colors` |
| 232 | `@Composable fun ToolGroupCard(items: List<ToolCallVM>, modifier: Modifier = Modifier, streaming: Boolean = false)` |
| 233 |   `val colors` |
| 236 |   `var expanded by remember(items.firstOrNull()?.id)` |
| 237 |   `val errorCount` |
| 279 | `fun bucketIcon(bucket: ToolKindBucket): ImageVector` |
| 291 | `private fun groupSummary(context: Context, items: List<ToolCallVM>): String` |
| 292 |   `val counts` |
| 293 |   `val order` |
| 297 |   `val res` |
| 298 |   `val phrases` |
| 316 | `private fun JsonObject.str(key: String): String?` |
| 317 |   `val prim` |

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
| 56 | `private val lenient` |
| 57 | `private val prettyJson` |
| 61 | `private fun parseAny(s: String?): JsonElement?` |
| 67 | `private fun JsonObject.strField(key: String): String?` |
| 68 |   `val prim` |
| 73 | `private fun scalarString(prim: JsonPrimitive): String` |
| 76 | `private fun prettyPrint(el: JsonElement): String` |
| 83 | `fun prettyPrintJsonString(s: String): String?` |
| 84 |   `val any` |
| 90 | `private val codeFields` |
| 96 | `private val hiddenFields` |
| 104 | `object ToolOutputFormat` |
| 106 |   `enum class Kind` |
| 111 |   `fun commandOutput(output: String): String?` |
| 136 |   `private val metaLine` |
| 140 |   `private val outputSepLine` |
| 142 |   `private fun isMetaLine(s: String)` |
| 146 |   `fun parseCliEnvelope(text: String): String` |
| 177 |   `fun stripMarkdownFence(text: String): String` |
| 186 |   `fun cleanCommandOutput(source: String): String` |
| 193 |   `fun classify(output: String): Kind` |
| 201 |   `fun looksLikeMarkdown(s: String): Boolean` |
| 224 |   `fun mcpResultText(output: String): String?` |
| 242 |   `fun errorFields(errorText: String): List<Pair<String, String>>?` |
| 254 | `object ReadOutputFormat` |
| 255 |   `data class File(val content: String, val startLine: Int)` |
| 257 |   `fun parse(output: String): File?` |
| 273 | `object ToolFieldLabel` |
| 274 |   `private val map` |
| 292 |   `fun label(key: String): String` |
| 295 |   `private fun humanize(key: String): String` |
| 310 | `@Composable fun ToolFieldInline(label: String, value: String)` |
| 311 |   `val colors` |
| 326 | `@Composable fun ToolFieldBlock(label: String, content: @Composable () -> Unit)` |
| 327 |   `val colors` |
| 337 | `@Composable fun PlainTextBlock(text: String)` |
| 338 |   `val colors` |
| 357 | `@Composable fun StructuredJsonView(json: String, inlineMaxLength: Int = 200)` |
| 358 |   `val obj` |
| 376 | `@Composable private fun ToolField(key: String, value: JsonElement, inlineMaxLength: Int)` |
| 377 |   `val label` |
| 392 | `private fun codeLanguage(key: String): String?` |
| 402 | `@Composable fun FileBodyView(file: ReadOutputFormat.File, collapsedLineLimit: Int = 60)` |
| 403 |   `val colors` |
| 404 |   `val content` |
| 405 |   `val lines` |
| 406 |   `val isLong` |
| 407 |   `var expanded by remember(file)` |
| 408 |   `val shown` |
| 409 |   `val maxNo` |
| 410 |   `val digits` |
| 411 |   `val gutterWidth` |
| 466 | `@Composable fun ToolErrorOutput(output: String)` |
| 467 |   `val colors` |
| 468 |   `val fields` |

## feature/sessiondetail/SessionActions.kt

| Line | Declaration |
|---|---|
| 57 | `@Composable fun SessionActionsMenu( ui: SessionDetailUiState, viewModel: SessionDetailViewModel, onDeleted: () -> Unit, )` |
| 62 |   `val colors` |
| 63 |   `val summary` |
| 64 |   `val connectionActions by viewModel.sessionActions.collectAsState()` |
| 65 |   `var menu by remember` |
| 66 |   `var showRename by remember` |
| 67 |   `var showStatus by remember` |
| 68 |   `var showDetails by remember` |
| 69 |   `var showDelete by remember` |
| 168 | `@Composable private fun DetailRow(label: String, value: String, valueColor: androidx.compose.ui.graphics.Color? = null)` |
| 169 |   `val colors` |

## feature/sessiondetail/SessionDetailScreen.kt

| Line | Declaration |
|---|---|
| 95 | `@Composable fun SessionDetailScreen( onBack: () -> Unit, onOpenSession: (Int) -> Unit = {}, onOpenServers: () -> Unit = {}, viewModel: SessionDetailViewModel = hiltViewModel(), )` |
| 101 |   `val ui by viewModel.ui.collectAsStateWithLifecycle()` |
| 102 |   `val streamStatus by viewModel.streamStatus.collectAsStateWithLifecycle()` |
| 107 |   `val toaster` |
| 108 |   `val noticeTitle` |
| 109 |   `val disconnectedNotice` |
| 110 |   `val droppedNotesNotice` |
| 121 |   `val colors` |
| 122 |   `var draft by remember` |
| 123 |   `var showInsert by remember` |
| 124 |   `var showOptions by remember` |
| 125 |   `val listState` |
| 126 |   `val scope` |
| 127 |   `val context` |
| 143 |   `val pickImages` |
| 159 |   `val hideReattachDup` |
| 160 |   `val persistedNodes` |
| 163 |   `val nodes` |
| 166 |   `val hasContent` |
| 169 |   `val isAtBottom by remember` |
| 176 |   `var stick by remember` |
| 192 |   `val scrollToId: (String) -> Unit` |
| 202 |   `val title` |
| 207 |   `val agentOptionsLabel` |
| 371 | `@Composable private fun JumpToLatestButton(onClick: () -> Unit)` |
| 372 |   `val colors` |
| 392 | `@Composable private fun SendStatusLine(status: String)` |
| 393 |   `val colors` |
| 406 | `@Composable private fun NoticeBanner( message: String, onDismiss: () -> Unit, actionLabel: String? = null, onAction: () -> Unit = {}, )` |
| 412 |   `val colors` |
| 440 | `@Composable private fun AttachmentStrip(attachments: List<Attachment>, onRemove: (String) -> Unit)` |
| 441 |   `val colors` |

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
| 160 |   `private val _sessionActions` |
| 161 |   `val sessionActions: StateFlow<SessionConnectionActions>` |
| 162 |   `private var preflightSupported` |
| 172 |   `fun load()` |
| 251 |   `fun send(text: String)` |
| 260 |   `private fun send(text: String, withAttachments: Boolean)` |
| 307 |   `private suspend fun runSend( text: String, userTurn: MessageTurn, attachments: List<Attachment>, createdConversation: Boolean, )` |
| 347 |   `private suspend fun attemptSend( c: CodegClient, text: String, attachments: List<Attachment>, clientMessageId: String, freshConnection: Boolean, )` |
| 372 |   `private suspend fun connectFresh(c: CodegClient): String` |
| 382 |   `private suspend fun connectWithPreferences( c: CodegClient, connectAgent: AgentType = agent, connectFolder: FolderDetail? = folder, ): String` |
| 398 |   `private suspend fun ensureConversationCreated(firstPrompt: String)` |
| 407 |   `private suspend fun resolveConnection(c: CodegClient): String` |
| 422 |   `private suspend fun openStream(conn: String, seedOnAttach: Boolean = false)` |
| 436 |   `private suspend fun connectStream(conn: String, awaitAttach: Boolean, seedOnAttach: Boolean = false)` |
| 522 |   `fun cancel()` |
| 541 |   `private fun reattachIfLive()` |
| 582 |   `private fun applyEvent(event: AcpEvent, seq: Long)` |
| 659 |   `private fun clearInteractivePrompts()` |
| 676 |   `private fun runningStatus(): String` |
| 685 |   `private fun adoptLinkedConversation(id: Int)` |
| 701 |   `fun respondPermission(optionId: String)` |
| 711 |   `fun answerQuestion(answer: app.codeg.android.core.model.QuestionAnswer)` |
| 721 |   `fun dismissQuestion()` |
| 734 |   `fun answerPlanApproval(decision: PlanApprovalDecision, feedback: String?)` |
| 771 |   `private fun seedLiveFromSnapshot(snap: app.codeg.android.core.model.LiveSessionSnapshot): Boolean` |
| 792 |   `private fun abortIdleReattach()` |
| 803 |   `private fun reconcileAfterMissedLive()` |
| 815 |   `private fun restorePending(snap: app.codeg.android.core.model.LiveSessionSnapshot)` |
| 835 |   `private fun finalizeTurn(stopReason: String)` |
| 864 |   `private fun failLive(message: String)` |
| 875 |   `private fun handleStreamEnded(gen: Int, reason: String?)` |
| 896 |   `fun recoverStream()` |
| 907 |   `fun dismissStreamStatus()` |
| 911 |   `fun resumeStream()` |
| 918 |   `fun pauseStream()` |
| 922 |   `private fun errorNotice(error: Throwable): String?` |
| 925 |   `private fun stopForStreamAuth()` |
| 935 |   `private fun reconnectStream(immediate: Boolean = false)` |
| 965 |   `private fun reconcileAfterTurn()` |
| 1026 |   `private fun promoteUnreconciled(markInterrupted: Boolean = false)` |
| 1057 |   `private fun discardOptimistic( userTurn: MessageTurn, text: String, attachments: List<Attachment>, createdConversation: Boolean, createdConversationId: Int?, notice: String?, )` |
| 1107 |   `fun consumeRestoredDraft()` |
| 1111 |   `fun dismissNotice()` |
| 1117 |   `private fun scheduleLiveEmit()` |
| 1127 |   `private fun emitLiveNow()` |
| 1132 |   `private fun closeStream()` |
| 1143 |   `override fun onCleared()` |
| 1154 |   `private fun unsupportedSessionCommand(e: Exception): Boolean` |
| 1158 |   `private suspend fun preflight(c: CodegClient, target: AgentType)` |
| 1172 |   `fun forkSession()` |
| 1173 |   `fun disconnectSession()` |
| 1175 |   `private fun sessionConnectionAction(fork: Boolean)` |
| 1225 |   `val canManage: Boolean get()` |
| 1226 |   `val isPinned: Boolean get()` |
| 1228 |   `fun rename(title: String)` |
| 1242 |   `fun togglePin()` |
| 1257 |   `fun setStatus(status: ConversationStatus)` |
| 1270 |   `fun deleteConversation(onDeleted: () -> Unit)` |
| 1285 |   `val agentForInsert: AgentType get()` |
| 1287 |   `suspend fun loadQuickMessages(): List<QuickMessage>` |
| 1291 |   `suspend fun loadExpertsForInsert(): List<ExpertListItem>` |
| 1299 |   `suspend fun loadSlashCommands(): List<AvailableCommandInfo>` |
| 1307 |   `suspend fun knownExpertIds(): Set<String>` |
| 1315 |   `fun addAttachments(uris: List<Uri>, resolver: ContentResolver)` |
| 1334 |   `fun removeAttachment(id: String)` |
| 1344 |   `fun selectAgent(newAgent: AgentType)` |
| 1353 |   `fun selectFolder(newFolder: FolderDetail)` |
| 1370 |   `suspend fun loadBranches(): GitBranchList?` |
| 1389 |   `suspend fun switchBranch(branch: String, isRemote: Boolean): BranchSwitchOutcome` |
| 1423 |   `private suspend fun checkoutInRoot(c: CodegClient, active: FolderDetail, root: FolderDetail, branch: String): BranchSwitchOutcome` |
| 1446 |   `private suspend fun recoverFromWorktreeCheckout(c: CodegClient, branch: String, root: FolderDetail, error: Exception): BranchSwitchOutcome?` |
| 1456 |   `private suspend fun openWorktreeSession(c: CodegClient, path: String, root: FolderDetail): BranchSwitchOutcome?` |
| 1464 |   `private fun worktreePathFromError(message: String): String?` |
| 1472 |   `suspend fun createBranch(name: String, from: String?): Boolean` |
| 1496 |   `suspend fun loadAgentOptions(): AgentOptionsData` |
| 1530 |   `private suspend fun optionsConnection(forAgent: AgentType, forFolder: FolderDetail?): String?` |
| 1586 |   `suspend fun applyMode(modeId: String): String?` |
| 1607 |   `suspend fun applyConfig(configId: String, valueId: String): String?` |
| 1630 |   `private fun dropStaleOptionsConnection(conn: String)` |
| 1638 |   `private suspend fun reconcileSelection(conn: String, key: String, desired: String): String` |
| 1657 | `private class StreamAttachFailed(val reason: String?) : Exception("stream attach failed: ${reason ?: "closed"}")` |
| 1661 | `private class OptionsConnect(val agent: AgentType, val folderId: Int?, val deferred: Deferred<String>)` |
| 1665 | `data class AgentOptionsData( val modes: SessionModeState? = null, val configOptions: List<SessionConfigOption> = emptyList(), val initialModeId: String? = null, val initialConfig: Map<String, String> = emptyMap(), )` |
| 1666 |   `val modes: SessionModeState?` |
| 1667 |   `val configOptions: List<SessionConfigOption>` |
| 1668 |   `val initialModeId: String?` |
| 1669 |   `val initialConfig: Map<String, String>` |
| 1673 | `private fun SessionSnapshot.selectionFor(key: String): String?` |
| 1678 | `private fun List<SessionConfigOption>.currentValues(): Map<String, String>` |
| 1683 | `private fun SessionConfigKind.allSelectValues(): List<String>` |
| 1687 | `sealed interface BranchSwitchOutcome` |
| 1689 |   `data object Noop : BranchSwitchOutcome` |
| 1691 |   `data object SwitchedInPlace : BranchSwitchOutcome` |
| 1693 |   `data class OpenSession(val folderId: Int) : BranchSwitchOutcome` |
| 1695 |   `data object Failed : BranchSwitchOutcome` |
| 1698 | `data class SessionDetailUiState( val isNew: Boolean, val loading: Boolean = false, val summary: ConversationSummary? = null, val folderName: String? = null, val folderPath: String? = null, val selectedFolderId: Int? = null, val currentBranch: String? = null, val isDraftEditable: Boolean = false, val availableFolders: List<FolderDetail> = emptyList(), val availableAgents: List<AgentType> = emptyList(), val turns: List<MessageTurn> = emptyList(), val interruptedTurnIds: Set<String> = emptySet(), val pendingUserTurns: List<MessageTurn> = emptyList(), val live: LiveTurnState? = null, /** The live turn was rebuilt from a reattach snapshot (owns the in-flight reply). */ val liveFromReattach: Boolean = false, val sessionStats: SessionStats? = null, val agent: AgentType = AgentType.CLAUDE_CODE, val isInFlight: Boolean = false, val sendStatus: String? = null, val notice: String? = null, /** Set when a failed send returns the user's typed text to the composer (consumed once). */ val restoreDraft: String? = null, val error: String? = null, val pendingPermission: PendingPermissionUi? = null, val pendingQuestion: PendingQuestionUi? = null, val pendingPlanApproval: PendingPlanApprovalUi? = null, val attachments: List<Attachment> = emptyList(), )` |
| 1699 |   `val isNew: Boolean` |
| 1700 |   `val loading: Boolean` |
| 1701 |   `val summary: ConversationSummary?` |
| 1702 |   `val folderName: String?` |
| 1703 |   `val folderPath: String?` |
| 1704 |   `val selectedFolderId: Int?` |
| 1705 |   `val currentBranch: String?` |
| 1706 |   `val isDraftEditable: Boolean` |
| 1707 |   `val availableFolders: List<FolderDetail>` |
| 1708 |   `val availableAgents: List<AgentType>` |
| 1709 |   `val turns: List<MessageTurn>` |
| 1710 |   `val interruptedTurnIds: Set<String>` |
| 1711 |   `val pendingUserTurns: List<MessageTurn>` |
| 1712 |   `val live: LiveTurnState?` |
| 1714 |   `val liveFromReattach: Boolean` |
| 1715 |   `val sessionStats: SessionStats?` |
| 1716 |   `val agent: AgentType` |
| 1717 |   `val isInFlight: Boolean` |
| 1718 |   `val sendStatus: String?` |
| 1719 |   `val notice: String?` |
| 1721 |   `val restoreDraft: String?` |
| 1722 |   `val error: String?` |
| 1723 |   `val pendingPermission: PendingPermissionUi?` |
| 1724 |   `val pendingQuestion: PendingQuestionUi?` |
| 1725 |   `val pendingPlanApproval: PendingPlanApprovalUi?` |
| 1726 |   `val attachments: List<Attachment>` |
| 1730 | `internal enum class PlanFollowUp` |
| 1741 | `internal data class ParkedPlanNotes(val turnId: String, val notes: String)` |
| 1743 |   `fun notesFor(turnId: String): String?` |
| 1762 | `internal fun planFollowUpAction( sameTurn: Boolean, inFlight: Boolean, stopReason: String?, errorMessage: String?, ): PlanFollowUp` |
| 1775 | `data class PendingPermissionUi( val requestId: String, val parsed: app.codeg.android.feature.sessiondetail.interactive.ParsedPermission, val options: List<app.codeg.android.core.model.PermissionOption>, )` |
| 1776 |   `val requestId: String` |
| 1777 |   `val parsed: app.codeg.android.feature.sessiondetail.interactive.ParsedPermission` |
| 1778 |   `val options: List<app.codeg.android.core.model.PermissionOption>` |
| 1782 | `data class PendingQuestionUi( val questionId: String, val questions: List<app.codeg.android.core.model.QuestionSpec>, )` |
| 1783 |   `val questionId: String` |
| 1784 |   `val questions: List<app.codeg.android.core.model.QuestionSpec>` |
| 1793 | `data class PendingPlanApprovalUi( val approvalId: String, /** Grok's `toolCallId` for the `exit_plan_mode` call. Not used for the answer (which * keys on [approvalId]) — kept so the card can correlate with the in-stream call. */ val toolCallId: String, /** The plan, read from Grok's `plan.md`. May be empty — the card then shows an * empty-state notice rather than hiding, since the turn is blocked either way. */ val planMarkdown: String, )` |
| 1794 |   `val approvalId: String` |
| 1797 |   `val toolCallId: String` |
| 1800 |   `val planMarkdown: String` |
| 1804 | `data class SessionConnectionActions( val forkSupported: Boolean = true, val disconnectSupported: Boolean = true, val busy: Boolean = false, )` |
| 1805 |   `val forkSupported: Boolean` |
| 1806 |   `val disconnectSupported: Boolean` |
| 1807 |   `val busy: Boolean` |
| 1810 | `internal fun suppressAuthNotice(error: Throwable, streamNotice: StreamNotice?): Boolean` |
| 1814 | `internal fun isInterruptedTurn(stopReason: String?, transcriptCompleted: Boolean): Boolean` |
| 1818 | `internal fun hasReconciledReply( turns: List<MessageTurn>, baseline: Int, liveFromReattach: Boolean, ): Boolean` |
| 1823 |   `val last` |

## feature/sessiondetail/StreamReconnectPolicy.kt

| Line | Declaration |
|---|---|
| 3 | `internal const val MAX_STREAM_RECONNECTS` |
| 5 | `internal fun streamReconnectDelay(attempt: Int): Long` |
| 8 | `internal enum class StreamRecovery` |
| 10 | `internal fun streamRecovery( inFlight: Boolean, authFailed: Boolean, connectionGone: Boolean, attempts: Int, immediate: Boolean, ): StreamRecovery` |
| 24 | `data class StreamNotice(val message: String, val openServers: Boolean = false)` |

## feature/sessiondetail/T55BranchChoice.kt

| Line | Declaration |
|---|---|
| 4 | `internal data class T55BranchChoice( val id: String, val target: String, val current: Boolean, val occupied: Boolean, val enabled: Boolean, )` |
| 5 |   `val id: String` |
| 6 |   `val target: String` |
| 7 |   `val current: Boolean` |
| 8 |   `val occupied: Boolean` |
| 9 |   `val enabled: Boolean` |
| 12 | `internal fun t55BranchChoice( name: String, remote: Boolean, current: String?, worktree: Set<String>, busy: Boolean, ): T55BranchChoice` |
| 19 |   `val target` |
| 20 |   `val isCurrent` |
| 21 |   `val occupied` |

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
| 44 |   `data object Interrupted : NodeContent` |
| 45 |   `data object Thinking : NodeContent` |
| 46 |   `data class Error(val message: String) : NodeContent` |
| 47 |   `data class Unsupported(val type: String) : NodeContent` |
| 56 | `data class TimelineNode( val id: String, val content: NodeContent, val agent: AgentType, val connectTop: Boolean = true, val connectBottom: Boolean = true, val startsGroup: Boolean = false, val rail: RailStyle = RailStyle.Standalone, )` |
| 57 |   `val id: String` |
| 58 |   `val content: NodeContent` |
| 59 |   `val agent: AgentType` |
| 60 |   `val connectTop: Boolean` |
| 61 |   `val connectBottom: Boolean` |
| 62 |   `val startsGroup: Boolean` |
| 63 |   `val rail: RailStyle` |
| 65 |   `val marker: MarkerKind` |
| 85 |   `val contentTypeKey: Any` |
| 114 | `internal fun systemText(turn: MessageTurn): String` |
| 120 | `internal fun List<MessageTurn>.dropTrailingAssistantRun(): List<MessageTurn>` |
| 121 |   `var end` |
| 132 | `object TranscriptTimeline` |
| 134 |   `fun build( turns: List<MessageTurn>, pending: List<MessageTurn>, live: LiveTurnState?, agent: AgentType, hideTrailingAssistantReply: Boolean = false, interruptedTurnIds: Set<String> = emptySet(), ): List<TimelineNode>` |
| 155 |   `fun buildPersisted( turns: List<MessageTurn>, pending: List<MessageTurn>, agent: AgentType, hideTrailingAssistantReply: Boolean = false, interruptedTurnIds: Set<String> = emptySet(), ): List<TimelineNode>` |
| 199 |   `fun withLive(persisted: List<TimelineNode>, live: LiveTurnState?, agent: AgentType): List<TimelineNode>` |
| 211 |   `private fun assistantNodes(merged: MessageTurn, questionId: String?, agent: AgentType): List<TimelineNode>` |
| 220 |   `private fun liveNodes(live: LiveTurnState, agent: AgentType): List<TimelineNode>` |
| 247 |   `private fun liveParts(live: LiveTurnState): List<RenderPart>` |
| 273 |   `private fun nodesFor(part: RenderPart, ownerId: String, index: Int, agent: AgentType, cached: Boolean): List<TimelineNode>` |
| 299 |   `private fun textBlockNodes(text: String, base: String, agent: AgentType, cached: Boolean): List<TimelineNode>` |
| 315 |   `fun merge(group: List<MessageTurn>): MessageTurn` |
| 339 | `private fun LiveToolCallState.toVM(): ToolCallVM` |
| 340 |   `val state` |

## feature/sessiondetail/timeline/TimelineNodeBody.kt

| Line | Declaration |
|---|---|
| 62 | `private val SuccessGreen: Color @Composable @ReadOnlyComposable get()` |
| 65 | `val LocalTimelineScroll` |
| 73 | `@Composable fun NodeBody(node: TimelineNode, modifier: Modifier = Modifier)` |
| 110 | `@Composable private fun UserNodeBody(turn: MessageTurn, modifier: Modifier)` |
| 111 |   `val colors` |
| 136 | `@Composable private fun SystemNodeBody(turn: MessageTurn, modifier: Modifier)` |
| 137 |   `val text` |
| 148 | `@Composable private fun TurnFooter(turn: MessageTurn, questionId: String?, modifier: Modifier)` |
| 149 |   `val colors` |
| 152 |   `val copyText` |
| 157 |   `val meta` |
| 180 | `@Composable private fun CopyButton(text: String)` |
| 181 |   `val colors` |
| 182 |   `val clipboard` |
| 183 |   `var copied by remember` |
| 207 | `@Composable private fun JumpToQuestionButton(questionId: String)` |
| 208 |   `val scroll` |
| 229 | `@Composable fun ThinkingShimmer(modifier: Modifier = Modifier)` |
| 230 |   `val colors` |
| 231 |   `val transition` |
| 256 | `@Composable private fun BlinkingCaret()` |
| 257 |   `val transition` |
| 258 |   `val a by transition.animateFloat( initialValue = 1f, targetValue = 0f, animationSpec = infiniteRepeatable(tween(530), RepeatMode.Reverse), label = "caret-alpha", )` |
| 278 | `@Composable fun ContextCompactionDivider(before: Int?, after: Int?, running: Boolean, modifier: Modifier = Modifier)` |
| 279 |   `val colors` |
| 280 |   `val label` |
| 301 | `private fun groupDigits(value: Int): String` |
| 305 | `@Composable fun InlineTurnError(message: String, modifier: Modifier = Modifier)` |
| 306 |   `val colors` |
| 322 | `private fun compactTokens(n: Int): String` |
| 328 | `private fun trimNum(v: Double): String` |
| 330 | `private fun compactDuration(ms: Int): String` |
| 331 |   `val seconds` |
| 333 |   `val whole` |
| 334 |   `val m` |
| 335 |   `val s` |

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

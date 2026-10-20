# core/designsystem

Source root: `app/src/main/java/app/codeg/android/`

## core/designsystem/CodegBackground.kt

| Line | Declaration |
|---|---|
| 15 | `@Composable fun CodegBackground( modifier: Modifier = Modifier, content: @Composable () -> Unit, )` |

## core/designsystem/component/AgentIcon.kt

| Line | Declaration |
|---|---|
| 23 | `object AgentVisuals` |
| 27 |   `fun icon(agent: AgentType): Int` |
| 54 |   `fun iconIsTemplate(agent: AgentType): Boolean` |
| 72 |   `fun accent(agent: AgentType, isDark: Boolean): Color` |
| 94 |   `@Composable fun accent(agent: AgentType): Color` |
| 105 | `@Composable fun AgentIcon( agent: AgentType, size: Dp, modifier: Modifier = Modifier, tint: Color? = null, )` |
| 111 |   `val painter` |
| 112 |   `val decorated` |

## core/designsystem/component/Badges.kt

| Line | Declaration |
|---|---|
| 37 | `private val Capsule` |
| 41 | `@Composable fun statusColor(status: ConversationStatus): Color` |
| 50 | `fun statusLabel(status: ConversationStatus): String` |
| 60 | `@Composable fun AgentAvatar( agent: AgentType, modifier: Modifier = Modifier, size: Dp = 36.dp, )` |
| 65 |   `val colors` |
| 80 | `@Composable fun AgentBadge(agent: AgentType, modifier: Modifier = Modifier)` |
| 81 |   `val web` |
| 101 | `@Composable fun StatusBadge(status: ConversationStatus, modifier: Modifier = Modifier)` |
| 102 |   `val colors` |
| 127 | `@Composable fun SectionBadgeIcon( icon: ImageVector, tint: Color, modifier: Modifier = Modifier, size: Dp = 26.dp, )` |
| 150 | `@Composable fun SettingsIconBadge( icon: ImageVector, modifier: Modifier = Modifier, tint: Color = CodegTheme.colors.textPrimary, size: Dp = 28.dp, )` |
| 169 | `@Composable fun FolderBadge( color: Color, modifier: Modifier = Modifier, size: Dp = 40.dp, )` |
| 193 | `@Composable fun CountBadge(count: Int, modifier: Modifier = Modifier)` |
| 194 |   `val web` |
| 209 | `@Composable fun LivePulse(modifier: Modifier = Modifier, dotSize: Dp = 8.dp)` |
| 210 |   `val accent` |
| 211 |   `val transition` |
| 212 |   `val progress by transition.animateFloat( initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable( animation = tween(durationMillis = 1100), repeatMode = RepeatMode.Restart, ), label = "pulse-progress", )` |

## core/designsystem/component/Buttons.kt

| Line | Declaration |
|---|---|
| 44 | `@Composable fun PrimaryButton( text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, loading: Boolean = false, icon: ImageVector? = null, )` |
| 52 |   `val colors` |
| 92 | `@Composable fun CodegSegmented( options: List<String>, selectedIndex: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier, )` |
| 98 |   `val colors` |
| 99 |   `val web` |
| 100 |   `val track` |
| 101 |   `val tab` |
| 148 | `@Composable fun CodegFilterChip( label: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier, icon: ImageVector? = null, enabled: Boolean = true, )` |
| 156 |   `val colors` |
| 157 |   `val web` |

## core/designsystem/component/Cards.kt

| Line | Declaration |
|---|---|
| 22 | `@Composable fun CodegCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit)` |
| 34 | `@Composable fun CodegCardHeader( title: String, description: String? = null, action: @Composable RowScope.() -> Unit = {}, )` |
| 49 | `@Composable fun CodegCardContent(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit)` |
| 54 | `@Composable fun CodegCardFooter(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit)` |
| 59 | `@Composable internal fun T44CardsGallery()` |
| 71 | `@Composable private fun CardsLightPreview()` |
| 75 | `@Composable private fun CardsDarkPreview()` |

## core/designsystem/component/CodeBlock.kt

| Line | Declaration |
|---|---|
| 44 | `private val SuccessGreen: Color @Composable @ReadOnlyComposable get()` |
| 53 | `@Composable fun CodeBlock( code: String, modifier: Modifier = Modifier, language: String? = null, collapseAfter: Int = 20, )` |
| 59 |   `val colors` |
| 60 |   `val lines` |
| 61 |   `val collapsible` |
| 62 |   `var expanded by remember(code)` |
| 63 |   `val shown` |
| 119 | `@Composable fun CopyButton(text: String, modifier: Modifier = Modifier)` |
| 120 |   `val clipboard` |
| 121 |   `val colors` |
| 122 |   `var copied by remember` |

## core/designsystem/component/Dialogs.kt

| Line | Declaration |
|---|---|
| 31 | `@Composable fun CodegDialog( title: String, description: String? = null, onDismiss: () -> Unit, footer: @Composable RowScope.() -> Unit = {}, content: @Composable ColumnScope.() -> Unit = {}, )` |
| 69 | `@Composable internal fun T44PanelHeading(title: String, description: String?)` |
| 78 | `@Composable fun CodegConfirmDialog( title: String, message: String, target: String, confirmLabel: String, destructive: Boolean = false, onConfirm: () -> Unit, onDismiss: () -> Unit, )` |
| 87 |   `val web` |
| 101 | `@Composable internal fun T44Gallery()` |
| 102 |   `var panel by remember` |
| 103 |   `val title` |
| 104 |   `val description` |
| 105 |   `val confirm` |
| 114 |   `val dismiss` |
| 132 | `@Composable private fun DialogLightPreview()` |
| 138 | `@Composable private fun DialogDarkPreview()` |
| 144 | `@Composable private fun ConfirmLightPreview()` |
| 147 | `@Composable private fun ConfirmDarkPreview()` |
| 149 | `@Composable private fun T44ConfirmPreview()` |

## core/designsystem/component/FileViewer.kt

| Line | Declaration |
|---|---|
| 31 | `@Composable fun FileViewer(content: FileViewerContent)` |
| 54 | `@Composable private fun FileImage(content: FileViewerContent.Image)` |
| 55 |   `var decoding by remember(content.base64)` |
| 56 |   `val bitmap by produceState<androidx.compose.ui.graphics.ImageBitmap?>(null, content.base64)` |
| 70 |   `var scale by remember(content)` |
| 71 |   `var offset by remember(content)` |
| 72 |   `val transform` |

## core/designsystem/component/Glass.kt

| Line | Declaration |
|---|---|
| 20 | `@Composable fun Modifier.hairlineBorder( shape: Shape, color: Color = CodegTheme.colors.surfaceStroke, width: Dp = CodegTheme.dimens.hairlineWidth, ): Modifier` |
| 32 | `@Composable fun Modifier.webSurface(shape: Shape): Modifier` |
| 33 |   `val colors` |
| 46 | `@Composable fun GlassCard( modifier: Modifier = Modifier, cornerRadius: Dp = CodegTheme.dimens.radiusLg, padding: Dp = 16.dp, content: @Composable ColumnScope.() -> Unit, )` |
| 52 |   `val colors` |
| 53 |   `val shape` |
| 69 | `@Composable fun GlassRow( modifier: Modifier = Modifier, selected: Boolean = false, content: @Composable ColumnScope.() -> Unit, )` |
| 74 |   `val colors` |
| 75 |   `val shape` |
| 76 |   `val fill` |
| 77 |   `val stroke` |

## core/designsystem/component/Sheets.kt

| Line | Declaration |
|---|---|
| 29 | `@Composable fun CodegSheet( title: String, description: String? = null, onDismiss: () -> Unit, footer: @Composable ColumnScope.() -> Unit = {}, content: @Composable ColumnScope.() -> Unit, )` |
| 36 |   `val web` |
| 66 | `@Composable fun CodegFullScreenSheet( title: String, onDismiss: () -> Unit, actions: @Composable RowScope.() -> Unit = {}, content: @Composable ColumnScope.() -> Unit, )` |
| 92 | `@Composable internal fun T44SheetSampleContent()` |
| 93 |   `var value by remember` |
| 100 | `@Composable private fun SheetLightPreview()` |
| 103 | `@Composable private fun SheetDarkPreview()` |
| 105 | `@Composable private fun T44SheetPreview()` |
| 113 | `@Composable private fun FullSheetLightPreview()` |
| 116 | `@Composable private fun FullSheetDarkPreview()` |
| 118 | `@Composable private fun T44FullSheetPreview()` |

## core/designsystem/component/StateViews.kt

| Line | Declaration |
|---|---|
| 26 | `@Composable fun EmptyState( icon: ImageVector, title: String, message: String, modifier: Modifier = Modifier, actionLabel: String? = null, onAction: (() -> Unit)? = null, )` |
| 61 | `@Composable fun LoadingView(message: String, modifier: Modifier = Modifier)` |
| 74 | `@Composable fun InlineError( icon: ImageVector, title: String, message: String, onRetry: () -> Unit, modifier: Modifier = Modifier, retryLabel: String = "Retry", )` |

## core/designsystem/component/TextFields.kt

| Line | Declaration |
|---|---|
| 36 | `@Composable fun CodegTextField( value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier, placeholder: String? = null, keyboardType: KeyboardType = KeyboardType.Text, mono: Boolean = false, singleLine: Boolean = true, )` |
| 46 |   `val colors` |
| 63 | `@Composable fun SecretField( value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier, )` |
| 69 |   `var visible by remember` |
| 94 | `@Composable private fun fieldColors(): TextFieldColors` |
| 95 |   `val colors` |
| 96 |   `val web` |

## core/designsystem/component/TopBar.kt

| Line | Declaration |
|---|---|
| 46 | `@Composable fun CodegTopBar( title: String, modifier: Modifier = Modifier, subtitle: String? = null, navigationIcon: @Composable () -> Unit = {}, actions: @Composable RowScope.() -> Unit = {}, scrollBehavior: TopAppBarScrollBehavior? = null, showDivider: Boolean = false, titleContent: (@Composable () -> Unit)? = null, )` |
| 56 |   `val colors` |
| 101 | `@Composable fun CodegIconButton( onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, visualSize: Dp = 36.dp, content: @Composable () -> Unit, )` |
| 108 |   `val colors` |
| 109 |   `val interactionSource` |
| 110 |   `val pressed by interactionSource.collectIsPressedAsState()` |
| 111 |   `val hovered by interactionSource.collectIsHoveredAsState()` |
| 112 |   `val focused by interactionSource.collectIsFocusedAsState()` |
| 113 |   `val active` |

## core/designsystem/diff/DiffView.kt

| Line | Declaration |
|---|---|
| 35 | `private val AddedFg: Color @Composable @ReadOnlyComposable get()` |
| 36 | `private val DeletedFg: Color @Composable @ReadOnlyComposable get()` |
| 37 | `private val AddedBg: Color @Composable @ReadOnlyComposable get()` |
| 38 | `private val DeletedBg: Color @Composable @ReadOnlyComposable get()` |
| 42 | `@Composable fun DiffView(files: List<DiffFile>, modifier: Modifier = Modifier, collapseAfter: Int = 80)` |
| 49 | `@Composable private fun DiffFileView(file: DiffFile, collapseAfter: Int)` |
| 50 |   `val colors` |
| 51 |   `val allRows` |
| 52 |   `val collapsible` |
| 53 |   `var expanded by remember(file)` |
| 54 |   `val rows` |
| 105 | `@Composable private fun DiffRowView(row: DiffRow)` |
| 106 |   `val colors` |
| 107 |   `val (fg, bg, sign)` |
| 137 | `@Composable private fun Gutter(line: Int?)` |
| 148 | `@Composable private fun ModeBadge(mode: DiffFile.Mode)` |
| 149 |   `val (label, color)` |
| 167 | `private fun shortPath(path: String): String` |
| 168 |   `val parts` |

## core/designsystem/diff/UnifiedDiff.kt

| Line | Declaration |
|---|---|
| 4 | `data class DiffRow( val kind: Kind, val oldLine: Int?, val newLine: Int?, val text: String, )` |
| 5 |   `val kind: Kind` |
| 6 |   `val oldLine: Int?` |
| 7 |   `val newLine: Int?` |
| 8 |   `val text: String` |
| 10 |   `enum class Kind` |
| 14 | `data class DiffHunk(val header: String?, val rows: List<DiffRow>)` |
| 17 | `data class DiffFile( val path: String, val oldPath: String?, val mode: Mode, val additions: Int, val deletions: Int, val hunks: List<DiffHunk>, )` |
| 18 |   `val path: String` |
| 19 |   `val oldPath: String?` |
| 20 |   `val mode: Mode` |
| 21 |   `val additions: Int` |
| 22 |   `val deletions: Int` |
| 23 |   `val hunks: List<DiffHunk>` |
| 25 |   `enum class Mode` |
| 27 |   `val isNewFile: Boolean get()` |
| 36 | `object UnifiedDiff` |
| 38 |   `private val applyPatchHeader` |
| 39 |   `private val hunkAtStart` |
| 40 |   `private val minusHeader` |
| 41 |   `private val plusHeader` |
| 42 |   `private val hunkRegex` |
| 44 |   `fun parse(raw: String): List<DiffFile>?` |
| 53 |   `fun looksLikeDiff(raw: String): Boolean` |
| 61 |   `private fun parseGitUnified(text: String): List<DiffFile>` |
| 77 |     `fun flushHunk()` |
| 81 |     `fun flushFile()` |
| 161 |   `private fun parseApplyPatch(text: String): List<DiffFile>` |
| 174 |     `fun flushHunk()` |
| 178 |     `fun flushFile()` |
| 207 |   `private fun parseHunkHeader(line: String): Pair<Int, Int>?` |
| 214 |   `private fun stripABPrefix(p: String): String` |

## core/designsystem/markdown/InlineMarkdown.kt

| Line | Declaration |
|---|---|
| 23 | `const val URL_TAG` |
| 29 | `@Composable fun rememberInlineMarkdown(raw: String): AnnotatedString` |
| 30 |   `val colors` |
| 41 | `fun inlineMarkdown( raw: String, linkColor: Color, codeBg: Color, codeColor: Color, ): AnnotatedString` |
| 50 | `private fun parseInline( builder: AnnotatedString.Builder, raw: String, linkColor: Color, codeBg: Color, codeColor: Color, )` |
| 57 |   `var i` |
| 58 |   `val n` |
| 59 |   `val plain` |
| 61 |   `fun flushPlain()` |

## core/designsystem/markdown/LiveBlockParser.kt

| Line | Declaration |
|---|---|
| 32 | `object LiveBlockParser` |
| 33 |   `private const val MAX_SEGMENTS` |
| 38 |   `internal var parsedChars: Long` |
| 41 |   `private class State` |
| 47 |   `private val states` |
| 48 |     `override fun removeEldestEntry(eldest: Map.Entry<String, State>): Boolean` |
| 52 |   `fun parse(key: String, newText: String): List<MarkdownBlock>` |
| 71 |   `fun forget(key: String)` |
| 79 |   `private fun advance(s: State)` |
| 112 |   `private fun parseBlocks(text: String): List<MarkdownBlock>` |

## core/designsystem/markdown/MarkdownBlock.kt

| Line | Declaration |
|---|---|
| 8 | `sealed interface MarkdownBlock` |
| 9 |   `data class Paragraph(val text: String) : MarkdownBlock` |
| 10 |   `data class Heading(val level: Int, val text: String) : MarkdownBlock` |
| 11 |   `data class BulletList(val items: List<String>) : MarkdownBlock` |
| 12 |   `data class NumberedList(val items: List<Pair<String, String>>) : MarkdownBlock` |
| 13 |   `data class Quote(val text: String) : MarkdownBlock` |
| 14 |   `data class Code(val language: String?, val code: String) : MarkdownBlock` |
| 15 |   `data object Rule : MarkdownBlock` |
| 16 |   `data class Table(val header: List<String>, val rows: List<List<String>>) : MarkdownBlock` |
| 24 | `fun parseMarkdownBlocks(raw: String): List<MarkdownBlock>` |
| 25 |   `val lines` |
| 26 |   `val blocks` |
| 27 |   `var i` |
| 133 | `private fun fenceMarker(trimmed: String): String?` |
| 139 | `private fun heading(trimmed: String): Pair<Int, String>?` |
| 140 |   `var n` |
| 143 |   `val rest` |
| 148 | `private fun isRule(trimmed: String): Boolean` |
| 149 |   `val s` |
| 154 | `private fun bulletContent(trimmed: String): String?` |
| 159 | `private fun orderedContent(trimmed: String): Pair<String, String>?` |
| 160 |   `var idx` |
| 163 |   `val sep` |
| 165 |   `val after` |
| 170 | `private fun isTableSeparator(line: String): Boolean` |
| 171 |   `val t` |
| 176 | `private fun tableCells(line: String): List<String>` |
| 177 |   `var t` |

## core/designsystem/markdown/MarkdownCache.kt

| Line | Declaration |
|---|---|
| 16 | `object MarkdownCache` |
| 17 |   `private const val MAX_BLOCKS` |
| 19 |   `private val blockCache` |
| 20 |     `override fun removeEldestEntry(eldest: Map.Entry<String, List<MarkdownBlock>>): Boolean` |
| 25 |   `fun blocks(raw: String): List<MarkdownBlock>` |

## core/designsystem/markdown/MarkdownContent.kt

| Line | Declaration |
|---|---|
| 37 | `@Composable fun MarkdownContent(raw: String, modifier: Modifier = Modifier)` |
| 41 |   `val blocks` |
| 56 | `@Composable internal fun SingleBlockView(block: MarkdownBlock, modifier: Modifier = Modifier)` |
| 57 |   `val colors` |
| 112 | `@Composable private fun ListRow(marker: String, content: String)` |
| 113 |   `val colors` |
| 132 | `@Composable private fun MarkdownTable(table: MarkdownBlock.Table, modifier: Modifier = Modifier)` |
| 133 |   `val colors` |
| 154 | `@Composable private fun TableRow(cells: List<String>, isHeader: Boolean)` |
| 155 |   `val colors` |
| 169 | `private fun headingSize(level: Int)` |

## core/designsystem/theme/CodegTheme.kt

| Line | Declaration |
|---|---|
| 19 | `val LocalCodegColors` |
| 22 | `val LocalCodegDimens` |
| 30 | `@Composable fun CodegTheme( themeMode: ThemeMode = ThemeMode.SYSTEM, themeId: String = WebThemePreset.DEFAULT.id, content: @Composable () -> Unit, )` |
| 35 |   `val isDark` |
| 40 |   `val preset` |
| 41 |   `val colors` |
| 42 |   `val material` |
| 47 |   `val view` |
| 72 | `object CodegTheme` |
| 73 |   `val colors: CodegColors` |
| 75 |   `@Composable val dimens: CodegDimens` |
| 79 | `private fun CodegColors.toMaterialScheme()` |

## core/designsystem/theme/Color.kt

| Line | Declaration |
|---|---|
| 13 | `data class CodegColors( val isDark: Boolean, val bg: Color, val bgElevated: Color, val surfaceStroke: Color, val hairline: Color, val codeSurface: Color, val rail: Color, val textPrimary: Color, val textSecondary: Color, val textTertiary: Color, val danger: Color, val accent: Color, val onAccent: Color, val accentDim: Color, val web: WebTokens, val success: Color = if (isDark) Color(0xFF4ADE80) else Color(0xFF16A34A), val diffAdded: Color = success, val diffDeleted: Color = if (isDark) Color(0xFFF87171) else Color(0xFFDC2626), val diffAddedBg: Color = Color(0xFF22C55E).copy(alpha = 0.10f), val diffDeletedBg: Color = Color(0xFFEF4444).copy(alpha = 0.10f), )` |
| 14 |   `val isDark: Boolean` |
| 15 |   `val bg: Color` |
| 16 |   `val bgElevated: Color` |
| 17 |   `val surfaceStroke: Color` |
| 18 |   `val hairline: Color` |
| 19 |   `val codeSurface: Color` |
| 20 |   `val rail: Color` |
| 21 |   `val textPrimary: Color` |
| 22 |   `val textSecondary: Color` |
| 23 |   `val textTertiary: Color` |
| 24 |   `val danger: Color` |
| 25 |   `val accent: Color` |
| 26 |   `val onAccent: Color` |
| 27 |   `val accentDim: Color` |
| 28 |   `val web: WebTokens` |
| 32 |   `val success: Color` |
| 33 |   `val diffAdded: Color` |
| 34 |   `val diffDeleted: Color` |
| 35 |   `val diffAddedBg: Color` |
| 36 |   `val diffDeletedBg: Color` |
| 43 | `fun Color.withOpacity(fraction: Float): Color` |
| 51 | `fun codegColors(isDark: Boolean, preset: WebThemePreset): CodegColors` |
| 52 |   `val w` |

## core/designsystem/theme/ColorHex.kt

| Line | Declaration |
|---|---|
| 10 | `fun colorFromHex(hexString: String): Color?` |
| 11 |   `var hex` |
| 14 |   `val value` |

## core/designsystem/theme/Dimens.kt

| Line | Declaration |
|---|---|
| 14 | `data class CodegDimens( val radiusXl: Dp = 26.dp, val radiusLg: Dp = 18.dp, val radiusMd: Dp = 14.dp, val radiusSm: Dp = 8.dp, val screenHMargin: Dp = 16.dp, val blockSpacing: Dp = 12.dp, val hairlineWidth: Dp = 1.dp, )` |
| 15 |   `val radiusXl: Dp` |
| 16 |   `val radiusLg: Dp` |
| 17 |   `val radiusMd: Dp` |
| 18 |   `val radiusSm: Dp` |
| 19 |   `val screenHMargin: Dp` |
| 20 |   `val blockSpacing: Dp` |
| 21 |   `val hairlineWidth: Dp` |

## core/designsystem/theme/Shape.kt

| Line | Declaration |
|---|---|
| 12 | `val CodegShapes` |
| 21 | `val PillShape` |

## core/designsystem/theme/Type.kt

| Line | Declaration |
|---|---|
| 19 | `val CodegSans: FontFamily` |
| 34 | `val CodegMono: FontFamily` |
| 37 | `val CodeTextStyle` |
| 48 | `private val BaseTypography` |
| 61 | `val CodegTypography: Typography` |

## core/designsystem/theme/WebThemes.kt

| Line | Declaration |
|---|---|
| 11 | `data class WebTokens( val background: Color, val foreground: Color, val card: Color, val cardForeground: Color, val popover: Color, val popoverForeground: Color, val primary: Color, val primaryForeground: Color, val secondary: Color, val secondaryForeground: Color, val muted: Color, val mutedForeground: Color, val accent: Color, val accentForeground: Color, val destructive: Color, val border: Color, val input: Color, val ring: Color, val sidebar: Color, val sidebarForeground: Color, val sidebarPrimary: Color, val sidebarPrimaryForeground: Color, val sidebarAccent: Color, val sidebarAccentForeground: Color, val sidebarBorder: Color, )` |
| 12 |   `val background: Color` |
| 13 |   `val foreground: Color` |
| 14 |   `val card: Color` |
| 15 |   `val cardForeground: Color` |
| 16 |   `val popover: Color` |
| 17 |   `val popoverForeground: Color` |
| 18 |   `val primary: Color` |
| 19 |   `val primaryForeground: Color` |
| 20 |   `val secondary: Color` |
| 21 |   `val secondaryForeground: Color` |
| 22 |   `val muted: Color` |
| 23 |   `val mutedForeground: Color` |
| 24 |   `val accent: Color` |
| 25 |   `val accentForeground: Color` |
| 26 |   `val destructive: Color` |
| 27 |   `val border: Color` |
| 28 |   `val input: Color` |
| 29 |   `val ring: Color` |
| 30 |   `val sidebar: Color` |
| 31 |   `val sidebarForeground: Color` |
| 32 |   `val sidebarPrimary: Color` |
| 33 |   `val sidebarPrimaryForeground: Color` |
| 34 |   `val sidebarAccent: Color` |
| 35 |   `val sidebarAccentForeground: Color` |
| 36 |   `val sidebarBorder: Color` |
| 40 | `enum class WebThemePreset(val id: String, val light: WebTokens, val dark: WebTokens)` |
| 726 |   `fun resolve(isDark: Boolean): WebTokens` |
| 728 |   `companion object` |
| 731 |     `fun from(id: String?): WebThemePreset` |

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

## core/designsystem/component/Avatar.kt

| Line | Declaration |
|---|---|
| 29 | `@Composable fun CodegAvatar( fallbackText: String, modifier: Modifier = Modifier, painter: Painter? = null, size: Dp = 32.dp, )` |
| 35 |   `val web` |
| 45 | `internal fun dsMiscInitial(text: String): String` |
| 46 |   `val trimmed` |
| 52 | `@Composable internal fun T53AvatarGallery()` |

## core/designsystem/component/Badges.kt

| Line | Declaration |
|---|---|
| 51 | `private val Capsule` |
| 55 | `@Composable fun statusColor(status: ConversationStatus): Color` |
| 64 | `fun statusLabel(status: ConversationStatus): String` |
| 74 | `@Composable fun AgentAvatar( agent: AgentType, modifier: Modifier = Modifier, size: Dp = 36.dp, )` |
| 79 |   `val colors` |
| 94 | `@Composable fun AgentBadge(agent: AgentType, modifier: Modifier = Modifier)` |
| 95 |   `val web` |
| 115 | `@Composable fun StatusBadge(status: ConversationStatus, modifier: Modifier = Modifier)` |
| 116 |   `val colors` |
| 141 | `@Composable fun SectionBadgeIcon( icon: ImageVector, tint: Color, modifier: Modifier = Modifier, size: Dp = 26.dp, )` |
| 164 | `@Composable fun SettingsIconBadge( icon: ImageVector, modifier: Modifier = Modifier, tint: Color = CodegTheme.colors.textPrimary, size: Dp = 28.dp, )` |
| 183 | `@Composable fun FolderBadge( color: Color, modifier: Modifier = Modifier, size: Dp = 40.dp, )` |
| 207 | `@Composable fun CountBadge(count: Int, modifier: Modifier = Modifier)` |
| 208 |   `val web` |
| 223 | `@Composable fun LivePulse(modifier: Modifier = Modifier, dotSize: Dp = 8.dp)` |
| 224 |   `val accent` |
| 225 |   `val transition` |
| 226 |   `val progress by transition.animateFloat( initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable( animation = tween(durationMillis = 1100), repeatMode = RepeatMode.Restart, ), label = "pulse-progress", )` |
| 248 | `enum class CodegBadgeVariant` |
| 251 | `@Composable fun CodegBadge( text: String, variant: CodegBadgeVariant = CodegBadgeVariant.Default, modifier: Modifier = Modifier, )` |
| 256 |   `val colors` |
| 257 |   `val web` |
| 258 |   `val fill` |
| 264 |   `val foreground` |
| 280 | `@Composable fun CodegFilterChip( selected: Boolean, label: String, onClick: () -> Unit, modifier: Modifier = Modifier, icon: ImageVector? = null, enabled: Boolean = true, )` |
| 288 |   `val web` |
| 289 |   `val foreground` |
| 304 | `@Composable internal fun T46BadgesGallery()` |
| 316 | `@Composable private fun BadgesLightPreview()` |
| 320 | `@Composable private fun BadgesDarkPreview()` |

## core/designsystem/component/Buttons.kt

| Line | Declaration |
|---|---|
| 41 | `enum class CodegButtonVariant` |
| 42 | `enum class CodegButtonSize` |
| 45 | `@Composable fun CodegButton( onClick: () -> Unit, modifier: Modifier = Modifier, variant: CodegButtonVariant = CodegButtonVariant.Default, size: CodegButtonSize = CodegButtonSize.Default, enabled: Boolean = true, loading: Boolean = false, leadingIcon: (@Composable () -> Unit)? = null, content: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit, )` |
| 55 |   `val colors` |
| 56 |   `val web` |
| 57 |   `val source` |
| 58 |   `val pressed by source.collectIsPressedAsState()` |
| 59 |   `val focused by source.collectIsFocusedAsState()` |
| 60 |   `val background` |
| 68 |   `val foreground` |
| 74 |   `val shape` |
| 75 |   `val height` |
| 76 |   `val inset` |
| 100 | `@Composable fun PrimaryButton( text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, loading: Boolean = false, icon: ImageVector? = null, )` |
| 118 | `@Composable fun CodegSegmented( options: List<String>, selectedIndex: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier, )` |
| 124 |   `val colors` |
| 125 |   `val web` |
| 126 |   `val track` |
| 127 |   `val tab` |
| 170 | `@Composable private fun ButtonPreview()` |
| 180 | `@Composable private fun PrimaryButtonPreview()` |

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

## core/designsystem/component/Collapsible.kt

| Line | Declaration |
|---|---|
| 29 | `@Composable fun CodegCollapsible( expanded: Boolean, onExpandedChange: (Boolean) -> Unit, header: @Composable () -> Unit, modifier: Modifier = Modifier, content: @Composable () -> Unit, )` |
| 38 | `@Composable fun CodegInstantCollapsible( expanded: Boolean, onExpandedChange: (Boolean) -> Unit, header: @Composable () -> Unit, modifier: Modifier = Modifier, content: @Composable () -> Unit, )` |
| 47 | `@Composable private fun CollapsibleBody( expanded: Boolean, onExpandedChange: (Boolean) -> Unit, header: @Composable () -> Unit, modifier: Modifier, instant: Boolean, content: @Composable () -> Unit, )` |
| 52 |   `val rotation by animateFloatAsState(if (expanded) 180f else 0f, label = "collapsible-chevron")` |
| 53 |   `val description` |
| 68 | `@Composable internal fun T53CollapsibleGallery()` |
| 69 |   `var expanded by remember` |

## core/designsystem/component/Command.kt

| Line | Declaration |
|---|---|
| 35 | `data class CodegCommandItem( val id: String, val label: String, val keywords: List<String> = emptyList(), val enabled: Boolean = true, )` |
| 36 |   `val id: String` |
| 37 |   `val label: String` |
| 38 |   `val keywords: List<String>` |
| 39 |   `val enabled: Boolean` |
| 41 | `data class CodegCommandGroup(val heading: String, val items: List<CodegCommandItem>)` |
| 43 | `internal fun filterCommandGroups(groups: List<CodegCommandGroup>, query: String): List<CodegCommandGroup>` |
| 44 |   `val needle` |
| 55 | `@Composable fun CodegCommandList( query: String, onQueryChange: (String) -> Unit, groups: List<CodegCommandGroup>, onSelect: (CodegCommandItem) -> Unit, modifier: Modifier = Modifier, empty: String = stringResource(R.string.ds_misc_no_results), placeholder: String = stringResource(R.string.ds_misc_search), )` |
| 64 |   `val web` |
| 65 |   `val filtered` |
| 66 |   `val enabled` |
| 67 |   `var selectedId by remember(query, groups)` |
| 68 |   `val activeId` |
| 69 |   `val listState` |
| 70 |   `val rowIds` |
| 132 |   `@Composable var query by remember` |
| 133 |   `val heading` |
| 134 |   `val label` |

## core/designsystem/component/Controls.kt

| Line | Declaration |
|---|---|
| 33 | `@Composable fun CodegSelect(label: String, value: String, options: List<String>, onSelect: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, )` |
| 38 | `@Composable fun CodegCombobox(label: String, value: String, options: List<String>, onSelect: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, )` |
| 43 | `@Composable fun <T> CodegSelect(label: String, selected: T?, options: List<T>, optionLabel: (T) -> String, onSelect: (T) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, optionDescription: ((T) -> String?)? = null, key: ((T) -> Any)? = null, )` |
| 49 | `@Composable fun <T> CodegCombobox(label: String, selected: T?, options: List<T>, optionLabel: (T) -> String, onSelect: (T) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, optionDescription: ((T) -> String?)? = null, key: ((T) -> Any)? = null, )` |
| 54 | `internal fun <T> choiceMatches(option: T, query: String, label: (T) -> String, description: ((T) -> String?)? = null, ): Boolean` |
| 59 | `internal fun <T> choiceSelected(option: T, selected: T?, key: ((T) -> Any)? = null): Boolean` |
| 64 | `@Composable private fun <T> ChoiceControl(label: String, value: T?, options: List<T>, optionLabel: (T) -> String, onSelect: (T) -> Unit, modifier: Modifier, enabled: Boolean, searchable: Boolean, optionDescription: ((T) -> String?)?, optionKey: ((T) -> Any)?, )` |
| 68 |   `var expanded by remember` |
| 69 |   `var query by remember` |
| 70 |   `val web` |
| 109 | `@Composable fun CodegRadioGroup(options: List<String>, value: String, onSelect: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, )` |
| 112 |   `val web` |
| 128 | `@Composable fun CodegCheckbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, label: String? = null, )` |
| 131 |   `val web` |
| 132 |   `val shape` |
| 145 | `@Composable fun CodegSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, label: String? = null, )` |
| 148 |   `val web` |
| 162 | `@Composable internal fun T45Gallery()` |
| 163 |   `val example` |
| 164 |   `val options` |
| 165 |   `var value by remember` |
| 166 |   `var text by remember` |
| 167 |   `var checked by remember` |
| 214 | `@Composable private fun T45Preview()` |
| 219 | `@Composable private fun SelectPreview()` |
| 229 | `@Composable private fun ComboboxPreview()` |
| 239 | `@Composable private fun RadioGroupPreview()` |
| 249 | `@Composable private fun CheckboxPreview()` |
| 259 | `@Composable private fun SwitchPreview()` |

## core/designsystem/component/Dialogs.kt

| Line | Declaration |
|---|---|
| 31 | `@Composable fun CodegDialog( title: String, description: String? = null, onDismiss: () -> Unit, footer: @Composable RowScope.() -> Unit = {}, content: @Composable ColumnScope.() -> Unit = {}, )` |
| 69 | `@Composable internal fun T44PanelHeading(title: String, description: String?)` |
| 78 | `@Composable fun CodegConfirmDialog( title: String, message: String, target: String, confirmLabel: String, destructive: Boolean = false, onConfirm: () -> Unit, onDismiss: () -> Unit, confirmEnabled: Boolean = true, busy: Boolean = false, content: (@Composable ColumnScope.() -> Unit)? = null, )` |
| 90 |   `val web` |
| 105 | `@Composable internal fun T44Gallery()` |
| 106 |   `var panel by remember` |
| 107 |   `val title` |
| 108 |   `val description` |
| 109 |   `val confirm` |
| 119 |   `val dismiss` |
| 140 | `@Composable private fun DialogLightPreview()` |
| 146 | `@Composable private fun DialogDarkPreview()` |
| 152 | `@Composable private fun ConfirmLightPreview()` |
| 155 | `@Composable private fun ConfirmDarkPreview()` |
| 157 | `@Composable private fun T44ConfirmPreview()` |

## core/designsystem/component/Feedback.kt

| Line | Declaration |
|---|---|
| 38 | `@Composable fun CodegSeparator(modifier: Modifier = Modifier, vertical: Boolean = false)` |
| 45 | `@Composable fun CodegSkeleton(modifier: Modifier = Modifier)` |
| 46 |   `val transition` |
| 47 |   `val opacity by transition.animateFloat(1f, 0.5f, infiniteRepeatable(tween(1000), RepeatMode.Reverse), label = "skeleton-opacity")` |
| 52 | `internal fun normalizedProgress(progress: Float): Float` |
| 57 | `@Composable fun CodegProgress(progress: Float, modifier: Modifier = Modifier)` |
| 58 |   `val value` |
| 59 |   `val web` |
| 69 | `@Composable fun CodegProgress(modifier: Modifier = Modifier)` |
| 70 |   `val transition` |
| 71 |   `val position by transition.animateFloat(-.35f, 1f, infiniteRepeatable(tween(1200, easing = LinearEasing)), label = "progress-position")` |
| 73 |   `val web` |
| 83 | `@Composable fun CodegSpinner(modifier: Modifier = Modifier, size: Dp = 16.dp)` |
| 84 |   `val transition` |
| 85 |   `val angle by transition.animateFloat(0f, 360f, infiniteRepeatable(tween(1000, easing = LinearEasing)), label = "spinner-angle")` |
| 92 | `@Composable internal fun T46FeedbackGallery()` |
| 109 | `@Composable private fun FeedbackLightPreview()` |
| 113 | `@Composable private fun FeedbackDarkPreview()` |

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

## core/designsystem/component/FolderBadge.kt

| Line | Declaration |
|---|---|
| 22 | `@Composable fun CodegFolderBadge(name: String, modifier: Modifier = Modifier, color: Color? = null)` |
| 23 |   `val web` |

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

## core/designsystem/component/ImagePreview.kt

| Line | Declaration |
|---|---|
| 40 | `data class CodegPreviewImage(val painter: Painter, val description: String)` |
| 42 | `internal fun boundedPreviewScale(scale: Float): Float` |
| 43 | `internal fun boundedPreviewIndex(index: Int, count: Int): Int` |
| 44 | `internal fun fittedPreviewSize(width: Float, height: Float, viewportWidth: Float, viewportHeight: Float): Pair<Float, Float>` |
| 48 |   `val ratio` |
| 51 | `internal fun boundedPreviewPan(pan: Float, extent: Float, scale: Float, imageExtent: Float = extent): Float` |
| 53 |   `val limit` |
| 58 | `@Composable fun CodegImagePreviewDialog(images: List<CodegPreviewImage>, initialIndex: Int = 0, onDismiss: () -> Unit)` |
| 59 |   `var index by remember(images, initialIndex)` |
| 60 |   `val current` |
| 67 | `@Composable private fun ImagePreviewBody(images: List<CodegPreviewImage>, index: Int, onIndexChange: (Int) -> Unit, onDismiss: () -> Unit)` |
| 68 |   `val web` |
| 69 |   `val arrowRotation` |
| 70 |   `val image` |
| 71 |   `var scale by remember(image, index)` |
| 72 |   `var offset by remember(image, index)` |
| 73 |   `var viewport by remember` |
| 74 |   `val intrinsic` |
| 75 |   `val fitted` |
| 121 |   `@Composable val web` |
| 122 |   `val description` |
| 123 |   `var index by remember` |

## core/designsystem/component/InputGroup.kt

| Line | Declaration |
|---|---|
| 38 | `@Composable fun CodegInputGroup( value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, leading: (@Composable () -> Unit)? = null, trailing: (@Composable () -> Unit)? = null, placeholder: String = "", enabled: Boolean = true, readOnly: Boolean = false, isError: Boolean = false, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, keyboardActions: KeyboardActions = KeyboardActions.Default, )` |
| 51 |   `val web` |
| 52 |   `var focused by remember` |
| 53 |   `val stroke` |
| 54 |   `val ring` |
| 55 |   `val shape` |
| 56 |   `val outline` |
| 80 |   `@Composable var text by remember` |

## core/designsystem/component/ListRows.kt

| Line | Declaration |
|---|---|
| 39 | `@Composable fun CodegListRow( title: String, onClick: () -> Unit, modifier: Modifier = Modifier, subtitle: String? = null, selected: Boolean = false, leading: (@Composable () -> Unit)? = null, trailing: (@Composable () -> Unit)? = null, onLongClick: (() -> Unit)? = null, onMoreClick: (() -> Unit)? = null, titleMaxLines: Int = 1, subtitleMaxLines: Int = 1, )` |
| 52 |   `val web` |
| 53 |   `val interactions` |
| 54 |   `val pressed by interactions.collectIsPressedAsState()` |
| 55 |   `val foreground` |
| 85 | `@Composable fun CodegRowMoreButton( onClick: () -> Unit, modifier: Modifier = Modifier, contentDescription: String = stringResource(R.string.ds_rows_more), )` |
| 90 |   `val web` |
| 91 |   `val interactions` |
| 92 |   `val pressed by interactions.collectIsPressedAsState()` |
| 103 | `@Composable fun CodegSectionHeader( title: String, count: Int? = null, action: (@Composable () -> Unit)? = null, collapsed: Boolean = false, modifier: Modifier = Modifier, onToggle: (() -> Unit)? = null, )` |
| 111 |   `val web` |
| 112 |   `val state` |
| 137 | `@Composable internal fun T46RowsGallery()` |
| 138 |   `var collapsed by remember` |
| 139 |   `var selected by remember` |
| 140 |   `val title` |
| 158 | `@Composable internal fun T46Gallery()` |
| 170 | `@Composable private fun RowsLightPreview()` |
| 174 | `@Composable private fun RowsDarkPreview()` |

## core/designsystem/component/Menus.kt

| Line | Declaration |
|---|---|
| 84 | `private val MenuShape` |
| 85 | `private val MenuTextStyle` |
| 87 | `enum class CodegMenuItemVariant` |
| 91 | `@Composable fun CodegDropdownMenu( expanded: Boolean, onDismissRequest: () -> Unit, modifier: Modifier = Modifier, offset: DpOffset = DpOffset(0.dp, 0.dp), properties: PopupProperties = PopupProperties(focusable = true), content: @Composable ColumnScope.() -> Unit, )` |
| 105 | `@Composable private fun MenuSurface( modifier: Modifier = Modifier, contentPadding: PaddingValues = PaddingValues(4.dp), shape: Shape = MenuShape, elevation: Dp = 6.dp, content: @Composable ColumnScope.() -> Unit, )` |
| 112 |   `val web` |
| 130 | `@Composable fun CodegMenuItem( text: @Composable () -> Unit, onClick: () -> Unit, modifier: Modifier = Modifier, leadingIcon: (@Composable () -> Unit)? = null, trailingIcon: (@Composable () -> Unit)? = null, shortcut: String? = null, enabled: Boolean = true, variant: CodegMenuItemVariant = CodegMenuItemVariant.Default, )` |
| 140 |   `val interactions` |
| 150 | `@Composable private fun MenuRow( text: @Composable () -> Unit, modifier: Modifier, interactions: MutableInteractionSource, enabled: Boolean, variant: CodegMenuItemVariant, leadingIcon: (@Composable () -> Unit)?, trailingIcon: (@Composable () -> Unit)?, shortcut: String?, )` |
| 160 |   `val web` |
| 161 |   `val pressed by interactions.collectIsPressedAsState()` |
| 162 |   `val focused by interactions.collectIsFocusedAsState()` |
| 163 |   `val hovered by interactions.collectIsHoveredAsState()` |
| 164 |   `val active` |
| 165 |   `val foreground` |
| 192 | `@Composable fun CodegMenuCheckboxItem( checked: Boolean, onCheckedChange: (Boolean) -> Unit, text: @Composable () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, leadingIcon: (@Composable () -> Unit)? = null, )` |
| 200 |   `val interactions` |
| 218 | `@Composable fun CodegMenuSeparator(modifier: Modifier = Modifier)` |
| 219 |   `val bleed` |
| 234 | `@Composable fun CodegMenuLabel(text: @Composable () -> Unit, modifier: Modifier = Modifier)` |
| 244 | `@Composable fun CodegContextMenu( menu: @Composable ColumnScope.(() -> Unit) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, content: @Composable () -> Unit, )` |
| 250 |   `var expanded by remember` |
| 251 |   `val dismiss` |
| 261 | `@Composable fun CodegPopover( expanded: Boolean, onDismissRequest: () -> Unit, modifier: Modifier = Modifier, offset: DpOffset = DpOffset(0.dp, 0.dp), properties: PopupProperties = PopupProperties(focusable = true), content: @Composable ColumnScope.() -> Unit, )` |
| 281 | `@Composable fun CodegTooltip( text: String, modifier: Modifier = Modifier, enabled: Boolean = true, content: @Composable () -> Unit, )` |
| 287 |   `var expanded by remember` |
| 288 |   `val accessibility` |
| 307 | `@Composable private fun TooltipSurface(text: String)` |
| 308 |   `val web` |
| 318 | `@Composable private fun Modifier.longPressMenu(enabled: Boolean, onLongPress: () -> Unit): Modifier` |
| 320 |   `val currentOnLongPress by rememberUpdatedState(onLongPress)` |
| 339 | `@Composable private fun MenuPopup( expanded: Boolean, onDismissRequest: () -> Unit, offset: DpOffset, properties: PopupProperties, content: @Composable () -> Unit, )` |
| 346 |   `val density` |
| 347 |   `val gap` |
| 348 |   `val offsetPx` |
| 349 |   `val provider` |
| 350 |     `object : PopupPositionProvider` |
| 365 | `internal fun menuPopupPosition( anchor: IntRect, window: IntSize, popup: IntSize, direction: LayoutDirection, gap: Int, ): IntOffset` |
| 372 |   `val x` |
| 373 |   `val below` |
| 374 |   `val y` |
| 383 | `private annotation class MenuPreviews` |
| 386 | `@Composable private fun MenuPreviewTheme(content: @Composable () -> Unit)` |
| 395 | `@Composable private fun GalleryItems()` |
| 423 | `@Composable internal fun T43Gallery()` |
| 447 | `@Composable private fun DropdownMenuPreview()` |
| 453 | `@Composable private fun MenuItemPreview()` |
| 457 | `@Composable private fun MenuSeparatorPreview()` |
| 461 | `@Composable private fun MenuLabelPreview()` |
| 467 | `@Composable private fun MenuCheckboxPreview()` |
| 476 | `@Composable private fun ContextMenuPreview()` |
| 484 | `@Composable private fun PopoverPreview()` |
| 490 | `@Composable private fun TooltipPreview()` |
| 499 | `@Composable private fun GalleryPreview()` |

## core/designsystem/component/Sheets.kt

| Line | Declaration |
|---|---|
| 29 | `@Composable fun CodegSheet( title: String, description: String? = null, onDismiss: () -> Unit, footer: @Composable ColumnScope.() -> Unit = {}, content: @Composable ColumnScope.() -> Unit, )` |
| 36 |   `val web` |
| 69 | `@Composable fun CodegFullScreenSheet( title: String, onDismiss: () -> Unit, actions: @Composable RowScope.() -> Unit = {}, content: @Composable ColumnScope.() -> Unit, )` |
| 95 | `@Composable internal fun T44SheetSampleContent()` |
| 96 |   `var value by remember` |
| 103 | `@Composable private fun SheetLightPreview()` |
| 106 | `@Composable private fun SheetDarkPreview()` |
| 108 | `@Composable private fun T44SheetPreview()` |
| 116 | `@Composable private fun FullSheetLightPreview()` |
| 119 | `@Composable private fun FullSheetDarkPreview()` |
| 121 | `@Composable private fun T44FullSheetPreview()` |

## core/designsystem/component/StateViews.kt

| Line | Declaration |
|---|---|
| 42 | `@Composable fun EmptyState( icon: ImageVector, title: String, message: String, modifier: Modifier = Modifier, actionLabel: String? = null, onAction: (() -> Unit)? = null, )` |
| 50 |   `val colors` |
| 76 | `@Composable fun LoadingView(message: String, modifier: Modifier = Modifier)` |
| 77 |   `val colors` |
| 91 | `@Composable fun InlineError( icon: ImageVector, title: String, message: String, onRetry: () -> Unit, modifier: Modifier = Modifier, retryLabel: String = stringResource(R.string.ds_states_retry), showRetry: Boolean = true, )` |
| 100 |   `val colors` |
| 124 | `@Composable internal fun T54Gallery()` |
| 125 |   `val title` |
| 126 |   `val message` |
| 150 | `@Composable private fun T54LightPreview()` |
| 156 | `@Composable private fun T54DarkPreview()` |

## core/designsystem/component/Tabs.kt

| Line | Declaration |
|---|---|
| 32 | `@Composable fun CodegTabs( selected: Int, tabs: List<String>, onSelect: (Int) -> Unit, modifier: Modifier = Modifier, )` |
| 38 |   `val colors` |
| 39 |   `val web` |
| 40 |   `val trigger` |
| 71 | `@Composable internal fun T46TabsGallery()` |
| 72 |   `var selected by remember` |
| 79 | `@Composable private fun TabsLightPreview()` |
| 83 | `@Composable private fun TabsDarkPreview()` |

## core/designsystem/component/TextFields.kt

| Line | Declaration |
|---|---|
| 45 | `@Composable fun CodegTextField( value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier, placeholder: String? = null, keyboardType: KeyboardType = KeyboardType.Text, mono: Boolean = false, singleLine: Boolean = true, isError: Boolean = false, supportingText: String? = null, enabled: Boolean = true, readOnly: Boolean = false, keyboardOptions: KeyboardOptions? = null, keyboardActions: KeyboardActions = KeyboardActions.Default, leading: (@Composable () -> Unit)? = null, trailing: (@Composable () -> Unit)? = null, minLines: Int = 1, maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE, )` |
| 90 | `@Composable fun SecretField( value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier, )` |
| 96 |   `var visible by remember` |
| 121 | `@Composable private fun fieldColors(): TextFieldColors` |
| 122 |   `val colors` |
| 123 |   `val web` |
| 146 | `@Composable fun CodegField( label: String, description: String? = null, error: String? = null, modifier: Modifier = Modifier, content: @Composable () -> Unit, )` |
| 153 |   `val web` |
| 165 | `@Composable private fun TextFieldPreview()` |
| 175 | `@Composable private fun FieldPreview()` |
| 185 | `@Composable private fun SecretFieldPreview()` |

## core/designsystem/component/Toasts.kt

| Line | Declaration |
|---|---|
| 44 | `value class ToastId(val value: Long)` |
| 45 | `enum class ToastType` |
| 46 | `data class ToastAction(val label: String, val onClick: () -> Unit)` |
| 48 | `internal data class ToastEntry( val id: ToastId, val type: ToastType, val title: String, val description: String? = null, val action: ToastAction? = null, val remainingMs: Long = 4000, )` |
| 49 |   `val id: ToastId` |
| 50 |   `val type: ToastType` |
| 51 |   `val title: String` |
| 52 |   `val description: String?` |
| 53 |   `val action: ToastAction?` |
| 54 |   `val remainingMs: Long` |
| 61 | `internal class ToastQueue` |
| 62 |   `private var nextId` |
| 63 |   `private var entries` |
| 64 |   `val visible: List<ToastEntry> get()` |
| 66 |   `fun add(type: ToastType, title: String, description: String? = null, action: ToastAction? = null): ToastId` |
| 72 |   `fun update(id: ToastId, type: ToastType, title: String, description: String?)` |
| 78 |   `fun dismiss(id: ToastId)` |
| 80 |   `fun advance(elapsedMs: Long)` |
| 89 | `class CodegToaster internal constructor(private val silent: Boolean)` |
| 91 |   `private val queue` |
| 92 |   `private val mutableEntries` |
| 93 |   `internal val entries` |
| 96 |   `private fun add(type: ToastType, title: String, description: String?, action: ToastAction?): ToastId` |
| 103 |   `fun success(title: String, description: String? = null, action: ToastAction? = null): ToastId` |
| 104 |   `fun error(title: String, description: String? = null, action: ToastAction? = null): ToastId` |
| 105 |   `fun info(title: String, description: String? = null, action: ToastAction? = null): ToastId` |
| 106 |   `fun warning(title: String, description: String? = null, action: ToastAction? = null): ToastId` |
| 107 |   `fun loading(title: String): ToastId` |
| 110 |   `fun update(id: ToastId, type: ToastType, title: String, description: String? = null)` |
| 115 |   `fun dismiss(id: ToastId)` |
| 117 |   `internal fun advance(elapsedMs: Long)` |
| 118 |   `private fun publish()` |
| 121 | `val LocalCodegToaster` |
| 124 | `@Composable fun CodegToasterHost( toaster: CodegToaster = remember { CodegToaster() }, content: @Composable () -> Unit, )` |
| 128 |   `val entries by toaster.entries.collectAsState()` |
| 130 |   `val rendered` |
| 174 | `@Composable private fun ToastCard(entry: ToastEntry, dismiss: () -> Unit)` |
| 175 |   `val web` |
| 176 |   `val threshold` |
| 177 |   `var dragOffset by remember(entry.id)` |
| 225 | `@Composable internal fun T52Gallery()` |
| 226 |   `val toaster` |
| 227 |   `val success` |
| 228 |   `val error` |
| 229 |   `val info` |
| 230 |   `val warning` |
| 231 |   `val loading` |
| 232 |   `val description` |
| 233 |   `val action` |
| 234 |   `val finish` |
| 235 |   `var pending by remember` |
| 249 | `@Composable private fun ToastPreviewContent()` |
| 266 | `@Composable private fun ToastsLightPreview()` |
| 269 | `@Composable private fun ToastsDarkPreview()` |

## core/designsystem/component/TopBar.kt

| Line | Declaration |
|---|---|
| 49 | `@Composable fun CodegTopBar( title: String, modifier: Modifier = Modifier, subtitle: String? = null, navigationIcon: @Composable () -> Unit = {}, actions: @Composable RowScope.() -> Unit = {}, scrollBehavior: TopAppBarScrollBehavior? = null, showDivider: Boolean = true, titleContent: (@Composable () -> Unit)? = null, )` |
| 59 |   `val colors` |
| 104 | `@Composable fun CodegIconButton( onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, visualSize: Dp = 36.dp, content: @Composable () -> Unit, )` |
| 111 |   `val colors` |
| 112 |   `val interactionSource` |
| 113 |   `val pressed by interactionSource.collectIsPressedAsState()` |
| 114 |   `val hovered by interactionSource.collectIsHoveredAsState()` |
| 115 |   `val focused by interactionSource.collectIsFocusedAsState()` |
| 116 |   `val active` |

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

## core/designsystem/icon/CodegIcon.kt

| Line | Declaration |
|---|---|
| 29 | `@Composable fun CodegIcon( icon: ImageVector, contentDescription: String?, modifier: Modifier = Modifier, size: Dp = 16.dp, tint: Color = LocalContentColor.current, )` |
| 46 | `@Composable internal fun T51Gallery()` |
| 47 |   `val icons` |
| 69 | `@Composable private fun LucideLightPreview()` |
| 77 | `@Composable private fun LucideDarkPreview()` |

## core/designsystem/icon/LucideIcons.kt

| Line | Declaration |
|---|---|
| 45 | `object Lucide` |
| 47 | `internal val lucideGallery: List<Pair<String, () -> ImageVector>>` |
| 50 | `private fun galleryA_F(): List<Pair<String, () -> ImageVector>>` |
| 179 | `private fun galleryG_L(): List<Pair<String, () -> ImageVector>>` |
| 244 | `private fun galleryM_R(): List<Pair<String, () -> ImageVector>>` |
| 315 | `private fun galleryS_Z(): List<Pair<String, () -> ImageVector>>` |

## core/designsystem/icon/LucideIconsA_F.kt

| Line | Declaration |
|---|---|
| 51 | `val Lucide.Activity: ImageVector get()` |
| 52 | `private val iconActivity: ImageVector by lazy` |
| 58 | `val Lucide.AlertCircle: ImageVector get()` |
| 59 | `private val iconAlertCircle: ImageVector by lazy` |
| 67 | `val Lucide.AlertTriangle: ImageVector get()` |
| 68 | `private val iconAlertTriangle: ImageVector by lazy` |
| 76 | `val Lucide.AlignLeft: ImageVector get()` |
| 77 | `private val iconAlignLeft: ImageVector by lazy` |
| 85 | `val Lucide.AppWindow: ImageVector get()` |
| 86 | `private val iconAppWindow: ImageVector by lazy` |
| 95 | `val Lucide.Archive: ImageVector get()` |
| 96 | `private val iconArchive: ImageVector by lazy` |
| 104 | `val Lucide.ArchiveRestore: ImageVector get()` |
| 105 | `private val iconArchiveRestore: ImageVector by lazy` |
| 115 | `val Lucide.ArrowDown: ImageVector get()` |
| 116 | `private val iconArrowDown: ImageVector by lazy` |
| 123 | `val Lucide.ArrowDownRight: ImageVector get()` |
| 124 | `private val iconArrowDownRight: ImageVector by lazy` |
| 131 | `val Lucide.ArrowLeft: ImageVector get()` |
| 132 | `private val iconArrowLeft: ImageVector by lazy` |
| 139 | `val Lucide.ArrowRight: ImageVector get()` |
| 140 | `private val iconArrowRight: ImageVector by lazy` |
| 147 | `val Lucide.ArrowUp: ImageVector get()` |
| 148 | `private val iconArrowUp: ImageVector by lazy` |
| 155 | `val Lucide.ArrowUpCircle: ImageVector get()` |
| 156 | `private val iconArrowUpCircle: ImageVector by lazy` |
| 164 | `val Lucide.ArrowUpRight: ImageVector get()` |
| 165 | `private val iconArrowUpRight: ImageVector by lazy` |
| 172 | `val Lucide.ArrowUpToLine: ImageVector get()` |
| 173 | `private val iconArrowUpToLine: ImageVector by lazy` |
| 181 | `val Lucide.AtSign: ImageVector get()` |
| 182 | `private val iconAtSign: ImageVector by lazy` |
| 189 | `val Lucide.AudioLines: ImageVector get()` |
| 190 | `private val iconAudioLines: ImageVector by lazy` |
| 201 | `val Lucide.BadgeCheck: ImageVector get()` |
| 202 | `private val iconBadgeCheck: ImageVector by lazy` |
| 209 | `val Lucide.Ban: ImageVector get()` |
| 210 | `private val iconBan: ImageVector by lazy` |
| 217 | `val Lucide.BarChart3: ImageVector get()` |
| 218 | `private val iconBarChart3: ImageVector by lazy` |
| 227 | `val Lucide.Bell: ImageVector get()` |
| 228 | `private val iconBell: ImageVector by lazy` |
| 235 | `val Lucide.BellOff: ImageVector get()` |
| 236 | `private val iconBellOff: ImageVector by lazy` |
| 245 | `val Lucide.BellRing: ImageVector get()` |
| 246 | `private val iconBellRing: ImageVector by lazy` |
| 255 | `val Lucide.BookOpen: ImageVector get()` |
| 256 | `private val iconBookOpen: ImageVector by lazy` |
| 263 | `val Lucide.BookOpenText: ImageVector get()` |
| 264 | `private val iconBookOpenText: ImageVector by lazy` |
| 275 | `val Lucide.BookmarkPlus: ImageVector get()` |
| 276 | `private val iconBookmarkPlus: ImageVector by lazy` |
| 284 | `val Lucide.Bot: ImageVector get()` |
| 285 | `private val iconBot: ImageVector by lazy` |
| 296 | `val Lucide.Box: ImageVector get()` |
| 297 | `private val iconBox: ImageVector by lazy` |
| 305 | `val Lucide.Boxes: ImageVector get()` |
| 306 | `private val iconBoxes: ImageVector by lazy` |
| 323 | `val Lucide.Braces: ImageVector get()` |
| 324 | `private val iconBraces: ImageVector by lazy` |
| 331 | `val Lucide.Brain: ImageVector get()` |
| 332 | `private val iconBrain: ImageVector by lazy` |
| 345 | `val Lucide.BrainCog: ImageVector get()` |
| 346 | `private val iconBrainCog: ImageVector by lazy` |
| 366 | `val Lucide.BrushCleaning: ImageVector get()` |
| 367 | `private val iconBrushCleaning: ImageVector by lazy` |
| 376 | `val Lucide.Bubbles: ImageVector get()` |
| 377 | `private val iconBubbles: ImageVector by lazy` |
| 386 | `val Lucide.Bug: ImageVector get()` |
| 387 | `private val iconBug: ImageVector by lazy` |
| 403 | `val Lucide.Calendar: ImageVector get()` |
| 404 | `private val iconCalendar: ImageVector by lazy` |
| 413 | `val Lucide.CalendarClock: ImageVector get()` |
| 414 | `private val iconCalendarClock: ImageVector by lazy` |
| 425 | `val Lucide.Camera: ImageVector get()` |
| 426 | `private val iconCamera: ImageVector by lazy` |
| 433 | `val Lucide.ChartNoAxesColumn: ImageVector get()` |
| 434 | `private val iconChartNoAxesColumn: ImageVector by lazy` |
| 442 | `val Lucide.Check: ImageVector get()` |
| 443 | `private val iconCheck: ImageVector by lazy` |
| 449 | `val Lucide.CheckCheck: ImageVector get()` |
| 450 | `private val iconCheckCheck: ImageVector by lazy` |
| 457 | `val Lucide.CheckCircle: ImageVector get()` |
| 458 | `private val iconCheckCircle: ImageVector by lazy` |
| 465 | `val Lucide.CheckCircle2: ImageVector get()` |
| 466 | `private val iconCheckCircle2: ImageVector by lazy` |
| 473 | `val Lucide.CheckSquare: ImageVector get()` |
| 474 | `private val iconCheckSquare: ImageVector by lazy` |
| 481 | `val Lucide.ChevronDown: ImageVector get()` |
| 482 | `private val iconChevronDown: ImageVector by lazy` |
| 488 | `val Lucide.ChevronLeft: ImageVector get()` |
| 489 | `private val iconChevronLeft: ImageVector by lazy` |
| 495 | `val Lucide.ChevronRight: ImageVector get()` |
| 496 | `private val iconChevronRight: ImageVector by lazy` |
| 502 | `val Lucide.ChevronUp: ImageVector get()` |
| 503 | `private val iconChevronUp: ImageVector by lazy` |
| 509 | `val Lucide.ChevronsDownUp: ImageVector get()` |
| 510 | `private val iconChevronsDownUp: ImageVector by lazy` |
| 517 | `val Lucide.ChevronsUp: ImageVector get()` |
| 518 | `private val iconChevronsUp: ImageVector by lazy` |
| 525 | `val Lucide.ChevronsUpDown: ImageVector get()` |
| 526 | `private val iconChevronsUpDown: ImageVector by lazy` |
| 533 | `val Lucide.Circle: ImageVector get()` |
| 534 | `private val iconCircle: ImageVector by lazy` |
| 540 | `val Lucide.CircleAlert: ImageVector get()` |
| 541 | `private val iconCircleAlert: ImageVector by lazy` |
| 549 | `val Lucide.CircleCheck: ImageVector get()` |
| 550 | `private val iconCircleCheck: ImageVector by lazy` |
| 557 | `val Lucide.CircleCheckBig: ImageVector get()` |
| 558 | `private val iconCircleCheckBig: ImageVector by lazy` |
| 565 | `val Lucide.CircleDashed: ImageVector get()` |
| 566 | `private val iconCircleDashed: ImageVector by lazy` |
| 579 | `val Lucide.CircleDot: ImageVector get()` |
| 580 | `private val iconCircleDot: ImageVector by lazy` |
| 587 | `val Lucide.CircleHelp: ImageVector get()` |
| 588 | `private val iconCircleHelp: ImageVector by lazy` |
| 596 | `val Lucide.CircleMinus: ImageVector get()` |
| 597 | `private val iconCircleMinus: ImageVector by lazy` |
| 604 | `val Lucide.CirclePlay: ImageVector get()` |
| 605 | `private val iconCirclePlay: ImageVector by lazy` |
| 612 | `val Lucide.CirclePlus: ImageVector get()` |
| 613 | `private val iconCirclePlus: ImageVector by lazy` |
| 621 | `val Lucide.CircleX: ImageVector get()` |
| 622 | `private val iconCircleX: ImageVector by lazy` |
| 630 | `val Lucide.Clapperboard: ImageVector get()` |
| 631 | `private val iconClapperboard: ImageVector by lazy` |
| 640 | `val Lucide.ClipboardList: ImageVector get()` |
| 641 | `private val iconClipboardList: ImageVector by lazy` |
| 652 | `val Lucide.ClipboardPaste: ImageVector get()` |
| 653 | `private val iconClipboardPaste: ImageVector by lazy` |
| 663 | `val Lucide.Clock: ImageVector get()` |
| 664 | `private val iconClock: ImageVector by lazy` |
| 671 | `val Lucide.Clock3: ImageVector get()` |
| 672 | `private val iconClock3: ImageVector by lazy` |
| 679 | `val Lucide.Cloud: ImageVector get()` |
| 680 | `private val iconCloud: ImageVector by lazy` |
| 686 | `val Lucide.CloudCheck: ImageVector get()` |
| 687 | `private val iconCloudCheck: ImageVector by lazy` |
| 694 | `val Lucide.CloudDownload: ImageVector get()` |
| 695 | `private val iconCloudDownload: ImageVector by lazy` |
| 703 | `val Lucide.CloudOff: ImageVector get()` |
| 704 | `private val iconCloudOff: ImageVector by lazy` |
| 712 | `val Lucide.CloudSync: ImageVector get()` |
| 713 | `private val iconCloudSync: ImageVector by lazy` |
| 723 | `val Lucide.CloudUpload: ImageVector get()` |
| 724 | `private val iconCloudUpload: ImageVector by lazy` |
| 732 | `val Lucide.Code: ImageVector get()` |
| 733 | `private val iconCode: ImageVector by lazy` |
| 740 | `val Lucide.Code2: ImageVector get()` |
| 741 | `private val iconCode2: ImageVector by lazy` |
| 749 | `val Lucide.Cog: ImageVector get()` |
| 750 | `private val iconCog: ImageVector by lazy` |
| 769 | `val Lucide.Coins: ImageVector get()` |
| 770 | `private val iconCoins: ImageVector by lazy` |
| 779 | `val Lucide.Columns2: ImageVector get()` |
| 780 | `private val iconColumns2: ImageVector by lazy` |
| 787 | `val Lucide.Command: ImageVector get()` |
| 788 | `private val iconCommand: ImageVector by lazy` |
| 794 | `val Lucide.Compass: ImageVector get()` |
| 795 | `private val iconCompass: ImageVector by lazy` |
| 802 | `val Lucide.Copy: ImageVector get()` |
| 803 | `private val iconCopy: ImageVector by lazy` |
| 810 | `val Lucide.Cpu: ImageVector get()` |
| 811 | `private val iconCpu: ImageVector by lazy` |
| 830 | `val Lucide.Crosshair: ImageVector get()` |
| 831 | `private val iconCrosshair: ImageVector by lazy` |
| 841 | `val Lucide.DatabaseBackup: ImageVector get()` |
| 842 | `private val iconDatabaseBackup: ImageVector by lazy` |
| 853 | `val Lucide.Download: ImageVector get()` |
| 854 | `private val iconDownload: ImageVector by lazy` |
| 862 | `val Lucide.Ellipsis: ImageVector get()` |
| 863 | `private val iconEllipsis: ImageVector by lazy` |
| 871 | `val Lucide.EllipsisVertical: ImageVector get()` |
| 872 | `private val iconEllipsisVertical: ImageVector by lazy` |
| 880 | `val Lucide.Eraser: ImageVector get()` |
| 881 | `private val iconEraser: ImageVector by lazy` |
| 888 | `val Lucide.Expand: ImageVector get()` |
| 889 | `private val iconExpand: ImageVector by lazy` |
| 902 | `val Lucide.ExternalLink: ImageVector get()` |
| 903 | `private val iconExternalLink: ImageVector by lazy` |
| 911 | `val Lucide.Eye: ImageVector get()` |
| 912 | `private val iconEye: ImageVector by lazy` |
| 919 | `val Lucide.EyeOff: ImageVector get()` |
| 920 | `private val iconEyeOff: ImageVector by lazy` |
| 929 | `val Lucide.File: ImageVector get()` |
| 930 | `private val iconFile: ImageVector by lazy` |
| 937 | `val Lucide.FileCode: ImageVector get()` |
| 938 | `private val iconFileCode: ImageVector by lazy` |
| 947 | `val Lucide.FileCode2: ImageVector get()` |
| 948 | `private val iconFileCode2: ImageVector by lazy` |
| 957 | `val Lucide.FileDiff: ImageVector get()` |
| 958 | `private val iconFileDiff: ImageVector by lazy` |
| 967 | `val Lucide.FileDown: ImageVector get()` |
| 968 | `private val iconFileDown: ImageVector by lazy` |
| 977 | `val Lucide.FileImage: ImageVector get()` |
| 978 | `private val iconFileImage: ImageVector by lazy` |
| 987 | `val Lucide.FileMinus2: ImageVector get()` |
| 988 | `private val iconFileMinus2: ImageVector by lazy` |
| 996 | `val Lucide.FilePen: ImageVector get()` |
| 997 | `private val iconFilePen: ImageVector by lazy` |
| 1005 | `val Lucide.FilePenLine: ImageVector get()` |
| 1006 | `private val iconFilePenLine: ImageVector by lazy` |
| 1014 | `val Lucide.FilePlus: ImageVector get()` |
| 1015 | `private val iconFilePlus: ImageVector by lazy` |
| 1024 | `val Lucide.FilePlus2: ImageVector get()` |
| 1025 | `private val iconFilePlus2: ImageVector by lazy` |
| 1034 | `val Lucide.FileSearch: ImageVector get()` |
| 1035 | `private val iconFileSearch: ImageVector by lazy` |
| 1044 | `val Lucide.FileSpreadsheet: ImageVector get()` |
| 1045 | `private val iconFileSpreadsheet: ImageVector by lazy` |
| 1056 | `val Lucide.FileStack: ImageVector get()` |
| 1057 | `private val iconFileStack: ImageVector by lazy` |
| 1065 | `val Lucide.FileText: ImageVector get()` |
| 1066 | `private val iconFileText: ImageVector by lazy` |
| 1076 | `val Lucide.FileUp: ImageVector get()` |
| 1077 | `private val iconFileUp: ImageVector by lazy` |
| 1086 | `val Lucide.FileWarning: ImageVector get()` |
| 1087 | `private val iconFileWarning: ImageVector by lazy` |
| 1095 | `val Lucide.Film: ImageVector get()` |
| 1096 | `private val iconFilm: ImageVector by lazy` |
| 1109 | `val Lucide.FlaskConical: ImageVector get()` |
| 1110 | `private val iconFlaskConical: ImageVector by lazy` |
| 1118 | `val Lucide.Folder: ImageVector get()` |
| 1119 | `private val iconFolder: ImageVector by lazy` |
| 1125 | `val Lucide.FolderClosed: ImageVector get()` |
| 1126 | `private val iconFolderClosed: ImageVector by lazy` |
| 1133 | `val Lucide.FolderCog: ImageVector get()` |
| 1134 | `private val iconFolderCog: ImageVector by lazy` |
| 1149 | `val Lucide.FolderGit2: ImageVector get()` |
| 1150 | `private val iconFolderGit2: ImageVector by lazy` |
| 1159 | `val Lucide.FolderInput: ImageVector get()` |
| 1160 | `private val iconFolderInput: ImageVector by lazy` |
| 1168 | `val Lucide.FolderOpen: ImageVector get()` |
| 1169 | `private val iconFolderOpen: ImageVector by lazy` |
| 1175 | `val Lucide.FolderOpenDot: ImageVector get()` |
| 1176 | `private val iconFolderOpenDot: ImageVector by lazy` |
| 1183 | `val Lucide.FolderPen: ImageVector get()` |
| 1184 | `private val iconFolderPen: ImageVector by lazy` |
| 1191 | `val Lucide.FolderPlus: ImageVector get()` |
| 1192 | `private val iconFolderPlus: ImageVector by lazy` |
| 1200 | `val Lucide.FolderRoot: ImageVector get()` |
| 1201 | `private val iconFolderRoot: ImageVector by lazy` |
| 1209 | `val Lucide.FolderSearch: ImageVector get()` |
| 1210 | `private val iconFolderSearch: ImageVector by lazy` |
| 1218 | `val Lucide.FolderTree: ImageVector get()` |
| 1219 | `private val iconFolderTree: ImageVector by lazy` |
| 1228 | `val Lucide.FolderX: ImageVector get()` |
| 1229 | `private val iconFolderX: ImageVector by lazy` |
| 1237 | `val Lucide.Frame: ImageVector get()` |
| 1238 | `private val iconFrame: ImageVector by lazy` |
| 1247 | `val Lucide.Funnel: ImageVector get()` |
| 1248 | `private val iconFunnel: ImageVector by lazy` |

## core/designsystem/icon/LucideIconsG_L.kt

| Line | Declaration |
|---|---|
| 51 | `val Lucide.GamepadDirectional: ImageVector get()` |
| 52 | `private val iconGamepadDirectional: ImageVector by lazy` |
| 61 | `val Lucide.Gauge: ImageVector get()` |
| 62 | `private val iconGauge: ImageVector by lazy` |
| 69 | `val Lucide.GitBranch: ImageVector get()` |
| 70 | `private val iconGitBranch: ImageVector by lazy` |
| 78 | `val Lucide.GitBranchPlus: ImageVector get()` |
| 79 | `private val iconGitBranchPlus: ImageVector by lazy` |
| 90 | `val Lucide.GitCommit: ImageVector get()` |
| 91 | `private val iconGitCommit: ImageVector by lazy` |
| 99 | `val Lucide.GitCommitHorizontal: ImageVector get()` |
| 100 | `private val iconGitCommitHorizontal: ImageVector by lazy` |
| 108 | `val Lucide.GitCompare: ImageVector get()` |
| 109 | `private val iconGitCompare: ImageVector by lazy` |
| 118 | `val Lucide.GitFork: ImageVector get()` |
| 119 | `private val iconGitFork: ImageVector by lazy` |
| 129 | `val Lucide.GitMerge: ImageVector get()` |
| 130 | `private val iconGitMerge: ImageVector by lazy` |
| 138 | `val Lucide.GitPullRequest: ImageVector get()` |
| 139 | `private val iconGitPullRequest: ImageVector by lazy` |
| 148 | `val Lucide.GitPullRequestArrow: ImageVector get()` |
| 149 | `private val iconGitPullRequestArrow: ImageVector by lazy` |
| 159 | `val Lucide.GitPullRequestClosed: ImageVector get()` |
| 160 | `private val iconGitPullRequestClosed: ImageVector by lazy` |
| 171 | `val Lucide.GitPullRequestDraft: ImageVector get()` |
| 172 | `private val iconGitPullRequestDraft: ImageVector by lazy` |
| 182 | `val Lucide.Github: ImageVector get()` |
| 183 | `private val iconGithub: ImageVector by lazy` |
| 190 | `val Lucide.Gitlab: ImageVector get()` |
| 191 | `private val iconGitlab: ImageVector by lazy` |
| 197 | `val Lucide.Globe: ImageVector get()` |
| 198 | `private val iconGlobe: ImageVector by lazy` |
| 206 | `val Lucide.GraduationCap: ImageVector get()` |
| 207 | `private val iconGraduationCap: ImageVector by lazy` |
| 215 | `val Lucide.Grid2x2: ImageVector get()` |
| 216 | `private val iconGrid2x2: ImageVector by lazy` |
| 224 | `val Lucide.GripVertical: ImageVector get()` |
| 225 | `private val iconGripVertical: ImageVector by lazy` |
| 236 | `val Lucide.HardDrive: ImageVector get()` |
| 237 | `private val iconHardDrive: ImageVector by lazy` |
| 246 | `val Lucide.Hash: ImageVector get()` |
| 247 | `private val iconHash: ImageVector by lazy` |
| 256 | `val Lucide.Heart: ImageVector get()` |
| 257 | `private val iconHeart: ImageVector by lazy` |
| 263 | `val Lucide.HeartCrack: ImageVector get()` |
| 264 | `private val iconHeartCrack: ImageVector by lazy` |
| 271 | `val Lucide.HeartHandshake: ImageVector get()` |
| 272 | `private val iconHeartHandshake: ImageVector by lazy` |
| 278 | `val Lucide.HeartOff: ImageVector get()` |
| 279 | `private val iconHeartOff: ImageVector by lazy` |
| 287 | `val Lucide.HeartPulse: ImageVector get()` |
| 288 | `private val iconHeartPulse: ImageVector by lazy` |
| 295 | `val Lucide.History: ImageVector get()` |
| 296 | `private val iconHistory: ImageVector by lazy` |
| 304 | `val Lucide.Home: ImageVector get()` |
| 305 | `private val iconHome: ImageVector by lazy` |
| 312 | `val Lucide.Image: ImageVector get()` |
| 313 | `private val iconImage: ImageVector by lazy` |
| 321 | `val Lucide.ImageDown: ImageVector get()` |
| 322 | `private val iconImageDown: ImageVector by lazy` |
| 331 | `val Lucide.ImageOff: ImageVector get()` |
| 332 | `private val iconImageOff: ImageVector by lazy` |
| 343 | `val Lucide.ImagePlus: ImageVector get()` |
| 344 | `private val iconImagePlus: ImageVector by lazy` |
| 354 | `val Lucide.ImageUp: ImageVector get()` |
| 355 | `private val iconImageUp: ImageVector by lazy` |
| 364 | `val Lucide.Import: ImageVector get()` |
| 365 | `private val iconImport: ImageVector by lazy` |
| 373 | `val Lucide.Inbox: ImageVector get()` |
| 374 | `private val iconInbox: ImageVector by lazy` |
| 381 | `val Lucide.Info: ImageVector get()` |
| 382 | `private val iconInfo: ImageVector by lazy` |
| 390 | `val Lucide.Kanban: ImageVector get()` |
| 391 | `private val iconKanban: ImageVector by lazy` |
| 399 | `val Lucide.KeyRound: ImageVector get()` |
| 400 | `private val iconKeyRound: ImageVector by lazy` |
| 407 | `val Lucide.Keyboard: ImageVector get()` |
| 408 | `private val iconKeyboard: ImageVector by lazy` |
| 422 | `val Lucide.Languages: ImageVector get()` |
| 423 | `private val iconLanguages: ImageVector by lazy` |
| 434 | `val Lucide.Layers: ImageVector get()` |
| 435 | `private val iconLayers: ImageVector by lazy` |
| 443 | `val Lucide.LayersPlus: ImageVector get()` |
| 444 | `private val iconLayersPlus: ImageVector by lazy` |
| 454 | `val Lucide.LayoutDashboard: ImageVector get()` |
| 455 | `private val iconLayoutDashboard: ImageVector by lazy` |
| 464 | `val Lucide.LayoutGrid: ImageVector get()` |
| 465 | `private val iconLayoutGrid: ImageVector by lazy` |
| 474 | `val Lucide.LayoutTemplate: ImageVector get()` |
| 475 | `private val iconLayoutTemplate: ImageVector by lazy` |
| 483 | `val Lucide.Lightbulb: ImageVector get()` |
| 484 | `private val iconLightbulb: ImageVector by lazy` |
| 492 | `val Lucide.Link2: ImageVector get()` |
| 493 | `private val iconLink2: ImageVector by lazy` |
| 501 | `val Lucide.Link2Off: ImageVector get()` |
| 502 | `private val iconLink2Off: ImageVector by lazy` |
| 511 | `val Lucide.List: ImageVector get()` |
| 512 | `private val iconList: ImageVector by lazy` |
| 523 | `val Lucide.ListChecks: ImageVector get()` |
| 524 | `private val iconListChecks: ImageVector by lazy` |
| 534 | `val Lucide.ListChevronsDownUp: ImageVector get()` |
| 535 | `private val iconListChevronsDownUp: ImageVector by lazy` |
| 545 | `val Lucide.ListChevronsUpDown: ImageVector get()` |
| 546 | `private val iconListChevronsUpDown: ImageVector by lazy` |
| 556 | `val Lucide.ListFilter: ImageVector get()` |
| 557 | `private val iconListFilter: ImageVector by lazy` |
| 565 | `val Lucide.ListMusic: ImageVector get()` |
| 566 | `private val iconListMusic: ImageVector by lazy` |
| 576 | `val Lucide.ListTodo: ImageVector get()` |
| 577 | `private val iconListTodo: ImageVector by lazy` |
| 587 | `val Lucide.ListTree: ImageVector get()` |
| 588 | `private val iconListTree: ImageVector by lazy` |
| 598 | `val Lucide.ListX: ImageVector get()` |
| 599 | `private val iconListX: ImageVector by lazy` |
| 609 | `val Lucide.Loader2: ImageVector get()` |
| 610 | `private val iconLoader2: ImageVector by lazy` |
| 616 | `val Lucide.LoaderCircle: ImageVector get()` |
| 617 | `private val iconLoaderCircle: ImageVector by lazy` |
| 623 | `val Lucide.LocateFixed: ImageVector get()` |
| 624 | `private val iconLocateFixed: ImageVector by lazy` |
| 635 | `val Lucide.Lock: ImageVector get()` |
| 636 | `private val iconLock: ImageVector by lazy` |
| 643 | `val Lucide.LockKeyhole: ImageVector get()` |
| 644 | `private val iconLockKeyhole: ImageVector by lazy` |

## core/designsystem/icon/LucideIconsM_R.kt

| Line | Declaration |
|---|---|
| 51 | `val Lucide.Mail: ImageVector get()` |
| 52 | `private val iconMail: ImageVector by lazy` |
| 59 | `val Lucide.Map: ImageVector get()` |
| 60 | `private val iconMap: ImageVector by lazy` |
| 68 | `val Lucide.Maximize: ImageVector get()` |
| 69 | `private val iconMaximize: ImageVector by lazy` |
| 78 | `val Lucide.Maximize2: ImageVector get()` |
| 79 | `private val iconMaximize2: ImageVector by lazy` |
| 88 | `val Lucide.Menu: ImageVector get()` |
| 89 | `private val iconMenu: ImageVector by lazy` |
| 97 | `val Lucide.Merge: ImageVector get()` |
| 98 | `private val iconMerge: ImageVector by lazy` |
| 106 | `val Lucide.MessageCircle: ImageVector get()` |
| 107 | `private val iconMessageCircle: ImageVector by lazy` |
| 113 | `val Lucide.MessageCircleQuestion: ImageVector get()` |
| 114 | `private val iconMessageCircleQuestion: ImageVector by lazy` |
| 122 | `val Lucide.MessageCircleQuestionMark: ImageVector get()` |
| 123 | `private val iconMessageCircleQuestionMark: ImageVector by lazy` |
| 131 | `val Lucide.MessageSquare: ImageVector get()` |
| 132 | `private val iconMessageSquare: ImageVector by lazy` |
| 138 | `val Lucide.MessageSquareMore: ImageVector get()` |
| 139 | `private val iconMessageSquareMore: ImageVector by lazy` |
| 148 | `val Lucide.MessageSquarePlus: ImageVector get()` |
| 149 | `private val iconMessageSquarePlus: ImageVector by lazy` |
| 157 | `val Lucide.MessageSquareQuote: ImageVector get()` |
| 158 | `private val iconMessageSquareQuote: ImageVector by lazy` |
| 166 | `val Lucide.MessageSquareReply: ImageVector get()` |
| 167 | `private val iconMessageSquareReply: ImageVector by lazy` |
| 175 | `val Lucide.MessageSquareText: ImageVector get()` |
| 176 | `private val iconMessageSquareText: ImageVector by lazy` |
| 185 | `val Lucide.MessagesSquare: ImageVector get()` |
| 186 | `private val iconMessagesSquare: ImageVector by lazy` |
| 193 | `val Lucide.Minimize2: ImageVector get()` |
| 194 | `private val iconMinimize2: ImageVector by lazy` |
| 203 | `val Lucide.Minus: ImageVector get()` |
| 204 | `private val iconMinus: ImageVector by lazy` |
| 210 | `val Lucide.Monitor: ImageVector get()` |
| 211 | `private val iconMonitor: ImageVector by lazy` |
| 219 | `val Lucide.MonitorCloud: ImageVector get()` |
| 220 | `private val iconMonitorCloud: ImageVector by lazy` |
| 229 | `val Lucide.MonitorCog: ImageVector get()` |
| 230 | `private val iconMonitorCog: ImageVector by lazy` |
| 247 | `val Lucide.MonitorDot: ImageVector get()` |
| 248 | `private val iconMonitorDot: ImageVector by lazy` |
| 257 | `val Lucide.Moon: ImageVector get()` |
| 258 | `private val iconMoon: ImageVector by lazy` |
| 264 | `val Lucide.MoonStar: ImageVector get()` |
| 265 | `private val iconMoonStar: ImageVector by lazy` |
| 273 | `val Lucide.MoreHorizontal: ImageVector get()` |
| 274 | `private val iconMoreHorizontal: ImageVector by lazy` |
| 282 | `val Lucide.MoreVertical: ImageVector get()` |
| 283 | `private val iconMoreVertical: ImageVector by lazy` |
| 291 | `val Lucide.MousePointer2: ImageVector get()` |
| 292 | `private val iconMousePointer2: ImageVector by lazy` |
| 298 | `val Lucide.MousePointerClick: ImageVector get()` |
| 299 | `private val iconMousePointerClick: ImageVector by lazy` |
| 309 | `val Lucide.MoveHorizontal: ImageVector get()` |
| 310 | `private val iconMoveHorizontal: ImageVector by lazy` |
| 318 | `val Lucide.MoveUpRight: ImageVector get()` |
| 319 | `private val iconMoveUpRight: ImageVector by lazy` |
| 326 | `val Lucide.Network: ImageVector get()` |
| 327 | `private val iconNetwork: ImageVector by lazy` |
| 337 | `val Lucide.OctagonX: ImageVector get()` |
| 338 | `private val iconOctagonX: ImageVector by lazy` |
| 346 | `val Lucide.Package: ImageVector get()` |
| 347 | `private val iconPackage: ImageVector by lazy` |
| 356 | `val Lucide.PackagePlus: ImageVector get()` |
| 357 | `private val iconPackagePlus: ImageVector by lazy` |
| 368 | `val Lucide.Palette: ImageVector get()` |
| 369 | `private val iconPalette: ImageVector by lazy` |
| 379 | `val Lucide.PanelBottom: ImageVector get()` |
| 380 | `private val iconPanelBottom: ImageVector by lazy` |
| 387 | `val Lucide.PanelBottomClose: ImageVector get()` |
| 388 | `private val iconPanelBottomClose: ImageVector by lazy` |
| 396 | `val Lucide.PanelLeft: ImageVector get()` |
| 397 | `private val iconPanelLeft: ImageVector by lazy` |
| 404 | `val Lucide.PanelRight: ImageVector get()` |
| 405 | `private val iconPanelRight: ImageVector by lazy` |
| 412 | `val Lucide.PanelRightOpen: ImageVector get()` |
| 413 | `private val iconPanelRightOpen: ImageVector by lazy` |
| 421 | `val Lucide.PanelTop: ImageVector get()` |
| 422 | `private val iconPanelTop: ImageVector by lazy` |
| 429 | `val Lucide.Paperclip: ImageVector get()` |
| 430 | `private val iconPaperclip: ImageVector by lazy` |
| 436 | `val Lucide.Pause: ImageVector get()` |
| 437 | `private val iconPause: ImageVector by lazy` |
| 444 | `val Lucide.PauseCircle: ImageVector get()` |
| 445 | `private val iconPauseCircle: ImageVector by lazy` |
| 453 | `val Lucide.PawPrint: ImageVector get()` |
| 454 | `private val iconPawPrint: ImageVector by lazy` |
| 463 | `val Lucide.Pencil: ImageVector get()` |
| 464 | `private val iconPencil: ImageVector by lazy` |
| 471 | `val Lucide.PencilLine: ImageVector get()` |
| 472 | `private val iconPencilLine: ImageVector by lazy` |
| 480 | `val Lucide.Phone: ImageVector get()` |
| 481 | `private val iconPhone: ImageVector by lazy` |
| 487 | `val Lucide.Pin: ImageVector get()` |
| 488 | `private val iconPin: ImageVector by lazy` |
| 495 | `val Lucide.PinOff: ImageVector get()` |
| 496 | `private val iconPinOff: ImageVector by lazy` |
| 505 | `val Lucide.Plane: ImageVector get()` |
| 506 | `private val iconPlane: ImageVector by lazy` |
| 512 | `val Lucide.Play: ImageVector get()` |
| 513 | `private val iconPlay: ImageVector by lazy` |
| 519 | `val Lucide.PlayCircle: ImageVector get()` |
| 520 | `private val iconPlayCircle: ImageVector by lazy` |
| 527 | `val Lucide.Plug: ImageVector get()` |
| 528 | `private val iconPlug: ImageVector by lazy` |
| 537 | `val Lucide.PlugZap: ImageVector get()` |
| 538 | `private val iconPlugZap: ImageVector by lazy` |
| 548 | `val Lucide.Plus: ImageVector get()` |
| 549 | `private val iconPlus: ImageVector by lazy` |
| 556 | `val Lucide.PlusCircle: ImageVector get()` |
| 557 | `private val iconPlusCircle: ImageVector by lazy` |
| 565 | `val Lucide.Power: ImageVector get()` |
| 566 | `private val iconPower: ImageVector by lazy` |
| 573 | `val Lucide.PowerOff: ImageVector get()` |
| 574 | `private val iconPowerOff: ImageVector by lazy` |
| 583 | `val Lucide.Presentation: ImageVector get()` |
| 584 | `private val iconPresentation: ImageVector by lazy` |
| 592 | `val Lucide.QrCode: ImageVector get()` |
| 593 | `private val iconQrCode: ImageVector by lazy` |
| 610 | `val Lucide.ReceiptText: ImageVector get()` |
| 611 | `private val iconReceiptText: ImageVector by lazy` |
| 620 | `val Lucide.RefreshCw: ImageVector get()` |
| 621 | `private val iconRefreshCw: ImageVector by lazy` |
| 630 | `val Lucide.Rocket: ImageVector get()` |
| 631 | `private val iconRocket: ImageVector by lazy` |
| 640 | `val Lucide.RotateCcw: ImageVector get()` |
| 641 | `private val iconRotateCcw: ImageVector by lazy` |
| 648 | `val Lucide.RotateCw: ImageVector get()` |
| 649 | `private val iconRotateCw: ImageVector by lazy` |
| 656 | `val Lucide.Rows3: ImageVector get()` |
| 657 | `private val iconRows3: ImageVector by lazy` |
| 665 | `val Lucide.RulerDimensionLine: ImageVector get()` |
| 666 | `private val iconRulerDimensionLine: ImageVector by lazy` |

## core/designsystem/icon/LucideIconsS_Z.kt

| Line | Declaration |
|---|---|
| 51 | `val Lucide.Save: ImageVector get()` |
| 52 | `private val iconSave: ImageVector by lazy` |
| 60 | `val Lucide.Scissors: ImageVector get()` |
| 61 | `private val iconScissors: ImageVector by lazy` |
| 71 | `val Lucide.ScreenShare: ImageVector get()` |
| 72 | `private val iconScreenShare: ImageVector by lazy` |
| 82 | `val Lucide.ScrollText: ImageVector get()` |
| 83 | `private val iconScrollText: ImageVector by lazy` |
| 92 | `val Lucide.Search: ImageVector get()` |
| 93 | `private val iconSearch: ImageVector by lazy` |
| 100 | `val Lucide.SearchX: ImageVector get()` |
| 101 | `private val iconSearchX: ImageVector by lazy` |
| 110 | `val Lucide.Send: ImageVector get()` |
| 111 | `private val iconSend: ImageVector by lazy` |
| 118 | `val Lucide.SendHorizonal: ImageVector get()` |
| 119 | `private val iconSendHorizonal: ImageVector by lazy` |
| 126 | `val Lucide.SendHorizontal: ImageVector get()` |
| 127 | `private val iconSendHorizontal: ImageVector by lazy` |
| 134 | `val Lucide.Server: ImageVector get()` |
| 135 | `private val iconServer: ImageVector by lazy` |
| 144 | `val Lucide.ServerCog: ImageVector get()` |
| 145 | `private val iconServerCog: ImageVector by lazy` |
| 162 | `val Lucide.Settings: ImageVector get()` |
| 163 | `private val iconSettings: ImageVector by lazy` |
| 170 | `val Lucide.Settings2: ImageVector get()` |
| 171 | `private val iconSettings2: ImageVector by lazy` |
| 180 | `val Lucide.Share2: ImageVector get()` |
| 181 | `private val iconShare2: ImageVector by lazy` |
| 191 | `val Lucide.Shield: ImageVector get()` |
| 192 | `private val iconShield: ImageVector by lazy` |
| 198 | `val Lucide.ShieldAlert: ImageVector get()` |
| 199 | `private val iconShieldAlert: ImageVector by lazy` |
| 207 | `val Lucide.ShieldCheck: ImageVector get()` |
| 208 | `private val iconShieldCheck: ImageVector by lazy` |
| 215 | `val Lucide.ShieldOff: ImageVector get()` |
| 216 | `private val iconShieldOff: ImageVector by lazy` |
| 224 | `val Lucide.Shrink: ImageVector get()` |
| 225 | `private val iconShrink: ImageVector by lazy` |
| 234 | `val Lucide.Signpost: ImageVector get()` |
| 235 | `private val iconSignpost: ImageVector by lazy` |
| 243 | `val Lucide.SlidersHorizontal: ImageVector get()` |
| 244 | `private val iconSlidersHorizontal: ImageVector by lazy` |
| 258 | `val Lucide.Smartphone: ImageVector get()` |
| 259 | `private val iconSmartphone: ImageVector by lazy` |
| 266 | `val Lucide.Sparkles: ImageVector get()` |
| 267 | `private val iconSparkles: ImageVector by lazy` |
| 276 | `val Lucide.Split: ImageVector get()` |
| 277 | `private val iconSplit: ImageVector by lazy` |
| 286 | `val Lucide.Square: ImageVector get()` |
| 287 | `private val iconSquare: ImageVector by lazy` |
| 293 | `val Lucide.SquareArrowOutUpRight: ImageVector get()` |
| 294 | `private val iconSquareArrowOutUpRight: ImageVector by lazy` |
| 302 | `val Lucide.SquareDashedMousePointer: ImageVector get()` |
| 303 | `private val iconSquareDashedMousePointer: ImageVector by lazy` |
| 318 | `val Lucide.SquareKanban: ImageVector get()` |
| 319 | `private val iconSquareKanban: ImageVector by lazy` |
| 328 | `val Lucide.SquarePen: ImageVector get()` |
| 329 | `private val iconSquarePen: ImageVector by lazy` |
| 336 | `val Lucide.SquareSlash: ImageVector get()` |
| 337 | `private val iconSquareSlash: ImageVector by lazy` |
| 344 | `val Lucide.SquareTerminal: ImageVector get()` |
| 345 | `private val iconSquareTerminal: ImageVector by lazy` |
| 353 | `val Lucide.Star: ImageVector get()` |
| 354 | `private val iconStar: ImageVector by lazy` |
| 360 | `val Lucide.Stethoscope: ImageVector get()` |
| 361 | `private val iconStethoscope: ImageVector by lazy` |
| 371 | `val Lucide.StickyNote: ImageVector get()` |
| 372 | `private val iconStickyNote: ImageVector by lazy` |
| 379 | `val Lucide.Store: ImageVector get()` |
| 380 | `private val iconStore: ImageVector by lazy` |
| 388 | `val Lucide.Sun: ImageVector get()` |
| 389 | `private val iconSun: ImageVector by lazy` |
| 403 | `val Lucide.Tablet: ImageVector get()` |
| 404 | `private val iconTablet: ImageVector by lazy` |
| 411 | `val Lucide.Tag: ImageVector get()` |
| 412 | `private val iconTag: ImageVector by lazy` |
| 419 | `val Lucide.Tags: ImageVector get()` |
| 420 | `private val iconTags: ImageVector by lazy` |
| 428 | `val Lucide.Terminal: ImageVector get()` |
| 429 | `private val iconTerminal: ImageVector by lazy` |
| 436 | `val Lucide.TerminalSquare: ImageVector get()` |
| 437 | `private val iconTerminalSquare: ImageVector by lazy` |
| 445 | `val Lucide.TextCursor: ImageVector get()` |
| 446 | `private val iconTextCursor: ImageVector by lazy` |
| 454 | `val Lucide.TextQuote: ImageVector get()` |
| 455 | `private val iconTextQuote: ImageVector by lazy` |
| 464 | `val Lucide.TextSelect: ImageVector get()` |
| 465 | `private val iconTextSelect: ImageVector by lazy` |
| 485 | `val Lucide.Timer: ImageVector get()` |
| 486 | `private val iconTimer: ImageVector by lazy` |
| 494 | `val Lucide.Trash2: ImageVector get()` |
| 495 | `private val iconTrash2: ImageVector by lazy` |
| 505 | `val Lucide.TrendingUp: ImageVector get()` |
| 506 | `private val iconTrendingUp: ImageVector by lazy` |
| 513 | `val Lucide.TriangleAlert: ImageVector get()` |
| 514 | `private val iconTriangleAlert: ImageVector by lazy` |
| 522 | `val Lucide.Type: ImageVector get()` |
| 523 | `private val iconType: ImageVector by lazy` |
| 531 | `val Lucide.Undo2: ImageVector get()` |
| 532 | `private val iconUndo2: ImageVector by lazy` |
| 539 | `val Lucide.UndoDot: ImageVector get()` |
| 540 | `private val iconUndoDot: ImageVector by lazy` |
| 548 | `val Lucide.Unlink: ImageVector get()` |
| 549 | `private val iconUnlink: ImageVector by lazy` |
| 560 | `val Lucide.Unplug: ImageVector get()` |
| 561 | `private val iconUnplug: ImageVector by lazy` |
| 572 | `val Lucide.Upload: ImageVector get()` |
| 573 | `private val iconUpload: ImageVector by lazy` |
| 581 | `val Lucide.User: ImageVector get()` |
| 582 | `private val iconUser: ImageVector by lazy` |
| 589 | `val Lucide.UserRound: ImageVector get()` |
| 590 | `private val iconUserRound: ImageVector by lazy` |
| 597 | `val Lucide.Users: ImageVector get()` |
| 598 | `private val iconUsers: ImageVector by lazy` |
| 607 | `val Lucide.Volume2: ImageVector get()` |
| 608 | `private val iconVolume2: ImageVector by lazy` |
| 616 | `val Lucide.Wand2: ImageVector get()` |
| 617 | `private val iconWand2: ImageVector by lazy` |
| 630 | `val Lucide.Webhook: ImageVector get()` |
| 631 | `private val iconWebhook: ImageVector by lazy` |
| 639 | `val Lucide.Wifi: ImageVector get()` |
| 640 | `private val iconWifi: ImageVector by lazy` |
| 649 | `val Lucide.WifiOff: ImageVector get()` |
| 650 | `private val iconWifiOff: ImageVector by lazy` |
| 662 | `val Lucide.Workflow: ImageVector get()` |
| 663 | `private val iconWorkflow: ImageVector by lazy` |
| 671 | `val Lucide.Wrench: ImageVector get()` |
| 672 | `private val iconWrench: ImageVector by lazy` |
| 678 | `val Lucide.X: ImageVector get()` |
| 679 | `private val iconX: ImageVector by lazy` |
| 686 | `val Lucide.XCircle: ImageVector get()` |
| 687 | `private val iconXCircle: ImageVector by lazy` |
| 695 | `val Lucide.Zap: ImageVector get()` |
| 696 | `private val iconZap: ImageVector by lazy` |
| 702 | `val Lucide.ZoomIn: ImageVector get()` |
| 703 | `private val iconZoomIn: ImageVector by lazy` |
| 712 | `val Lucide.ZoomOut: ImageVector get()` |
| 713 | `private val iconZoomOut: ImageVector by lazy` |

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

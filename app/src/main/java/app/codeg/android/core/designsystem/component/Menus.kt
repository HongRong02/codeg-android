package app.codeg.android.core.designsystem.component

import app.codeg.android.core.designsystem.icon.Lucide
import app.codeg.android.core.designsystem.icon.Check
import app.codeg.android.core.designsystem.icon.ChevronRight
import app.codeg.android.core.designsystem.icon.EllipsisVertical
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import kotlinx.coroutines.delay

/** Web rounded-2xl containers and rounded-xl menu rows. */
private val MenuShape = CodegShapes.medium
private val MenuTextStyle = TextStyle(fontSize = 14.sp, lineHeight = 20.sp)

enum class CodegMenuItemVariant { Default, Destructive }

/** Place inside the same Box as its anchor, like M3 DropdownMenu. Items dismiss via the caller. */
@Composable
fun CodegDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit,
) {
    MenuPopup(expanded, onDismissRequest, offset, properties) {
        MenuSurface(modifier, content = content)
    }
}

@Composable
private fun MenuSurface(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    shape: Shape = MenuShape,
    elevation: Dp = 6.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    val web = CodegTheme.colors.web
    Column(
        modifier.widthIn(min = 128.dp).width(IntrinsicSize.Max)
            .shadow(elevation, shape)
            .background(web.popover, shape)
            .border(1.dp, web.border, shape)
            .clip(shape)
            .verticalScroll(rememberScrollState())
            .padding(contentPadding),
    ) {
        CompositionLocalProvider(LocalContentColor provides web.popoverForeground) {
            ProvideTextStyle(MenuTextStyle) { content() }
        }
    }
}

/** Slots inherit text/icon color; leading and trailing icons are constrained to 16dp. */
@Composable
fun CodegMenuItem(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    shortcut: String? = null,
    enabled: Boolean = true,
    variant: CodegMenuItemVariant = CodegMenuItemVariant.Default,
) {
    val interactions = remember { MutableInteractionSource() }
    MenuRow(
        text, modifier.clickable(
            interactionSource = interactions, indication = null, enabled = enabled,
            role = Role.Button, onClick = onClick,
        ), interactions, enabled, variant, leadingIcon, trailingIcon, shortcut,
    )
}

@Composable
private fun MenuRow(
    text: @Composable () -> Unit,
    modifier: Modifier,
    interactions: MutableInteractionSource,
    enabled: Boolean,
    variant: CodegMenuItemVariant,
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
    shortcut: String?,
) {
    val web = CodegTheme.colors.web
    val pressed by interactions.collectIsPressedAsState()
    val focused by interactions.collectIsFocusedAsState()
    val hovered by interactions.collectIsHoveredAsState()
    val active = enabled && (pressed || focused || hovered)
    val foreground = when {
        variant == CodegMenuItemVariant.Destructive -> web.destructive
        active -> web.accentForeground
        else -> web.popoverForeground
    }
    Row(
        modifier.fillMaxWidth().hoverable(interactions, enabled)
            .alpha(if (enabled) 1f else 0.5f)
            .background(if (active) web.accent else Color.Transparent, CodegShapes.small)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CompositionLocalProvider(LocalContentColor provides foreground) {
            ProvideTextStyle(MenuTextStyle) {
                if (leadingIcon != null) Box(Modifier.size(16.dp)) { leadingIcon() }
                Box(Modifier.weight(1f)) { text() }
                if (shortcut != null) {
                    Text(shortcut, color = if (active) foreground else web.mutedForeground, fontSize = 12.sp)
                }
                if (trailingIcon != null) Box(Modifier.size(16.dp)) { trailingIcon() }
            }
        }
    }
}

@Composable
fun CodegMenuCheckboxItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    val interactions = remember { MutableInteractionSource() }
    MenuRow(
        text = text,
        modifier = modifier.toggleable(
            value = checked, enabled = enabled, role = Role.Checkbox,
            interactionSource = interactions, indication = null, onValueChange = onCheckedChange,
        ),
        interactions = interactions,
        enabled = enabled,
        variant = CodegMenuItemVariant.Default,
        leadingIcon = leadingIcon,
        trailingIcon = { if (checked) Icon(Lucide.Check, contentDescription = null) },
        shortcut = null,
    )
}

/** Bleed across the menu's p-1 inset, equivalent to -mx-1 my-1 h-px. */
@Composable
fun CodegMenuSeparator(modifier: Modifier = Modifier) {
    val bleed = with(LocalDensity.current) { 4.dp.roundToPx() }
    Box(
        modifier.padding(vertical = 4.dp).layout { measurable, constraints ->
            val placeable = measurable.measure(constraints.copy(
                minWidth = constraints.minWidth + bleed * 2,
                maxWidth = if (constraints.hasBoundedWidth) constraints.maxWidth + bleed * 2 else constraints.maxWidth,
            ))
            layout((placeable.width - bleed * 2).coerceAtLeast(0), placeable.height) {
                placeable.placeRelative(-bleed, 0)
            }
        }.fillMaxWidth().height(1.dp).background(CodegTheme.colors.web.border),
    )
}

@Composable
fun CodegMenuLabel(text: @Composable () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
        CompositionLocalProvider(LocalContentColor provides CodegTheme.colors.web.popoverForeground) {
            ProvideTextStyle(MenuTextStyle.copy(fontWeight = FontWeight.Medium)) { text() }
        }
    }
}

/** Long press any wrapped content. Normal taps remain available to child controls. */
@Composable
fun CodegContextMenu(
    menu: @Composable ColumnScope.(() -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val dismiss = { expanded = false }
    Box(modifier.longPressMenu(enabled) { expanded = true }) {
        content()
        CodegDropdownMenu(expanded && enabled, dismiss) { menu(dismiss) }
    }
    LaunchedEffect(enabled) { if (!enabled) expanded = false }
}

/** Controlled anchored popover; call within the anchor's Box. */
@Composable
fun CodegPopover(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit,
) {
    MenuPopup(expanded, onDismissRequest, offset, properties) {
        MenuSurface(
            modifier.width(288.dp), PaddingValues(16.dp),
            shape = CodegShapes.medium, elevation = 24.dp,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp), content = content)
        }
    }
}

/** Long-press tooltip with an accessibility-adjusted timeout and outside/back dismissal. */
@Composable
fun CodegTooltip(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val accessibility = LocalAccessibilityManager.current
    LaunchedEffect(expanded, enabled, text) {
        if (!enabled) expanded = false
        if (expanded) {
            delay(accessibility?.calculateRecommendedTimeoutMillis(
                originalTimeoutMillis = 1500L, containsText = true,
            ) ?: 1500L)
            expanded = false
        }
    }
    Box(modifier.longPressMenu(enabled) { expanded = true }) {
        content()
        MenuPopup(expanded && enabled, { expanded = false }, DpOffset.Zero, PopupProperties(focusable = true)) {
            TooltipSurface(text)
        }
    }
}

@Composable
private fun TooltipSurface(text: String) {
    val web = CodegTheme.colors.web
    Text(
        text, modifier = Modifier.widthIn(max = 320.dp)
            .background(web.foreground, CodegShapes.medium)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        color = web.background, fontSize = 12.sp, lineHeight = 16.sp,
    )
}

@Composable
private fun Modifier.longPressMenu(enabled: Boolean, onLongPress: () -> Unit): Modifier {
    // Showing the popup recomposes the anchor; keep the active gesture alive across that change.
    val currentOnLongPress by rememberUpdatedState(onLongPress)
    return if (!enabled) this else this
        .semantics { onLongClick { currentOnLongPress(); true } }
        .pointerInput(Unit) {
            awaitEachGesture {
                val down = awaitFirstDown(requireUnconsumed = false)
                if (awaitLongPressOrCancellation(down.id) != null) {
                    currentOnLongPress()
                    do {
                        // Consume before child click handlers see release, avoiding an accidental click.
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        event.changes.forEach { it.consume() }
                    } while (event.changes.any { it.pressed })
                }
            }
        }
}

@Composable
private fun MenuPopup(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    offset: DpOffset,
    properties: PopupProperties,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val gap = with(density) { 4.dp.roundToPx() }
    val collisionPadding = with(density) { 8.dp.roundToPx() }
    val offsetPx = with(density) { IntOffset(offset.x.roundToPx(), offset.y.roundToPx()) }
    val provider = remember(gap, collisionPadding, offsetPx) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = menuPopupPosition(
                anchorBounds.translate(offsetPx), windowSize, popupContentSize, layoutDirection, gap, collisionPadding,
            )
        }
    }
    if (expanded) Popup(provider, onDismissRequest, properties) {
        // Constrain measurement as well as placement so long items cannot consume the edge margin.
        Box(Modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(constraints.copy(
                minWidth = 0,
                maxWidth = (constraints.maxWidth - collisionPadding * 2).coerceAtLeast(0),
                minHeight = 0,
                maxHeight = (constraints.maxHeight - collisionPadding * 2).coerceAtLeast(0),
            ))
            layout(placeable.width, placeable.height) { placeable.place(0, 0) }
        }) { content() }
    }
}

/** Prefer below, then above, and keep the result within the visible window (including RTL). */
internal fun menuPopupPosition(
    anchor: IntRect,
    window: IntSize,
    popup: IntSize,
    direction: LayoutDirection,
    gap: Int,
    collisionPadding: Int = 8,
): IntOffset {
    val x = if (direction == LayoutDirection.Ltr) anchor.left else anchor.right - popup.width
    val below = anchor.bottom + gap
    val y = if (below + popup.height <= window.height - collisionPadding) below else anchor.top - gap - popup.height
    val marginX = collisionPadding.coerceIn(0, ((window.width - popup.width) / 2).coerceAtLeast(0))
    val marginY = collisionPadding.coerceIn(0, ((window.height - popup.height) / 2).coerceAtLeast(0))
    return IntOffset(
        x.coerceIn(marginX, (window.width - popup.width - marginX).coerceAtLeast(marginX)),
        y.coerceIn(marginY, (window.height - popup.height - marginY).coerceAtLeast(marginY)),
    )
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
private annotation class MenuPreviews

@Composable
private fun MenuPreviewTheme(content: @Composable () -> Unit) {
    CodegTheme {
        Surface(color = CodegTheme.colors.web.background) {
            Box(Modifier.padding(16.dp)) { content() }
        }
    }
}

@Composable
private fun GalleryItems() {
    CodegMenuLabel({ Text(stringResource(R.string.ds_menus_actions)) })
    CodegMenuItem(
        text = { Text(stringResource(R.string.ds_menus_open)) }, onClick = {},
        leadingIcon = { Icon(Lucide.EllipsisVertical, null) },
        shortcut = stringResource(R.string.ds_menus_shortcut),
    )
    CodegMenuItem(
        text = { Text(stringResource(R.string.ds_menus_more)) }, onClick = {},
        trailingIcon = { Icon(Lucide.ChevronRight, null) },
    )
    CodegMenuItem({ Text(stringResource(R.string.ds_menus_disabled)) }, {}, enabled = false)
    CodegMenuSeparator()
    CodegMenuCheckboxItem(true, {}, { Text(stringResource(R.string.ds_menus_checked)) })
    CodegMenuCheckboxItem(false, {}, { Text(stringResource(R.string.ds_menus_unchecked)) })
    CodegMenuCheckboxItem(true, {}, { Text(stringResource(R.string.ds_menus_disabled)) }, enabled = false)
    CodegMenuSeparator()
    CodegMenuItem(
        { Text(stringResource(R.string.ds_menus_delete)) }, {}, variant = CodegMenuItemVariant.Destructive,
    )
    CodegMenuItem(
        { Text(stringResource(R.string.ds_menus_delete)) }, {}, enabled = false,
        variant = CodegMenuItemVariant.Destructive,
    )
}

/** Inline variants remain visible in static previews; interactive anchors exercise popup behavior. */
@Composable
internal fun T43Gallery() {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        MenuSurface(Modifier.width(240.dp)) { GalleryItems() }
        var dropdown by remember { mutableStateOf(false) }
        Box {
            CodegMenuItem({ Text(stringResource(R.string.ds_menus_actions)) }, { dropdown = true })
            CodegDropdownMenu(dropdown, { dropdown = false }) { GalleryItems() }
        }
        CodegContextMenu(menu = { dismiss ->
            CodegMenuItem({ Text(stringResource(R.string.ds_menus_open)) }, dismiss)
        }) { Text(stringResource(R.string.ds_menus_context)) }
        var popover by remember { mutableStateOf(false) }
        Box {
            CodegMenuItem({ Text(stringResource(R.string.ds_menus_popover)) }, { popover = true })
            CodegPopover(popover, { popover = false }) { Text(stringResource(R.string.ds_menus_popover)) }
        }
        CodegTooltip(stringResource(R.string.ds_menus_tooltip)) {
            Text(stringResource(R.string.ds_menus_tooltip))
        }
    }
}

@MenuPreviews
@Composable
private fun DropdownMenuPreview() = MenuPreviewTheme {
    Box { CodegDropdownMenu(true, {}) { GalleryItems() } }
}

@MenuPreviews
@Composable
private fun MenuItemPreview() = MenuPreviewTheme { MenuSurface(Modifier.width(240.dp)) { GalleryItems() } }

@MenuPreviews
@Composable
private fun MenuSeparatorPreview() = MenuPreviewTheme { MenuSurface { CodegMenuSeparator() } }

@MenuPreviews
@Composable
private fun MenuLabelPreview() = MenuPreviewTheme {
    CodegMenuLabel({ Text(stringResource(R.string.ds_menus_actions)) })
}

@MenuPreviews
@Composable
private fun MenuCheckboxPreview() = MenuPreviewTheme {
    MenuSurface {
        CodegMenuCheckboxItem(true, {}, { Text(stringResource(R.string.ds_menus_checked)) })
        CodegMenuCheckboxItem(false, {}, { Text(stringResource(R.string.ds_menus_unchecked)) })
    }
}

@MenuPreviews
@Composable
private fun ContextMenuPreview() = MenuPreviewTheme {
    CodegContextMenu(menu = { dismiss ->
        CodegMenuItem({ Text(stringResource(R.string.ds_menus_open)) }, dismiss)
    }) { Text(stringResource(R.string.ds_menus_context)) }
}

@MenuPreviews
@Composable
private fun PopoverPreview() = MenuPreviewTheme {
    Box { CodegPopover(true, {}) { Text(stringResource(R.string.ds_menus_popover)) } }
}

@MenuPreviews
@Composable
private fun TooltipPreview() = MenuPreviewTheme {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TooltipSurface(stringResource(R.string.ds_menus_tooltip))
        CodegTooltip(stringResource(R.string.ds_menus_tooltip)) { Text(stringResource(R.string.ds_menus_context)) }
    }
}

@MenuPreviews
@Composable
private fun GalleryPreview() = MenuPreviewTheme { T43Gallery() }

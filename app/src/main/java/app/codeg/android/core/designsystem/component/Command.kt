package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.withOpacity

/** IDs must be unique across groups. Keywords participate in filtering. */
data class CodegCommandItem(
    val id: String,
    val label: String,
    val keywords: List<String> = emptyList(),
    val enabled: Boolean = true,
)
data class CodegCommandGroup(val heading: String, val items: List<CodegCommandItem>)

internal fun filterCommandGroups(groups: List<CodegCommandGroup>, query: String): List<CodegCommandGroup> {
    val needle = query.trim()
    return groups.mapNotNull { group ->
        val matches = group.items.filter { item ->
            needle.isEmpty() || item.label.contains(needle, ignoreCase = true) ||
                item.keywords.any { it.contains(needle, ignoreCase = true) }
        }
        if (matches.isEmpty()) null else group.copy(items = matches)
    }
}

@Composable
fun CodegCommandList(
    query: String,
    onQueryChange: (String) -> Unit,
    groups: List<CodegCommandGroup>,
    onSelect: (CodegCommandItem) -> Unit,
    modifier: Modifier = Modifier,
    empty: String = stringResource(R.string.ds_misc_no_results),
    placeholder: String = stringResource(R.string.ds_misc_search),
) {
    val web = CodegTheme.colors.web
    val filtered = remember(groups, query) { filterCommandGroups(groups, query) }
    val enabled = filtered.flatMap { it.items }.filter { it.enabled }
    var selectedId by remember(query, groups) { mutableStateOf<String?>(null) }
    val activeId = selectedId?.takeIf { id -> enabled.any { it.id == id } } ?: enabled.firstOrNull()?.id
    val listState = rememberLazyListState()
    val rowIds = filtered.flatMap { group -> listOf<String?>(null) + group.items.map { it.id } }
    LaunchedEffect(activeId, filtered) {
        val row = rowIds.indexOf(activeId)
        if (activeId != null && row >= 0) listState.animateScrollToItem(row)
    }
    Column(modifier.fillMaxWidth().clip(CodegShapes.extraSmall).background(web.popover)
        .onPreviewKeyEvent { event ->
            if (event.type != KeyEventType.KeyDown) false
            else when (event.key) {
                Key.DirectionDown, Key.DirectionUp -> {
                    if (enabled.isNotEmpty()) {
                        val index = enabled.indexOfFirst { it.id == activeId }
                        val delta = if (event.key == Key.DirectionDown) 1 else -1
                        selectedId = enabled[(index + delta + enabled.size) % enabled.size].id
                    }
                    true
                }
                Key.Enter -> { enabled.firstOrNull { it.id == activeId }?.let(onSelect); true }
                else -> false
            }
        }) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp).height(40.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // TODO(T51): Lucide.Search
            Icon(Icons.Default.Search, null, Modifier.size(16.dp), tint = web.popoverForeground.withOpacity(.5f))
            BasicTextField(query, onQueryChange, Modifier.weight(1f), singleLine = true,
                textStyle = TextStyle(color = web.popoverForeground, fontSize = 14.sp),
                cursorBrush = SolidColor(web.popoverForeground), decorationBox = { inner ->
                    Box { if (query.isEmpty()) Text(placeholder, color = web.mutedForeground, fontSize = 14.sp, maxLines = 1); inner() }
                })
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(web.border))
        LazyColumn(Modifier.fillMaxWidth().heightIn(max = 300.dp), state = listState) {
            if (filtered.isEmpty()) item {
                Box(Modifier.fillMaxWidth().padding(vertical = 24.dp), contentAlignment = Alignment.Center) {
                    Text(empty, color = web.popoverForeground, fontSize = 14.sp)
                }
            }
            filtered.forEach { group ->
                item {
                    Text(group.heading, color = web.mutedForeground, fontSize = 12.sp, fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 6.dp),
                        maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                items(group.items) { item ->
                    val selected = item.id == activeId
                    Text(item.label, fontSize = 14.sp,
                        color = (if (selected) web.accentForeground else web.foreground).withOpacity(if (item.enabled) 1f else .5f),
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 4.dp).fillMaxWidth()
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(6.dp))
                            .then(if (selected) Modifier.background(web.accent) else Modifier)
                            .onFocusChanged { if (it.isFocused && item.enabled) selectedId = item.id }
                            .selectable(selected, enabled = item.enabled, onClick = { selectedId = item.id; onSelect(item) })
                            .padding(horizontal = 8.dp, vertical = 6.dp))
                }
            }
        }
    }
}

@Composable internal fun T53CommandGallery() {
    var query by remember { mutableStateOf("") }
    val heading = stringResource(R.string.ds_misc_group)
    val label = stringResource(R.string.ds_misc_details)
    CodegCommandList(query, { query = it }, listOf(CodegCommandGroup(heading, listOf(
        CodegCommandItem("details", label), CodegCommandItem("disabled", label, enabled = false),
    ))), {})
}
@Preview @Composable private fun CommandLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T53CommandGallery() } }
@Preview @Composable private fun CommandDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T53CommandGallery() } }

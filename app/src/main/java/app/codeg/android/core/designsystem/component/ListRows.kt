package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme

/** A controlled list item. Supply [trailing] or [onMoreClick] for row actions. */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CodegListRow(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    selected: Boolean = false,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    onMoreClick: (() -> Unit)? = null,
) {
    val web = CodegTheme.colors.web
    val interactions = remember { MutableInteractionSource() }
    val pressed by interactions.collectIsPressedAsState()
    val foreground = if (selected) web.accentForeground else web.foreground
    Row(
        modifier.fillMaxWidth().heightIn(min = if (subtitle == null) 48.dp else 56.dp)
            .clip(CodegShapes.extraSmall)
            .background(if (selected || pressed) web.accent else web.background)
            .semantics { this.selected = selected }
            .combinedClickable(interactionSource = interactions, indication = null,
                role = Role.Button, onClick = onClick, onLongClick = onLongClick)
            .padding(horizontal = 12.dp, vertical = if (subtitle == null) 0.dp else 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides foreground) {
            leading?.invoke()
            Column(Modifier.weight(1f)) {
                Text(title, color = foreground, fontSize = 14.sp, maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                subtitle?.let {
                    Text(it, color = if (selected) web.accentForeground else web.mutedForeground,
                        fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            trailing?.invoke()
            onMoreClick?.let { CodegRowMoreButton(it) }
        }
    }
}

/** Touch-visible row menu trigger. Its click is consumed separately from the parent row. */
@Composable
fun CodegRowMoreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(R.string.ds_rows_more),
) {
    val web = CodegTheme.colors.web
    val interactions = remember { MutableInteractionSource() }
    val pressed by interactions.collectIsPressedAsState()
    Box(modifier.size(48.dp).clip(CodegShapes.extraSmall)
        .background(if (pressed) web.accent else androidx.compose.ui.graphics.Color.Transparent)
        .clickable(interactionSource = interactions, indication = null, role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center) {
        Icon(Icons.Rounded.MoreHoriz, contentDescription, Modifier.size(14.dp), tint = web.mutedForeground)
    }
}

/** A section disclosure and optional independent trailing action. */
@Composable
fun CodegSectionHeader(
    title: String,
    count: Int? = null,
    action: (@Composable () -> Unit)? = null,
    collapsed: Boolean = false,
    modifier: Modifier = Modifier,
    onToggle: (() -> Unit)? = null,
) {
    val web = CodegTheme.colors.web
    val state = stringResource(if (collapsed) R.string.ds_rows_collapsed else R.string.ds_rows_expanded)
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(Modifier.weight(1f).heightIn(min = 48.dp).clip(CodegShapes.extraSmall)
            .then(if (onToggle != null) Modifier.clickable(role = Role.Button, onClick = onToggle) else Modifier)
            .semantics {
                heading()
                if (onToggle != null) stateDescription = state
            }
            .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(title, Modifier.weight(1f, fill = false), color = web.mutedForeground,
                fontSize = 12.sp, fontWeight = FontWeight.Medium,
                maxLines = 1, overflow = TextOverflow.Ellipsis)
            count?.let { CodegBadge(it.toString(), CodegBadgeVariant.Secondary) }
            if (onToggle != null) {
                Icon(Icons.Rounded.ChevronRight, null,
                    Modifier.size(14.dp).rotate(if (collapsed) 0f else 90f), tint = web.mutedForeground)
            }
        }
        CompositionLocalProvider(LocalContentColor provides web.mutedForeground) { action?.invoke() }
    }
}

@Composable
internal fun T46RowsGallery() {
    var collapsed by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(false) }
    val title = stringResource(R.string.ds_rows_sample)
    Column {
        CodegSectionHeader(stringResource(R.string.ds_rows_recent), count = 2,
            action = { CodegRowMoreButton({}) }, collapsed = collapsed, onToggle = { collapsed = !collapsed })
        if (!collapsed) {
            CodegListRow(title, { selected = !selected }, selected = selected, onLongClick = {}, onMoreClick = {})
            CodegListRow(title, {}, subtitle = stringResource(R.string.ds_rows_subtitle), selected = true,
                leading = { Icon(Icons.Rounded.ChevronRight, null) },
                trailing = { CodegBadge("2", CodegBadgeVariant.Secondary) }, onMoreClick = {})
        }
        CodegSectionHeader(title)
        CodegSectionHeader(title, count = 0, collapsed = true, onToggle = {})
    }
}

/** All T46 variants, reusable by the follow-up component preview page. */
@Composable
internal fun T46Gallery() {
    Column(Modifier.background(CodegTheme.colors.web.background).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        T46TabsGallery()
        T46BadgesGallery()
        T46FeedbackGallery()
        T46RowsGallery()
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun RowsLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T46Gallery() } }

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun RowsDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T46Gallery() } }

package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.withOpacity

import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role

/** Controlled segmented tabs; [selected] is the selected index in [tabs]. */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CodegTabs(
    selected: Int,
    tabs: List<String>,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = CodegTheme.colors
    val web = colors.web
    val trigger = CodegShapes.small
    val textStyle = LocalTextStyle.current.merge(TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium))
    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val scroll = rememberScrollState()
    BoxWithConstraints(modifier.height(36.dp).clip(CodegShapes.extraLarge).background(web.muted).padding(3.dp)) {
        val availableWidth = constraints.maxWidth
        val widths = tabs.map { label ->
            measurer.measure(label, textStyle, softWrap = false, maxLines = 1).size.width +
                with(density) { 16.dp.roundToPx() }
        }
        val equalWidth = if (constraints.hasBoundedWidth && tabs.isNotEmpty()) availableWidth / tabs.size else 0
        val overflow = widths.any { it > equalWidth }
        Row(
            Modifier.fillMaxHeight().horizontalScroll(scroll).selectableGroup(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEachIndexed { index, label ->
                val active = selected == index
                val requester = remember { BringIntoViewRequester() }
                LaunchedEffect(selected, tabs, availableWidth, textStyle) {
                    if (active && overflow) requester.bringIntoView()
                }
                val widthPx = if (overflow) maxOf(widths[index], equalWidth) else equalWidth
                Box(
                    Modifier.width(with(density) { widthPx.toDp() }).fillMaxHeight().bringIntoViewRequester(requester)
                        .then(if (active && !colors.isDark) Modifier.shadow(1.dp, trigger) else Modifier)
                        .clip(trigger)
                        .background(when {
                            !active -> Color.Transparent
                            colors.isDark -> web.input.withOpacity(0.3f)
                            else -> web.background
                        })
                        .then(if (active && colors.isDark) Modifier.border(1.dp, web.input, trigger) else Modifier)
                        .selectable(active, role = Role.Tab, onClick = { onSelect(index) })
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(label, color = if (active) web.foreground else web.mutedForeground,
                        style = textStyle, softWrap = false,
                        maxLines = 1)
                }
            }
        }
    }
}

@Composable
internal fun T46TabsGallery() {
    var selected by remember { mutableIntStateOf(0) }
    var overflowSelected by remember { mutableIntStateOf(0) }
    CodegTabs(selected, listOf(stringResource(R.string.ds_rows_all), stringResource(R.string.ds_rows_recent)),
        { selected = it }, Modifier.fillMaxWidth())
    val longLabel = List(4) { stringResource(R.string.ds_rows_recent) }.joinToString(" ")
    CodegTabs(overflowSelected, List(4) { longLabel }, { overflowSelected = it }, Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
private fun TabsLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T46TabsGallery() } }

@Preview(showBackground = true)
@Composable
private fun TabsDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T46TabsGallery() } }

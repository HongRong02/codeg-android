package app.codeg.android.core.designsystem.component

import app.codeg.android.core.designsystem.icon.Lucide
import app.codeg.android.core.designsystem.icon.ChevronDown
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import androidx.compose.ui.tooling.preview.Preview
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegTheme

@Composable
fun CodegCollapsible(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    header: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) = CollapsibleBody(expanded, onExpandedChange, header, modifier, false, content)

@Composable
fun CodegInstantCollapsible(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    header: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) = CollapsibleBody(expanded, onExpandedChange, header, modifier, true, content)

@Composable
private fun CollapsibleBody(
    expanded: Boolean, onExpandedChange: (Boolean) -> Unit,
    header: @Composable () -> Unit, modifier: Modifier, instant: Boolean,
    content: @Composable () -> Unit,
) {
    val rotation by animateFloatAsState(if (expanded) 180f else 0f, label = "collapsible-chevron")
    val description = stringResource(if (expanded) R.string.ds_misc_expanded else R.string.ds_misc_collapsed)
    Column(modifier) {
        Row(Modifier.fillMaxWidth().semantics { stateDescription = description }
            .clickable { onExpandedChange(!expanded) }, verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(1f)) { header() }
            Icon(Lucide.ChevronDown, null, Modifier.size(16.dp)
                .rotate(if (instant) { if (expanded) 180f else 0f } else rotation),
                tint = CodegTheme.colors.web.mutedForeground)
        }
        if (instant) { if (expanded) content() }
        else AnimatedVisibility(expanded) { Column { content() } }
    }
}
@Composable
internal fun T53CollapsibleGallery() {
    var expanded by remember { mutableStateOf(true) }
    Column {
        CodegCollapsible(expanded, { expanded = it }, { Text(stringResource(R.string.ds_misc_group), color = CodegTheme.colors.web.foreground) }) {
            Text(stringResource(R.string.ds_misc_details), color = CodegTheme.colors.web.foreground)
        }
        CodegInstantCollapsible(expanded, { expanded = it }, { Text(stringResource(R.string.ds_misc_instant), color = CodegTheme.colors.web.foreground) }) {
            Text(stringResource(R.string.ds_misc_details), color = CodegTheme.colors.web.foreground)
        }
    }
}
@Preview @Composable private fun CollapsibleLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T53CollapsibleGallery() } }
@Preview @Composable private fun CollapsibleDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T53CollapsibleGallery() } }

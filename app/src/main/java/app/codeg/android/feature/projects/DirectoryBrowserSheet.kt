package app.codeg.android.feature.projects

import app.codeg.android.core.designsystem.component.CodegFullScreenSheet
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegButton
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import app.codeg.android.core.designsystem.component.CodegIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.DirectoryEntry
import kotlinx.coroutines.launch

/** Full-screen server-filesystem browser used by Open Folder + Clone. Port of iOS `DirectoryBrowserView`. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryBrowserSheet(
    title: String,
    confirmLabel: String,
    loadHome: suspend () -> String,
    loadDirs: suspend (String) -> List<DirectoryEntry>,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val colors = CodegTheme.colors
    val scope = rememberCoroutineScope()
    var path by remember { mutableStateOf("") }
    var dirs by remember { mutableStateOf<List<DirectoryEntry>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) { path = loadHome() }
    LaunchedEffect(path) {
        if (path.isNotEmpty()) {
            loading = true
            dirs = loadDirs(path)
            loading = false
        }
    }

    CodegFullScreenSheet(
        title = title,
        onDismiss = onDismiss,
        actions = {
            CodegButton(onClick = { onSelect(path) }, enabled = path.isNotEmpty()) { Text(confirmLabel) }
            CodegIconButton(onClick = { scope.launch { path = loadHome() } }) {
                Icon(Icons.Rounded.Home, contentDescription = stringResource(R.string.dirbrowser_home), tint = colors.textSecondary)
            }
        },
    ) {
        Text(path, fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = colors.textSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp))
            Box(Modifier.fillMaxWidth()) {
                if (loading) {
                    Box(Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = colors.accent) }
                } else {
                    Column(Modifier.fillMaxWidth()) {
                        val parent = path.substringBeforeLast('/', "")
                        if (parent.isNotEmpty() && parent != path) {
                            BrowserRow("..", hasChildren = false, icon = Icons.Rounded.ArrowUpward) { path = parent }
                        }
                        dirs.forEach { entry ->
                            BrowserRow(entry.name, entry.hasChildren, Icons.Rounded.Folder) { path = entry.path }
                        }
                    }
                }
            }
    }
}

@Composable
private fun BrowserRow(name: String, hasChildren: Boolean, icon: ImageVector, onClick: () -> Unit) {
    val colors = CodegTheme.colors
    CodegListRow(
        title = name,
        onClick = onClick,
        leading = { Icon(icon, null, tint = colors.accent, modifier = Modifier.size(20.dp)) },
        trailing = {
            if (hasChildren) Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null, tint = colors.textSecondary, modifier = Modifier.size(18.dp))
        },
    )
}

package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegTheme

/** Compact web folder monogram. Supply a web token when overriding [color]. */
@Composable
fun CodegFolderBadge(name: String, modifier: Modifier = Modifier, color: Color? = null) {
    val web = CodegTheme.colors.web
    Box(modifier.size(14.dp).background(color ?: web.primary, RoundedCornerShape(4.dp))
        .semantics { contentDescription = name }, contentAlignment = Alignment.Center) {
        Text(dsMiscInitial(name), fontSize = 9.sp, lineHeight = 10.sp, color = web.primaryForeground, maxLines = 1)
    }
}
@Composable internal fun T53FolderBadgeGallery() { CodegFolderBadge("Codeg") }
@Preview @Composable private fun FolderBadgeLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T53FolderBadgeGallery() } }
@Preview @Composable private fun FolderBadgeDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T53FolderBadgeGallery() } }

package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegTheme

/** The caller supplies a loaded painter; no network loader is introduced. */
@Composable
fun CodegAvatar(
    fallbackText: String,
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    size: Dp = 32.dp,
) {
    val web = CodegTheme.colors.web
    Box(modifier.size(size).clip(CircleShape).background(web.muted)
        .border(1.dp, web.border, CircleShape).semantics { contentDescription = fallbackText },
        contentAlignment = Alignment.Center) {
        if (painter != null) Image(painter, null, Modifier.size(size), contentScale = ContentScale.Crop)
        else Text(dsMiscInitial(fallbackText), color = web.mutedForeground,
            fontSize = if (size <= 24.dp) 12.sp else 14.sp)
    }
}

internal fun dsMiscInitial(text: String): String {
    val trimmed = text.trim()
    if (trimmed.isEmpty()) return ""
    return String(Character.toChars(trimmed.codePointAt(0))).uppercase(java.util.Locale.ROOT)
}

@Composable
internal fun T53AvatarGallery() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        CodegAvatar("Codeg", size = 24.dp)
        CodegAvatar("中文")
        CodegAvatar("", size = 40.dp)
    }
}
@Preview @Composable private fun AvatarLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T53AvatarGallery() } }
@Preview @Composable private fun AvatarDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T53AvatarGallery() } }

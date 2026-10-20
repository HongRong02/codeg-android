package app.codeg.android.core.designsystem.icon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegTheme

/** Matches the web button's default size-4; callers own accessible descriptions. */
@Composable
fun CodegIcon(
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        tint = tint,
    )
}

/** Developer gallery: labels are upstream symbol identifiers, not interface copy. */
@Composable
internal fun T51Gallery() {
    val icons = remember { lucideGallery }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(112.dp),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(icons, key = { it.first }) { (name, vector) ->
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                CodegIcon(vector(), contentDescription = null)
                Text(name)
            }
        }
    }
}

@Preview(name = "Lucide Light", showBackground = true, widthDp = 420, heightDp = 800)
@Composable
private fun LucideLightPreview() {
    CodegTheme(themeMode = ThemeMode.LIGHT) {
        Surface { T51Gallery() }
    }
}

@Preview(name = "Lucide Dark", showBackground = true, widthDp = 420, heightDp = 800)
@Composable
private fun LucideDarkPreview() {
    CodegTheme(themeMode = ThemeMode.DARK) {
        Surface { T51Gallery() }
    }
}

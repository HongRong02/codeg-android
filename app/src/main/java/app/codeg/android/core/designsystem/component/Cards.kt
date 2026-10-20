package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.datastore.ThemeMode
import androidx.compose.ui.res.stringResource
import app.codeg.android.R

/** Web card surface, with section spacing and horizontal padding owned by its slots. */
@Composable
fun CodegCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    ProvideTextStyle(TextStyle(color = CodegTheme.colors.web.cardForeground, fontSize = 14.sp)) {
        Column(
            modifier.shadow(1.dp, CodegShapes.small).webSurface(CodegShapes.small)
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            content = content,
        )
    }
}

@Composable
fun CodegCardHeader(
    title: String,
    description: String? = null,
    action: @Composable RowScope.() -> Unit = {},
) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, color = CodegTheme.colors.web.cardForeground, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            description?.let { Text(it, color = CodegTheme.colors.web.mutedForeground, fontSize = 14.sp) }
        }
        action()
    }
}

@Composable
fun CodegCardContent(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier.padding(horizontal = 24.dp), content = content)
}

@Composable
fun CodegCardFooter(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), content = content)
}

@Composable
internal fun T44CardsGallery() {
    CodegCard {
        CodegCardHeader(stringResource(R.string.ds_dialogs_title), stringResource(R.string.ds_dialogs_description)) {
            Text(stringResource(R.string.ds_dialogs_target))
        }
        CodegCardContent { Text(stringResource(R.string.ds_dialogs_message)) }
        CodegCardFooter { Text(stringResource(R.string.ds_dialogs_confirm)) }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardsLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T44CardsGallery() } }

@Preview(showBackground = true)
@Composable
private fun CardsDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T44CardsGallery() } }

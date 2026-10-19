package app.codeg.android.feature.sessiondetail.rendering

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.core.designsystem.markdown.MarkdownContent
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.LocalCodegColors

/**
 * A collapsible "Reasoning" disclosure for an agent's thinking text, after the
 * web `Reasoning` component: a borderless muted trigger row (brain icon, label,
 * chevron that turns from right to down) and the thinking text in
 * `--muted-foreground` below it. Collapsed by default once finalized.
 */
@Composable
fun ReasoningBlock(text: String, modifier: Modifier = Modifier, initiallyExpanded: Boolean = false) {
    val colors = CodegTheme.colors
    var expanded by remember(text) { mutableStateOf(initiallyExpanded) }
    val chevron by animateFloatAsState(if (expanded) 90f else 0f, label = "reasoning-chevron")

    Column(modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(Icons.Rounded.Psychology, null, tint = colors.textSecondary, modifier = Modifier.size(16.dp))
            Text("Reasoning", fontSize = 14.sp, color = colors.textSecondary)
            Icon(
                Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = colors.textSecondary,
                modifier = Modifier.size(16.dp).rotate(chevron),
            )
        }
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(tween(220)) + fadeIn(tween(220)),
            exit = shrinkVertically(tween(180)) + fadeOut(tween(120)),
        ) {
            // The web sets the whole reasoning body in --muted-foreground.
            CompositionLocalProvider(LocalCodegColors provides colors.copy(textPrimary = colors.textSecondary)) {
                Column(Modifier.fillMaxWidth().padding(top = 12.dp)) {
                    MarkdownContent(text)
                }
            }
        }
    }
}

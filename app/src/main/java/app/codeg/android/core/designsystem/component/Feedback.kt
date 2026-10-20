package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.withOpacity

import androidx.compose.animation.core.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics

/** Vertical separators need a bounded height from the caller. */
@Composable
fun CodegSeparator(modifier: Modifier = Modifier, vertical: Boolean = false) {
    Box(modifier.then(if (vertical) Modifier.fillMaxHeight().width(1.dp)
        else Modifier.fillMaxWidth().height(1.dp)).background(CodegTheme.colors.web.border))
}

/** Caller supplies the placeholder size. */
@Composable
fun CodegSkeleton(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "skeleton")
    val opacity by transition.animateFloat(1f, 0.5f,
        infiniteRepeatable(tween(1000), RepeatMode.Reverse), label = "skeleton-opacity")
    Box(modifier.alpha(opacity).clip(CodegShapes.small).background(CodegTheme.colors.web.accent))
}

internal fun normalizedProgress(progress: Float): Float =
    if (progress.isNaN()) 0f else progress.coerceIn(0f, 1f)

/** Determinate progress in the inclusive 0..1 range. */
@Composable
fun CodegProgress(progress: Float, modifier: Modifier = Modifier) {
    val value = normalizedProgress(progress)
    val web = CodegTheme.colors.web
    Box(modifier.fillMaxWidth().height(8.dp).clip(CircleShape)
        .background(web.primary.withOpacity(0.2f))
        .semantics { progressBarRangeInfo = ProgressBarRangeInfo(value, 0f..1f) }) {
        Box(Modifier.fillMaxWidth(value).fillMaxHeight().background(web.primary))
    }
}

@Composable
internal fun T46FeedbackGallery() {
    Column(Modifier.background(CodegTheme.colors.web.background).padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CodegSeparator()
        Row(Modifier.height(24.dp)) { CodegSeparator(vertical = true) }
        CodegSkeleton(Modifier.fillMaxWidth().height(24.dp))
        CodegProgress(0f)
        CodegProgress(0.5f)
        CodegProgress(1f)
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedbackLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T46FeedbackGallery() } }

@Preview(showBackground = true)
@Composable
private fun FeedbackDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T46FeedbackGallery() } }

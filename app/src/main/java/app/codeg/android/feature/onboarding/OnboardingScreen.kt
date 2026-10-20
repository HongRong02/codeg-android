package app.codeg.android.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegTheme

/** Shown when no server is configured (iOS `OnboardingView`). */
@Composable
fun OnboardingScreen(
    onAddServer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = CodegTheme.colors
    Box(modifier.fillMaxSize().safeDrawingPadding(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .widthIn(max = 384.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                Icons.Rounded.Code,
                contentDescription = null,
                tint = colors.accent,
                modifier = Modifier.size(56.dp),
            )
            Text(
                stringResource(R.string.onboarding_title),
                style = MaterialTheme.typography.titleLarge,
                color = colors.textPrimary,
                modifier = Modifier.padding(top = 16.dp),
            )
            Text(
                stringResource(R.string.onboarding_tagline),
                style = MaterialTheme.typography.bodyMedium,
                color = colors.web.mutedForeground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp).widthIn(max = 320.dp),
            )

            CodegCard(Modifier.padding(vertical = 24.dp).fillMaxWidth()) {
                CodegCardContent {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        FeatureRow(Icons.Rounded.Visibility, stringResource(R.string.onboarding_feature_watch))
                        FeatureRow(Icons.Rounded.Bolt, stringResource(R.string.onboarding_feature_steer))
                        FeatureRow(Icons.Rounded.PlayArrow, stringResource(R.string.onboarding_feature_start))
                    }
                }
            }

            PrimaryButton(
                text = stringResource(R.string.onboarding_add_server),
                onClick = onAddServer,
                modifier = Modifier.widthIn(max = 360.dp),
            )
            Text(
                stringResource(R.string.onboarding_footnote),
                style = MaterialTheme.typography.bodySmall,
                color = colors.web.mutedForeground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp).widthIn(max = 320.dp),
            )
        }
    }
}

@Composable
private fun FeatureRow(icon: ImageVector, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Icon(icon, contentDescription = null, tint = CodegTheme.colors.accent, modifier = Modifier.size(22.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium, color = CodegTheme.colors.textPrimary)
    }
}

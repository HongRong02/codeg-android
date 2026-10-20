package app.codeg.android.shell.download

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import kotlinx.coroutines.delay

internal enum class DownloadStatus { Loading, Success, Error }

// Identity is intentional: two identical successive results must each get a fresh timeout.
internal class DownloadNotice(
    val status: DownloadStatus,
    val detail: String,
    val uri: Uri? = null,
    val mime: String? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DownloadToast(notice: DownloadNotice, onDismiss: () -> Unit, onOpen: () -> Unit) {
    key(notice) {
        LaunchedEffect(notice) {
            delay(4_000)
            onDismiss()
        }
        val swipe = rememberSwipeToDismissBoxState()
        LaunchedEffect(swipe.currentValue) {
            if (swipe.currentValue != SwipeToDismissBoxValue.Settled) onDismiss()
        }
        val colors = MaterialTheme.colorScheme
        val failed = notice.status == DownloadStatus.Error
        val foreground = if (failed) colors.onErrorContainer else colors.onSurface
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            SwipeToDismissBox(
                state = swipe,
                modifier = Modifier.navigationBarsPadding().padding(16.dp).widthIn(max = 560.dp).fillMaxWidth(),
                backgroundContent = {},
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (failed) colors.errorContainer else colors.surface,
                    contentColor = foreground,
                    border = BorderStroke(1.dp, if (failed) colors.error else colors.outline),
                    shadowElevation = 6.dp,
                    modifier = Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite },
                ) {
                    Row(
                        Modifier.padding(start = 16.dp, end = 8.dp, top = 12.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        when (notice.status) {
                            DownloadStatus.Loading -> CircularProgressIndicator(
                                modifier = Modifier.size(20.dp), color = colors.primary, strokeWidth = 2.dp,
                            )
                            DownloadStatus.Success -> Icon(Icons.Outlined.CheckCircle, null, Modifier.size(20.dp))
                            DownloadStatus.Error -> Icon(Icons.Outlined.ErrorOutline, null, Modifier.size(20.dp), tint = colors.error)
                        }
                        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                stringResource(when (notice.status) {
                                    DownloadStatus.Loading -> R.string.shell_download_started
                                    DownloadStatus.Success -> R.string.shell_download_saved
                                    DownloadStatus.Error -> R.string.shell_download_failed
                                }),
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(
                                notice.detail,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (failed) colors.onErrorContainer else colors.onSurfaceVariant,
                                maxLines = 2, overflow = TextOverflow.Ellipsis,
                            )
                        }
                        if (notice.status == DownloadStatus.Success && notice.uri != null) {
                            TextButton(onClick = onOpen) { Text(stringResource(R.string.shell_download_open)) }
                        }
                    }
                }
            }
        }
    }
}

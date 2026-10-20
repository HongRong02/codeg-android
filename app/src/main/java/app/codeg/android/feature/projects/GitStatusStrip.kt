package app.codeg.android.feature.projects

import app.codeg.android.core.designsystem.theme.CodegShapes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.component.webSurface
import androidx.compose.runtime.*
import app.codeg.android.core.designsystem.component.CodegSpinner
import app.codeg.android.core.designsystem.component.LocalCodegToaster
import app.codeg.android.core.designsystem.component.ToastId
import app.codeg.android.core.designsystem.component.ToastType

/** Persistent busy status; completed operations are delivered through the app toaster. */
@Composable
internal fun GitStatusStrip(
    busy: Boolean,
    busyTitle: String?,
    banner: GitBanner?,
    onDismissBanner: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val toaster = LocalCodegToaster.current
    val working = busyTitle ?: stringResource(R.string.git_working)
    var loadingToast by remember { mutableStateOf<ToastId?>(null) }
    LaunchedEffect(busy, banner) {
        val feedback = banner
        if (feedback != null) {
            val type = when (feedback.kind) {
                GitBannerKind.SUCCESS -> ToastType.Success
                GitBannerKind.WARNING -> ToastType.Warning
                GitBannerKind.ERROR -> ToastType.Error
            }
            val id = loadingToast
            if (id != null) toaster.update(id, type, feedback.message)
            else when (feedback.kind) {
                GitBannerKind.SUCCESS -> toaster.success(feedback.message)
                GitBannerKind.WARNING -> toaster.warning(feedback.message)
                GitBannerKind.ERROR -> toaster.error(feedback.message)
            }
            loadingToast = null
            onDismissBanner()
        } else if (busy && loadingToast == null) {
            loadingToast = toaster.loading(working)
        } else if (!busy) {
            loadingToast?.let(toaster::dismiss)
            loadingToast = null
        }
    }
    DisposableEffect(toaster) {
        onDispose { loadingToast?.let(toaster::dismiss) }
    }
    val colors = CodegTheme.colors
    if (busy) Row(
        modifier.fillMaxWidth().webSurface(CodegShapes.small).padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        CodegSpinner()
        Text(working, fontSize = 12.sp, color = colors.textSecondary)
    }
}

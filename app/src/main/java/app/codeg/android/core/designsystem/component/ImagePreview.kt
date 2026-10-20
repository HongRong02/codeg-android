package app.codeg.android.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegTheme

/** A loaded image and its accessible description. Caller owns decoding/downsampling. */
data class CodegPreviewImage(val painter: Painter, val description: String)

internal fun boundedPreviewScale(scale: Float): Float = if (scale.isFinite()) scale.coerceIn(1f, 5f) else 1f
internal fun boundedPreviewIndex(index: Int, count: Int): Int = if (count <= 0) 0 else index.coerceIn(0, count - 1)
internal fun fittedPreviewSize(width: Float, height: Float, viewportWidth: Float, viewportHeight: Float): Pair<Float, Float> {
    if (!width.isFinite() || !height.isFinite() || width <= 0f || height <= 0f) {
        return viewportWidth to viewportHeight
    }
    val ratio = minOf(viewportWidth / width, viewportHeight / height)
    return width * ratio to height * ratio
}
internal fun boundedPreviewPan(pan: Float, extent: Float, scale: Float, imageExtent: Float = extent): Float {
    if (!pan.isFinite() || !extent.isFinite() || !imageExtent.isFinite() || extent <= 0f) return 0f
    val limit = ((imageExtent * boundedPreviewScale(scale) - extent) / 2f).coerceAtLeast(0f)
    return pan.coerceIn(-limit, limit)
}

@Composable
fun CodegImagePreviewDialog(images: List<CodegPreviewImage>, initialIndex: Int = 0, onDismiss: () -> Unit) {
    var index by remember(images, initialIndex) { mutableStateOf(boundedPreviewIndex(initialIndex, images.size)) }
    val current = boundedPreviewIndex(index, images.size)
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)) {
        ImagePreviewBody(images, current, { index = it }, onDismiss)
    }
}

@Composable
private fun ImagePreviewBody(images: List<CodegPreviewImage>, index: Int, onIndexChange: (Int) -> Unit, onDismiss: () -> Unit) {
    val web = CodegTheme.colors.web
    val arrowRotation = if (LocalLayoutDirection.current == LayoutDirection.Rtl) 180f else 0f
    val image = images.getOrNull(index)
    var scale by remember(image, index) { mutableStateOf(1f) }
    var offset by remember(image, index) { mutableStateOf(Offset.Zero) }
    var viewport by remember { mutableStateOf(IntSize.Zero) }
    val intrinsic = image?.painter?.intrinsicSize?.takeUnless { it == androidx.compose.ui.geometry.Size.Unspecified }
    val fitted = fittedPreviewSize(intrinsic?.width ?: Float.NaN, intrinsic?.height ?: Float.NaN,
        viewport.width.toFloat(), viewport.height.toFloat())
    Box(Modifier.fillMaxSize().background(web.background).safeDrawingPadding()) {
        if (image == null) Text(stringResource(R.string.ds_misc_no_images), color = web.foreground, modifier = Modifier.align(Alignment.Center))
        else Box(Modifier.fillMaxSize().padding(top = 64.dp, bottom = 64.dp).clipToBounds()
            .onSizeChanged { viewport = it }
            .pointerInput(image, index, viewport, fitted) {
                detectTapGestures(onDoubleTap = {
                    scale = if (scale > 1f) 1f else 2f
                    offset = Offset.Zero
                })
            }
            .pointerInput(image, index, viewport, fitted) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = boundedPreviewScale(scale * zoom)
                    offset = Offset(
                        boundedPreviewPan(offset.x + pan.x, viewport.width.toFloat(), scale, fitted.first),
                        boundedPreviewPan(offset.y + pan.y, viewport.height.toFloat(), scale, fitted.second),
                    )
                }
            }, contentAlignment = Alignment.Center) {
            Image(image.painter, image.description, Modifier.fillMaxSize().graphicsLayer {
                scaleX = scale; scaleY = scale
                translationX = boundedPreviewPan(offset.x, viewport.width.toFloat(), scale, fitted.first)
                translationY = boundedPreviewPan(offset.y, viewport.height.toFloat(), scale, fitted.second)
            }, contentScale = ContentScale.Fit)
        }
        // TODO(T51): Lucide.X
        IconButton(onDismiss, Modifier.align(Alignment.TopEnd).padding(16.dp)) {
            Icon(Icons.Default.Close, stringResource(R.string.ds_misc_close), tint = web.foreground)
        }
        if (images.isNotEmpty()) Row(Modifier.align(Alignment.BottomCenter).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // TODO(T51): Lucide.ChevronLeft
            IconButton({ onIndexChange(index - 1) }, enabled = index > 0) {
                Icon(Icons.Default.ChevronLeft, stringResource(R.string.ds_misc_previous), Modifier.rotate(arrowRotation), tint = if (index > 0) web.foreground else web.mutedForeground)
            }
            Text(stringResource(R.string.ds_misc_image_index, index + 1, images.size), color = web.foreground)
            // TODO(T51): Lucide.ChevronRight
            IconButton({ onIndexChange(index + 1) }, enabled = index < images.lastIndex) {
                Icon(Icons.Default.ChevronRight, stringResource(R.string.ds_misc_next), Modifier.rotate(arrowRotation), tint = if (index < images.lastIndex) web.foreground else web.mutedForeground)
            }
        }
    }
}
@Composable internal fun T53ImagePreviewGallery() {
    val web = CodegTheme.colors.web
    val description = stringResource(R.string.ds_misc_image)
    var index by remember { mutableStateOf(0) }
    ImagePreviewBody(listOf(CodegPreviewImage(ColorPainter(web.muted), description), CodegPreviewImage(ColorPainter(web.accent), description)), index, { index = it }, {})
}
@Preview @Composable private fun ImagePreviewLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { T53ImagePreviewGallery() } }
@Preview @Composable private fun ImagePreviewDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { T53ImagePreviewGallery() } }

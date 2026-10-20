package app.codeg.android.core.designsystem.component

import app.codeg.android.core.designsystem.icon.LoaderCircle
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.ui.draw.rotate
import app.codeg.android.core.designsystem.icon.Lucide
import app.codeg.android.core.designsystem.icon.CircleCheck
import app.codeg.android.core.designsystem.icon.CircleX
import app.codeg.android.core.designsystem.icon.Info
import app.codeg.android.core.designsystem.icon.TriangleAlert
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.theme.CodegTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@JvmInline
value class ToastId(val value: Long)
enum class ToastType { Success, Error, Info, Warning, Loading }
data class ToastAction(val label: String, val onClick: () -> Unit)

internal data class ToastEntry(
    val id: ToastId,
    val type: ToastType,
    val title: String,
    val description: String? = null,
    val action: ToastAction? = null,
    val remainingMs: Long = 4000,
)

/** Pure Kotlin queue. Sonner defaults: 4 seconds, newest first, 3 visible.
 * Hidden entries retain their reading budget; loading has no deadline.
 * Callers serialize through CodegToaster; elapsed time is supplied by the host.
 */
internal class ToastQueue {
    private var nextId = 0L
    private var entries = emptyList<ToastEntry>()
    val visible: List<ToastEntry> get() = entries.take(3)

    fun add(type: ToastType, title: String, description: String? = null, action: ToastAction? = null): ToastId {
        val id = ToastId(++nextId)
        entries = listOf(ToastEntry(id, type, title, description, action)) + entries
        return id
    }

    fun update(id: ToastId, type: ToastType, title: String, description: String?) {
        entries = entries.map {
            if (it.id == id) it.copy(type = type, title = title, description = description, remainingMs = 4000) else it
        }
    }

    fun dismiss(id: ToastId) { entries = entries.filterNot { it.id == id } }

    fun advance(elapsedMs: Long) {
        if (elapsedMs <= 0) return
        val active = visible.map { it.id }.toSet()
        entries = entries.map {
            if (it.id in active && it.type != ToastType.Loading) it.copy(remainingMs = it.remainingMs - elapsedMs) else it
        }.filter { it.type == ToastType.Loading || it.remainingMs > 0 }
    }
}

class CodegToaster internal constructor(private val silent: Boolean) {
    constructor() : this(false)
    private val queue = ToastQueue()
    private val mutableEntries = MutableStateFlow<List<ToastEntry>>(emptyList())
    internal val entries = mutableEntries.asStateFlow()

    @Synchronized
    private fun add(type: ToastType, title: String, description: String?, action: ToastAction?): ToastId {
        if (silent) {
            Log.w("CodegToaster", "Toast ignored: CodegToasterHost is not installed ($type)")
            return ToastId(-1)
        }
        return queue.add(type, title, description, action).also { publish() }
    }
    fun success(title: String, description: String? = null, action: ToastAction? = null): ToastId = add(ToastType.Success, title, description, action)
    fun error(title: String, description: String? = null, action: ToastAction? = null): ToastId = add(ToastType.Error, title, description, action)
    fun info(title: String, description: String? = null, action: ToastAction? = null): ToastId = add(ToastType.Info, title, description, action)
    fun warning(title: String, description: String? = null, action: ToastAction? = null): ToastId = add(ToastType.Warning, title, description, action)
    fun loading(title: String): ToastId = add(ToastType.Loading, title, null, null)

    @Synchronized
    fun update(id: ToastId, type: ToastType, title: String, description: String? = null) {
        queue.update(id, type, title, description)
        publish()
    }
    @Synchronized
    fun dismiss(id: ToastId) { queue.dismiss(id); publish() }
    @Synchronized
    internal fun advance(elapsedMs: Long) { queue.advance(elapsedMs); publish() }
    private fun publish() { mutableEntries.value = queue.visible.map { it.copy(remainingMs = 4000) } }
}

val LocalCodegToaster = staticCompositionLocalOf { CodegToaster(silent = true) }

@Composable
fun CodegToasterHost(
    toaster: CodegToaster = remember { CodegToaster() },
    content: @Composable () -> Unit,
) {
    val entries by toaster.entries.collectAsState()
    // Retain dismissed cards briefly so their exit animation can finish.
    val rendered = remember(toaster) { mutableStateListOf<ToastEntry>() }
    LaunchedEffect(entries.map { it.id }) {
        entries.forEach { entry ->
            val index = rendered.indexOfFirst { it.id == entry.id }
            if (index < 0) rendered.add(entry) else rendered[index] = entry
        }
        delay(200)
        rendered.removeAll { old -> entries.none { it.id == old.id } }
    }
    LaunchedEffect(toaster) {
        var previous = System.nanoTime()
        while (true) {
            delay(50)
            val now = System.nanoTime()
            toaster.advance((now - previous) / 1_000_000)
            previous = now
        }
    }
    CompositionLocalProvider(LocalCodegToaster provides toaster) {
        Box(Modifier.fillMaxSize()) {
            content()
            Column(
                Modifier.align(Alignment.BottomEnd).windowInsetsPadding(WindowInsets.safeDrawing)
                    .imePadding().padding(16.dp).widthIn(max = 356.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Oldest above newest: sonner's bottom-right origin.
                rendered.sortedBy { it.id.value }.forEach { entry ->
                    key(entry.id) {
                        var entered by remember { mutableStateOf(false) }
                        LaunchedEffect(Unit) { entered = true }
                        AnimatedVisibility(
                            visible = entered && entries.any { it.id == entry.id },
                            enter = fadeIn(tween(180)) + slideInVertically { it },
                            exit = fadeOut(tween(180)) + slideOutVertically { it },
                        ) { ToastCard(entries.firstOrNull { it.id == entry.id } ?: entry) { toaster.dismiss(entry.id) } }
                    }
                }
            }
        }
    }
}

@Composable
private fun ToastCard(entry: ToastEntry, dismiss: () -> Unit) {
    val web = CodegTheme.colors.web
    val threshold = with(LocalDensity.current) { 48.dp.toPx() }
    var dragOffset by remember(entry.id) { mutableStateOf(0f) }
    // rounded-lg is the web base radius (10dp); CodegShapes starts at md (8dp).
    Surface(
        modifier = Modifier.fillMaxWidth().graphicsLayer { translationX = dragOffset }.semantics { liveRegion = LiveRegionMode.Polite }
            .pointerInput(entry.id) {
                var drag = 0f
                detectHorizontalDragGestures(
                    onDragStart = { drag = 0f; dragOffset = 0f },
                    onHorizontalDrag = { change, amount -> change.consume(); drag += amount; dragOffset = drag },
                    onDragEnd = { if (kotlin.math.abs(drag) >= threshold) dismiss() else dragOffset = 0f },
                    onDragCancel = { drag = 0f; dragOffset = 0f },
                )
            },
        shape = RoundedCornerShape(10.dp), color = web.popover,
        contentColor = web.popoverForeground, border = BorderStroke(1.dp, web.border), shadowElevation = 8.dp,
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            // Sonner without richColors uses the normal foreground for all icons.
            if (entry.type == ToastType.Loading) {
                val rotation by rememberInfiniteTransition(label = "loadingIcon").animateFloat(
                    initialValue = 0f, targetValue = 360f,
                    animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing), RepeatMode.Restart),
                    label = "loadingRotation",
                )
                Icon(Lucide.LoaderCircle, null, Modifier.size(18.dp).rotate(rotation), tint = web.popoverForeground)
            } else {
                val icon = when (entry.type) {
                    ToastType.Success -> Lucide.CircleCheck
                    ToastType.Error -> Lucide.CircleX
                    ToastType.Warning -> Lucide.TriangleAlert
                    ToastType.Info, ToastType.Loading -> Lucide.Info
                }
                val label = stringResource(when (entry.type) {
                    ToastType.Success -> R.string.ds_toasts_success
                    ToastType.Error -> R.string.ds_toasts_error
                    ToastType.Warning -> R.string.ds_toasts_warning
                    ToastType.Info, ToastType.Loading -> R.string.ds_toasts_info
                })
                Icon(icon, label, Modifier.size(20.dp), tint = web.popoverForeground)
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(entry.title, fontSize = 14.sp, lineHeight = 20.sp)
                entry.description?.let { Text(it, color = web.mutedForeground, fontSize = 14.sp, lineHeight = 20.sp) }
            }
            entry.action?.let { action ->
                TextButton(onClick = { dismiss(); action.onClick() }) { Text(action.label, color = web.popoverForeground, fontSize = 14.sp) }
            }
        }
    }
}

@Composable
internal fun T52Gallery() {
    val toaster = remember { CodegToaster() }
    val success = stringResource(R.string.ds_toasts_success)
    val error = stringResource(R.string.ds_toasts_error)
    val info = stringResource(R.string.ds_toasts_info)
    val warning = stringResource(R.string.ds_toasts_warning)
    val loading = stringResource(R.string.ds_toasts_loading)
    val description = stringResource(R.string.ds_toasts_description)
    val action = stringResource(R.string.ds_toasts_action)
    val finish = stringResource(R.string.ds_toasts_finish)
    var pending by remember { mutableStateOf<ToastId?>(null) }
    CodegToasterHost(toaster) {
        Column {
            TextButton(onClick = { toaster.success(success) }) { Text(success) }
            TextButton(onClick = { toaster.error(error, description) }) { Text(error) }
            TextButton(onClick = { toaster.info(info, description, ToastAction(action) { toaster.success(success) }) }) { Text(info) }
            TextButton(onClick = { toaster.warning(warning) }) { Text(warning) }
            TextButton(onClick = { pending = toaster.loading(loading) }) { Text(loading) }
            TextButton(onClick = { pending?.let { toaster.update(it, ToastType.Success, success) } }) { Text(finish) }
        }
    }
}

@Composable
private fun ToastPreviewContent() {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ToastType.values().forEach { type ->
            val title = stringResource(when (type) {
                ToastType.Success -> R.string.ds_toasts_success
                ToastType.Error -> R.string.ds_toasts_error
                ToastType.Info -> R.string.ds_toasts_info
                ToastType.Warning -> R.string.ds_toasts_warning
                ToastType.Loading -> R.string.ds_toasts_loading
            })
            ToastCard(ToastEntry(ToastId(type.ordinal.toLong()), type, title, stringResource(R.string.ds_toasts_description))) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ToastsLightPreview() { CodegTheme(themeMode = ThemeMode.LIGHT) { ToastPreviewContent() } }
@Preview(showBackground = true)
@Composable
private fun ToastsDarkPreview() { CodegTheme(themeMode = ThemeMode.DARK) { ToastPreviewContent() } }

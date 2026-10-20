package app.codeg.android.feature.projects

import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegSectionHeader
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.EmptyState
import app.codeg.android.core.designsystem.component.InlineError
import app.codeg.android.core.designsystem.component.LoadingView
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.component.FileViewer
import app.codeg.android.core.model.FileViewerContent
import app.codeg.android.core.model.isPreviewImage
import app.codeg.android.core.model.previewText
import kotlinx.coroutines.CancellationException
import app.codeg.android.core.model.DirectoryItem
import app.codeg.android.core.network.CodegClient
import app.codeg.android.core.network.ApiError
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegImagePreviewDialog
import app.codeg.android.core.designsystem.component.CodegPreviewImage
import app.codeg.android.core.designsystem.icon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Recursive file browser with an internal directory stack + file preview. */
@Composable
fun FolderFilesView(client: CodegClient, rootPath: String) {
    val colors = CodegTheme.colors
    var stack by remember { mutableStateOf(listOf(rootPath)) }
    var previewPath by remember { mutableStateOf<DirectoryItem?>(null) }

    var unavailablePaths by remember(rootPath) { mutableStateOf(setOf<String>()) }

    BackHandler(enabled = previewPath != null || stack.size > 1) {
        if (previewPath != null) previewPath = null else stack = stack.dropLast(1)
    }

    val preview = previewPath
    if (preview != null) {
        FilePreview(client, rootPath, preview.path, size = preview.size, onUnsupported = { unavailablePaths = unavailablePaths + preview.path; previewPath = null })
        return
    }

    val dir = stack.last()
    var result by remember(dir) { mutableStateOf<Result<List<DirectoryItem>>?>(null) }
    LaunchedEffect(dir) { result = runCatching { client.listDirectoryWithFiles(dir) } }

    val r = result
    when {
        r == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { LoadingView(message = stringResource(R.string.common_loading)) }
        r.isFailure -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            InlineError(Lucide.File, stringResource(R.string.files_load_failed), r.exceptionOrNull()?.message ?: stringResource(R.string.files_error), onRetry = { result = null })
        }
        else -> {
            val items = sortItems(r.getOrDefault(emptyList())).filter { it.path !in unavailablePaths }
            LazyColumn(Modifier.fillMaxSize(), contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp)) {
                if (stack.size > 1) {
                    item("up") {
                        CodegListRow(title = "..", onClick = { stack = stack.dropLast(1) },
                            leading = { Icon(Lucide.ArrowUp, null, tint = colors.textSecondary, modifier = Modifier.size(16.dp)) })
                    }
                }
                if (items.isEmpty()) {
                    item("empty") { Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) { EmptyState(Lucide.Folder, stringResource(R.string.files_empty_folder), stringResource(R.string.files_empty_folder_message)) } }
                }
                items(items, key = { it.path }) { item ->
                    FileRow(item) {
                        if (item.isDir) stack = stack + item.path else previewPath = item
                    }
                }
            }
        }
    }
}

@Composable
private fun FileRow(item: DirectoryItem, onClick: () -> Unit) {
    val colors = CodegTheme.colors
    val sizeLabel = item.size?.takeIf { !item.isDir }?.let { formatBytes(it) }
    CodegListRow(
        title = item.name,
        onClick = onClick,
        leading = {
            Icon(if (item.isDir) Lucide.Folder else Lucide.File,
                null, tint = if (item.isDir) colors.accent else colors.textSecondary, modifier = Modifier.size(16.dp))
        },
        trailing = sizeLabel?.let { { Text(it, fontSize = 12.sp, color = colors.textSecondary) } },
    )
}

@Composable
internal fun FilePreview(client: CodegClient, rootPath: String, absPath: String, size: Long? = null, commit: String? = null, onUnsupported: () -> Unit = {}) {
    val colors = CodegTheme.colors
    val rel = if (absPath.startsWith("$rootPath/")) absPath.removePrefix("$rootPath/") else absPath
    var result by remember(rootPath, absPath, commit) { mutableStateOf<Result<FileViewerContent>?>(null) }
    var attempt by remember(rootPath, absPath, commit) { mutableStateOf(0) }
    LaunchedEffect(rootPath, absPath, commit, attempt) {
        result = try {
            Result.success(when {
                size != null && size > (if (isPreviewImage(rel)) 10_485_760 else 2_097_152) -> FileViewerContent.Unavailable(size, true)
                isPreviewImage(rel) -> {
                    val data = if (commit == null) client.readFileBase64(absPath) else client.gitShowFileBase64(rootPath, rel, commit).base64
                    FileViewerContent.Image(data, rel)
                }
                else -> previewText(if (commit == null) client.readFilePreview(rootPath, rel).content else client.gitShowFile(rootPath, rel, commit), rel)
            })
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            val message = e.message.orEmpty().lowercase()
            when {
                e is ApiError.Server && (e.status == 404 || e.code in setOf("unknown_command", "unknown_method") || message.contains("unknown command")) -> { onUnsupported(); null }
                message.contains("too large") || message.contains("exceeds") -> Result.success(FileViewerContent.Unavailable(size, true))
                message.contains("binary") || message.contains("utf-8") || message.contains("utf8") -> Result.success(FileViewerContent.Unavailable(size))
                else -> Result.failure(e)
            }
        }
    }
    Column(Modifier.fillMaxSize()) {
        CodegSectionHeader(title = if (commit == null) rel else "$rel @ ${commit.take(8)}")
        val r = result
        when {
            r == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { LoadingView(stringResource(R.string.common_loading)) }
            r.isFailure -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                InlineError(Lucide.File, stringResource(R.string.files_read_failed), r.exceptionOrNull()?.message ?: stringResource(R.string.files_error), onRetry = { result = null; attempt++ })
            }
            else -> r.getOrNull()?.let { content ->
                if (content is FileViewerContent.Image) ProjectImagePreview(content) else FileViewer(content)
            }
        }
    }
}

private fun sortItems(items: List<DirectoryItem>): List<DirectoryItem> =
    items.sortedWith(compareByDescending<DirectoryItem> { it.isDir }.thenBy { it.name.lowercase() })

private fun formatBytes(bytes: Long): String = when {
    bytes >= 1_073_741_824 -> String.format("%.1f GB", bytes / 1_073_741_824.0)
    bytes >= 1_048_576 -> String.format("%.1f MB", bytes / 1_048_576.0)
    bytes >= 1024 -> String.format("%.1f KB", bytes / 1024.0)
    else -> "$bytes B"
}

@Composable
private fun ProjectImagePreview(content: FileViewerContent.Image) {
    var open by remember(content) { mutableStateOf(true) }
    var decoding by remember(content) { mutableStateOf(true) }
    val bitmap by produceState<ImageBitmap?>(null, content) {
        value = withContext(Dispatchers.Default) {
            runCatching {
                val bytes = Base64.decode(content.base64.substringAfter(','), Base64.DEFAULT)
                val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
                val options = BitmapFactory.Options().apply {
                    while (bounds.outWidth / inSampleSize > 4096 || bounds.outHeight / inSampleSize > 4096) inSampleSize *= 2
                }
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)?.asImageBitmap()
            }.getOrNull()
        }
        decoding = false
    }
    val image = bitmap
    when {
        decoding -> LoadingView(stringResource(R.string.common_loading))
        image == null -> Text(stringResource(R.string.file_viewer_image_unavailable), color = CodegTheme.colors.web.mutedForeground)
        else -> {
            val images = remember(image, content.path) { listOf(CodegPreviewImage(BitmapPainter(image), content.path)) }
            CodegButton(onClick = { open = true }) { Text(stringResource(R.string.t56_preview_image)) }
            if (open) CodegImagePreviewDialog(images, onDismiss = { open = false })
        }
    }
}

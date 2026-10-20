package app.codeg.android.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class GitFileImage(
    @JsonNames("dataUrl", "data_url", "content", "data") val base64: String,
    val size: Long = 0,
)

sealed interface FileViewerContent {
    data class Text(val text: String, val path: String) : FileViewerContent
    data class Image(val base64: String, val path: String) : FileViewerContent
    data class Unavailable(val size: Long?, val tooLarge: Boolean = false) : FileViewerContent
}

fun isPreviewImage(path: String): Boolean = path.substringAfterLast('.', "").lowercase() in
    setOf("png", "jpg", "jpeg", "gif", "webp", "bmp", "ico")

fun previewText(text: String, path: String): FileViewerContent = when {
    text.indexOf('\u0000') >= 0 -> FileViewerContent.Unavailable(text.toByteArray().size.toLong())
    text.toByteArray().size > 2_097_152 -> FileViewerContent.Unavailable(text.toByteArray().size.toLong(), true)
    else -> FileViewerContent.Text(text, path)
}

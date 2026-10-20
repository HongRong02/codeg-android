package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable

@Serializable
data class ReadFileBase64Request(val path: String, val maxBytes: Long)

@Serializable
data class GitShowFileRequest(val path: String, val file: String, val refName: String, val maxBytes: Long? = null)

package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable

@Serializable
data class StashPathRequest(val path: String)
@Serializable
data class StashPushRequest(val path: String, val message: String? = null, val keepIndex: Boolean = false)
@Serializable
data class StashRefRequest(val path: String, val stashRef: String)

package app.codeg.android.core.model.wire
import kotlinx.serialization.Serializable
@Serializable data class MergeBranchRequest(val path: String, val branchName: String)
@Serializable data class MergeResetRequest(val path: String, val commit: String, val mode: String)
@Serializable data class MergeFileRequest(val path: String, val file: String)
@Serializable data class MergeResolveRequest(val path: String, val file: String, val content: String)
@Serializable data class MergeOperationRequest(val path: String, val operation: String)

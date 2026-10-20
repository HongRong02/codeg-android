package app.codeg.android.core.model.wire

import app.codeg.android.core.model.GitCredentials
import kotlinx.serialization.Serializable

@Serializable
data class BranchManageListBody(val path: String)
@Serializable
data class BranchDeleteBody(val path: String, val branchName: String, val force: Boolean)
@Serializable
data class BranchRemoteDeleteBody(val path: String, val remote: String, val branch: String, val credentials: GitCredentials? = null)
@Serializable
data class BranchUpdateBody(val path: String, val branch: String, val isRemote: Boolean, val credentials: GitCredentials? = null)
@Serializable
data class BranchCompareBody(val path: String, val branch: String, val file: String? = null)

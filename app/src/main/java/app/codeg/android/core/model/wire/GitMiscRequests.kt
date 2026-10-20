package app.codeg.android.core.model.wire

import app.codeg.android.core.model.GitCredentials
import kotlinx.serialization.Serializable

@Serializable
data class GitMiscRemoteRequest(val path: String, val name: String, val url: String? = null, val credentials: GitCredentials? = null)
@Serializable
data class GitMiscWorktreeAddRequest(val path: String, val branchName: String, val worktreePath: String, val base: String? = null)
@Serializable
data class GitMiscWorktreeRemoveRequest(val path: String, val branchName: String, val sourceFolderId: Int, val deleteBranch: Boolean = false, val force: Boolean = false)

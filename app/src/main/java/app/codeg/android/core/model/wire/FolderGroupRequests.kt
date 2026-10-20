package app.codeg.android.core.model.wire

import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderGroupBody(val name: String)
@Serializable
data class UpdateFolderGroupBody(val groupId: Int, val name: String)
@Serializable
data class FolderGroupIdBody(val groupId: Int)
@Serializable
data class SetFolderGroupBody(val folderId: Int, val groupId: Int?)
@Serializable
data class UpdateFolderAliasBody(val folderId: Int, val alias: String?)
@Serializable
data class UpdateFolderColorBody(val folderId: Int, val color: String)

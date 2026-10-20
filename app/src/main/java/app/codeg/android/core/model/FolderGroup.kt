package app.codeg.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class FolderGroup(val id: Int, val name: String, val color: String = "inherit", val sortOrder: Int = 0)

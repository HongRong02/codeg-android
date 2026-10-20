package app.codeg.android.feature.projects.groups

import app.codeg.android.core.model.FolderDetail
import app.codeg.android.core.model.FolderGroup

data class FolderEntry(val folder: FolderDetail? = null, val group: FolderGroup? = null)

fun folderLayout(folders: List<FolderDetail>, groups: List<FolderGroup>): List<FolderEntry> {
    val ids = groups.map { it.id }.toSet()
    return (groups.map { FolderEntry(group = it) } +
        folders.filter { it.groupId !in ids }.map { FolderEntry(folder = it) })
        .sortedWith(compareBy<FolderEntry> { it.group?.sortOrder ?: it.folder!!.sortOrder }
            .thenBy { if (it.group != null) 0 else 1 }
            .thenBy { it.group?.id ?: it.folder!!.id })
}

fun groupMembers(folders: List<FolderDetail>, id: Int): List<FolderDetail> =
    folders.filter { it.groupId == id }.sortedWith(compareBy({ it.sortOrder }, { it.id }))

fun folderDisplayName(folder: FolderDetail): String =
    folder.alias?.takeIf { it.isNotBlank() }?.let { "$it [${folder.name}]" } ?: folder.name

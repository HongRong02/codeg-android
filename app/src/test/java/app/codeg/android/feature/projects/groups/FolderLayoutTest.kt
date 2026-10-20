package app.codeg.android.feature.projects.groups

import app.codeg.android.core.model.FolderDetail
import app.codeg.android.core.model.FolderGroup
import app.codeg.android.core.model.wire.SetFolderGroupBody
import app.codeg.android.core.model.wire.UpdateFolderAliasBody
import app.codeg.android.core.network.CodegJson
import kotlinx.serialization.encodeToString
import org.junit.Assert.*
import org.junit.Test

class FolderLayoutTest {
    private fun folder(id: Int, order: Int = 0, group: Int? = null) =
        FolderDetail(id = id, name = "Project $id", path = "/$id", sortOrder = order, groupId = group)

    @Test fun mixedOrderMatchesWebAndKeepsEmptyGroups() {
        val folders = listOf(folder(5, 2), folder(3, 0), folder(4, 0), folder(6, 1, 9))
        val groups = listOf(FolderGroup(9, "Group", sortOrder = 0), FolderGroup(8, "Empty", sortOrder = 3))
        val layout = folderLayout(folders.reversed(), groups)
        assertEquals(listOf("g9", "f3", "f4", "f5", "g8"), layout.map {
            it.group?.let { g -> "g${g.id}" } ?: "f${it.folder!!.id}"
        })
        assertEquals(listOf(6), groupMembers(folders, 9).map { it.id })
    }

    @Test fun missingGroupFallsBackToTopLevelAndMembersUseStableIdTies() {
        val folders = listOf(folder(3, group = 7), folder(2, group = 7), folder(1, group = 99))
        assertEquals(listOf(1), folderLayout(folders, listOf(FolderGroup(7, "Group"))).mapNotNull { it.folder?.id })
        assertEquals(listOf(2, 3), groupMembers(folders, 7).map { it.id })
        assertEquals(listOf(1, 2, 3), folderLayout(folders, emptyList()).mapNotNull { it.folder?.id })
    }

    @Test fun aliasDoesNotChangeUnderlyingName() {
        val folder = folder(1).copy(alias = "Work")
        assertEquals("Work [Project 1]", folderDisplayName(folder))
        assertEquals("Project 1", folderDisplayName(folder.copy(alias = " ")))
    }

    @Test fun responsesUseSnakeCaseAndRequestsUseCamelCase() {
        val folder = CodegJson.response.decodeFromString(FolderDetail.serializer(),
            """{"id":1,"name":"Project","path":"/p","alias":"Work","group_id":8}""")
        assertEquals(8, folder.groupId)
        assertEquals("Work", folder.alias)
        val group = CodegJson.response.decodeFromString(FolderGroup.serializer(),
            """{"id":8,"name":"Group","sort_order":4}""")
        assertEquals(4, group.sortOrder)
        val old = CodegJson.response.decodeFromString(FolderDetail.serializer(),
            """{"id":2,"name":"Old","path":"/old"}""")
        assertNull(old.alias)
        assertNull(old.groupId)
        assertEquals("""{"folderId":1,"groupId":8}""", CodegJson.request.encodeToString(SetFolderGroupBody(1, 8)))
        // Omitted optional values deserialize to None on the original server too.
        assertEquals("""{"folderId":1}""", CodegJson.request.encodeToString(SetFolderGroupBody(1, null)))
        assertEquals("""{"folderId":1}""", CodegJson.request.encodeToString(UpdateFolderAliasBody(1, null)))
    }
}

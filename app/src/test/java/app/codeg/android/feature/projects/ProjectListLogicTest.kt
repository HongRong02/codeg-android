package app.codeg.android.feature.projects

import app.codeg.android.core.model.FolderDetail
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectListLogicTest {

    private fun folder(id: Int, name: String, sortOrder: Int = 0) =
        FolderDetail(id = id, name = name, path = "/path/$name", sortOrder = sortOrder)

    @Test
    fun `sortedFolders orders by sortOrder then case-insensitive name`() {
        val folders = listOf(
            folder(1, "zebra", sortOrder = 1),
            folder(2, "Alpha", sortOrder = 0),
            folder(3, "beta", sortOrder = 0),
            folder(4, "apple", sortOrder = 0),
            folder(5, "Banana", sortOrder = 1),
        )

        val sorted = sortedFolders(folders)
        val expectedIds = listOf(2, 4, 3, 5, 1)

        assertEquals(expectedIds, sorted.map { it.id })
    }

    @Test
    fun `sortedFolders preserves empty list`() {
        assertEquals(emptyList<FolderDetail>(), sortedFolders(emptyList()))
    }
}

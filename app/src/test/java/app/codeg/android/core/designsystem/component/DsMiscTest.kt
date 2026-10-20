package app.codeg.android.core.designsystem.component

import org.junit.Assert.*
import org.junit.Test

class DsMiscTest {
    private val groups = listOf(
        CodegCommandGroup("Tools", listOf(
            CodegCommandItem("a", "Open file", listOf("document")),
            CodegCommandItem("b", "Close", enabled = false),
        )),
        CodegCommandGroup("Other", listOf(CodegCommandItem("c", "Settings"))),
    )

    @Test fun filtersLabelsAndKeywordsWithoutCase() {
        assertEquals(listOf("a"), filterCommandGroups(groups, " DOCUMENT ").flatMap { it.items }.map { it.id })
        assertEquals(listOf("c"), filterCommandGroups(groups, "SETT").flatMap { it.items }.map { it.id })
    }
    @Test fun removesEmptyGroupsAndPreservesOrder() {
        assertTrue(filterCommandGroups(groups, "missing").isEmpty())
        assertEquals(groups, filterCommandGroups(groups, "  "))
        assertTrue(filterCommandGroups(emptyList(), "").isEmpty())
        assertTrue(filterCommandGroups(listOf(CodegCommandGroup("Empty", emptyList())), "").isEmpty())
    }
    @Test fun keepsDisabledItemsVisible() {
        assertFalse(filterCommandGroups(groups, "Close").single().items.single().enabled)
    }
    @Test fun clampsScaleIncludingNonFiniteValues() {
        assertEquals(1f, boundedPreviewScale(0f), 0f)
        assertEquals(5f, boundedPreviewScale(100f), 0f)
        assertEquals(1f, boundedPreviewScale(Float.NaN), 0f)
        assertEquals(1f, boundedPreviewScale(Float.POSITIVE_INFINITY), 0f)
        assertEquals(2f, boundedPreviewScale(2f), 0f)
    }
    @Test fun clampsIndexForEmptyAndChangedLists() {
        assertEquals(0, boundedPreviewIndex(4, 0))
        assertEquals(0, boundedPreviewIndex(-2, 3))
        assertEquals(2, boundedPreviewIndex(99, 3))
    }
    @Test fun clampsPanAndResetsAtOriginalScale() {
        assertEquals(0f, boundedPreviewPan(100f, 200f, 1f), 0f)
        assertEquals(100f, boundedPreviewPan(900f, 200f, 2f), 0f)
        assertEquals(-100f, boundedPreviewPan(-900f, 200f, 2f), 0f)
        assertEquals(0f, boundedPreviewPan(Float.NaN, 200f, 2f), 0f)
    }
    @Test fun fitsPortraitAndLandscapeBeforeClampingPan() {
        val portrait = fittedPreviewSize(100f, 1000f, 200f, 400f)
        assertEquals(40f, portrait.first, 0f)
        assertEquals(400f, portrait.second, 0f)
        assertEquals(0f, boundedPreviewPan(900f, 200f, 5f, portrait.first), 0f)
        val landscape = fittedPreviewSize(1000f, 100f, 200f, 400f)
        assertEquals(200f, landscape.first, 0f)
        assertEquals(20f, landscape.second, 0f)
        assertEquals(0f, boundedPreviewPan(-900f, 400f, 5f, landscape.second), 0f)
        assertEquals(400f, boundedPreviewPan(900f, 200f, 5f, landscape.first), 0f)
        assertEquals(200f to 400f, fittedPreviewSize(Float.NaN, Float.NaN, 200f, 400f))
    }
    @Test fun initialsHandleBlankWhitespaceAndUnicode() {
        assertEquals("", dsMiscInitial("  "))
        assertEquals("C", dsMiscInitial("  codeg"))
        assertEquals("中", dsMiscInitial("中文"))
        assertEquals("😀", dsMiscInitial("😀 folder"))
    }
}

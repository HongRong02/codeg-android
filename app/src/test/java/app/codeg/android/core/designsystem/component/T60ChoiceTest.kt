package app.codeg.android.core.designsystem.component

import org.junit.Assert.*
import org.junit.Test

class T60ChoiceTest {
    private data class Option(val id: Int, val name: String, val path: String?)
    private val first = Option(1, "Project", "/One")
    private val second = Option(2, "Project", "/Two")

    @Test fun duplicateLabelsKeepIdentity() {
        assertFalse(choiceSelected(first, second) { it.id })
        assertTrue(choiceSelected(first, first.copy(name = "Renamed")) { it.id })
        assertFalse(choiceSelected(first, null))
        assertTrue(choiceSelected(first, first.copy()))
    }

    @Test fun searchMatchesLabelAndDescriptionIgnoringCase() {
        assertTrue(choiceMatches(first, "project", { it.name }))
        assertTrue(choiceMatches(first, "ONE", { it.name }, { it.path }))
        assertFalse(choiceMatches(second, "ONE", { it.name }, { it.path }))
        assertFalse(choiceMatches(first.copy(path = null), "ONE", { it.name }, { it.path }))
        assertTrue(choiceMatches(first, "", { it.name }))
    }
}

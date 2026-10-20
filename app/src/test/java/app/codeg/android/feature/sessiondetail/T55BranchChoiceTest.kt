package app.codeg.android.feature.sessiondetail

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class T55BranchChoiceTest {
    @Test fun remoteBranchKeepsFullDisplayAndCheckoutTarget() {
        val choice = t55BranchChoice("origin/feature/topic", true, "main", emptySet(), false)
        assertEquals("r-origin/feature/topic", choice.id)
        assertEquals("feature/topic", choice.target)
        assertTrue(choice.enabled)
        assertFalse(choice.current)
    }

    @Test fun localAndRemoteCurrentBranchesAreDisabled() {
        assertFalse(t55BranchChoice("main", false, "main", emptySet(), false).enabled)
        assertFalse(t55BranchChoice("origin/main", true, "main", emptySet(), false).enabled)
        assertTrue(t55BranchChoice("origin/main", true, "main", emptySet(), false).current)
    }

    @Test fun occupiedAndBusyBranchesAreDisabled() {
        val occupied = t55BranchChoice("origin/topic", true, "main", setOf("topic"), false)
        assertTrue(occupied.occupied)
        assertFalse(occupied.enabled)
        assertFalse(t55BranchChoice("topic", false, "main", emptySet(), true).enabled)
    }

    @Test fun sameNameLocalAndRemoteKeepDistinctIdentity() {
        assertEquals("l-topic", t55BranchChoice("topic", false, null, emptySet(), false).id)
        assertEquals("r-topic", t55BranchChoice("topic", true, null, emptySet(), false).id)
    }
}

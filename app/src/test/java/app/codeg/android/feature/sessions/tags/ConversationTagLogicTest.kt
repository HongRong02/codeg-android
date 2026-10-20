package app.codeg.android.feature.sessions.tags

import org.junit.Assert.*
import org.junit.Test

class ConversationTagLogicTest {
    @Test fun emptyFilterIncludesUntaggedConversations() {
        assertTrue(matchesTagFilter(null, emptySet(), false))
    }

    @Test fun anyAndAllHaveDifferentSemantics() {
        assertTrue(matchesTagFilter(listOf(1), setOf(1, 2), false))
        assertFalse(matchesTagFilter(listOf(1), setOf(1, 2), true))
        assertTrue(matchesTagFilter(listOf(1, 2, 3), setOf(1, 2), true))
        assertFalse(matchesTagFilter(null, setOf(1), false))
    }

    @Test fun deltaPreservesUnchangedAssociations() {
        val delta = tagDelta(listOf(1, 2), setOf(2, 3))
        assertEquals(listOf(3), delta.first)
        assertEquals(listOf(1), delta.second)
    }
}

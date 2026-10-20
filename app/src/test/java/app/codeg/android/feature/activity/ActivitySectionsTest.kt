package app.codeg.android.feature.activity

import app.codeg.android.core.model.AgentType
import app.codeg.android.core.model.ConversationStatus
import app.codeg.android.core.model.ConversationSummary
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Duration
import java.time.Instant

/**
 * Tests the section grouping and feed filtering for the Activity tab:
 * live sessions -> Running section (sorted by updatedAt desc),
 * non-live sessions within 24 hours -> Recent section (sorted by updatedAt desc),
 * older non-live sessions excluded,
 * and isEmptyFeed calculation.
 */
class ActivitySectionsTest {

    private val now = Instant.now()

    private fun conv(
        id: Int,
        folderId: Int = 1,
        status: ConversationStatus = ConversationStatus.COMPLETED,
        updated: Instant = now.minus(Duration.ofHours(1)),
    ) = ConversationSummary(
        id = id,
        folderId = folderId,
        title = "Session " + id,
        agentType = AgentType.CLAUDE_CODE,
        status = status,
        createdAt = now.minus(Duration.ofHours(2)),
        updatedAt = updated,
    )

    @Test
    fun runningSectionIncludesLiveConversationsSortedNewestFirst() {
        val folderNames = mapOf(1 to "Project")
        val convs = listOf(
            conv(1, status = ConversationStatus.IN_PROGRESS, updated = now.minusSeconds(100)),
            conv(2, status = ConversationStatus.IN_PROGRESS, updated = now.minusSeconds(20)),
        )

        val sections = buildActivitySections(convs, folderNames)

        assertEquals(1, sections.size)
        val running = sections.first()
        assertEquals("running", running.id)
        assertEquals(ActivityKind.RUNNING, running.kind)
        assertEquals(listOf(2, 1), running.rows.map { it.conversation.id })
        assertEquals("Project", running.rows.first().folderName)
    }

    @Test
    fun recentSectionIncludesNonLiveConversationsWithin24Hours() {
        val folderNames = mapOf(1 to "Project")
        val convs = listOf(
            conv(1, status = ConversationStatus.COMPLETED, updated = now.minus(Duration.ofHours(5))),
            conv(2, status = ConversationStatus.CANCELLED, updated = now.minus(Duration.ofHours(1))),
            conv(3, status = ConversationStatus.COMPLETED, updated = now.minus(Duration.ofHours(25))),
        )

        val sections = buildActivitySections(convs, folderNames)

        assertEquals(1, sections.size)
        val recent = sections.first()
        assertEquals("recent", recent.id)
        assertEquals(ActivityKind.RECENT, recent.kind)
        assertEquals(listOf(2, 1), recent.rows.map { it.conversation.id })
    }

    @Test
    fun bothRunningAndRecentSectionsAreCreatedWhenPresent() {
        val folderNames = mapOf(1 to "FolderA", 2 to "FolderB")
        val convs = listOf(
            conv(1, folderId = 1, status = ConversationStatus.IN_PROGRESS, updated = now.minusSeconds(30)),
            conv(2, folderId = 2, status = ConversationStatus.COMPLETED, updated = now.minus(Duration.ofHours(2))),
        )

        val sections = buildActivitySections(convs, folderNames)

        assertEquals(listOf("running", "recent"), sections.map { it.id })
        assertEquals("FolderA", sections[0].rows[0].folderName)
        assertEquals("FolderB", sections[1].rows[0].folderName)
    }

    @Test
    fun emptyFeedIsDetectedCorrectly() {
        val stateEmpty = ActivityUiState(conversations = emptyList())
        assertTrue(stateEmpty.isEmptyFeed)

        val stateOldOnly = ActivityUiState(
            conversations = listOf(
                conv(1, status = ConversationStatus.COMPLETED, updated = now.minus(Duration.ofHours(30))),
            ),
        )
        assertTrue(stateOldOnly.isEmptyFeed)

        val stateLive = ActivityUiState(
            conversations = listOf(
                conv(1, status = ConversationStatus.IN_PROGRESS, updated = now.minus(Duration.ofHours(30))),
            ),
        )
        assertFalse(stateLive.isEmptyFeed)

        val stateRecent = ActivityUiState(
            conversations = listOf(
                conv(1, status = ConversationStatus.COMPLETED, updated = now.minus(Duration.ofHours(2))),
            ),
        )
        assertFalse(stateRecent.isEmptyFeed)
    }
}

package app.codeg.android.feature.sessiondetail

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.Assert.assertEquals
import app.codeg.android.core.model.AgentType
import app.codeg.android.core.model.MessageTurn
import app.codeg.android.core.model.TurnRole
import app.codeg.android.feature.sessiondetail.timeline.NodeContent
import app.codeg.android.feature.sessiondetail.timeline.TranscriptTimeline
import java.time.Instant

class InterruptedTurnTest {
    @Test fun mergedReplyHasOneTailNotice() {
        val turns = listOf("a", "b").map {
            MessageTurn(id = it, role = TurnRole.ASSISTANT, timestamp = Instant.EPOCH)
        }
        val nodes = TranscriptTimeline.buildPersisted(
            turns, emptyList(), AgentType.CLAUDE_CODE, interruptedTurnIds = setOf("a", "b"),
        )
        assertEquals(1, nodes.count { it.content == NodeContent.Interrupted })
        assertEquals(NodeContent.Interrupted, nodes.last().content)
        assertFalse(TranscriptTimeline.buildPersisted(turns, emptyList(), AgentType.CLAUDE_CODE)
            .any { it.content == NodeContent.Interrupted })
    }

    @Test fun missingCompletionIsInterrupted() {
        assertTrue(isInterruptedTurn(null, false))
    }

    @Test fun completionEventWinsOverPartialTranscript() {
        assertFalse(isInterruptedTurn("end_turn", false))
    }

    @Test fun completedTranscriptRecoversMissedEvent() {
        assertFalse(isInterruptedTurn(null, true))
    }

    @Test fun cancellationIsNotAnInterruption() {
        assertFalse(isInterruptedTurn("cancelled", false))
    }
}

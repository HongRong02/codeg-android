package app.codeg.android.feature.sessions.tags

import app.codeg.android.core.model.ConversationTag
import app.codeg.android.core.model.wire.UpdateConversationTagsRequest
import app.codeg.android.core.network.CodegJson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import org.junit.Assert.*
import org.junit.Test

class ConversationTagWireTest {
    @Test fun decodesServerScopeAndOrder() {
        val tag = CodegJson.response.decodeFromString<ConversationTag>(
            """{"id":4,"name":"Review","color":"#123456","folder_id":7,"sort_order":2}""",
        )
        assertEquals(7, tag.folderId)
        assertEquals(2, tag.sortOrder)
    }

    @Test fun sendsCamelCaseIncrementalUpdate() {
        val body = CodegJson.request.parseToJsonElement(CodegJson.request.encodeToString(
            UpdateConversationTagsRequest(9, listOf(4), listOf(2)),
        ))
        assertEquals(CodegJson.request.parseToJsonElement(
            """{"conversationId":9,"add":[4],"remove":[2]}""",
        ), body)
    }
}

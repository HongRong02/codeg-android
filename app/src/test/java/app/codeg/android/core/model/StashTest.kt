package app.codeg.android.core.model

import app.codeg.android.core.model.wire.StashPushRequest
import app.codeg.android.core.model.wire.StashRefRequest
import app.codeg.android.core.network.CodegJson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.*
import org.junit.Test

class StashTest {
    @Test fun decodesServerStashEntry() {
        val entry = CodegJson.response.decodeFromString<StashEntry>(
            """{"ref_name":"stash@{0}","message":"WIP on main","date":"2026-10-20","branch":"main"}"""
        )
        assertEquals("stash@{0}", entry.refName)
        assertEquals("WIP on main", entry.message)
        assertEquals("2026-10-20", entry.date)
    }

    @Test fun pushUsesCamelCaseAndOmitsOptionalMessage() {
        val body = CodegJson.request.parseToJsonElement(
            CodegJson.request.encodeToString(StashPushRequest("/repo", keepIndex = true))
        ).jsonObject
        assertEquals("true", body.getValue("keepIndex").jsonPrimitive.content)
        assertFalse(body.containsKey("message"))
        assertFalse(body.containsKey("keep_index"))
    }

    @Test fun sendsExactStashRefForPopApplyDropAndShow() {
        val body = CodegJson.request.parseToJsonElement(
            CodegJson.request.encodeToString(StashRefRequest("/repo", "stash@{2}"))
        ).jsonObject
        assertEquals("stash@{2}", body.getValue("stashRef").jsonPrimitive.content)
        assertEquals("/repo", body.getValue("path").jsonPrimitive.content)
    }
}

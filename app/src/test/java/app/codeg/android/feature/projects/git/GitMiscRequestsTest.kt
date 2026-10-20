package app.codeg.android.feature.projects.git

import app.codeg.android.core.model.wire.GitMiscWorktreeAddRequest
import app.codeg.android.core.model.wire.GitMiscWorktreeRemoveRequest
import app.codeg.android.core.network.CodegJson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class GitMiscRequestsTest {
    @Test fun removalPreservesBranchAndRefusesForceByDefault() {
        val body = CodegJson.request.parseToJsonElement(CodegJson.request.encodeToString(
            GitMiscWorktreeRemoveRequest("/repo", "topic", 42),
        )).jsonObject
        assertEquals("42", body.getValue("sourceFolderId").jsonPrimitive.content)
        assertEquals("false", body.getValue("deleteBranch").jsonPrimitive.content)
        assertEquals("false", body.getValue("force").jsonPrimitive.content)
    }

    @Test fun addUsesServerCamelCaseAndOmitsAbsentBase() {
        val body = CodegJson.request.parseToJsonElement(CodegJson.request.encodeToString(
            GitMiscWorktreeAddRequest("/repo", "topic", "/trees/topic"),
        )).jsonObject
        assertEquals("topic", body.getValue("branchName").jsonPrimitive.content)
        assertEquals("/trees/topic", body.getValue("worktreePath").jsonPrimitive.content)
        assertFalse(body.containsKey("base"))
    }
}

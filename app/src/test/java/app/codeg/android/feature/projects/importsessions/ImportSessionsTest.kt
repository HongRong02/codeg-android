package app.codeg.android.feature.projects.importsessions

import app.codeg.android.core.model.*
import app.codeg.android.core.model.wire.*
import app.codeg.android.core.network.CodegJson
import kotlinx.serialization.encodeToString
import org.junit.Assert.*
import org.junit.Test

class ImportSessionsTest {
    @Test fun bulkSelectionExcludesImportedAndDeleted() {
        val sessions = listOf("new", "imported", "deleted").map {
            ImportScanSession(externalId = it, agentType = "codex", startedAt = "2026-10-20T00:00:00Z", status = it)
        }
        assertEquals(listOf("new"), sessions.importableKeys().map { it.externalId })
    }

    @Test fun selectionIdentityIncludesAgent() {
        val a = ImportSessionKey("claude", "same")
        val b = ImportSessionKey("codex", "same")
        assertEquals(2, setOf(a, b).size)
    }

    @Test fun requestUsesCamelCaseKeys() {
        val body = CodegJson.request.encodeToString(ImportSelectedSessionsBody(listOf(ImportSessionKey("codex", "id"))))
        assertEquals("{\"selections\":[{\"agentType\":\"codex\",\"externalId\":\"id\"}]}", body)
    }
}

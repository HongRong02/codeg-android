package app.codeg.android.core.model

import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test

class BranchManageTest {
    @Test fun remoteNamesKeepBranchSlashes() {
        val branch = BranchManageEntry("origin/feature/topic", isRemote = true)
        assertEquals("origin", branch.remote)
        assertEquals("feature/topic", branch.remoteBranch)
    }

    @Test fun decodesNamesAndOptionalTrackingCounts() {
        val result = parseManagedBranches(Json.parseToJsonElement("""["main",{"name":"topic","ahead":2,"behind":1}]"""))
        assertEquals("main", result[0].name)
        assertEquals(2, result[1].ahead)
        assertEquals(1, result[1].behind)
    }
}

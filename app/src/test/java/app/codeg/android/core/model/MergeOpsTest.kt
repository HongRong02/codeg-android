package app.codeg.android.core.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MergeOpsTest {
    @Test fun mergeHeadIdentifiesAnOperationAfterAllFilesAreResolved() {
        assertTrue(mergeOperationActive(true, emptyList(), false, "merge"))
    }
    @Test fun rebaseRemainsActiveAfterLastFileIsStaged() {
        assertTrue(mergeOperationActive(false, emptyList(), true, "rebase"))
    }
    @Test fun conflictsIdentifyAnOperationWithoutMergeHead() {
        assertTrue(mergeOperationActive(false, listOf("file.txt"), false, "rebase"))
    }
    @Test fun completedOperationDoesNotRemainActive() {
        assertFalse(mergeOperationActive(false, emptyList(), false, "rebase"))
        assertFalse(mergeOperationActive(false, emptyList(), true, "merge"))
    }
}

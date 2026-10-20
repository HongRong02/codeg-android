package app.codeg.android.feature.sessiondetail

import org.junit.Assert.assertEquals
import org.junit.Test

class StreamReconnectPolicyTest {
    @Test fun backoffIsExponentialAndCapped() {
        assertEquals(listOf(1000L, 2000L, 4000L, 8000L, 16000L, 30000L, 30000L), (1..7).map(::streamReconnectDelay))
        assertEquals(30000L, streamReconnectDelay(Int.MAX_VALUE))
        assertEquals(1000L, streamReconnectDelay(0))
    }
    @Test fun recoveryResetsBudgetButNeverOverridesAuthentication() {
        assertEquals(StreamRecovery.RETRY, streamRecovery(true, false, false, 6, true))
        assertEquals(StreamRecovery.STOP, streamRecovery(true, true, false, 0, true))
        assertEquals(StreamRecovery.STOP, streamRecovery(false, false, false, 0, true))
        assertEquals(StreamRecovery.RECONCILE, streamRecovery(true, false, false, 6, false))
        assertEquals(StreamRecovery.RECONCILE, streamRecovery(true, false, true, 0, true))
        assertEquals(StreamRecovery.RETRY, streamRecovery(true, false, false, 5, false))
    }
}

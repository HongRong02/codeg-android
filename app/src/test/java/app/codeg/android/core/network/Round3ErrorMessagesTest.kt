package app.codeg.android.core.network

import app.codeg.android.R
import org.junit.Assert.assertEquals
import org.junit.Test

class Round3ErrorMessagesTest {
    private fun resource(error: Throwable): String = error.displayMessage { it.toString() }
    @Test fun localizesTimeoutsAndRegistryNetworkErrors() {
        assertEquals(R.string.error_timeout.toString(), resource(ApiError.Transport("Socket timeout has expired [url=secret]")))
        assertEquals(R.string.error_timeout.toString(), resource(java.net.SocketTimeoutException("timeout")))
        assertEquals(R.string.error_network.toString(), resource(ApiError.Server(502, "network_error", "registry failed")))
    }
    @Test fun preservesOtherServerErrors() {
        assertEquals("specific failure", resource(ApiError.Server(400, "invalid", "specific failure")))
    }
}

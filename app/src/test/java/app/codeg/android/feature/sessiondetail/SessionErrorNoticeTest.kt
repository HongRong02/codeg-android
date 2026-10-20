package app.codeg.android.feature.sessiondetail

import app.codeg.android.core.network.ApiError
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SessionErrorNoticeTest {
    @Test fun suppressesUnauthorizedOnlyWhenTokenBannerIsVisible() {
        assertTrue(suppressAuthNotice(ApiError.Unauthorized, StreamNotice("Token expired", openServers = true)))
        assertFalse(suppressAuthNotice(ApiError.Unauthorized, null))
        assertFalse(suppressAuthNotice(ApiError.Unauthorized, StreamNotice("Reconnecting")))
        assertFalse(suppressAuthNotice(ApiError.Transport("offline"), StreamNotice("Token expired", openServers = true)))
        assertFalse(suppressAuthNotice(ApiError.Server(403, null, "Forbidden"), StreamNotice("Token expired", openServers = true)))
    }
}

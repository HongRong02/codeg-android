package app.codeg.android.core.network

import app.codeg.android.R
import org.junit.Assert.assertEquals
import org.junit.Test

class ErrorMessagesTest {
    private val english = mapOf(
        R.string.error_unauthorized to "Invalid or missing token",
        R.string.error_turn_in_progress to "A turn is already in progress",
        R.string.error_decoding to "Unexpected response from the server",
        R.string.error_unknown to "Something went wrong",
    )
    private val chinese = mapOf(
        R.string.error_unauthorized to "Token 无效或缺失",
        R.string.error_turn_in_progress to "当前会话已有一轮对话正在进行",
        R.string.error_decoding to "服务器返回了意外的响应",
        R.string.error_unknown to "发生错误",
    )

    @Test fun mapsKnownErrorsUsingLocalizedResources() {
        for (strings in listOf(english, chinese)) {
            assertEquals(strings.getValue(R.string.error_unauthorized), ApiError.Unauthorized.displayMessage(strings::getValue))
            assertEquals(strings.getValue(R.string.error_turn_in_progress), ApiError.TurnInProgress.displayMessage(strings::getValue))
            assertEquals(strings.getValue(R.string.error_decoding), ApiError.Decoding("raw decoding details").displayMessage(strings::getValue))
            assertEquals(strings.getValue(R.string.error_unknown), Exception().displayMessage(strings::getValue))
        }
    }

    @Test fun preservesServerTransportAndOtherExceptionDetails() {
        for (strings in listOf(english, chinese)) {
            val messages = listOf(
                ApiError.Server(500, "failure", "服务端原始消息"),
                ApiError.Transport("TLS handshake failed"),
                Exception("Original exception detail"),
                ApiError.Server(400, null, ""),
            )
            for (error in messages) assertEquals(error.message, error.displayMessage(strings::getValue))
        }
    }
}

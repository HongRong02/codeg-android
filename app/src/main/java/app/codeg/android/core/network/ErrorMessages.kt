package app.codeg.android.core.network

import android.content.Context
import app.codeg.android.R

/** Maps a thrown error to a localized user-facing message. */
fun Throwable.displayMessage(context: Context): String = displayMessage { context.getString(it) }

internal fun Throwable.displayMessage(stringResource: (Int) -> String): String = when (this) {
    is ApiError.Unauthorized -> stringResource(R.string.error_unauthorized)
    is ApiError.TurnInProgress -> stringResource(R.string.error_turn_in_progress)
    is ApiError.Transport -> detail
    is ApiError.Server -> serverMessage
    is ApiError.Decoding -> stringResource(R.string.error_decoding)
    else -> message ?: stringResource(R.string.error_unknown)
}

/** Maps a thrown error (typically an [ApiError]) to a short user-facing message. */
@Deprecated("Use displayMessage(context) for localized messages")
fun Throwable.displayMessage(): String = when (this) {
    is ApiError.Unauthorized -> "Invalid or missing token"
    is ApiError.TurnInProgress -> "A turn is already in progress"
    is ApiError.Transport -> detail
    is ApiError.Server -> serverMessage
    is ApiError.Decoding -> "Unexpected response from the server"
    else -> message ?: "Something went wrong"
}

package app.codeg.android.feature.sessiondetail

internal const val MAX_STREAM_RECONNECTS = 6

internal fun streamReconnectDelay(attempt: Int): Long =
    (1000L shl (attempt.coerceIn(1, 6) - 1)).coerceAtMost(30000L)

internal enum class StreamRecovery { STOP, RETRY, RECONCILE }

internal fun streamRecovery(
    inFlight: Boolean,
    authFailed: Boolean,
    connectionGone: Boolean,
    attempts: Int,
    immediate: Boolean,
): StreamRecovery = when {
    authFailed || !inFlight -> StreamRecovery.STOP
    connectionGone -> StreamRecovery.RECONCILE
    !immediate && attempts >= MAX_STREAM_RECONNECTS -> StreamRecovery.RECONCILE
    else -> StreamRecovery.RETRY
}

/** Stream connection status shown above the composer; [openServers] adds a jump to server settings. */
data class StreamNotice(val message: String, val openServers: Boolean = false)

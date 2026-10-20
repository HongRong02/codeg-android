package app.codeg.android.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerLogRecord(
    val seq: Long,
    val timestampMs: Long,
    val level: String,
    val target: String,
    val message: String,
    val fields: Map<String, String> = emptyMap(),
    val spans: List<ServerLogSpan> = emptyList(),
) {
    fun line(): String = buildString {
        append("${java.time.Instant.ofEpochMilli(timestampMs)} [$level] $target: $message")
        if (fields.isNotEmpty()) append(" $fields")
        spans.forEach { append(" › ${it.name} ${it.fields}") }
    }
}

@Serializable
data class ServerLogSpan(val name: String, val fields: Map<String, String> = emptyMap())

@Serializable
data class ServerLogFile(val name: String, val sizeBytes: Long, val modifiedMs: Long)

@Serializable
data class ServerConversationStats(
    val totalConversations: Long,
    val totalMessages: Long,
    val byAgent: List<ServerAgentCount> = emptyList(),
)

@Serializable
data class ServerAgentCount(val agentType: String, val conversationCount: Long)

@Serializable
data class ServerTokenTotals(
    val inputTokens: Long,
    val outputTokens: Long,
    val cacheCreationTokens: Long,
    val cacheReadTokens: Long,
    val totalTokens: Long,
    val conversationCount: Long,
    val turnCount: Long,
)

@Serializable
data class ServerTokenPoint(val bucketKey: String, val totalTokens: Long)

@Serializable
data class ServerTokenReport(
    val totals: ServerTokenTotals,
    val series: List<ServerTokenPoint> = emptyList(),
    val truncated: Boolean = false,
)

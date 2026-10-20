package app.codeg.android.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssistantAvatar(val kind: String = "monogram", val color: String = "blue", val emoji: String? = null, @SerialName("data_url") val dataUrl: String? = null)

@Serializable
data class AssistantOptions(@SerialName("mode_id") val modeId: String? = null, @SerialName("config_values") val configValues: Map<String, String> = emptyMap())

@Serializable
data class AssistantOptionLabel(val id: String, val kind: String, val name: String, @SerialName("value_label") val valueLabel: String)

@Serializable
data class AssistantLabels(val agent: String, val mode: String? = null, val options: List<AssistantOptionLabel> = emptyList())

@Serializable
data class AssistantAgentConfig(@SerialName("agent_type") val agentType: AgentType, @SerialName("agent_options") val agentOptions: AssistantOptions, @SerialName("label_snapshot") val labelSnapshot: AssistantLabels)

@Serializable
data class Assistant(
    val id: Int,
    val name: String,
    val description: String = "",
    val avatar: AssistantAvatar = AssistantAvatar(),
    val agentType: AgentType,
    val agentOptions: AssistantOptions = AssistantOptions(),
    val labelSnapshot: AssistantLabels,
    val instructions: String = "",
    val enabled: Boolean = true,
    val sortOrder: Int = 0,
    val deletedAt: String? = null,
)

fun reorderedAssistantIds(ids: List<Int>, id: Int, delta: Int): List<Int> {
    val index = ids.indexOf(id)
    val target = index + delta
    if (index < 0 || target !in ids.indices || delta !in listOf(-1, 1)) return ids
    return ids.toMutableList().apply { this[index] = ids[target]; this[target] = id }
}

/** Build only complete selections, with a snapshot matching the values sent. */
fun assistantConfig(agent: AgentType, label: String, snapshot: AgentOptionsSnapshot, mode: String?, values: Map<String, String>): AssistantAgentConfig? {
    val modes = snapshot.modes?.availableModes.orEmpty()
    val selectedMode = modes.find { it.id == mode }
    if (modes.isNotEmpty() && selectedMode == null) return null
    val labels = snapshot.configOptions.map { option ->
        val value = values[option.id] ?: return null
        val choices = option.kind.options + option.kind.groups.flatMap { it.options }
        val picked = choices.find { it.value == value }
        val boolean = option.kind.type == "boolean"
        if (boolean && value !in listOf("true", "false")) return null
        if (!boolean && picked == null) return null
        AssistantOptionLabel(option.id, if (boolean) "boolean" else "select", option.name, picked?.name ?: value)
    }
    return AssistantAgentConfig(agent, AssistantOptions(selectedMode?.id, values.filterKeys { key -> labels.any { it.id == key } }), AssistantLabels(label, selectedMode?.name, labels))
}

fun assistantMonogram(name: String): String {
    val text = name.trim()
    if (text.isEmpty()) return ""
    val iterator = java.text.BreakIterator.getCharacterInstance(java.util.Locale.ROOT)
    iterator.setText(text)
    return text.substring(0, iterator.next()).uppercase(java.util.Locale.ROOT)
}

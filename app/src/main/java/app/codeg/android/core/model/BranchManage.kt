package app.codeg.android.core.model

import kotlinx.serialization.json.*

data class BranchManageEntry(
    val name: String,
    val isRemote: Boolean = false,
    val ahead: Int? = null,
    val behind: Int? = null,
) {
    val remote: String get() = name.substringBefore('/')
    val remoteBranch: String get() = name.substringAfter('/')
}

fun parseManagedBranches(value: JsonElement): List<BranchManageEntry> =
    value.jsonArray.map { entry ->
        if (entry is JsonPrimitive) BranchManageEntry(entry.content)
        else entry.jsonObject.let {
            BranchManageEntry(
                name = it.getValue("name").jsonPrimitive.content,
                ahead = it["ahead"]?.jsonPrimitive?.intOrNull,
                behind = it["behind"]?.jsonPrimitive?.intOrNull,
            )
        }
    }

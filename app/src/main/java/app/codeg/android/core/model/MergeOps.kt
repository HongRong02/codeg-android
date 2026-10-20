package app.codeg.android.core.model
import kotlinx.serialization.Serializable
@Serializable data class MergeVersions(val base: String, val ours: String, val theirs: String)

/** Rebase has no MERGE_HEAD and remains active after its last conflict is staged. */
fun mergeOperationActive(hasMergeHead: Boolean, conflicts: List<String>, wasActive: Boolean, operation: String): Boolean =
    hasMergeHead || conflicts.isNotEmpty() || (wasActive && operation == "rebase")

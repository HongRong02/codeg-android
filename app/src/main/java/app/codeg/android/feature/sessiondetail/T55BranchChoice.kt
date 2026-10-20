package app.codeg.android.feature.sessiondetail

/** Presentation identity and availability; checkout remains owned by the ViewModel. */
internal data class T55BranchChoice(
    val id: String,
    val target: String,
    val current: Boolean,
    val occupied: Boolean,
    val enabled: Boolean,
)

internal fun t55BranchChoice(
    name: String,
    remote: Boolean,
    current: String?,
    worktree: Set<String>,
    busy: Boolean,
): T55BranchChoice {
    val target = if (remote) name.substringAfter('/', name) else name
    val isCurrent = target == current
    val occupied = target in worktree && !isCurrent
    return T55BranchChoice(
        id = (if (remote) "r-" else "l-") + name,
        target = target,
        current = isCurrent,
        occupied = occupied,
        enabled = !busy && !occupied && !isCurrent,
    )
}

package app.codeg.android.feature.sessions.tags

/** Empty selection disables filtering; absent tag ids represent an untagged conversation. */
internal fun matchesTagFilter(ids: List<Int>?, selected: Set<Int>, matchAll: Boolean): Boolean =
    selected.isEmpty() || if (matchAll) selected.all { it in ids.orEmpty() }
    else selected.any { it in ids.orEmpty() }

internal fun tagDelta(previous: List<Int>?, selected: Set<Int>): Pair<List<Int>, List<Int>> =
    (selected - previous.orEmpty().toSet()).toList() to (previous.orEmpty().toSet() - selected).toList()

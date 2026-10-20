package app.codeg.android.feature.projects

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.codeg.android.core.designsystem.component.CodegBadge
import app.codeg.android.core.designsystem.component.CodegBadgeVariant
import app.codeg.android.core.model.GitChange

/** Per-category tint for a working-tree change, shared by the Changes tab + commit sheet. */
/** The colored letter badge (M / A / U / …) for a change category. */
@Composable
internal fun GitChangeBadge(change: GitChange, modifier: Modifier = Modifier) {
    CodegBadge(
        text = change.badge,
        variant = when (change) {
            GitChange.DELETED, GitChange.CONFLICTED -> CodegBadgeVariant.Destructive
            GitChange.ADDED, GitChange.UNTRACKED -> CodegBadgeVariant.Default
            else -> CodegBadgeVariant.Secondary
        },
        modifier = modifier,
    )
}

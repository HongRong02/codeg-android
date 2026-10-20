package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegIconButton

/** Shared insertion point for the project Git actions added by T17–T20. */
@Composable
fun GitActionsMenu(onSwitchBranch: () -> Unit, enabled: Boolean = true) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        CodegIconButton(onClick = { expanded = true }, enabled = enabled) {
            Icon(Icons.Rounded.MoreVert, contentDescription = stringResource(R.string.project_detail_actions))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            // T05: existing branch picker; T17–T20 append independent items here.
            DropdownMenuItem(
                text = { Text(stringResource(R.string.branch_switch_branch)) },
                onClick = {
                    expanded = false
                    onSwitchBranch()
                },
            )
        }
    }
}

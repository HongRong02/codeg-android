package app.codeg.android.feature.projects.git

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.codeg.android.feature.projects.ProjectDetailViewModel
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
    val project: ProjectDetailViewModel = hiltViewModel()
    val projectUi by project.ui.collectAsStateWithLifecycle()
    val client = projectUi.client
    val path = projectUi.folder?.path
    var showManage by remember(client, path) { mutableStateOf(false) }
    val manager: BranchManageViewModel? = if (client != null && path != null) {
        viewModel(key = "git-branches-$path-${System.identityHashCode(client)}", factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = BranchManageViewModel(client, path) as T
        })
    } else null
    val manageUi = manager?.ui?.collectAsStateWithLifecycle()?.value
    if (showManage && manager != null && manageUi?.supported == true) {
        BranchManageSheet(manager, onDismiss = { showManage = false })
    }
    var expanded by remember { mutableStateOf(false) }
    Box {
        CodegIconButton(onClick = { expanded = true }, enabled = enabled) {
            Icon(Icons.Rounded.MoreVert, contentDescription = stringResource(R.string.project_detail_actions))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            if (manager != null && manageUi?.supported == true) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.bm_title)) },
                    onClick = { expanded = false; showManage = true; manager.refresh() },
                )
            }
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

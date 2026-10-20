package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.LaunchedEffect
import app.codeg.android.feature.projects.ProjectDetailViewModel
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
    val mergeVm: MergeOpsViewModel = viewModel(key = "git-merge:$path")
    val mergeUi by mergeVm.ui.collectAsStateWithLifecycle()
    var showMerge by remember(path) { mutableStateOf(false) }
    LaunchedEffect(client, path, enabled) {
        if (client != null && path != null && enabled) mergeVm.refresh(client, path)
    }
    if (showMerge && client != null && path != null && mergeUi.supported) {
        MergeOpsSheet(mergeVm, client, path, projectUi.branch) {
            showMerge = false
            project.load()
        }
    }
    var expanded by remember { mutableStateOf(false) }
    Box {
        CodegIconButton(onClick = { expanded = true }, enabled = enabled) {
            Icon(Icons.Rounded.MoreVert, contentDescription = stringResource(R.string.project_detail_actions))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            if (client != null && path != null && mergeUi.supported) {
                DropdownMenuItem(text = { Text(stringResource(R.string.gm_title)) }, onClick = {
                    expanded = false
                    showMerge = true
                    mergeVm.refresh(client, path)
                }, enabled = !projectUi.gitBusy)
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

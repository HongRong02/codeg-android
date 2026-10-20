package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.feature.projects.ProjectDetailViewModel

/**
 * Shared insertion point for the project Git actions added by T17–T20. Each task
 * owns one labelled block of state + sheet above the menu and one menu item below;
 * they share the project's [ProjectDetailViewModel] (same nav-entry instance).
 */
@Composable
fun GitActionsMenu(onSwitchBranch: () -> Unit, enabled: Boolean = true) {
    val project: ProjectDetailViewModel = hiltViewModel()
    val projectUi by project.ui.collectAsStateWithLifecycle()
    val client = projectUi.client
    val folder = projectUi.folder
    val path = folder?.path

    // T17: stash management
    var showStash by remember(client, path) { mutableStateOf(false) }
    val stash: StashViewModel? = if (client != null && path != null) {
        viewModel(key = "git-stash-$path-${System.identityHashCode(client)}", factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = StashViewModel(client, path) as T
        })
    } else null
    val stashUi = stash?.ui?.collectAsStateWithLifecycle()?.value
    LaunchedEffect(stash, enabled) { if (enabled) stash?.refresh() }
    if (showStash && stash != null && stashUi?.supported == true) {
        StashSheet(stash, onDismiss = { showStash = false }, onChanged = project::load)
    }

    // T18: branch management
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

    // T19: merge / rebase / reset and conflicts
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

    // T20: remotes, init, worktrees, per-file commit
    var showMisc by remember { mutableStateOf(false) }
    if (showMisc && client != null && folder != null) {
        GitMiscSheet(client, folder.path, folder.id, onDismiss = { showMisc = false }, onChanged = project::load)
    }

    // T32: forge
    var showForge by remember(client, folder?.id) { mutableStateOf(false) }
    val forge: app.codeg.android.feature.projects.forge.ForgeViewModel? = if (client != null && folder != null) {
        viewModel(key = "forge-${folder.id}-${System.identityHashCode(client)}", factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                app.codeg.android.feature.projects.forge.ForgeViewModel(client, folder.id) as T
        })
    } else null
    val forgeUi = forge?.ui?.collectAsStateWithLifecycle()?.value
    LaunchedEffect(forge, enabled) { if (enabled) forge?.probe() }
    if (showForge && forge != null && forgeUi?.remote?.supported == true) {
        app.codeg.android.feature.projects.forge.ForgeSheet(forge, onDismiss = { showForge = false })
    }

    var expanded by remember { mutableStateOf(false) }
    Box {
        CodegIconButton(onClick = { expanded = true }, enabled = enabled) {
            Icon(Icons.Rounded.MoreVert, contentDescription = stringResource(R.string.project_detail_actions))
        }
        CodegDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            // T05: existing branch picker
            CodegMenuItem(
                text = { Text(stringResource(R.string.branch_switch_branch)) },
                onClick = {
                    expanded = false
                    onSwitchBranch()
                },
            )
            // T17
            if (stash != null && stashUi?.supported == true) {
                CodegMenuItem(
                    text = { Text(stringResource(R.string.stash_title)) },
                    onClick = { expanded = false; showStash = true; stash.refresh() },
                    enabled = !projectUi.gitBusy,
                )
            }
            // T18
            if (manager != null && manageUi?.supported == true) {
                CodegMenuItem(
                    text = { Text(stringResource(R.string.bm_title)) },
                    onClick = { expanded = false; showManage = true; manager.refresh() },
                )
            }
            // T19
            if (client != null && path != null && mergeUi.supported) {
                CodegMenuItem(text = { Text(stringResource(R.string.gm_title)) }, onClick = {
                    expanded = false
                    showMerge = true
                    mergeVm.refresh(client, path)
                }, enabled = !projectUi.gitBusy)
            }
            // T20
            CodegMenuItem(
                text = { Text(stringResource(R.string.git_misc_title)) },
                onClick = { expanded = false; showMisc = true },
            )
            // T32
            if (forge != null && forgeUi?.remote?.supported == true) {
                CodegMenuItem(
                    text = { Text(stringResource(R.string.forge_title)) },
                    onClick = { expanded = false; showForge = true; forge.refresh() },
                    enabled = !projectUi.gitBusy,
                )
            }
        }
    }
}

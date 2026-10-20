package app.codeg.android.feature.projects

import app.codeg.android.core.designsystem.component.CodegMenuItem
import app.codeg.android.core.designsystem.component.CodegDropdownMenu
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegSectionHeader
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegConfirmDialog
import app.codeg.android.core.designsystem.component.CodegCard
import app.codeg.android.core.designsystem.component.CodegBadge
import app.codeg.android.core.designsystem.component.CodegBadgeVariant
import androidx.compose.foundation.ExperimentalFoundationApi
import app.codeg.android.feature.projects.groups.*
import app.codeg.android.core.model.FolderGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.common.RelativeTime
import app.codeg.android.core.designsystem.component.CodegIconButton
import app.codeg.android.core.designsystem.component.CodegTopBar
import app.codeg.android.core.designsystem.component.EmptyState
import app.codeg.android.core.designsystem.component.InlineError
import app.codeg.android.core.designsystem.component.LoadingView
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.FolderDetail
import androidx.compose.runtime.LaunchedEffect
import app.codeg.android.core.designsystem.component.CodegCollapsible
import app.codeg.android.core.designsystem.component.CodegFolderBadge
import app.codeg.android.core.designsystem.component.LocalCodegToaster
import app.codeg.android.core.designsystem.icon.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(
    onOpenFolder: (Int) -> Unit,
    viewModel: ProjectsViewModel = hiltViewModel(),
) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val toaster = LocalCodegToaster.current
    val refreshFailed = stringResource(R.string.projects_load_failed)
    LaunchedEffect(ui.error, ui.hasLoaded) {
        if (ui.hasLoaded) ui.error?.let { toaster.error(refreshFailed, it) }
    }
    val colors = CodegTheme.colors
    val importViewModel: app.codeg.android.feature.projects.importsessions.ImportSessionsViewModel = hiltViewModel()
    val importUi by importViewModel.ui.collectAsStateWithLifecycle()
    var showImport by remember { mutableStateOf(false) }
    var menuOpen by remember { mutableStateOf(false) }
    var showBrowser by remember { mutableStateOf(false) }
    var showClone by remember { mutableStateOf(false) }
    var createGroup by remember { mutableStateOf(false) }
    var editFolder by remember { mutableStateOf<FolderDetail?>(null) }
    var renameGroup by remember { mutableStateOf<FolderGroup?>(null) }
    var deleteGroup by remember { mutableStateOf<FolderGroup?>(null) }
    var collapsed by remember { mutableStateOf(setOf<Int>()) }

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CodegTopBar(
                title = stringResource(R.string.tab_folders),
                showDivider = true,
                actions = {
                    Box {
                        CodegIconButton(
                            onClick = { menuOpen = true },
                            visualSize = 32.dp,
                        ) {
                            Icon(
                                imageVector = Lucide.Plus,
                                contentDescription = stringResource(R.string.projects_add),
                                tint = colors.textSecondary,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                        CodegDropdownMenu(
                            expanded = menuOpen,
                            onDismissRequest = { menuOpen = false },
                        ) {
                            if (importUi.supported) {
                                CodegMenuItem(text = { Text(stringResource(R.string.imp_title)) },
                                    onClick = { menuOpen = false; showImport = true })
                            }
                            if (ui.groupsSupported) {
                                CodegMenuItem(text = { Text(stringResource(R.string.fg_create)) },
                                    enabled = !ui.isBusy,
                                    onClick = { menuOpen = false; createGroup = true })
                            }
                            CodegMenuItem(
                                text = { Text(stringResource(R.string.projects_open_folder)) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Lucide.FolderPlus,
                                        contentDescription = null,
                                        tint = colors.textSecondary,
                                        modifier = Modifier.size(16.dp),
                                    )
                                },
                                onClick = {
                                    menuOpen = false
                                    showBrowser = true
                                },
                            )
                            CodegMenuItem(
                                text = { Text(stringResource(R.string.projects_clone_repo)) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Lucide.CloudDownload,
                                        contentDescription = null,
                                        tint = colors.textSecondary,
                                        modifier = Modifier.size(16.dp),
                                    )
                                },
                                onClick = {
                                    menuOpen = false
                                    showClone = true
                                },
                            )
                        }
                    }
                },
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            when {
                ui.loading && !ui.hasLoaded -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingView(stringResource(R.string.common_loading))
                    }
                }
                !ui.hasLoaded && ui.error != null -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        InlineError(
                            icon = Lucide.FolderOpen,
                            title = stringResource(R.string.projects_load_failed),
                            message = ui.error!!,
                            onRetry = viewModel::refresh,
                        )
                    }
                }
                ui.hasLoaded && ui.folders.isEmpty() && ui.groups.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        EmptyState(
                            icon = Lucide.FolderOpen,
                            title = stringResource(R.string.projects_empty_title),
                            message = stringResource(R.string.projects_empty_message),
                            actionLabel = stringResource(R.string.projects_open_folder),
                            onAction = { showBrowser = true },
                        )
                    }
                }
                else -> {
                    PullToRefreshBox(
                        isRefreshing = ui.refreshing,
                        onRefresh = viewModel::refresh,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                start = 12.dp,
                                end = 12.dp,
                                top = 8.dp,
                                bottom = 88.dp,
                            ),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            item(key = "folders-card") {
                                CodegCard(Modifier.fillMaxWidth()) {
                                    Column {
                                    folderLayout(ui.folders, ui.groups).forEach { entry ->
                                        val group = entry.group
                                        if (group != null) {
                                            CodegCollapsible(
                                                expanded = group.id !in collapsed,
                                                onExpandedChange = { expanded -> collapsed = if (expanded) collapsed - group.id else collapsed + group.id },
                                                header = {
                                                    CodegSectionHeader(
                                                        title = group.name,
                                                        action = {
                                                            if (ui.groupsSupported) {
                                                                Row {
                                                                    CodegButton(variant = CodegButtonVariant.Ghost, enabled = !ui.isBusy, onClick = { renameGroup = group }) { Text(stringResource(R.string.fg_rename)) }
                                                                    CodegButton(variant = CodegButtonVariant.Ghost, enabled = !ui.isBusy, onClick = { deleteGroup = group }) { Text(stringResource(R.string.fg_delete)) }
                                                                }
                                                            }
                                                        },
                                                    )
                                                },
                                            ) {
                                                val members = groupMembers(ui.folders, group.id)
                                                if (members.isEmpty()) Text(stringResource(R.string.fg_empty), Modifier.padding(16.dp), color = colors.textSecondary)
                                                members.forEach { folder ->
                                                    FolderRow(folder, ui.runningCount(folder.id), Modifier.padding(start = 16.dp),
                                                        onLongClick = if (ui.groupsSupported && !ui.isBusy) ({ editFolder = folder }) else null,
                                                        onClick = { onOpenFolder(folder.id) })
                                                }
                                            }
                                        } else entry.folder?.let { folder ->
                                            FolderRow(folder, ui.runningCount(folder.id),
                                                onLongClick = if (ui.groupsSupported && !ui.isBusy) ({ editFolder = folder }) else null,
                                                onClick = { onOpenFolder(folder.id) })
                                        }
                                    }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (createGroup && ui.groupsSupported) GroupTextDialog(stringResource(R.string.fg_create), "", onDismiss = { createGroup = false }) { name ->
        viewModel.editGroup { it.createFolderGroup(name) }
    }
    renameGroup?.takeIf { ui.groupsSupported }?.let { group ->
        GroupTextDialog(stringResource(R.string.fg_rename), group.name, onDismiss = { renameGroup = null }) { name ->
            viewModel.editGroup { it.updateFolderGroup(group.id, name) }
        }
    }
    deleteGroup?.takeIf { ui.groupsSupported }?.let { group ->
        CodegConfirmDialog(
            title = stringResource(R.string.fg_delete),
            message = stringResource(R.string.fg_delete_confirm, group.name),
            target = group.name,
            confirmLabel = stringResource(R.string.fg_delete),
            destructive = true,
            onConfirm = {
                if (!ui.isBusy) {
                    deleteGroup = null
                    viewModel.editGroup { it.deleteFolderGroup(group.id) }
                }
            },
            onDismiss = { deleteGroup = null },
        )
    }
    editFolder?.takeIf { ui.groupsSupported }?.let { folder ->
        FolderEditDialog(folder, ui.groups, onDismiss = { editFolder = null },
            onAlias = { alias -> viewModel.editGroup { it.updateFolderAlias(folder.id, alias.takeIf(String::isNotBlank)) } },
            onColor = { color -> viewModel.editGroup { it.updateFolderColor(folder.id, color) } },
            onGroup = { id -> viewModel.editGroup { it.setFolderGroup(folder.id, id) } })
    }

    if (showImport && importUi.supported) {
        app.codeg.android.feature.projects.importsessions.ImportSessionsSheet(
            ui = importUi, viewModel = importViewModel,
            onDismiss = { showImport = false }, onImported = viewModel::refresh)
    }
    if (showBrowser) {
        DirectoryBrowserSheet(
            title = stringResource(R.string.projects_open_folder),
            confirmLabel = stringResource(R.string.projects_open_this_folder),
            loadHome = viewModel::homeDirectory,
            loadDirs = viewModel::listDirectories,
            onSelect = { path -> showBrowser = false; viewModel.openFolder(path) {} },
            onDismiss = { showBrowser = false },
        )
    }
    if (showClone) {
        CloneRepoSheet(
            loadHome = viewModel::homeDirectory,
            loadDirs = viewModel::listDirectories,
            clone = viewModel::clone,
            onCloned = { showClone = false; viewModel.refresh() },
            onDismiss = { showClone = false },
        )
    }
}

/**
 * Shared project row with its colored folder tile, path, branch, and activity metadata.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FolderRow(
    folder: FolderDetail,
    running: Int,
    modifier: Modifier = Modifier,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    val colors = CodegTheme.colors
    val tile = folderTint(folder.color)
    CodegListRow(
        title = folderDisplayName(folder),
        subtitle = folder.path,
        modifier = modifier,
        onClick = onClick,
        onLongClick = onLongClick,
        leading = { CodegFolderBadge(name = folderDisplayName(folder), color = tile) },
        trailing = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                folder.gitBranch?.let { BranchPill(it) }
                if (running > 0) RunningPill(running)
                else folder.lastOpenedAt?.let { Text(RelativeTime.compact(it), fontSize = 11.sp, color = colors.textSecondary) }
                Icon(Lucide.ChevronRight, null, tint = colors.textSecondary, modifier = Modifier.size(16.dp))
            }
        },
    )
}

/** Active-task indicator: a pulsing dot + count in an accent capsule. */
@Composable
private fun RunningPill(count: Int, modifier: Modifier = Modifier) {
    CodegBadge(text = count.toString(), variant = CodegBadgeVariant.Secondary, modifier = modifier)
}

/** Git branch indicator capsule. */
@Composable
fun BranchPill(branch: String, modifier: Modifier = Modifier) {
    CodegBadge(text = branch, variant = CodegBadgeVariant.Outline, modifier = modifier)
}

internal fun sortedFolders(folders: List<FolderDetail>): List<FolderDetail> =
    folders.sortedWith(compareBy({ it.sortOrder }, { it.name.lowercase() }))

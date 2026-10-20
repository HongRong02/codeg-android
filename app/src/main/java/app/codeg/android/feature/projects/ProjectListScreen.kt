package app.codeg.android.feature.projects

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import app.codeg.android.feature.projects.groups.*
import app.codeg.android.core.model.FolderGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.AltRoute
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CloudDownload
import androidx.compose.material.icons.rounded.CreateNewFolder
import androidx.compose.material.icons.rounded.FolderOpen
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.common.RelativeTime
import app.codeg.android.core.designsystem.component.CodegIconButton
import app.codeg.android.core.designsystem.component.CodegTopBar
import app.codeg.android.core.designsystem.component.EmptyState
import app.codeg.android.core.designsystem.component.FolderBadge
import app.codeg.android.core.designsystem.component.InlineError
import app.codeg.android.core.designsystem.component.LivePulse
import app.codeg.android.core.designsystem.component.LoadingView
import app.codeg.android.core.designsystem.component.webSurface
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.PillShape
import app.codeg.android.core.designsystem.theme.colorFromHex
import app.codeg.android.core.model.FolderDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(
    onOpenFolder: (Int) -> Unit,
    viewModel: ProjectsViewModel = hiltViewModel(),
) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
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
                                imageVector = Icons.Rounded.Add,
                                contentDescription = stringResource(R.string.projects_add),
                                tint = colors.textSecondary,
                            )
                        }
                        DropdownMenu(
                            expanded = menuOpen,
                            onDismissRequest = { menuOpen = false },
                        ) {
                            if (importUi.supported) {
                                DropdownMenuItem(text = { Text(stringResource(R.string.imp_title)) },
                                    onClick = { menuOpen = false; showImport = true })
                            }
                            if (ui.groupsSupported) {
                                DropdownMenuItem(text = { Text(stringResource(R.string.fg_create)) },
                                    enabled = !ui.isBusy,
                                    onClick = { menuOpen = false; createGroup = true })
                            }
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.projects_open_folder)) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Rounded.CreateNewFolder,
                                        contentDescription = null,
                                        tint = colors.textSecondary,
                                    )
                                },
                                onClick = {
                                    menuOpen = false
                                    showBrowser = true
                                },
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.projects_clone_repo)) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Rounded.CloudDownload,
                                        contentDescription = null,
                                        tint = colors.textSecondary,
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
                            icon = Icons.Rounded.FolderOpen,
                            title = stringResource(R.string.projects_load_failed),
                            message = ui.error!!,
                            onRetry = viewModel::refresh,
                        )
                    }
                }
                ui.hasLoaded && ui.folders.isEmpty() && ui.groups.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        EmptyState(
                            icon = Icons.Rounded.FolderOpen,
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
                            if (ui.error != null) {
                                item(key = "refresh-error") {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(CodegTheme.dimens.radiusMd))
                                            .background(colors.danger.copy(alpha = 0.12f))
                                            .border(
                                                CodegTheme.dimens.hairlineWidth,
                                                colors.danger.copy(alpha = 0.35f),
                                                RoundedCornerShape(CodegTheme.dimens.radiusMd),
                                            )
                                            .padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Icon(
                                            Icons.Rounded.WarningAmber,
                                            contentDescription = null,
                                            tint = colors.danger,
                                            modifier = Modifier.size(16.dp),
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            text = ui.error!!,
                                            color = colors.danger,
                                            style = MaterialTheme.typography.bodySmall,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.weight(1f),
                                        )
                                        TextButton(onClick = viewModel::refresh) {
                                            Text(
                                                stringResource(R.string.common_retry),
                                                color = colors.danger,
                                            )
                                        }
                                    }
                                }
                            }

                            item(key = "folders-card") {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .webSurface(RoundedCornerShape(CodegTheme.dimens.radiusLg)),
                                ) {
                                    folderLayout(ui.folders, ui.groups).forEach { entry ->
                                        val group = entry.group
                                        if (group != null) {
                                            Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                                                TextButton(modifier = Modifier.weight(1f), onClick = {
                                                    collapsed = if (group.id in collapsed) collapsed - group.id else collapsed + group.id
                                                }) {
                                                    Box(Modifier.size(8.dp).background(folderTint(group.color), CircleShape))
                                                    Spacer(Modifier.width(8.dp))
                                                    Text((if (group.id in collapsed) "▸ " else "▾ ") + group.name)
                                                }
                                                if (ui.groupsSupported) {
                                                    TextButton(enabled = !ui.isBusy, onClick = { renameGroup = group }) { Text(stringResource(R.string.fg_rename)) }
                                                    TextButton(enabled = !ui.isBusy, onClick = { deleteGroup = group }) { Text(stringResource(R.string.fg_delete)) }
                                                }
                                            }
                                            if (group.id !in collapsed) {
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

    if (createGroup && ui.groupsSupported) GroupTextDialog(stringResource(R.string.fg_create), "", onDismiss = { createGroup = false }) { name ->
        viewModel.editGroup { it.createFolderGroup(name) }
    }
    renameGroup?.takeIf { ui.groupsSupported }?.let { group ->
        GroupTextDialog(stringResource(R.string.fg_rename), group.name, onDismiss = { renameGroup = null }) { name ->
            viewModel.editGroup { it.updateFolderGroup(group.id, name) }
        }
    }
    deleteGroup?.takeIf { ui.groupsSupported }?.let { group ->
        AlertDialog(onDismissRequest = { deleteGroup = null }, title = { Text(stringResource(R.string.fg_delete)) },
            text = { Text(stringResource(R.string.fg_delete_confirm, group.name)) },
            confirmButton = { TextButton(enabled = !ui.isBusy, onClick = {
                deleteGroup = null; viewModel.editGroup { it.deleteFolderGroup(group.id) }
            }) { Text(stringResource(R.string.fg_delete)) } },
            dismissButton = { TextButton(onClick = { deleteGroup = null }) { Text(stringResource(R.string.fg_cancel)) } })
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
 * One folder as a borderless grouped row: a colored [FolderBadge] tile, the name
 * with its full path + branch underneath, a trailing running-count pill or
 * relative last-opened time, then a drill-in chevron. The enclosing [webSurface]
 * card draws the surface; rows are split by inset hairlines.
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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        FolderBadge(color = tile, size = 40.dp)
        Box(Modifier.size(7.dp).background(tile, CircleShape))
        Column(Modifier.weight(1f)) {
            Text(
                text = folderDisplayName(folder),
                style = MaterialTheme.typography.bodyLarge,
                color = colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(top = 2.dp),
            ) {
                Text(
                    text = folder.path,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = colors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                )
                folder.gitBranch?.let { BranchPill(it) }
            }
        }
        if (running > 0) {
            RunningPill(running)
        } else {
            folder.lastOpenedAt?.let {
                Text(
                    text = RelativeTime.compact(it),
                    fontSize = 11.sp,
                    color = colors.textSecondary,
                )
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = colors.textSecondary,
            modifier = Modifier.size(18.dp),
        )
    }
}

/** Active-task indicator: a pulsing dot + count in an accent capsule. */
@Composable
private fun RunningPill(count: Int, modifier: Modifier = Modifier) {
    val colors = CodegTheme.colors
    Row(
        modifier = modifier
            .clip(PillShape)
            .background(colors.accentDim)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        LivePulse(dotSize = 7.dp)
        Text(
            text = "$count",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.accent,
        )
    }
}

/** Git branch indicator capsule. */
@Composable
fun BranchPill(branch: String, modifier: Modifier = Modifier) {
    val colors = CodegTheme.colors
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = modifier
            .clip(PillShape)
            .background(colors.web.secondary)
            .padding(horizontal = 7.dp, vertical = 2.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.AltRoute,
            contentDescription = null,
            tint = colors.textSecondary,
            modifier = Modifier.size(11.dp),
        )
        Text(
            text = branch,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

internal fun sortedFolders(folders: List<FolderDetail>): List<FolderDetail> =
    folders.sortedWith(compareBy({ it.sortOrder }, { it.name.lowercase() }))

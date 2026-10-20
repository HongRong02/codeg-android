package app.codeg.android.feature.server

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.datastore.ServerProfile
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerListScreen(
    servers: List<ServerProfile>,
    selectedId: String?,
    onSelect: (String) -> Unit,
    onDelete: (String) -> Unit,
    onAdd: () -> Unit,
    onEdit: (String) -> Unit,
    onBack: () -> Unit,
) {
    val colors = CodegTheme.colors
    var pendingDelete by remember { mutableStateOf<ServerProfile?>(null) }

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color.Transparent,
        topBar = {
            CodegTopBar(
                title = stringResource(R.string.servers_title),
                navigationIcon = {
                    CodegIconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = stringResource(R.string.common_cancel), tint = colors.textSecondary)
                    }
                },
                showDivider = true,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                containerColor = colors.accent,
                contentColor = colors.onAccent,
            ) {
                Icon(Icons.Rounded.Add, contentDescription = stringResource(R.string.server_add))
            }
        },
    ) { padding ->
        if (servers.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                EmptyState(
                    icon = Icons.Rounded.Dns,
                    title = stringResource(R.string.servers_title),
                    message = stringResource(R.string.onboarding_footnote),
                    actionLabel = stringResource(R.string.server_add),
                    onAction = onAdd,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(servers, key = { it.id }) { profile ->
                    ServerRow(
                        profile = profile,
                        selected = profile.id == selectedId,
                        onClick = {
                            onSelect(profile.id)
                            onBack()
                        },
                        onEdit = { onEdit(profile.id) },
                        onDelete = { pendingDelete = profile },
                    )
                }
            }
        }
    }

    val toDelete = pendingDelete
    if (toDelete != null) {
        CodegConfirmDialog(
            title = stringResource(R.string.server_delete),
            message = stringResource(R.string.server_delete),
            target = toDelete.name,
            confirmLabel = stringResource(R.string.server_delete),
            destructive = true,
            onConfirm = { onDelete(toDelete.id); pendingDelete = null },
            onDismiss = { pendingDelete = null },
        )
    }
}

@Composable
private fun ServerRow(
    profile: ServerProfile,
    selected: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    val colors = CodegTheme.colors
    var menuOpen by remember { mutableStateOf(false) }

    CodegListRow(
        title = profile.name,
        subtitle = profile.baseUrl,
        selected = selected,
        onClick = onClick,
        leading = { if (selected) Icon(Icons.Rounded.Check, contentDescription = null, tint = colors.accent) },
        trailing = {
            Box {
                CodegRowMoreButton(onClick = { menuOpen = true }, contentDescription = stringResource(R.string.servers_onboarding_actions))
                CodegDropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                    CodegMenuItem(text = { Text(stringResource(R.string.server_edit)) },
                        leadingIcon = { Icon(Icons.Rounded.Edit, contentDescription = null) },
                        onClick = { menuOpen = false; onEdit() })
                    CodegMenuItem(text = { Text(stringResource(R.string.server_delete)) },
                        variant = CodegMenuItemVariant.Destructive,
                        leadingIcon = { Icon(Icons.Rounded.Delete, contentDescription = null) },
                        onClick = { menuOpen = false; onDelete() })
                }
            }
        },
    )
}

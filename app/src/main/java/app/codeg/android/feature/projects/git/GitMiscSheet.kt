package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.GitCredentials
import app.codeg.android.core.network.CodegClient

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GitMiscSheet(client: CodegClient, path: String, folderId: Int, onDismiss: () -> Unit, onChanged: () -> Unit) {
    val model: GitMiscViewModel = viewModel(key = "git-misc-$folderId")
    val ui by model.ui.collectAsStateWithLifecycle()
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var base by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmation by remember { mutableStateOf<Pair<String, () -> Unit>?>(null) }
    LaunchedEffect(client, path) { model.run("load") { model.load(client, path) } }
    fun mutate(action: String, operation: suspend () -> Unit) = model.run(action) {
        operation()
        model.load(client, path)
        onChanged()
    }
    val toaster = LocalCodegToaster.current
    val failureTitle = stringResource(R.string.t56_operation_failed)
    val doneTitle = stringResource(R.string.t56_operation_completed)
    LaunchedEffect(ui.error, ui.success) {
        ui.error?.let { toaster.error(failureTitle, it) }
        if (ui.success) toaster.success(doneTitle)
    }
    CodegSheet(title = stringResource(R.string.git_misc_title), onDismiss = { if (!ui.busy) onDismiss() }) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(path, color = CodegTheme.colors.textSecondary)
            if (ui.busy) LoadingView(stringResource(R.string.common_loading))
            if (ui.remotes.isEmpty() && ui.worktrees.isEmpty()) ui.error?.let { Text(it, color = CodegTheme.colors.danger) }
            CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { model.run("load") { model.load(client, path) } }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_refresh)) }
            if (ui.notRepository) {
                if ("init" !in ui.hidden) CodegButton(onClick = { mutate("init") { client.gitInit(path) } }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_init)) }
            } else {
                CodegSectionHeader(title = stringResource(R.string.git_misc_remotes))
                ui.remotes.forEach { remote ->
                    CodegListRow(title = remote.name, subtitle = remote.url, onClick = { if (!ui.busy) { name = remote.name; url = remote.url } })
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { name = remote.name; url = remote.url }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_edit)) }
                        if ("fetch" !in ui.hidden) CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { model.fetch(client, path, remote.name) }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_fetch)) }
                        if ("remove" !in ui.hidden) CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { confirmation = remote.name to { mutate("remove") { client.gitRemoveRemote(path, remote.name) } } }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_remove)) }
                    }
                }
                CodegTextField(name, { name = it }, label = stringResource(R.string.git_misc_name))
                CodegTextField(url, { url = it }, label = stringResource(R.string.git_misc_url))
                val existing = ui.remotes.any { it.name == name.trim() }
                val remoteAction = if (existing) "url" else "add"
                if (remoteAction !in ui.hidden) CodegButton(onClick = {
                    mutate(remoteAction) { if (existing) client.gitSetRemoteUrl(path, name.trim(), url.trim()) else client.gitAddRemote(path, name.trim(), url.trim()) }
                }, enabled = !ui.busy && name.isNotBlank() && url.isNotBlank()) { Text(stringResource(if (existing) R.string.git_misc_save else R.string.git_misc_add)) }
                ui.authRemote?.let { remote ->
                    CodegSectionHeader(title = stringResource(R.string.git_misc_credentials, remote))
                    CodegTextField(username, { username = it }, label = stringResource(R.string.git_misc_username))
                    SecretField(password, { password = it }, label = stringResource(R.string.git_misc_password))
                    CodegButton(onClick = { model.fetch(client, path, remote, GitCredentials(username.trim(), password)); password = "" }, enabled = !ui.busy && password.isNotBlank()) { Text(stringResource(R.string.git_misc_fetch)) }
                }
                CodegSeparator()
                CodegSectionHeader(title = stringResource(R.string.git_misc_worktrees))
                ui.worktrees.forEach { tree ->
                    CodegListRow(title = tree.branch, subtitle = tree.path, onClick = {})
                    if ("worktree_remove" !in ui.hidden) CodegButton(size = CodegButtonSize.Sm, variant = CodegButtonVariant.Ghost, onClick = { confirmation = "${tree.branch}\n${tree.path}" to { mutate("worktree_remove") { client.gitRemoveWorktree(path, tree.branch, folderId) } } }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_remove)) }
                }
                if ("worktree_add" !in ui.hidden) {
                    CodegTextField(branch, { branch = it }, label = stringResource(R.string.git_misc_branch))
                    CodegTextField(destination, { destination = it }, label = stringResource(R.string.git_misc_destination))
                    CodegTextField(base, { base = it }, label = stringResource(R.string.git_misc_base))
                    CodegButton(onClick = { mutate("worktree_add") { client.gitWorktreeAdd(path, branch.trim(), destination.trim(), base.trim().ifEmpty { null }) } }, enabled = !ui.busy && branch.isNotBlank() && destination.isNotBlank()) { Text(stringResource(R.string.git_misc_add)) }
                }
            }
        }
    }
    confirmation?.let { (target, action) ->
        CodegConfirmDialog(title = stringResource(R.string.git_misc_remove),
            message = stringResource(R.string.git_misc_confirm, target), target = target,
            confirmLabel = stringResource(R.string.git_misc_remove), destructive = true,
            onConfirm = { confirmation = null; action() }, onDismiss = { confirmation = null })
    }
}

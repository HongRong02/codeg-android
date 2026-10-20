package app.codeg.android.feature.projects.git

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.component.CodegTopBar
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.GitCredentials
import app.codeg.android.core.network.CodegClient

@OptIn(ExperimentalMaterial3Api::class)
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
    ModalBottomSheet(contentWindowInsets = { WindowInsets.safeDrawing }, onDismissRequest = { if (!ui.busy) onDismiss() }, sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true), containerColor = CodegTheme.colors.bgElevated) {
        Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CodegTopBar(title = stringResource(R.string.git_misc_title), showDivider = true)
            Text(path, color = CodegTheme.colors.textSecondary)
            if (ui.busy) LinearProgressIndicator(Modifier.fillMaxWidth())
            ui.error?.let { Text(it, color = CodegTheme.colors.danger) }
            if (ui.success) Text(stringResource(R.string.git_misc_success), color = CodegTheme.colors.textSecondary)
            TextButton(onClick = { model.run("load") { model.load(client, path) } }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_refresh)) }
            if (ui.notRepository) {
                if ("init" !in ui.hidden) Button(onClick = { mutate("init") { client.gitInit(path) } }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_init)) }
            } else {
                Text(stringResource(R.string.git_misc_remotes), color = CodegTheme.colors.textPrimary)
                ui.remotes.forEach { remote ->
                    Text(remote.name + " · " + remote.url, color = CodegTheme.colors.textPrimary)
                    Row {
                        TextButton(onClick = { name = remote.name; url = remote.url }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_edit)) }
                        if ("fetch" !in ui.hidden) TextButton(onClick = { model.fetch(client, path, remote.name) }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_fetch)) }
                        if ("remove" !in ui.hidden) TextButton(onClick = { confirmation = remote.name to { mutate("remove") { client.gitRemoveRemote(path, remote.name) } } }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_remove)) }
                    }
                }
                CodegTextField(name, { name = it }, label = stringResource(R.string.git_misc_name))
                CodegTextField(url, { url = it }, label = stringResource(R.string.git_misc_url))
                val existing = ui.remotes.any { it.name == name.trim() }
                val remoteAction = if (existing) "url" else "add"
                if (remoteAction !in ui.hidden) Button(onClick = {
                    mutate(remoteAction) { if (existing) client.gitSetRemoteUrl(path, name.trim(), url.trim()) else client.gitAddRemote(path, name.trim(), url.trim()) }
                }, enabled = !ui.busy && name.isNotBlank() && url.isNotBlank()) { Text(stringResource(if (existing) R.string.git_misc_save else R.string.git_misc_add)) }
                ui.authRemote?.let { remote ->
                    Text(stringResource(R.string.git_misc_credentials, remote))
                    CodegTextField(username, { username = it }, label = stringResource(R.string.git_misc_username))
                    OutlinedTextField(password, { password = it }, label = { Text(stringResource(R.string.git_misc_password)) }, visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
                    Button(onClick = { model.fetch(client, path, remote, GitCredentials(username.trim(), password)); password = "" }, enabled = !ui.busy && password.isNotBlank()) { Text(stringResource(R.string.git_misc_fetch)) }
                }
                HorizontalDivider()
                Text(stringResource(R.string.git_misc_worktrees), color = CodegTheme.colors.textPrimary)
                ui.worktrees.forEach { tree ->
                    Text(tree.branch + " · " + tree.path, color = CodegTheme.colors.textPrimary)
                    if ("worktree_remove" !in ui.hidden) TextButton(onClick = { confirmation = "${tree.branch}\n${tree.path}" to { mutate("worktree_remove") { client.gitRemoveWorktree(path, tree.branch, folderId) } } }, enabled = !ui.busy) { Text(stringResource(R.string.git_misc_remove)) }
                }
                if ("worktree_add" !in ui.hidden) {
                    CodegTextField(branch, { branch = it }, label = stringResource(R.string.git_misc_branch))
                    CodegTextField(destination, { destination = it }, label = stringResource(R.string.git_misc_destination))
                    CodegTextField(base, { base = it }, label = stringResource(R.string.git_misc_base))
                    Button(onClick = { mutate("worktree_add") { client.gitWorktreeAdd(path, branch.trim(), destination.trim(), base.trim().ifEmpty { null }) } }, enabled = !ui.busy && branch.isNotBlank() && destination.isNotBlank()) { Text(stringResource(R.string.git_misc_add)) }
                }
            }
        }
    }
    confirmation?.let { (target, action) ->
        AlertDialog(onDismissRequest = { confirmation = null }, title = { Text(stringResource(R.string.git_misc_remove)) }, text = { Text(stringResource(R.string.git_misc_confirm, target)) }, confirmButton = { TextButton(onClick = { confirmation = null; action() }) { Text(stringResource(R.string.git_misc_remove)) } }, dismissButton = { TextButton(onClick = { confirmation = null }) { Text(stringResource(R.string.common_cancel)) } })
    }
}

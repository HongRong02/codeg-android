package app.codeg.android.feature.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegButtonSize
import androidx.compose.material3.Icon
import app.codeg.android.core.designsystem.component.CodegSheet
import app.codeg.android.core.designsystem.component.CodegListRow
import app.codeg.android.core.designsystem.component.CodegSectionHeader
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.theme.CodegTheme

/**
 * Branch picker: lists the repo's local + remote branches (current marked) for
 * checkout, with an inline "new branch" field. Mirrors the iOS branch picker.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BranchPickerSheet(viewModel: ProjectDetailViewModel, onDismiss: () -> Unit) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors
    var loading by remember { mutableStateOf(true) }
    var locals by remember { mutableStateOf<List<String>>(emptyList()) }
    var remotes by remember { mutableStateOf<List<String>>(emptyList()) }
    var newBranch by remember { mutableStateOf("") }
    var busy by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val list = viewModel.loadBranches()
        locals = list.local
        remotes = list.remote.filterNot { it.endsWith("/HEAD") }
        loading = false
    }

    fun doCheckout(branch: String) {
        busy = true; error = null
        viewModel.checkout(branch) { err -> busy = false; if (err == null) onDismiss() else error = err }
    }

    CodegSheet(title = stringResource(R.string.branch_switch_branch), onDismiss = onDismiss) {
        Column(Modifier.fillMaxWidth().heightIn(max = 520.dp).padding(horizontal = 16.dp).padding(bottom = 28.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // TODO(T52): CodegToaster for checkout/create failures.
            error?.let { Text(it, fontSize = 12.sp, color = colors.danger) }

            // New branch.
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.weight(1f)) { CodegTextField(newBranch, { newBranch = it }, label = stringResource(R.string.branch_new_branch), mono = true) }
                CodegButton(variant = CodegButtonVariant.Secondary, size = CodegButtonSize.Icon,
                    onClick = {
                        busy = true; error = null
                        viewModel.createBranch(newBranch) { err -> busy = false; if (err == null) onDismiss() else error = err }
                    },
                    enabled = newBranch.isNotBlank() && !busy,
                    modifier = Modifier.size(48.dp),
                ) { Icon(Icons.Rounded.Add, stringResource(R.string.branch_create_branch)) }
            }

            if (loading) {
                Box(Modifier.fillMaxWidth().padding(30.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = colors.accent) }
            } else {
                LazyColumn(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    if (locals.isNotEmpty()) item("lh") { SectionLabel(stringResource(R.string.branch_local)) }
                    items(locals, key = { "l-$it" }) { b -> BranchRow(b, current = b == ui.branch, enabled = !busy) { doCheckout(b) } }
                    if (remotes.isNotEmpty()) item("rh") { SectionLabel(stringResource(R.string.branch_remote)) }
                    items(remotes, key = { "r-$it" }) { b -> BranchRow(b, current = false, enabled = !busy) { doCheckout(b) } }
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    CodegSectionHeader(title = text)
}

@Composable
private fun BranchRow(branch: String, current: Boolean, enabled: Boolean, onClick: () -> Unit) {
    val colors = CodegTheme.colors
    CodegListRow(
        title = branch,
        selected = current,
        onClick = { if (enabled && !current) onClick() },
        trailing = if (current) {
            { Icon(Icons.Rounded.Check, null, tint = colors.accent, modifier = Modifier.size(18.dp)) }
        } else null,
    )
}

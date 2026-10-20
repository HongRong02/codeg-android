package app.codeg.android.feature.server

import app.codeg.android.core.designsystem.icon.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerEditorScreen(
    onDone: () -> Unit,
    viewModel: ServerEditorViewModel = hiltViewModel(),
) {
    val colors = CodegTheme.colors
    val toaster = LocalCodegToaster.current
    val test = viewModel.test
    val testMessage = when (test) {
        is TestState.Success -> stringResource(R.string.server_connection_ok, test.version)
        is TestState.Failure -> stringResource(R.string.server_connection_failed, test.message)
        else -> null
    }
    LaunchedEffect(test) {
        when (test) {
            is TestState.Success -> toaster.success(requireNotNull(testMessage))
            is TestState.Failure -> toaster.error(requireNotNull(testMessage))
            else -> Unit
        }
    }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CodegTopBar(
                title = stringResource(if (viewModel.isEditing) R.string.server_edit else R.string.server_add),
                navigationIcon = {
                    CodegIconButton(onClick = onDone) {
                        CodegIcon(Lucide.ArrowLeft, contentDescription = stringResource(R.string.common_cancel), tint = colors.textSecondary)
                    }
                },
                showDivider = true,
            )
        },
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding).imePadding(), contentAlignment = Alignment.TopCenter) {
            CodegCard(Modifier.widthIn(max = 448.dp).fillMaxWidth()
                .verticalScroll(rememberScrollState()).padding(16.dp)) {
                CodegCardContent {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        CodegSectionHeader(stringResource(R.string.server_section_connection))
                        CodegTextField(
                            value = viewModel.name,
                            onValueChange = viewModel::onNameChange,
                            label = stringResource(R.string.server_name),
                        )
                        CodegTextField(
                            value = viewModel.url,
                            onValueChange = viewModel::onUrlChange,
                            label = stringResource(R.string.server_url),
                            placeholder = stringResource(R.string.server_url_placeholder),
                            keyboardType = KeyboardType.Uri,
                            mono = true,
                        )

                        CodegSeparator()
                        CodegSectionHeader(stringResource(R.string.server_section_auth))
                        SecretField(
                            value = viewModel.token,
                            onValueChange = viewModel::onTokenChange,
                            label = stringResource(R.string.server_token),
                        )

                        PrimaryButton(
                            text = stringResource(R.string.server_test_connection),
                            onClick = viewModel::testConnection,
                            loading = viewModel.test is TestState.Testing,
                            enabled = viewModel.url.isNotBlank() && viewModel.token.isNotBlank(),
                        )
                        PrimaryButton(
                            text = stringResource(R.string.server_save),
                            onClick = { viewModel.save(onDone) },
                            enabled = viewModel.canSave,
                        )


                    }
                }
            }
        }
    }
}

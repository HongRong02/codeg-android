package app.codeg.android.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.PillShape
import app.codeg.android.core.designsystem.theme.withOpacity
import app.codeg.android.feature.sessions.SessionRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onOpenConversation: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val ui by viewModel.ui.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val recent by viewModel.recent.collectAsStateWithLifecycle()
    val colors = CodegTheme.colors
    val web = colors.web

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CodegTopBar(
                title = stringResource(R.string.tab_search),
                showDivider = true,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .imePadding()
                .fillMaxSize(),
        ) {
            // TODO(T53): CodegInputGroup; retain IME Search, clear action and searching indicator.
            // Search field — a pill-shaped input matching web CommandInput / Input:
            // --input background and border, --ring border on focus, with IME Search action,
            // ghost clear button, and inline progress indicator.
            OutlinedTextField(
                value = query,
                onValueChange = viewModel::onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_placeholder),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = null,
                        tint = colors.textSecondary,
                        modifier = Modifier.size(18.dp),
                    )
                },
                trailingIcon = {
                    when {
                        ui.searching -> CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = colors.accent,
                        )
                        query.isNotEmpty() -> CodegIconButton(
                            onClick = { viewModel.onQueryChange("") },
                            visualSize = 28.dp,
                        ) {
                            Icon(
                                Icons.Rounded.Close,
                                contentDescription = stringResource(R.string.search_clear),
                                tint = colors.textSecondary,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    }
                },
                singleLine = true,
                shape = PillShape,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = colors.textPrimary),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.submit() }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = web.input.withOpacity(0.30f),
                    unfocusedContainerColor = web.input.withOpacity(0.30f),
                    focusedBorderColor = web.ring,
                    unfocusedBorderColor = web.input,
                    cursorColor = colors.accent,
                    focusedTextColor = colors.textPrimary,
                    unfocusedTextColor = colors.textPrimary,
                    focusedLeadingIconColor = colors.textSecondary,
                    unfocusedLeadingIconColor = colors.textSecondary,
                    focusedTrailingIconColor = colors.textSecondary,
                    unfocusedTrailingIconColor = colors.textSecondary,
                    focusedPlaceholderColor = colors.textSecondary,
                    unfocusedPlaceholderColor = colors.textSecondary,
                ),
            )

            Box(Modifier.fillMaxSize()) {
                when {
                    query.isBlank() && recent.isNotEmpty() ->
                        RecentList(
                            recent = recent,
                            onUse = viewModel::useRecent,
                            onClear = viewModel::clearRecent,
                        )
                    query.isBlank() ->
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            EmptyState(
                                icon = Icons.Rounded.Search,
                                title = stringResource(R.string.search_title),
                                message = stringResource(R.string.search_hint),
                            )
                        }
                    ui.error != null ->
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            InlineError(
                                icon = Icons.Rounded.ErrorOutline,
                                title = stringResource(R.string.search_failed),
                                message = ui.error!!,
                                onRetry = viewModel::retry,
                                retryLabel = stringResource(R.string.common_retry),
                            )
                        }
                    ui.searching && ui.results.isEmpty() ->
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LoadingView(message = stringResource(R.string.common_loading))
                        }
                    ui.results.isEmpty() && !ui.searching ->
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            EmptyState(
                                icon = Icons.Rounded.Search,
                                title = stringResource(R.string.search_no_results),
                                message = stringResource(R.string.search_no_results_for, ui.query),
                            )
                        }
                    else -> LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    ) {
                        items(ui.results, key = { it.id }) { c ->
                            SessionRow(
                                conversation = c,
                                onClick = {
                                    viewModel.submit()
                                    onOpenConversation(c.id)
                                },
                                modifier = Modifier.animateItem(),
                                folderName = ui.folderNames[c.folderId],
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecentList(
    recent: List<String>,
    onUse: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = CodegTheme.colors
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        item {
            CodegSectionHeader(title = stringResource(R.string.search_recent), action = {
                CodegButton(onClick = onClear, variant = CodegButtonVariant.Ghost, size = CodegButtonSize.Sm) {
                    Text(stringResource(R.string.search_clear))
                }
            })
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .webSurface(RoundedCornerShape(CodegTheme.dimens.radiusMd)),
            ) {
                recent.forEachIndexed { index, term ->
                    CodegListRow(title = term, onClick = { onUse(term) }, leading = {
                        Icon(Icons.Rounded.History, contentDescription = null, modifier = Modifier.size(16.dp))
                    })
                    if (index < recent.lastIndex) {
                        CodegSeparator(modifier = Modifier.padding(start = 40.dp))
                    }
                }
            }
        }
    }
}

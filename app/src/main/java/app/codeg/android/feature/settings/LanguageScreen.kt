package app.codeg.android.feature.settings

import app.codeg.android.core.designsystem.component.CodegCard
import app.codeg.android.core.designsystem.component.CodegCardContent
import app.codeg.android.core.designsystem.component.CodegCardHeader
import app.codeg.android.core.designsystem.component.CodegRadioGroup
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.common.AppLanguage
import app.codeg.android.core.common.LocaleManager

/**
 * App display language (System / English / 中文). Purely device-local; persisted
 * by [LocaleManager] and applied by recreating the activity (which re-runs
 * `attachBaseContext`). Mirrors iOS `LanguageSettingsView`.
 */
@Composable
fun LanguageContent() {
    val context = LocalContext.current
    var selected by remember { mutableStateOf(LocaleManager.current(context)) }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CodegCard {
                CodegCardHeader(title = stringResource(R.string.settings_leaf_language),
                    description = stringResource(R.string.language_interface_description))
                CodegCardContent {
                    CodegRadioGroup(options = AppLanguage.entries.map { it.title }, value = selected.title,
                        onSelect = { title ->
                            val lang = AppLanguage.entries.first { it.title == title }
                            if (lang != selected) {
                                LocaleManager.set(context, lang)
                                selected = lang
                                context.findActivity()?.recreate()
                            }
                    })
                }
            }
        }
}

/** Walk the ContextWrapper chain to find the host Activity (for `recreate()`). */
private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

package app.codeg.android.feature.devgallery

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.BuildConfig
import app.codeg.android.R
import app.codeg.android.core.datastore.ThemeMode
import app.codeg.android.core.designsystem.component.*
import app.codeg.android.core.designsystem.icon.*
import app.codeg.android.core.designsystem.theme.CodegTheme

/** Debug-only component harness; it does not persist app appearance settings. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevGalleryScreen(onBack: () -> Unit) {
    if (!BuildConfig.DEBUG) return
    var dark by rememberSaveable { mutableStateOf(false) }
    var group by rememberSaveable { mutableStateOf<Int?>(null) }
    var sample by rememberSaveable { mutableStateOf<String?>(null) }
    val back = {
        when {
            sample != null -> sample = null
            group != null -> group = null
            else -> onBack()
        }
    }
    BackHandler(onBack = back)
    CodegTheme(themeMode = if (dark) ThemeMode.DARK else ThemeMode.LIGHT) {
        Column(Modifier.fillMaxSize().background(CodegTheme.colors.web.background).navigationBarsPadding()) {
            CodegTopBar(
                title = stringResource(R.string.dev_gallery_title),
                navigationIcon = {
                    CodegIconButton(onClick = back) {
                        CodegIcon(Lucide.ArrowLeft, stringResource(R.string.dev_gallery_back))
                    }
                },
                actions = {
                    CodegIconButton(onClick = { dark = !dark }) {
                        CodegIcon(if (dark) Lucide.Sun else Lucide.Moon,
                            stringResource(if (dark) R.string.dev_gallery_light else R.string.dev_gallery_dark))
                    }
                },
            )
            key(group, sample) {
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    if (sample != null) {
                        // Scroll containers and full-size hosts must receive a bounded viewport.
                        val ownsScroll = sample in setOf(
                            "T43Gallery", "T44Gallery", "T45Gallery", "T51Gallery",
                            "T52Gallery", "T53ImagePreviewGallery", "T54Gallery",
                        )
                        val modifier = if (ownsScroll) Modifier.fillMaxSize() else
                            Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                        Box(modifier.padding(16.dp)) { GallerySample(sample!!) }
                    } else {
                        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (group == null) {
                                CodegButton(onClick = { group = 0 }, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.dev_gallery_t43)) }
                                CodegButton(onClick = { group = 1 }, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.dev_gallery_t44)) }
                                CodegButton(onClick = { group = 2 }, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.dev_gallery_t45)) }
                                CodegButton(onClick = { group = 3 }, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.dev_gallery_t46)) }
                                CodegButton(onClick = { group = 4 }, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.dev_gallery_t51)) }
                                CodegButton(onClick = { group = 5 }, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.dev_gallery_t52)) }
                                CodegButton(onClick = { group = 6 }, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.dev_gallery_t53)) }
                                CodegButton(onClick = { group = 7 }, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.dev_gallery_t54)) }
                                CodegButton(onClick = { group = 8 }, variant = CodegButtonVariant.Outline) { Text(stringResource(R.string.dev_gallery_t60)) }
                            } else {
                                val samples = when (group) {
                                    0 -> listOf(R.string.dev_gallery_sample_t43gallery to "T43Gallery")
                                    1 -> listOf(R.string.dev_gallery_sample_t44gallery to "T44Gallery", R.string.dev_gallery_sample_t44cardsgallery to "T44CardsGallery")
                                    2 -> listOf(R.string.dev_gallery_sample_t45gallery to "T45Gallery")
                                    3 -> listOf(R.string.dev_gallery_sample_t46gallery to "T46Gallery", R.string.dev_gallery_sample_t46tabsgallery to "T46TabsGallery", R.string.dev_gallery_sample_t46badgesgallery to "T46BadgesGallery", R.string.dev_gallery_sample_t46rowsgallery to "T46RowsGallery", R.string.dev_gallery_sample_t46feedbackgallery to "T46FeedbackGallery")
                                    4 -> listOf(R.string.dev_gallery_sample_t51gallery to "T51Gallery")
                                    5 -> listOf(R.string.dev_gallery_sample_t52gallery to "T52Gallery")
                                    6 -> listOf(R.string.dev_gallery_sample_t53collapsiblegallery to "T53CollapsibleGallery", R.string.dev_gallery_sample_t53avatargallery to "T53AvatarGallery", R.string.dev_gallery_sample_t53inputgroupgallery to "T53InputGroupGallery", R.string.dev_gallery_sample_t53imagepreviewgallery to "T53ImagePreviewGallery", R.string.dev_gallery_sample_t53commandgallery to "T53CommandGallery", R.string.dev_gallery_sample_t53folderbadgegallery to "T53FolderBadgeGallery")
                                    7 -> listOf(R.string.dev_gallery_sample_t54gallery to "T54Gallery")
                                    // T60 extends these existing galleries in its own component files.
                                    else -> listOf(R.string.dev_gallery_sample_t44gallery to "T44Gallery", R.string.dev_gallery_sample_t45gallery to "T45Gallery", R.string.dev_gallery_sample_t46gallery to "T46Gallery")
                                }
                                if (group == 8) Text(stringResource(R.string.dev_gallery_t60_note))
                                samples.forEach { (label, name) ->
                                    CodegButton(onClick = { sample = name }, variant = CodegButtonVariant.Outline) { Text(stringResource(label)) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GallerySample(name: String) {
    when (name) {
        "T43Gallery" -> T43Gallery()
        "T44Gallery" -> T44Gallery()
        "T44CardsGallery" -> T44CardsGallery()
        "T45Gallery" -> T45Gallery()
        "T46Gallery" -> T46Gallery()
        "T46TabsGallery" -> T46TabsGallery()
        "T46BadgesGallery" -> T46BadgesGallery()
        "T46RowsGallery" -> T46RowsGallery()
        "T46FeedbackGallery" -> T46FeedbackGallery()
        "T51Gallery" -> T51Gallery()
        "T52Gallery" -> CodegToasterHost { T52Gallery() }
        "T53CollapsibleGallery" -> T53CollapsibleGallery()
        "T53AvatarGallery" -> T53AvatarGallery()
        "T53InputGroupGallery" -> T53InputGroupGallery()
        "T53ImagePreviewGallery" -> T53ImagePreviewGallery()
        "T53CommandGallery" -> T53CommandGallery()
        "T53FolderBadgeGallery" -> T53FolderBadgeGallery()
        "T54Gallery" -> T54Gallery()
    }
}

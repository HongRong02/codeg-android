package app.codeg.android.feature.sessiondetail.interactive

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.codeg.android.R
import app.codeg.android.core.designsystem.component.CodegBadge
import app.codeg.android.core.designsystem.component.CodegBadgeVariant
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegCard
import app.codeg.android.core.designsystem.component.CodegCheckbox
import app.codeg.android.core.designsystem.component.CodegRadioGroup
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.model.QuestionAnswer
import app.codeg.android.core.model.QuestionAnswerItem
import app.codeg.android.core.model.QuestionSpec

private const val OTHER_KEY = "__codeg_other__"

/** A multiple-choice / free-text question card. Port of iOS `AskQuestionCard`. */
@Composable
fun AskQuestionCard(
    questions: List<QuestionSpec>,
    onSubmit: (QuestionAnswer) -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = CodegTheme.colors
    val selections = remember(questions) { mutableStateMapOf<String, Set<String>>() }
    val otherText = remember(questions) { mutableStateMapOf<String, String>() }
    var submitting by remember(questions) { mutableStateOf(false) }

    fun answered(q: QuestionSpec): Boolean {
        val sel = selections[q.id].orEmpty()
        val hasReal = sel.any { it != OTHER_KEY }
        val hasOther = sel.contains(OTHER_KEY) && otherText[q.id]?.isNotBlank() == true
        return hasReal || hasOther
    }
    val canSubmit = questions.all { answered(it) } && !submitting

    CodegCard(modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Rounded.Forum, contentDescription = null, tint = colors.accent, modifier = Modifier.size(20.dp))
                Column(Modifier.weight(1f)) {
                    Text(stringResource(R.string.ask_needs_input_title), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = colors.textPrimary)
                    Text(stringResource(R.string.ask_needs_input_subtitle), fontSize = 12.sp, color = colors.textSecondary)
                }
            }

            Column(
                Modifier.heightIn(max = 360.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                for (q in questions) {
                    QuestionBlock(
                        question = q,
                        showHeader = questions.size > 1,
                        selected = selections[q.id].orEmpty(),
                        otherText = otherText[q.id].orEmpty(),
                        onToggle = { label ->
                            val cur = selections[q.id].orEmpty()
                            selections[q.id] = if (q.multiSelect) {
                                if (label in cur) cur - label else cur + label
                            } else {
                                if (label in cur) emptySet() else setOf(label)
                            }
                        },
                        onOtherText = { otherText[q.id] = it },
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                CodegButton(variant = CodegButtonVariant.Ghost, onClick = onSkip, enabled = !submitting) {
                    Text(stringResource(R.string.ask_skip), color = colors.textSecondary)
                }
                Box(Modifier.weight(1f))
                CodegButton(
                    onClick = {
                        submitting = true
                        onSubmit(buildAnswer(questions, selections, otherText))
                    },
                    enabled = canSubmit,
                ) {
                    if (submitting) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = colors.onAccent)
                    } else {
                        Text(stringResource(R.string.ask_submit), fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuestionBlock(
    question: QuestionSpec,
    showHeader: Boolean,
    selected: Set<String>,
    otherText: String,
    onToggle: (String) -> Unit,
    onOtherText: (String) -> Unit,
) {
    val colors = CodegTheme.colors
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CodegBadge(
                text = if (question.multiSelect) stringResource(R.string.ask_badge_multiple) else stringResource(R.string.ask_badge_single),
                variant = CodegBadgeVariant.Secondary,
            )
            if (showHeader && question.header.isNotEmpty()) {
                Text(question.header, fontSize = 12.sp, color = colors.web.mutedForeground)
            }
        }
        Text(question.question, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = colors.textPrimary)

        for (option in question.options) {
            OptionRow(
                label = option.label,
                description = option.description,
                checked = option.label in selected,
                multi = question.multiSelect,
                onClick = { onToggle(option.label) },
            )
        }
        // "Other" free-text row.
        OptionRow(
            label = stringResource(R.string.ask_other),
            description = "",
            checked = OTHER_KEY in selected,
            multi = question.multiSelect,
            onClick = { onToggle(OTHER_KEY) },
        )
        if (OTHER_KEY in selected) {
            CodegTextField(value = otherText, onValueChange = onOtherText,
                label = stringResource(R.string.ask_other), placeholder = stringResource(R.string.ask_other_placeholder), singleLine = false)
        }
    }
}

@Composable
private fun OptionRow(label: String, description: String, checked: Boolean, multi: Boolean, onClick: () -> Unit) {
    val colors = CodegTheme.colors
    Column {
        if (multi) {
            CodegCheckbox(checked = checked, onCheckedChange = { onClick() }, label = label,
                modifier = Modifier.fillMaxWidth())
        } else {
            CodegRadioGroup(options = listOf(label), value = if (checked) label else "", onSelect = { onClick() })
        }
        if (description.isNotEmpty()) Text(description, fontSize = 12.sp, color = colors.web.mutedForeground,
            modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(start = 24.dp, bottom = 8.dp))
    }
}

private fun buildAnswer(
    questions: List<QuestionSpec>,
    selections: Map<String, Set<String>>,
    otherText: Map<String, String>,
): QuestionAnswer {
    val items = questions.map { q ->
        val sel = selections[q.id].orEmpty()
        val labels = sel.filter { it != OTHER_KEY }.toMutableList()
        if (OTHER_KEY in sel) otherText[q.id]?.takeIf { it.isNotBlank() }?.let { labels.add(it.trim()) }
        QuestionAnswerItem(questionId = q.id, labels = labels)
    }
    return QuestionAnswer(answers = items, declined = false)
}

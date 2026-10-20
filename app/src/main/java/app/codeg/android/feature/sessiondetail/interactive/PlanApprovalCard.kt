package app.codeg.android.feature.sessiondetail.interactive

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import app.codeg.android.core.designsystem.component.CodegButton
import app.codeg.android.core.designsystem.component.CodegButtonVariant
import app.codeg.android.core.designsystem.component.CodegCard
import app.codeg.android.core.designsystem.component.CodegSpinner
import app.codeg.android.core.designsystem.component.CodegTextField
import app.codeg.android.core.designsystem.icon.*
import app.codeg.android.core.designsystem.markdown.MarkdownContent
import app.codeg.android.core.designsystem.theme.CodegShapes
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.withOpacity
import app.codeg.android.core.model.PlanApprovalDecision

/**
 * The inline card for Grok's native `exit_plan_mode`. When the agent finishes planning
 * it BLOCKS on the user's decision, so this is pinned above the compose bar with the
 * plan and Grok's own three outcomes (mirroring its TUI approval bar): approve and
 * build, request changes, or abandon the plan.
 *
 * Distinct from [PermissionRequestCard], which handles Claude's ExitPlanMode — that
 * arrives as an ordinary permission with an option list, while Grok's is its own
 * request with a fixed set of outcomes plus freeform revision notes.
 *
 * Follows [AskQuestionCard]'s in-flight pattern: on success the backend's
 * `plan_approval_resolved` clears the pending state and unmounts this card, so the
 * controls stay disabled rather than flashing back on.
 */
@Composable
fun PlanApprovalCard(
    planMarkdown: String,
    onAnswer: (PlanApprovalDecision, String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = CodegTheme.colors
    val plan = planMarkdown.trim()
    var submitting by remember(planMarkdown) { mutableStateOf(false) }
    var changesOpen by remember(planMarkdown) { mutableStateOf(false) }
    var feedback by remember(planMarkdown) { mutableStateOf("") }
    val notes = feedback.trim()

    fun submit(decision: PlanApprovalDecision, text: String?) {
        if (submitting) return
        submitting = true
        onAnswer(decision, text)
    }

    CodegCard(modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Lucide.ListTodo, contentDescription = null, tint = colors.accent, modifier = Modifier.size(16.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.plan_approval_title),
                        fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = colors.textPrimary,
                    )
                    Text(stringResource(R.string.plan_approval_subtitle), fontSize = 12.sp, color = colors.textSecondary)
                }
            }

            // An empty plan still opens the approval surface (the turn is blocked either
            // way), so it gets an explicit notice instead of a blank card.
            if (plan.isEmpty()) {
                Text(stringResource(R.string.plan_approval_empty), fontSize = 13.sp, color = colors.web.mutedForeground)
            } else {
                Column(Modifier.fillMaxWidth().heightIn(max = 320.dp).verticalScroll(rememberScrollState())
                    .clip(CodegShapes.extraSmall)
                    .background(colors.web.muted.withOpacity(0.3f))
                    .border(1.dp, colors.web.border.withOpacity(0.6f), CodegShapes.extraSmall)
                    .padding(12.dp)) {
                    MarkdownContent(plan)
                }
            }

            AnimatedVisibility(changesOpen) {
                CodegTextField(value = feedback, onValueChange = { if (!submitting) feedback = it },
                    label = stringResource(R.string.plan_approval_request_changes),
                    placeholder = stringResource(R.string.plan_approval_notes_placeholder), singleLine = false, enabled = !submitting, minLines = 2, maxLines = 6)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                CodegButton(variant = CodegButtonVariant.Outline,
                    onClick = {
                        if (changesOpen) submit(PlanApprovalDecision.REQUEST_CHANGES, notes) else changesOpen = true
                    },
                    // Opening the notes field is free; sending them needs text.
                    enabled = !submitting && (!changesOpen || notes.isNotEmpty()),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        stringResource(if (changesOpen) R.string.plan_approval_send_notes else R.string.plan_approval_request_changes),
                        color = colors.textPrimary,
                    )
                }
                CodegButton(
                    onClick = { submit(PlanApprovalDecision.APPROVE, null) },
                    enabled = !submitting,

                    modifier = Modifier.weight(1f),
                ) {
                    if (submitting) {
                        CodegSpinner(modifier = Modifier.size(16.dp))
                    } else {
                        Text(stringResource(R.string.plan_approval_approve), fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            CodegButton(variant = CodegButtonVariant.Ghost,
                onClick = { submit(PlanApprovalDecision.ABANDON, null) },
                enabled = !submitting,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(stringResource(R.string.plan_approval_abandon), color = colors.textSecondary, fontSize = 12.sp)
            }
        }
    }
}

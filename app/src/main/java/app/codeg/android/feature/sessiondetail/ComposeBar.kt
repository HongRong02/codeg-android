package app.codeg.android.feature.sessiondetail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.codeg.android.R
import app.codeg.android.core.designsystem.icon.*
import app.codeg.android.core.designsystem.theme.CodegTheme
import app.codeg.android.core.designsystem.theme.withOpacity

/**
 * The prompt composer pinned to the bottom of the session detail screen, after
 * the web composer: one rounded-xl box with a `foreground/20` border (`--ring`
 * plus a 3dp `ring/50` halo while focused) holding the multiline editor on top
 * and an action row below — ghost insert ("+") and attach buttons on the left,
 * the 32dp primary send button on the right, which turns into the web's
 * destructive stop button while a turn is running. [attachments] renders inside
 * the box above the editor, where the web shows image thumbnails.
 */
@Composable
fun ComposeBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    isInFlight: Boolean,
    onStop: () -> Unit,
    modifier: Modifier = Modifier,
    onPlus: (() -> Unit)? = null,
    onAttach: (() -> Unit)? = null,
    canSendOverride: Boolean = false,
    attachments: (@Composable ColumnScope.() -> Unit)? = null,
) {
    // CodegInputGroup is single-line only; retain multiline composer and attachment slots.
    val colors = CodegTheme.colors
    val web = colors.web
    val canSend = (text.isNotBlank() || canSendOverride) && !isInFlight
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val box = RoundedCornerShape(CodegTheme.dimens.radiusMd)
    val halo = RoundedCornerShape(CodegTheme.dimens.radiusMd + 3.dp)

    Box(
        modifier
            .fillMaxWidth()
            .background(colors.bg)
            .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 10.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                // The web's `focus-within:ring-[3px] ring-ring/50`: a soft halo around the box.
                .then(if (focused) Modifier.border(3.dp, web.ring.withOpacity(0.5f), halo) else Modifier)
                .padding(3.dp)
                .border(1.dp, if (focused) web.ring else colors.textPrimary.copy(alpha = 0.20f), box)
                .background(web.background, box),
        ) {
            attachments?.invoke(this)
            Box(
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                if (text.isEmpty()) {
                    Text(
                        stringResource(R.string.compose_message_placeholder),
                        color = colors.textSecondary,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                BasicTextField(
                    value = text,
                    onValueChange = onTextChange,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = colors.textPrimary),
                    cursorBrush = SolidColor(colors.textPrimary),
                    maxLines = 8,
                    interactionSource = interaction,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Row(
                Modifier.fillMaxWidth().padding(start = 4.dp, end = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                if (onPlus != null) {
                    GhostIconButton(onPlus) {
                        Icon(Lucide.Plus, contentDescription = stringResource(R.string.compose_insert), modifier = Modifier.size(16.dp))
                    }
                }
                if (onAttach != null) {
                    GhostIconButton(onAttach) {
                        Icon(Lucide.Image, contentDescription = stringResource(R.string.compose_attach_image), modifier = Modifier.size(16.dp))
                    }
                }
                Box(Modifier.weight(1f))
                SendStopButton(
                    isInFlight = isInFlight,
                    enabled = canSend || isInFlight,
                    onClick = { if (isInFlight) onStop() else onSend() },
                )
            }
        }
    }
}

/** The web `Button` ghost/icon variant: no fill, muted glyph. */
@Composable
private fun GhostIconButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp),
        colors = IconButtonDefaults.iconButtonColors(contentColor = CodegTheme.colors.textSecondary),
        content = content,
    )
}

/**
 * 32dp round send button (web default variant, `--primary`), which becomes the
 * web destructive variant (`destructive/10` wash, `/20` in dark, red glyph) as a
 * stop button while a turn runs.
 */
@Composable
private fun SendStopButton(isInFlight: Boolean, enabled: Boolean, onClick: () -> Unit) {
    val colors = CodegTheme.colors
    val stopWash = colors.danger.withOpacity(if (colors.isDark) 0.20f else 0.10f)
    FilledIconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.padding(bottom = 2.dp).size(48.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = if (isInFlight) stopWash else colors.accent,
            contentColor = if (isInFlight) colors.danger else colors.onAccent,
            disabledContainerColor = colors.accent.withOpacity(0.5f),
            disabledContentColor = colors.onAccent.withOpacity(0.5f),
        ),
    ) {
        AnimatedContent(
            targetState = isInFlight,
            transitionSpec = {
                (fadeIn(tween(150)) + scaleIn(tween(150), initialScale = 0.6f)) togetherWith
                    (fadeOut(tween(120)) + scaleOut(tween(120), targetScale = 0.6f))
            },
            label = "send-stop-icon",
        ) { inFlight ->
            Icon(
                imageVector = if (inFlight) Lucide.Square else Lucide.ArrowUp,
                contentDescription = if (inFlight) stringResource(R.string.compose_stop) else stringResource(R.string.compose_send),
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

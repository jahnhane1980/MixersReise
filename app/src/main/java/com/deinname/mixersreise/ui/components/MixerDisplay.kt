package com.deinname.mixersreise.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.viewmodel.ToolType
import com.deinname.mixersreise.ui.components.MixerSpeechBubble

@Composable
fun MixerDisplay(
    isSleeping: Boolean,
    droolAlpha: Float,
    speechText: String,
    showHearts: Boolean,
    isInteractionLocked: Boolean,
    activeTool: ToolType,
    onMixerClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Zentraler Container mit dem visuellen Versatz nach unten (120.dp)
        Box(
            modifier = Modifier
                .offset(y = 120.dp)
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {

            // DIE MIXER-GRAFIK (mixer_idle)
            Image(
                painter = painterResource(id = R.drawable.mixer_idle),
                contentDescription = "Mixer",
                modifier = Modifier
                    .size(220.dp)
                    .clickable(
                        enabled = !isInteractionLocked,
                        onClick = { onMixerClick() }
                    )
            )

            // R7: WERKZEUG-LOGIK (Exakt nach deiner ToolType-Definition)
            if (isInteractionLocked) {
                val toolResId = when (activeTool) {
                    ToolType.FOOD -> R.drawable.tool_food
                    //ToolType.CLEAN -> R.drawable.ic_tool_clean
                    ToolType.SPONGE -> R.drawable.tool_sponge // Mapping falls gleiches Icon
                    ToolType.COKE -> R.drawable.tool_coke  // Mapping falls gleiches Icon
                    ToolType.HAND -> R.drawable.tool_hand
                    ToolType.TALK -> R.drawable.tool_talk
                    else -> null
                }

                toolResId?.let { resId ->
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = "Aktives Werkzeug",
                        modifier = Modifier
                            .size(70.dp)
                            .offset(x = (-90).dp, y = 10.dp)
                    )
                }
            }

            // Schlafanimation (mixer_sleeping)
            if (isSleeping) {
                val infiniteTransition = rememberInfiniteTransition(label = "SleepTransition")
                val sleepAlpha by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "SleepAlpha"
                )

                Image(
                    painter = painterResource(id = R.drawable.mixer_sleeping),
                    contentDescription = "Zzz",
                    modifier = Modifier
                        .size(60.dp)
                        .offset(x = 60.dp, y = (-70).dp)
                        .alpha(sleepAlpha)
                )
            }

            // Sabber-Anzeige (overlay_drool)
            if (droolAlpha > 0f) {
                Image(
                    painter = painterResource(id = R.drawable.overlay_drool),
                    contentDescription = "Speichel",
                    modifier = Modifier
                        .size(40.dp)
                        .offset(x = (-10).dp, y = 50.dp)
                        .alpha(droolAlpha)
                )
            }

            // Herzen-Animation
            if (showHearts) {
                HeartParticles()
            }

            // Sprechblase (MixerSpeechBubble)
            AnimatedVisibility(
                visible = speechText.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .offset(x = 0.dp, y = (-160).dp)
            ) {
                MixerSpeechBubble(text = speechText)
            }
        }
    }
}
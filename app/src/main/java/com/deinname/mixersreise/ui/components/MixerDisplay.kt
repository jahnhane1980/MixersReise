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

@Composable
fun MixerDisplay(
    isSleeping: Boolean,
    droolAlpha: Float,
    speechText: String,
    showHearts: Boolean,
    isInteractionLocked: Boolean,
    activeTool: ToolType?, // Fix: Typ auf nullable geändert
    onMixerClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset(y = 120.dp)
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            // Hauptgrafik Mixer
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

            // Werkzeug-Anzeige während der Interaktion
            if (isInteractionLocked) {
                val toolResId = when (activeTool) {
                    ToolType.FOOD -> R.drawable.tool_food
                    ToolType.SPONGE -> R.drawable.tool_sponge
                    ToolType.COKE -> R.drawable.tool_coke
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

            // Schlaf-Animation
            if (isSleeping) {
                val infiniteTransition = rememberInfiniteTransition(label = "Sleep")
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
                    contentDescription = "Schläft",
                    modifier = Modifier
                        .size(60.dp)
                        .offset(x = 60.dp, y = (-70).dp)
                        .alpha(sleepAlpha)
                )
            }

            // Sabber-Overlay
            if (droolAlpha > 0f) {
                Image(
                    painter = painterResource(id = R.drawable.overlay_drool),
                    contentDescription = "Sabber",
                    modifier = Modifier
                        .size(40.dp)
                        .offset(x = (-10).dp, y = 50.dp)
                        .alpha(droolAlpha)
                )
            }

            // Herzen-Effekt
            if (showHearts) {
                HeartParticles()
            }

            // Sprechblase
            AnimatedVisibility(
                visible = speechText.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier.offset(y = (-160).dp)
            ) {
                MixerSpeechBubble(text = speechText)
            }
        }
    }
}
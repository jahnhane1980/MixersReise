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
// R1.1 Quittung: Import der korrekt benannten Komponente
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
        Box(
            modifier = Modifier
                .offset(y = 120.dp) // Beibehalten des gewünschten Offsets nach unten
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {

            // DIE MIXER-GRAFIK (Vordergrund)
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

            // Schlafanimation
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

                // R1.1 Quittung: Korrigierter Name mixer_sleeping
                Image(
                    painter = painterResource(id = R.drawable.mixer_sleeping),
                    contentDescription = "Zzz",
                    modifier = Modifier
                        .size(60.dp)
                        .offset(x = 60.dp, y = (-70).dp)
                        .alpha(sleepAlpha)
                )
            }

            // Sabber-Anzeige
            if (droolAlpha > 0f) {
                // R1.1 Quittung: Korrigierter Name overlay_drool
                Image(
                    painter = painterResource(id = R.drawable.overlay_drool),
                    contentDescription = "Speichel",
                    modifier = Modifier
                        .size(40.dp)
                        .offset(x = (-10).dp, y = 50.dp)
                        .alpha(droolAlpha)
                )
            }

            if (showHearts) {
                HeartParticles()
            }

            // Sprechblase
            AnimatedVisibility(
                visible = speechText.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .offset(x = 0.dp, y = (-160).dp)
            ) {
                // R1.1 Quittung: Verwendung von MixerSpeechBubble
                MixerSpeechBubble(text = speechText)
            }
        }
    }
}
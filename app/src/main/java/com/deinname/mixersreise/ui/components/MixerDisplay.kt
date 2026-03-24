package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.viewmodel.ToolType // R1.1: Verifizierter Pfad

@Composable
fun MixerDisplay(
    isSleeping: Boolean,
    droolAlpha: Float,
    speechText: String,
    showHearts: Boolean,
    isInteractionLocked: Boolean, // R2: Neu für Cooldown-Status
    activeTool: ToolType,         // R2: Neu zur Anzeige des Icons
    onMixerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Interaktions-Bereich
        Box(
            modifier = Modifier
                .size(300.dp)
                // R6: Klick sperren, solange die Interaktion läuft (enabled = !locked)
                .clickable(enabled = !isInteractionLocked) { onMixerClick() },
            contentAlignment = Alignment.Center
        ) {
            // Basis Charakter
            SafeImage(
                resId = if (isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle,
                contentDescription = "Mixer Charakter",
                modifier = Modifier.fillMaxSize()
            )

            // R6: Visualisierung des Tools während der 4 Sekunden
            if (isInteractionLocked) {
                val toolDisplay = when (activeTool) {
                    ToolType.HAND -> "🖐️"
                    ToolType.FOOD -> "🍎"
                    ToolType.CLEAN -> "🧼"
                    ToolType.SPONGE -> "🧽"
                    ToolType.COKE -> "🥤"
                    ToolType.TALK -> "💬"
                    else -> "✨"
                }

                Text(
                    text = toolDisplay,
                    fontSize = 80.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-40).dp) // Leicht über dem Gesicht positioniert
                )
            }

            // Herzchen-Animation
            if (showHearts) {
                HeartParticles()
            }
        }

        // Sabber-Overlay
        if (isSleeping) {
            SafeImage(
                resId = R.drawable.overlay_drool,
                contentDescription = "Sabber",
                modifier = Modifier.size(300.dp),
                alpha = droolAlpha
            )
        }

        // Sprechblase
        if (speechText.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 360.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                MixerSpeechBubble(text = speechText)
            }
        }
    }
}
package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R

@Composable
fun MixerDisplay(
    isSleeping: Boolean,
    droolAlpha: Float,
    speechText: String,
    showHearts: Boolean,
    modifier: Modifier = Modifier
) {
    // R6: Alignment von Center auf BottomCenter geändert und Padding für das untere Drittel hinzugefügt
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 80.dp), // Schiebt den Mixer vom untersten Rand etwas hoch ins untere Drittel
        contentAlignment = Alignment.BottomCenter
    ) {
        // 1. Der Mixer (Basis)
        SafeImage(
            resId = if (isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle,
            contentDescription = "Mixer",
            modifier = Modifier.size(300.dp) // Größe bleibt gleich
        )

        // 2. Sabber-Overlay (nur wenn schläft)
        if (isSleeping) {
            SafeImage(
                resId = R.drawable.overlay_drool,
                contentDescription = "Sabber",
                modifier = Modifier.size(300.dp),
                alpha = droolAlpha
            )
        }

        // 3. Sprechblase (Positioniert über dem Mixer)
        if (speechText.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 220.dp), // Sprechblase über dem Kopf
                contentAlignment = Alignment.BottomCenter
            ) {
                MixerSpeechBubble(text = speechText)
            }
        }
    }
}
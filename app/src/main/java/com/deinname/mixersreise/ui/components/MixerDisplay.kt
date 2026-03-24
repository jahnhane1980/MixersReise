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
    // Root-Box für den Mixer-Bereich
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 80.dp), // Position im unteren Drittel
        contentAlignment = Alignment.BottomCenter
    ) {
        // 1. Der Mixer (Basis-Bild)
        SafeImage(
            resId = if (isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle,
            contentDescription = "Mixer",
            modifier = Modifier.size(300.dp)
        )

        // 2. Sabber-Overlay
        if (isSleeping) {
            SafeImage(
                resId = R.drawable.overlay_drool,
                contentDescription = "Sabber",
                modifier = Modifier.size(300.dp),
                alpha = droolAlpha
            )
        }

        // 3. Sprechblase (R6: Padding erhöht, um das Gesicht nicht zu verdecken)
        if (speechText.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 360.dp), // Von 220.dp auf 360.dp erhöht
                contentAlignment = Alignment.BottomCenter
            ) {
                MixerSpeechBubble(text = speechText)
            }
        }
    }
}
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
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // 1. Der Mixer (Basis)
        SafeImage(
            resId = if (isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle,
            contentDescription = "Mixer Charakter",
            modifier = Modifier.size(300.dp)
        )

        // 2. Partikel-Layer (Über dem Mixer, unter der Sprechblase)
        if (showHearts) {
            Box(
                modifier = Modifier.size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                HeartParticles()
            }
        }

        // 3. Sabber-Overlay
        if (isSleeping) {
            SafeImage(
                resId = R.drawable.overlay_drool,
                contentDescription = "Sabber Effekt",
                modifier = Modifier.size(300.dp),
                alpha = droolAlpha
            )
        }

        // 4. Sprechblase (Höher positioniert, um Gesicht nicht zu verdecken)
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
package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
// R1.1 Quittung: HeartParticles liegt im selben Package, Import zur Sicherheit explizit:
import com.deinname.mixersreise.ui.components.HeartParticles

@Composable
fun MixerDisplay(
    isSleeping: Boolean,
    droolAlpha: Float,
    speechText: String,
    showHearts: Boolean,
    onMixerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .clickable { onMixerClick() },
            contentAlignment = Alignment.Center
        ) {
            SafeImage(
                resId = if (isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle,
                contentDescription = "Mixer",
                modifier = Modifier.fillMaxSize()
            )

            // R6: Physischer Aufruf der Partikel-Komponente
            if (showHearts) {
                HeartParticles()
            }
        }

        if (isSleeping) {
            SafeImage(
                resId = R.drawable.overlay_drool,
                contentDescription = "Sabber",
                modifier = Modifier.size(300.dp),
                alpha = droolAlpha
            )
        }

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
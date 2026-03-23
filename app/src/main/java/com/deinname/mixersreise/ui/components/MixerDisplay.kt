package com.deinname.mixersreise.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R

@Composable
fun MixerDisplay(
    isSleeping: Boolean,
    droolAlpha: Float,
    speechText: String,
    showHearts: Boolean // Trigger für den Partikel-Effekt
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Hier kommen die aufsteigenden Herzen hin (Partikel)
        if (showHearts) {
            HeartParticles()
        }

        // Mixer Basis-Bild
        SafeImage(
            resId = if (isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle,
            contentDescription = "Mixer",
            modifier = Modifier.size(350.dp)
        )

        // Schmodder-Overlay
        if (droolAlpha > 0f) {
            SafeImage(
                resId = R.drawable.overlay_drool,
                contentDescription = "Schmodder",
                modifier = Modifier.size(350.dp).alpha(droolAlpha)
            )
        }

        // Sprechblase (Nutzt jetzt die warmen Farben)
        if (speechText.isNotEmpty()) {
            Box(modifier = Modifier.align(Alignment.TopCenter).padding(top = 100.dp)) {
                MixerSpeechBubble(text = speechText)
            }
        }
    }
}

@Composable
fun HeartParticles() {
    // Platzhalter für die echte Partikel-Animation
    // Hier werden wir im nächsten Schritt die Herzen fliegen lassen
}
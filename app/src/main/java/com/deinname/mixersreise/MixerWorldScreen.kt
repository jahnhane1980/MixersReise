package com.deinname.mixersreise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.ui.components.MixerSpeechBubble
import com.deinname.mixersreise.ui.components.SafeImage
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MixerWorldScreen(viewModel: MixerViewModel) {
    // Wir extrahieren die Werte aus den States für eine sauberere Verwendung
    val speechText = viewModel.speechText.value
    val droolAlpha = viewModel.droolAlpha.value
    val isSleeping = viewModel.isSleeping.value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Hintergrund
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "Hintergrund",
            modifier = Modifier.fillMaxSize()
        )

        // Mixer Darstellung
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Sprechblase - Nutzt jetzt speechText (vorher mixerResponseText)
            if (speechText.isNotEmpty()) {
                MixerSpeechBubble(text = speechText)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Box(contentAlignment = Alignment.Center) {
                // Hauptbild des Mixers
                SafeImage(
                    resId = if (isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle,
                    contentDescription = "Mixer",
                    modifier = Modifier.size(300.dp)
                )

                // Schmodder-Overlay
                // Korrektur: Vergleich auf .value (jetzt oben extrahiert)
                if (droolAlpha > 0f) {
                    Image(
                        painter = painterResource(id = R.drawable.overlay_drool),
                        contentDescription = "Schmodder",
                        modifier = Modifier.size(300.dp),
                        // Korrektur: Übergabe des Float-Werts, nicht des States
                        alpha = droolAlpha
                    )
                }
            }
        }
    }
}
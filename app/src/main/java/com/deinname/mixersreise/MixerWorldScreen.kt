package com.deinname.mixersreise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.ui.components.MixerSpeechBubble

@Composable
fun MixerWorldScreen(viewModel: MixerViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Hintergrundbild
        Image(
            painter = painterResource(id = R.drawable.bg_bedroom_plushies),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Mixer und Sprechblase (Am unteren Rand ausgerichtet)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sprechblase
            if (viewModel.mixerResponseText.isNotEmpty()) {
                MixerSpeechBubble(text = viewModel.mixerResponseText)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Der Charakter Mixer (Pferd)
            Image(
                painter = painterResource(id = R.drawable.mixer_idle),
                contentDescription = "Mixer Charakter",
                modifier = Modifier.size(280.dp)
            )

            // Abstandshalter nach unten (schiebt Mixer ein Stück hoch von der Toolbar)
            // Verringere diesen Wert (z.B. auf 10.dp), wenn er noch tiefer soll.
            Spacer(modifier = Modifier.height(20.dp))
        }

        // 3. Sabber-Overlay (Korrigiert auf droolAlpha)
        if (viewModel.droolAlpha > 0f) {
            Image(
                painter = painterResource(id = R.drawable.overlay_drool),
                contentDescription = "Sabber",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = viewModel.droolAlpha
            )
        }
    }
}
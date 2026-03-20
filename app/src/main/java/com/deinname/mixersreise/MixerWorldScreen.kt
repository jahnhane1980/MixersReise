package com.deinname.mixersreise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
// DIESE ZEILE FIXT DIE ROTEN DRAWABLES:
import com.deinname.mixersreise.R
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.ui.components.MixerSpeechBubble

@Composable
fun MixerWorldScreen(viewModel: MixerViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Hintergrund
        Image(
            painter = painterResource(id = R.drawable.bg_bedroom_plushies),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Mixer Figur & UI
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sprechblase
            if (viewModel.mixerResponseText.isNotEmpty()) {
                MixerSpeechBubble(text = viewModel.mixerResponseText)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Hauptfigur
            Image(
                painter = painterResource(id = R.drawable.mixer_idle),
                contentDescription = "Mixer",
                modifier = Modifier.size(250.dp)
            )

            // Sabber-Overlay
            if (viewModel.droolAlpha > 0.05f) {
                Image(
                    painter = painterResource(id = R.drawable.overlay_drool),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp)
                        .graphicsLayer { alpha = viewModel.droolAlpha }
                )
            }
        }
    }
}
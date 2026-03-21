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
        // 1. Hintergrund
        Image(
            painter = painterResource(id = R.drawable.bg_bedroom_plushies),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Mixer und Sprechblase
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sprechblase (nur wenn Text da ist)
            if (viewModel.mixerResponseText.isNotEmpty()) {
                MixerSpeechBubble(text = viewModel.mixerResponseText)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Der Charakter Mixer
            Image(
                painter = painterResource(id = R.drawable.mixer_idle),
                contentDescription = "Mixer",
                modifier = Modifier.size(280.dp)
            )
        }
    }
}
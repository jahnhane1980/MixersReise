package com.deinname.mixersreise

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.deinname.mixersreise.ui.components.MixerDisplay
import com.deinname.mixersreise.ui.components.SafeImage
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MixerWorldScreen(viewModel: MixerViewModel) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Hintergrundbild
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "Hintergrund",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Mixer-Display mit allen States aus dem ViewModel
        MixerDisplay(
            isSleeping = viewModel.isSleeping.value,
            droolAlpha = viewModel.droolAlpha.value,
            speechText = viewModel.speechText.value,
            showHearts = viewModel.showHearts.value,
            isInteractionLocked = viewModel.isInteractionLocked.value,
            activeTool = viewModel.activeTool.value, // Fix: Jetzt korrekt übergeben
            onMixerClick = { viewModel.petMixer() }
        )
    }
}
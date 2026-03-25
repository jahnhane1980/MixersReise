package com.deinname.mixersreise

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.deinname.mixersreise.ui.components.MixerDisplay
import com.deinname.mixersreise.ui.components.SafeImage
import com.deinname.mixersreise.viewmodel.MixerViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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

        // MixerWorldScreen.kt -> Innerhalb der Box

        if (viewModel.showTalkMenu.value) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center) // Zentriert über dem Mixer
                    .fillMaxWidth(0.8f)
                    .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.8f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                com.deinname.mixersreise.data.mixerTalkOptions.forEach { option ->
                    androidx.compose.material3.Button(
                        onClick = { viewModel.handleTalkOptionSelected(option) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        androidx.compose.material3.Text(option.question)
                    }
                }
                androidx.compose.material3.TextButton(onClick = { viewModel.showTalkMenu.value = false }) {
                    androidx.compose.material3.Text("Abbrechen", color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.6f))
                }
            }
        }
    }
}
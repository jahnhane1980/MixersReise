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
import androidx.compose.foundation.clickable

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

// MixerWorldScreen.kt -> Innerhalb der Box einfügen

        if (viewModel.showTalkMenu.value) {
            // 1. Hintergrund-Dimmer (fängt Klicks außerhalb ab)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f))
                    .clickable { viewModel.showTalkMenu.value = false },
                contentAlignment = Alignment.Center
            ) {
                // 2. Das eigentliche Menü-Fenster
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .background(
                            color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.9f),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp)
                        // Verhindert, dass Klicks auf das Menü selbst das Menü schließen
                        .clickable(enabled = false) { },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material3.Text(
                        text = "Was möchtest du fragen?",
                        color = androidx.compose.ui.graphics.Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // 3. Dynamische Buttons aus der TalkOption.kt
                    com.deinname.mixersreise.data.mixerTalkOptions.forEach { option ->
                        androidx.compose.material3.Button(
                            onClick = { viewModel.handleTalkOptionSelected(option) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            androidx.compose.material3.Text(text = option.question)
                        }
                    }

                    // 4. Abbrechen Button
                    androidx.compose.material3.TextButton(
                        onClick = { viewModel.showTalkMenu.value = false },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        androidx.compose.material3.Text(
                            text = "Abbrechen",
                            color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
        //Ende IF
    }
}
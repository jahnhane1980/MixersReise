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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

// MixerWorldScreen.kt -> Ganz unten in der Box einfügen:

        if (viewModel.showTalkMenu.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    // Schließt das Menü, wenn man daneben klickt
                    .clickable { viewModel.showTalkMenu.value = false },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .background(Color.Black.copy(alpha = 0.9f), shape = RoundedCornerShape(20.dp))
                        .padding(20.dp)
                        .clickable(enabled = false) { }, // Verhindert Schließen bei Klick aufs Menü selbst
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Frag Mixer etwas:",
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Zugriff auf die Liste über den Import
                    com.deinname.mixersreise.data.mixerTalkOptions.forEach { option ->
                        Button(
                            onClick = { viewModel.handleTalkOptionSelected(option) },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(option.question)
                        }
                    }

                    TextButton(onClick = { viewModel.showTalkMenu.value = false }) {
                        Text("Abbrechen", color = Color.White.copy(alpha = 0.6f))
                    }
                }
            }
        }

    }
}
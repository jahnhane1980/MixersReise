package com.deinname.mixersreise // WICHTIG: Passe dies an deinen Projektnamen an!

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun MixerWorldScreen(viewModel: MixerViewModel) {
    val hearts = viewModel.hearts
    val selectedTool = viewModel.selectedTool

    Box(modifier = Modifier.fillMaxSize()) {

        // --- LAYER 1: HINTERGRUND ---
        // Bild: Bett, Oktopus, Croissant, Bert
        Image(
            painter = painterResource(id = R.drawable.bg_bedroom_plushies),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // --- LAYER 2: MIXER (CHARAKTER) ---
        val mixerImage = if (viewModel.isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle
        val mixerAlignment = if (viewModel.isSleeping) Alignment.BottomCenter else Alignment.Center

        Box(
            modifier = Modifier
                .align(mixerAlignment)
                .size(350.dp)
                // Hier wird die Interaktion (z.B. Streicheln) erkannt
                .pointerInput(selectedTool) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        // Wir werten die Geste im ViewModel aus
                        viewModel.handleInteraction()
                    }
                }
        ) {
            Image(
                painter = painterResource(id = mixerImage),
                contentDescription = "Mixer",
                modifier = Modifier.fillMaxSize()
            )

            // Sabber-Overlay nur anzeigen, wenn er schläft und Sabber da ist
            if (viewModel.hasDrool && viewModel.isSleeping) {
                Image(
                    painter = painterResource(id = R.drawable.overlay_drool),
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterStart)
                        .offset(x = 50.dp, y = 20.dp), // Feinjustierung am Kopf
                    contentDescription = null
                )
            }
        }

        // --- LAYER 3: UI OVERLAYS (HERZEN & TOOLBAR) ---

        // Herz-Anzeige oben links
        StatusDisplay(hearts = hearts, multiplier = viewModel.multiplier)

        // Werkzeugleiste am linken Rand
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val tools = listOf(
                Tool.HAND to R.drawable.tool_hand,
                Tool.SPONGE to R.drawable.tool_sponge,
                Tool.FOOD to R.drawable.tool_food,
                Tool.COKE to R.drawable.tool_coke,
                Tool.TALK to R.drawable.tool_talk
            )

            tools.forEach { (tool, iconId) ->
                ToolButton(
                    iconRes = iconId,
                    isSelected = selectedTool == tool,
                    onClick = { viewModel.selectTool(tool) }
                )
            }
        }
    }
}

@Composable
fun StatusDisplay(hearts: Int, multiplier: Int) {
    Surface(
        color = Color.Black.copy(alpha = 0.4f),
        shape = CircleShape,
        modifier = Modifier.padding(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "❤️ $hearts",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            if (multiplier > 1) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "x$multiplier",
                    color = Color.Yellow,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun ToolButton(iconRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        // Wenn ausgewählt, leuchtet der Button gelb
        color = if (isSelected) Color.Yellow.copy(alpha = 0.9f) else Color.White.copy(alpha = 0.6f),
        modifier = Modifier.size(70.dp),
        border = BorderStroke(3.dp, if (isSelected) Color.Black else Color.Transparent)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.padding(14.dp)
        )
    }
}
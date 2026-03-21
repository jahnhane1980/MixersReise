package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.ToolType

@Composable
fun MixerToolBar(viewModel: MixerViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(CircleShape),
        color = Color.White.copy(alpha = 0.9f),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Die Namen entsprechen jetzt exakt deinem Screenshot!
            ToolButton(R.drawable.tool_food, "Essen") { viewModel.onToolSelected(ToolType.FOOD) }
            ToolButton(R.drawable.tool_hand, "Hand") { viewModel.onToolSelected(ToolType.HAND) }
            ToolButton(R.drawable.tool_sponge, "Schwamm") { viewModel.onToolSelected(ToolType.SPONGE) }
            ToolButton(R.drawable.tool_talk, "Reden") { viewModel.onToolSelected(ToolType.TALK) }

            // Da in deinem Screenshot kein "tool_map" zu sehen ist,
            // habe ich hier ic_launcher_foreground als Platzhalter gelassen.
            // Falls du ein Karten-Icon hast, ändere es hier entsprechend ab.
            ToolButton(R.drawable.ic_launcher_foreground, "Karte") { viewModel.onToolSelected(ToolType.MAP) }
        }
    }
}

@Composable
fun ToolButton(iconRes: Int, contentDescription: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(Color(0xFFF0F0F0))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(40.dp) // Etwas größer für bessere Sichtbarkeit
        )
    }
}
package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.ToolType

@Composable
fun MixerToolBar(viewModel: MixerViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.9f),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ESSEN
            Button(onClick = { viewModel.onToolSelected(ToolType.FOOD) }) {
                Text("Essen")
            }

            // STREICHELN
            Button(onClick = { viewModel.onToolSelected(ToolType.HAND) }) {
                Text("Hand")
            }

            // REINIGEN (Schwamm)
            Button(onClick = { viewModel.onToolSelected(ToolType.SPONGE) }) {
                Text("Schwamm")
            }

            // REDEN
            Button(onClick = { viewModel.onToolSelected(ToolType.TALK) }) {
                Text("Reden")
            }
        }
    }
}
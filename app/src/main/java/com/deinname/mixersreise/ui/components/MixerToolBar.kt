package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// DIE ENTSCHEIDENDEN IMPORTS:
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.ToolType

@Composable
fun MixerToolBar(viewModel: MixerViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { viewModel.useTool(ToolType.FOOD, "Küche") },
            enabled = viewModel.isToolEnabled(ToolType.FOOD)
        ) { Text("Essen") }

        Button(
            onClick = { viewModel.useTool(ToolType.HAND, "Wohnzimmer") },
            enabled = viewModel.isToolEnabled(ToolType.HAND)
        ) { Text("Streicheln") }
    }
}
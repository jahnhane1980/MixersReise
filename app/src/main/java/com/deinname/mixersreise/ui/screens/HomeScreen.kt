package com.deinname.mixersreise.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.ToolType

@Composable
fun HomeScreen(viewModel: MixerViewModel, onOpenMap: () -> Unit, onOpenSettings: () -> Unit) {
    val mixerScale by animateFloatAsState(targetValue = if (viewModel.isBaby) 0.7f else 1.0f)

    Scaffold(
        topBar = { MixerTopBar(viewModel.level, viewModel.totalHearts, onOpenMap, onOpenSettings) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Bonus: x${viewModel.currentMultiplier}", style = MaterialTheme.typography.labelMedium)
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                // HIER DER NEUE NAME: mixer_idle
                Image(
                    painter = painterResource(id = R.drawable.mixer_idle),
                    contentDescription = "Mixer",
                    modifier = Modifier.scale(mixerScale)
                )
                if (viewModel.droolAlpha > 0f) {
                    Image(
                        painter = painterResource(id = R.drawable.overlay_drool),
                        modifier = Modifier.size(200.dp).alpha(viewModel.droolAlpha).pointerInput(Unit) {
                            detectDragGestures { change, _ -> change.consume(); viewModel.onSpongeStroke() }
                        }, contentDescription = null
                    )
                }
                viewModel.activeDialog?.let { dialog ->
                    AlertDialog(onDismissRequest = { viewModel.activeDialog = null }, title = { Text("Mixer fragt:") },
                        text = { Column { Text(dialog.question); dialog.options.forEach { Button(onClick = { viewModel.selectDialogOption(it.second) }, modifier = Modifier.fillMaxWidth()) { Text(it.first) } } } },
                        confirmButton = {})
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                InventoryItem(ToolType.HAND, viewModel); InventoryItem(ToolType.FOOD, viewModel)
                InventoryItem(ToolType.COKE, viewModel); InventoryItem(ToolType.SPONGE, viewModel)
                InventoryItem(ToolType.TALK, viewModel)
            }
        }
    }
}

@Composable
fun InventoryItem(tool: ToolType, viewModel: MixerViewModel) {
    val enabled = viewModel.isToolEnabled(tool)
    IconButton(onClick = { viewModel.useTool(tool, "Ort") }, enabled = enabled, modifier = Modifier.size(56.dp)) {
        val icon = when(tool) {
            ToolType.HAND -> R.drawable.tool_hand; ToolType.FOOD -> R.drawable.tool_food
            ToolType.COKE -> R.drawable.tool_coke; ToolType.SPONGE -> R.drawable.tool_sponge
            else -> R.drawable.tool_talk
        }
        Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.alpha(if (enabled) 1f else 0.3f))
    }
}
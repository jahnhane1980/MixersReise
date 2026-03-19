package com.deinname.mixersreise.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.ToolType

@Composable
fun HomeScreen(viewModel: MixerViewModel, onOpenMap: () -> Unit, onOpenSettings: () -> Unit) {
    val mixerScale by animateFloatAsState(targetValue = if (viewModel.isBaby) 0.7f else 1.0f)

    // Hält fest, welches Tool gerade in der Hand gehalten wird
    var selectedTool by remember { mutableStateOf<ToolType?>(null) }

    Scaffold(
        topBar = { MixerTopBar(viewModel.level, viewModel.totalHearts, onOpenMap, onOpenSettings) },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 12.dp,
                shadowElevation = 20.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
            ) {
                Row(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Die Buttons wählen jetzt das Tool nur aus
                    InventoryItem(ToolType.HAND, selectedTool == ToolType.HAND, viewModel) { selectedTool = it }
                    InventoryItem(ToolType.FOOD, selectedTool == ToolType.FOOD, viewModel) { selectedTool = it }
                    InventoryItem(ToolType.COKE, selectedTool == ToolType.COKE, viewModel) { selectedTool = it }
                    InventoryItem(ToolType.SPONGE, selectedTool == ToolType.SPONGE, viewModel) { selectedTool = it }
                    InventoryItem(ToolType.TALK, false, viewModel) {
                        // Talk ist ein Sofort-Click für den Dialog
                        viewModel.useTool(it, "Home")
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {

            // 1. HINTERGRUND
            Image(
                painter = painterResource(id = R.drawable.bg_bedroom_plushies),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 2. INTERAKTIONS-LAYER (Mixer)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Mixer Character
                Image(
                    painter = painterResource(id = R.drawable.mixer_idle),
                    contentDescription = "Mixer",
                    modifier = Modifier
                        .scale(mixerScale)
                        // DAS IST DIE HAPTIK: Wischen über Mixer
                        .pointerInput(selectedTool) {
                            detectDragGestures(
                                onDragStart = { /* Optional: Effekt starten */ },
                                onDrag = { change, _ ->
                                    change.consume()
                                    // Wenn die Hand ausgewählt ist, wird gestreichelt
                                    if (selectedTool == ToolType.HAND) {
                                        viewModel.useTool(ToolType.HAND, "Home")
                                        // Tool nach Benutzung wieder abwählen?
                                        // Wenn du Dauer-Streicheln willst, lass die nächste Zeile weg:
                                        selectedTool = null
                                    }

                                    // Wenn der Schwamm ausgewählt ist, wird gewischt
                                    if (selectedTool == ToolType.SPONGE && viewModel.droolAlpha > 0f) {
                                        viewModel.onSpongeStroke()
                                    }
                                }
                            )
                        }
                )

                // Sabber-Overlay (Optisch drüber, aber Wischen geht an Mixer-Layer)
                if (viewModel.droolAlpha > 0f) {
                    Image(
                        painter = painterResource(id = R.drawable.overlay_drool),
                        modifier = Modifier.size(220.dp).alpha(viewModel.droolAlpha),
                        contentDescription = null
                    )
                }
            }

            // Multiplikator Anzeige
            Surface(
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 10.dp),
                color = Color.Black.copy(alpha = 0.5f),
                shape = CircleShape
            ) {
                Text("Bonus: x${viewModel.currentMultiplier}", color = Color.White, modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp))
            }
        }
    }
}

@Composable
fun InventoryItem(
    tool: ToolType,
    isSelected: Boolean,
    viewModel: MixerViewModel,
    onSelect: (ToolType) -> Unit
) {
    val enabled = viewModel.isToolEnabled(tool)
    val bgColor = when {
        isSelected -> MaterialTheme.colorScheme.primary // Kräftiges Blau wenn aktiv
        enabled -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        else -> Color.Transparent
    }

    IconButton(
        onClick = { onSelect(tool) },
        enabled = enabled,
        modifier = Modifier
            .size(54.dp)
            .background(bgColor, CircleShape)
            .clip(CircleShape)
    ) {
        val iconRes = when(tool) {
            ToolType.HAND -> R.drawable.tool_hand
            ToolType.FOOD -> R.drawable.tool_food
            ToolType.COKE -> R.drawable.tool_coke
            ToolType.SPONGE -> R.drawable.tool_sponge
            ToolType.TALK -> R.drawable.tool_talk
        }
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(36.dp).alpha(if (enabled) 1f else 0.3f)
        )
    }
}
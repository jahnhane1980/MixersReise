package com.deinname.mixersreise.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
    var selectedTool by remember { mutableStateOf<ToolType?>(null) }

    // Auswahl aufheben, wenn Hand deaktiviert wird
    LaunchedEffect(viewModel.isPettingWanted) {
        if (!viewModel.isPettingWanted && selectedTool == ToolType.HAND) {
            selectedTool = null
        }
    }

    Scaffold(
        topBar = { MixerTopBar(viewModel.level, viewModel.totalHearts, onOpenMap, onOpenSettings) },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 12.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
            ) {
                Row(
                    modifier = Modifier.navigationBarsPadding().padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InventoryItem(ToolType.HAND, selectedTool == ToolType.HAND, viewModel) { selectedTool = it }
                    InventoryItem(ToolType.FOOD, false, viewModel) { viewModel.useTool(it, "Zuhause") }
                    InventoryItem(ToolType.COKE, false, viewModel) { viewModel.useTool(it, "Zuhause") }
                    InventoryItem(ToolType.SPONGE, selectedTool == ToolType.SPONGE, viewModel) { selectedTool = it }
                    InventoryItem(ToolType.TALK, false, viewModel) { viewModel.useTool(it, "Zuhause") }
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

            // 2. MIXER & INTERAKTION
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                // --- SPRECHBLASE ---
                AnimatedVisibility(
                    visible = viewModel.mixerResponseText.isNotEmpty(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 80.dp)
                ) {
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(15.dp),
                        shadowElevation = 8.dp
                    ) {
                        Text(
                            text = viewModel.mixerResponseText,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    }
                }

                // Mixer Character (mixer_idle)
                Image(
                    painter = painterResource(id = R.drawable.mixer_idle),
                    contentDescription = "Mixer",
                    modifier = Modifier
                        .scale(mixerScale)
                        .size(300.dp)
                        .pointerInput(selectedTool) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                if (selectedTool == ToolType.HAND && viewModel.isPettingWanted) {
                                    if (dragAmount.getDistance() > 25f) {
                                        viewModel.useTool(ToolType.HAND, "Zuhause")
                                    }
                                }
                                if (selectedTool == ToolType.SPONGE) {
                                    viewModel.onSpongeStroke()
                                }
                            }
                        }
                )

                // Sabber Overlay
                if (viewModel.droolAlpha > 0f) {
                    Image(
                        painter = painterResource(id = R.drawable.overlay_drool),
                        modifier = Modifier.size(220.dp).alpha(viewModel.droolAlpha),
                        contentDescription = null
                    )
                }
            }

            // Fortschrittsanzeige unten
            if (viewModel.isPettingWanted && selectedTool == ToolType.HAND) {
                LinearProgressIndicator(
                    progress = { viewModel.petCount / 15f },
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp).fillMaxWidth(0.6f),
                    color = Color.Magenta
                )
            }

            // Test-Button (Kannst du später löschen)
            Button(
                onClick = { viewModel.triggerPettingDesire() },
                modifier = Modifier.align(Alignment.TopStart).padding(16.dp).alpha(0.5f)
            ) { Text("Test: Knuddeln") }
        }
    }
}

@Composable
fun InventoryItem(tool: ToolType, isSelected: Boolean, viewModel: MixerViewModel, onClick: (ToolType) -> Unit) {
    val enabled = viewModel.isToolEnabled(tool)
    IconButton(
        onClick = { onClick(tool) },
        enabled = enabled,
        modifier = Modifier
            .size(54.dp)
            .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent, CircleShape)
            .clip(CircleShape)
    ) {
        val icon = when(tool) {
            ToolType.HAND -> R.drawable.tool_hand
            ToolType.FOOD -> R.drawable.tool_food
            ToolType.COKE -> R.drawable.tool_coke
            ToolType.SPONGE -> R.drawable.tool_sponge
            ToolType.TALK -> R.drawable.tool_talk
        }
        Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(36.dp).alpha(if (enabled) 1f else 0.3f))
    }
}
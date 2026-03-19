package com.deinname.mixersreise.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight // DIESER IMPORT HAT GEFEHLT
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.ui.theme.*
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.ToolType

@Composable
fun HomeScreen(viewModel: MixerViewModel, onOpenMap: () -> Unit, onOpenSettings: () -> Unit) {
    val mixerScale by animateFloatAsState(targetValue = if (viewModel.isBaby) 0.7f else 1.0f)
    var selectedTool by remember { mutableStateOf<ToolType?>(null) }

    LaunchedEffect(viewModel.isPettingWanted) {
        if (!viewModel.isPettingWanted && selectedTool == ToolType.HAND) {
            selectedTool = null
        }
    }

    Scaffold(
        topBar = { MixerTopBar(viewModel.level, viewModel.totalHearts, onOpenMap, onOpenSettings) },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(2.dp, TextBrown.copy(alpha = 0.2f), RoundedCornerShape(24.dp)),
                tonalElevation = 8.dp,
                color = Color.White.copy(alpha = 0.9f)
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

            // 1. HINTERGRUND (Dein Bild)
            Image(
                painter = painterResource(id = R.drawable.bg_bedroom_plushies),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 2. MIXER & INTERAKTION
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                // --- SPRECHBLASE (Creme & Braun) ---
                AnimatedVisibility(
                    visible = viewModel.mixerResponseText.isNotEmpty(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 40.dp)
                ) {
                    Surface(
                        color = BubbleCream,
                        shape = RoundedCornerShape(20.dp),
                        shadowElevation = 10.dp,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Text(
                            text = viewModel.mixerResponseText,
                            modifier = Modifier.padding(20.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                            color = TextBrown
                        )
                    }
                }

                // Mixer Character
                Image(
                    painter = painterResource(id = R.drawable.mixer_idle),
                    contentDescription = "Mixer",
                    modifier = Modifier
                        .scale(mixerScale)
                        .size(320.dp)
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
            }

            // --- GOLDENER LADEBALKEN ---
            if (viewModel.isPettingWanted && selectedTool == ToolType.HAND) {
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 120.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Mixer möchte knuddeln!", color = TextBrown, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { viewModel.petCount / viewModel.MAX_PETS.toFloat() },
                        modifier = Modifier.fillMaxWidth(0.6f).height(12.dp).clip(CircleShape),
                        color = ProgressGold,
                        trackColor = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun InventoryItem(tool: ToolType, isSelected: Boolean, viewModel: MixerViewModel, onClick: (ToolType) -> Unit) {
    val enabled = viewModel.isToolEnabled(tool)
    val containerColor = when {
        tool == ToolType.HAND && enabled -> PettingwantedPink
        isSelected -> Color(0xFFE6E6FA) // Lavendel für Selektion
        enabled -> Color.White.copy(alpha = 0.7f)
        else -> Color.LightGray.copy(alpha = 0.3f)
    }

    IconButton(
        onClick = { onClick(tool) },
        enabled = enabled,
        modifier = Modifier
            .size(56.dp)
            .background(containerColor, CircleShape)
            .clip(CircleShape)
            .border(if (isSelected) 2.dp else 0.dp, TextBrown, CircleShape)
    ) {
        val iconRes = when(tool) {
            ToolType.HAND -> R.drawable.tool_hand
            ToolType.FOOD -> R.drawable.tool_food
            ToolType.COKE -> R.drawable.tool_coke
            ToolType.SPONGE -> R.drawable.tool_sponge
            ToolType.TALK -> R.drawable.tool_talk
        }
        Image(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(32.dp).alpha(if (enabled) 1f else 0.4f))
    }
}
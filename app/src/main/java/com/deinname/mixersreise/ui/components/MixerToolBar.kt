package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R

enum class ToolType {
    NONE, FOOD, HAND, SPONGE, TALK, MAP
}

@Composable
fun MixerToolBar(
    activeTool: ToolType,
    onToolSelected: (ToolType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Black.copy(alpha = 0.5f)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToolButton(
            iconRes = R.drawable.tool_food,
            description = "Essen",
            isSelected = activeTool == ToolType.FOOD,
            onClick = { onToolSelected(ToolType.FOOD) }
        )
        ToolButton(
            iconRes = R.drawable.tool_hand,
            description = "Streicheln",
            isSelected = activeTool == ToolType.HAND,
            onClick = { onToolSelected(ToolType.HAND) }
        )
        ToolButton(
            iconRes = R.drawable.tool_sponge,
            description = "Putzen",
            isSelected = activeTool == ToolType.SPONGE,
            onClick = { onToolSelected(ToolType.SPONGE) }
        )
        ToolButton(
            iconRes = R.drawable.tool_talk,
            description = "Reden",
            isSelected = activeTool == ToolType.TALK,
            onClick = { onToolSelected(ToolType.TALK) }
        )
        ToolButton(
            iconRes = R.drawable.bg_world_map, // Beispielhaft für Map-Icon
            description = "Karte",
            isSelected = activeTool == ToolType.MAP,
            onClick = { onToolSelected(ToolType.MAP) }
        )
    }
}

@Composable
fun ToolButton(
    iconRes: Int,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.White.copy(alpha = 0.4f) else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = description,
            modifier = Modifier.size(45.dp)
        )
    }
}
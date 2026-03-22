package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R

/**
 * Definition der verfügbaren Werkzeuge.
 */
enum class ToolType {
    NONE, FOOD, HAND, SPONGE, TALK, COKE
}

@Composable
fun MixerToolBar(
    activeTool: ToolType,
    onToolSelected: (ToolType) -> Unit
) {
    // Dunkler Hintergrund für die Toolbar
    val barBackground = Color(0xFF1A1A1A)
    // Blau für den Kreis hinter dem aktiven Icon
    val activeCircleColor = Color(0xFF1976D2)
    // Dezenter Kreis für inaktive Icons
    val inactiveCircleColor = Color.White.copy(alpha = 0.15f)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp),
        color = barBackground
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ToolButton(ToolType.FOOD, R.drawable.tool_food, activeTool, onToolSelected, activeCircleColor, inactiveCircleColor)
            ToolButton(ToolType.HAND, R.drawable.tool_hand, activeTool, onToolSelected, activeCircleColor, inactiveCircleColor)
            ToolButton(ToolType.SPONGE, R.drawable.tool_sponge, activeTool, onToolSelected, activeCircleColor, inactiveCircleColor)
            ToolButton(ToolType.TALK, R.drawable.tool_talk, activeTool, onToolSelected, activeCircleColor, inactiveCircleColor)
            ToolButton(ToolType.COKE, R.drawable.tool_coke, activeTool, onToolSelected, activeCircleColor, inactiveCircleColor)
        }
    }
}

@Composable
fun ToolButton(
    tool: ToolType,
    resId: Int,
    activeTool: ToolType,
    onToolSelected: (ToolType) -> Unit,
    activeColor: Color,
    inactiveColor: Color
) {
    val isSelected = activeTool == tool

    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(if (isSelected) activeColor else inactiveColor)
            .clickable { onToolSelected(tool) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            // KORREKTUR: Tint auf Unspecified setzen, damit die Icons ihre
            // Originalfarben behalten und nicht weiß übermalt werden.
            tint = Color.Unspecified
        )
    }
}
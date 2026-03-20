package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.ToolType

@Composable
fun MixerToolBar(viewModel: MixerViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ToolButton(R.drawable.tool_food, ToolType.FOOD, viewModel)
        ToolButton(R.drawable.tool_hand, ToolType.HAND, viewModel)
        ToolButton(R.drawable.tool_sponge, ToolType.SPONGE, viewModel)
        ToolButton(R.drawable.tool_talk, ToolType.TALK, viewModel)
    }
}

@Composable
fun ToolButton(iconRes: Int, tool: ToolType, viewModel: MixerViewModel) {
    val isEnabled = viewModel.isToolEnabled(tool)
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = Modifier
            .size(48.dp)
            .alpha(if (isEnabled) 1f else 0.3f)
            .clickable(enabled = isEnabled) {
                viewModel.useTool(tool, "Heimatstadt")
            }
    )
}
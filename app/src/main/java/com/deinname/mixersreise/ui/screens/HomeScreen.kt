package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.SafeImage
import com.deinname.mixersreise.ui.components.ToolType
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun HomeScreen(viewModel: MixerViewModel) {
    val activeTool = viewModel.activeTool.value
    val speechText = viewModel.speechText.value
    val droolAlpha = viewModel.droolAlpha.value
    val isSleeping = viewModel.isSleeping.value
    val touchPos = viewModel.touchPosition.value

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Hintergrund
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "Schlafzimmer Hintergrund",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Funktioniert jetzt durch Update oben
        )

        // Interaktions-Bereich des Mixers
        Box(
            modifier = Modifier
                .size(300.dp)
                .pointerInput(activeTool) {
                    detectTapGestures(
                        onPress = { offset ->
                            viewModel.updateTouchPosition(offset)
                            tryAwaitRelease()
                            viewModel.updateTouchPosition(null)
                        },
                        onTap = {
                            when (activeTool) {
                                ToolType.FOOD -> viewModel.feedMixer()
                                ToolType.HAND -> viewModel.petMixer()
                                ToolType.SPONGE -> viewModel.cleanMixer()
                                ToolType.TALK -> viewModel.talkToMixer()
                                else -> {}
                            }
                        }
                    )
                }
                .pointerInput(activeTool) {
                    detectDragGestures(
                        onDragStart = { offset -> viewModel.updateTouchPosition(offset) },
                        onDragEnd = { viewModel.updateTouchPosition(null) },
                        onDragCancel = { viewModel.updateTouchPosition(null) },
                        onDrag = { change, _ ->
                            viewModel.updateTouchPosition(change.position)
                        }
                    )
                }
        ) {
            // Mixer Bild
            SafeImage(
                resId = if (isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle,
                contentDescription = "Mixer",
                modifier = Modifier.fillMaxSize()
            )

            // Schmodder-Overlay
            if (droolAlpha > 0f) {
                SafeImage(
                    resId = R.drawable.overlay_drool,
                    contentDescription = "Schmodder",
                    modifier = Modifier.fillMaxSize(),
                    alpha = droolAlpha // Funktioniert jetzt durch Update oben
                )
            }

            // Haptisches Icon am Touch-Punkt
            if (activeTool != ToolType.NONE && touchPos != null) {
                val toolIconRes = when (activeTool) {
                    ToolType.FOOD -> R.drawable.tool_food
                    ToolType.HAND -> R.drawable.tool_hand
                    ToolType.SPONGE -> R.drawable.tool_sponge
                    ToolType.TALK -> R.drawable.tool_talk
                    else -> null
                }

                toolIconRes?.let { res ->
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = "Tool Cursor",
                        modifier = Modifier
                            .size(48.dp)
                            .offset(
                                x = (touchPos.x / 2.5f).dp,
                                y = (touchPos.y / 2.5f).dp
                            )
                    )
                }
            }
        }
    }
}
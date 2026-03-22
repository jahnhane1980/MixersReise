package com.deinname.mixersreise.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import com.deinname.mixersreise.ui.components.MixerSpeechBubble
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
        contentAlignment = Alignment.BottomCenter
    ) {
        // Hintergrundbild - Füllt den kompletten Bereich
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "Schlafzimmer Hintergrund",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Der Mixer-Container
        Box(
            modifier = Modifier
                // KORREKTUR: Padding auf 0 gesetzt für maximale Tiefe
                .padding(bottom = 0.dp)
                // Beibehalten der 350.dp für eine ordentliche Präsenz
                .size(350.dp)
                .pointerInput(activeTool) {
                    detectTapGestures(
                        onTap = { offset ->
                            viewModel.updateTouchPosition(offset)

                            when (activeTool) {
                                ToolType.FOOD -> viewModel.feedMixer()
                                ToolType.HAND -> viewModel.petMixer()
                                ToolType.SPONGE -> viewModel.cleanMixer()
                                ToolType.TALK -> viewModel.talkToMixer()
                                ToolType.COKE -> viewModel.feedMixer()
                                else -> {}
                            }
                        }
                    )
                }
        ) {
            // Das Pferd (Mixer)
            SafeImage(
                resId = if (isSleeping) R.drawable.mixer_sleeping else R.drawable.mixer_idle,
                contentDescription = "Mixer",
                modifier = Modifier.fillMaxSize()
            )

            // Schmodder-Effekt
            if (droolAlpha > 0f) {
                SafeImage(
                    resId = R.drawable.overlay_drool,
                    contentDescription = "Schmodder",
                    modifier = Modifier.fillMaxSize(),
                    alpha = droolAlpha
                )
            }

            // Das Tool-Icon mit Fading-Effekt (4 Sek. Timer im ViewModel)
            AnimatedVisibility(
                visible = activeTool != ToolType.NONE && touchPos != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                if (touchPos != null) {
                    val toolIconRes = when (activeTool) {
                        ToolType.FOOD -> R.drawable.tool_food
                        ToolType.HAND -> R.drawable.tool_hand
                        ToolType.SPONGE -> R.drawable.tool_sponge
                        ToolType.TALK -> R.drawable.tool_talk
                        ToolType.COKE -> R.drawable.tool_coke
                        else -> null
                    }

                    toolIconRes?.let { res ->
                        Image(
                            painter = painterResource(id = res),
                            contentDescription = "Tool Visual",
                            modifier = Modifier
                                .size(80.dp)
                                .offset(
                                    x = (touchPos.x / 2.5f).dp - 40.dp,
                                    y = (touchPos.y / 2.5f).dp - 40.dp
                                )
                        )
                    }
                }
            }

            // Sprechblase
            if (speechText.isNotEmpty()) {
                Box(modifier = Modifier.align(Alignment.TopCenter)) {
                    MixerSpeechBubble(text = speechText)
                }
            }
        }
    }
}
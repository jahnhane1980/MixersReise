package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.ui.components.StatsHeader
import com.deinname.mixersreise.ui.components.MixerDisplay
import com.deinname.mixersreise.ui.components.MixerSpeechBubble
import com.deinname.mixersreise.ui.components.MixerToolBar
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.ui.components.SettingsDialog

@Composable
fun HomeScreen(
    viewModel: MixerViewModel,
    onOpenMap: () -> Unit,
    onNavigateToWorld: () -> Unit
) {
    // State für den Einstellungsdialog
    var showSettings by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_bedroom_plushies),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // MixerTopBar mit Logik-Verknüpfung für das Zahnrad
            MixerTopBar(
                hearts = viewModel.totalHearts.value,
                onOpenMap = onOpenMap,
                onOpenSettings = { showSettings = true }
            )

            StatsHeader(
                level = 1,
                hearts = viewModel.totalHearts.value
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                if (viewModel.speechText.value.isNotEmpty()) {
                    MixerSpeechBubble(text = viewModel.speechText.value)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                MixerDisplay(
                    isSleeping = viewModel.isSleeping.value,
                    droolAlpha = viewModel.droolAlpha.value,
                    showHearts = viewModel.showHearts.value,
                    speechText = viewModel.speechText.value,
                    isInteractionLocked = viewModel.isInteractionLocked.value,
                    activeTool = viewModel.activeTool.value,
                    onMixerClick = { viewModel.petMixer() }
                )
            }

            MixerToolBar(
                activeTool = viewModel.activeTool.value,
                onToolSelected = { tool -> viewModel.selectTool(tool) }
            )
        }

        // Dialog-Anzeige basierend auf State
        if (showSettings) {
            SettingsDialog(
                onDismiss = { showSettings = false },
                viewModel = viewModel
            )
        }

        if (viewModel.isInteractionLocked.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        enabled = true
                    ) {}
            )
        }
    }
}
package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.*
import com.deinname.mixersreise.ui.theme.LemonChiffon
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun HomeScreen(
    viewModel: MixerViewModel,
    onOpenMap: () -> Unit
) {
    var showSettings by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonChiffon)
    ) {
        // Hintergrund
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "Hintergrund",
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                MixerTopBar(
                    hearts = viewModel.totalHearts.value,
                    onOpenMap = { onOpenMap() },
                    onOpenSettings = { showSettings = true }
                )
            },
            bottomBar = {
                MixerToolBar(
                    activeTool = viewModel.activeTool.value,
                    onToolSelected = { viewModel.selectTool(it) }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // R2 & R6: Synchronisierter Aufruf mit allen Interaktions-States
                MixerDisplay(
                    isSleeping = viewModel.isSleeping.value,
                    droolAlpha = viewModel.droolAlpha.value,
                    speechText = viewModel.speechText.value,
                    showHearts = viewModel.showHearts.value,
                    isInteractionLocked = viewModel.isInteractionLocked.value,
                    activeTool = viewModel.activeTool.value,
                    onMixerClick = {
                        // Wir rufen petMixer auf, das die 4s Logik steuert
                        viewModel.petMixer()
                    }
                )

                if (showSettings) {
                    SettingsDialog(
                        onDismiss = { showSettings = false },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
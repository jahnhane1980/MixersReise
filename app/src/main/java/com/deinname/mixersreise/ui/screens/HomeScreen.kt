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

    // R5: Root-Box mit LemonChiffon statt Weiß als Basis
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonChiffon)
    ) {
        // LAYER 1: Hintergrundbild
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "Hintergrund Schlafzimmer",
            modifier = Modifier.fillMaxSize()
        )

        // LAYER 2: UI (Scaffold)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                MixerTopBar(
                    // R2: 'level' entfernt, nur noch 'hearts' wird übergeben
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
                // Zentrales Mixer Display
                MixerDisplay(
                    isSleeping = viewModel.isSleeping.value,
                    droolAlpha = viewModel.droolAlpha.value,
                    speechText = viewModel.speechText.value,
                    showHearts = false
                )

                // Dialog Overlays
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
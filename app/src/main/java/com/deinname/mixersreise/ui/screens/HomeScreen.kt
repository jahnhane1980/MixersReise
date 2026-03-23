package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.deinname.mixersreise.ui.components.*
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun HomeScreen(
    viewModel: MixerViewModel,
    onOpenMap: () -> Unit // NEU: Parameter für die Navigation zur Karte
) {
    var showSettings by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MixerTopBar(
                level = viewModel.level,
                hearts = viewModel.totalHearts.value,
                onOpenMap = onOpenMap, // NEU: Reicht den Klick an die TopBar weiter
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
            // Hintergrund und Mixer-Anzeige
            MixerDisplay(
                isSleeping = viewModel.isSleeping.value,
                droolAlpha = viewModel.droolAlpha.value,
                speechText = viewModel.speechText.value,
                showHearts = false // Hier kommt später die Logik für die Partikel rein
            )

            // Stats (Hunger, Hygiene etc.)
            StatsHeader(
                level = viewModel.level,
                hearts = viewModel.totalHearts.value
            )

            // Settings Dialog anzeigen
            if (showSettings) {
                SettingsDialog(
                    onDismiss = { showSettings = false },
                    viewModel = viewModel
                )
            }
        }
    }
}
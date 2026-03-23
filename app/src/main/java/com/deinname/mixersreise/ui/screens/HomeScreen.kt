package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.*
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun HomeScreen(
    viewModel: MixerViewModel,
    onOpenMap: () -> Unit
) {
    var showSettings by remember { mutableStateOf(false) }

    // R5: Root-Box ermöglicht das Stapeln von Hintergrundbild und UI
    Box(modifier = Modifier.fillMaxSize()) {
        // LAYER 1: Hintergrundbild (Ganz unten)
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "", // R1/R5: Darf laut Signatur nicht null sein
            modifier = Modifier.fillMaxSize()
        )

        // LAYER 2: UI über transparentem Scaffold
        Scaffold(
            containerColor = Color.Transparent, // FIX: Verhindert die weiße Überlagerung
            topBar = {
                MixerTopBar(
                    level = viewModel.level,
                    hearts = viewModel.totalHearts.value,
                    onOpenMap = onOpenMap,
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
                MixerDisplay(
                    isSleeping = viewModel.isSleeping.value,
                    droolAlpha = viewModel.droolAlpha.value,
                    speechText = viewModel.speechText.value,
                    showHearts = false
                )

                // FIX: StatsHeader entfernt, da MixerTopBar die Werte anzeigt

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
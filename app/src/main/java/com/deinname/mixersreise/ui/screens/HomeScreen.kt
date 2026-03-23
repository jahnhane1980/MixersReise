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

    // Das Bild liegt in einer Box ganz unten
    Box(modifier = Modifier.fillMaxSize()) {
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            // WICHTIG: Scaffold muss transparent sein, um das Bild zu sehen
            containerColor = Color.Transparent,
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
                // Mixer-Anzeige
                MixerDisplay(
                    isSleeping = viewModel.isSleeping.value,
                    droolAlpha = viewModel.droolAlpha.value,
                    speechText = viewModel.speechText.value,
                    showHearts = false
                )

                // FIX: StatsHeader hier entfernt, da MixerTopBar die Werte anzeigt.

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
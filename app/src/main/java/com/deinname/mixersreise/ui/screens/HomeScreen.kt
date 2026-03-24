package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

    // R1.1 Quittung: LemonChiffon als Basis-Layer
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonChiffon)
    ) {
        // LAYER 1: Das Plüschtier-Hintergrundbild
        // FIX: ContentScale.Crop sorgt dafür, dass es den gesamten Bereich ausfüllt
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "Schlafzimmer mit Kuscheltieren",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // LAYER 2: UI-Elemente
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent, // Wichtig für die Sichtbarkeit des Bildes
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
                // Hier wird der Mixer und seine Interaktionen gerendert
                MixerDisplay(
                    isSleeping = viewModel.isSleeping.value,
                    droolAlpha = viewModel.droolAlpha.value,
                    speechText = viewModel.speechText.value,
                    showHearts = viewModel.showHearts.value,
                    isInteractionLocked = viewModel.isInteractionLocked.value,
                    activeTool = viewModel.activeTool.value,
                    onMixerClick = { viewModel.petMixer() }
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
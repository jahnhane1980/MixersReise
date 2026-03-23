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

    // Die Box legt das Hintergrundbild als unterste Ebene fest
    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Hintergrundbild (Zuerst zeichnen)
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Scaffold transparent machen, damit das Bild und das Theme-Gelb sichtbar bleiben
        Scaffold(
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
                // Mixer-Anzeige mit Fix für den Null-Fehler
                MixerDisplay(
                    isSleeping = viewModel.isSleeping.value,
                    droolAlpha = viewModel.droolAlpha.value,
                    // FIX: Sicherstellen, dass speechText niemals null ist
                    speechText = viewModel.speechText.value ?: "",
                    showHearts = false
                )

                // Der StatsHeader wurde hier entfernt, da die MixerTopBar das Level bereits anzeigt

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
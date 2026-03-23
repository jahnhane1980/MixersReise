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

    // R5: Die Box ist der absolute Anker. Alles darin wird von oben nach unten gestapelt.
    Box(modifier = Modifier.fillMaxSize()) {

        // EBENE 1: Hintergrundbild (Muss ganz oben im Code stehen, um ganz unten im UI zu sein)
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )

        // EBENE 2: UI-Gerüst
        Scaffold(
            // WICHTIG: Wenn das nicht transparent ist, siehst du Ebene 1 nicht!
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
            // EBENE 3: Mixer & Interaktion
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
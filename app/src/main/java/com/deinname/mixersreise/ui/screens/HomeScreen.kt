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
    onOpenMap: () -> Unit,
    onNavigateToWorld: () -> Unit // Synchronisation mit NavHost Fehlermeldung
) {
    var showSettings by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonChiffon)
    ) {
        SafeImage(
            resId = R.drawable.bg_bedroom_plushies,
            contentDescription = "Schlafzimmer mit Kuscheltieren",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                MixerTopBar(
                    // Physischer Check: MixerViewModel.totalHearts ist MutableState<Int>
                    hearts = viewModel.totalHearts.value,
                    onOpenMap = { onOpenMap() },
                    onOpenSettings = { showSettings = true }
                )
            },
            bottomBar = {
                MixerToolBar(
                    // Physischer Check: activeTool ist MutableState<ToolType>
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
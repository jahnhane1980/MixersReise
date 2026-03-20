package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
// WICHTIG: Diese Imports verknüpfen deine Bausteine
import com.deinname.mixersreise.MixerWorldScreen
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.ui.components.MixerToolBar
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun HomeScreen(
    viewModel: MixerViewModel,
    onOpenMap: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            MixerTopBar(
                level = viewModel.level,
                hearts = viewModel.totalHearts,
                onOpenMap = onOpenMap,
                onOpenSettings = onOpenSettings
            )
        },
        bottomBar = {
            MixerToolBar(viewModel = viewModel)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Hier wird das Zimmer mit Mixer angezeigt
            MixerWorldScreen(viewModel = viewModel)
        }
    }
}
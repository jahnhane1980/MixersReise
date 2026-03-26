package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.ui.components.MixerDisplay
import com.deinname.mixersreise.ui.components.MixerToolBar
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.ui.components.SettingsDialog
// Beleg: Import für den neuen Dialog (angenommen er liegt in components)
 import com.deinname.mixersreise.ui.components.TalkDialog

@Composable
fun HomeScreen(
    viewModel: MixerViewModel,
    onOpenMap: () -> Unit,
    onNavigateToWorld: () -> Unit
) {
    var showSettings by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_bedroom_plushies),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                MixerTopBar(
                    hearts = viewModel.totalHearts.value,
                    onOpenMap = onOpenMap,
                    onOpenSettings = { showSettings = true }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                MixerDisplay(
                    isSleeping = viewModel.isSleeping.value,
                    droolAlpha = viewModel.droolAlpha.value,
                    showHearts = viewModel.showHearts.value,
                    speechText = viewModel.speechText.value,
                    isInteractionLocked = viewModel.isInteractionLocked.value,
                    activeTool = viewModel.activeTool.value,
                    onMixerClick = { viewModel.petMixer() }
                )
            }

            MixerToolBar(
                activeTool = viewModel.activeTool.value,
                onToolSelected = { tool -> viewModel.useTool(tool) }
            )
        }

        // Beleg: Dialog-Steuerung für Talk-Optionen
        /*
        if (viewModel.showTalkMenu.value) {
            TalkDialog(
                options = viewModel.talkOptions,
                onOptionSelected = { option -> viewModel.handleTalkOptionSelected(option) },
                onDismiss = { viewModel.showTalkMenu.value = false }
            )
        }*/

        // ANKER: Suche diesen Block am Ende von HomeScreen.kt
        if (viewModel.showTalkMenu.value) {
            TalkDialog(
                options = viewModel.talkOptions,
                onOptionSelected = { option ->
                    // WICHTIG: Hier muss der Aufruf an die neue Logik gehen
                    viewModel.handleTalkOptionSelected(option)
                },
                onDismiss = { viewModel.showTalkMenu.value = false }
            )
        }

        if (showSettings) {
            SettingsDialog(
                onDismiss = { showSettings = false },
                viewModel = viewModel
            )
        }

        if (viewModel.isInteractionLocked.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        enabled = true
                    ) {}
            )
        }
    }
}
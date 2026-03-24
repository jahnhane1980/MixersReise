package com.deinname.mixersreise

import androidx.compose.runtime.Composable
import com.deinname.mixersreise.ui.components.MixerDisplay
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MixerWorldScreen(viewModel: MixerViewModel) {
    MixerDisplay(
        isSleeping = viewModel.isSleeping.value,
        droolAlpha = viewModel.droolAlpha.value,
        speechText = viewModel.speechText.value,
        showHearts = viewModel.showHearts.value,
        isInteractionLocked = viewModel.isInteractionLocked.value,
        activeTool = viewModel.activeTool.value,
        onMixerClick = { viewModel.petMixer() }
    )
}
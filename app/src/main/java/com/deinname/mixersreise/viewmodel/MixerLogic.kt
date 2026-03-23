package com.deinname.mixersreise.viewmodel

// KORREKTUR: Import des Enums aus dem Komponenten-Package
import com.deinname.mixersreise.viewmodel.ToolType

fun handleMixerAction(
    activeTool: ToolType,
    viewModel: MixerViewModel
) {
    when (activeTool) {
        ToolType.FOOD -> viewModel.feedMixer()
        ToolType.HAND -> viewModel.petMixer()
        ToolType.SPONGE -> viewModel.cleanMixer()
        ToolType.TALK -> viewModel.talkToMixer()
        else -> { /* Keine Aktion für NONE oder MAP an dieser Stelle */ }
    }
}
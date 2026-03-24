package com.deinname.mixersreise.viewmodel

class MixerLogic(private val viewModel: MixerViewModel) {
    fun handleInteraction(tool: ToolType) {
        when (tool) {
            ToolType.FOOD -> viewModel.feedMixer()
            ToolType.CLEAN -> viewModel.cleanMixer()
            ToolType.TALK -> viewModel.talkToMixer()
            ToolType.HAND -> viewModel.petMixer()
            ToolType.SPONGE -> viewModel.cleanMixer() // Neu hinzugefügt
            ToolType.COKE -> viewModel.feedMixer()   // Neu hinzugefügt
            // Falls noch mehr kommen, deckt else alles ab:
            else -> viewModel.petMixer()
        }
    }
}
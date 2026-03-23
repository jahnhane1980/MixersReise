package com.deinname.mixersreise.viewmodel

fun handleToolInteraction(viewModel: MixerViewModel, tool: ToolType) {
    when (tool) {
        ToolType.FOOD -> viewModel.feedMixer()
        ToolType.HAND -> viewModel.petMixer()
        ToolType.SPONGE, ToolType.CLEAN -> viewModel.cleanMixer()
        ToolType.TALK -> viewModel.talkToMixer()
        ToolType.COKE -> viewModel.feedMixer()
    }
}
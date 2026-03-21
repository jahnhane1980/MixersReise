package com.deinname.mixersreise.viewmodel

class MixerLogic {
    fun getMixerResponse(tool: ToolType, isPettingWanted: Boolean): String {
        return when (tool) {
            ToolType.FOOD -> "Mampf! Das schmeckt!"
            ToolType.HAND -> if (isPettingWanted) "Hehe, das kitzelt!" else "Lass das!"
            ToolType.SPONGE -> "Ah, viel besser!"
            ToolType.TALK -> "Blabla? Ananas!"
            else -> ""
        }
    }
}
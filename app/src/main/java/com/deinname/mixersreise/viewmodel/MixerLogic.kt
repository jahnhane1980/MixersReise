package com.deinname.mixersreise.viewmodel

enum class ToolType { FOOD, HAND, SPONGE, TALK, MAP }

class MixerLogic {
    fun getMixerResponse(tool: ToolType, isPettingWanted: Boolean): String {
        return when (tool) {
            ToolType.FOOD -> "Mampf! Danke!"
            ToolType.HAND -> if (isPettingWanted) "Schnurr..." else "Lass das!"
            ToolType.SPONGE -> "Blub, sauber!"
            ToolType.TALK -> "Hallo Mensch!"
            ToolType.MAP -> "Wo geht's hin?"
        }
    }
}
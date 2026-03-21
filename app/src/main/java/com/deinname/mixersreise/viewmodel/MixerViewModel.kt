package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.deinname.mixersreise.data.SettingsManager

// 1. Das Enum direkt hierhin, damit der Compiler es sofort findet
enum class ToolType { NONE, FOOD, HAND, SPONGE, TALK, MAP }

class MixerViewModel(private val settingsManager: SettingsManager) : ViewModel() {

    // 2. UI States mit expliziten Typen und korrekten Imports
    var totalHearts: Int by mutableStateOf(settingsManager.totalHearts)
    var level: Int by mutableStateOf((settingsManager.totalHearts / 1000) + 1)

    var mixerResponseText: String by mutableStateOf("Hallo! Wollen wir reisen?")
    var droolAlpha: Float by mutableStateOf(0.0f)

    var isBaby: Boolean by mutableStateOf(false)
    var isPettingWanted: Boolean by mutableStateOf(true)

    // Hier sagen wir explizit, dass es ein ToolType ist
    var activeTool: ToolType by mutableStateOf(ToolType.NONE)

    fun onToolSelected(tool: ToolType) {
        activeTool = tool
        when (tool) {
            ToolType.FOOD -> feedMixer()
            ToolType.HAND -> petMixer()
            ToolType.SPONGE -> cleanMixer()
            ToolType.TALK -> talkToMixer()
            ToolType.MAP -> { /* Navigation zur Karte wird hier getriggert */ }
            ToolType.NONE -> { activeTool = ToolType.NONE }
        }
    }

    private fun feedMixer() {
        addHearts(50)
        mixerResponseText = "Mampf! Das schmeckt!"
    }

    private fun petMixer() {
        if (isPettingWanted) {
            addHearts(20)
            mixerResponseText = "Hehe, das kitzelt!"
        } else {
            mixerResponseText = "Nicht jetzt..."
        }
    }

    private fun cleanMixer() {
        if (droolAlpha > 0f) {
            droolAlpha = 0f
            addHearts(30)
            mixerResponseText = "Ah, viel besser!"
        } else {
            mixerResponseText = "Ich bin doch sauber!"
        }
    }

    private fun talkToMixer() {
        mixerResponseText = "Blabla? Ananas!"
        addHearts(5)
    }

    private fun addHearts(amount: Int) {
        totalHearts += amount
        settingsManager.totalHearts = totalHearts
        level = (totalHearts / 1000) + 1
    }
}
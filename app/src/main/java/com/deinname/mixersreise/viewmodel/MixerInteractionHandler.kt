package com.deinname.mixersreise.viewmodel

import kotlinx.coroutines.delay

/**
 * R6: Kapselt die Interaktions-Logik und Texte.
 * Verhindert "Magic Strings" im ViewModel.
 */
class MixerInteractionHandler(private val viewModel: MixerViewModel) {

    suspend fun execute(tool: ToolType) {
        if (viewModel.isInteractionLocked.value) return

        viewModel.isInteractionLocked.value = true
        viewModel.showHearts.value = true

        // R6: Zentrale Verwaltung der Texte (Vorbereitung für Strings.xml)
        viewModel.speechText.value = getSpeechForTool(tool)

        // Belohnungs-Logik
        val reward = when(tool) {
            ToolType.FOOD, ToolType.COKE -> 10
            ToolType.CLEAN, ToolType.SPONGE -> 7
            else -> 5
        }
        viewModel.totalHearts.value += reward

        // Die geforderten 4 Sekunden Cooldown
        delay(4000)

        viewModel.isInteractionLocked.value = false
        viewModel.showHearts.value = false
        viewModel.speechText.value = ""
    }

    private fun getSpeechForTool(tool: ToolType): String = when(tool) {
        ToolType.HAND -> "Huiii, das kitzelt!"
        ToolType.FOOD -> "Mampf! Danke!"
        ToolType.CLEAN -> "Ich glänze wieder!"
        ToolType.SPONGE -> "Schrubb schrubb..."
        ToolType.COKE -> "Prickelnd!"
        ToolType.TALK -> "Blabla? Blub!"
    }
}
package com.deinname.mixersreise.viewmodel

import android.util.Log

/**
 * R6: Logik-Klasse zur Verarbeitung von Interaktionen.
 * Diese Klasse verbindet die Tool-Auswahl mit den entsprechenden ViewModel-Aktionen.
 */
class MixerLogic(private val viewModel: MixerViewModel) {

    fun handleInteraction(tool: ToolType) {
        Log.d("MixerLogic", "Verarbeite Interaktion mit Tool: $tool")

        when (tool) {
            ToolType.FOOD -> {
                // R6: Physischer Aufruf der verifizierten ViewModel-Funktion
                viewModel.feedMixer()
            }
            ToolType.CLEAN -> {
                // R6: Physischer Aufruf der verifizierten ViewModel-Funktion
                viewModel.cleanMixer()
            }
            ToolType.TALK -> {
                // R6: Physischer Aufruf der verifizierten ViewModel-Funktion
                viewModel.talkToMixer()
            }
            ToolType.HAND -> {
                // Standard-Interaktion (Streicheln)
                viewModel.petMixer()
            }
        }
    }

    // Zukünftige Erweiterung für automatische Bedürfnisse (Hunger-Timer etc.)
    fun updateStatus() {
        // Hier könnte später die Logik für sinkende Werte stehen
    }
}
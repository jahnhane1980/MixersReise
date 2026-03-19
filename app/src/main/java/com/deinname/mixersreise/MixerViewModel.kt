package com.deinname.mixersreise

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class Tool { NONE, HAND, SPONGE, FOOD, COKE, TALK }

class MixerViewModel(application: Application) : AndroidViewModel(application) {

    // Gesamter Zustand der App
    var hearts by mutableStateOf(0)
    var multiplier by mutableStateOf(1)
    var selectedTool by mutableStateOf(Tool.NONE)
    var isSleeping by mutableStateOf(false)
    var hasDrool by mutableStateOf(false)
    var currentCity by mutableStateOf("Zuhause")

    fun selectTool(tool: Tool) {
        selectedTool = tool
    }

    // Interaktion basierend auf dem gewählten Werkzeug
    fun handleInteraction() {
        when (selectedTool) {
            Tool.HAND -> {
                addHearts(5) // Streicheln gibt 5 Basis-Punkte
            }
            Tool.SPONGE -> {
                if (hasDrool) {
                    hasDrool = false
                    addHearts(20) // Sabber putzen gibt Bonus
                }
            }
            Tool.FOOD -> addHearts(10)
            Tool.COKE -> {
                viewModelScope.launch {
                    multiplier *= 2 // Turbo-Modus
                    delay(60000) // Hält 1 Minute
                    multiplier /= 2
                }
            }
            else -> {}
        }
    }

    private fun addHearts(amount: Int) {
        hearts += (amount * multiplier)
    }

    // GPS Logik (stark vereinfacht)
    fun updateLocation(lat: Double, lon: Double) {
        // Hier käme die Berechnung: Wenn weit weg von Home -> Multiplier hoch
        currentCity = "Berlin" // Beispiel
        multiplier = 2
    }
}
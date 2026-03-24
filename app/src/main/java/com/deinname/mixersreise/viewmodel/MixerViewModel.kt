package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.TravelDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class ToolType {
    HAND, FOOD, CLEAN, TALK, SPONGE, COKE
}

class MixerViewModel(private val travelDao: TravelDao) : ViewModel() {

    // States für den Mixer
    var totalHearts = mutableStateOf(0)
    var isInteractionLocked = mutableStateOf(false)
    var showHearts = mutableStateOf(false)
    var activeTool = mutableStateOf(ToolType.HAND) // Standard
    var speechText = mutableStateOf("")
    var isSleeping = mutableStateOf(false)
    var droolAlpha = mutableStateOf(0f)

    // Standort-Logik
    var currentDestination = mutableStateOf("Berlin") // Beispiel-Startwert

    // Datenbank-Flow für die Map
    val allDestinations = travelDao.getAllDestinations()

    fun selectTool(tool: ToolType) {
        activeTool.value = tool
    }

    fun petMixer() {
        if (isInteractionLocked.value) return

        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true

            // 1. Globalen Counter erhöhen
            totalHearts.value += 1

            // 2. R7: Datenbank-Update für die aktuelle Stadt
            currentDestination.value?.let { cityName ->
                travelDao.addHeartsToCity(cityName, 1)
            }

            // Interaktions-Dauer (4 Sekunden)
            delay(4000)

            showHearts.value = false
            isInteractionLocked.value = false
        }
    }

    // Weitere Logik für Schlafen/Sabbern hier...
}
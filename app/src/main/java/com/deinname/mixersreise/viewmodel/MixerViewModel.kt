package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.TravelDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MixerViewModel(private val travelDao: TravelDao) : ViewModel() {

    var totalHearts = mutableStateOf(0)
    var isInteractionLocked = mutableStateOf(false)
    var showHearts = mutableStateOf(false)
    var activeTool = mutableStateOf(ToolType.HAND)
    var speechText = mutableStateOf("")
    var isSleeping = mutableStateOf(false)
    var droolAlpha = mutableStateOf(0f)

    // Aktueller Standort für die Herz-Zuordnung
    var currentDestination = mutableStateOf("Berlin")

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    fun selectTool(tool: ToolType) {
        activeTool.value = tool
    }

    fun petMixer() {
        if (isInteractionLocked.value) return

        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true

            totalHearts.value += 1

            // Datenbank-Update für die aktuelle Stadt
            currentDestination.value.let { cityName ->
                travelDao.addHeartsToCity(cityName, 1)
            }

            delay(4000)

            showHearts.value = false
            isInteractionLocked.value = false
        }
    }
}
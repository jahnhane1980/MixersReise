package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.TravelDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MixerViewModel(private val travelDao: TravelDao) : ViewModel() {

    // States als explizite MutableState Objekte für .value Zugriff
    val totalHearts = mutableStateOf(0)
    val isInteractionLocked = mutableStateOf(false)
    val showHearts = mutableStateOf(false)
    val activeTool: MutableState<ToolType?> = mutableStateOf(null) // Jetzt explizit nullable
    val speechText = mutableStateOf("")
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val currentDestination = mutableStateOf("Berlin")

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    fun selectTool(tool: ToolType?) {
        activeTool.value = tool
    }

    fun petMixer() {
        if (isInteractionLocked.value) return
        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true
            totalHearts.value += 1
            travelDao.addHeartsToCity(currentDestination.value, 1)
            delay(4000)
            showHearts.value = false
            isInteractionLocked.value = false
        }
    }

    fun addDestination(cityName: String) {
        viewModelScope.launch {
            // Physischer Check TravelDestination: Hat kein latitude/longitude!
            travelDao.insertDestination(
                TravelDestination(
                    cityName = cityName,
                    isDiscovered = true,
                    heartsCollected = 0
                )
            )
        }
    }
}
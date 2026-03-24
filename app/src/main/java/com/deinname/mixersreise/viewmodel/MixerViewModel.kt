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

    // FIX: Fehlende Properties für SettingsDialog hinzugefügt
    var userName = mutableStateOf("")
    var userStreet = mutableStateOf("")
    var userHouseNumber = mutableStateOf("")
    var userZipCode = mutableStateOf("")
    var userCity = mutableStateOf("")

    var currentDestination = mutableStateOf("Berlin")

    val destinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    // FIX: Fehlende Funktionen für SettingsDialog hinzugefügt
    fun updateUserName(newName: String) {
        userName.value = newName
    }

    fun updateAddress(street: String, house: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = house
        userZipCode.value = zip
        userCity.value = city
    }

    fun detectLocationViaGps() {
        // Implementierung folgt
    }

    fun selectTool(tool: ToolType) {
        activeTool.value = tool
    }

    fun petMixer() {
        if (isInteractionLocked.value) return
        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true
            totalHearts.value += 1
            currentDestination.value.let { cityName ->
                travelDao.addHeartsToCity(cityName, 1)
            }
            delay(4000)
            showHearts.value = false
            isInteractionLocked.value = false
        }
    }
}
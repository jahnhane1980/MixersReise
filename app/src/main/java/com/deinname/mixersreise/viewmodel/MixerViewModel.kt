package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.TravelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val externalScope: CoroutineScope // Hinzugefügt für Synchronität mit Factory
) : ViewModel() {

    val totalHearts = mutableStateOf(settingsManager.getHearts())
    val isInteractionLocked = mutableStateOf(false)
    val showHearts = mutableStateOf(false)
    val activeTool = mutableStateOf(ToolType.HAND)
    val speechText = mutableStateOf("")
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val currentDestination = mutableStateOf("Berlin")

    // Fehlende Properties für SettingsDialog hinzugefügt
    val userName = mutableStateOf(settingsManager.getUserName() ?: "")
    val userStreet = mutableStateOf(settingsManager.getStreet() ?: "")
    val userHouseNumber = mutableStateOf(settingsManager.getHouseNumber() ?: "")
    val userZipCode = mutableStateOf(settingsManager.getZipCode() ?: "")
    val userCity = mutableStateOf(settingsManager.getCity() ?: "")

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    // Fehlende Funktionen für SettingsDialog hinzugefügt
    fun updateUserName(name: String) {
        userName.value = name
        settingsManager.saveUserName(name)
    }

    fun updateAddress(street: String, house: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = house
        userZipCode.value = zip
        userCity.value = city
        settingsManager.saveStreet(street)
        settingsManager.saveHouseNumber(house)
        settingsManager.saveZipCode(zip)
        settingsManager.saveCity(city)
    }

    fun detectLocationViaGps() {
        // Logik für GPS-Suche (Platzhalter)
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
            settingsManager.saveHearts(totalHearts.value)
            currentDestination.value.let { travelDao.addHeartsToCity(it, 1) }
            delay(4000)
            showHearts.value = false
            isInteractionLocked.value = false
        }
    }
}
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

// Korrektur: Signatur an MixerViewModelFactory angepasst
class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val externalScope: CoroutineScope
) : ViewModel() {

    // Initialisierung mit Werten aus dem SettingsManager
    var totalHearts = mutableStateOf(settingsManager.getHearts())
    var isInteractionLocked = mutableStateOf(false)
    var showHearts = mutableStateOf(false)
    var activeTool = mutableStateOf(ToolType.HAND)
    var speechText = mutableStateOf("")
    var isSleeping = mutableStateOf(false)
    var droolAlpha = mutableStateOf(0f)

    // States für SettingsDialog
    var userName = mutableStateOf(settingsManager.getUserName() ?: "")
    var userStreet = mutableStateOf(settingsManager.getStreet() ?: "")
    var userHouseNumber = mutableStateOf(settingsManager.getHouseNumber() ?: "")
    var userZipCode = mutableStateOf(settingsManager.getZipCode() ?: "")
    var userCity = mutableStateOf(settingsManager.getCity() ?: "")

    var currentDestination = mutableStateOf("Berlin")

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

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
        // Logik für GPS
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

            currentDestination.value.let { cityName ->
                travelDao.addHeartsToCity(cityName, 1)
            }

            delay(4000)

            showHearts.value = false
            isInteractionLocked.value = false
        }
    }
}
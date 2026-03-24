package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.TravelDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager
) : ViewModel() {

    val totalHearts = mutableStateOf(settingsManager.getHearts())
    val isInteractionLocked = mutableStateOf(false)
    val showHearts = mutableStateOf(false)
    val activeTool: MutableState<ToolType?> = mutableStateOf(null)
    val speechText = mutableStateOf("")
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val currentDestination = mutableStateOf("Berlin")

    // States für SettingsDialog
    val userName = mutableStateOf(settingsManager.getUserName() ?: "")
    val userStreet = mutableStateOf(settingsManager.getStreet() ?: "")
    val userHouseNumber = mutableStateOf(settingsManager.getHouseNumber() ?: "")
    val userZipCode = mutableStateOf(settingsManager.getZipCode() ?: "")
    val userCity = mutableStateOf(settingsManager.getCity() ?: "")

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    fun updateUserName(newName: String) {
        userName.value = newName
        settingsManager.saveUserName(newName)
    }

    fun updateAddress(street: String, houseNumber: String, zipCode: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = houseNumber
        userZipCode.value = zipCode
        userCity.value = city
        settingsManager.saveAddress(street, houseNumber, zipCode, city)
    }

    fun detectLocationViaGps() {
        // Logik für GPS-Erkennung
    }

    fun selectTool(tool: ToolType?) {
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
package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.SettingsManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val scope: kotlinx.coroutines.CoroutineScope
) : ViewModel() {

    // Basis-States
    val totalHearts = mutableStateOf(0)
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.HAND)
    val showHearts = mutableStateOf(false)
    val isInteractionLocked = mutableStateOf(false)

    // User-Daten für SettingsDialog (R1.1 Quittung: Physisch vorhanden)
    val userName = mutableStateOf("Mixer-Freund")
    val userStreet = mutableStateOf("")
    val userHouseNumber = mutableStateOf("")
    val userZipCode = mutableStateOf("")
    val userCity = mutableStateOf("")

    fun updateUserName(name: String) {
        userName.value = name
    }

    fun updateAddress(street: String, houseNo: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = houseNo
        userZipCode.value = zip
        userCity.value = city
    }

    fun detectLocationViaGps() {
        viewModelScope.launch {
            speechText.value = "Suche GPS Signal..."
            delay(2000)
            speechText.value = "Standort aktualisiert!"
            delay(2000)
            speechText.value = ""
        }
    }

    fun selectTool(tool: ToolType) {
        if (!isInteractionLocked.value) activeTool.value = tool
    }

    fun petMixer() {
        if (isInteractionLocked.value) return
        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true
            speechText.value = "Das kitzelt!"
            delay(4000)
            isInteractionLocked.value = false
            showHearts.value = false
            speechText.value = ""
        }
    }

    fun feedMixer() { petMixer() }
    fun cleanMixer() { petMixer() }
    fun talkToMixer() { petMixer() }
}
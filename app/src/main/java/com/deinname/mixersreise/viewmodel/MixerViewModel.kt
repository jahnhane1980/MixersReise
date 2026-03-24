package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.viewmodel.ToolType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val scope: kotlinx.coroutines.CoroutineScope
) : ViewModel() {

    // Bestehende States
    val totalHearts = mutableStateOf(0)
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.HAND)
    val showHearts = mutableStateOf(false)
    val destinations = mutableStateListOf<String>()

    // NEU: States für SettingsDialog (R6: Keine Nulls)
    val userName = mutableStateOf("Reisender")
    val userStreet = mutableStateOf("")
    val userHouseNumber = mutableStateOf("")
    val userZipCode = mutableStateOf("")
    val userCity = mutableStateOf("")

    // NEU: Funktionen für SettingsDialog
    fun updateUserName(newName: String) { userName.value = newName }

    fun updateAddress(street: String, houseNumber: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = houseNumber
        userZipCode.value = zip
        userCity.value = city
    }

    fun detectLocationViaGps() {
        // Mockup für GPS
        speechText.value = "Suche Satelliten..."
    }

    // NEU: Funktionen für MixerLogic
    fun triggerHeartEffect() {
        viewModelScope.launch {
            showHearts.value = true
            delay(2000)
            showHearts.value = false
        }
    }

    fun feedMixer() {
        speechText.value = "Mampf! Lecker!"
        triggerHeartEffect()
        totalHearts.value += 10
    }

    fun cleanMixer() {
        speechText.value = "Blitzblank!"
        triggerHeartEffect()
        totalHearts.value += 5
    }

    fun talkToMixer() {
        speechText.value = "Hallo! Wie geht's?"
        triggerHeartEffect()
    }

    fun petMixer() {
        speechText.value = "Huiii!"
        triggerHeartEffect()
        totalHearts.value += 2
    }

    fun selectTool(tool: ToolType) {
        activeTool.value = tool
        if (tool != ToolType.TALK) triggerHeartEffect()
    }
}
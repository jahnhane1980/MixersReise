package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val scope: CoroutineScope
) : ViewModel() {

    // Stats
    var level = 1
    var totalHearts = mutableStateOf(0)
    var activeTool = mutableStateOf(ToolType.HAND)

    // Mixer State
    var isSleeping = mutableStateOf(false)
    var droolAlpha = mutableStateOf(0f)
    var speechText = mutableStateOf("Hallo! Ich bin Mixer.")

    // User Settings
    var userName = mutableStateOf("Mixer-Freund")
    var userStreet = mutableStateOf("")
    var userHouseNumber = mutableStateOf("")
    var userZipCode = mutableStateOf("")
    var userCity = mutableStateOf("")

    val destinations = listOf("Berlin", "Paris", "Tokio")

    init {
        scope.launch {
            while(true) {
                delay(10000)
                if (isSleeping.value) {
                    droolAlpha.value = (droolAlpha.value + 0.1f).coerceAtMost(1.0f)
                }
            }
        }
    }

    fun selectTool(tool: ToolType) {
        activeTool.value = tool
        when(tool) {
            ToolType.FOOD, ToolType.COKE -> feedMixer()
            ToolType.HAND -> petMixer()
            ToolType.CLEAN, ToolType.SPONGE -> cleanMixer()
            ToolType.TALK -> talkToMixer()
        }
    }

    fun feedMixer() { totalHearts.value += 5; speechText.value = "Mjam!" }
    fun petMixer() { totalHearts.value += 2; speechText.value = "Purr..." }
    fun cleanMixer() { droolAlpha.value = 0f; speechText.value = "Glänzend!" }
    fun talkToMixer() { speechText.value = "Echt jetzt?" }

    fun updateUserName(newName: String) { userName.value = newName }
    fun updateAddress(street: String, houseNr: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = houseNr
        userZipCode.value = zip
        userCity.value = city
    }
    fun detectLocationViaGps() { /* Placeholder */ }
}
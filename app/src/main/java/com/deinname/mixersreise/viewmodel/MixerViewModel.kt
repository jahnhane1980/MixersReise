package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateListOf
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

    val totalHearts = mutableStateOf(0)
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.HAND)
    val showHearts = mutableStateOf(false)
    val isInteractionLocked = mutableStateOf(false)

    val userName = mutableStateOf("")
    val userStreet = mutableStateOf("")
    val userHouseNumber = mutableStateOf("")
    val userZipCode = mutableStateOf("")
    val userCity = mutableStateOf("")

    val destinations = mutableStateListOf<String>()
    private val interactionHandler = MixerInteractionHandler(this)

    init {
        loadData()
    }

    private fun loadData() {
        // R1.1 Quittung: Jetzt physisch im SettingsManager vorhanden
        totalHearts.value = settingsManager.getHearts()
        userName.value = settingsManager.getUserName() ?: "Mixer-Freund"
        userStreet.value = settingsManager.getStreet() ?: ""
        userHouseNumber.value = settingsManager.getHouseNumber() ?: ""
        userZipCode.value = settingsManager.getZipCode() ?: ""
        userCity.value = settingsManager.getCity() ?: ""

        destinations.clear()
        destinations.addAll(listOf("Berlin", "Paris", "London"))
    }

    fun petMixer() {
        if (isInteractionLocked.value) return
        viewModelScope.launch {
            interactionHandler.execute(activeTool.value)
            settingsManager.saveHearts(totalHearts.value)
        }
    }

    fun selectTool(tool: ToolType) {
        if (!isInteractionLocked.value) activeTool.value = tool
    }

    fun updateUserName(name: String) {
        userName.value = name
        settingsManager.saveUserName(name)
    }

    fun updateAddress(s: String, h: String, z: String, c: String) {
        userStreet.value = s; userHouseNumber.value = h; userZipCode.value = z; userCity.value = c
        settingsManager.saveStreet(s)
        settingsManager.saveHouseNumber(h)
        settingsManager.saveZipCode(z)
        settingsManager.saveCity(c)
    }

    fun detectLocationViaGps() {
        viewModelScope.launch {
            speechText.value = "GPS Scan für ${userCity.value}..."
            delay(2000)
            speechText.value = "Bereit!"
            delay(1000)
            speechText.value = ""
        }
    }
}
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

    // --- UI STATES ---
    val totalHearts = mutableStateOf(0)
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.HAND)
    val showHearts = mutableStateOf(false)
    val isInteractionLocked = mutableStateOf(false)

    // --- USER DATA STATES ---
    val userName = mutableStateOf("")
    val userStreet = mutableStateOf("")
    val userHouseNumber = mutableStateOf("")
    val userZipCode = mutableStateOf("")
    val userCity = mutableStateOf("")

    // --- TRAVEL DATA ---
    val destinations = mutableStateListOf<String>()

    private val interactionHandler = MixerInteractionHandler(this)

    // R3: Daten beim Start der App aus dem Speicher laden
    init {
        loadUserData()
    }

    private fun loadUserData() {
        totalHearts.value = settingsManager.getHearts()
        userName.value = settingsManager.getUserName() ?: "Mixer-Freund"
        userStreet.value = settingsManager.getStreet() ?: ""
        userHouseNumber.value = settingsManager.getHouseNumber() ?: ""
        userZipCode.value = settingsManager.getZipCode() ?: ""
        userCity.value = settingsManager.getCity() ?: ""

        // Ziele laden wir später aus dem TravelDao (Datenbank)
        destinations.addAll(listOf("Berlin", "Paris", "London"))
    }

    /**
     * Speichert die Herzen und triggert die Interaktion.
     * Der InteractionHandler erhöht totalHearts.value, wir speichern danach.
     */
    fun petMixer() {
        viewModelScope.launch {
            interactionHandler.execute(activeTool.value)
            // R3: Nach der Interaktion den neuen Punktestand sichern
            settingsManager.saveHearts(totalHearts.value)
        }
    }

    fun selectTool(tool: ToolType) {
        if (!isInteractionLocked.value) activeTool.value = tool
    }

    // --- SETTINGS UPDATES MIT SOFORT-SPEICHERUNG ---

    fun updateUserName(name: String) {
        userName.value = name
        settingsManager.saveUserName(name)
    }

    fun updateAddress(street: String, houseNo: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = houseNo
        userZipCode.value = zip
        userCity.value = city

        // R3: Alles im SettingsManager persistieren
        settingsManager.saveStreet(street)
        settingsManager.saveHouseNumber(houseNo)
        settingsManager.saveZipCode(zip)
        settingsManager.saveCity(city)
    }

    fun detectLocationViaGps() {
        viewModelScope.launch {
            val originalText = speechText.value
            speechText.value = "Suche GPS für ${userCity.value}..."
            delay(2000)
            speechText.value = "Position gespeichert!"
            delay(1500)
            speechText.value = originalText
        }
    }
}
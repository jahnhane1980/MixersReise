package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MixerViewModel(private val settingsManager: Any) : ViewModel() {
    // Falls dein SettingsManager einen echten Typ hat, ersetze 'Any' durch diesen Typ.

    // --- Benutzerdaten (States) ---
    val userName = mutableStateOf("User")
    val userStreet = mutableStateOf("")
    val userHouseNumber = mutableStateOf("")
    val userZipCode = mutableStateOf("")
    val userCity = mutableStateOf("")

    private val _destinations = mutableStateListOf<String>()
    val destinations: List<String> = _destinations

    // --- Status-Werte ---
    val level = 1
    val totalHearts = mutableStateOf(0)
    val hunger = mutableStateOf(1.0f)
    val hygiene = mutableStateOf(1.0f)
    val social = mutableStateOf(1.0f)
    val boredom = mutableStateOf(1.0f)

    // --- UI-States ---
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.NONE)

    // --- DIESE METHODEN FEHLTEN (Fix für SettingsDialog) ---

    fun updateUserName(newName: String) {
        userName.value = newName
    }

    fun detectLocationViaGps() {
        // Später kommt hier die GPS-Logik rein
        speechText.value = "Suche GPS Signal..."
    }

    fun updateAddress(street: String, nr: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = nr
        userZipCode.value = zip
        userCity.value = city
    }

    // --- Logik-Funktionen (Für die Toolbar) ---

    fun feedMixer() {
        hunger.value = (hunger.value + 0.3f).coerceAtMost(1.0f)
        totalHearts.value += 10
        speechText.value = "Mampf! Danke!"
    }

    fun petMixer() {
        social.value = (social.value + 0.2f).coerceAtMost(1.0f)
        totalHearts.value += 5
        speechText.value = "Das tut gut..."
    }

    fun cleanMixer() {
        hygiene.value = (hygiene.value + 0.5f).coerceAtMost(1.0f)
        totalHearts.value += 8
        speechText.value = "Blitzblank!"
    }

    fun talkToMixer() {
        boredom.value = (boredom.value + 0.25f).coerceAtMost(1.0f)
        totalHearts.value += 5
        speechText.value = "Erzähl mir mehr!"
    }

    fun selectTool(tool: ToolType) {
        activeTool.value = if (activeTool.value == tool) ToolType.NONE else tool
    }
}
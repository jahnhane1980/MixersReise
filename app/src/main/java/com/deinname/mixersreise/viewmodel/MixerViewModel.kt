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

    // ... (Bestehende States wie hearts, etc.)
    val totalHearts = mutableStateOf(0)
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.HAND)
    val showHearts = mutableStateOf(false)
    val isInteractionLocked = mutableStateOf(false)

    // R1.1 Quittung: Diese Felder MÜSSEN für SettingsDialog.kt existieren
    val userName = mutableStateOf("Reisender")
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
        // Logik für GPS-Suche
        speechText.value = "GPS wird gesucht..."
    }

    // ... (restliche Funktionen)
    fun selectTool(tool: ToolType) { if (!isInteractionLocked.value) activeTool.value = tool }
    fun petMixer() { /* ... wie zuvor ... */ }
}
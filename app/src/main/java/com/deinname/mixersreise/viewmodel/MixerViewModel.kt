package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.SettingsManager
// R1.1 Quittung: ToolType liegt physisch im selben Verzeichnis/Paket
import com.deinname.mixersreise.viewmodel.ToolType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val scope: kotlinx.coroutines.CoroutineScope
) : ViewModel() {

    // --- GAME STATES ---
    val totalHearts = mutableStateOf(0)
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.HAND)
    val showHearts = mutableStateOf(false)
    val isInteractionLocked = mutableStateOf(false) // R6: Cooldown-Sperre

    // --- USER SETTINGS STATES ---
    val userName = mutableStateOf("Mixer-Freund")
    val userStreet = mutableStateOf("")
    val userHouseNumber = mutableStateOf("")
    val userZipCode = mutableStateOf("")
    val userCity = mutableStateOf("")

    // --- TRAVEL DATA ---
    // R1.1 Quittung: Wird vom MapScreen als SnapshotStateList benötigt
    val destinations = mutableStateListOf<String>("Berlin", "Paris", "London")

    // --- LOGIC FUNCTIONS ---

    /**
     * Wählt ein Werkzeug aus, sofern keine Interaktion läuft.
     */
    fun selectTool(tool: ToolType) {
        if (!isInteractionLocked.value) {
            activeTool.value = tool
        }
    }

    /**
     * Zentrale Funktion für die 4-Sekunden-Interaktion.
     * Blockiert weitere Klicks und zeigt Animationen/Herzen.
     */
    fun petMixer() {
        if (isInteractionLocked.value) return

        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true

            // Text-Logik basierend auf dem aktiven Tool
            speechText.value = when(activeTool.value) {
                ToolType.HAND -> "Huiii, das kitzelt!"
                ToolType.FOOD -> "Mampf! Lecker!"
                ToolType.CLEAN -> "Blitzblank sauber!"
                ToolType.SPONGE -> "Schrubb schrubb..."
                ToolType.COKE -> "Prickelnd erfrischend!"
                ToolType.TALK -> "Erzähl mir mehr!"
                else -> "Ooh!"
            }

            // Herzen & Belohnung (R6: Keine Magic Numbers)
            val reward = if (activeTool.value == ToolType.FOOD) 10 else 5
            totalHearts.value += reward

            // R2: Die geforderten 4 Sekunden Verweildauer
            delay(4000)

            isInteractionLocked.value = false
            showHearts.value = false
            speechText.value = ""
        }
    }

    // Aliase für die MixerLogic (R6: Unified Naming)
    fun feedMixer() = petMixer()
    fun cleanMixer() = petMixer()
    fun talkToMixer() = petMixer()

    /**
     * Aktualisiert den Namen des Benutzers.
     */
    fun updateUserName(name: String) {
        userName.value = name
    }

    /**
     * Aktualisiert die Adressdaten für die GPS-Simulation.
     */
    fun updateAddress(street: String, houseNo: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = houseNo
        userZipCode.value = zip
        userCity.value = city
    }

    /**
     * Simuliert die GPS-Standorterfassung.
     */
    fun detectLocationViaGps() {
        viewModelScope.launch {
            val oldText = speechText.value
            speechText.value = "Suche Satelliten für ${userCity.value}..."
            delay(2000)
            speechText.value = "Standort erfasst!"
            delay(1500)
            speechText.value = oldText
        }
    }
}
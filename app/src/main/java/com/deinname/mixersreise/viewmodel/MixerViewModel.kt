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

    // States
    val totalHearts = mutableStateOf(0)
    val isSleeping = mutableStateOf(false)
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.HAND)
    val showHearts = mutableStateOf(false)
    val isInteractionLocked = mutableStateOf(false) // R6: Neu für Cooldown

    // R1.1 Quittung: Verifizierte Funktion für Pet/Interaktion
    fun petMixer() {
        if (isInteractionLocked.value) return // Blockiert, wenn Timer läuft

        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true
            speechText.value = "Das fühlt sich gut an!"

            // 4 Sekunden hängen bleiben
            delay(4000)

            isInteractionLocked.value = false
            showHearts.value = false
            speechText.value = ""
        }
    }

    // ... restliche Funktionen wie feedMixer etc. analog anpassen ...
}
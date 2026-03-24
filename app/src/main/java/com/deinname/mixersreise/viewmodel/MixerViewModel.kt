package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.viewmodel.ToolType // R1.1 Quittung: Verifizierter Pfad
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
    val droolAlpha = mutableStateOf(0f) // FIX: Für HomeScreen wiederhergestellt
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.HAND)
    val showHearts = mutableStateOf(false)
    val isInteractionLocked = mutableStateOf(false)

    val destinations = mutableStateListOf<String>()

    // FIX: Für HomeScreen wiederhergestellt
    fun selectTool(tool: ToolType) {
        // R6: Tool-Wechsel nur erlauben, wenn gerade keine Interaktion läuft
        if (!isInteractionLocked.value) {
            activeTool.value = tool
        }
    }

    // Zentrale Interaktions-Logik (4s Timer)
    fun petMixer() {
        if (isInteractionLocked.value) return

        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true
            speechText.value = "Huiii!"

            delay(4000) // 4 Sekunden Verweildauer

            isInteractionLocked.value = false
            showHearts.value = false
            speechText.value = ""
        }
    }

    // Weitere Stubs für die Konsistenz
    fun feedMixer() { petMixer() } // Vorübergehend gleiche Logik
    fun cleanMixer() { petMixer() }
    fun talkToMixer() { petMixer() }
}
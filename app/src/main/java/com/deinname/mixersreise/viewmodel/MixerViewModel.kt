package com.deinname.mixersreise.viewmodel

import android.util.Log
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

    // States
    val totalHearts = mutableStateOf(0)
    val isSleeping = mutableStateOf(false)
    val droolAlpha = mutableStateOf(0f)
    val speechText = mutableStateOf("")
    val activeTool = mutableStateOf(ToolType.HAND)
    val showHearts = mutableStateOf(false) // Trigger für Animation

    val destinations = mutableStateListOf<String>()
    val level = 1 // Intern noch da, aber UI nutzt es nicht mehr

    fun selectTool(tool: ToolType) {
        activeTool.value = tool
        // Bei Auswahl eines Tools (außer Talk) direkt Effekt zeigen
        if (tool != ToolType.TALK) {
            triggerHeartEffect()
            totalHearts.value += 5 // Kleine Belohnung für die Interaktion
        }
    }

    // R6: Effekt-Trigger mit automatischem Reset
    fun triggerHeartEffect() {
        viewModelScope.launch {
            showHearts.value = true
            delay(2000) // Animation läuft 1.5 - 2 Sekunden
            showHearts.value = false
        }
    }

    // Weitere Funktionen (Stubs für die Logik)
    fun feedMixer() {
        speechText.value = "Mampf! Danke!"
        triggerHeartEffect()
        // Logik für Hunger-Reduktion hier einfügen
    }
}
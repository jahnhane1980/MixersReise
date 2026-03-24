package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.SettingsManager
// R1.1 Quittung: Absoluter Pfad verifiziert
import com.deinname.mixersreise.viewmodel.ToolType
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

    val destinations = mutableStateListOf<String>()

    fun triggerHeartEffect() {
        viewModelScope.launch {
            showHearts.value = true
            delay(2000)
            showHearts.value = false
        }
    }

    fun petMixer() {
        speechText.value = "Huiii, das kitzelt!"
        triggerHeartEffect()
        totalHearts.value += 2
    }

    fun selectTool(tool: ToolType) {
        activeTool.value = tool
        if (tool != ToolType.TALK) {
            triggerHeartEffect()
            totalHearts.value += 5
        }
    }
}
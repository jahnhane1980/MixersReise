package com.deinname.mixersreise.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.ui.components.ToolType // Angenommener Pfad des existierenden Enums
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
    val showHearts = mutableStateOf(false)

    val destinations = mutableStateListOf<String>()
    val level = 1

    // R6: Verwendet nun das global definierte ToolType Enum
    fun selectTool(tool: ToolType) {
        activeTool.value = tool
        if (tool != ToolType.TALK) {
            triggerHeartEffect()
            totalHearts.value += 5
        }
    }

    fun triggerHeartEffect() {
        viewModelScope.launch {
            showHearts.value = true
            delay(2000)
            showHearts.value = false
        }
    }

    fun feedMixer() {
        speechText.value = "Mampf! Danke!"
        triggerHeartEffect()
        totalHearts.value += 10
    }

    fun petMixer() {
        speechText.value = "Huiii, das kitzelt!"
        triggerHeartEffect()
        totalHearts.value += 2
    }
}
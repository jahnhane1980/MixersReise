package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val scope: CoroutineScope
) : ViewModel() {

    var level = 1
    var totalHearts = mutableStateOf(0)
    var activeTool = mutableStateOf(ToolType.HAND)

    var isSleeping = mutableStateOf(false)
    var droolAlpha = mutableStateOf(0f)
    var speechText = mutableStateOf("Hallo! Ich bin Mixer.")

    val destinations = listOf("Berlin", "Paris", "Tokio")

    init {
        // Beispiel für eine Hintergrund-Logik mit dem übergebenen Scope
        scope.launch {
            while(true) {
                delay(10000)
                if (isSleeping.value) {
                    droolAlpha.value = (droolAlpha.value + 0.1f).coerceAtMost(1.0f)
                }
            }
        }
    }

    fun selectTool(tool: ToolType) {
        activeTool.value = tool
        if (tool == ToolType.FOOD) {
            totalHearts.value += 5
            speechText.value = "Mjam!"
        }
    }
}
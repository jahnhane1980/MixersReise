package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.ui.components.ToolType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MixerViewModel(private val settingsManager: SettingsManager) : ViewModel() {

    private val _totalHearts = mutableStateOf<Int>(settingsManager.totalHearts)
    val totalHearts: State<Int> = _totalHearts

    private val _activeTool = mutableStateOf(ToolType.NONE)
    val activeTool: State<ToolType> = _activeTool

    private val _isSleeping = mutableStateOf(false)
    val isSleeping: State<Boolean> = _isSleeping

    private val _droolAlpha = mutableStateOf(0f)
    val droolAlpha: State<Float> = _droolAlpha

    private val _speechText = mutableStateOf("")
    val speechText: State<String> = _speechText

    private val _touchPosition = mutableStateOf<Offset?>(null)
    val touchPosition: State<Offset?> = _touchPosition

    private var timerJob: Job? = null

    val level: Int
        get() = (_totalHearts.value / 1000) + 1

    fun selectTool(tool: ToolType) {
        _activeTool.value = if (_activeTool.value == tool) ToolType.NONE else tool
    }

    fun updateTouchPosition(offset: Offset?) {
        _touchPosition.value = offset

        if (offset != null) {
            timerJob?.cancel()
            timerJob = viewModelScope.launch {
                delay(4000)
                _touchPosition.value = null
            }
        }
    }

    fun feedMixer() {
        if (_isSleeping.value) return
        addHearts(10)
        showSpeech("Mampf! Das schmeckt!")
    }

    fun petMixer() {
        if (_isSleeping.value) return
        addHearts(5)
        showSpeech("Ooh, das kitzelt!")
    }

    fun cleanMixer() {
        if (_droolAlpha.value > 0f) {
            _droolAlpha.value = 0f
            addHearts(20)
            showSpeech("Endlich wieder sauber!")
        }
    }

    fun talkToMixer() {
        showSpeech("Hallo! Wie geht es dir?")
    }

    private fun addHearts(amount: Int) {
        _totalHearts.value += amount
        settingsManager.totalHearts = _totalHearts.value
    }

    private fun showSpeech(text: String) {
        viewModelScope.launch {
            _speechText.value = text
            delay(3000)
            if (_speechText.value == text) {
                _speechText.value = ""
            }
        }
    }
}
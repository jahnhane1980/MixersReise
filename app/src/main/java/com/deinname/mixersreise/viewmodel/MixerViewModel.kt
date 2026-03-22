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

    private val _totalHearts = mutableStateOf(settingsManager.totalHearts)
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

    // Neue States für Personalisierung und Standort
    private val _userName = mutableStateOf(settingsManager.userName)
    val userName: State<String> = _userName

    private val _userStreet = mutableStateOf(settingsManager.street)
    val userStreet: State<String> = _userStreet

    private val _userHouseNumber = mutableStateOf(settingsManager.houseNumber)
    val userHouseNumber: State<String> = _userHouseNumber

    private val _userZipCode = mutableStateOf(settingsManager.zipCode)
    val userZipCode: State<String> = _userZipCode

    private val _userCity = mutableStateOf(settingsManager.city)
    val userCity: State<String> = _userCity

    private val _latitude = mutableStateOf(settingsManager.latitude)
    val latitude: State<Float> = _latitude

    private val _longitude = mutableStateOf(settingsManager.longitude)
    val longitude: State<Float> = _longitude

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

    fun updateUserName(newName: String) {
        _userName.value = newName
        settingsManager.userName = newName
    }

    fun updateAddress(street: String, nr: String, zip: String, city: String, lat: Float? = null, lon: Float? = null) {
        _userStreet.value = street
        _userHouseNumber.value = nr
        _userZipCode.value = zip
        _userCity.value = city

        settingsManager.street = street
        settingsManager.houseNumber = nr
        settingsManager.zipCode = zip
        settingsManager.city = city

        lat?.let {
            _latitude.value = it
            settingsManager.latitude = it
        }
        lon?.let {
            _longitude.value = it
            settingsManager.longitude = it
        }
    }

    fun detectLocationViaGps() {
        // Mock-Daten inklusive Koordinaten (Länge/Breite)
        // Später wird hier der echte FusedLocationProvider integriert
        updateAddress(
            street = "Musterstraße",
            nr = "42",
            zip = "12345",
            city = "Mixerstadt",
            lat = 52.5200f,
            lon = 13.4050f
        )
    }

    fun feedMixer() {
        if (_isSleeping.value) return
        addHearts(10)
        showSpeech("Mampf! Das schmeckt, ${_userName.value}!")
    }

    fun petMixer() {
        if (_isSleeping.value) return
        addHearts(5)
        showSpeech("Ooh, danke ${_userName.value}!")
    }

    fun cleanMixer() {
        if (_droolAlpha.value > 0f) {
            _droolAlpha.value = 0f
            addHearts(20)
            showSpeech("Sauber! Danke!")
        }
    }

    fun talkToMixer() {
        showSpeech("Hallo ${_userName.value}! Wie geht's?")
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
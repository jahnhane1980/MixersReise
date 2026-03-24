package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.TravelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val externalScope: CoroutineScope
) : ViewModel() {

    // UI States für Mixer-Interaktion
    var speechText by mutableStateOf("")
    var droolAlpha by mutableFloatOf(0f)
    var isSleeping by mutableStateOf(false)
    var showHearts by mutableStateOf(false)
    var isInteractionLocked by mutableStateOf(false)
    var activeTool by mutableStateOf<ToolType?>(null)

    // Stats
    var totalHearts by mutableStateOf(settingsManager.getHearts())
        private set

    // User Settings
    var userName by mutableStateOf(settingsManager.getUserName() ?: "")
        private set
    var userStreet by mutableStateOf(settingsManager.getStreet() ?: "")
        private set
    var userHouseNumber by mutableStateOf(settingsManager.getHouseNumber() ?: "")
        private set
    var userZipCode by mutableStateOf(settingsManager.getZipCode() ?: "")
        private set
    var userCity by mutableStateOf(settingsManager.getCity() ?: "")
        private set

    // Datenbank Flows (Synchronisation mit MapScreen)
    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    fun selectTool(tool: ToolType?) {
        activeTool = tool
    }

    fun petMixer() {
        // Logik wird im InteractionHandler ausgeführt, hier nur als Trigger falls nötig
    }

    fun updateUserName(newName: String) {
        userName = newName
        settingsManager.saveUserName(newName)
    }

    fun updateAddress(street: String, houseNumber: String, zipCode: String, city: String) {
        userStreet = street
        userHouseNumber = houseNumber
        userZipCode = zipCode
        userCity = city
        settingsManager.saveAddress(street, houseNumber, zipCode, city)
    }

    fun addHeart() {
        totalHearts++
        settingsManager.saveHearts(totalHearts)
    }

    fun detectLocationViaGps() { /* Mock */ }

    fun addDestination(cityName: String, lat: Double, lon: Double) {
        viewModelScope.launch {
            // Prüfung der TravelDestination Parameter (cityName, latitude, longitude)
            travelDao.insert(TravelDestination(cityName = cityName, latitude = lat, longitude = lon, heartsCollected = 0))
        }
    }
}
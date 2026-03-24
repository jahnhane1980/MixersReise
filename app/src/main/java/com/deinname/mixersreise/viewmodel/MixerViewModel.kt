package com.deinname.mixersreise.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    // States als explizite MutableState für .value Zugriff im UI/Handler
    val speechText = mutableStateOf("")
    val droolAlpha = mutableStateOf(0f)
    val isSleeping = mutableStateOf(false)
    val showHearts = mutableStateOf(false)
    val isInteractionLocked = mutableStateOf(false)
    val activeTool: MutableState<ToolType?> = mutableStateOf(null)
    val totalHearts = mutableStateOf(settingsManager.getHearts())

    // User Settings
    var userName = settingsManager.getUserName() ?: ""
    var userStreet = settingsManager.getStreet() ?: ""
    var userHouseNumber = settingsManager.getHouseNumber() ?: ""
    var userZipCode = settingsManager.getZipCode() ?: ""
    var userCity = settingsManager.getCity() ?: ""

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    fun selectTool(tool: ToolType?) {
        activeTool.value = tool
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
        totalHearts.value++
        settingsManager.saveHearts(totalHearts.value)
    }

    fun detectLocationViaGps() { /* Mock */ }

    fun petMixer() { /* Interaction Logik */ }

    fun addDestination(cityName: String, lat: Double, lon: Double) {
        viewModelScope.launch {
            // Verifizierung mit TravelDestination.kt: cityName, latitude, longitude
            travelDao.insert(TravelDestination(
                cityName = cityName,
                latitude = lat,
                longitude = lon,
                heartsCollected = 0
            ))
        }
    }
}
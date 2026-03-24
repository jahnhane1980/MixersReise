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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val externalScope: CoroutineScope
) : ViewModel() {

    var totalHearts by mutableStateOf(settingsManager.getHearts())
        private set

    // Settings-Fields
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

    fun detectLocationViaGps() {
        // Mock-Implementation für den Dialog-Fehler
    }

    val destinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    fun addDestination(name: String, lat: Double, lon: Double) {
        viewModelScope.launch {
            travelDao.insert(TravelDestination(name = name, latitude = lat, longitude = lon))
        }
    }
}
package com.deinname.mixersreise.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.TravelDestination
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Locale

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val externalScope: CoroutineScope,
    private val context: Context
) : ViewModel() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var totalHearts = mutableStateOf(settingsManager.getHearts())
    var isInteractionLocked = mutableStateOf(false)
    var showHearts = mutableStateOf(false)
    var activeTool = mutableStateOf<ToolType?>(ToolType.HAND)
    var speechText = mutableStateOf("")
    var isSleeping = mutableStateOf(false)
    var droolAlpha = mutableStateOf(0f)

    var heartMultiplier = mutableStateOf(1.0f)
    var currentDestination = mutableStateOf("Heimat")

    var userName = mutableStateOf(settingsManager.getUserName() ?: "")
    var userStreet = mutableStateOf(settingsManager.getStreet() ?: "")
    var userHouseNumber = mutableStateOf(settingsManager.getHouseNumber() ?: "")
    var userZipCode = mutableStateOf(settingsManager.getZipCode() ?: "")
    var userCity = mutableStateOf(settingsManager.getCity() ?: "")

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    init {
        val cityInStore = settingsManager.getCity()
        if (cityInStore.isNullOrBlank()) {
            speechText.value = "DEBUG: Speicher leer, starte GPS..."
            detectLocationViaGps()
        } else {
            speechText.value = "Willkommen zurück in $cityInStore!"
        }
    }

    fun updateUserName(name: String) {
        userName.value = name
    }

    fun updateAddress(street: String, house: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = house
        userZipCode.value = zip
        userCity.value = city
    }

    @SuppressLint("MissingPermission")
    fun detectLocationViaGps() {
        speechText.value = "Suche Satelliten..."
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                location?.let {
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            settingsManager.saveLocation(it.latitude, it.longitude)
                            val geocoder = Geocoder(context, Locale.getDefault())
                            val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)

                            addresses?.firstOrNull()?.let { addr ->
                                val street = addr.thoroughfare ?: ""
                                val house = addr.subThoroughfare ?: ""
                                val zip = addr.postalCode ?: ""
                                val city = addr.locality ?: ""

                                settingsManager.saveStreet(street)
                                settingsManager.saveHouseNumber(house)
                                settingsManager.saveZipCode(zip)
                                settingsManager.saveCity(city)

                                val verifyCity = settingsManager.getCity()

                                launch(Dispatchers.Main) {
                                    updateAddress(street, house, zip, city)
                                    speechText.value = if (verifyCity == city) {
                                        "ERFOLG: $city gespeichert!"
                                    } else {
                                        "FEHLER: Speicher-Mismatch!"
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            launch(Dispatchers.Main) {
                                speechText.value = "GPS Fehler: ${e.message}"
                            }
                        }
                    }
                }
            }
    }

    fun petMixer() {
        if (isInteractionLocked.value) return
        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true
            addHeartsWithMultiplier(1)
            delay(4000)
            showHearts.value = false
            isInteractionLocked.value = false
        }
    }

    fun useTool(tool: ToolType) {
        if (isInteractionLocked.value) return
        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true
            when(tool) {
                ToolType.FOOD -> addHeartsWithMultiplier(5)
                else -> addHeartsWithMultiplier(1)
            }
            delay(4000)
            showHearts.value = false
            isInteractionLocked.value = false
        }
    }

    private fun addHeartsWithMultiplier(basePoints: Int) {
        val pointsToAdd = (basePoints * heartMultiplier.value).toInt()
        val newTotal = totalHearts.value + pointsToAdd
        totalHearts.value = newTotal
        settingsManager.saveHearts(newTotal)
    }

    fun saveAllSettingsWithGeocoding(onComplete: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                settingsManager.saveUserName(userName.value)
                settingsManager.saveStreet(userStreet.value)
                settingsManager.saveHouseNumber(userHouseNumber.value)
                settingsManager.saveZipCode(userZipCode.value)
                settingsManager.saveCity(userCity.value)
                launch(Dispatchers.Main) { onComplete(true, "Gespeichert!") }
            } catch (e: Exception) {
                launch(Dispatchers.Main) { onComplete(false, "Fehler!") }
            }
        }
    }

    fun selectTool(tool: ToolType?) {
        activeTool.value = tool
    }
}
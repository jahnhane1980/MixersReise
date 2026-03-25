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

    // --- GAME STATES (Absolut unverändert) ---
    var totalHearts = mutableStateOf(settingsManager.getHearts())
    var isInteractionLocked = mutableStateOf(false)
    var showHearts = mutableStateOf(false)
    var activeTool = mutableStateOf<ToolType?>(ToolType.HAND)
    var speechText = mutableStateOf("")
    var isSleeping = mutableStateOf(false)
    var droolAlpha = mutableStateOf(0f)

    var heartMultiplier = mutableStateOf(1.0f)

    // --- SETTINGS STATES ---
    var userName = mutableStateOf(settingsManager.getUserName() ?: "")
    var userStreet = mutableStateOf(settingsManager.getStreet() ?: "")
    var userHouseNumber = mutableStateOf(settingsManager.getHouseNumber() ?: "")
    var userZipCode = mutableStateOf(settingsManager.getZipCode() ?: "")
    var userCity = mutableStateOf(settingsManager.getCity() ?: "")
    var currentDestination = mutableStateOf("Berlin")

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    init {
        val savedName = settingsManager.getUserName() ?: ""
        speechText.value = if (savedName.isBlank()) "Hallo!" else "Hallo $savedName!"

        viewModelScope.launch {
            launch { delay(15000); speechText.value = "" }

            try {
                delay(2500)
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location == null) return@addOnSuccessListener

                    val homeLat = settingsManager.getLatitude()
                    val homeLon = settingsManager.getLongitude()

                    if (homeLat != 0.0 && homeLon != 0.0) {
                        val results = FloatArray(1)
                        Location.distanceBetween(homeLat, homeLon, location.latitude, location.longitude, results)
                        val distanceKm = (results[0] / 1000).toInt()

                        heartMultiplier.value = when {
                            distanceKm < 2 -> 1.0f
                            distanceKm < 10 -> 1.5f
                            distanceKm < 50 -> 2.0f
                            else -> 3.0f
                        }

                        speechText.value = "Du bist $distanceKm km entfernt. Bonus: x${heartMultiplier.value}!"

                        viewModelScope.launch {
                            delay(5000)
                            speechText.value = ""
                        }
                    }
                }
            } catch (e: Exception) {
                delay(3000); speechText.value = ""
            }
        }
    }

    // --- KORRIGIERTE PUNKTE-LOGIK (R1: Suspend-Fix) ---
    private fun addHeartsWithMultiplier(basePoints: Int) {
        val pointsToAdd = (basePoints * heartMultiplier.value).toInt()

        // UI und SharedPreferences (Synchron)
        totalHearts.value += pointsToAdd
        settingsManager.saveHearts(totalHearts.value)

        // Datenbank (Asynchron in Coroutine - FIX)
        viewModelScope.launch(Dispatchers.IO) {
            currentDestination.value.let { cityName ->
                travelDao.addHeartsToCity(cityName, pointsToAdd)
            }
        }
    }

    // --- TOOL INTERAKTIONEN (R1: Keine erfundenen Enums) ---

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

            // FIX: Nur existierende ToolTypes verwenden
            when(tool) {
                ToolType.FOOD -> addHeartsWithMultiplier(5)
                else -> addHeartsWithMultiplier(1)
            }

            delay(4000)
            showHearts.value = false
            isInteractionLocked.value = false
        }
    }

    // --- SYSTEM-FUNKTIONEN (Unverändert) ---
    @SuppressLint("MissingPermission")
    fun detectLocationViaGps() {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                location?.let {
                    settingsManager.saveLocation(it.latitude, it.longitude)
                    val geocoder = Geocoder(context, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            val addr = addresses[0]
                            updateAddress(addr.thoroughfare ?: "", addr.subThoroughfare ?: "", addr.postalCode ?: "", addr.locality ?: "")
                        }
                    } catch (e: Exception) { /* ignore */ }
                }
            }
    }

    fun saveAllSettingsWithGeocoding(onComplete: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val fullAddress = "${userStreet.value} ${userHouseNumber.value}, ${userZipCode.value} ${userCity.value}"
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocationName(fullAddress, 1)
                if (!addresses.isNullOrEmpty()) {
                    val addr = addresses[0]
                    settingsManager.saveLocation(addr.latitude, addr.longitude)
                }
                settingsManager.saveUserName(userName.value)
                settingsManager.saveStreet(userStreet.value)
                settingsManager.saveHouseNumber(userHouseNumber.value)
                settingsManager.saveZipCode(userZipCode.value)
                settingsManager.saveCity(userCity.value)
                onComplete(true, "Gespeichert!")
            } catch (e: Exception) { onComplete(false, "Fehler!") }
        }
    }

    fun updateUserName(name: String) { userName.value = name }
    fun updateAddress(street: String, house: String, zip: String, city: String) {
        userStreet.value = street; userHouseNumber.value = house; userZipCode.value = zip; userCity.value = city
    }
    fun selectTool(tool: ToolType?) { activeTool.value = tool }
}
package com.deinname.mixersreise.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.TravelDestination
import com.google.android.gms.location.*
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

    // --- GAME STATES (Wiederhergestellt) ---
    var totalHearts = mutableStateOf(settingsManager.getHearts())
    var isInteractionLocked = mutableStateOf(false)
    var showHearts = mutableStateOf(false)
    var activeTool = mutableStateOf<ToolType?>(ToolType.HAND)
    var speechText = mutableStateOf("")
    var isSleeping = mutableStateOf(false) // Fix: Wieder da
    var droolAlpha = mutableStateOf(0f)    // Fix: Wieder da

    var heartMultiplier = mutableStateOf(1.0f)
    var currentDestination = mutableStateOf("Heimat")

    // --- SETTINGS STATES ---
    var userName = mutableStateOf(settingsManager.getUserName().let { if (it.isNullOrBlank()) "Entdecker" else it })
    var userStreet = mutableStateOf(settingsManager.getStreet() ?: "")
    var userHouseNumber = mutableStateOf(settingsManager.getHouseNumber() ?: "")
    var userZipCode = mutableStateOf(settingsManager.getZipCode() ?: "")
    var userCity = mutableStateOf(settingsManager.getCity() ?: "")

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    init {
        // Initial-Check: Name sichern
        if (settingsManager.getUserName().isNullOrBlank()) {
            settingsManager.saveUserName("Entdecker")
        }

        // Standort-Initialisierung
        val cityInStore = settingsManager.getCity()
        if (cityInStore.isNullOrBlank()) {
            detectLocationViaGps()
        } else {
            speechText.value = "Willkommen zurück!"
        }
    }

    // --- UI METHODS (Wiederhergestellt) ---

    fun selectTool(tool: ToolType?) { // Fix: Wieder da
        activeTool.value = tool
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

    // --- LOCATION LOGIC (Mit Warte-Zustand) ---

    @SuppressLint("MissingPermission")
    fun detectLocationViaGps() {
        speechText.value = "Warte auf Rückmeldung deiner Geodaten..."

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setMaxUpdates(1)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    processLocation(location)
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun processLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                settingsManager.saveLocation(location.latitude, location.longitude)
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                addresses?.firstOrNull()?.let { addr ->
                    val street = addr.thoroughfare ?: ""
                    val house = addr.subThoroughfare ?: ""
                    val zip = addr.postalCode ?: ""
                    val city = addr.locality ?: ""

                    // PHYSISCH SPEICHERN
                    settingsManager.saveStreet(street)
                    settingsManager.saveHouseNumber(house)
                    settingsManager.saveZipCode(zip)
                    settingsManager.saveCity(city)

                    launch(Dispatchers.Main) {
                        updateAddress(street, house, zip, city)
                        speechText.value = "Ort gefunden: $city. Daten gesichert!"
                        delay(3000)
                        if (speechText.value.contains("Ort gefunden")) speechText.value = ""
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) { speechText.value = "Fehler beim Auflösen der Adresse." }
            }
        }
    }

    // --- INTERACTION METHODS ---

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
        val newTotal = (settingsManager.getHearts() + pointsToAdd)
        settingsManager.saveHearts(newTotal)
        totalHearts.value = newTotal
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
}
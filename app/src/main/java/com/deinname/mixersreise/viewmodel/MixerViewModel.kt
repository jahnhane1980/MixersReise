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

    // --- GAME STATES (R1: Physical Truth - Erhalten aus Repository) ---
    var totalHearts = mutableStateOf(settingsManager.getHearts())
    var isInteractionLocked = mutableStateOf(false)
    var showHearts = mutableStateOf(false)
    var activeTool = mutableStateOf<ToolType?>(ToolType.HAND)
    var speechText = mutableStateOf("")
    var isSleeping = mutableStateOf(false)
    var droolAlpha = mutableStateOf(0f)

    // --- SETTINGS STATES ---
    var userName = mutableStateOf(settingsManager.getUserName() ?: "")
    var userStreet = mutableStateOf(settingsManager.getStreet() ?: "")
    var userHouseNumber = mutableStateOf(settingsManager.getHouseNumber() ?: "")
    var userZipCode = mutableStateOf(settingsManager.getZipCode() ?: "")
    var userCity = mutableStateOf(settingsManager.getCity() ?: "")
    var currentDestination = mutableStateOf("Berlin")

    private var lastLat: Double = settingsManager.getLatitude()
    private var lastLon: Double = settingsManager.getLongitude()

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    // --- INITIALISIERUNG & BEGRÜSSUNG ---
    init {
        val savedName = settingsManager.getUserName() ?: ""
        val greeting = if (savedName.isBlank()) "Hallo!" else "Hallo $savedName!"
        speechText.value = greeting

        // Standort-Check für Distanz-Begrüßung
        viewModelScope.launch {
            try {
                fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location ->
                        location?.let { current ->
                            val homeLat = settingsManager.getLatitude()
                            val homeLon = settingsManager.getLongitude()

                            // Nur berechnen, wenn Heimatkoordinaten existieren
                            if (homeLat != 0.0 && homeLon != 0.0) {
                                val results = FloatArray(1)
                                Location.distanceBetween(homeLat, homeLon, current.latitude, current.longitude, results)
                                val distanceKm = (results[0] / 1000).toInt()
                                speechText.value = "$greeting Du bist $distanceKm km von deiner Heimat entfernt."
                            }
                        }
                    }
            } catch (e: SecurityException) {
                // Keine Berechtigung -> Nur Standard-Gruß
            }

            // Sprechblase nach 8 Sekunden ausblenden
            delay(8000)
            if (speechText.value.startsWith("Hallo")) {
                speechText.value = ""
            }
        }
    }

    // --- GAME LOGIK ---
    fun selectTool(tool: ToolType?) {
        activeTool.value = tool
    }

    fun petMixer() {
        if (isInteractionLocked.value) return
        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true
            totalHearts.value += 1
            settingsManager.saveHearts(totalHearts.value)

            // Stats in DB aktualisieren
            currentDestination.value.let { cityName ->
                travelDao.addHeartsToCity(cityName, 1)
            }

            delay(4000)
            showHearts.value = false
            isInteractionLocked.value = false
        }
    }

    // --- LOCATION & SETTINGS LOGIK ---
    @SuppressLint("MissingPermission")
    fun detectLocationViaGps() {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                location?.let {
                    lastLat = it.latitude
                    lastLon = it.longitude
                    // WICHTIG: Koordinaten für spätere Distanzberechnungen mitspeichern
                    settingsManager.saveLocation(lastLat, lastLon)

                    val geocoder = Geocoder(context, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            val addr = addresses[0]
                            updateAddress(
                                street = addr.thoroughfare ?: "",
                                house = addr.subThoroughfare ?: "",
                                zip = addr.postalCode ?: "",
                                city = addr.locality ?: ""
                            )
                        }
                    } catch (e: Exception) {
                        speechText.value = "GPS-Fehler: ${e.message}"
                    }
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
                    lastLat = addr.latitude
                    lastLon = addr.longitude
                    settingsManager.saveLocation(lastLat, lastLon)
                }

                // Persistenz aller Felder im SettingsManager
                settingsManager.saveUserName(userName.value)
                settingsManager.saveStreet(userStreet.value)
                settingsManager.saveHouseNumber(userHouseNumber.value)
                settingsManager.saveZipCode(userZipCode.value)
                settingsManager.saveCity(userCity.value)

                onComplete(true, "Daten erfolgreich gespeichert!")
            } catch (e: Exception) {
                onComplete(false, "Fehler beim Speichern: ${e.message}")
            }
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
}
package com.deinname.mixersreise.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
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

    // --- WIEDERHERGESTELLTE GAME-STATES (Fix für Unresolved References) ---
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

    private var lastLat: Double = settingsManager.getLatitude()
    private var lastLon: Double = settingsManager.getLongitude()

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    // --- GAME LOGIK (Fix für petMixer/selectTool) ---
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
            // Hinweis: currentDestination Logik hier optional wieder einfügen falls im Repo vorhanden
            delay(4000)
            showHearts.value = false
            isInteractionLocked.value = false
        }
    }

    // --- LOCATION LOGIK (Die neue Funktion) ---
    @SuppressLint("MissingPermission")
    fun detectLocationViaGps() {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                location?.let {
                    lastLat = it.latitude
                    lastLon = it.longitude
                    settingsManager.saveLocation(lastLat, lastLon)

                    val geocoder = Geocoder(context, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            val addr = addresses[0]
                            updateAddressState(
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

    fun updateAddressState(street: String, house: String, zip: String, city: String) {
        userStreet.value = street
        userHouseNumber.value = house
        userZipCode.value = zip
        userCity.value = city
    }
}
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
import com.deinname.mixersreise.data.TalkOption
import com.deinname.mixersreise.data.mixerTalkOptions
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Locale
import kotlinx.coroutines.withContext

class MixerViewModel(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val externalScope: CoroutineScope,
    private val context: Context
) : ViewModel() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var gpsCheckJob: Job? = null

    // --- GAME STATES ---
    var totalHearts = mutableStateOf(settingsManager.getHearts())
    var isInteractionLocked = mutableStateOf(false)
    var showHearts = mutableStateOf(false)
    var activeTool = mutableStateOf<ToolType?>(ToolType.HAND)
    var showTalkMenu = mutableStateOf(false)
    var speechText = mutableStateOf("")
    var isSleeping = mutableStateOf(false)
    var droolAlpha = mutableStateOf(0f)

    var heartMultiplier = mutableStateOf(1.0f)
    var currentDestination = mutableStateOf("Heimat")

    // Beleg: Zugriff auf die statischen Optionen des Handlers
    val talkOptions: List<TalkOption> = MixerInteractionHandler.mixerTalkOptions

    // --- SETTINGS STATES ---
    var userName = mutableStateOf(settingsManager.getUserName() ?: "Entdecker")
    var userStreet = mutableStateOf(settingsManager.getStreet() ?: "")
    var userHouseNumber = mutableStateOf(settingsManager.getHouseNumber() ?: "")
    var userZipCode = mutableStateOf(settingsManager.getZipCode() ?: "")
    var userCity = mutableStateOf(settingsManager.getCity() ?: "")

    val allDestinations: Flow<List<TravelDestination>> = travelDao.getAllDestinations()

    init {
        viewModelScope.launch {
            val dbSum = withContext(Dispatchers.IO) {
                travelDao.getTotalHeartsSum() ?: 0
            }
            settingsManager.saveHearts(dbSum)
            totalHearts.value = dbSum

            val savedName = settingsManager.getUserName()
            if (savedName.isNullOrBlank()) {
                settingsManager.saveUserName("Entdecker")
                userName.value = "Entdecker"
            } else {
                userName.value = savedName
            }

            if (settingsManager.getCity().isNullOrBlank()) {
                detectLocationViaGps()
            } else {
                val name = settingsManager.getUserName() ?: "Entdecker"
                speechText.value = "Hallo $name!"
            }
        }
    }

    fun selectTool(tool: ToolType?) {
        if (isInteractionLocked.value) return
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

    fun handleTalkOptionSelected(option: TalkOption) {
        showTalkMenu.value = false
        speechText.value = option.answer
        viewModelScope.launch {
            isInteractionLocked.value = true
            addHeartsWithMultiplier(2)
            delay(4000)
            if (speechText.value == option.answer) speechText.value = ""
            isInteractionLocked.value = false
        }
    }

    fun useTool(tool: ToolType) {
        if (isInteractionLocked.value) return

        if (tool == ToolType.TALK) {
            showTalkMenu.value = true
            return
        }

        viewModelScope.launch {
            isInteractionLocked.value = true
            showHearts.value = true
            when (tool) {
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
        val newTotal = settingsManager.getHearts() + pointsToAdd
        settingsManager.saveHearts(newTotal)
        totalHearts.value = newTotal

        val currentCity = settingsManager.getCity() ?: return

        viewModelScope.launch(Dispatchers.IO) {
            val existing = travelDao.getDestinationByName(currentCity)
            val destinationToSave = if (existing != null) {
                existing.copy(heartsCollected = existing.heartsCollected + pointsToAdd)
            } else {
                TravelDestination(
                    cityName = currentCity,
                    heartsCollected = pointsToAdd,
                    isDiscovered = true
                )
            }
            travelDao.insertDestination(destinationToSave)
        }
    }

    @SuppressLint("MissingPermission")
    fun detectLocationViaGps() {
        gpsCheckJob?.cancel()
        gpsCheckJob = viewModelScope.launch {
            isInteractionLocked.value = true
            var locationFound = false
            var attempts = 0
            val maxAttempts = 2

            while (!locationFound && attempts < maxAttempts) {
                speechText.value = if (attempts == 0)
                    "Suche Geodaten... Bitte warten."
                else
                    "Zweiter Versuch... Oberfläche bleibt gesperrt."

                val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000)
                    .setMaxUpdates(1)
                    .build()

                val currentAttemptJob = launch {
                    val locationCallback = object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            result.lastLocation?.let {
                                locationFound = true
                                processLocation(it)
                            }
                        }
                    }

                    try {
                        fusedLocationClient.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )
                        delay(6000)
                    } finally {
                        fusedLocationClient.removeLocationUpdates(locationCallback)
                    }
                }

                currentAttemptJob.join()

                if (!locationFound) {
                    attempts++
                    if (attempts >= maxAttempts) {
                        speechText.value = "Kein GPS-Fix möglich. Bitte Ort manuell eingeben!"
                        delay(4000)
                        speechText.value = ""
                        isInteractionLocked.value = false
                    }
                }
            }
            if (locationFound) {
                isInteractionLocked.value = false
            }
        }
    }

    private fun processLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                addresses?.firstOrNull()?.let { addr ->
                    val street = addr.thoroughfare ?: ""
                    val house = addr.subThoroughfare ?: ""
                    val zip = addr.postalCode ?: ""
                    val city = addr.locality ?: ""

                    settingsManager.saveLocation(location.latitude, location.longitude)
                    settingsManager.saveStreet(street)
                    settingsManager.saveHouseNumber(house)
                    settingsManager.saveZipCode(zip)
                    settingsManager.saveCity(city)

                    launch(Dispatchers.Main) {
                        updateAddress(street, house, zip, city)
                        speechText.value = "Ort erkannt: $city!"
                        delay(3000)
                        speechText.value = ""
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    speechText.value = "Adress-Fehler. Internetverbindung prüfen."
                }
            }
        }
    }

    fun saveAllSettingsWithGeocoding(onComplete: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                settingsManager.saveUserName(userName.value)
                settingsManager.saveStreet(userStreet.value)
                settingsManager.saveHouseNumber(userHouseNumber.value)
                settingsManager.saveZipCode(userZipCode.value)
                settingsManager.saveCity(userCity.value)
                launch(Dispatchers.Main) {
                    onComplete(true, "Gespeichert!")
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    onComplete(false, "Fehler!")
                }
            }
        }
    }
}
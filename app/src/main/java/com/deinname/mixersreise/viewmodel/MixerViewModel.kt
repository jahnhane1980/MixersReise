package com.deinname.mixersreise.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.*
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class ToolType { HAND, SPONGE, FOOD, COKE, TALK }

class MixerViewModel(
    private val settings: SettingsManager,
    private val travelDao: TravelDao,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {

    // --- Status Werte ---
    var totalHearts by mutableStateOf(settings.totalHearts)
    var droolAlpha by mutableStateOf(0f)
    var currentMultiplier by mutableStateOf(1.0f)

    // --- Knuddel Logik ---
    var isPettingWanted by mutableStateOf(false)
    var petCount by mutableStateOf(0)
    private val MAX_PETS = 15

    // --- Dialog System ---
    var activeDialog by mutableStateOf<MixerDialog?>(null)
    var mixerResponseText by mutableStateOf("")

    val level get() = (totalHearts / 1000) + 1
    val isBaby get() = level < 5

    init {
        updateLocationMultiplier()
    }

    @SuppressLint("MissingPermission")
    fun updateLocationMultiplier() {
        fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
            loc?.let {
                // ... (Distanzberechnung bleibt gleich wie zuvor)
                currentMultiplier = 1.0f // Platzhalter
            }
        }
    }

    fun triggerPettingDesire() {
        if (!isPettingWanted) {
            isPettingWanted = true
            petCount = 0
            mixerResponseText = "" // Alten Text löschen
        }
    }

    fun isToolEnabled(tool: ToolType): Boolean = when(tool) {
        ToolType.HAND -> isPettingWanted
        ToolType.SPONGE -> droolAlpha > 0.1f
        else -> true
    }

    fun useTool(tool: ToolType, city: String) {
        if (!isToolEnabled(tool)) return

        if (tool == ToolType.HAND) {
            petCount++
            addHearts(5, city)

            if (petCount >= MAX_PETS) {
                isPettingWanted = false
                petCount = 0
                showTemporaryMessage("Vielen Dank! Jetzt bin ich ordentlich durchgekuschelt. 😊")
            }
        } else if (tool == ToolType.TALK) {
            activeDialog = MixerDialog("Mir ist langweilig!", listOf("Witz" to "Haha! 😂", "Reise" to "Au ja! ✈️"))
        } else {
            addHearts(20, city)
        }
    }

    private fun showTemporaryMessage(message: String) {
        mixerResponseText = message
        viewModelScope.launch {
            delay(5000) // 5 Sekunden warten
            if (mixerResponseText == message) {
                mixerResponseText = ""
            }
        }
    }

    private fun addHearts(base: Int, city: String) {
        val final = (base * currentMultiplier).toInt()
        totalHearts += final
        settings.totalHearts = totalHearts
        viewModelScope.launch {
            travelDao.insertPoint(TravelPoint(cityName = city, latitude = 0.0, longitude = 0.0, heartsCollected = final))
        }
    }

    fun onSpongeStroke() {
        droolAlpha = (droolAlpha - 0.1f).coerceAtLeast(0f)
        if (droolAlpha <= 0f) useTool(ToolType.SPONGE, "Zuhause")
    }

    fun selectDialogOption(reaction: String) {
        mixerResponseText = ""
        activeDialog = null
        showTemporaryMessage(reaction)
    }
}
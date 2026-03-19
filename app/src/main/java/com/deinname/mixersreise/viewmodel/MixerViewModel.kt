package com.deinname.mixersreise.viewmodel

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.*
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

enum class ToolType { HAND, SPONGE, FOOD, COKE, TALK }

class MixerViewModel(
    private val settings: SettingsManager,
    private val travelDao: TravelDao,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {

    var totalHearts by mutableStateOf(settings.totalHearts)
    var hunger by mutableStateOf(100f)
    var hygiene by mutableStateOf(100f)
    var droolAlpha by mutableStateOf(0f)
    var currentMultiplier by mutableStateOf(1.0f)
    var activeDialog by mutableStateOf<MixerDialog?>(null)
    var mixerResponseText by mutableStateOf("")

    val level get() = (totalHearts / 1000) + 1
    val isBaby get() = level < 5

    init { updateLocationMultiplier() }

    @SuppressLint("MissingPermission")
    fun updateLocationMultiplier() {
        fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
            loc?.let {
                val results = FloatArray(1)
                Location.distanceBetween(settings.homeLat.toDouble(), settings.homeLng.toDouble(), it.latitude, it.longitude, results)
                val dist = results[0] / 1000
                currentMultiplier = when {
                    dist < 10 -> 1.0f
                    dist < 50 -> 1.5f
                    else -> 5.0f
                }
            }
        }
    }

    fun isToolEnabled(tool: ToolType): Boolean = when(tool) {
        ToolType.FOOD -> hunger < 90f
        ToolType.COKE -> hunger < 95f
        ToolType.SPONGE -> hygiene < 30f
        else -> true
    }

    @SuppressLint("MissingPermission")
    fun useTool(tool: ToolType, city: String) {
        if (!isToolEnabled(tool)) return
        if (tool == ToolType.TALK) {
            activeDialog = MixerDialog("Mir ist langweilig!", listOf("Witz" to "Haha! 😂", "Reise" to "Au ja! ✈️"))
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
            val lat = loc?.latitude ?: settings.homeLat.toDouble()
            val lng = loc?.longitude ?: settings.homeLng.toDouble()
            val base = when(tool) {
                ToolType.HAND -> 5
                ToolType.FOOD -> 20
                ToolType.COKE -> 15
                ToolType.SPONGE -> 10
                else -> 0
            }
            val penalty = if (settings.notificationSentTime > 0) {
                val diff = (System.currentTimeMillis() - settings.notificationSentTime) / 60000
                if (diff > 5) ((diff - 5) / 5 * 10).toInt().coerceAtMost(100) else 0
            } else 0
            val final = ((base * currentMultiplier) - penalty).toInt().coerceAtLeast(-100)
            totalHearts += final
            settings.totalHearts = totalHearts
            settings.notificationSentTime = 0L
            if (tool == ToolType.FOOD || tool == ToolType.COKE) hunger = 100f
            viewModelScope.launch { travelDao.insertPoint(TravelPoint(cityName = city, latitude = lat, longitude = lng, heartsCollected = final)) }
        }
    }

    fun onSpongeStroke() {
        if (droolAlpha > 0f) {
            droolAlpha = (droolAlpha - 0.1f).coerceAtLeast(0f)
            if (droolAlpha <= 0f) { hygiene = 100f; useTool(ToolType.SPONGE, "Zuhause") }
        }
    }

    fun selectDialogOption(reaction: String) {
        mixerResponseText = reaction
        activeDialog = null
        useTool(ToolType.TALK, "Zuhause")
    }
}
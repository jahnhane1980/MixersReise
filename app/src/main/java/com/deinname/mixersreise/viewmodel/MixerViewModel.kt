package com.deinname.mixersreise.viewmodel

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import com.deinname.mixersreise.data.TravelPoint
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

class MixerViewModel(
    private val settings: SettingsManager?,
    private val travelDao: TravelDao?,
    private val fusedLocationClient: FusedLocationProviderClient?
) : ViewModel() {

    private val logic = MixerLogic()

    var totalHearts by mutableIntStateOf(settings?.totalHearts ?: 0)
    var hunger by mutableFloatStateOf(100f)
    var droolAlpha by mutableFloatStateOf(0f)
    var currentMultiplier by mutableFloatStateOf(1.0f)
    var isPettingWanted by mutableStateOf(false)
    var mixerResponseText by mutableStateOf("")

    val level: Int get() = (totalHearts / 1000) + 1

    init {
        updateLocationMultiplier()
    }

    fun isToolEnabled(tool: ToolType): Boolean = when(tool) {
        ToolType.HAND -> isPettingWanted
        ToolType.SPONGE -> droolAlpha > 0.1f
        else -> true
    }

    @SuppressLint("MissingPermission")
    fun updateLocationMultiplier() {
        fusedLocationClient?.lastLocation?.addOnSuccessListener { loc ->
            loc?.let {
                val results = FloatArray(1)
                Location.distanceBetween(
                    settings?.homeLat?.toDouble() ?: 50.9375,
                    settings?.homeLng?.toDouble() ?: 6.9603,
                    it.latitude, it.longitude, results
                )
                currentMultiplier = if (results[0] > 50000) 5.0f else 1.0f
            }
        }
    }

    fun useTool(tool: ToolType, city: String) {
        mixerResponseText = logic.getMixerResponse(tool, isPettingWanted)
        val basePoints = if (tool == ToolType.HAND) 5 else 20
        val gained = (basePoints * currentMultiplier).toInt()
        totalHearts += gained
        settings?.totalHearts = totalHearts

        viewModelScope.launch {
            travelDao?.insertPoint(
                TravelPoint(
                    cityName = city,
                    heartsCollected = gained,
                    latitude = 0.0,
                    longitude = 0.0
                )
            )
        }
    }

    fun onSpongeStroke() {
        droolAlpha = (droolAlpha - 0.15f).coerceAtLeast(0f)
    }
}
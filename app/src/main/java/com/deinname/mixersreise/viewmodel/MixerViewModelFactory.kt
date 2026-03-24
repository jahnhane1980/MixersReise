package com.deinname.mixersreise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import kotlinx.coroutines.CoroutineScope

class MixerViewModelFactory(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val scope: CoroutineScope
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MixerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Jetzt synchron mit dem aktualisierten MixerViewModel Konstruktor
            return MixerViewModel(travelDao, settingsManager, scope) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
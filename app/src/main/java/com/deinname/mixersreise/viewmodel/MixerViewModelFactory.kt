package com.deinname.mixersreise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deinname.mixersreise.data.SettingsManager

class MixerViewModelFactory(
    private val settingsManager: SettingsManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MixerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MixerViewModel(settingsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
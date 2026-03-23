package com.deinname.mixersreise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MixerViewModelFactory(private val settingsManager: Any) : ViewModelProvider.Factory {
    // HINWEIS: Ersetze 'Any' wieder durch deinen echten Typ

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MixerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MixerViewModel(settingsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
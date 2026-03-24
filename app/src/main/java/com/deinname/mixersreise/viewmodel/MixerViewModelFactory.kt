package com.deinname.mixersreise.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import kotlinx.coroutines.CoroutineScope

class MixerViewModelFactory(
    private val travelDao: TravelDao,
    private val settingsManager: SettingsManager,
    private val scope: CoroutineScope,
    private val context: Context // R2: Sync für GPS Support
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MixerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MixerViewModel(travelDao, settingsManager, scope, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
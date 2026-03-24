package com.deinname.mixersreise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deinname.mixersreise.data.TravelDao

class MixerViewModelFactory(
    private val travelDao: TravelDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MixerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MixerViewModel(travelDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.deinname.mixersreise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.TravelDao
import com.google.android.gms.location.FusedLocationProviderClient

/**
 * Die Factory ist der "Bauplan" für das ViewModel.
 * Sie erlaubt es uns, eigene Parameter (wie Datenbank und GPS-Client)
 * an das ViewModel zu übergeben.
 */
class MixerViewModelFactory(
    private val settings: SettingsManager,
    private val dao: TravelDao,
    private val locationClient: FusedLocationProviderClient
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Wir prüfen, ob die angeforderte Klasse wirklich unser MixerViewModel ist
        if (modelClass.isAssignableFrom(MixerViewModel::class.java)) {
            return MixerViewModel(settings, dao, locationClient) as T
        }
        // Falls eine falsche Klasse angefragt wird, werfen wir einen Fehler
        throw IllegalArgumentException("Unbekannte ViewModel-Klasse: ${modelClass.name}")
    }
}
package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deinname.mixersreise.data.AppDatabase
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Scope definieren, damit er für die Datenbank verfügbar ist
        val scope = lifecycleScope

        // 2. Datenbank-Instanz mit Context UND Scope initialisieren
        val database = AppDatabase.getDatabase(this, scope)
        val travelDao = database.travelDao()

        val settingsManager = SettingsManager(this)
        val locationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MixersReiseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 3. ViewModel-Initialisierung
                    val mixerViewModel: MixerViewModel = viewModel(
                        factory = MixerViewModelFactory(
                            travelDao = travelDao,
                            settingsManager = settingsManager,
                            scope = scope,
                            locationClient = locationClient
                        )
                    )

                    HomeScreen(
                        viewModel = mixerViewModel,
                        onOpenMap = { },
                        onOpenSettings = { }
                    )
                }
            }
        }
    }
}
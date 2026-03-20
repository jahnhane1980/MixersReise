package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.lifecycleScope
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.data.AppDatabase
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.ui.components.MixerToolBar
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisierung
        val settings = SettingsManager(this)
        val database = AppDatabase.getDatabase(this, lifecycleScope)
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MixersReiseTheme {
                val mixerViewModel: MixerViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return MixerViewModel(settings, database.travelDao(), fusedLocationClient) as T
                        }
                    }
                )

                Scaffold(
                    topBar = {
                        MixerTopBar(
                            level = mixerViewModel.level,
                            hearts = mixerViewModel.totalHearts,
                            onOpenMap = { },
                            onOpenSettings = { }
                        )
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Box(modifier = Modifier.weight(1f)) {
                            // Hier kommt dein MixerWorldScreen rein, falls vorhanden
                        }
                        MixerToolBar(viewModel = mixerViewModel)
                    }
                }
            }
        }
    }
}
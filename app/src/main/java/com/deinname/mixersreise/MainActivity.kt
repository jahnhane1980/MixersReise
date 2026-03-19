package com.deinname.mixersreise

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel // DIESER IMPORT GEHT JETZT
import androidx.work.*
import com.deinname.mixersreise.data.AppDatabase
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.screens.MapScreen
import com.deinname.mixersreise.ui.components.SettingsDialog
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory
import com.deinname.mixersreise.worker.MixerWorker
import com.google.android.gms.location.LocationServices
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settings = SettingsManager(this)
        val database = AppDatabase.getDatabase(this, lifecycleScope)
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}
        requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))

        setContent {
            MixersReiseTheme {
                val mixerViewModel: MixerViewModel = viewModel(factory = MixerViewModelFactory(settings, database.travelDao(), fusedLocationClient))
                var showMap by remember { mutableStateOf(false) }
                var showSettings by remember { mutableStateOf(false) }

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (showMap) {
                        val pts by database.travelDao().getAllPoints().collectAsState(initial = emptyList())
                        MapScreen(pts, settings.googleApiKey, onClose = { showMap = false })
                    } else {
                        HomeScreen(mixerViewModel, onOpenMap = { showMap = true }, onOpenSettings = { showSettings = true })
                    }
                    if (showSettings) SettingsDialog(settings, onDismiss = { showSettings = false })
                }
            }
        }
    }
}
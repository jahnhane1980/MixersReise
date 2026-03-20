package com.deinname.mixersreise

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.lifecycleScope
import com.deinname.mixersreise.data.AppDatabase
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MIXER_BOOT", "App startet...")

        val settings = try { SettingsManager(this) } catch (e: Exception) { null }
        val database = try { AppDatabase.getDatabase(this, lifecycleScope) } catch (e: Exception) { null }
        val fusedLocationClient = try { LocationServices.getFusedLocationProviderClient(this) } catch (e: Exception) { null }

        setContent {
            MaterialTheme {
                val mixerViewModel: MixerViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return MixerViewModel(settings, database?.travelDao(), fusedLocationClient) as T
                        }
                    }
                )

                val locationPermissionRequest = registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { mixerViewModel.updateLocationMultiplier() }

                LaunchedEffect(Unit) {
                    locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Hier muss dein UI-Screen-Aufruf rein, falls er in einer anderen Datei liegt
                    // Z.B. MixerMainContent(mixerViewModel)
                }
            }
        }
    }
}
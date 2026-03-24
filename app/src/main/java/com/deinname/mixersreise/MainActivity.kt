package com.deinname.mixersreise

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.deinname.mixersreise.data.AppDatabase
import com.deinname.mixersreise.data.SettingsManager
// R1.1 Quittung: Diese beiden Imports sind zwingend für die Navigation
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.screens.MapScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scope = lifecycleScope
        val database = AppDatabase.getDatabase(applicationContext, scope)
        val travelDao = database.travelDao()
        val settingsManager = SettingsManager(applicationContext)

        val viewModel: MixerViewModel by viewModels {
            MixerViewModelFactory(travelDao, settingsManager, scope)
        }

        setContent {
            MixersReiseTheme {
                // Navigation State
                var currentScreen by remember { mutableStateOf("home") }

                when (currentScreen) {
                    "home" -> HomeScreen(
                        viewModel = viewModel,
                        onOpenMap = {
                            Log.d("MixerNav", "Navigiere zu Map")
                            currentScreen = "map"
                        }
                    )
                    "map" -> MapScreen(
                        viewModel = viewModel,
                        onBack = {
                            Log.d("MixerNav", "Navigiere zu Home")
                            currentScreen = "home"
                        }
                    )
                }
            }
        }
    }
}
package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.deinname.mixersreise.data.AppDatabase
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.screens.MapScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsManager = SettingsManager(applicationContext)
        val database = AppDatabase.getDatabase(applicationContext, lifecycleScope)

        val viewModel: MixerViewModel by viewModels {
            MixerViewModelFactory(database.travelDao(), settingsManager, lifecycleScope)
        }

        setContent {
            MixersReiseTheme {
                // R6: Stabiles State-basiertes Navigations-System ohne externe Library
                var currentScreen by remember { mutableStateOf("home") }

                when (currentScreen) {
                    "home" -> HomeScreen(
                        viewModel = viewModel,
                        onOpenMap = { currentScreen = "map" }
                    )
                    "map" -> MapScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = "home" }
                    )
                }
            }
        }
    }
}
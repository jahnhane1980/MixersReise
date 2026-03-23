package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.screens.MapScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hier dein reales SettingsManager-Objekt einfügen
        val mySettingsManager = Any()

        setContent {
            MixersReiseTheme {
                val viewModel: MixerViewModel = viewModel(
                    factory = MixerViewModelFactory(mySettingsManager)
                )

                var currentScreen by remember { mutableStateOf("home") }

                when (currentScreen) {
                    "map" -> {
                        MapScreen(
                            viewModel = viewModel,
                            onBack = { currentScreen = "home" }
                        )
                    }
                    "home" -> {
                        HomeScreen(
                            viewModel = viewModel,
                            onOpenMap = { currentScreen = "map" } // FIX: Parameter wird jetzt erkannt
                        )
                    }
                    else -> {
                        HomeScreen(
                            viewModel = viewModel,
                            onOpenMap = { currentScreen = "map" }
                        )
                    }
                }
            }
        }
    }
}
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
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.screens.MapScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // R5: Physikalische Initialisierung der Abhängigkeiten für die Factory
        val database = AppDatabase.getDatabase(applicationContext)
        val travelDao = database.travelDao()
        val settingsManager = SettingsManager(applicationContext)
        val scope = lifecycleScope // Der CoroutineScope der Activity

        val viewModel: MixerViewModel by viewModels {
            MixerViewModelFactory(travelDao, settingsManager, scope)
        }

        setContent {
            MixersReiseTheme {
                var currentScreen by remember { mutableStateOf("home") }

                when (currentScreen) {
                    "home" -> HomeScreen(
                        viewModel = viewModel,
                        onOpenMap = {
                            Log.d("MixerNav", "Navigation zur Map getriggert")
                            currentScreen = "map"
                        }
                    )
                    "map" -> MapScreen(
                        viewModel = viewModel,
                        onBack = {
                            Log.d("MixerNav", "Zurück zum HomeScreen")
                            currentScreen = "home"
                        }
                    )
                }
            }
        }
    }
}
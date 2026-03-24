package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
        // Fix: getDatabase erwartet physisch nur context
        val database = AppDatabase.getDatabase(applicationContext)

        val viewModel: MixerViewModel by viewModels {
            MixerViewModelFactory(database.travelDao(), settingsManager, lifecycleScope)
        }

        setContent {
            MixersReiseTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onOpenMap = { navController.navigate("map") },
                            onNavigateToWorld = { navController.navigate("world") } // Hinzugefügt
                        )
                    }
                    composable("map") {
                        MapScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() } // Hinzugefügt
                        )
                    }
                    composable("world") {
                        MixerWorldScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
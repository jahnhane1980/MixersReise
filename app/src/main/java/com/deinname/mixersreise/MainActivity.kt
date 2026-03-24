package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deinname.mixersreise.data.AppDatabase
import com.deinname.mixersreise.data.SettingsManager
// R1.1 Quittung: Neue Import-Pfade für die Screens
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.screens.MapScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MixersReiseTheme {
                val navController = rememberNavController()
                val database = AppDatabase.getDatabase(this)
                val settingsManager = SettingsManager(this)
                val scope = rememberCoroutineScope()

                // Factory mit allen benötigten Abhängigkeiten
                val factory = MixerViewModelFactory(database.travelDao(), settingsManager, scope)
                val mixerViewModel: MixerViewModel = viewModel(factory = factory)

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            viewModel = mixerViewModel,
                            onOpenMap = { navController.navigate("map") }
                        )
                    }
                    composable("map") {
                        MapScreen(
                            viewModel = mixerViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
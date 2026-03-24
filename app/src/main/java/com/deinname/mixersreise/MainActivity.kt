package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deinname.mixersreise.data.AppDatabase
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val settingsManager = SettingsManager(this)

        val viewModel: MixerViewModel by viewModels {
            MixerViewModelFactory(database.travelDao(), settingsManager, lifecycleScope)
        }

        setContent {
            MixersReiseTheme {
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onNavigateToWorld = { navController.navigate("world") }
                        )
                    }
                    composable("world") {
                        MixerWorldScreen(
                            viewModel = viewModel
                            // onBack entfernt, da MixerWorldScreen diesen Parameter laut Fehler nicht hat
                        )
                    }
                }
            }
        }
    }
}
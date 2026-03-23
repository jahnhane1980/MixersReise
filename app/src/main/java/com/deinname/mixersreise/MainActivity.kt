package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.deinname.mixersreise.data.AppDatabase
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Datenbank und Settings initialisieren
        val database = AppDatabase.getDatabase(this)
        val settingsManager = SettingsManager(this)

        // R5: Physischer Check der Parameter-Reihenfolge:
        // 1. TravelDao, 2. SettingsManager, 3. CoroutineScope
        val viewModel: MixerViewModel by viewModels {
            MixerViewModelFactory(
                database.travelDao(),
                settingsManager,
                lifecycleScope
            )
        }

        setContent {
            MixersReiseTheme {
                // R5: Surface auf Transparent, damit das Bild im HomeScreen (Box) sichtbar ist
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    HomeScreen(
                        viewModel = viewModel,
                        onOpenMap = {
                            // Navigation zur Map (späteres Feature)
                        }
                    )
                }
            }
        }
    }
}
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

        val database = AppDatabase.getDatabase(this)
        val settingsManager = SettingsManager(this)

        // R5: Explizite Instanziierung der Factory zur Fehlervermeidung
        val factory = MixerViewModelFactory(
            database.travelDao(),
            settingsManager,
            lifecycleScope
        )

        val viewModel: MixerViewModel by viewModels { factory }

        setContent {
            MixersReiseTheme {
                // R5: Surface auf Transparent, damit Hintergrundbild im HomeScreen sichtbar ist
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    HomeScreen(
                        viewModel = viewModel,
                        onOpenMap = {
                            // Map Navigation
                        }
                    )
                }
            }
        }
    }
}
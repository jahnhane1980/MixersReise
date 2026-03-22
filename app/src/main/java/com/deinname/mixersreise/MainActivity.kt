package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.components.SettingsDialog
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory
import com.deinname.mixersreise.data.SettingsManager // Sicherstellen, dass der Import stimmt

class MainActivity : ComponentActivity() {

    // REPARATUR: Die Factory bekommt nur, was sie laut Fehlerbericht (Punkt 3) will.
    private val viewModel: MixerViewModel by viewModels {
        // Wir nutzen hier direkt eine Instanz des SettingsManagers
        MixerViewModelFactory(SettingsManager(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // State für den Settings-Dialog
            var showSettingsDialog by remember { mutableStateOf(false) }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Der HomeScreen
                    HomeScreen(
                        viewModel = viewModel,
                        onOpenSettings = { showSettingsDialog = true }
                    )

                    // REPARATUR: SettingsDialog-Aufruf passend zu deiner Signatur (Punkt 6/7)
                    if (showSettingsDialog) {
                        SettingsDialog(
                            onDismiss = { showSettingsDialog = false },
                            viewModel = viewModel // Erwartet laut Fehler das viewModel
                        )
                    }
                }
            }
        }
    }
}
package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialisierung des Managers
        val settingsManager = SettingsManager(this)

        // 2. Erstellung der Factory (Jetzt nur noch mit dem settingsManager)
        val viewModelFactory = MixerViewModelFactory(settingsManager)

        // 3. ViewModel über die Factory holen
        val viewModel = ViewModelProvider(this, viewModelFactory)[MixerViewModel::class.java]

        setContent {
            MixersReiseTheme {
                // 4. Den HomeScreen aufrufen
                HomeScreen(
                    viewModel = viewModel,
                    onOpenMap = {
                        // Hier kommt später die Navigation zur Reise-Tabelle (V1) rein
                    },
                    onOpenSettings = {
                        // Hier kommt die Navigation zum SettingsDialog rein
                    }
                )
            }
        }
    }
}
package com.deinname.mixersreise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.ui.components.MixerToolBar
import com.deinname.mixersreise.ui.components.SettingsDialog
import com.deinname.mixersreise.ui.screens.HomeScreen
import com.deinname.mixersreise.ui.screens.MapScreen
import com.deinname.mixersreise.ui.theme.MixersReiseTheme
import com.deinname.mixersreise.viewmodel.MixerViewModel
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory
import com.deinname.mixersreise.ui.components.ToolType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsManager = SettingsManager(this)
        val viewModelFactory = MixerViewModelFactory(settingsManager)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MixerViewModel::class.java]

        setContent {
            MixersReiseTheme {
                var showSettingsDialog by remember { mutableStateOf(false) }
                // NEU: Eigener State für die Karte, um ToolType.MAP zu ersetzen
                var isMapVisible by remember { mutableStateOf(false) }

                val activeTool by viewModel.activeTool

                Column(modifier = Modifier.fillMaxSize()) {
                    MixerTopBar(
                        hearts = viewModel.totalHearts.value,
                        level = viewModel.level,
                        onOpenSettings = { showSettingsDialog = true },
                        // KORREKTUR: Nutzt jetzt den Boolean statt das ViewModel-Tool
                        onOpenMap = { isMapVisible = !isMapVisible }
                    )

                    Box(modifier = Modifier.weight(1f)) {
                        // KORREKTUR: Prüfung erfolgt nun über den Boolean
                        if (isMapVisible) {
                            MapScreen(
                                points = emptyList(),
                                apiKey = "DEIN_API_KEY",
                                // KORREKTUR: Schließt die Karte über den Boolean
                                onClose = { isMapVisible = false }
                            )
                        } else {
                            HomeScreen(viewModel = viewModel)
                        }
                    }

                    MixerToolBar(
                        activeTool = activeTool,
                        onToolSelected = { tool ->
                            // Wenn ein Werkzeug gewählt wird, schließen wir die Karte
                            isMapVisible = false
                            viewModel.selectTool(tool)
                        }
                    )
                }

                if (showSettingsDialog) {
                    SettingsDialog(
                        onDismiss = { showSettingsDialog = false },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
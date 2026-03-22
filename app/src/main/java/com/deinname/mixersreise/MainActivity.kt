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
                var isMapVisible by remember { mutableStateOf(false) }

                val activeTool by viewModel.activeTool
                val currentHearts by viewModel.totalHearts

                // Testdaten für die Weltkarte
                val testDestinations = listOf("Berlin", "Paris", "New York", "Tokio")

                Column(modifier = Modifier.fillMaxSize()) {
                    MixerTopBar(
                        hearts = currentHearts,
                        level = viewModel.level,
                        onOpenSettings = { showSettingsDialog = true },
                        onOpenMap = { isMapVisible = !isMapVisible }
                    )

                    Box(modifier = Modifier.weight(1f)) {
                        if (isMapVisible) {
                            MapScreen(
                                points = testDestinations,
                                hearts = currentHearts, // Übergabe der Herzen an den MapScreen
                                apiKey = "DEIN_API_KEY",
                                onClose = { isMapVisible = false }
                            )
                        } else {
                            HomeScreen(viewModel = viewModel)
                        }
                    }

                    MixerToolBar(
                        activeTool = activeTool,
                        onToolSelected = { tool ->
                            // Beim Auswählen eines Werkzeugs schließen wir die Karte automatisch
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
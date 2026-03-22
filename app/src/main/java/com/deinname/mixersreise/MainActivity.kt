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
                // Wir nutzen hier direkt den State aus dem ViewModel
                val activeTool by viewModel.activeTool

                Column(modifier = Modifier.fillMaxSize()) {
                    // Fehlerbehebung MixerTopBar: Nutzt jetzt die Parameter aus deinem Repository
                    MixerTopBar(
                        hearts = viewModel.totalHearts.value,
                        level = viewModel.level,
                        onOpenSettings = { showSettingsDialog = true },
                        onOpenMap = { viewModel.selectTool(ToolType.MAP) }
                    )

                    // Box-Layout korrekt innerhalb des Composable-Contexts
                    Box(modifier = Modifier.weight(1f)) {
                        if (activeTool == ToolType.MAP) {
                            // MapScreen benötigt laut Fehlermeldung spezifische Parameter
                            MapScreen(
                                points = emptyList(), // Hier müssten später die echten Daten rein
                                apiKey = "DEIN_API_KEY",
                                onClose = { viewModel.selectTool(ToolType.NONE) }
                            )
                        } else {
                            HomeScreen(viewModel = viewModel)
                        }
                    }

                    MixerToolBar(
                        activeTool = activeTool,
                        onToolSelected = { viewModel.selectTool(it) }
                    )
                }

                if (showSettingsDialog) {
                    // Fehlerbehebung SettingsDialog: Erwartet 'viewModel' statt 'settingsManager'
                    SettingsDialog(
                        onDismiss = { showSettingsDialog = false },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
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
// KORREKTUR: Dieser Import hat gefehlt
import com.deinname.mixersreise.viewmodel.MixerViewModelFactory
import com.deinname.mixersreise.ui.components.ToolType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsManager = SettingsManager(this)
        // KORREKTUR: Hier wird die Factory jetzt durch den Import oben erkannt
        val viewModelFactory = MixerViewModelFactory(settingsManager)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MixerViewModel::class.java]

        setContent {
            MixersReiseTheme {
                var showSettingsDialog by remember { mutableStateOf(false) }
                var isMapVisible by remember { mutableStateOf(false) }

                val activeTool by viewModel.activeTool

                Column(modifier = Modifier.fillMaxSize()) {
                    MixerTopBar(
                        hearts = viewModel.totalHearts.value,
                        level = viewModel.level,
                        onOpenSettings = { showSettingsDialog = true },
                        onOpenMap = { isMapVisible = !isMapVisible }
                    )

                    Box(modifier = Modifier.weight(1f)) {
                        if (isMapVisible) {
                            MapScreen(
                                points = emptyList(),
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
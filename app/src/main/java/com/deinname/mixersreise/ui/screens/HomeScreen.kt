package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.deinname.mixersreise.MixerWorldScreen
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.MixerTopBar
import com.deinname.mixersreise.ui.components.MixerToolBar
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun HomeScreen(
    viewModel: MixerViewModel,
    onOpenSettings: () -> Unit
) {
    var showMapDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MixerTopBar(
                level = viewModel.level,
                hearts = viewModel.totalHearts,
                onOpenMap = { showMapDialog = true },
                onOpenSettings = onOpenSettings
            )
        },
        bottomBar = {
            MixerToolBar(viewModel = viewModel)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MixerWorldScreen(viewModel = viewModel)

            if (showMapDialog) {
                TravelTableDialog(
                    onDismiss = { showMapDialog = false },
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun TravelTableDialog(onDismiss: () -> Unit, viewModel: MixerViewModel) {
    // DialogProperties für Fullscreen-Optik (optional)
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(24.dp),
            color = Color.Black // Basis für den Fall, dass das Bild lädt
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // 1. Das Hintergrundbild aus dem Ressourcen-Ordner
                Image(
                    painter = painterResource(id = R.drawable.bg_world_map),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // 2. Halbtransparente weiße Schicht für die Tabelle
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .background(
                            Color.White.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Mixers Reiseziele",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Tabellen-Header
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Herzchen", style = MaterialTheme.typography.titleMedium, color = Color.Black)
                        Text("Ort", style = MaterialTheme.typography.titleMedium, color = Color.Black)
                    }

                    Divider(color = Color.Gray, thickness = 1.dp)

                    // Beispiel-Daten (später binden wir hier das ViewModel an)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("1000", color = Color.DarkGray)
                        Text("New York", color = Color.DarkGray)
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                    ) {
                        Text("Zurück zur Reise")
                    }
                }
            }
        }
    }
}
package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(24.dp),
            color = Color.Black
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Hintergrundbild
                Image(
                    painter = painterResource(id = R.drawable.bg_world_map),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Tabelle auf halbtransparentem Weiß
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .background(
                            Color.White.copy(alpha = 0.75f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Mixers Reiseziele",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Herzchen", style = MaterialTheme.typography.titleMedium, color = Color.Black)
                        Text("Ort", style = MaterialTheme.typography.titleMedium, color = Color.Black)
                    }

                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)

                    // Scrollbare Liste
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    ) {
                        item {
                            TravelRow(hearts = "1000", location = "New York")
                            TravelRow(hearts = "550", location = "Berlin")
                            TravelRow(hearts = "2100", location = "Tokio")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Button mit DarkCyan und weißem Text
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(0.8f)
                            .height(52.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.DarkCyan),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text(
                            text = "Zurück zur Reise",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TravelRow(hearts: String, location: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = hearts, color = Color.DarkGray, style = MaterialTheme.typography.bodyLarge)
            Text(text = location, color = Color.DarkGray, style = MaterialTheme.typography.bodyLarge)
        }
        HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.5.dp)
    }
}
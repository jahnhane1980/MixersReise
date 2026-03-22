package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp // DIESER IMPORT HAT GEFEHLT
import androidx.compose.ui.window.Dialog
import com.deinname.mixersreise.MixerWorldScreen
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
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White.copy(alpha = 0.95f)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Mixers Reiseziele",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text("Hier erscheinen bald deine gesammelten Herzen pro Ort.")

                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = onDismiss) {
                    Text("Schließen")
                }
            }
        }
    }
}
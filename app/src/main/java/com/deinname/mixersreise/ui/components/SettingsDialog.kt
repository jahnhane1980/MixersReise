package com.deinname.mixersreise.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun SettingsDialog(
    viewModel: MixerViewModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Einstellungen") },
        text = {
            Text("Aktuelles Level: ${viewModel.level}")
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Schließen")
            }
        }
    )
}
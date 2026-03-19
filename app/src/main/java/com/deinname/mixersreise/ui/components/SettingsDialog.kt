package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.data.SettingsManager

@Composable
fun SettingsDialog(
    settings: SettingsManager,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(settings.userName) }
    var apiKey by remember { mutableStateOf(settings.googleApiKey) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Einstellungen") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Dein Name") })
                Text("Google Maps API", style = MaterialTheme.typography.labelLarge)
                if (apiKey.isEmpty()) {
                    Text("Coming soon - API Key hier eingeben", color = MaterialTheme.colorScheme.secondary)
                }
                TextField(value = apiKey, onValueChange = { apiKey = it }, label = { Text("API Key") })
            }
        },
        confirmButton = {
            Button(onClick = {
                settings.userName = name
                settings.googleApiKey = apiKey
                onDismiss()
            }) { Text("Speichern") }
        }
    )
}
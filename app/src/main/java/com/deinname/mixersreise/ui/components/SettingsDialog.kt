package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.data.SettingsManager

@Composable
fun SettingsDialog(settings: SettingsManager, onDismiss: () -> Unit) {
    // Lokale Zustände für die Textfelder (Strings)
    var userName by remember { mutableStateOf(settings.userName) }
    var homeAddress by remember { mutableStateOf(settings.homeAddress) }
    var googleApiKey by remember { mutableStateOf(settings.googleApiKey) }
    var googleMapId by remember { mutableStateOf(settings.googleMapId) }
    var isTestMode by remember { mutableStateOf(settings.isTestModeActive) }

    // Koordinaten müssen als String im Textfeld stehen
    var homeLat by remember { mutableStateOf(settings.homeLat.toString()) }
    var homeLng by remember { mutableStateOf(settings.homeLng.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Einstellungen") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Benutzername") }
                )
                OutlinedTextField(
                    value = homeAddress,
                    onValueChange = { homeAddress = it },
                    label = { Text("Heimatadresse") }
                )
                OutlinedTextField(
                    value = homeLat,
                    onValueChange = { homeLat = it },
                    label = { Text("Heimat Breitengrad (Lat)") }
                )
                OutlinedTextField(
                    value = homeLng,
                    onValueChange = { homeLng = it },
                    label = { Text("Heimat Längengrad (Lng)") }
                )
                OutlinedTextField(
                    value = googleApiKey,
                    onValueChange = { googleApiKey = it },
                    label = { Text("Google API Key") }
                )
                OutlinedTextField(
                    value = googleMapId,
                    onValueChange = { googleMapId = it },
                    label = { Text("Google Map ID") }
                )
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Checkbox(checked = isTestMode, onCheckedChange = { isTestMode = it })
                    Text("Testmodus aktiv")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                // Speichern im Manager mit Konvertierung
                settings.userName = userName
                settings.homeAddress = homeAddress
                settings.googleApiKey = googleApiKey
                settings.googleMapId = googleMapId
                settings.isTestModeActive = isTestMode

                // Sicher von String zu Float konvertieren
                settings.homeLat = homeLat.toFloatOrNull() ?: 0f
                settings.homeLng = homeLng.toFloatOrNull() ?: 0f

                onDismiss()
            }) {
                Text("Speichern")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen")
            }
        }
    )
}
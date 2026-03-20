package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Diese Imports müssen exakt zu deiner Ordnerstruktur passen
import com.deinname.mixersreise.data.SettingsManager
import com.deinname.mixersreise.viewmodel.MixerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(
    settings: SettingsManager,
    viewModel: MixerViewModel,
    onDismiss: () -> Unit
) {
    // Lokale States für die Textfelder
    var userName by remember { mutableStateOf(settings.userName) }
    var homeAddress by remember { mutableStateOf(settings.homeAddress) }
    var googleApiKey by remember { mutableStateOf(settings.googleApiKey) }
    var googleMapId by remember { mutableStateOf(settings.googleMapId) }
    var isTestMode by remember { mutableStateOf(settings.isTestModeActive) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Einstellungen & Profil") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sektion: Benutzer
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Dein Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = homeAddress,
                    onValueChange = { homeAddress = it },
                    label = { Text("Mixers Zuhause (Startpunkt)") },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider()

                // Sektion: Google Maps API
                Text("Developer / Maps Config", style = MaterialTheme.typography.labelLarge)

                OutlinedTextField(
                    value = googleApiKey,
                    onValueChange = { googleApiKey = it },
                    label = { Text("Google Maps API Key") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = googleMapId,
                    onValueChange = { googleMapId = it },
                    label = { Text("Google Maps Style ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider()

                // Sektion: Test-System
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Test-Modus aktiv", style = MaterialTheme.typography.bodyLarge)
                        Text("Sofort-Effekte & Highscore", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = isTestMode,
                        onCheckedChange = {
                            isTestMode = it
                            viewModel.toggleTestMode(it)
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                // Daten final im SettingsManager speichern
                settings.userName = userName
                settings.homeAddress = homeAddress
                settings.googleApiKey = googleApiKey
                settings.googleMapId = googleMapId
                // Der Testmodus wird bereits über das ViewModel/Switch oben gesteuert
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
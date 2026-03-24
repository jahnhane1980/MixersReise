package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: MixerViewModel
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Einstellungen", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                // Name eingeben
                OutlinedTextField(
                    value = viewModel.userName.value,
                    onValueChange = { viewModel.updateUserName(it) },
                    label = { Text("Dein Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Adresseingabe
                Text(text = "Adresse für GPS-Simulation", style = MaterialTheme.typography.labelLarge)

                OutlinedTextField(
                    value = viewModel.userStreet.value,
                    onValueChange = { viewModel.updateAddress(it, viewModel.userHouseNumber.value, viewModel.userZipCode.value, viewModel.userCity.value) },
                    label = { Text("Straße") }
                )

                OutlinedTextField(
                    value = viewModel.userHouseNumber.value,
                    onValueChange = { viewModel.updateAddress(viewModel.userStreet.value, it, viewModel.userZipCode.value, viewModel.userCity.value) },
                    label = { Text("Hausnummer") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text("Abbrechen")
                    }
                    Button(onClick = {
                        // R6: Nur Logik-Aufrufe hier, keine UI-Elemente!
                        viewModel.detectLocationViaGps()
                        onDismiss()
                    }) {
                        Text("Speichern & GPS")
                    }
                }
            }
        }
    }
}
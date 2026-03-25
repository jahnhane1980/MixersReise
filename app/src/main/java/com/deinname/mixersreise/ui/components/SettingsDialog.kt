package com.deinname.mixersreise.ui.components

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: MixerViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = "Einstellungen",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Benutzername
                OutlinedTextField(
                    value = viewModel.userName.value,
                    onValueChange = { viewModel.updateUserName(it) },
                    label = { Text("Dein Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Heimatadresse",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Straße & Hausnummer
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = viewModel.userStreet.value,
                        onValueChange = {
                            viewModel.updateAddress(it, viewModel.userHouseNumber.value, viewModel.userZipCode.value, viewModel.userCity.value)
                        },
                        label = { Text("Straße") },
                        modifier = Modifier.weight(0.7f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = viewModel.userHouseNumber.value,
                        onValueChange = {
                            viewModel.updateAddress(viewModel.userStreet.value, it, viewModel.userZipCode.value, viewModel.userCity.value)
                        },
                        label = { Text("Nr.") },
                        modifier = Modifier.weight(0.3f),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // PLZ & Stadt
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = viewModel.userZipCode.value,
                        onValueChange = {
                            viewModel.updateAddress(viewModel.userStreet.value, viewModel.userHouseNumber.value, it, viewModel.userCity.value)
                        },
                        label = { Text("PLZ") },
                        modifier = Modifier.weight(0.4f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = viewModel.userCity.value,
                        onValueChange = {
                            viewModel.updateAddress(viewModel.userStreet.value, viewModel.userHouseNumber.value, viewModel.userZipCode.value, it)
                        },
                        label = { Text("Stadt") },
                        modifier = Modifier.weight(0.6f),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // GPS Button - Layout vereinheitlicht (Standard-Farben)
                Button(
                    onClick = {
                        viewModel.detectLocationViaGps()
                        Toast.makeText(context, "GPS wird abgefragt...", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Aktuellen Standort abrufen")
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Abbrechen")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            viewModel.saveAllSettingsWithGeocoding { success, message ->
                                (context as? Activity)?.runOnUiThread {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    if (success) {
                                        onDismiss()
                                    }
                                }
                            }
                        },
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                    ) {
                        Text("Speichern & Schließen")
                    }
                }
            }
        }
    }
}
package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun SettingsDialog(
    viewModel: MixerViewModel,
    onDismiss: () -> Unit
) {
    var message by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Einstellungen",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = viewModel.userName.value,
                    onValueChange = { viewModel.updateUserName(it) },
                    label = { Text("Dein Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Heimatadresse",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = { viewModel.detectLocationViaGps() }) {
                        Text("GPS")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.userStreet.value,
                    onValueChange = {
                        viewModel.updateAddress(it, viewModel.userHouseNumber.value, viewModel.userZipCode.value, viewModel.userCity.value)
                    },
                    label = { Text("Straße") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = viewModel.userHouseNumber.value,
                        onValueChange = {
                            viewModel.updateAddress(viewModel.userStreet.value, it, viewModel.userZipCode.value, viewModel.userCity.value)
                        },
                        label = { Text("Nr.") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = viewModel.userZipCode.value,
                        onValueChange = {
                            viewModel.updateAddress(viewModel.userStreet.value, viewModel.userHouseNumber.value, it, viewModel.userCity.value)
                        },
                        label = { Text("PLZ") },
                        modifier = Modifier.weight(2f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.userCity.value,
                    onValueChange = {
                        viewModel.updateAddress(viewModel.userStreet.value, viewModel.userHouseNumber.value, viewModel.userZipCode.value, it)
                    },
                    label = { Text("Stadt") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (message.isNotEmpty()) {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Abbrechen")
                    }
                    Button(
                        onClick = {
                            viewModel.saveAllSettingsWithGeocoding { success, info ->
                                message = info
                                if (success) {
                                    onDismiss()
                                }
                            }
                        }
                    ) {
                        Text("Speichern")
                    }
                }
            }
        }
    }
}
package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = "Einstellungen", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.userName,
                    onValueChange = { viewModel.updateUserName(it) },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.userStreet,
                    onValueChange = { viewModel.updateAddress(it, viewModel.userHouseNumber, viewModel.userZipCode, viewModel.userCity) },
                    label = { Text("Straße") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.userHouseNumber,
                    onValueChange = { viewModel.updateAddress(viewModel.userStreet, it, viewModel.userZipCode, viewModel.userCity) },
                    label = { Text("Hausnummer") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.userZipCode,
                    onValueChange = { viewModel.updateAddress(viewModel.userStreet, viewModel.userHouseNumber, it, viewModel.userCity) },
                    label = { Text("PLZ") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.userCity,
                    onValueChange = { viewModel.updateAddress(viewModel.userStreet, viewModel.userHouseNumber, viewModel.userZipCode, it) },
                    label = { Text("Stadt") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.detectLocationViaGps() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Standort via GPS")
                }

                TextButton(onClick = onDismiss, modifier = Modifier.align(androidx.compose.ui.Alignment.End)) {
                    Text("Schließen")
                }
            }
        }
    }
}
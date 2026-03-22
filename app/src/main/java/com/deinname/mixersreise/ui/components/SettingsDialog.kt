package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: MixerViewModel
) {
    val customBlue = Color(0xFF1976D2)
    val pureWhite = Color.White

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Einstellungen",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Level-Anzeige (Reiner Text, keine Button-Optik)
                Text(
                    text = "Aktuelles Level: ${viewModel.level}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = customBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Namens-Eingabe
                OutlinedTextField(
                    value = viewModel.userName.value,
                    onValueChange = { viewModel.updateUserName(it) },
                    label = { Text("Dein Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 4.dp))

                Text(
                    text = "Mixers Zuhause (Standort)",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                // GPS Button (Blau mit weißer Schrift)
                Button(
                    onClick = { viewModel.detectLocationViaGps() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = customBlue,
                        contentColor = pureWhite
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Standort über GPS ermitteln", fontWeight = FontWeight.Bold)
                }

                // Adresszeile 1: Straße
                OutlinedTextField(
                    value = viewModel.userStreet.value,
                    onValueChange = {
                        viewModel.updateAddress(it, viewModel.userHouseNumber.value, viewModel.userZipCode.value, viewModel.userCity.value)
                    },
                    label = { Text("Straße") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Adresszeile 2: Hausnummer & PLZ
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = viewModel.userHouseNumber.value,
                        onValueChange = {
                            viewModel.updateAddress(viewModel.userStreet.value, it, viewModel.userZipCode.value, viewModel.userCity.value)
                        },
                        label = { Text("Nr.") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = viewModel.userZipCode.value,
                        onValueChange = {
                            viewModel.updateAddress(viewModel.userStreet.value, viewModel.userHouseNumber.value, it, viewModel.userCity.value)
                        },
                        label = { Text("PLZ") },
                        modifier = Modifier.weight(1.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Adresszeile 3: Stadt
                OutlinedTextField(
                    value = viewModel.userCity.value,
                    onValueChange = {
                        viewModel.updateAddress(viewModel.userStreet.value, viewModel.userHouseNumber.value, viewModel.userZipCode.value, it)
                    },
                    label = { Text("Stadt") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            // Speichern Button (Blau mit weißer Schrift)
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = customBlue,
                    contentColor = pureWhite
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Speichern", fontWeight = FontWeight.Bold)
            }
        }
    )
}
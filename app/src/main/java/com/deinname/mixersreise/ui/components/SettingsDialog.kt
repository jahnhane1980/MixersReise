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
import com.deinname.mixersreise.ui.theme.*
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: MixerViewModel
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        // Hintergrund des Dialogs in warmem Beige
        containerColor = LemonChiffon,
        title = {
            Text(
                text = "Einstellungen",
                style = MaterialTheme.typography.headlineSmall,
                color = DarkWood,
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
                // Info-Text
                Text(
                    text = "Hier kannst du deine Daten und Mixers Zuhause verwalten.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkWood.copy(alpha = 0.8f)
                )

                // Namens-Eingabe
                OutlinedTextField(
                    value = viewModel.userName.value,
                    onValueChange = { viewModel.updateUserName(it) },
                    label = { Text("Dein Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WarmWood,
                        unfocusedBorderColor = DarkWood.copy(alpha = 0.5f),
                        focusedLabelColor = WarmWood,
                        cursorColor = WarmWood
                    )
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = DarkWood.copy(alpha = 0.1f))

                Text(
                    text = "Standort (Zuhause)",
                    style = MaterialTheme.typography.labelLarge,
                    color = DarkWood,
                    fontWeight = FontWeight.Bold
                )

                // GPS Button (CosyBlue passend zum Halstuch)
                Button(
                    onClick = { viewModel.detectLocationViaGps() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CosyBlue,
                        contentColor = DarkWood // Dunkler Text auf hellem Blau für bessere Lesbarkeit
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("📍 Aktuellen Standort speichern", fontWeight = FontWeight.Bold)
                }

                // Adresszeile 1: Straße
                OutlinedTextField(
                    value = viewModel.userStreet.value,
                    onValueChange = {
                        viewModel.updateAddress(it, viewModel.userHouseNumber.value, viewModel.userZipCode.value, viewModel.userCity.value)
                    },
                    label = { Text("Straße") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WarmWood,
                        unfocusedBorderColor = DarkWood.copy(alpha = 0.5f)
                    )
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
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WarmWood,
                            unfocusedBorderColor = DarkWood.copy(alpha = 0.5f)
                        )
                    )
                    OutlinedTextField(
                        value = viewModel.userZipCode.value,
                        onValueChange = {
                            viewModel.updateAddress(viewModel.userStreet.value, viewModel.userHouseNumber.value, it, viewModel.userCity.value)
                        },
                        label = { Text("PLZ") },
                        modifier = Modifier.weight(1.5f),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WarmWood,
                            unfocusedBorderColor = DarkWood.copy(alpha = 0.5f)
                        )
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
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = WarmWood,
                        unfocusedBorderColor = DarkWood.copy(alpha = 0.5f)
                    )
                )
            }
        },
        confirmButton = {
            // Speichern Button (WarmWood mit LemonChiffon Schrift)
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarmWood,
                    contentColor = LemonChiffon
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Fertig", fontWeight = FontWeight.Bold)
            }
        }
    )
}
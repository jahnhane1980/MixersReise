package com.deinname.mixersreise.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// Standard Compose & Material 3
import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // Für Button, Text, AlertDialog etc.
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

// Spezifische Material 3 Dropdown-Komponenten
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import com.deinname.mixersreise.data.TalkOption
import com.deinname.mixersreise.ui.theme.LemonChiffon
import com.deinname.mixersreise.ui.theme.DarkWood

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TalkDialog(
    options: List<TalkOption>,
    onOptionSelected: (TalkOption) -> Unit,
    onDismiss: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options.firstOrNull()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        // Hintergrundfarbe des Dialogs
        containerColor = LemonChiffon,
        title = {
            Text(
                text = "Was möchtest du wissen?",
                color = DarkWood // Schriftfarbe
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Wähle eine Frage aus der Liste:",
                    color = DarkWood // Schriftfarbe
                )
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedOption?.question ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Frage", color = DarkWood) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        // Farben für das Textfeld
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            focusedTextColor = DarkWood,
                            unfocusedTextColor = DarkWood,
                            focusedBorderColor = DarkWood,
                            unfocusedBorderColor = DarkWood.copy(alpha = 0.7f),
                            focusedLabelColor = DarkWood,
                            unfocusedLabelColor = DarkWood
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        // Hintergrund des Dropdowns
                        containerColor = LemonChiffon
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option.question,
                                        color = DarkWood // Schriftfarbe der Items
                                    )
                                },
                                onClick = {
                                    selectedOption = option
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedOption?.let { onOptionSelected(it) }
                },
                // Button-Farben passend zum Thema
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkWood,
                    contentColor = LemonChiffon
                )
            ) {
                Text("Fragen")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Abbrechen",
                    color = DarkWood // Schriftfarbe
                )
            }
        }
    )
}
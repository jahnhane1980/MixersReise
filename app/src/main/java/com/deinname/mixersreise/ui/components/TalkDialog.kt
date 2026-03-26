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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TalkDialog(
    options: List<TalkOption>,
    onOptionSelected: (TalkOption) -> Unit,
    onDismiss: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    // Initialisierung mit der ersten Option, falls die Liste nicht leer ist
    var selectedOption by remember { mutableStateOf(options.firstOrNull()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Was möchtest du wissen?") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Wähle eine Frage aus der Liste:")
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        // Hier lag der Fehler: Zugriff erfolgt über .question
                        value = selectedOption?.question ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Frage") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                // Hier lag der zweite Fehler: Zugriff über .question
                                text = { Text(text = option.question) },
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
                }
            ) {
                Text("Fragen")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen")
            }
        }
    )
}
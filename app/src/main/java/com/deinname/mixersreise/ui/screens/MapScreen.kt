package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MapScreen(
    viewModel: MixerViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Weltkarte") },
                navigationIcon = {
                    Button(onClick = onBack) { Text("Zurück") }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Deine Reiseziele",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // R6: Sicherer Zugriff auf die destinations Liste
            if (viewModel.destinations.isEmpty()) {
                Text(text = "Noch keine Ziele entdeckt.")
            } else {
                LazyColumn {
                    // Wir nutzen .items(), um direkt auf die Strings zuzugreifen
                    items(viewModel.destinations) { destination ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = destination, // FIX: Hier wird nun garantiert ein String übergeben
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
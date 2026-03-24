package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.SafeImage
import com.deinname.mixersreise.viewmodel.MixerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MixerViewModel,
    onBack: () -> Unit
) {
    // Box ist der Container, der das Layering erlaubt (Bild ganz unten, UI drüber)
    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Hintergrund-Layer
        SafeImage(
            resId = R.drawable.bg_world_map, // Hier händisch deinen Namen eintragen
            contentDescription = "Weltkarte Hintergrund",
            modifier = Modifier.fillMaxSize()
        )

        // 2. UI-Layer (Scaffold muss transparent sein!)
        Scaffold(
            containerColor = Color.Transparent, // KRITISCH: Ohne dies ist alles weiß/grau
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Weltkarte",
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.weight(1).width(16.dp))
                            // HERZ-ANZEIGE: Explizit mit Kontrastfarbe
                            Surface(
                                color = Color.Black.copy(alpha = 0.4f),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = " ❤️ ${viewModel.totalHearts.value} ",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Zurück",
                                tint = Color.White
                            )
                        }
                    },
                    // Dunkle TopBar für bessere Lesbarkeit über der Karte
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black.copy(alpha = 0.6f),
                        titleContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Titel über der Liste
                Surface(
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = " Entdeckte Ziele ",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (viewModel.destinations.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Noch keine Reiseziele vorhanden.", color = Color.White)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.destinations) { destination ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White.copy(alpha = 0.85f) // Leicht transparent
                                )
                            ) {
                                Text(
                                    text = destination,
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
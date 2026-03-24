package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.background
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
import com.deinname.mixersreise.ui.theme.LemonChiffon
import com.deinname.mixersreise.viewmodel.MixerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MixerViewModel,
    onBack: () -> Unit
) {
    // Die Box garantiert den LemonChiffon Hintergrund, falls das Bild Transparenzen hat
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonChiffon)
    ) {
        // LAYER 1: Das Hintergrundbild (R1.1 Quittung: bg_world_map fixiert)
        SafeImage(
            resId = R.drawable.bg_world_map,
            contentDescription = "Weltkarte",
            modifier = Modifier.fillMaxSize()
        )

        // LAYER 2: UI (Manuelle Anordnung statt Scaffold für 100% Transparenz-Kontrolle)
        Column(modifier = Modifier.fillMaxSize()) {

            // Custom TopBar Bereich
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White.copy(alpha = 0.5f))
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Zurück", tint = Color.Black)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Weltkarte",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))

                // HERZ-COUNTER (Gegen den weißen Hintergrund abgesichert)
                Surface(
                    color = Color.White.copy(alpha = 0.8f),
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 4.dp
                ) {
                    Text(
                        text = " ❤️ ${viewModel.totalHearts.value} ",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }
            }

            // Inhalts-Bereich (Liste der Ziele)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Surface(
                        color = Color.Black.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = " Entdeckte Ziele ",
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.DarkGray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(viewModel.destinations) { destination ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.9f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Text(
                            text = destination,
                            modifier = Modifier.padding(20.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
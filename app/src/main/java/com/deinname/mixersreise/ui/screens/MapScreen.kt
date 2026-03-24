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
import androidx.compose.ui.layout.ContentScale
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonChiffon)
    ) {
        // LAYER 1: Weltkarte (Mit ContentScale.Crop für volle Fläche)
        // R1.1 Quittung: bg_world_map physisch vorhanden
        SafeImage(
            resId = R.drawable.bg_world_map,
            contentDescription = "Weltkarte",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // FIX: Zieht das Bild über den ganzen Bereich
        )

        // LAYER 2: UI
        Column(modifier = Modifier.fillMaxSize()) {

            // TopBar
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
            }

            // Tabelle (50% Weiß, ohne Header)
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.5f)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // R6: Header-Zeile wurde wie gewünscht entfernt

                    items(viewModel.destinations) { destination ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Spalte 1: Herzchen Symbol
                            Text(
                                text = "❤️",
                                modifier = Modifier.width(40.dp)
                            )

                            // Spalte 2: Anzahl (Dummy 0)
                            Text(
                                text = "0",
                                modifier = Modifier.width(60.dp),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )

                            // Spalte 3: Stadtname
                            Text(
                                text = destination,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black
                            )
                        }
                        // Dezente Trennlinie zwischen den Städten
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.Black.copy(alpha = 0.1f)
                        )
                    }
                }
            }
        }
    }
}
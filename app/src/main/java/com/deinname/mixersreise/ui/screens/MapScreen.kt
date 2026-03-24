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
    // R1.1 Quittung: Box als Basis, um LemonChiffon zu garantieren
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonChiffon)
    ) {
        // LAYER 1: Bild
        SafeImage(
            resId = R.drawable.DEIN_DATEINAME, // Dein händischer Eintrag
            contentDescription = "Weltkarte",
            modifier = Modifier.fillMaxSize()
        )

        // LAYER 2: UI (Kein Scaffold, um jegliche Standard-Hintergründe zu vermeiden)
        Column(modifier = Modifier.fillMaxSize()) {

            // Eigene TopBar-Konstruktion ohne Scaffold-Zwang
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Zurück", tint = Color.DarkGray)
                }

                Text(
                    text = "Weltkarte",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.weight(1f))

                // DIE HERZCHEN (Explizit gerahmt für Sichtbarkeit)
                Surface(
                    color = Color.White.copy(alpha = 0.6f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = " ❤️ ${viewModel.totalHearts.value} ",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }
            }

            // Die Liste der Ziele
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text(
                        text = "Entdeckte Ziele",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(viewModel.destinations) { destination ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                    ) {
                        Text(
                            text = destination,
                            modifier = Modifier.padding(16.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
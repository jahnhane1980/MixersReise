package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.SafeImage

@Composable
fun MapScreen(
    points: List<String>,
    hearts: Int, // NEU: Herzchen-Anzahl als Parameter
    apiKey: String,
    onClose: () -> Unit
) {
    // Definition der Farben basierend auf unseren Vereinbarungen
    val transparentWhite = Color.White.copy(alpha = 0.85f)
    val customBlue = Color(0xFF1976D2) // Dein neu definiertes Blau
    val darkText = Color(0xFF212121)

    Box(modifier = Modifier.fillMaxSize()) {
        // Weltkarten Hintergrundbild
        SafeImage(
            resId = R.drawable.bg_world_map,
            contentDescription = "Weltkarte Hintergrund",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Haupt-Container für die Tabelle
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header-Bereich mit Herzchen-Stand
            Card(
                colors = CardDefaults.cardColors(containerColor = transparentWhite),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Deine Reiseziele",
                            style = MaterialTheme.typography.headlineSmall,
                            color = customBlue,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Entdeckte Orte: ${points.size}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = darkText
                        )
                    }

                    // Herzchen-Anzeige im Dialog
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Herzen",
                            tint = Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = hearts.toString(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = darkText,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Die Tabelle mit den Zielen
            Card(
                colors = CardDefaults.cardColors(containerColor = transparentWhite),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(points) { point ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .border(1.dp, customBlue.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📍",
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = point,
                                style = MaterialTheme.typography.bodyLarge,
                                color = darkText,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Der Button in deinem definierten Blau
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = customBlue),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Zurück zum Mixer",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
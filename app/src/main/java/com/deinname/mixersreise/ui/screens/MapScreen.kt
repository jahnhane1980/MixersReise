package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.SafeImage
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MapScreen(
    viewModel: MixerViewModel,
    onBack: () -> Unit
) {
    val destinationList by viewModel.allDestinations.collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        // Hintergrundbild der Weltkarte
        SafeImage(
            resId = R.drawable.bg_world_map,
            contentDescription = "Weltkarte Hintergrund",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay für die Statistik-Tabelle
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xAAFFF9C4)) // Halbtransparente LemonChiffon für Lesbarkeit
                .padding(16.dp)
        ) {
            Text(
                text = "Reise-Statistik",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // R3: Minimalist Selection - Header Row und dicker Divider entfernt

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(destinationList) { destination ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 1. Spalte: Icon (0.5f)
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.weight(0.5f).size(20.dp)
                        )

                        // 2. Spalte: Herzen (1f) - R1: Physical Truth: heartsCollected
                        Text(
                            text = "${destination.heartsCollected}",
                            modifier = Modifier.weight(1f),
                            fontSize = 16.sp,
                            color = Color.Black
                        )

                        // 3. Spalte: Stadt (2f) - R1: Physical Truth: cityName
                        Text(
                            text = destination.cityName,
                            modifier = Modifier.weight(2f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                    HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
                }
            }
        }
    }
}
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MapScreen(
    viewModel: MixerViewModel,
    onBack: () -> Unit
) {
    // R1: Physische Wahrheit - Abruf der realen Daten aus dem Flow
    val destinationList by viewModel.allDestinations.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4)) // LemonChiffon Äquivalent
            .padding(16.dp)
    ) {
        Text(
            text = "Reise-Statistik",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Tabellen-Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Icon", Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
            Text("Herzen", Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Stadt", Modifier.weight(2f), fontWeight = FontWeight.Bold)
        }

        HorizontalDivider(thickness = 2.dp, color = Color.Gray)

        // R3: Minimalist Selection - Nur die Tabelle mit realen Werten
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
                    // 1. Spalte: Ein Herz
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.weight(0.5f).size(20.dp)
                    )

                    // 2. Spalte: Anzahl der Herzen
                    Text(
                        text = "${destination.heartsCollected}",
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp
                    )

                    // 3. Spalte: Name der Stadt
                    Text(
                        text = destination.cityName,
                        modifier = Modifier.weight(2f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
            }
        }
    }
}
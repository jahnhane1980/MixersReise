package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.theme.DarkWood
import com.deinname.mixersreise.ui.theme.LemonChiffon

@Composable
fun MixerTopBar(
    hearts: Int,
    onOpenMap: () -> Unit,
    onOpenSettings: () -> Unit
) {
    // Hintergrund in DarkWood mit Transparenz
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = DarkWood.copy(alpha = 0.8f),
        tonalElevation = 0.dp // Elevation entfernt, um Transparenz-Effekt nicht zu stören
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Herz-Anzeige
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.tool_hand), // Platzhalter für Herz-Icon, falls kein separates existiert
                    contentDescription = "Hearts",
                    modifier = Modifier.size(24.dp),
                    tint = LemonChiffon // Farbe auf LemonChiffon gesetzt
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = hearts.toString(),
                    color = LemonChiffon, // Schriftfarbe auf LemonChiffon gesetzt
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Buttons für Map und Settings
            Row {
                IconButton(onClick = onOpenMap) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = "Map",
                        tint = LemonChiffon // Farbe auf LemonChiffon gesetzt
                    )
                }
                IconButton(onClick = onOpenSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = LemonChiffon // Farbe auf LemonChiffon gesetzt
                    )
                }
            }
        }
    }
}
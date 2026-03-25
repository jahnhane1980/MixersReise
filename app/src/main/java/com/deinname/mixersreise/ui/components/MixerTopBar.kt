package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deinname.mixersreise.ui.theme.DarkWood
import com.deinname.mixersreise.ui.theme.LemonChiffon

@Composable
fun MixerTopBar(
    hearts: Int,
    onOpenMap: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = DarkWood.copy(alpha = 0.8f),
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Herz-Anzeige (Wiederhergestellt)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Favorite, // Zurück zum Herz-Icon
                    contentDescription = "Hearts",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Red // Explizit Rot wie gewünscht
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = hearts.toString(),
                    color = LemonChiffon,
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
                        tint = LemonChiffon
                    )
                }
                IconButton(onClick = onOpenSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = LemonChiffon
                    )
                }
            }
        }
    }
}
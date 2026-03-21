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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MixerTopBar(
    level: Int,
    hearts: Int,
    onOpenMap: () -> Unit,
    onOpenSettings: () -> Unit
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Lv. $level", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.width(16.dp))
                Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Red)
                Spacer(Modifier.width(4.dp))
                Text("$hearts", style = MaterialTheme.typography.titleMedium)
            }
        },
        actions = {
            IconButton(onClick = onOpenMap) {
                Icon(Icons.Default.Map, contentDescription = "Karte")
            }
            IconButton(onClick = onOpenSettings) {
                Icon(Icons.Default.Settings, contentDescription = "Einstellungen")
            }
        }
    )
}
package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.ui.theme.TextBrown

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
            Column {
                Text(
                    text = "Mixers Reise",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextBrown // Passendes Pferde-Braun
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // LVL-Badge im Stil des Rahmens
                    Surface(
                        color = Color(0xFFF5DEB3), // Weizen-Gelb (wie der Rahmen)
                        shape = RoundedCornerShape(8.dp),
                        border = MaterialTheme.colorScheme.outline.run { null } // Kein harter Rand
                    ) {
                        Text(
                            text = "LVL $level",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = TextBrown // Brauner Text
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "❤️ $hearts",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFFDC143C) // Karmesinrot für das Herz
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onOpenMap) {
                Icon(imageVector = Icons.Default.Place, contentDescription = "Weltkarte", tint = TextBrown)
            }
            IconButton(onClick = onOpenSettings) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Einstellungen", tint = TextBrown)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White.copy(alpha = 0.6f) // Leicht transparent
        )
    )
}
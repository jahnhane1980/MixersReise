package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deinname.mixersreise.ui.theme.WarmWood

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MixerTopBar(
    hearts: Int, // R5: Parameter 'level' entfernt
    onOpenMap: () -> Unit,
    onOpenSettings: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(WarmWood.copy(alpha = 0.8f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                // Nur noch die Herzen anzeigen
                Text("❤️ $hearts", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        },
        actions = {
            IconButton(onClick = { onOpenMap() }) { Text("📍", fontSize = 24.sp) }
            IconButton(onClick = { onOpenSettings() }) { Text("⚙️", fontSize = 24.sp) }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}
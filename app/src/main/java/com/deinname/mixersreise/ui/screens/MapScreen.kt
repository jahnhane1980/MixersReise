package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.ui.components.SafeImage
import com.deinname.mixersreise.ui.theme.*
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MapScreen(viewModel: MixerViewModel, onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // FIX: contentDescription darf nicht null sein
        SafeImage(
            resId = R.drawable.bg_world_map,
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Deine Reiseziele",
                style = MaterialTheme.typography.headlineMedium,
                color = DarkWood,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = LemonChiffon.copy(alpha = 0.85f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                val currentDestinations = viewModel.destinations
                if (currentDestinations.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Noch keine Ziele entdeckt", color = DarkWood)
                    }
                } else {
                    LazyColumn(modifier = Modifier.padding(8.dp)) {
                        items(currentDestinations) { city ->
                            ListItem(
                                headlineContent = { Text(text = city, color = DarkWood) },
                                leadingContent = { Text("📍") },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WarmWood)
            ) {
                Text("Zurück", fontWeight = FontWeight.Bold, color = LemonChiffon)
            }
        }
    }
}
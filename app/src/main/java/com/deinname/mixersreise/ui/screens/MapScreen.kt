package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.background
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
import com.deinname.mixersreise.ui.theme.*
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MapScreen(viewModel: MixerViewModel, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(LemonChiffon).padding(16.dp)
    ) {
        Text("Deine Reiseziele", style = MaterialTheme.typography.headlineMedium, color = DarkWood, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f)),
            shape = RoundedCornerShape(24.dp)
        ) {
            val currentDestinations = viewModel.destinations
            if (currentDestinations.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Noch keine Ziele entdeckt", color = DarkWood.copy(alpha = 0.5f))
                }
            } else {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(currentDestinations) { city: String ->
                        ListItem(
                            headlineContent = { Text(text = city, color = DarkWood) },
                            leadingContent = { Text(text = "📍") }
                        )
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CosyBlue, contentColor = DarkWood),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Zurück zum Mixer", fontWeight = FontWeight.Bold)
        }
    }
}
package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.deinname.mixersreise.data.TravelPoint

@Composable
fun MapScreen(
    points: List<TravelPoint>,
    apiKey: String,
    onClose: () -> Unit // Parameter Name FIX
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = onClose) { Text("Zurück") }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (apiKey.isEmpty()) {
                Text("Weltkarte Coming Soon (API Key fehlt)")
            } else {
                Text("Hier wird die Karte geladen (${points.size} Ziele)")
            }
        }
    }
}
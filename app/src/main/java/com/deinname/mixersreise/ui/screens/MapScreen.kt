package com.deinname.mixersreise.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.deinname.mixersreise.data.TravelDestination
import com.deinname.mixersreise.ui.components.SafeImage
import com.deinname.mixersreise.ui.theme.LemonChiffon
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MapScreen(viewModel: MixerViewModel) {
    // Fix: Explizite Typangabe für TravelDestination zur Behebung des Inferenz-Fehlers
    val destinations by viewModel.allDestinations.collectAsState(initial = emptyList<TravelDestination>())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LemonChiffon)
    ) {
        SafeImage(
            resId = R.drawable.bg_world_map,
            contentDescription = "Weltkarte Hintergrund",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.5f))
                    .padding(8.dp)
            ) {
                LazyColumn {
                    items(destinations) { destination ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "❤️", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${destination.heartsCollected}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = destination.cityName,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
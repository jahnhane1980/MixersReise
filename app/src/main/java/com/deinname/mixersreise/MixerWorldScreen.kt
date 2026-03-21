package com.deinname.mixersreise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.deinname.mixersreise.viewmodel.MixerViewModel

@Composable
fun MixerWorldScreen(viewModel: MixerViewModel) {
    // KOMPLETT OHNE BILDER UND OHNE VIEWMODEL-WERTE
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text("WENN DU DAS SIEHST, LIEGT DER CRASH AN DEN BILDERN ODER VIEWMODEL-LOGIK", color = Color.White)
    }
}
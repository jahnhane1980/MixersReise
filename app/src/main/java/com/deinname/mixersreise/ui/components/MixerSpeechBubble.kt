package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MixerSpeechBubble(text: String) { // Hier muss exakt 'text' stehen
    Text(
        text = text,
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        color = Color.Black,
        fontSize = 16.sp
    )
}
package com.deinname.mixersreise.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun HeartParticles() {
    val particles = remember { List(6) { it } }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        particles.forEach { index ->
            // R6: Animation für X und Y Achse
            val animatedX = remember { Animatable(0f) }
            val animatedY = remember { Animatable(0f) }
            val animatedAlpha = remember { Animatable(1f) }
            val animatedScale = remember { Animatable(1f) }

            // Zufällige Start-Verzögerung für natürliches Aussehen
            val randomDelay = remember { Random.nextInt(0, 500) }

            // Berechnete Ziel-Koordinaten (Richtung TopBar oben links)
            // Diese Werte sind Schätzungen basierend auf Standard-Screen-Größen
            // und müssen lokal zum Mixer-Container (300dp) berechnet werden.
            val targetX = -150f // Zieht nach links
            val targetY = -600f // Fliegt weit nach oben

            LaunchedEffect(Unit) {
                // 1. X-Achse (Nach links ziehen)
                launch {
                    animatedX.animateTo(
                        targetValue = targetX,
                        animationSpec = tween(
                            durationMillis = 1500,
                            delayMillis = randomDelay,
                            easing = FastOutSlowInEasing
                        )
                    )
                }

                // 2. Y-Achse (Nach oben fliegen)
                launch {
                    animatedY.animateTo(
                        targetValue = targetY,
                        animationSpec = tween(
                            durationMillis = 1500,
                            delayMillis = randomDelay,
                            easing = LinearOutSlowInEasing // Zuerst schnell, dann langsamer
                        )
                    )
                }

                // 3. Alpha (Verblassen kurz vor dem Ziel)
                launch {
                    animatedAlpha.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(
                            durationMillis = 1500,
                            delayMillis = randomDelay + 200 // Startet leicht verzögert
                        )
                    )
                }

                // 4. Scale (Kleiner werden)
                launch {
                    animatedScale.animateTo(
                        targetValue = 0.5f,
                        animationSpec = tween(
                            durationMillis = 1500,
                            delayMillis = randomDelay
                        )
                    )
                }
            }

            Text(
                text = "❤️",
                fontSize = 28.sp,
                modifier = Modifier
                    .offset(x = animatedX.value.dp, y = animatedY.value.dp) // R6: Dynamischer Offset
                    .alpha(animatedAlpha.value)
                    .scale(animatedScale.value)
            )
        }
    }
}
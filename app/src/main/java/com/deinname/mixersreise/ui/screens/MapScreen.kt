package com.deinname.mixersreise.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun HeartParticles() {
    // Erzeugt 6 Partikel-Indizes
    val particles = remember { List(6) { it } }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        particles.forEach { index ->
            val animatedY = remember { Animatable(0f) }
            val animatedAlpha = remember { Animatable(1f) }

            // Zufällige Werte für die Streuung
            val randomX = remember { Random.nextInt(-120, 120).dp }
            val randomDelay = remember { Random.nextInt(0, 400) }
            val duration = remember { Random.nextInt(1200, 1800) }

            LaunchedEffect(Unit) {
                // Animation des Aufsteigens
                launch {
                    animatedY.animateTo(
                        targetValue = -500f,
                        animationSpec = tween(
                            durationMillis = duration,
                            delayMillis = randomDelay,
                            easing = FastOutExoEasing // R6: Korrekter Easing-Name
                        )
                    )
                }
                // Animation des Verblassens
                launch {
                    animatedAlpha.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(
                            durationMillis = duration,
                            delayMillis = randomDelay
                        )
                    )
                }
            }

            Text(
                text = "❤️",
                fontSize = 28.sp,
                modifier = Modifier
                    .offset(x = randomX, y = animatedY.value.dp)
                    .alpha(animatedAlpha.value)
            )
        }
    }
}

// Easing Definition
private val FastOutExoEasing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1f)
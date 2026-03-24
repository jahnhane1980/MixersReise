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
    val particles = remember { List(6) { it } }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        particles.forEach { _ ->
            val animatedY = remember { Animatable(0f) }
            val animatedAlpha = remember { Animatable(1f) }
            val randomX = remember { Random.nextInt(-120, 120).dp }
            val randomDelay = remember { Random.nextInt(0, 400) }

            LaunchedEffect(Unit) {
                launch {
                    animatedY.animateTo(
                        targetValue = -500f,
                        animationSpec = tween(1500, delayMillis = randomDelay, easing = FastOutSlowInEasing)
                    )
                }
                launch {
                    animatedAlpha.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(1500, delayMillis = randomDelay)
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
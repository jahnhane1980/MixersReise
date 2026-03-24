package com.deinname.mixersreise.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deinname.mixersreise.R
import com.deinname.mixersreise.viewmodel.ToolType

@Composable
fun MixerDisplay(
    isSleeping: Boolean,
    droolAlpha: Float,
    speechText: String,
    showHearts: Boolean,
    isInteractionLocked: Boolean,
    activeTool: ToolType,
    onMixerClick: () -> Unit
) {
    // R1.1 Quittung: Box als Container für den Mixer im Inhaltsbereich
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Start-Position: Center
    ) {

        // R6: Der Mixer (und seine Animationen) wird tiefer positioniert
        // FIX: Wir verschieben den gesamten Block massiv nach unten (y = 120.dp)
        Box(
            modifier = Modifier
                .offset(y = 120.dp) // <-- HIER: Die visuelle Korrektur
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {

            // DIE MIXER-GRAFIK (Vordergrund)
            // R1.1 Quittung: drawable/mixer_idle physisch vorhanden
            Image(
                painter = painterResource(id = R.drawable.mixer_idle),
                contentDescription = "Mixer",
                modifier = Modifier
                    .size(220.dp)
                    .clickable(
                        enabled = !isInteractionLocked, // Blockierung, wenn gesperrt
                        onClick = { onMixerClick() }
                    )
            )

            // LOGIK FÜR SPECIAL EFFECTS (SCHLAFEN, SPEICHEL, HERZEN, TEXT)

            // Schlafanimation
            if (isSleeping) {
                // R6: Sanftes Blinken der Schlafgrafik
                val infiniteTransition = rememberInfiniteTransition(label = "SleepTransition")
                val sleepAlpha by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "SleepAlpha"
                )

                Image(
                    painter = painterResource(id = R.drawable.sleep_icon),
                    contentDescription = "Zzz",
                    modifier = Modifier
                        .size(60.dp)
                        .offset(x = 60.dp, y = (-70).dp)
                        .alpha(sleepAlpha)
                )
            }

            // Sabber-Anzeige (Blinken, wenn droolAlpha > 0)
            if (droolAlpha > 0f) {
                Image(
                    painter = painterResource(id = R.drawable.drool_icon),
                    contentDescription = "Speichel",
                    modifier = Modifier
                        .size(40.dp)
                        .offset(x = (-10).dp, y = 50.dp)
                        .alpha(droolAlpha)
                )
            }

            // Herz-Animation (nur wenn showHearts=true)
            if (showHearts) {
                HeartParticles()
            }

            // Sprechblase (nur wenn Text vorhanden)
            AnimatedVisibility(
                visible = speechText.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .offset(x = 0.dp, y = (-160).dp)
            ) {
                SpeechBubble(text = speechText)
            }
        }
    }
}
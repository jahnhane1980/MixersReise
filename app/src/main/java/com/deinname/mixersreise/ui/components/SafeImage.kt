package com.deinname.mixersreise.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
fun SafeImage(
    resId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    fallbackEmoji: String = "❓"
) {
    val context = LocalContext.current

    // Wir prüfen sicherheitshalber, ob die ID gültig ist
    val exists = try {
        context.resources.getResourceName(resId)
        true
    } catch (e: Exception) {
        false
    }

    if (exists && resId != 0) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = contentDescription,
            modifier = modifier
        )
    } else {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text(text = fallbackEmoji, style = MaterialTheme.typography.displayLarge)
        }
    }
}
package com.deinname.mixersreise.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val CosyColorScheme = lightColorScheme(
    primary = WarmWood,
    onPrimary = LemonChiffon,
    secondary = CosyBlue,
    onSecondary = DarkWood,
    tertiary = SoftAmber,
    background = LemonChiffon,
    surface = LemonChiffon,
    onSurface = DarkWood
)

@Composable
fun MixersReiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = CosyColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        // R5: Die Statusleiste wird farblich an die App angepasst
        window.statusBarColor = colorScheme.primary.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
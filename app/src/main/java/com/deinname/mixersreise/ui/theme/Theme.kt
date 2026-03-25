package com.deinname.mixersreise.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Definition des Farbschemas basierend auf deinen "Cosy" Vorgaben
private val CosyColorScheme = lightColorScheme(
    primary = WarmWood,        // Das dunkle Braun für Header/Buttons
    onPrimary = LemonChiffon,  // Helle Schrift auf dunklem Grund
    secondary = CosyBlue,      // Akzentfarbe
    onSecondary = DarkWood,
    tertiary = SoftAmber,
    background = LemonChiffon, // Dies ist das "Weiß/Gelb", das wir im HomeScreen auf Transparent setzen
    surface = LemonChiffon,
    onSurface = DarkWood
)

@Composable
fun MixersReiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic Color deaktiviert, um dein spezielles Design zu erzwingen
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = CosyColorScheme
    val view = LocalView.current

    // R5: System-UI Anpassung (Statusleiste oben)
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        // Setzt die Farbe der Statusleiste auf unser primäres Braun
        window.statusBarColor = colorScheme.primary.toArgb()
        // Sorgt dafür, dass die Icons in der Statusleiste hell bleiben (da Hintergrund dunkel ist)
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
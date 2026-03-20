package com.deinname.mixersreise.viewmodel

// Diese Enums sind jetzt hier "zu Hause"
enum class ToolType { HAND, SPONGE, FOOD, COKE, TALK }

enum class MixerError(val message: String) {
    DATABASE("Datenbank-Fehler: Speichern nicht möglich."),
    GPS_DISABLED("GPS-Fehler: Weltreise-Bonus inaktiv."),
    STORAGE("Speicher-Fehler: Einstellungen fehlen."),
    IMAGE_MISSING("Grafik-Fehler: Bild konnte nicht geladen werden.")
}

data class MixerDialog(
    val question: String,
    val options: List<Pair<String, String>>
)
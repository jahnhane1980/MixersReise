package com.deinname.mixersreise.data

// Nur noch der Dialog-Typ für die Sprechblase
data class MixerDialog(
    val question: String,
    val options: List<Pair<String, String>>
)
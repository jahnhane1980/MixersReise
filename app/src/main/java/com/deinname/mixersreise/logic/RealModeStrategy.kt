// Datei: logic/RealModeStrategy.kt
package com.deinname.mixersreise.logic

class RealModeStrategy : MixerDataStrategy {
    override fun calculatePoints(base: Int, multiplier: Float, penalty: Int): Int {
        // Die echte Formel: (Basis * Ort) - Verspätungs-Abzug
        val result = (base * multiplier).toInt() - penalty
        return result.coerceAtLeast(-100) // Nie weniger als -100
    }

    override fun getInitialStatus(): Pair<Float, Float> {
        return Pair(100f, 100f) // Alles okay im Echtmodus
    }
}
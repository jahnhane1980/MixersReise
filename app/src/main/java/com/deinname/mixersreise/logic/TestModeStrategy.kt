// Datei: logic/TestModeStrategy.kt
package com.deinname.mixersreise.logic

class TestModeStrategy : MixerDataStrategy {
    override fun calculatePoints(base: Int, multiplier: Float, penalty: Int): Int {
        // Im Testmodus geben wir einfach immer das 10-fache der Basis,
        // egal wo wir sind (ignoriert multiplier), ziehen aber penalty ab.
        return (base * 10) - penalty
    }

    override fun getInitialStatus(): Pair<Float, Float> {
        return Pair(20f, 10f) // Mixer ist sofort hungrig/dreckig zum Testen
    }
}
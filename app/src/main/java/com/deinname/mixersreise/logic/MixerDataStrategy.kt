// Datei: logic/MixerDataStrategy.kt
package com.deinname.mixersreise.logic

interface MixerDataStrategy {
    // Hier müssen base, multiplier und penalty definiert sein!
    fun calculatePoints(base: Int, multiplier: Float, penalty: Int): Int
    fun getInitialStatus(): Pair<Float, Float>
}
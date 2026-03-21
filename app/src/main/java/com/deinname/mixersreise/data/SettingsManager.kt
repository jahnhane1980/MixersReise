package com.deinname.mixersreise.data

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("mixer_prefs", Context.MODE_PRIVATE)

    fun saveLevel(level: Int) {
        prefs.edit().putInt("current_level", level).apply()
    }

    fun getLevel(): Int {
        return prefs.getInt("current_level", 1)
    }

    fun saveHearts(hearts: Int) {
        prefs.edit().putInt("total_hearts", hearts).apply()
    }

    fun getHearts(): Int {
        return prefs.getInt("total_hearts", 0)
    }
}
package com.deinname.mixersreise.data

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("mixer_prefs", Context.MODE_PRIVATE)

    // Nutzerdaten
    var userName: String
        get() = prefs.getString("user_name", "Entdecker") ?: "Entdecker"
        set(value) = prefs.edit().putString("user_name", value).apply()

    // Heimat-Koordinaten für die "Ananas"-Reiselogik
    var homeLatitude: Double
        get() = prefs.getFloat("home_lat", 0.0f).toDouble()
        set(value) = prefs.edit().putFloat("home_lat", value.toFloat()).apply()

    var homeLongitude: Double
        get() = prefs.getFloat("home_lng", 0.0f).toDouble()
        set(value) = prefs.edit().putFloat("home_lng", value.toFloat()).apply()

    // Fortschritt
    var totalHearts: Int
        get() = prefs.getInt("total_hearts", 0)
        set(value) = prefs.edit().putInt("total_hearts", value).apply()

    // Technische Keys (Google Maps / API)
    var googleApiKey: String
        get() = prefs.getString("google_api_key", "") ?: ""
        set(value) = prefs.edit().putString("google_api_key", value).apply()

    var googleMapId: String
        get() = prefs.getString("google_map_id", "") ?: ""
        set(value) = prefs.edit().putString("google_map_id", value).apply()

    // Status-Variablen für den Worker und Tests
    var isTestModeActive: Boolean
        get() = prefs.getBoolean("test_mode", false)
        set(value) = prefs.edit().putBoolean("test_mode", value).apply()

    var notificationSentTime: Long
        get() = prefs.getLong("notification_sent_time", 0L)
        set(value) = prefs.edit().putLong("notification_sent_time", value).apply()

    var lastHungerUpdate: Long
        get() = prefs.getLong("last_hunger_update", System.currentTimeMillis())
        set(value) = prefs.edit().putLong("last_hunger_update", value).apply()
}
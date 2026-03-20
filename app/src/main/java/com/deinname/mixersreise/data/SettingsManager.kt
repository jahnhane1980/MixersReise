package com.deinname.mixersreise.data

class SettingsManager(context: android.content.Context) {
    private val prefs = context.getSharedPreferences("mixer_prefs", android.content.Context.MODE_PRIVATE)

    var userName: String
        get() = prefs.getString("user_name", "Spieler") ?: "Spieler"
        set(value) = prefs.edit().putString("user_name", value).apply()

    var homeAddress: String
        get() = prefs.getString("home_address", "") ?: ""
        set(value) = prefs.edit().putString("home_address", value).apply()

    var googleApiKey: String
        get() = prefs.getString("google_api_key", "") ?: ""
        set(value) = prefs.edit().putString("google_api_key", value).apply()

    var googleMapId: String
        get() = prefs.getString("google_map_id", "") ?: ""
        set(value) = prefs.edit().putString("google_map_id", value).apply()

    var isTestModeActive: Boolean
        get() = prefs.getBoolean("is_test_mode", false)
        set(value) = prefs.edit().putBoolean("is_test_mode", value).apply()

    var totalHearts: Int
        get() = prefs.getInt("total_hearts", 0)
        set(value) = prefs.edit().putInt("total_hearts", value).apply()

    var homeLat: Float
        get() = prefs.getFloat("home_lat", 50.9375f)
        set(value) = prefs.edit().putFloat("home_lat", value).apply()

    var homeLng: Float
        get() = prefs.getFloat("home_lng", 6.9603f)
        set(value) = prefs.edit().putFloat("home_lng", value).apply()
}
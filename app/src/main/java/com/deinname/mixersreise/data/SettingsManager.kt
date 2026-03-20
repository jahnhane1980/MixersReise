package com.deinname.mixersreise.data

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("mixer_prefs", Context.MODE_PRIVATE)

    var userName by PrefDelegate(prefs, "user_name", "Entdecßker")
    var homeAddress by PrefDelegate(prefs, "home_address", "Köln")
    var homeLat by PrefDelegate(prefs, "home_lat", 50.9375f)
    var homeLng by PrefDelegate(prefs, "home_lng", 6.9603f)
    var googleApiKey by PrefDelegate(prefs, "api_key", "")
    var googleMapId by PrefDelegate(prefs, "map_id", "")
    var totalHearts by PrefDelegate(prefs, "total_hearts", 0)
    var notificationSentTime by PrefDelegate(prefs, "notif_time", 0L)
    var isTestModeActive by PrefDelegate(prefs, "test_mode", false)
}

class PrefDelegate<T>(val prefs: SharedPreferences, val key: String, val default: T) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (default) {
            is String -> prefs.getString(key, default) as T
            is Int -> prefs.getInt(key, default) as T
            is Float -> prefs.getFloat(key, default) as T
            is Long -> prefs.getLong(key, default) as T
            is Boolean -> prefs.getBoolean(key, default) as T
            else -> default
        }
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(prefs.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                is Boolean -> putBoolean(key, value)
            }
            apply()
        }
    }
}
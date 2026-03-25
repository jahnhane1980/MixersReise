package com.deinname.mixersreise.data

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("mixer_prefs", Context.MODE_PRIVATE)

    // Bestehende Funktionen (Physisch im Repo)
    fun saveHearts(count: Int) = prefs.edit().putInt("hearts", count).apply()
    fun getHearts(): Int = prefs.getInt("hearts", 0)

    fun saveUserName(name: String) = prefs.edit().putString("user_name", name).apply()
    fun getUserName(): String? = prefs.getString("user_name", null)

    fun saveStreet(value: String) = prefs.edit().putString("street", value).apply()
    fun getStreet(): String? = prefs.getString("street", null)

    fun saveHouseNumber(value: String) = prefs.edit().putString("house_no", value).apply()
    fun getHouseNumber(): String? = prefs.getString("house_no", null)

    fun saveZipCode(value: String) = prefs.edit().putString("zip", value).apply()
    fun getZipCode(): String? = prefs.getString("zip", null)

    fun saveCity(value: String) = prefs.edit().putString("city", value).apply()
    fun getCity(): String? = prefs.getString("city", null)

    // Diese beiden brauchen wir zwingend für die Distanz:
    fun saveLocation(lat: Double, lon: Double) {
        prefs.edit()
            .putFloat("latitude", lat.toFloat())
            .putFloat("longitude", lon.toFloat())
            .apply()
    }

    fun getLatitude(): Double = prefs.getFloat("latitude", 0f).toDouble()
    fun getLongitude(): Double = prefs.getFloat("longitude", 0f).toDouble()
}
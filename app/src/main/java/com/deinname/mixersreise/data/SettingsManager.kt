package com.deinname.mixersreise.data

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("mixer_settings", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_STREET = "street"
        private const val KEY_HOUSE_NUMBER = "house_number"
        private const val KEY_ZIP_CODE = "zip_code"
        private const val KEY_CITY = "city"
        private const val KEY_HEARTS = "total_hearts"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

    // Standard-Felder
    fun saveUserName(name: String) = prefs.edit().putString(KEY_USER_NAME, name).apply()
    fun getUserName(): String? = prefs.getString(KEY_USER_NAME, "")

    fun saveStreet(street: String) = prefs.edit().putString(KEY_STREET, street).apply()
    fun getStreet(): String? = prefs.getString(KEY_STREET, "")

    fun saveHouseNumber(number: String) = prefs.edit().putString(KEY_HOUSE_NUMBER, number).apply()
    fun getHouseNumber(): String? = prefs.getString(KEY_HOUSE_NUMBER, "")

    fun saveZipCode(zip: String) = prefs.edit().putString(KEY_ZIP_CODE, zip).apply()
    fun getZipCode(): String? = prefs.getString(KEY_ZIP_CODE, "")

    fun saveCity(city: String) = prefs.edit().putString(KEY_CITY, city).apply()
    fun getCity(): String? = prefs.getString(KEY_CITY, "")

    fun saveHearts(hearts: Int) = prefs.edit().putInt(KEY_HEARTS, hearts).apply()
    fun getHearts(): Int = prefs.getInt(KEY_HEARTS, 0)

    // Koordinaten-Felder für die Entfernungsberechnung
    fun saveLocation(lat: Double, lon: Double) {
        prefs.edit()
            .putFloat(KEY_LATITUDE, lat.toFloat())
            .putFloat(KEY_LONGITUDE, lon.toFloat())
            .apply()
    }

    fun getLatitude(): Double = prefs.getFloat(KEY_LATITUDE, 0f).toDouble()
    fun getLongitude(): Double = prefs.getFloat(KEY_LONGITUDE, 0f).toDouble()
}
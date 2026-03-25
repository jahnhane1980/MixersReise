package com.deinname.mixersreise.data

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("mixer_prefs", Context.MODE_PRIVATE)

    fun saveUserName(name: String) {
        sharedPreferences.edit().putString("user_name", name).apply()
    }

    fun getUserName(): String? = sharedPreferences.getString("user_name", "")

    fun saveHearts(hearts: Int) {
        sharedPreferences.edit().putInt("total_hearts", hearts).apply()
    }

    fun getHearts(): Int = sharedPreferences.getInt("total_hearts", 0)

    fun saveLocation(latitude: Double, longitude: Double) {
        sharedPreferences.edit()
            .putLong("latitude", java.lang.Double.doubleToRawLongBits(latitude))
            .putLong("longitude", java.lang.Double.doubleToRawLongBits(longitude))
            .apply()
    }

    fun getLatitude(): Double = java.lang.Double.longBitsToDouble(sharedPreferences.getLong("latitude", 0L))
    fun getLongitude(): Double = java.lang.Double.longBitsToDouble(sharedPreferences.getLong("longitude", 0L))

    fun saveStreet(street: String) {
        sharedPreferences.edit().putString("street", street).apply()
    }

    fun getStreet(): String? = sharedPreferences.getString("street", "")

    fun saveHouseNumber(number: String) {
        sharedPreferences.edit().putString("house_number", number).apply()
    }

    fun getHouseNumber(): String? = sharedPreferences.getString("house_number", "")

    fun saveZipCode(zip: String) {
        sharedPreferences.edit().putString("zip_code", zip).apply()
    }

    fun getZipCode(): String? = sharedPreferences.getString("zip_code", "")

    fun saveCity(city: String) {
        sharedPreferences.edit().putString("city", city).apply()
    }

    fun getCity(): String? = sharedPreferences.getString("city", "")
}
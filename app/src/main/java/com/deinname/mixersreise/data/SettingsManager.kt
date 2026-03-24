package com.deinname.mixersreise.data

import android.content.Context

class SettingsManager(context: Context) {
    private val prefs = context.getSharedPreferences("mixer_prefs", Context.MODE_PRIVATE)

    fun saveUserName(name: String) = prefs.edit().putString("user_name", name).apply()
    fun getUserName(): String? = prefs.getString("user_name", null)

    fun saveHearts(count: Int) = prefs.edit().putInt("total_hearts", count).apply()
    fun getHearts(): Int = prefs.getInt("total_hearts", 0)

    // Notwendige Erweiterung für den SettingsDialog
    fun saveAddress(street: String, houseNumber: String, zipCode: String, city: String) {
        prefs.edit().apply {
            putString("street", street)
            putString("house_number", houseNumber)
            putString("zip_code", zipCode)
            putString("city", city)
            apply()
        }
    }

    fun getStreet(): String? = prefs.getString("street", null)
    fun getHouseNumber(): String? = prefs.getString("house_number", null)
    fun getZipCode(): String? = prefs.getString("zip_code", null)
    fun getCity(): String? = prefs.getString("city", null)
}
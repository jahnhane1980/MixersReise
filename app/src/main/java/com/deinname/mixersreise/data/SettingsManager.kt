package com.deinname.mixersreise.data

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("mixer_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOTAL_HEARTS = "total_hearts"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_STREET = "address_street"
        private const val KEY_HOUSE_NUMBER = "address_house_number"
        private const val KEY_ZIP_CODE = "address_zip_code"
        private const val KEY_CITY = "address_city"
        private const val KEY_LATITUDE = "address_latitude"
        private const val KEY_LONGITUDE = "address_longitude"

        private const val DEFAULT_NAME = "Reisender"
        private const val EMPTY_STRING = ""
        private const val DEFAULT_COORD = 0f
    }

    var totalHearts: Int
        get() = prefs.getInt(KEY_TOTAL_HEARTS, 0)
        set(value) = prefs.edit().putInt(KEY_TOTAL_HEARTS, value).apply()

    var userName: String
        get() = prefs.getString(KEY_USER_NAME, DEFAULT_NAME) ?: DEFAULT_NAME
        set(value) = prefs.edit().putString(KEY_USER_NAME, value).apply()

    var street: String
        get() = prefs.getString(KEY_STREET, EMPTY_STRING) ?: EMPTY_STRING
        set(value) = prefs.edit().putString(KEY_STREET, value).apply()

    var houseNumber: String
        get() = prefs.getString(KEY_HOUSE_NUMBER, EMPTY_STRING) ?: EMPTY_STRING
        set(value) = prefs.edit().putString(KEY_HOUSE_NUMBER, value).apply()

    var zipCode: String
        get() = prefs.getString(KEY_ZIP_CODE, EMPTY_STRING) ?: EMPTY_STRING
        set(value) = prefs.edit().putString(KEY_ZIP_CODE, value).apply()

    var city: String
        get() = prefs.getString(KEY_CITY, EMPTY_STRING) ?: EMPTY_STRING
        set(value) = prefs.edit().putString(KEY_CITY, value).apply()

    var latitude: Float
        get() = prefs.getFloat(KEY_LATITUDE, DEFAULT_COORD)
        set(value) = prefs.edit().putFloat(KEY_LATITUDE, value).apply()

    var longitude: Float
        get() = prefs.getFloat(KEY_LONGITUDE, DEFAULT_COORD)
        set(value) = prefs.edit().putFloat(KEY_LONGITUDE, value).apply()
}
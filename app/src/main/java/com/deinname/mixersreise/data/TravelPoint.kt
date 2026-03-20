package com.deinname.mixersreise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travel_points")
data class TravelPoint(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    val heartsCollected: Int,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis()
)
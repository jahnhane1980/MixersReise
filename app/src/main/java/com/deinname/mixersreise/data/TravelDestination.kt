package com.deinname.mixersreise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travel_destinations")
data class TravelDestination(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String,
    val isDiscovered: Boolean = false,
    val heartsCollected: Int = 0
)
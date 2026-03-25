package com.deinname.mixersreise.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// R3: Cross-File Validation - Index hinzugefügt, um Dubletten bei cityName zu verhindern
@Entity(
    tableName = "travel_destinations",
    indices = [Index(value = ["cityName"], unique = true)]
)
data class TravelDestination(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String,
    val isDiscovered: Boolean = false,
    val heartsCollected: Int = 0
)
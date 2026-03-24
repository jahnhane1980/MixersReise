package com.deinname.mixersreise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelDao {
    @Query("SELECT * FROM travel_destinations")
    fun getAllDestinations(): Flow<List<TravelDestination>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(destination: TravelDestination)

    @Query("UPDATE travel_destinations SET heartsCollected = heartsCollected + :amount WHERE cityName = :cityName")
    suspend fun addHeartsToCity(cityName: String, amount: Int)

    @Query("SELECT * FROM travel_destinations WHERE cityName = :cityName LIMIT 1")
    suspend fun getDestinationByName(cityName: String): TravelDestination?
}
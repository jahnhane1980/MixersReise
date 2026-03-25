package com.deinname.mixersreise.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelDao {

    @Query("SELECT * FROM travel_destinations ORDER BY heartsCollected DESC")
    fun getAllDestinations(): Flow<List<TravelDestination>>

    // R2: Signature Synchronicity - Name beibehalten, Strategie auf REPLACE geändert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(destination: TravelDestination)

    @Query("UPDATE travel_destinations SET heartsCollected = heartsCollected + :amount WHERE cityName = :cityName")
    suspend fun addHeartsToCity(cityName: String, amount: Int)

    @Query("SELECT * FROM travel_destinations WHERE cityName = :cityName LIMIT 1")
    suspend fun getDestinationByName(cityName: String): TravelDestination?

    @Query("SELECT SUM(heartsCollected) FROM travel_destinations")
    suspend fun getTotalHeartsSum(): Int?
}
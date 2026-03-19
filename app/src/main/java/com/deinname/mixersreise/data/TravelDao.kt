package com.deinname.mixersreise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelDao {
    @Query("SELECT * FROM travel_points ORDER BY timestamp DESC")
    fun getAllPoints(): Flow<List<TravelPoint>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(point: TravelPoint)
}
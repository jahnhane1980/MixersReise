package com.com.deinname.mixersreise.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Entity(tableName = "travel_points")
data class TravelPoint(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val heartsCollected: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface TravelDao {
    @Query("SELECT * FROM travel_points ORDER BY timestamp DESC")
    fun getAllPoints(): Flow<List<TravelPoint>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(point: TravelPoint)
}

@Database(entities = [TravelPoint::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun travelDao(): TravelDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "mixer_db")
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { database ->
                                scope.launch(Dispatchers.IO) {
                                    val dao = database.travelDao()
                                    dao.insertPoint(TravelPoint(0, "Köln", 50.9375, 6.9603, 120))
                                    dao.insertPoint(TravelPoint(0, "New York", 40.7128, -74.0060, 500))
                                    dao.insertPoint(TravelPoint(0, "Singapur", 1.3521, 103.8198, 1000))
                                }
                            }
                        }
                    }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
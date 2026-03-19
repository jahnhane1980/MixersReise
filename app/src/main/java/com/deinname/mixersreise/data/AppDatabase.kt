package com.deinname.mixersreise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TravelPoint::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun travelDao(): TravelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mixer_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Initial-Daten beim ersten Start laden
                            INSTANCE?.let { database ->
                                scope.launch(Dispatchers.IO) {
                                    val dao = database.travelDao()
                                    dao.insertPoint(TravelPoint(cityName = "Köln (Home)", latitude = 50.9375, longitude = 6.9603, heartsCollected = 100))
                                    dao.insertPoint(TravelPoint(cityName = "New York", latitude = 40.7128, longitude = -74.0060, heartsCollected = 500))
                                    dao.insertPoint(TravelPoint(cityName = "Seoul", latitude = 37.5665, longitude = 126.9780, heartsCollected = 800))
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
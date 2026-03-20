package com.deinname.mixersreise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TravelPoint::class], version = 2, exportSchema = false)
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
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            scope.launch(Dispatchers.IO) {
                                INSTANCE?.let { database ->
                                    val dao = database.travelDao()
                                    // FIX: Explizite Zuweisung verhindert Fehler
                                    dao.insertPoint(TravelPoint(cityName = "Köln (Home)", heartsCollected = 100, latitude = 50.9375, longitude = 6.9603))
                                    dao.insertPoint(TravelPoint(cityName = "Porz", heartsCollected = 150, latitude = 50.8833, longitude = 7.0500))
                                    dao.insertPoint(TravelPoint(cityName = "New York", heartsCollected = 500, latitude = 40.7128, longitude = -74.0060))
                                    dao.insertPoint(TravelPoint(cityName = "Seoul", heartsCollected = 800, latitude = 37.5665, longitude = 126.9780))
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
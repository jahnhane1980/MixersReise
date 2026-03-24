package com.deinname.mixersreise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TravelDestination::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun travelDao(): TravelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mixers_reise_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            // Daten nur einfügen, wenn die DB frisch geöffnet wird
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    val dao = database.travelDao()
                                    // R3: Minimalist Selection - Nur einfügen, wenn Köln noch nicht existiert
                                    if (dao.getDestinationByName("Köln") == null) {
                                        dao.insertDestination(TravelDestination(cityName = "Köln", heartsCollected = 120, isDiscovered = true))
                                        dao.insertDestination(TravelDestination(cityName = "New York", heartsCollected = 500, isDiscovered = true))
                                        dao.insertDestination(TravelDestination(cityName = "Singapur", heartsCollected = 1000, isDiscovered = true))
                                    }
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
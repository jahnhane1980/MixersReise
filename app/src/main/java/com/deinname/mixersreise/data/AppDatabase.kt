package com.deinname.mixersreise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Wir importieren hier nichts extra, da alles im selben Package 'data' liegt.
// Das verhindert die "Unresolved Reference" Fehler.

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
                    .fallbackToDestructiveMigration() // WICHTIG: Löscht alte DB-Reste bei Fehlern
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Der Trick: Wir warten nicht auf INSTANCE, sondern nutzen den Scope
                            scope.launch(Dispatchers.IO) {
                                // Wir holen uns die Instanz hier frisch, wenn sie fertig ist
                                INSTANCE?.travelDao()?.insertPoint(
                                    TravelPoint(
                                        cityName = "Heimat",
                                        heartsCollected = 100,
                                        latitude = 50.9375,
                                        longitude = 6.9603
                                    )
                                )
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
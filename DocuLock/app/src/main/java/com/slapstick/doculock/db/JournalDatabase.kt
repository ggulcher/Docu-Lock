package com.slapstick.doculock.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Journal::class], version = 1, exportSchema = false)
abstract class JournalDatabase: RoomDatabase() {

    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: JournalDatabase? = null

        fun getDatabase(context: Context): JournalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    "journal_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
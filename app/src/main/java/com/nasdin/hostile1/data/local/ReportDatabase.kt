package com.nasdin.hostile1.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ReportEntity::class], version = 1)
abstract class ReportDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao

    companion object {
        @Volatile private var INSTANCE: ReportDatabase? = null

        fun getInstance(context: Context): ReportDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ReportDatabase::class.java,
                    "report_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
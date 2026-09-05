package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnalysisEntity::class], version = 1, exportSchema = false)
abstract class KinematicDatabase : RoomDatabase() {
    abstract fun analysisDao(): AnalysisDao

    companion object {
        @Volatile
        private var INSTANCE: KinematicDatabase? = null

        fun getDatabase(context: Context): KinematicDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KinematicDatabase::class.java,
                    "kinematic_biomechanics.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

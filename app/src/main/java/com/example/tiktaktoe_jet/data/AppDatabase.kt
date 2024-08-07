package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.dao.PreparationDataDao
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.model.PreparationData

@Database(entities = [PreparationData::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun preparationDataDao(): PreparationDataDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}


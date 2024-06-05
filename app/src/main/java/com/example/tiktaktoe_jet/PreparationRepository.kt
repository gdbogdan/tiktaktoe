
package com.example.tiktaktoe_jet

import android.content.Context
import androidx.room.Room


class PreparationRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "preparation_database"
    ).fallbackToDestructiveMigration().build()

    private val preparationDataDao = db.preparationDataDao()

    suspend fun getPreparationData(id: Int): PreparationData? {
        return preparationDataDao.getPreparationData(id)
    }

    suspend fun savePreparationData(data: PreparationData) {
        preparationDataDao.insert(data)
    }
}

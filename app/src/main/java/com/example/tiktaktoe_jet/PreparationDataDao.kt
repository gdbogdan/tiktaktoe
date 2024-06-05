package com.example.tiktaktoe_jet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PreparationDataDao {
    @Query("SELECT * FROM preparation_data WHERE id = :id LIMIT 1")
    suspend fun getPreparationData(id: Int): PreparationData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PreparationData)
}

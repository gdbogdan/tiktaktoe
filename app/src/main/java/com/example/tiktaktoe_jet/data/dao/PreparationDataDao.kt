package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.model.PreparationData

@Dao
interface PreparationDataDao {
    @Query("SELECT * FROM preparation_data WHERE id = :id LIMIT 1")
    suspend fun getPreparationData(id: Int): PreparationData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PreparationData)
}

package com.example.tiktaktoe_jet.com.example.tiktaktoe_jet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preparation_data")
data class PreparationData(
    @PrimaryKey val id: Int,
    val aliasText: String,
    val time: Boolean,
    val difficulty: String
)

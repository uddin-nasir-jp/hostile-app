package com.nasdin.hostile1.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_table")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String?,
    val rating: Int,
    val latitude: Double,
    val longitude: Double
)
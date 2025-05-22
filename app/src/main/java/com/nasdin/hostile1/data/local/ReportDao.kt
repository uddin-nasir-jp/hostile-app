package com.nasdin.hostile1.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: ReportEntity)

    @Query("SELECT * FROM report_table")
    suspend fun getAllReports(): List<ReportEntity>
}
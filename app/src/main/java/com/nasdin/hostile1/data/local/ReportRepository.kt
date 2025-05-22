package com.nasdin.hostile1.data.local

class ReportRepository(private val dao: ReportDao) {
    suspend fun insertReport(report: ReportEntity) = dao.insert(report)
    suspend fun getReports(): List<ReportEntity> = dao.getAllReports()
}
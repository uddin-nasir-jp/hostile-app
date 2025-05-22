package com.nasdin.hostile1.model


data class ReportData(
    val imageUri: String?,
    val rating: Int,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis()
)
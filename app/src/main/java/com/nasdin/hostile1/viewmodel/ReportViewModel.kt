package com.nasdin.hostile1.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasdin.hostile1.data.local.ReportDatabase
import com.nasdin.hostile1.data.local.ReportEntity
import com.nasdin.hostile1.data.local.ReportRepository
import kotlinx.coroutines.launch

class ReportViewModel(context: Context) : ViewModel() {
    private val db = ReportDatabase.getInstance(context)
    private val repository = ReportRepository(db.reportDao())

    private val _reports = mutableStateListOf<ReportEntity>()
    val reports: List<ReportEntity> = _reports

    fun saveReport(report: ReportEntity) {
        viewModelScope.launch {
            repository.insertReport(report)
            loadReports()
        }
    }

    fun loadReports() {
        viewModelScope.launch {
            _reports.clear()
            _reports.addAll(repository.getReports())
        }
    }
}
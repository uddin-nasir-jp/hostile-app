package com.nasdin.hostile1.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nasdin.hostile1.model.StressData
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.nasdin.hostile1.Utility.MapUtils
import com.nasdin.hostile1.data.local.ReportDatabase
import com.nasdin.hostile1.data.local.ReportEntity
import com.nasdin.hostile1.data.local.ReportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KeplerViewModel(application: Application) : AndroidViewModel(application) {
    // Location properties
    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    // Database properties
    private val db = ReportDatabase.getInstance(application)
    private val repository = ReportRepository(db.reportDao())

    private val _reports = mutableStateListOf<ReportEntity>()
    val reports: List<ReportEntity> = _reports

    fun loadStressData(context: Context): List<StressData> {
        val jsonString = context.assets.open("kepler_data.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<StressData>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    fun getMarkerColor(stressLevel: String): BitmapDescriptor {
        return when (stressLevel.lowercase()) {
            "calm" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            "slightly stressed" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
            "stressed" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
            "highly stressed" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            else -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE) // default/fallback
        }
    }

    fun fetchLocation(context: Context) {
        MapUtils.getCurrentLocation(context) { loc ->
            _location.value = loc
        }
    }

    fun saveReport(report: ReportEntity) {
        viewModelScope.launch {
            repository.insertReport(report)
            //loadReports()
        }
    }

    fun loadReports() {
        viewModelScope.launch {
            _reports.clear()
            _reports.addAll(repository.getReports())
        }
    }
}
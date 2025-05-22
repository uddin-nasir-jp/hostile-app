package com.nasdin.hostile1.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class StressData(
    val latitude: Double,
    val longitude: Double,
    val timestamp: String,
    val Combined_Stress: Double,
    val State: String
): ClusterItem {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)
    override fun getTitle(): String = "Stress: $Combined_Stress"
    override fun getSnippet(): String = "State: $State"
}
package com.nasdin.hostile1.Utility

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

object LocationUtils {
    fun getCurrentLocation(context: Context, onLocationResult: (Location?) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onLocationResult(null)
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                onLocationResult(location)
            }
            .addOnFailureListener {
                onLocationResult(null)
            }
    }
}
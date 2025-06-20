package com.nasdin.hostile1.Utility

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

object MapUtils {
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

    fun createScaledDotBitmap(color: Int, size: Int = 32): Bitmap {
        val paint = Paint().apply {
            this.color = color
            isAntiAlias = true
        }

        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawCircle(size / 2f, size / 2f, size / 2.5f, paint)
        return bitmap
    }

    fun getDotColorForStress(stressType: String): Int {
        return when (stressType.lowercase()) {
            "calm" -> Color.GREEN
            "slightly stressed" -> Color.YELLOW
            "stressed" -> 0xFFFFA500.toInt() // Orange
            "highly stressed" -> Color.RED
            else -> Color.GRAY
        }
    }

    fun isWithin10Km(currentLat: Double, currentLng: Double, targetLat: Double, targetLng: Double): Boolean {
        val results = FloatArray(1)
        Location.distanceBetween(currentLat, currentLng, targetLat, targetLng, results)
        return results[0] <= 10000 // 10 km in meters
    }

    fun promptEnableLocation(activity: Activity) {
        val locationRequest = LocationRequest.create()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true) // Important: shows the dialog

        val settingsClient = LocationServices.getSettingsClient(activity)
        val task = settingsClient.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            // All settings are satisfied. Location can be accessed.
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(activity, 1001)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
}
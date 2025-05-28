package com.nasdin.hostile1.Utility

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

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
}
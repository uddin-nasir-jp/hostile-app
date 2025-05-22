package com.nasdin.hostile1.ui.screen.mapview

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nasdin.hostile1.viewmodel.KeplerViewModel

@Composable
fun StressMap(viewModel: KeplerViewModel) {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()

    AndroidView(factory = { mapView }) { map ->
        map.getMapAsync { googleMap ->
            val initial = LatLng(41.3851, 2.1734)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initial, 10f))
            val hostileData = viewModel.loadStressData(context)
            googleMap.clear()
            hostileData.forEach { data ->
                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(data.latitude, data.longitude))
                        .title("Combined stress:" + " ${data.Combined_Stress}")
                        .snippet("State:" + " ${data.State}")
                        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .icon(viewModel.getMarkerColor(data.State))
                )
            }
        }
    }
}
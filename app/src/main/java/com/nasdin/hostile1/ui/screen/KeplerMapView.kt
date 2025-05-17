package com.hostile.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nasdin.hostile1.viewmodel.KeplerViewModel

@SuppressLint("MissingPermission")
@Composable
fun KeplerMapView(navController: NavController) {
    val viewModel: KeplerViewModel = viewModel()
    val keplerPoints = viewModel.keplerData

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { ctx ->
            val mapView = MapView(ctx)
            mapView.onCreate(null)
            mapView.onResume()

            MapsInitializer.initialize(ctx)

            mapView.getMapAsync(OnMapReadyCallback { googleMap ->
                val initial = LatLng(41.3851, 2.1734)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initial, 10f))

                keplerPoints.forEach { data ->
                    val position = LatLng(data.latitude, data.longitude)
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(position)
                            .title("Combined stress:" + " ${data.Combined_Stress}")
                            .snippet("State:" + " ${data.State}")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    )
                }

                googleMap.setOnMarkerClickListener { marker ->
                    marker.showInfoWindow()
                    true
                }
            })

            mapView
        }, modifier = Modifier.fillMaxSize())

        Button(onClick = { navController.navigate("homeView") },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd))
        {
            Text("Home")
        }
    }
}

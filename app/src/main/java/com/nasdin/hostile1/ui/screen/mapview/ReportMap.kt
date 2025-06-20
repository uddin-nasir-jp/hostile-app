package com.nasdin.hostile1.ui.screen.mapview

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nasdin.hostile1.Utility.MapUtils
import com.nasdin.hostile1.data.local.ReportEntity
import com.nasdin.hostile1.ui.components.RectButton
import com.nasdin.hostile1.viewmodel.KeplerViewModel

@Composable
fun ReportMap(viewModel: KeplerViewModel) {
    val context = LocalContext.current
    val reports = viewModel.reports
    val mapView = rememberMapViewWithLifecycle()

    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    var selectedReport by remember { mutableStateOf<ReportEntity?>(null) }

    // Load data only once
    LaunchedEffect(viewModel) {
        viewModel.loadReports()
    }

    // Initialize MapView
    AndroidView(factory = { mapView }) { mapView ->
        mapView.getMapAsync { map ->
            googleMap = map
            map.uiSettings.isZoomControlsEnabled = true
        }
    }

    // Add markers after map is ready and data is loaded
    LaunchedEffect(googleMap, reports.size) {
        googleMap?.let { map ->
            map.clear()
            reports.forEach { report ->
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(LatLng(report.latitude, report.longitude))
                        .title("Rating: ${report.rating}")
                        .icon(BitmapDescriptorFactory.defaultMarker())
                )
                marker?.tag = report
            }

            // Move camera to user's location (optional)
            MapUtils.getCurrentLocation(context) { location ->
                location?.let {
                    val current = LatLng(it.latitude, it.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 12f))
                }
            }

            // Handle marker click
            map.setOnMarkerClickListener { marker ->
                val report = marker.tag as? ReportEntity
                report?.let {
                    selectedReport = it
                }
                true
            }
        }
    }

    // Show Dialog on marker click
    selectedReport?.let { report ->
        AlertDialog(
            onDismissRequest = { selectedReport = null },
            confirmButton = {
                TextButton(onClick = { selectedReport = null }) {
                    Text("Close")
                }
            },
            title = { Text("Report Info") },
            text = {
                Column {
                    AsyncImage(model = report.imageUri, contentDescription = null)
                    Text(
                        text = "Rating: ${report.rating}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        )
    }
}
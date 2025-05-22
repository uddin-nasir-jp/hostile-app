package com.nasdin.hostile1.ui.screen.mapview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nasdin.hostile1.data.local.ReportEntity
import com.nasdin.hostile1.ui.components.RectButton
import com.nasdin.hostile1.viewmodel.KeplerViewModel

@Composable
fun ReportMap(viewModel: KeplerViewModel) {
    val mapView = rememberMapViewWithLifecycle()
    var selectedReport by remember { mutableStateOf<ReportEntity?>(null) }

    AndroidView(factory = { mapView }) { map ->
        viewModel.loadReports()
        map.getMapAsync { googleMap ->
            if (viewModel.reports.isNotEmpty()) {
                val initial =
                    LatLng(viewModel.reports.first().latitude, viewModel.reports.first().longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initial, 10f))
                googleMap.clear()
                viewModel.reports.forEach { data ->
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(data.latitude, data.longitude))
                    )
                    googleMap.setOnMarkerClickListener { clickedMarker ->
                        selectedReport = data
                        true
                    }
                }
            }
        }
    }
    selectedReport?.let { report ->
        AlertDialog(
            onDismissRequest = { selectedReport = null },
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
            },
            confirmButton = {
                RectButton(text = "OK",
                    onClick = { selectedReport = null },
                    backgroundColor = Color(0xFF4CAF50),
                    modifier = Modifier
                        //.align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                )
            }
        )
    }
}
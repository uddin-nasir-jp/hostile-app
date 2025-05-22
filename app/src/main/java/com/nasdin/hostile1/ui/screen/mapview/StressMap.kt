package com.nasdin.hostile1.ui.screen.mapview

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.nasdin.hostile1.model.StressData
import com.nasdin.hostile1.viewmodel.KeplerViewModel

@Composable
fun StressMap(viewModel: KeplerViewModel) {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()
    val hostileData = viewModel.loadStressData(context)

    AndroidView({ mapView }) { mapView ->
        mapView.getMapAsync { googleMap ->
            val clusterManager = ClusterManager<StressData>(context, googleMap)
            googleMap.setOnCameraIdleListener(clusterManager)
            googleMap.setOnMarkerClickListener(clusterManager)

            val initial = LatLng(41.3851, 2.1734)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initial, 10f))
            // Set your custom renderer if needed
            clusterManager.renderer =
                object : DefaultClusterRenderer<StressData>(context, googleMap, clusterManager) {
                    override fun onBeforeClusterItemRendered(
                        item: StressData,
                        markerOptions: MarkerOptions
                    ) {
                        val color = when {
                            item.Combined_Stress.toString().equals(
                                "calm",
                                ignoreCase = true
                            ) -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)

                            item.Combined_Stress.toString().equals(
                                "slightly stressed",
                                ignoreCase = true
                            ) -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)

                            item.Combined_Stress.toString().equals(
                                "stressed",
                                ignoreCase = true
                            ) -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)

                            item.Combined_Stress.toString().equals(
                                "highly stressed",
                                ignoreCase = true
                            ) -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)

                            else -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE) // default/fallback
                        }
                        markerOptions.icon(color)
                    }
                }

            val items = hostileData.map {
                StressData(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    timestamp = it.timestamp,
                    Combined_Stress = it.Combined_Stress,
                    State = it.State
                )
            }

            clusterManager.addItems(items)
            clusterManager.cluster()

            // Optional: Marker click info window
            clusterManager.setOnClusterItemClickListener { item ->
                Toast.makeText(
                    context,
                    "Stress: ${item.Combined_Stress}, State: ${item.State}",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }
    }
}
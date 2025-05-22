package com.nasdin.hostile1.ui.screen.mapview

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.maps.MapView

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = androidx.compose.ui.R.id.compose_view_saveable_id_tag // just a unique id
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val observer = object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> mapView.onCreate(null)
                    Lifecycle.Event.ON_START -> mapView.onStart()
                    Lifecycle.Event.ON_RESUME -> mapView.onResume()
                    Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                    Lifecycle.Event.ON_STOP -> mapView.onStop()
                    Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                    else -> Unit
                }
            }
        }
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return mapView
}
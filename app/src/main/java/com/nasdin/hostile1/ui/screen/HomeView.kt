package com.hostile.view

import android.Manifest
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nasdin.hostile1.permission.PermissionHandler
import com.nasdin.hostile1.ui.components.RectButton

@Composable
fun HomeView(navController: NavController) {
    var triggerPermissionRequest by remember { mutableStateOf(false) }

    if (triggerPermissionRequest) {
        PermissionHandler(
            permissions = listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) { granted ->
            triggerPermissionRequest = false
            if (granted) {
                Log.d("Permissions", "All permissions granted. Navigating to CameraView")
                navController.navigate("cameraView")
            } else {
                Log.d("Permissions", "Permissions denied.")
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.DarkGray, Color.DarkGray)
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp) // Padding inside background
        ) {
            Text(
                text = "MENU",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            RectButton(text = "REPORT", icon = Icons.Default.EmojiEvents, iconDescription = "Reward icon",
                onClick = { triggerPermissionRequest = true },
                backgroundColor = Color(0xFF4CAF50)
            )

            Spacer(modifier = Modifier.width(16.dp))

            RectButton(text = "EXPLORE", icon = Icons.Default.LocationOn, iconDescription = "Reward icon",
                onClick = { navController.navigate("mapView") },
                backgroundColor = Color(0xFF4CAF50)
            )
        }
    }
}
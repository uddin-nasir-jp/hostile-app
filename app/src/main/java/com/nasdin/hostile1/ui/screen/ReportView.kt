package com.hostile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nasdin.hostile1.ui.components.RectButton

@Composable
fun ReportView(navController: NavController, imageUri: String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RectButton(text = "REPORT", icon = Icons.Default.Report, iconDescription = "Report",
                onClick = { navController.navigate("RatingView") },
                backgroundColor = Color(0xFF4CAF50)
            )

            Spacer(modifier = Modifier.height(16.dp))

            RectButton(text = "REFRESH", icon = Icons.Default.Refresh, iconDescription = "Refresh",
                onClick = { navController.navigate("cameraView") },
                backgroundColor = Color(0xFF4CAF50)
            )
        }
        Button(onClick = { navController.navigate("homeView") },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd))
        {
            Text("Home")
        }
    }
}
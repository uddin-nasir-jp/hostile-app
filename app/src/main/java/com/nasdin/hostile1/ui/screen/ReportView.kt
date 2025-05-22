package com.hostile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
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
//            Image(
//                painter = rememberAsyncImagePainter(model = imageUri),
//                contentDescription = null,
//                modifier = Modifier.height(200.dp).fillMaxWidth()
//            )
            AsyncImage(
                model = imageUri,
                contentDescription = "Captured Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            RectButton(text = "REPORT", icon = Icons.Default.Report, iconDescription = "Report",
                onClick = { navController.navigate("RatingView?imageUri=${imageUri}") },
                backgroundColor = Color(0xFF4CAF50)
            )

            Spacer(modifier = Modifier.height(16.dp))

            RectButton(text = "REFRESH", icon = Icons.Default.Refresh, iconDescription = "Refresh",
                onClick = { navController.navigate("cameraView") },
                backgroundColor = Color(0xFF4CAF50)
            )
        }
        // Home button
        RectButton(text = "", icon = Icons.Default.Home,
            onClick = { navController.navigate("homeView") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            backgroundColor = Color(0xFF4CAF50)
        )
    }
}
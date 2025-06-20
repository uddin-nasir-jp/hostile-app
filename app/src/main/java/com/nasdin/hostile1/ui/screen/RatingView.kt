package com.hostile.view

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.nasdin.hostile1.Utility.MapUtils
import com.nasdin.hostile1.data.local.ReportEntity
import com.nasdin.hostile1.model.ReportData
import com.nasdin.hostile1.ui.components.RectButton
import com.nasdin.hostile1.viewmodel.KeplerViewModel
import com.nasdin.hostile1.viewmodel.ReportViewModel
import java.util.Date

@Composable
fun RatingView(navController: NavController, keplerViewModel: KeplerViewModel, imageUri: String?) {
    val context = LocalContext.current
    val location by keplerViewModel.location.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color.DarkGray, Color.DarkGray)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp) // Padding inside background
            ) {
                Text(
                    text = "RATING",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                (1..5).forEach { rating ->
                    RectButton(
                        text = "$rating",
                        onClick = {
                            //keplerViewModel.fetchLocation(context)
                            MapUtils.getCurrentLocation(context) { location ->
                                location?.let {
                                    keplerViewModel.saveReport(
                                        ReportEntity(
                                            imageUri = imageUri,
                                            rating = rating,
                                            latitude = it.latitude,
                                            longitude = it.longitude
                                        )
                                    )
                                }
                            }
                            navController.navigate("RewardView")
                        },
                        backgroundColor = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
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
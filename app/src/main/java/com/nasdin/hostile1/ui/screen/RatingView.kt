package com.hostile.view

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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.nasdin.hostile1.ui.components.RectButton

@Composable
fun RatingView(navController: NavController, imageUri: String?) {
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
//                    Button(
//                        onClick = { navController.navigate("RewardView") },
//                        modifier = Modifier.padding(4.dp)
//                    ) {
//                        Text("$rating")
//                    }
                    RectButton(text = "$rating",
                        onClick = { navController.navigate("RewardView") },
                        backgroundColor = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
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
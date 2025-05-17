package com.hostile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nasdin.hostile1.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Report
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nasdin.hostile1.ui.components.RectButton

@Composable
fun RewardView(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Title at top center
        Text(
            text = "Reward!",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.DarkGray, Color.DarkGray)
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp) // Padding inside background
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "REWARD",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }

        // Centered static reward image
//        Image(
//            painter = painterResource(id = R.drawable.reward), // Replace with your drawable name
//            contentDescription = "Reward Image",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .size(200.dp)
//                .align(Alignment.Center)
//        )

        Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "Reward Icon",
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.Center),
            tint = MaterialTheme.colorScheme.primary
        )

        RectButton(text = "OK",
            onClick = { navController.navigate("reportMoreView") },
            backgroundColor = Color(0xFF4CAF50),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}
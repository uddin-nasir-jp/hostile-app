package com.hostile.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nasdin.hostile1.ui.components.RectButton
import com.nasdin.hostile1.ui.screen.mapview.StressMap
import com.nasdin.hostile1.ui.screen.mapview.ReportMap
import com.nasdin.hostile1.viewmodel.KeplerViewModel

@SuppressLint("MissingPermission")
@Composable
fun KeplerMapView(navController: NavController, keplerViewModel: KeplerViewModel ) {
    val tabs = listOf("Report Map", "Stress Map")
    var selectedTabIndex by remember { mutableStateOf(0) }


    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> ReportMap(keplerViewModel)
                1 -> StressMap(keplerViewModel)
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

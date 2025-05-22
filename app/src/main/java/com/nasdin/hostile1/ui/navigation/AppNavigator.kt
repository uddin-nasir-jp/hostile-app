package com.nasdin.hostile1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hostile.view.CameraView
import com.hostile.view.HomeView
import com.hostile.view.KeplerMapView
import com.hostile.view.RatingView
import com.hostile.view.ReportMoreView
import com.hostile.view.ReportView
import com.hostile.view.RewardView
import com.nasdin.hostile1.viewmodel.KeplerViewModel
import com.nasdin.hostile1.viewmodel.ReportViewModel

@Composable
fun AppNavigator(keplerViewModel: KeplerViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "homeView") {
        composable("homeView") {
            HomeView(navController)
        }
        composable("cameraView") {
            CameraView(
                navController = navController,
                onImageCaptured = { /* Optional: store or log */ },
                onError = { /* Show error */ }
            )
        }
        composable(
            "reportView?imageUri={imageUri}",
            arguments = listOf(navArgument("imageUri") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("imageUri")
            ReportView(navController = navController, imageUri = imageUri)
        }
        composable(
            "reportView?imageUri={imageUri}",
            arguments = listOf(navArgument("imageUri") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("imageUri")
            ReportView(navController = navController, imageUri = imageUri)
        }
        composable(
            "ratingView?imageUri={imageUri}",
            arguments = listOf(navArgument("imageUri") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("imageUri")
            RatingView(navController = navController, keplerViewModel, imageUri = imageUri)
        }
        composable("rewardView") {
            RewardView(navController)
        }
        composable("reportMoreView") {
            ReportMoreView(navController)
        }
        composable("mapView") {
            KeplerMapView(navController, keplerViewModel)
        }
    }
}

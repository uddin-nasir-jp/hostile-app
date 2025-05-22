package com.nasdin.hostile1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.nasdin.hostile1.ui.navigation.AppNavigator
import com.nasdin.hostile1.ui.theme.Hostile1Theme
import com.nasdin.hostile1.viewmodel.KeplerViewModel
import com.nasdin.hostile1.viewmodel.ReportViewModel

class MainActivity : ComponentActivity() {
    private val keplerViewModel: KeplerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hostile1Theme {
                AppNavigator(keplerViewModel)
            }
        }
    }
}
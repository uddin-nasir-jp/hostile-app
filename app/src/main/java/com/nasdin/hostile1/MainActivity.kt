package com.nasdin.hostile1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nasdin.hostile1.ui.navigation.AppNavigator
import com.nasdin.hostile1.ui.theme.Hostile1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hostile1Theme {
                AppNavigator()
            }
        }
    }
}
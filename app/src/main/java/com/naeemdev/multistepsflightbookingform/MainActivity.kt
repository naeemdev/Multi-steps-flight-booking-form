package com.naeemdev.multistepsflightbookingform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.naeemdev.multistepsflightbookingform.navigation.Navigation
import com.naeemdev.multistepsflightbookingform.ui.theme.MultiStepsFlightBookingFormTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiStepsFlightBookingFormTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}
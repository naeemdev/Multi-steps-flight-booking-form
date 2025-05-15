package com.naeemdev.multistepsflightbookingform.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.naeemdev.multistepsflightbookingform.BuildConfig
import com.naeemdev.multistepsflightbookingform.navigation.Screen.BookingSuccessScreenRoute
import com.naeemdev.multistepsflightbookingform.navigation.Screen.PassengerContactScreenRoute
import com.naeemdev.multistepsflightbookingform.navigation.Screen.PassengerInfoRoute
import com.naeemdev.multistepsflightbookingform.navigation.Screen.PassengerPassportScreenRoute
import com.naeemdev.multistepsflightbookingform.presentation.booking.PassengerContactScreen
import com.naeemdev.multistepsflightbookingform.presentation.booking.PassengerInfoScreen
import com.naeemdev.multistepsflightbookingform.presentation.booking.PassengerPassportScreen
import com.naeemdev.multistepsflightbookingform.presentation.booking.viewmodel.BookingViewModel
import com.naeemdev.multistepsflightbookingform.presentation.success.BookingSuccessScreen

fun <T : Screen> NavController.navigateSingleTop(screen: T) {
    this.navigate(screen) {
        launchSingleTop = true
    }
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
) {
    val sharedViewModel: BookingViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = PassengerInfoRoute,
    ) {
        composable<PassengerInfoRoute> {
            PassengerInfoScreen(
                viewModel = sharedViewModel,
                onNextClicked = {
                    if (BuildConfig.IS_INTERNATIONAL) {
                        navController.navigateSingleTop(PassengerPassportScreenRoute)
                    }else{
                        navController.navigateSingleTop(PassengerContactScreenRoute)
                    }
                },
            )
        }

        composable<PassengerPassportScreenRoute> {
            PassengerPassportScreen(
                viewModel = sharedViewModel,
                onNextClicked = {
                    navController.navigateSingleTop(PassengerContactScreenRoute)
                },
                onBackClicked = {
                    navController.navigateUp()
                }
            )
        }
        composable<PassengerContactScreenRoute> {
            PassengerContactScreen(
                viewModel = sharedViewModel,
                onBackClicked = {
                    navController.navigateUp()
                },
                onNextClicked = {
                    navController.navigateSingleTop(BookingSuccessScreenRoute)
                },
            )
        }
        composable<BookingSuccessScreenRoute> {
            BookingSuccessScreen(
                onBackClicked = {
                    navController.navigate(PassengerInfoRoute) {
                        popUpTo(PassengerInfoRoute) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}


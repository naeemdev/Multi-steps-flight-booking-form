package com.naeemdev.multistepsflightbookingform.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object PassengerInfoRoute : Screen()

    @Serializable
    data object PassengerPassportScreenRoute : Screen()

    @Serializable
    data object PassengerContactScreenRoute : Screen()

    @Serializable
    data object BookingSuccessScreenRoute : Screen()
}
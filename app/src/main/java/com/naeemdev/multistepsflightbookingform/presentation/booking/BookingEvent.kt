package com.naeemdev.multistepsflightbookingform.presentation.booking


sealed interface BookingEvent {
    data class Error(val error: Int): BookingEvent
    data object Success: BookingEvent
}
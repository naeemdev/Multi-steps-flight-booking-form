package com.naeemdev.multistepsflightbookingform.presentation.booking.state

data class PassportState(
    val format: String = "",
    val passportNumber: String = "",
    val expiryDate: Long = System.currentTimeMillis(),
    val example: String = "",
)
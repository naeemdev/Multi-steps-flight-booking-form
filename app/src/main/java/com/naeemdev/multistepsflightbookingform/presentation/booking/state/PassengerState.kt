package com.naeemdev.multistepsflightbookingform.presentation.booking.state

import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD

data class PassengerState(
    val name: String = "",
    val dob: Long = System.currentTimeMillis(),
    val gender: String = "",
    val countryIso: String = "",
    val nationality: PassportFormatD? = null,
)

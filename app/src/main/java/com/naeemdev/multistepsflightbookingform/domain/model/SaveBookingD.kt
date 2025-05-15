package com.naeemdev.multistepsflightbookingform.domain.model

data class SaveBookingD(
    val id: Int,
    val name: String,
    val dob: String,
    val nationality: String,
    val gender: String,
    val passportNumber: String?,
    val email: String,
    val phone: String
)
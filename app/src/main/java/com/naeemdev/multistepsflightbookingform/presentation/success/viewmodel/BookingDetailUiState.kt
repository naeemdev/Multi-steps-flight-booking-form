package com.naeemdev.multistepsflightbookingform.presentation.success.viewmodel

data class BookingDetailUiState(
    val name: String = "",
    val dob: String = "",
    val nationality: String = "",
    val gender: String = "",
    val passportNumber: String = "",
    val email: String = "",
    val phone: String = "",
    val isLoading: Boolean = false,
)
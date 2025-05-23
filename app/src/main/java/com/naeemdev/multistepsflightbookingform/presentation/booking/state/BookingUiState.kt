package com.naeemdev.multistepsflightbookingform.presentation.booking.state

import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD
import com.naeemdev.multistepsflightbookingform.domain.model.resquest.SaveBookingRequest
import com.naeemdev.multistepsflightbookingform.extension.toFormattedDate


data class BookingUiState(
    val passportFormatsList: List<PassportFormatD> = emptyList(),
    val isLoading: Boolean = false,
    val serverErrorMessage: Int? = null,
    val passportExpiryDateSelected: Boolean = false,
    val dateSelected: Boolean = false,
    val bookingId: Long = 0,
    val passenger: PassengerState = PassengerState(),
    val contact: ContactState = ContactState(),
    val passport: PassportState = PassportState(),
)

fun BookingUiState.toSaveBookingRequest(): SaveBookingRequest {
    return SaveBookingRequest(
        name = passenger.name,
        dob = passenger.dob.toFormattedDate(),
        nationality = passenger.nationality?.nationality.orEmpty(),
        gender = passenger.gender,
        passportNumber = passport.passportNumber,
        email = contact.email,
        phone = contact.phone,
        passportExpiryDate = passport.expiryDate.toFormattedDate(),
    )
}
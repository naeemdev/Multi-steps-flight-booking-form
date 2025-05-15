package com.naeemdev.multistepsflightbookingform.presentation.booking

import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD

sealed interface BookingAction {
    data class OnFullName(val name: String): BookingAction
    data class OnDateOfBirth(val dateOfBirth: Long): BookingAction
    data class OnGenderSelection(val gender: String): BookingAction
    data class OnNationality(val passportFormatD: PassportFormatD): BookingAction
    data class OnPassportNumber(val passportNumber: String): BookingAction
    data class OnPassportExpiryDate(val expiryDate: Long): BookingAction
    data class OnEmail(val email: String): BookingAction
    data class OnPhoneNumber(val phoneNumber: String): BookingAction
    data object OnStepOne: BookingAction
    data object OnStepTwo: BookingAction
    data object OnSubmit: BookingAction
}
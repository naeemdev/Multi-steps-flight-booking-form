package com.naeemdev.multistepsflightbookingform.domain.usecase


import com.naeemdev.multistepsflightbookingform.domain.model.resquest.SaveBookingRequest
import com.naeemdev.multistepsflightbookingform.domain.repositories.BookingRepository
import jakarta.inject.Inject

class SaveBookingUseCase @Inject constructor(
    private val repository: BookingRepository
) {
    suspend operator fun invoke( saveBooking: SaveBookingRequest): Long {
        return repository.saveBooking(
            saveBooking = saveBooking
        )
    }
}
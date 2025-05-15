package com.naeemdev.multistepsflightbookingform.domain.usecase


import com.naeemdev.multistepsflightbookingform.domain.repositories.BookingRepository
import jakarta.inject.Inject

class DeleteBookingUseCase @Inject constructor(
    private val repository: BookingRepository
) {
    suspend operator fun invoke() {
        return repository.deleteBookingHistory()
    }
}
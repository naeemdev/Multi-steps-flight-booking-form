package com.naeemdev.multistepsflightbookingform.domain.usecase


import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.model.SaveBookingD
import com.naeemdev.multistepsflightbookingform.domain.repositories.BookingRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class BookingDetailUseCase @Inject constructor(
    private val repository: BookingRepository
) {
    suspend operator fun invoke(): Flow<Resource<SaveBookingD?>> {
        return repository.getBookingDetail()
    }
}
package com.naeemdev.multistepsflightbookingform.domain.repositories

import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.model.SaveBookingD
import com.naeemdev.multistepsflightbookingform.domain.model.resquest.SaveBookingRequest
import kotlinx.coroutines.flow.Flow


interface BookingRepository {
    suspend fun getBookingDetail(): Flow<Resource<SaveBookingD?>>

    suspend fun saveBooking(
        saveBooking: SaveBookingRequest
    ): Long

    suspend fun deleteBookingHistory()

}
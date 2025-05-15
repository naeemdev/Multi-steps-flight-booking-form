package com.naeemdev.multistepsflightbookingform.data.repositories


import com.naeemdev.multistepsflightbookingform.data.local.dao.BookingDao
import com.naeemdev.multistepsflightbookingform.data.mapper.BookingMapper.mapToDomain
import com.naeemdev.multistepsflightbookingform.data.mapper.BookingMapper.mapToEntry
import com.naeemdev.multistepsflightbookingform.domain.ErrorType
import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.model.SaveBookingD
import com.naeemdev.multistepsflightbookingform.domain.model.resquest.SaveBookingRequest
import com.naeemdev.multistepsflightbookingform.domain.repositories.BookingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val bookingDao: BookingDao,
) : BookingRepository {


    override suspend fun getBookingDetail(): Flow<Resource<SaveBookingD?>> {
        return flow {
            val detail = bookingDao.getBookingById()
            if (detail != null) {
                emit(Resource.Success(detail.mapToDomain()))
            } else {
                emit(Resource.Error(ErrorType.BOOKING_NOT_FOUND))
            }
        }
    }

    override suspend fun saveBooking(saveBooking: SaveBookingRequest): Long {
        return bookingDao.insertBooking(saveBooking.mapToEntry())
    }

    override suspend fun deleteBookingHistory() {
        bookingDao.deleteAllBooking()
    }

}
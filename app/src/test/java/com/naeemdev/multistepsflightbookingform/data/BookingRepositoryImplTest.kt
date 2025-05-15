package com.naeemdev.multistepsflightbookingform.data

import com.naeemdev.multistepsflightbookingform.data.mapper.BookingMapper.mapToEntry
import com.naeemdev.multistepsflightbookingform.data.remote.local.dao.BookingDao
import com.naeemdev.multistepsflightbookingform.data.remote.local.entity.BookingEntity
import com.naeemdev.multistepsflightbookingform.data.repositories.BookingRepositoryImpl
import com.naeemdev.multistepsflightbookingform.domain.ErrorType
import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.model.resquest.SaveBookingRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

class BookingRepositoryImplTest {
    private lateinit var bookingDao: BookingDao
    private lateinit var bookingRepository: BookingRepositoryImpl

    @Before
    fun setup() {
        bookingDao = mockk()
        bookingRepository = BookingRepositoryImpl(bookingDao)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getBookingDetail when booking exists`() = runTest {
        val mockBooking = BookingEntity(
            nationality = "USA",
            id = 1,
            name = "John Doe",
            dob = "1990-01-01",
            gender = "Male",
            passportNumber = "A1234567",
            email = "john.doe@example.com",
            phone = "+123456789",
            passportExpiryDate = "2050-05-10"
        )
        coEvery { bookingDao.getBookingById() } returns mockBooking
        val result = bookingRepository.getBookingDetail()
        result.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    assertNotNull(resource.data)
                    assertEquals("USA", resource.data.nationality)
                    assertEquals(1, resource.data.id)
                    assertEquals("John Doe", resource.data.name)
                }

                else -> fail("Expected Resource.Success")
            }
        }

        coVerify { bookingDao.getBookingById() }
    }

    @Test
    fun `test getBookingDetail when booking does not exist`() = runTest {

        coEvery { bookingDao.getBookingById() } returns null

        val result = bookingRepository.getBookingDetail()

        result.collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    assertEquals(ErrorType.BOOKING_NOT_FOUND, resource.errorType)
                }

                else -> fail("Expected Resource.Error")
            }
        }
        coVerify { bookingDao.getBookingById() }
    }

    @Test
    fun `test saveBooking`() = runTest {
        val saveBookingRequest = SaveBookingRequest(
            nationality = "Canada",
            name = "Jane Doe",
            dob = "1985-05-10",
            gender = "Female",
            passportNumber = "B9876543",
            email = "jane.doe@example.com",
            phone = "+987654321",
            passportExpiryDate = "2050-05-10"
        )
        coEvery { bookingDao.insertBooking(any()) } returns 1L

        val result = bookingRepository.saveBooking(saveBookingRequest)
        assertEquals(1L, result)

        coVerify { bookingDao.insertBooking(saveBookingRequest.mapToEntry()) }
    }

    @Test
    fun `test deleteBookingHistory`() = runTest {
        coEvery { bookingRepository.deleteBookingHistory() } returns run {}

        bookingRepository.deleteBookingHistory()

        coVerify { bookingDao.deleteAllBooking() }
    }

}
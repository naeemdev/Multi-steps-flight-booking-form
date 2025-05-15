package com.naeemdev.multistepsflightbookingform.domain.usecases

import com.naeemdev.multistepsflightbookingform.domain.ErrorType
import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.model.SaveBookingD
import com.naeemdev.multistepsflightbookingform.domain.repositories.BookingRepository
import com.naeemdev.multistepsflightbookingform.domain.usecase.BookingDetailUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BookingDetailUseCaseTest {
    private lateinit var bookingRepository: BookingRepository
    private lateinit var bookingDetailUseCase: BookingDetailUseCase

    @Before
    fun setup() {
        bookingRepository = mockk()

        bookingDetailUseCase = BookingDetailUseCase(bookingRepository)
    }

    @Test
    fun `test invoke when booking exists`() = runTest {
        val mockBooking = SaveBookingD(
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
        coEvery { bookingRepository.getBookingDetail() } returns flowOf(Resource.Success(mockBooking))

        val resultFlow = bookingDetailUseCase.invoke()

        resultFlow.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    assertNotNull(resource.data)
                    assertEquals("USA", resource.data.nationality)
                    assertEquals(1, resource.data.id)
                    assertEquals("John Doe", resource.data.name)
                }

                else -> throw AssertionError("Expected Resource.Success")
            }
        }

        coVerify { bookingRepository.getBookingDetail() }
    }

    @Test
    fun `test invoke when booking does not exist`() = runTest {
        coEvery { bookingRepository.getBookingDetail() } returns flowOf(Resource.Error(ErrorType.BOOKING_NOT_FOUND))
        val resultFlow = bookingDetailUseCase.invoke()
        resultFlow.collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    assertEquals(ErrorType.BOOKING_NOT_FOUND, resource.errorType)
                }

                else -> throw AssertionError("Expected Resource.Error")
            }
        }
        coVerify { bookingRepository.getBookingDetail() }
    }
}
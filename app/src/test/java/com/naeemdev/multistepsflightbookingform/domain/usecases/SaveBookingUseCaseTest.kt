package com.naeemdev.multistepsflightbookingform.domain.usecases

import com.naeemdev.multistepsflightbookingform.domain.model.resquest.SaveBookingRequest
import com.naeemdev.multistepsflightbookingform.domain.repositories.BookingRepository
import com.naeemdev.multistepsflightbookingform.domain.usecase.SaveBookingUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SaveBookingUseCaseTest {
    private lateinit var bookingRepository: BookingRepository
    private lateinit var saveBookingUseCase: SaveBookingUseCase

    @Before
    fun setup() {
        bookingRepository = mockk()
        saveBookingUseCase = SaveBookingUseCase(bookingRepository)
    }

    @Test
    fun `test invoke when booking is saved successfully`() = runTest {
        val saveBookingRequest = SaveBookingRequest(
            nationality = "USA",
            name = "John Doe",
            dob = "1990-01-01",
            gender = "Male",
            passportNumber = "A1234567",
            email = "john.doe@example.com",
            phone = "+123456789",
            passportExpiryDate = "2050-05-10"
        )
        val mockSavedBookingId = 1L

        coEvery { bookingRepository.saveBooking(saveBookingRequest) } returns mockSavedBookingId

        val result = saveBookingUseCase.invoke(saveBookingRequest)
        assertEquals(mockSavedBookingId, result)
        coVerify { bookingRepository.saveBooking(saveBookingRequest) }
    }

    @Test
    fun `test invoke when saving booking fails`() = runTest {
        val saveBookingRequest = SaveBookingRequest(
            nationality = "USA",
            name = "John Doe",
            dob = "1990-01-01",
            gender = "Male",
            passportNumber = "A1234567",
            email = "john.doe@example.com",
            phone = "+123456789",
            passportExpiryDate = "2050-05-10"
        )
        val exception = Exception("Failed to save booking")

        coEvery { bookingRepository.saveBooking(saveBookingRequest) } throws exception

        assertFailsWith<Exception>("Failed to save booking") {
            saveBookingUseCase.invoke(saveBookingRequest)
        }

        coVerify { bookingRepository.saveBooking(saveBookingRequest) }
    }
}
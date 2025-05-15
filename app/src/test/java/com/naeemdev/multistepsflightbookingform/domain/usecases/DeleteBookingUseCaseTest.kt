package com.naeemdev.multistepsflightbookingform.domain.usecases

import com.naeemdev.multistepsflightbookingform.domain.repositories.BookingRepository
import com.naeemdev.multistepsflightbookingform.domain.usecase.DeleteBookingUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

class DeleteBookingUseCaseTest {
    private lateinit var bookingRepository: BookingRepository
    private lateinit var deleteBookingUseCase: DeleteBookingUseCase

    @Before
    fun setup() {
        bookingRepository = mockk()
        deleteBookingUseCase = DeleteBookingUseCase(bookingRepository)
    }

    @Test
    fun `test invoke deletes booking history`() = runTest {
        coEvery { deleteBookingUseCase() } returns run {}

        deleteBookingUseCase.invoke()
        coVerify { bookingRepository.deleteBookingHistory() }
    }

}
package com.naeemdev.multistepsflightbookingform.viewmodel

import app.cash.turbine.test
import com.naeemdev.multistepsflightbookingform.BaseCoroutineTest
import com.naeemdev.multistepsflightbookingform.dispatcher.DispatchersProvider
import com.naeemdev.multistepsflightbookingform.domain.Resource
import com.naeemdev.multistepsflightbookingform.domain.ValidationResult
import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD
import com.naeemdev.multistepsflightbookingform.domain.usecase.DeleteBookingUseCase
import com.naeemdev.multistepsflightbookingform.domain.usecase.GetPassportFormatListUseCase
import com.naeemdev.multistepsflightbookingform.domain.usecase.SaveBookingUseCase
import com.naeemdev.multistepsflightbookingform.domain.usecase.ValidationUseCases
import com.naeemdev.multistepsflightbookingform.presentation.booking.BookingAction
import com.naeemdev.multistepsflightbookingform.presentation.booking.BookingEvent
import com.naeemdev.multistepsflightbookingform.presentation.booking.viewmodel.BookingViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.mockito.MockitoAnnotations
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BookingViewModelTest : BaseCoroutineTest() {

    private lateinit var viewModel: BookingViewModel

    private val mockGetPassportFormatListUseCase: GetPassportFormatListUseCase = mockk()
    private val mockValidationUseCases: ValidationUseCases = mockk()
    private val mockSaveBookingUseCase: SaveBookingUseCase = mockk()
    private val mockDeleteBookingUseCase: DeleteBookingUseCase = mockk()

    private val mockDispatchersProvider: DispatchersProvider = mockk()


    @Before
    override fun setup() {
        MockitoAnnotations.openMocks(this)
        coEvery { mockGetPassportFormatListUseCase.invoke() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Success(listOf(PassportFormatD("USA", "###", "123"))))
        }
        coEvery { mockDeleteBookingUseCase.invoke() } just runs
        coEvery { mockDispatchersProvider.io } returns Dispatchers.IO
        coEvery { mockDispatchersProvider.default } returns Dispatchers.Default

        viewModel = BookingViewModel(
            getPassportFormatListUseCase = mockGetPassportFormatListUseCase,
            validationUseCases = mockValidationUseCases,
            saveBookingUseCase = mockSaveBookingUseCase,
            deleteBookingUseCase = mockDeleteBookingUseCase,
            dispatchersProvider = mockDispatchersProvider
        )
    }

    @Test
    fun `onAction OnFullName - updates passenger name in uiState`() =
        mainCoroutineDispatcher.runTest {
            val testName = "John Doe"
            viewModel.onAction(BookingAction.OnFullName(testName))
            viewModel.uiState.test {
                awaitItem()
                val updatedState = awaitItem()
                assertEquals(testName, updatedState.passenger.name)
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `onAction OnPhoneNumber - updates phone number in uiState`() =
        mainCoroutineDispatcher.runTest {

            val testPhone = "1234567890"
            viewModel.onAction(BookingAction.OnPhoneNumber(testPhone))

            viewModel.uiState.test {
                awaitItem()
                val updatedState = awaitItem()
                assertEquals(testPhone, updatedState.contact.phone)
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `onAction OnStepOne - success validation`() = mainCoroutineDispatcher.runTest {

        coEvery { mockValidationUseCases.validateName(any()) } returns ValidationResult.Valid
        coEvery { mockValidationUseCases.validateDob(any()) } returns ValidationResult.Valid
        coEvery { mockValidationUseCases.validateGender(any()) } returns ValidationResult.Valid
        coEvery { mockValidationUseCases.validateNationality(any()) } returns ValidationResult.Valid

        viewModel.onAction(BookingAction.OnStepOne)
        viewModel.uiState.test {
            awaitEvent()
            coVerify { mockValidationUseCases.validateName(any()) }
            coVerify { mockValidationUseCases.validateDob(any()) }
            coVerify { mockValidationUseCases.validateGender(any()) }
            coVerify { mockValidationUseCases.validateNationality(any()) }
            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `onAction OnStepOne - validation fails for name - sends error event`() =
        mainCoroutineDispatcher.runTest {
            val nameErrorId = 1
            every { mockValidationUseCases.validateName(any()) } returns ValidationResult.Invalid(
                nameErrorId
            )
            every { mockValidationUseCases.validateDob(any()) } returns ValidationResult.Valid
            every { mockValidationUseCases.validateGender(any()) } returns ValidationResult.Valid
            every { mockValidationUseCases.validateNationality(any()) } returns ValidationResult.Valid
            viewModel.events.test {
                viewModel.onAction(BookingAction.OnStepOne)
                val event = awaitItem()
                assertEquals(nameErrorId, (event as BookingEvent.Error).error)
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `onAction OnStepOne - validation fails for dob - sends error event`() =
        mainCoroutineDispatcher.runTest {
            val nameErrorId = 1
            every { mockValidationUseCases.validateDob(any()) } returns ValidationResult.Invalid(
                nameErrorId
            )
            every { mockValidationUseCases.validateName(any()) } returns ValidationResult.Valid
            every { mockValidationUseCases.validateGender(any()) } returns ValidationResult.Valid
            every { mockValidationUseCases.validateNationality(any()) } returns ValidationResult.Valid
            viewModel.events.test {
                viewModel.onAction(BookingAction.OnStepOne)
                val event = awaitItem()
                assertEquals(nameErrorId, (event as BookingEvent.Error).error)
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `onAction OnStepOne - validation fails for validateDob - sends error event`() =
        mainCoroutineDispatcher.runTest {

            val nameErrorId = 1
            every { mockValidationUseCases.validateGender(any()) } returns ValidationResult.Invalid(
                nameErrorId
            )
            every { mockValidationUseCases.validateName(any()) } returns ValidationResult.Valid
            every { mockValidationUseCases.validateDob(any()) } returns ValidationResult.Valid
            every { mockValidationUseCases.validateNationality(any()) } returns ValidationResult.Valid
            viewModel.events.test {
                viewModel.onAction(BookingAction.OnStepOne)
                val event = awaitItem()
                assertEquals(nameErrorId, (event as BookingEvent.Error).error)
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `onAction OnSubmit - success submits and saves booking`() =
        mainCoroutineDispatcher.runTest {

            every { mockValidationUseCases.validateEmail(any()) } returns ValidationResult.Valid
            every {
                mockValidationUseCases.validatePhone(
                    any(),
                    any()
                )
            } returns ValidationResult.Valid
            coEvery { mockSaveBookingUseCase.invoke(any()) } returns 1L

            viewModel.onAction(BookingAction.OnSubmit)

            viewModel.uiState.test {
                awaitItem()
                awaitItem()
                coVerify { mockSaveBookingUseCase.invoke(any()) }
                cancelAndConsumeRemainingEvents()
            }
        }
}
package com.naeemdev.multistepsflightbookingform.presentation.booking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naeemdev.multistepsflightbookingform.domain.ValidationResult
import com.naeemdev.multistepsflightbookingform.domain.usecase.DeleteBookingUseCase
import com.naeemdev.multistepsflightbookingform.domain.usecase.GetPassportFormatListUseCase
import com.naeemdev.multistepsflightbookingform.domain.usecase.SaveBookingUseCase
import com.naeemdev.multistepsflightbookingform.domain.usecase.ValidationUseCases
import com.naeemdev.multistepsflightbookingform.extension.toFormattedDate
import com.naeemdev.multistepsflightbookingform.presentation.booking.BookingAction
import com.naeemdev.multistepsflightbookingform.presentation.booking.BookingEvent
import com.naeemdev.multistepsflightbookingform.presentation.booking.state.BookingUiState
import com.naeemdev.multistepsflightbookingform.presentation.booking.state.toSaveBookingRequest
import com.naeemdev.multistepsflightbookingform.util.collectResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookingViewModel @Inject constructor(
    @Named("isInternational") private val isInternational: Boolean,
    private val getPassportFormatListUseCase: GetPassportFormatListUseCase,
    private val validationUseCases: ValidationUseCases,
    private val saveBookingUseCase: SaveBookingUseCase,
    private val deleteBookingUseCase: DeleteBookingUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookingUiState())

    val uiState = _uiState
        .onStart { fetchPassportFormatList() }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(10_000L), BookingUiState())

    private val eventChannel = Channel<BookingEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        _uiState.update { it.copy(isInternational = isInternational) }
    }

    fun fetchPassportFormatList() {
        viewModelScope.launch(Dispatchers.IO) {
            getPassportFormatListUseCase.invoke()
                .collectResource(
                    onLoading = { _uiState.update { it.copy(isLoading = true) } },
                    onSuccess = { data ->
                        _uiState.update {
                            it.copy(
                                passportFormatsList = data,
                                isLoading = false
                            )
                        }
                    },
                    onError = { error ->
                        _uiState.update {
                            it.copy(
                                serverErrorMessage = error.errorMessageResId,
                                isLoading = false
                            )
                        }
                    }
                )
        }
    }

    fun onAction(bookingAction: BookingAction) {
        when (bookingAction) {
            is BookingAction.OnDateOfBirth -> handleDateOfBirthChange(bookingAction)
            is BookingAction.OnPhoneNumber -> handlePhoneNumberChange(bookingAction)
            is BookingAction.OnEmail -> handleEmailChange(bookingAction)
            is BookingAction.OnFullName -> handleFullNameChange(bookingAction)
            is BookingAction.OnGenderSelection -> handleGenderSelection(bookingAction)
            is BookingAction.OnNationality -> handleNationalityChange(bookingAction)
            is BookingAction.OnPassportExpiryDate -> handlePassportExpiryDateChange(bookingAction)
            is BookingAction.OnPassportNumber -> handlePassportNumberChange(bookingAction)
            is BookingAction.OnStepOne -> handleStepOneValidation()
            is BookingAction.OnStepTwo -> handleStepTwoValidation()
            is BookingAction.OnSubmit -> handleSubmitValidation()
        }
    }

    // Generic validation and state update
    private fun validateAndHandleError(
        validation: () -> ValidationResult?,
        onError: (Int) -> Unit,
        onSuccess: () -> Unit
    ) {
        validation()?.let { result ->
            if (result is ValidationResult.Invalid) {
                viewModelScope.launch {
                    onError(result.errorMessageId)
                }
                return
            }
        }
        onSuccess()
    }

    private fun handleDateOfBirthChange(bookingAction: BookingAction.OnDateOfBirth) {
        _uiState.update {
            it.copy(
                dateSelected = true,
                passenger = it.passenger.copy(
                    dob = bookingAction.dateOfBirth
                )
            )
        }
    }

    private fun handlePhoneNumberChange(bookingAction: BookingAction.OnPhoneNumber) {
        _uiState.update { it.copy(contact = it.contact.copy(phone = bookingAction.phoneNumber)) }
    }

    private fun handleEmailChange(bookingAction: BookingAction.OnEmail) {
        _uiState.update { it.copy(contact = it.contact.copy(email = bookingAction.email)) }
    }

    private fun handleFullNameChange(bookingAction: BookingAction.OnFullName) {
        _uiState.update {
            it.copy(
                passenger = it.passenger.copy(
                    name = bookingAction.name
                )
            )
        }
    }

    private fun handleGenderSelection(bookingAction: BookingAction.OnGenderSelection) {
        _uiState.update {
            it.copy(
                passenger = it.passenger.copy(
                    gender = bookingAction.gender
                )
            )
        }
    }

    private fun handleNationalityChange(bookingAction: BookingAction.OnNationality) {

        _uiState.update {
            it.copy(
                passenger = it.passenger.copy(
                    nationality = bookingAction.passportFormatD,
                    countryIso = uiState.value.passenger.nationality?.nationality ?: ""
                ),
                passport = it.passport.copy(
                    format = bookingAction.passportFormatD.format,
                    example = bookingAction.passportFormatD.example
                )
            )
        }
    }

    private fun handlePassportExpiryDateChange(bookingAction: BookingAction.OnPassportExpiryDate) {
        _uiState.update {
            it.copy(
                passportExpiryDateSelected = true,
                passport = it.passport.copy(expiryDate = bookingAction.expiryDate)
            )
        }
    }

    private fun handlePassportNumberChange(bookingAction: BookingAction.OnPassportNumber) {
        _uiState.update { it.copy(passport = it.passport.copy(passportNumber = bookingAction.passportNumber)) }
    }

    private fun handleStepOneValidation() {
        validateAndHandleError(
            validation = {
                listOf(
                    validationUseCases.validateName(uiState.value.passenger.name),
                    validationUseCases.validateDob(uiState.value.passenger.dob.toFormattedDate()),
                    validationUseCases.validateGender(uiState.value.passenger.gender),
                    validationUseCases.validateNationality(
                        uiState.value.passenger.nationality?.nationality ?: ""
                    )
                ).find { it is ValidationResult.Invalid }
            },
            onError = { errorMessageId -> sendError(errorMessageId) },
            onSuccess = { sendSuccess() }
        )
    }

    private fun handleStepTwoValidation() {
        validateAndHandleError(
            validation = {
                listOf(
                    validationUseCases.validatePassportNumber(
                        uiState.value.passport.passportNumber,
                        uiState.value.passport.format
                    ),
                    validationUseCases.validatePassportExpiryDate(uiState.value.passport.expiryDate.toFormattedDate())
                ).find { it is ValidationResult.Invalid }
            },
            onError = { errorMessageId -> sendError(errorMessageId) },
            onSuccess = { sendSuccess() }
        )
    }

    private fun handleSubmitValidation() {
        validateAndHandleError(
            validation = {
                listOf(
                    validationUseCases.validateEmail(uiState.value.contact.email),
                    validationUseCases.validatePhone(
                        uiState.value.contact.phone,
                        uiState.value.passenger.countryIso
                    )
                ).find { it is ValidationResult.Invalid }
            },
            onError = { errorMessageId -> sendError(errorMessageId) },
            onSuccess = {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteBookingUseCase()
                    saveBookingUseCase(
                        uiState.value.toSaveBookingRequest()
                    )
                    sendSuccess()
                    resetStateAfterSuccess()
                }
            }
        )
    }

    // Function to send error event
    private fun sendError(errorMessageId: Int) {
        viewModelScope.launch {
            eventChannel.send(BookingEvent.Error(errorMessageId))
        }
    }

    // Function to send success event
    private fun sendSuccess() {
        viewModelScope.launch {
            eventChannel.send(BookingEvent.Success)
        }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(serverErrorMessage = null) }
    }

    fun resetStateAfterSuccess() {
        _uiState.update {
            it.copy(
                isLoading = false,
                serverErrorMessage = null,
                canProceed = false,
                passengerError = null,
                passportError = null,
                contactError = null,
                passportExpiryDateSelected = false,
                dateSelected = false,
                nationalityError = null,
                emailError = null,
                phoneError = null,
                passenger = it.passenger.copy(
                    name = "",
                    dob = System.currentTimeMillis(),
                    gender = "",
                    nationality = null
                ),
                contact = it.contact.copy(
                    email = "",
                    phone = ""
                ),
                passport = it.passport.copy(
                    passportNumber = "",
                    expiryDate = System.currentTimeMillis(),
                    format = "",
                    example = ""
                )
            )
        }
    }
}




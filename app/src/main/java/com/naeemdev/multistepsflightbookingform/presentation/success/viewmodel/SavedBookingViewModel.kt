package com.naeemdev.multistepsflightbookingform.presentation.success.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naeemdev.multistepsflightbookingform.domain.usecase.BookingDetailUseCase
import com.naeemdev.multistepsflightbookingform.util.collectResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedBookingViewModel @Inject constructor(
    private val bookingDetailUseCase: BookingDetailUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookingDetailUiState())
    val uiState: StateFlow<BookingDetailUiState> = _uiState.asStateFlow()

    init {
        fetchBookingDetail()
    }

    fun fetchBookingDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            bookingDetailUseCase()
                .collectResource(
                    onLoading = { },
                    onSuccess = { data ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                name = data?.name ?: "",
                                nationality = data?.nationality ?: "",
                                dob = data?.dob ?: "",
                                phone = data?.phone ?: "",
                                gender = data?.gender ?: "",
                                email = data?.email ?: "",
                                passportNumber = data?.passportNumber.toString(),
                            )
                        }

                    },
                    onError = {}
                )
        }
    }


}




package com.naeemdev.multistepsflightbookingform.presentation.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naeemdev.multistepsflightbookingform.R
import com.naeemdev.multistepsflightbookingform.extension.toFormattedDate
import com.naeemdev.multistepsflightbookingform.presentation.booking.state.BookingUiState
import com.naeemdev.multistepsflightbookingform.presentation.booking.viewmodel.BookingViewModel
import com.naeemdev.multistepsflightbookingform.presentation.component.ActionButtonWithIcon
import com.naeemdev.multistepsflightbookingform.presentation.component.TopBar
import com.naeemdev.multistepsflightbookingform.presentation.component.datepicker.DatePickerDialog
import com.naeemdev.multistepsflightbookingform.presentation.component.datepicker.DatePickerSelectionMode
import com.naeemdev.multistepsflightbookingform.ui.theme.MultiStepsFlightBookingFormTheme
import com.naeemdev.multistepsflightbookingform.ui.theme.RedErrorLight
import com.naeemdev.multistepsflightbookingform.util.ObserveAsEvents

@Composable
fun PassengerPassportScreen(
    viewModel: BookingViewModel,
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var errorMessage by remember { mutableIntStateOf(0) }
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is BookingEvent.Error -> {
                errorMessage = event.error
            }

            is BookingEvent.Success -> {
                onNextClicked()
            }
        }
    }
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.enter_passport_details),
                navigationIconResId= R.drawable.outline_arrow_back_24,
                onBackClick = onBackClicked
            )
        },
        bottomBar = {
            ActionButtonWithIcon(
                onClick = {
                    viewModel.onAction(BookingAction.OnStepTwo)
                }
            )

        },
        containerColor = White
    ) { innerPadding ->
        PassengerPassportContent(
            modifier = Modifier.padding(innerPadding),
            state = uiState.value,
            errorMessage = errorMessage,
            onUiEvent = viewModel::onAction,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerPassportContent(
    modifier: Modifier,
    state: BookingUiState,
    errorMessage: Int? = null,
    onUiEvent: (BookingAction) -> Unit,
) {
    val datePickerDialog = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        if (errorMessage != null && errorMessage != 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(RedErrorLight)
                    .border(1.dp, Color.Red, RoundedCornerShape(4.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(errorMessage),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Text(
            text = stringResource(R.string.enter_passport_number),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.passport.passportNumber,
            onValueChange = {
                onUiEvent(BookingAction.OnPassportNumber(it.uppercase()))
            },
            placeholder = {
                Text(
                    text = "e.g ${state.passport.example}",
                    color = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )


        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                datePickerDialog.value = true
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(),
                text = if (state.passportExpiryDateSelected) {
                    state.passport.expiryDate.toFormattedDate()
                } else {
                    "Select passport expiry date"
                },
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
            )
        }

    }
    if (datePickerDialog.value) {
        DatePickerDialog(
            selectedDate = state.passport.expiryDate,
            modifier = Modifier,
            dateSelectionMode = DatePickerSelectionMode.FUTURE_DATES_ONLY_STRICT,
            onDateSelected = {
                datePickerDialog.value = false
                onUiEvent(BookingAction.OnPassportExpiryDate(it))
            },
            onCancel = {
                datePickerDialog.value = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PassengerPassportContentPreview() {
    MultiStepsFlightBookingFormTheme {
        PassengerPassportContent(
            modifier = Modifier,
            errorMessage = R.string.enter_your_full_name,
            state = BookingUiState(),
            onUiEvent = {}
        )
    }
}
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
import com.naeemdev.multistepsflightbookingform.presentation.component.ConfigurableItemPicker
import com.naeemdev.multistepsflightbookingform.presentation.component.LoadingView
import com.naeemdev.multistepsflightbookingform.presentation.component.TopBar
import com.naeemdev.multistepsflightbookingform.presentation.component.datepicker.DatePickerDialog
import com.naeemdev.multistepsflightbookingform.presentation.component.dialog.DialogButtonAction
import com.naeemdev.multistepsflightbookingform.presentation.component.dialog.DialogButtonType
import com.naeemdev.multistepsflightbookingform.presentation.component.dialog.ErrorDialog
import com.naeemdev.multistepsflightbookingform.ui.theme.MultiStepsFlightBookingFormTheme
import com.naeemdev.multistepsflightbookingform.ui.theme.RedErrorLight
import com.naeemdev.multistepsflightbookingform.util.NAME_MIN_LENGTH
import com.naeemdev.multistepsflightbookingform.util.ObserveAsEvents

@Composable
fun PassengerInfoScreen(
    viewModel: BookingViewModel,
    onNextClicked: () -> Unit,
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
                title = stringResource(R.string.user_info)
            )
        },
        bottomBar = {
            ActionButtonWithIcon(
                onClick = {
                    viewModel.onAction(BookingAction.OnStepOne)
                }
            )
        },
        containerColor = White
    ) { innerPadding ->
        PassengerInfoContent(
            modifier = Modifier.padding(innerPadding),
            state = uiState.value,
            errorMessage = errorMessage,
            onUiEvent = viewModel::onAction,
        )
    }
    if (uiState.value.isLoading) {
        LoadingView()
    }
    uiState.value.serverErrorMessage?.let {
        ErrorDialog(
            description = stringResource(it),
            buttons = listOf(
                DialogButtonAction(
                    text = stringResource(R.string.retry),
                    type = DialogButtonType.PRIMARY,
                    onClick = {
                        viewModel.onAction(BookingAction.GetPassportFormatList)
                    }
                )
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerInfoContent(
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
                    text = stringResource(errorMessage, NAME_MIN_LENGTH),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Text(
            text = stringResource(R.string.enter_your_full_name),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.passenger.name,
            onValueChange = {
                onUiEvent(BookingAction.OnFullName(it))
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.e_g_johan_devoid),
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
                text = if (state.dateSelected) {
                    state.passenger.dob.toFormattedDate()
                } else {
                    stringResource(R.string.select_your_dob)
                },
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        ConfigurableItemPicker(
            modifier = Modifier.padding(vertical = 8.dp),
            items = listOf(
                stringResource(R.string.male), stringResource(R.string.female),
                stringResource(R.string.other)
            ),
            selectedItem = state.passenger.gender,
            onItemSelected = {
                onUiEvent(BookingAction.OnGenderSelection(it))
            },
            itemToString = { genderString -> genderString },
            label = stringResource(R.string.select_your_gender),
            textFieldShape = RoundedCornerShape(8.dp)
        )
        ConfigurableItemPicker(
            modifier = Modifier.padding(vertical = 8.dp),
            items = state.passportFormatsList,
            selectedItem = state.passenger.nationality,
            onItemSelected = {
                onUiEvent(BookingAction.OnNationality(it))
            },
            itemToString = { passport -> passport.nationality },
            label = stringResource(R.string.select_your_nationality),
            textFieldShape = RoundedCornerShape(8.dp)
        )

    }
    if (datePickerDialog.value) {
        DatePickerDialog(
            selectedDate = state.passenger.dob,
            modifier = Modifier,
            onDateSelected = {
                datePickerDialog.value = false
                onUiEvent(BookingAction.OnDateOfBirth(it))
            },
            onCancel = {
                datePickerDialog.value = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PassengerInfoContentPreview() {
    MultiStepsFlightBookingFormTheme {
        PassengerInfoContent(
            modifier = Modifier,
            errorMessage = R.string.enter_your_full_name,
            state = BookingUiState(),
            onUiEvent = {}
        )
    }
}
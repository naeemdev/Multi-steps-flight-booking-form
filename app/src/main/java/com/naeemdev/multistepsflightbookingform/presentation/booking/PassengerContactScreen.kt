package com.naeemdev.multistepsflightbookingform.presentation.booking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naeemdev.multistepsflightbookingform.R
import com.naeemdev.multistepsflightbookingform.presentation.booking.state.BookingUiState
import com.naeemdev.multistepsflightbookingform.presentation.booking.viewmodel.BookingViewModel
import com.naeemdev.multistepsflightbookingform.presentation.component.TopBar
import com.naeemdev.multistepsflightbookingform.ui.theme.MultiStepsFlightBookingFormTheme
import com.naeemdev.multistepsflightbookingform.ui.theme.RedErrorLight
import com.naeemdev.multistepsflightbookingform.util.ObserveAsEvents

@Composable
fun PassengerContactScreen(
    viewModel: BookingViewModel,
    onNextClicked: (Long) -> Unit,
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
                errorMessage = 0
                Log.d("bookingId", "${uiState.value.bookingId}")

                onNextClicked(uiState.value.bookingId)
            }

        }
    }
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.contact_info),
                navigationIconResId= R.drawable.outline_arrow_back_24,
                onBackClick = onBackClicked
            )
        },
        bottomBar = {
            Button(
                onClick = {
                   viewModel.onAction(BookingAction.OnSubmit)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp,
                        horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    style = MaterialTheme.typography.titleMedium,
                    color = White
                )
            }
        },
        containerColor = White
    ) { innerPadding ->
        PassengerContactContent(
            modifier = Modifier.padding(innerPadding),
            state = uiState.value,
            errorMessage = errorMessage,
            onUiEvent = viewModel::onAction,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassengerContactContent(
    modifier: Modifier,
    state: BookingUiState,
    errorMessage: Int? = null,
    onUiEvent: (BookingAction) -> Unit,
) {

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
            text = stringResource(R.string.enter_email),
            style = MaterialTheme.typography.titleMedium,
        )

        OutlinedTextField(
            value = state.contact.email,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            onValueChange = {
                onUiEvent(BookingAction.OnEmail(it))
            },
            placeholder = {
                Text(
                    text = "e.g john@gmail.com",
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            text = stringResource(R.string.enter_phone_number),
            style = MaterialTheme.typography.titleMedium,
        )

        OutlinedTextField(
            value = state.contact.phone,
            onValueChange = {
                onUiEvent(BookingAction.OnPhoneNumber(it.toString()))
            },
            placeholder = {
                Text(
                    text = "e.g +923138788880",
                    color = Color.Gray
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PassengerContactContentPreview() {
    MultiStepsFlightBookingFormTheme {
        PassengerContactContent(
            modifier = Modifier,
            errorMessage = R.string.enter_your_full_name,
            state = BookingUiState(),
            onUiEvent = {}
        )
    }
}
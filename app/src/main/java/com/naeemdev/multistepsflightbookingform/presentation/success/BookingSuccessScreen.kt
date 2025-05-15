package com.naeemdev.multistepsflightbookingform.presentation.success

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naeemdev.multistepsflightbookingform.BuildConfig
import com.naeemdev.multistepsflightbookingform.R
import com.naeemdev.multistepsflightbookingform.presentation.component.TopBar
import com.naeemdev.multistepsflightbookingform.presentation.success.viewmodel.BookingDetailUiState
import com.naeemdev.multistepsflightbookingform.presentation.success.viewmodel.SavedBookingViewModel
import com.naeemdev.multistepsflightbookingform.ui.theme.MultiStepsFlightBookingFormTheme

@Composable
fun BookingSuccessScreen(
    onBackClicked: () -> Unit,
) {
    val viewmodel = hiltViewModel<SavedBookingViewModel>()
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
    BackHandler {
        onBackClicked()
    }
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.booking_confirmed),
                navigationIconResId = R.drawable.outline_arrow_back_24,
                onBackClick = onBackClicked
            )
        },
        containerColor = White
    ) { innerPadding ->
        BookingSuccessContent(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingSuccessContent(
    modifier: Modifier,
    uiState: BookingDetailUiState
) {
    Log.d("testingDetail", "BookingSuccessContent: ${uiState.name}")
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.your_booking_was_successful),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.thank_you_for_your_reservation_we_look_forward_to_hosting_you),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Ticket(
            passengerName = uiState.name,
            dob = uiState.dob,
            nationality = uiState.nationality,
            gender = uiState.gender,
            phoneNumber = uiState.phone,
            email = uiState.email,
            passportNumber = uiState.passportNumber,
            passportExpiry = uiState.passportExpiryDate
        )
    }
}

@Composable
fun Ticket(
    dob: String,
    nationality: String,
    gender: String,
    phoneNumber: String,
    passengerName: String,
    email: String,
    passportNumber: String,
    passportExpiry: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.passenger_info),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = passengerName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = stringResource(R.string.dob),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dob,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Blue
                    )
                }
            }
            if (BuildConfig.IS_INTERNATIONAL) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.passport_info),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = passportNumber,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = stringResource(R.string.expiry),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = passportExpiry,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Blue
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(0.6f))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.nationality),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = nationality,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.gender),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = gender,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = stringResource(R.string.phone_number),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = phoneNumber,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Text(
                text = email,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                textAlign = TextAlign.Center
            )

            DottedLineDivider()
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_barcode),
                    contentDescription = "Barcode",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

    }
}


@Composable
fun DottedLineDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        val canvasWidth = size.width
        val dotRadius = 2.dp.toPx()
        val spaceBetweenDots = 4.dp.toPx()
        var currentX = 0f

        while (currentX < canvasWidth) {
            drawCircle(
                color = Color.Gray,
                radius = dotRadius,
                center = Offset(x = currentX, y = size.height / 2)
            )
            currentX += dotRadius * 2 + spaceBetweenDots
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BookingConfirmationPreview() {
    MultiStepsFlightBookingFormTheme {
        Ticket(
            dob = "Nov 20",
            nationality = "Pakistan",
            gender = "Male",
            phoneNumber = "090990090909",
            email = "naeem@gmail.com",
            passengerName = "Naeem",
            passportExpiry = "Nov 20",
            passportNumber = "AB1234567",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookingSuccessContentPreview() {
    MultiStepsFlightBookingFormTheme {
        BookingSuccessContent(
            modifier = Modifier,
            uiState = BookingDetailUiState(
                name = "John Doe",
                dob = "1990-01-01",
                nationality = "US",
                gender = "Male",
                passportNumber = "A12345678",
                email = "johndoe@example.com",
                phone = "+1234567890"
            ),
        )
    }
}
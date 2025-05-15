package com.naeemdev.multistepsflightbookingform.presentation.component.datepicker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.naeemdev.multistepsflightbookingform.R
import com.naeemdev.multistepsflightbookingform.extension.toFormattedDate
import com.naeemdev.multistepsflightbookingform.ui.theme.MultiStepsFlightBookingFormTheme

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    modifier: Modifier,
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
    title: String = stringResource(R.string.select_date),
    initialDisplayMode: DisplayMode = DisplayMode.Picker,
    dateSelectionMode: DatePickerSelectionMode = DatePickerSelectionMode.PAST_ONLY,
    onCancel: () -> Unit
) {
    val selectableDatesStrategy = remember(dateSelectionMode) {
        when (dateSelectionMode) {
            DatePickerSelectionMode.PAST_ONLY -> SelectableDatesForPastOnly
            DatePickerSelectionMode.CURRENT_AND_FUTURE_DATES -> SelectableDatesForCurrentAndFuture
            DatePickerSelectionMode.FUTURE_DATES_ONLY_STRICT -> SelectableDatesForFutureOnlyStrict
        }
    }


    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate,
        initialDisplayedMonthMillis = selectedDate,
        yearRange = DatePickerDefaults.YearRange,
        selectableDates = selectableDatesStrategy,
        initialDisplayMode = initialDisplayMode
    )
    val dateText = remember(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.toFormattedDate() ?: selectedDate.toFormattedDate()
    }

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            modifier = Modifier.wrapContentSize()
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = modifier,
                    title = {
                        Text(
                            text = title,
                            color = Color.Black,
                            modifier = Modifier.padding(16.dp)
                        )
                    },
                    headline = {
                        Text(
                            text = dateText,
                            modifier = Modifier.padding(start = 16.dp),
                            color = Color.DarkGray
                        )
                    },
                    showModeToggle = true,
                    colors = DatePickerDefaults.colors().copy(
                        containerColor = Color.White,
                        titleContentColor = Color.Red,

                        dateTextFieldColors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Blue,
                        )

                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancel) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(it)
                        }
                        onCancel()
                    }) {
                        Text(text = stringResource(R.string.ok))
                    }

                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun DisplayDatePreview() {
    MultiStepsFlightBookingFormTheme {
        DatePickerDialog(
            selectedDate = System.currentTimeMillis(),
            modifier = Modifier,
            onDateSelected = {},
            onCancel = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun DisplayDatePreviewWithInput() {
    MultiStepsFlightBookingFormTheme {
        DatePickerDialog(
            selectedDate = System.currentTimeMillis(),
            modifier = Modifier,
            onDateSelected = {},
            onCancel = {},
            initialDisplayMode = DisplayMode.Input,
            title = "",
        )
    }
}


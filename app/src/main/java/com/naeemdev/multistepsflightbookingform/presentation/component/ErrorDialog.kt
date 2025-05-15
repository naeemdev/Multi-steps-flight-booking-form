package com.naeemdev.multistepsflightbookingform.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.naeemdev.multistepsflightbookingform.R
import com.naeemdev.multistepsflightbookingform.ui.theme.MultiStepsFlightBookingFormTheme

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    description: String,
    buttonText: String,
    onClickButton: () -> Unit,
    onDismissRequest: () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .size(35.dp),
                    painter = painterResource(id = R.drawable.baseline_warning_amber_24),
                    tint = Color.Red,
                    contentDescription = stringResource(R.string.error_title),
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 10.dp,
                            horizontal = 16.dp
                        )
                        .height(40.dp),
                    onClick = onClickButton
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = buttonText,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorsDialog() {
    MultiStepsFlightBookingFormTheme {
        ErrorDialog(
            description = "testing ",
            buttonText = "ok",
            onClickButton = {},
            onDismissRequest = {}
        )
    }
}

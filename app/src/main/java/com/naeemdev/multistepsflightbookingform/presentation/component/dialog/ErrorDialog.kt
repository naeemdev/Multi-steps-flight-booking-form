package com.naeemdev.multistepsflightbookingform.presentation.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.naeemdev.multistepsflightbookingform.R
import com.naeemdev.multistepsflightbookingform.ui.theme.MultiStepsFlightBookingFormTheme

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    title: String? = null,
    description: String,
    icon: Painter? = painterResource(id = R.drawable.baseline_warning_amber_24),
    iconTint: Color = MaterialTheme.colorScheme.error,
    iconContentDescription: String = stringResource(R.string.error_title),
    buttons: List<DialogButtonAction>
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(all = 24.dp)
            ) {
                if (icon != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = iconContentDescription,
                            tint = iconTint,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = if (buttons.isNotEmpty()) 24.dp else 0.dp)
                )

                if (buttons.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        buttons.forEach { buttonAction ->
                            val buttonModifier = Modifier
                                .height(40.dp)
                            when (buttonAction.type) {
                                DialogButtonType.PRIMARY -> Button(
                                    onClick = {
                                        buttonAction.onClick()
                                    },
                                    enabled = buttonAction.enabled,
                                    modifier = buttonModifier
                                ) {
                                    Text(
                                        text = buttonAction.text,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                DialogButtonType.SECONDARY -> OutlinedButton(
                                    onClick = {
                                        buttonAction.onClick()
                                    },
                                    enabled = buttonAction.enabled,
                                    modifier = buttonModifier
                                ) {
                                    Text(
                                        text = buttonAction.text,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                DialogButtonType.TEXT -> TextButton(
                                    onClick = {
                                        buttonAction.onClick()
                                    },
                                    enabled = buttonAction.enabled,
                                    modifier = buttonModifier
                                ) {
                                    Text(
                                        text = buttonAction.text,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewErrorDialogOneButton() {
    MultiStepsFlightBookingFormTheme {
        ErrorDialog(
            onDismissRequest = {},
            title = stringResource(R.string.error_title),
            description = stringResource(R.string.error_no_internet),
            icon = painterResource(id = R.drawable.baseline_warning_amber_24),
            iconTint = MaterialTheme.colorScheme.error,
            buttons = listOf(
                DialogButtonAction(
                    text = stringResource(R.string.ok),
                    type = DialogButtonType.PRIMARY,
                    onClick = { }
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConfirmationDialogTwoButtons() {
    MultiStepsFlightBookingFormTheme {
        ErrorDialog(
            onDismissRequest = {},
            title = stringResource(R.string.app_name),
            description = stringResource(R.string.error_no_internet),
            iconTint = MaterialTheme.colorScheme.primary,
            buttons = listOf(
                DialogButtonAction(
                    text = stringResource(R.string.cancel),
                    type = DialogButtonType.TEXT,
                    onClick = { }
                ),
                DialogButtonAction(
                    text = stringResource(R.string.retry),
                    type = DialogButtonType.PRIMARY,
                    onClick = { }
                )
            )
        )
    }
}
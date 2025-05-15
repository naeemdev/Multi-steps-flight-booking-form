package com.naeemdev.multistepsflightbookingform.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naeemdev.multistepsflightbookingform.ui.theme.MultiStepsFlightBookingFormTheme


@Composable
fun ActionButtonWithIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowForward,
    contentDescription: String = "",
    containerColor: Color = Color.Black,
    contentColor: Color = White,
    shape: Shape = CircleShape,
    padding: PaddingValues = PaddingValues(16.dp),
    alignment: Alignment = Alignment.BottomEnd
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .padding(padding)
                .align(alignment),
            containerColor = containerColor,
            contentColor = contentColor,
            shape = shape
        ) {
            Icon(
                icon,
                contentDescription = contentDescription
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FloatingActionButtonPreview() {
    MultiStepsFlightBookingFormTheme {
        ActionButtonWithIcon(
            modifier = Modifier,
            onClick = {},
        )
    }
}
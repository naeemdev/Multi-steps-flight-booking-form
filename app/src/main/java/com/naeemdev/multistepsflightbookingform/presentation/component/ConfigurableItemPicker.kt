package com.naeemdev.multistepsflightbookingform.presentation.component
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naeemdev.multistepsflightbookingform.domain.model.PassportFormatD

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ConfigurableItemPicker(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    itemToString: (T) -> String,
    label: String = "Select Item",
    labelStyle: TextStyle = MaterialTheme.typography.bodySmall,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    enabled: Boolean = true,
    textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    textFieldShape: Shape = OutlinedTextFieldDefaults.shape,
    dropdownMenuBackgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    dropdownMenuItemTextColor: Color = MaterialTheme.colorScheme.onSurface,
    trailingIcon: @Composable ((Boolean) -> Unit) = { expanded ->
        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
    },
    placeholderText: String = ""
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (enabled) {
                expanded = !expanded
            }
        },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedItem?.let { itemToString(it) } ?: placeholderText,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            label = { Text(text = label, style = labelStyle, color = labelColor) },
            trailingIcon = { trailingIcon(expanded) },
            colors = textFieldColors,
            shape = textFieldShape,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(dropdownMenuBackgroundColor)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemToString(item), color = dropdownMenuItemTextColor) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
            if (items.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No options available", color = dropdownMenuItemTextColor.copy(alpha = 0.6f)) },
                    onClick = { expanded = false },
                    enabled = false
                )
            }
        }
    }
}



@Preview(showBackground = true, widthDp = 360)
@Composable
fun ItemPickerPreview() {
    MaterialTheme {
        val passportOptions = listOf(
            PassportFormatD("us", "United States", "USA"),
            PassportFormatD("ca", "Canada", "CAN"),
            PassportFormatD("gb", "United Kingdom", "GBR"),
            PassportFormatD("de", "Germany", "DEU")
        )
        var selectedPassport by remember { mutableStateOf<PassportFormatD?>(passportOptions.firstOrNull()) }

        val genderOptions = listOf("Male", "Female", "Non-binary", "Prefer not to say")
        var selectedGender by remember { mutableStateOf<String?>(genderOptions[0]) }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text("Passport Picker (Custom Objects):", style = MaterialTheme.typography.titleMedium)
            ConfigurableItemPicker(
                items = passportOptions,
                selectedItem = selectedPassport,
                onItemSelected = { selectedPassport = it },
                itemToString = { passport -> "${passport.nationality} (${passport.nationality})" },
                placeholderText = "Choose a country"
            )

            Text("Gender Picker (Strings):", style = MaterialTheme.typography.titleMedium)
            ConfigurableItemPicker(
                items = genderOptions,
                selectedItem = selectedGender,
                onItemSelected = { selectedGender = it },
                itemToString = { genderString -> genderString },
                label = "Select Your Gender",
                textFieldShape = RoundedCornerShape(8.dp)
            )

            Text("User Picker (Customized Look):", style = MaterialTheme.typography.titleMedium)
            ConfigurableItemPicker(
                items = passportOptions,
                selectedItem = selectedPassport,
                onItemSelected = { selectedPassport = it },
                itemToString = { user -> user.nationality },
                label = "Select User",
                placeholderText = "Pick a user...",
                labelStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                labelColor = MaterialTheme.colorScheme.primary,
                textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    disabledBorderColor = Color.LightGray,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.secondary
                ),
                textFieldShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                dropdownMenuBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                dropdownMenuItemTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                trailingIcon = { expanded ->
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) "Hide options" else "Show options",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    }
}
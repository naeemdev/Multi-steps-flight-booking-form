package com.naeemdev.multistepsflightbookingform.presentation.component.dialog

data class DialogButtonAction(
    val text: String,
    val type: DialogButtonType = DialogButtonType.PRIMARY,
    val enabled: Boolean = true,
    val onClick: () -> Unit
)
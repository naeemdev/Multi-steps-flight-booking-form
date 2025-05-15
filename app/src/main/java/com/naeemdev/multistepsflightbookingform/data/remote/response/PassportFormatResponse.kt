package com.naeemdev.multistepsflightbookingform.data.remote.response

import kotlinx.serialization.Serializable


@Serializable
data class PassportFormatResponse(
    val nationality: String? = null,
    val format: String? = null,
    val example: String? = null
)





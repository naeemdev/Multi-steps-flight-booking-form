package com.naeemdev.multistepsflightbookingform.domain

import com.naeemdev.multistepsflightbookingform.R

enum class ErrorType(val errorMessageResId: Int) {
    NO_INTERNET(R.string.error_no_internet),
    FORBIDDEN(R.string.error_forbidden),
    UNAUTHORIZED(R.string.error_unauthorized),
    UNKNOWN(R.string.error_unknown),
    API_RESPONSE_NULL(R.string.api_response_null),
    INTERNAL_SERVER_ERROR(R.string.error_internal_server),
    IO_EXCEPTION(R.string.error_no_internet),
    BOOKING_NOT_FOUND(R.string.booking_not_found),
}
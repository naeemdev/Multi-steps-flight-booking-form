package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

import android.os.Build
import androidx.annotation.RequiresApi
import com.naeemdev.multistepsflightbookingform.R
import java.time.LocalDate

class ExpiryDateNotInPastRule(private var errorMsg: Int = R.string.expiry_date_must_be_in_the_future) : BaseRule {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun validate(text: String): Boolean {
        // Convert text (assuming it is in the format "YYYY-MM-DD") to LocalDate
        val expiryDate = LocalDate.parse(text)
        val currentDate = LocalDate.now()
        return expiryDate.isAfter(currentDate)
    }

    override fun getErrorMessage(): Int = errorMsg

    override fun setError(msg: Int) {
        errorMsg = msg
    }
}
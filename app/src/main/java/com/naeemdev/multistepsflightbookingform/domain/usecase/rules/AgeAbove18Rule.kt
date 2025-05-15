package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

import android.os.Build
import androidx.annotation.RequiresApi
import com.naeemdev.multistepsflightbookingform.R
import java.time.LocalDate
import java.time.Period
import kotlin.Int

class AgeAbove18Rule(private var errorMsg: Int = R.string.dob_must_be_in_the_past_and_age_over_18) : BaseRule {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun validate(text: String): Boolean {
        val dob = LocalDate.parse(text)
        val currentDate = LocalDate.now()
        val age = Period.between(dob, currentDate).years

        return age >= 18
    }

    override fun getErrorMessage(): Int = errorMsg

    override fun setError(msg: Int) {
        errorMsg = msg
    }
}
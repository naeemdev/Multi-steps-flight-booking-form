package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

import android.util.Patterns
import com.naeemdev.multistepsflightbookingform.R

class EmailRule(var errorMsg: Int = R.string.invalid_email_address) : BaseRule {
    override fun validate(text: String): Boolean {
        val emailRegex = Patterns.EMAIL_ADDRESS.toRegex()
        return text.matches(emailRegex)
    }

    override fun getErrorMessage(): Int = errorMsg

    override fun setError(msg: Int) {
        errorMsg = msg
    }
}

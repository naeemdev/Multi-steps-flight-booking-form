package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

import com.naeemdev.multistepsflightbookingform.R

class MinLengthRule(
    private val minLength: Int,
    var errorMsg: Int = R.string.length_should_be_greater_than
) : BaseRule {

    override fun validate(text: String): Boolean {
        return text.length >= minLength
    }

    override fun getErrorMessage(): Int = errorMsg

    override fun setError(msg: Int) {
        errorMsg = msg
    }
}

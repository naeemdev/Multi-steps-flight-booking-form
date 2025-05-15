package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

import com.naeemdev.multistepsflightbookingform.R

class NonEmptyRule(var errorMsg: Int = R.string.can_t_be_empty) : BaseRule {
    override fun validate(text: String): Boolean = text.isNotEmpty()

    override fun getErrorMessage(): Int = errorMsg

    override fun setError(msg: Int) {
        errorMsg = msg
    }
}

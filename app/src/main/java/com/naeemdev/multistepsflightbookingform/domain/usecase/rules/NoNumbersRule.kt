package com.naeemdev.multistepsflightbookingform.domain.usecase.rules

import com.naeemdev.multistepsflightbookingform.R


class NoNumbersRule(var errorMsg: Int = R.string.should_not_contain_any_numbers) : BaseRule {

    override fun validate(text: String): Boolean {
        return !text.contains(Regex("\\d"))
    }

    override fun getErrorMessage(): Int = errorMsg

    override fun setError(msg: Int) {
        errorMsg = msg
    }
}
